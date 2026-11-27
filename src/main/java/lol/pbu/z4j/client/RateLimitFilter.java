/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lol.pbu.z4j.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import jakarta.inject.Inject;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lol.pbu.z4j.ratelimit.EndpointRateLimit;
import lol.pbu.z4j.ratelimit.RateLimitConfiguration;
import lol.pbu.z4j.ratelimit.RateLimitSnapshot;
import lol.pbu.z4j.ratelimit.RateLimitTracker;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Client filter that tracks Zendesk rate limits, coordinates shared 429 cooldowns,
 * paces outbound requests, and handles retries cleanly.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.3
 */
@ClientFilter(value = "/**", serviceId = "zendesk")
public class RateLimitFilter implements HttpClientFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RateLimitFilter.class);
    private static final int FILTER_ORDER = 0;

    private static final String HEADER_X_RATE_LIMIT = "X-Rate-Limit";
    private static final String HEADER_RATELIMIT_LIMIT = "ratelimit-limit";
    private static final String HEADER_X_RATE_LIMIT_REMAINING = "X-Rate-Limit-Remaining";
    private static final String HEADER_RATELIMIT_REMAINING = "ratelimit-remaining";
    private static final String HEADER_RATELIMIT_RESET = "ratelimit-reset";
    private static final String HEADER_RETRY_AFTER = "Retry-After";
    private static final String HEADER_CF_RAY = "cf-ray";
    private static final String NETTY_CHANNEL_ATTRIBUTE = "netty_channel";
    private static final String ATTR_IN_PROGRESS = "z4j.rate_limit.in_progress";

    private final RateLimitTracker tracker;
    private final RateLimitConfiguration config;
    private final Clock clock;
    private final AtomicLong nextAvailableTime = new AtomicLong();

    @Inject
    public RateLimitFilter(RateLimitTracker tracker, RateLimitConfiguration config) {
        this(tracker, config, Clock.systemUTC());
    }

    RateLimitFilter(RateLimitTracker tracker, RateLimitConfiguration config, Clock clock) {
        this.tracker = Objects.requireNonNull(tracker, "tracker");
        this.config = Objects.requireNonNull(config, "config");
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    @Override
    public int getOrder() {
        return FILTER_ORDER;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        if (isInProgress(request)) {
            return chain.proceed(request);
        }
        return Flux.defer(() -> executeWithRetry(request, chain, 0));
    }

    private static boolean isInProgress(@Nullable MutableHttpRequest<?> request) {
        if (request == null) {
            return false;
        }
        return request.getAttribute(ATTR_IN_PROGRESS, Boolean.class).orElse(false);
    }

    Duration calculatePacingDelay() {
        double rps = config.getRequestsPerSecond();
        if (rps <= 0.0) {
            return Duration.ZERO;
        }
        long intervalNanos = (long) (1_000_000_000L / rps);
        while (true) {
            long now = System.nanoTime();
            long current = nextAvailableTime.get();
            long scheduledTime = (current == 0 || (now - current) > 0) ? now : current;
            long next = scheduledTime + intervalNanos;
            if (nextAvailableTime.compareAndSet(current, next)) {
                long delayNanos = scheduledTime - now;
                return delayNanos > 0 ? Duration.ofNanos(delayNanos) : Duration.ZERO;
            }
        }
    }

    private Flux<HttpResponse<?>> executeWithRetry(MutableHttpRequest<?> request, ClientFilterChain chain, int attempt) {
        return waitBeforeAttempt(request, attempt)
                .thenMany(Flux.defer(() -> {
                    MutableHttpRequest<?> attemptRequest = copyRequest(request);
                    return Flux.from(proceedWithAttempt(attemptRequest, chain))
                            .doOnNext(response -> handleResponse(attemptRequest, response))
                            .onErrorResume(HttpClientException.class,
                                    ex -> handleException(request, attemptRequest, chain, attempt, ex));
                }));
    }

    private Mono<Void> waitBeforeAttempt(MutableHttpRequest<?> request, int attempt) {
        return Mono.defer(() -> {
            Duration preDelay = resolvePreAttemptDelay(request, attempt);
            Mono<Void> gate = preDelay.isZero() ? Mono.empty() : Mono.delay(preDelay).then();
            return gate.then(Mono.defer(() -> {
                Duration pacingDelay = calculatePacingDelay();
                return pacingDelay.isZero() ? Mono.empty() : Mono.delay(pacingDelay).then();
            }));
        });
    }

    private Duration resolvePreAttemptDelay(MutableHttpRequest<?> request, int attempt) {
        Duration sharedCooldown = tracker.getRetryAfterDelay();
        if (!sharedCooldown.isZero() && !sharedCooldown.isNegative()) {
            log.warn("Shared HTTP 429 cooldown active. Pausing {} ms before sending {} {}",
                    sharedCooldown.toMillis(), request.getMethodName(), request.getPath());
            return sharedCooldown;
        }
        if (shouldAutoWait(attempt)) {
            Duration waitDuration = resolveAutoWaitDuration();
            if (!waitDuration.isZero() && !waitDuration.isNegative()) {
                log.warn("Rate limit approaching threshold (<= {}). Auto-wait enabled. Pausing for {} seconds before sending request to {}",
                        config.getApproachThreshold(), formatSeconds(waitDuration), request.getPath());
                return waitDuration;
            }
        }
        return Duration.ZERO;
    }

    @SuppressWarnings("unchecked")
    private Publisher<HttpResponse<?>> proceedWithAttempt(MutableHttpRequest<?> attemptRequest, ClientFilterChain chain) {
        return (Publisher<HttpResponse<?>>) chain.proceed(attemptRequest);
    }

    private MutableHttpRequest<Object> copyRequest(MutableHttpRequest<?> source) {
        MutableHttpRequest<Object> copy = HttpRequest.create(
                source.getMethod(),
                source.getUri().toString(),
                source.getMethodName()
        );
        source.getBody().ifPresent(copy::body);
        source.getHeaders().forEach((name, values) ->
                values.forEach(value -> copy.header(name, value)));
        source.getAttributes().forEach((name, value) -> {
            if (!NETTY_CHANNEL_ATTRIBUTE.equals(name)) {
                copy.setAttribute(name, value);
            }
        });
        copy.setAttribute(ATTR_IN_PROGRESS, Boolean.TRUE);
        return copy;
    }

    private Duration autoWaitFallback() {
        return Duration.ofSeconds(Math.max(0, config.getWaitDurationSeconds()));
    }

    private Duration resolveAutoWaitDuration() {
        Duration fallback = autoWaitFallback();
        if (tracker.getLatestSnapshot() == null && tracker.getAllEndpointLimits().isEmpty()) {
            return fallback;
        }
        return tracker.getApproachingLimitWaitDuration(config.getApproachThreshold(), fallback);
    }

    private boolean shouldAutoWait(int attempt) {
        return attempt == 0
                && config.isAutoWaitEnabled()
                && tracker.isApproachingLimit(config.getApproachThreshold(), autoWaitFallback());
    }

    private Publisher<HttpResponse<?>> handleException(
            MutableHttpRequest<?> request,
            MutableHttpRequest<?> attemptRequest,
            ClientFilterChain chain,
            int attempt,
            HttpClientException ex
    ) {
        if (ex instanceof HttpClientResponseException responseException) {
            handleResponse(attemptRequest, responseException.getResponse());
            if (canRetryRateLimit(responseException, attempt)) {
                return scheduleRetry(request, chain, attempt, responseException);
            }
            if (isCloudflareBlock(responseException)) {
                return mapCloudflareBlock(attemptRequest, responseException);
            }
            if (canRetryLockConflict(responseException, attempt)) {
                return scheduleConflictRetry(request, chain, attempt);
            }
            if (canRetryTransientResponse(attemptRequest, responseException, attempt)) {
                return scheduleTransientRetry(
                        request,
                        chain,
                        attempt,
                        "HTTP " + resolveStatusCode(responseException)
                );
            }
            return Flux.error(responseException);
        }
        if (canRetryTransientNetwork(attemptRequest, attempt)) {
            return scheduleTransientRetry(
                    request,
                    chain,
                    attempt,
                    "Network error: " + ex.getMessage()
            );
        }
        return Flux.error(ex);
    }

    private boolean canRetryRateLimit(HttpClientResponseException ex, int attempt) {
        return resolveStatusCode(ex) == HttpStatus.TOO_MANY_REQUESTS.getCode() && attempt < 5;
    }

    private boolean canRetryLockConflict(HttpClientResponseException ex, int attempt) {
        return resolveStatusCode(ex) == HttpStatus.CONFLICT.getCode() && attempt < 2;
    }

    private Publisher<HttpResponse<?>> scheduleConflictRetry(MutableHttpRequest<?> request, ClientFilterChain chain, int attempt) {
        log.warn("HTTP 409 Conflict received for {} {}. Retrying lock conflict (attempt {}) after 1 second...",
                request.getMethodName(), request.getPath(), attempt + 1);
        return Mono.delay(Duration.ofSeconds(1))
                .flatMapMany(v -> executeWithRetry(request, chain, attempt + 1));
    }

    private boolean canRetryTransientResponse(MutableHttpRequest<?> request, HttpClientResponseException ex, int attempt) {
        if (!isIdempotent(request) || attempt >= config.getMaxTransientRetries()) {
            return false;
        }
        int code = resolveStatusCode(ex);
        return code >= 500 && code < 600;
    }

    private boolean canRetryTransientNetwork(MutableHttpRequest<?> request, int attempt) {
        return isIdempotent(request) && attempt < config.getMaxTransientRetries();
    }

    private boolean isIdempotent(MutableHttpRequest<?> request) {
        HttpMethod method = request.getMethod();
        return method == HttpMethod.GET
                || method == HttpMethod.PUT
                || method == HttpMethod.DELETE
                || method == HttpMethod.HEAD
                || method == HttpMethod.OPTIONS;
    }

    private Publisher<HttpResponse<?>> scheduleTransientRetry(MutableHttpRequest<?> request, ClientFilterChain chain, int attempt, String reason) {
        long waitTime = Math.max(1, config.getTransientRetryDelaySeconds());
        log.warn("{} received for {} {}. Retrying transient error (attempt {}) after {} seconds...",
                reason, request.getMethodName(), request.getPath(), attempt + 1, waitTime);
        return Mono.delay(Duration.ofSeconds(waitTime))
                .flatMapMany(v -> executeWithRetry(request, chain, attempt + 1));
    }

    private Publisher<HttpResponse<?>> scheduleRetry(MutableHttpRequest<?> request, ClientFilterChain chain, int attempt, HttpClientResponseException ex) {
        Duration waitDuration = resolveWaitDuration(ex.getResponse());
        tracker.recordRetryAfter(waitDuration);
        log.warn("HTTP 429 received for {} {}. Retrying (attempt {}) after {} seconds...",
                request.getMethodName(), request.getPath(), attempt + 1, formatSeconds(waitDuration));
        if (waitDuration.isZero() || waitDuration.isNegative()) {
            return executeWithRetry(request, chain, attempt + 1);
        }
        return Mono.delay(waitDuration)
                .flatMapMany(v -> executeWithRetry(request, chain, attempt + 1));
    }

    private Duration resolveWaitDuration(@Nullable HttpResponse<?> response) {
        HttpHeaders headers = response != null ? response.getHeaders() : null;
        if (headers != null) {
            Duration retryAfter = parseRetryAfterDuration(headers);
            if (retryAfter != null) {
                return retryAfter;
            }
            Integer resetSeconds = parseIntegerHeader(headers, HEADER_RATELIMIT_RESET);
            if (resetSeconds != null && resetSeconds >= 0) {
                return Duration.ofSeconds(resetSeconds);
            }
        }
        return Duration.ofSeconds(Math.max(5, config.getWaitDurationSeconds()));
    }

    @Nullable
    Duration parseRetryAfterDuration(HttpHeaders headers) {
        if (headers == null) {
            return null;
        }
        String raw = headers.get(HEADER_RETRY_AFTER);
        if (raw == null || raw.isBlank()) {
            return null;
        }
        String trimmed = raw.trim();
        try {
            long seconds = Long.parseLong(trimmed);
            return Duration.ofSeconds(Math.max(0, seconds));
        } catch (NumberFormatException _) {
            // Fall through to HTTP-date parsing.
        }
        try {
            Instant target = ZonedDateTime.parse(trimmed, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant();
            Duration delta = Duration.between(clock.instant(), target);
            return delta.isNegative() ? Duration.ZERO : delta;
        } catch (DateTimeParseException _) {
            return null;
        }
    }

    @Nullable
    private Integer parseRetryAfterSeconds(HttpHeaders headers) {
        Duration duration = parseRetryAfterDuration(headers);
        if (duration == null) {
            return null;
        }
        if (duration.isZero() || duration.isNegative()) {
            return 0;
        }
        long ceilingSeconds = (duration.toMillis() + 999L) / 1000L;
        return (int) Math.min(Integer.MAX_VALUE, ceilingSeconds);
    }

    private Publisher<HttpResponse<?>> mapCloudflareBlock(MutableHttpRequest<?> request, HttpClientResponseException ex) {
        HttpResponse<?> original = ex.getResponse();
        HttpHeaders originalHeaders = original.getHeaders();
        String cfRay = originalHeaders.get(HEADER_CF_RAY);
        log.warn("Cloudflare edge returned 400 HTML instead of 403 Forbidden for {} {} (cf-ray={}). Mapping to 403 Forbidden.",
                request.getMethodName(), request.getPath(), cfRay != null ? cfRay : "none");

        String body = extractResponseBody(original);
        MutableHttpResponse<String> mapped = HttpResponse.status(HttpStatus.FORBIDDEN);
        originalHeaders.forEach((name, values) ->
                values.forEach(value -> mapped.header(name, value)));
        if (body != null) {
            mapped.body(body);
        }
        return Flux.error(new HttpClientResponseException(
                "Cloudflare edge returned 400 HTML instead of 403 Forbidden: " + ex.getMessage(),
                mapped
        ));
    }

    private boolean isCloudflareBlock(HttpClientResponseException ex) {
        if (resolveStatusCode(ex) != HttpStatus.BAD_REQUEST.getCode()) {
            return false;
        }
        return containsCloudflareHtml(ex.getMessage()) || containsCloudflareHtml(extractResponseBody(ex.getResponse()));
    }

    private boolean containsCloudflareHtml(String content) {
        if (content == null) {
            return false;
        }
        String lower = content.toLowerCase();
        return lower.contains("<center>cloudflare</center>")
                || (lower.contains("cloudflare") && lower.contains("<html"));
    }

    private String extractResponseBody(HttpResponse<?> response) {
        if (response == null) {
            return null;
        }
        try {
            return response.getBody(String.class).orElse(null);
        } catch (Exception _) {
            return null;
        }
    }

    public void handleResponse(@Nullable MutableHttpRequest<?> request, @Nullable HttpResponse<?> response) {
        if (response == null) {
            return;
        }

        HttpHeaders headers = response.getHeaders();
        Integer globalLimit = parseIntegerHeader(headers, HEADER_RATELIMIT_LIMIT, HEADER_X_RATE_LIMIT);
        Integer globalRemaining = parseIntegerHeader(headers, HEADER_RATELIMIT_REMAINING, HEADER_X_RATE_LIMIT_REMAINING);
        Integer globalResetSeconds = parseIntegerHeader(headers, HEADER_RATELIMIT_RESET);
        Integer retryAfterSeconds = parseRetryAfterSeconds(headers);

        Map<String, EndpointRateLimit> endpointLimits = headers.names().stream()
                .filter(name -> name != null && name.toLowerCase().startsWith(EndpointRateLimit.PREFIX))
                .flatMap(name -> Optional.ofNullable(headers.get(name)).stream()
                        .map(value -> EndpointRateLimit.parse(name, value)))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(EndpointRateLimit::getName, limit -> limit, (first, second) -> second));

        String requestMethod = request != null ? request.getMethodName() : null;
        String requestPath = request != null ? request.getPath() : null;
        String requestUri = request != null ? request.getUri().toString() : null;

        RateLimitSnapshot snapshot = RateLimitSnapshot.builder()
                .requestMethod(requestMethod)
                .requestPath(requestPath)
                .requestUri(requestUri)
                .statusCode(resolveStatusCode(response))
                .globalLimit(globalLimit)
                .globalRemaining(globalRemaining)
                .globalResetSeconds(globalResetSeconds)
                .retryAfterSeconds(retryAfterSeconds)
                .endpointLimits(endpointLimits)
                .timestamp(clock.instant())
                .build();

        if (snapshot.isRateLimited()) {
            log.warn("Zendesk Rate Limit Exceeded (HTTP 429) for {} {}. Retry after: {} seconds",
                    requestMethod, requestPath, retryAfterSeconds);
        } else if (snapshot.isApproachingLimit(config.getApproachThreshold())) {
            log.warn("Zendesk Rate Limit approaching threshold for {} {}: global remaining={}, endpoint limits={}",
                    requestMethod, requestPath, globalRemaining, endpointLimits);
        } else {
            log.debug("Zendesk Rate Limit for {} {}: remaining={}, reset={}s, endpoints={}",
                    requestMethod, requestPath, globalRemaining, globalResetSeconds, endpointLimits.keySet());
        }

        tracker.recordSnapshot(snapshot);
    }

    private static int resolveStatusCode(@Nullable HttpResponse<?> response) {
        if (response == null) {
            return 0;
        }
        int code = response.code();
        if (code > 0) {
            return code;
        }
        return response.getStatus().getCode();
    }

    private static int resolveStatusCode(HttpClientResponseException ex) {
        int code = ex.code();
        return code > 0 ? code : resolveStatusCode(ex.getResponse());
    }

    @Nullable
    private Integer parseIntegerHeader(@Nullable HttpHeaders headers, String... candidateNames) {
        if (headers == null || candidateNames == null || candidateNames.length == 0) {
            return null;
        }
        return Arrays.stream(candidateNames)
                .filter(Objects::nonNull)
                .map(headers::get)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .map(RateLimitFilter::parseIntegerOrNull)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    private static Integer parseIntegerOrNull(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException _) {
            return null;
        }
    }

    private static long formatSeconds(@Nullable Duration duration) {
        if (duration == null || duration.isZero() || duration.isNegative()) {
            return 0;
        }
        return (duration.toMillis() + 999L) / 1000L;
    }
}
