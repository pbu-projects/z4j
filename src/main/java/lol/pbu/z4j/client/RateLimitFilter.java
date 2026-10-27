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

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
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
 * Client filter that intercepts Zendesk HTTP responses to capture rate limit metrics,
 * including global quotas and endpoint-specific headers (e.g. {@code zendesk-ratelimit-*})
 * as well as HTTP 429 Retry-After headers.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.3
 */
@ClientFilter(value = "/**", serviceId = "zendesk")
public class RateLimitFilter implements HttpClientFilter {

    private static final Logger log = LoggerFactory.getLogger(RateLimitFilter.class);

    private static final String HEADER_X_RATE_LIMIT = "X-Rate-Limit";
    private static final String HEADER_RATELIMIT_LIMIT = "ratelimit-limit";
    private static final String HEADER_X_RATE_LIMIT_REMAINING = "X-Rate-Limit-Remaining";
    private static final String HEADER_RATELIMIT_REMAINING = "ratelimit-remaining";
    private static final String HEADER_RATELIMIT_RESET = "ratelimit-reset";
    private static final String HEADER_RETRY_AFTER = "Retry-After";

    private final RateLimitTracker tracker;
    private final RateLimitConfiguration config;
    private final AtomicLong nextAvailableTime = new AtomicLong();

    public RateLimitFilter(RateLimitTracker tracker, RateLimitConfiguration config) {
        this.tracker = tracker;
        this.config = config;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        return Flux.defer(() -> {
            Duration delay = calculatePacingDelay();
            if (!delay.isZero()) {
                return Mono.delay(delay).flatMapMany(v -> executeWithRetry(request, chain, 0));
            }
            return executeWithRetry(request, chain, 0);
        });
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
        return buildProceedFlux(request, chain, attempt)
                .doOnNext(response -> handleResponse(request, response))
                .onErrorResume(HttpClientResponseException.class, ex -> handleException(request, chain, attempt, ex));
    }

    @SuppressWarnings("unchecked")
    private Flux<HttpResponse<?>> buildProceedFlux(MutableHttpRequest<?> request, ClientFilterChain chain, int attempt) {
        return Flux.defer(() -> {
            if (shouldAutoWait(attempt)) {
                log.warn("Rate limit approaching threshold (<= {}). Auto-wait enabled. Pausing for {} seconds before sending request to {}",
                        config.getApproachThreshold(), config.getWaitDurationSeconds(), request.getPath());
                return Mono.delay(Duration.ofSeconds(config.getWaitDurationSeconds()))
                        .flatMapMany(v -> (Publisher<HttpResponse<?>>) chain.proceed(request));
            }
            return (Publisher<HttpResponse<?>>) chain.proceed(request);
        });
    }

    private boolean shouldAutoWait(int attempt) {
        return attempt == 0 && config.isAutoWaitEnabled() && tracker.isApproachingLimit(config.getApproachThreshold());
    }

    private Publisher<HttpResponse<?>> handleException(MutableHttpRequest<?> request, ClientFilterChain chain, int attempt, HttpClientResponseException ex) {
        handleResponse(request, ex.getResponse());
        if (canRetryRateLimit(ex, attempt)) {
            return scheduleRetry(request, chain, attempt, ex);
        }
        if (isCloudflareBlock(ex)) {
            return mapCloudflareBlock(request, ex);
        }
        return Flux.error(ex);
    }

    private boolean canRetryRateLimit(HttpClientResponseException ex, int attempt) {
        return ex.getStatus() == HttpStatus.TOO_MANY_REQUESTS && attempt < 5;
    }

    private Publisher<HttpResponse<?>> scheduleRetry(MutableHttpRequest<?> request, ClientFilterChain chain, int attempt, HttpClientResponseException ex) {
        long waitTime = resolveWaitTime(ex.getResponse());
        log.warn("HTTP 429 received for {} {}. Retrying (attempt {}) after {} seconds...",
                request.getMethodName(), request.getPath(), attempt + 1, waitTime);
        return Mono.delay(Duration.ofSeconds(waitTime))
                .flatMapMany(v -> executeWithRetry(request, chain, attempt + 1));
    }

    private long resolveWaitTime(HttpResponse<?> response) {
        if (response != null) {
            Integer retryAfter = parseIntegerHeader(response.getHeaders(), HEADER_RETRY_AFTER);
            if (retryAfter != null && retryAfter > 0) {
                return retryAfter;
            }
        }
        return Math.max(5, config.getWaitDurationSeconds());
    }

    private Publisher<HttpResponse<?>> mapCloudflareBlock(MutableHttpRequest<?> request, HttpClientResponseException ex) {
        log.warn("Cloudflare edge returned 400 HTML instead of 403 Forbidden for {} {}. Mapping to 403 Forbidden.",
                request.getMethodName(), request.getPath());
        return Flux.error(new HttpClientResponseException(
                "Cloudflare edge returned 400 HTML instead of 403 Forbidden: " + ex.getMessage(),
                HttpResponse.status(HttpStatus.FORBIDDEN)
        ));
    }

    private boolean isCloudflareBlock(HttpClientResponseException ex) {
        if (ex.getStatus() != HttpStatus.BAD_REQUEST) {
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

    public void handleResponse(MutableHttpRequest<?> request, HttpResponse<?> response) {
        if (response == null) {
            return;
        }

        HttpHeaders headers = response.getHeaders();
        Integer globalLimit = parseIntegerHeader(headers, HEADER_RATELIMIT_LIMIT, HEADER_X_RATE_LIMIT);
        Integer globalRemaining = parseIntegerHeader(headers, HEADER_RATELIMIT_REMAINING, HEADER_X_RATE_LIMIT_REMAINING);
        Integer globalResetSeconds = parseIntegerHeader(headers, HEADER_RATELIMIT_RESET);
        Integer retryAfterSeconds = parseIntegerHeader(headers, HEADER_RETRY_AFTER);

        Map<String, EndpointRateLimit> endpointLimits = new HashMap<>();
        for (String name : headers.names()) {
            if (name.toLowerCase().startsWith(EndpointRateLimit.PREFIX)) {
                String val = headers.get(name);
                if (val != null) {
                    EndpointRateLimit limit = EndpointRateLimit.parse(name, val);
                    endpointLimits.put(limit.getName(), limit);
                }
            }
        }

        RateLimitSnapshot snapshot = RateLimitSnapshot.builder()
                .requestMethod(request.getMethodName())
                .requestPath(request.getPath())
                .requestUri(request.getUri().toString())
                .statusCode(response.getStatus().getCode())
                .globalLimit(globalLimit)
                .globalRemaining(globalRemaining)
                .globalResetSeconds(globalResetSeconds)
                .retryAfterSeconds(retryAfterSeconds)
                .endpointLimits(endpointLimits)
                .timestamp(Instant.now())
                .build();

        if (snapshot.isRateLimited()) {
            log.warn("Zendesk Rate Limit Exceeded (HTTP 429) for {} {}. Retry after: {} seconds",
                    request.getMethodName(), request.getPath(), retryAfterSeconds);
        } else if (snapshot.isApproachingLimit(config.getApproachThreshold())) {
            log.warn("Zendesk Rate Limit approaching threshold for {} {}: global remaining={}, endpoint limits={}",
                    request.getMethodName(), request.getPath(), globalRemaining, endpointLimits);
        } else {
            log.debug("Zendesk Rate Limit for {} {}: remaining={}, reset={}s, endpoints={}",
                    request.getMethodName(), request.getPath(), globalRemaining, globalResetSeconds, endpointLimits.keySet());
        }

        tracker.recordSnapshot(snapshot);
    }

    private Integer parseIntegerHeader(HttpHeaders headers, String... candidateNames) {
        for (String name : candidateNames) {
            String value = headers.get(name);
            if (value != null && !value.isBlank()) {
                try {
                    return Integer.parseInt(value.trim());
                } catch (NumberFormatException _) {
                    // try next candidate
                }
            }
        }
        return null;
    }
}
