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
package lol.pbu.z4j.ratelimit;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lol.pbu.z4j.Generated;

/**
 * Snapshot of Zendesk rate limit state after an HTTP response.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.3
 */
@Serdeable
@RecordBuilder
@Generated
public record RateLimitSnapshot(
        @Nullable String requestMethod,
        @Nullable String requestPath,
        @Nullable String requestUri,
        int statusCode,
        @Nullable Integer globalLimit,
        @Nullable Integer globalRemaining,
        @Nullable Integer globalResetSeconds,
        @Nullable Integer retryAfterSeconds,
        Map<String, EndpointRateLimit> endpointLimits,
        Instant timestamp
) implements RateLimitSnapshotBuilder.With, Serializable {

    public RateLimitSnapshot {
        if (endpointLimits == null) {
            endpointLimits = Collections.emptyMap();
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }

    public RateLimitSnapshot() {
        this(null, null, null, 0, null, null, null, null, Collections.emptyMap(), Instant.now());
    }

    public static RateLimitSnapshotBuilder builder() {
        return RateLimitSnapshotBuilder.builder();
    }

    /**
     * @return true if the response was an HTTP 429 Too Many Requests
     */
    public boolean isRateLimited() {
        return statusCode == 429;
    }

    /**
     * Checks if either the global quota or any endpoint quota has fallen to or below the given threshold.
     *
     * @param threshold Warning threshold
     * @return true if any remaining quota &lt;= threshold
     */
    public boolean isApproachingLimit(int threshold) {
        if (globalRemaining != null && globalRemaining <= threshold) {
            return true;
        }
        if (endpointLimits != null) {
            return endpointLimits.values().stream()
                    .anyMatch(e -> e.remaining() != null && e.remaining() <= threshold);
        }
        return false;
    }

    /**
     * Alias for isApproachingLimit.
     *
     * @param threshold Warning threshold
     * @return true if any remaining quota &lt;= threshold
     */
    public boolean isApproachingRateLimit(int threshold) {
        return isApproachingLimit(threshold);
    }

    /**
     * Calculates the effective wait duration based on retry-after or reset headers.
     *
     * @return effective wait duration
     */
    public Duration getEffectiveWaitDuration() {
        if (retryAfterSeconds != null && retryAfterSeconds > 0) {
            return Duration.ofSeconds(retryAfterSeconds);
        }
        if (globalResetSeconds != null && globalResetSeconds > 0) {
            return Duration.ofSeconds(globalResetSeconds);
        }
        return Duration.ZERO;
    }

    /**
     * Retrieves an endpoint-specific rate limit by its clean name (e.g. {@code "search-index"}).
     *
     * @param name Name of the endpoint limit
     * @return Optional containing the {@link EndpointRateLimit} if present
     */
    public Optional<EndpointRateLimit> getEndpointLimit(String name) {
        if (endpointLimits == null || name == null) {
            return Optional.empty();
        }
        String clean = name.toLowerCase();
        if (clean.startsWith(EndpointRateLimit.PREFIX)) {
            clean = clean.substring(EndpointRateLimit.PREFIX.length());
        }
        return Optional.ofNullable(endpointLimits.get(clean));
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Integer getGlobalLimit() {
        return globalLimit;
    }

    public Integer getGlobalRemaining() {
        return globalRemaining;
    }

    public Integer getGlobalResetSeconds() {
        return globalResetSeconds;
    }

    public Integer getRetryAfterSeconds() {
        return retryAfterSeconds;
    }

    public Map<String, EndpointRateLimit> getEndpointLimits() {
        return endpointLimits;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
