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

import io.micronaut.context.annotation.ConfigurationProperties;
import lol.pbu.z4j.Generated;

/**
 * Configuration options for Zendesk rate limiting.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.3
 */
@ConfigurationProperties("z4j.rate-limit")
@Generated
public class RateLimitConfiguration {
    public static final String PREFIX = "z4j.rate-limit";

    /**
     * Whether to automatically wait/pause when approaching rate limits before sending a request.
     */
    private boolean autoWaitEnabled = true;

    /**
     * The duration to wait (in seconds) before proceeding, if approaching a rate limit.
     */
    private long waitDurationSeconds = 60;

    /**
     * The remaining request count threshold to trigger the auto-wait.
     */
    private int approachThreshold = 1;

    /**
     * Target speed limit for outbound requests in requests per second.
     * When greater than zero, outbound requests are paced to stay under this rate.
     */
    private double requestsPerSecond = 0.0;

    /**
     * Maximum retry attempts for transient server errors (5xx) on idempotent requests.
     * Defaults to 0 (disabled).
     */
    private int maxTransientRetries = 0;

    /**
     * Delay in seconds between transient retry attempts.
     * Defaults to 1 second.
     */
    private long transientRetryDelaySeconds = 1;

    public boolean isAutoWaitEnabled() {
        return autoWaitEnabled;
    }

    public void setAutoWaitEnabled(boolean autoWaitEnabled) {
        this.autoWaitEnabled = autoWaitEnabled;
    }

    public long getWaitDurationSeconds() {
        return waitDurationSeconds;
    }

    public void setWaitDurationSeconds(long waitDurationSeconds) {
        this.waitDurationSeconds = waitDurationSeconds;
    }

    public int getApproachThreshold() {
        return approachThreshold;
    }

    public void setApproachThreshold(int approachThreshold) {
        this.approachThreshold = approachThreshold;
    }

    public double getRequestsPerSecond() {
        return requestsPerSecond;
    }

    public void setRequestsPerSecond(double requestsPerSecond) {
        this.requestsPerSecond = requestsPerSecond;
    }

    public int getMaxTransientRetries() {
        return maxTransientRetries;
    }

    public void setMaxTransientRetries(int maxTransientRetries) {
        this.maxTransientRetries = maxTransientRetries;
    }

    public long getTransientRetryDelaySeconds() {
        return transientRetryDelaySeconds;
    }

    public void setTransientRetryDelaySeconds(long transientRetryDelaySeconds) {
        this.transientRetryDelaySeconds = transientRetryDelaySeconds;
    }
}
