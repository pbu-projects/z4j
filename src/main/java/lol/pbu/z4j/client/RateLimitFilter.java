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
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.ClientFilter;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import lol.pbu.z4j.ratelimit.EndpointRateLimit;
import lol.pbu.z4j.ratelimit.RateLimitSnapshot;
import lol.pbu.z4j.ratelimit.RateLimitTracker;

import lol.pbu.z4j.ratelimit.RateLimitConfiguration;
import java.time.Duration;
import reactor.core.publisher.Mono;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Client filter that intercepts Zendesk HTTP responses to capture rate limit metrics,
 * including global quotas and endpoint-specific headers (e.g. {@code zendesk-ratelimit-*})
 * as well as HTTP 429 Retry-After headers.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.3
 */
@ClientFilter("/**")
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

    public RateLimitFilter(RateLimitTracker tracker, RateLimitConfiguration config) {
        this.tracker = tracker;
        this.config = config;
    }

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        return executeWithRetry(request, chain, 0);
    }

    @SuppressWarnings("unchecked")
    private Flux<HttpResponse<?>> executeWithRetry(MutableHttpRequest<?> request, ClientFilterChain chain, int attempt) {
        Flux<HttpResponse<?>> proceedFlux = Flux.defer(() -> {
            if (attempt == 0 && config.isAutoWaitEnabled() && tracker.isApproachingLimit(config.getApproachThreshold())) {
                log.warn("Rate limit approaching threshold (<= {}). Auto-wait enabled. Pausing for {} seconds before sending request to {}",
                        config.getApproachThreshold(), config.getWaitDurationSeconds(), request.getPath());
                return Mono.delay(Duration.ofSeconds(config.getWaitDurationSeconds()))
                        .flatMapMany(v -> (Publisher<HttpResponse<?>>) chain.proceed(request));
            }
            return (Publisher<HttpResponse<?>>) chain.proceed(request);
        });

        return proceedFlux
                .doOnNext(response -> handleResponse(request, response))
                .onErrorResume(HttpClientResponseException.class, ex -> {
                    handleResponse(request, ex.getResponse());
                    if (ex.getResponse() != null && ex.getStatus() != null
                            && ex.getStatus().getCode() == 429
                            && attempt < 5) {
                        HttpHeaders headers = ex.getResponse().getHeaders();
                        Integer retryAfter = parseIntegerHeader(headers, HEADER_RETRY_AFTER);
                        long waitTime = (retryAfter != null && retryAfter > 0) ? retryAfter : config.getWaitDurationSeconds();
                        if (waitTime <= 0) waitTime = 5;
                        log.warn("HTTP 429 received for {} {}. Retrying (attempt {}) after {} seconds...",
                                request.getMethodName(), request.getPath(), attempt + 1, waitTime);
                        final long finalWait = waitTime;
                        return Mono.delay(Duration.ofSeconds(finalWait))
                                .flatMapMany(v -> executeWithRetry(request, chain, attempt + 1));
                    }
                    return Flux.error(ex);
                });
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
        } else if (snapshot.isApproachingLimit(50)) {
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
