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
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread-safe singleton tracker for Zendesk rate limit states.
 * Collects rate limit metrics, coordinates shared cooldowns, and notifies {@link RateLimitListener}s.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.3
 */
@Singleton
public class RateLimitTracker {

    private static final Logger log = LoggerFactory.getLogger(RateLimitTracker.class);
    private static final Duration DEFAULT_STALE_WINDOW = Duration.ofSeconds(60);

    private record EndpointLimitEntry(EndpointRateLimit limit, Instant recordedAt) {}

    private final Clock clock;
    private final AtomicReference<RateLimitSnapshot> latestSnapshot = new AtomicReference<>();
    private final AtomicReference<Instant> retryAfterUntil = new AtomicReference<>();
    private final Map<String, EndpointLimitEntry> endpointLimits = new ConcurrentHashMap<>();
    private final List<RateLimitListener> listeners = new CopyOnWriteArrayList<>();

    public RateLimitTracker() {
        this(null, Clock.systemUTC());
    }

    @Inject
    public RateLimitTracker(@Nullable List<RateLimitListener> injectedListeners) {
        this(injectedListeners, Clock.systemUTC());
    }

    RateLimitTracker(Clock clock) {
        this(null, clock);
    }

    RateLimitTracker(@Nullable List<RateLimitListener> injectedListeners, Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
        if (injectedListeners != null) {
            this.listeners.addAll(injectedListeners);
        }
    }

    /**
     * Registers a rate limit listener.
     *
     * @param listener Listener to add
     * @return this tracker
     */
    public RateLimitTracker addListener(RateLimitListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
        return this;
    }

    /**
     * Unregisters a rate limit listener.
     *
     * @param listener Listener to remove
     * @return true if removed
     */
    public boolean removeListener(RateLimitListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Records a new rate limit snapshot, updates shared cooldown state, and notifies listeners.
     *
     * @param snapshot Snapshot from an HTTP response
     */
    public void recordSnapshot(RateLimitSnapshot snapshot) {
        if (snapshot == null) {
            return;
        }

        Instant recordedAt = snapshot.getTimestamp() != null ? snapshot.getTimestamp() : clock.instant();
        latestSnapshot.set(snapshot);

        if (snapshot.getEndpointLimits() != null) {
            snapshot.getEndpointLimits().forEach((name, limit) -> {
                if (name != null && limit != null) {
                    endpointLimits.put(name, new EndpointLimitEntry(limit, recordedAt));
                }
            });
        }

        if (snapshot.isRateLimited()) {
            Duration wait = snapshot.getEffectiveWaitDuration();
            if (!wait.isZero() && !wait.isNegative()) {
                recordRetryAfterUntil(recordedAt.plus(wait));
            }
        }

        listeners.forEach(listener -> {
            try {
                listener.onRateLimitUpdate(snapshot);
            } catch (Exception e) {
                log.error("Error in RateLimitListener: {}", e.getMessage(), e);
            }
        });
    }

    /**
     * Advances the shared 429 cooldown timestamp if {@code until} is later than any active cooldown.
     *
     * @param until Instant until which outbound requests should wait
     */
    public void recordRetryAfterUntil(@Nullable Instant until) {
        if (until == null) {
            return;
        }
        retryAfterUntil.updateAndGet(existing -> (existing == null || until.isAfter(existing)) ? until : existing);
    }

    /**
     * Advances the shared 429 cooldown timestamp by the given positive delay from now.
     *
     * @param delay Wait duration from the current time
     */
    public void recordRetryAfter(@Nullable Duration delay) {
        if (delay == null || delay.isZero() || delay.isNegative()) {
            return;
        }
        recordRetryAfterUntil(clock.instant().plus(delay));
    }

    /**
     * Returns the active shared 429 cooldown deadline, or null if none is active.
     *
     * @return Active retry-after deadline, or null
     */
    @Nullable
    public Instant getRetryAfterUntil() {
        Instant until = retryAfterUntil.get();
        if (until == null || !until.isAfter(clock.instant())) {
            return null;
        }
        return until;
    }

    /**
     * Returns the remaining wait time for an active shared 429 cooldown, or {@link Duration#ZERO}.
     *
     * @return Remaining retry-after duration
     */
    public Duration getRetryAfterDelay() {
        Instant until = retryAfterUntil.get();
        if (until == null) {
            return Duration.ZERO;
        }
        Duration remaining = Duration.between(clock.instant(), until);
        return remaining.isNegative() ? Duration.ZERO : remaining;
    }

    /**
     * @return The most recent rate limit snapshot, or null if no calls made yet
     */
    @Nullable
    public RateLimitSnapshot getLatestSnapshot() {
        return latestSnapshot.get();
    }

    /**
     * @return Unmodifiable map of all latest known endpoint-specific rate limits
     */
    public Map<String, EndpointRateLimit> getAllEndpointLimits() {
        return Collections.unmodifiableMap(endpointLimits.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().limit())));
    }

    /**
     * Retrieves the latest known limit for a specific endpoint (e.g. {@code "search-index"}).
     *
     * @param name Endpoint limit name
     * @return Optional containing the {@link EndpointRateLimit}
     */
    public Optional<EndpointRateLimit> getEndpointLimit(String name) {
        if (name == null) {
            return Optional.empty();
        }
        String clean = name.toLowerCase();
        if (clean.startsWith(EndpointRateLimit.PREFIX)) {
            clean = clean.substring(EndpointRateLimit.PREFIX.length());
        }
        EndpointLimitEntry entry = endpointLimits.get(clean);
        return Optional.ofNullable(entry != null ? entry.limit() : null);
    }

    /**
     * @return Latest global remaining request count, or null if unknown
     */
    @Nullable
    public Integer getGlobalRemaining() {
        RateLimitSnapshot snapshot = latestSnapshot.get();
        return snapshot != null ? snapshot.getGlobalRemaining() : null;
    }

    /**
     * Checks if any active (non-expired) global or endpoint remaining quota is at or below the threshold.
     *
     * @param threshold Warning threshold
     * @return true if approaching rate limit
     */
    public boolean isApproachingLimit(int threshold) {
        return isApproachingLimit(threshold, DEFAULT_STALE_WINDOW);
    }

    /**
     * Checks if any active (non-expired) global or endpoint remaining quota is at or below the threshold,
     * falling back to {@code fallbackWait} when no reset or retry header is recorded.
     *
     * @param threshold    Warning threshold
     * @param fallbackWait Default stale window when no reset or retry header is recorded
     * @return true if approaching rate limit
     */
    public boolean isApproachingLimit(int threshold, @Nullable Duration fallbackWait) {
        Instant now = clock.instant();
        Duration safeFallback = sanitizeFallback(fallbackWait);
        RateLimitSnapshot snapshot = latestSnapshot.get();
        if (isGlobalApproaching(snapshot, threshold, now, safeFallback)) {
            return true;
        }
        return endpointLimits.values().stream()
                .anyMatch(entry -> isEndpointApproaching(entry, threshold, now, safeFallback));
    }

    /**
     * Computes how long callers should pause when approaching the rate limit, using header reset times
     * when available and falling back to {@code fallbackWait} when no reset header is present.
     *
     * @param threshold    Warning threshold
     * @param fallbackWait Default wait duration when no reset or retry header is recorded
     * @return Wait duration, or {@link Duration#ZERO} if nothing is actively approaching
     */
    public Duration getApproachingLimitWaitDuration(int threshold, @Nullable Duration fallbackWait) {
        Instant now = clock.instant();
        Duration safeFallback = sanitizeFallback(fallbackWait);
        Duration maxWait = Duration.ZERO;

        RateLimitSnapshot snapshot = latestSnapshot.get();
        if (snapshot != null && snapshot.getGlobalRemaining() != null && snapshot.getGlobalRemaining() <= threshold) {
            Instant resetAt = resolveSnapshotResetAt(snapshot, safeFallback);
            Duration wait = Duration.between(now, resetAt);
            if (wait.compareTo(maxWait) > 0) {
                maxWait = wait;
            }
        }

        Duration endpointWait = endpointLimits.values().stream()
                .filter(entry -> entry.limit().getRemaining() != null && entry.limit().getRemaining() <= threshold)
                .map(entry -> Duration.between(now, resolveEndpointResetAt(entry, safeFallback)))
                .filter(wait -> !wait.isNegative())
                .max(Duration::compareTo)
                .orElse(Duration.ZERO);

        return endpointWait.compareTo(maxWait) > 0 ? endpointWait : maxWait;
    }

    private static Duration sanitizeFallback(@Nullable Duration fallbackWait) {
        return (fallbackWait == null || fallbackWait.isNegative()) ? Duration.ZERO : fallbackWait;
    }

    private boolean isGlobalApproaching(@Nullable RateLimitSnapshot snapshot, int threshold, Instant now, Duration fallbackWindow) {
        if (snapshot == null || snapshot.getGlobalRemaining() == null || snapshot.getGlobalRemaining() > threshold) {
            return false;
        }
        Instant resetAt = resolveSnapshotResetAt(snapshot, fallbackWindow);
        return now.isBefore(resetAt);
    }

    private boolean isEndpointApproaching(EndpointLimitEntry entry, int threshold, Instant now, Duration fallbackWindow) {
        EndpointRateLimit limit = entry.limit();
        if (limit.getRemaining() == null || limit.getRemaining() > threshold) {
            return false;
        }
        Instant resetAt = resolveEndpointResetAt(entry, fallbackWindow);
        return now.isBefore(resetAt);
    }

    private Instant resolveSnapshotResetAt(RateLimitSnapshot snapshot, Duration fallbackWindow) {
        Instant recordedAt = snapshot.getTimestamp() != null ? snapshot.getTimestamp() : clock.instant();
        if (snapshot.getRetryAfterSeconds() != null) {
            return recordedAt.plusSeconds(Math.max(0, snapshot.getRetryAfterSeconds()));
        }
        if (snapshot.getGlobalResetSeconds() != null) {
            return recordedAt.plusSeconds(Math.max(0, snapshot.getGlobalResetSeconds()));
        }
        return recordedAt.plus(fallbackWindow);
    }

    private Instant resolveEndpointResetAt(EndpointLimitEntry entry, Duration fallbackWindow) {
        Integer resets = entry.limit().getResets();
        if (resets != null) {
            return entry.recordedAt().plusSeconds(Math.max(0, resets));
        }
        return entry.recordedAt().plus(fallbackWindow);
    }
}
