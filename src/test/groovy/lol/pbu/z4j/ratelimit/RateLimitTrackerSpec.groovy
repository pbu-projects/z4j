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
package lol.pbu.z4j.ratelimit

import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import spock.lang.Specification

class RateLimitTrackerSpec extends Specification {

    def "does not retain unbounded per-path snapshots"() {
        given:
        def tracker = new RateLimitTracker()

        expect:
        !RateLimitTracker.declaredFields.any { it.name == "pathSnapshots" }

        when:
        (1..500).each { idx ->
            def snapshot = RateLimitSnapshot.builder()
                    .requestMethod("GET")
                    .requestPath("/api/v2/tickets/${idx}")
                    .statusCode(200)
                    .globalRemaining(700 - (idx % 100))
                    .endpointLimits(["tickets-show": EndpointRateLimit.builder().name("tickets-show").remaining(100L).build()])
                    .build()
            tracker.recordSnapshot(snapshot)
        }

        then:
        verifyAll {
            tracker.latestSnapshot != null
            tracker.latestSnapshot.requestPath == "/api/v2/tickets/500"
            tracker.allEndpointLimits.size() == 1
            tracker.getEndpointLimit("tickets-show").isPresent()
        }
    }

    def "coordinates shared retryAfterUntil monotonically and clears after deadline"() {
        given:
        def clock = new MutableClock(Instant.parse("2026-01-01T00:00:00Z"))
        def tracker = new RateLimitTracker(clock)

        when: "recording a 10s cooldown then a shorter 3s cooldown"
        tracker.recordRetryAfter(Duration.ofSeconds(10))
        tracker.recordRetryAfter(Duration.ofSeconds(3))

        then: "the longer 10s deadline wins"
        verifyAll {
            tracker.retryAfterUntil == Instant.parse("2026-01-01T00:00:10Z")
            tracker.retryAfterDelay == Duration.ofSeconds(10)
        }

        when: "recording a longer 15s cooldown"
        tracker.recordRetryAfterUntil(Instant.parse("2026-01-01T00:00:15Z"))

        then: "the deadline advances to 15s"
        verifyAll {
            tracker.retryAfterUntil == Instant.parse("2026-01-01T00:00:15Z")
            tracker.retryAfterDelay == Duration.ofSeconds(15)
        }

        when: "time advances past the deadline"
        clock.advance(Duration.ofSeconds(16))

        then: "cooldown is no longer active"
        verifyAll {
            tracker.retryAfterUntil == null
            tracker.retryAfterDelay == Duration.ZERO
        }
    }

    def "recordSnapshot on HTTP 429 populates shared retryAfterUntil"() {
        given:
        def start = Instant.parse("2026-01-01T00:00:00Z")
        def clock = new MutableClock(start)
        def tracker = new RateLimitTracker(clock)

        when:
        tracker.recordSnapshot(RateLimitSnapshot.builder()
                .statusCode(429)
                .retryAfterSeconds(12)
                .timestamp(start)
                .build())

        then:
        verifyAll {
            tracker.retryAfterUntil == start.plusSeconds(12)
            tracker.retryAfterDelay == Duration.ofSeconds(12)
        }
    }

    def "isApproachingLimit expires after globalResetSeconds and endpoint resets"() {
        given:
        def start = Instant.parse("2026-01-01T00:00:00Z")
        def clock = new MutableClock(start)
        def tracker = new RateLimitTracker(clock)

        when: "global quota is low with a 4s reset window"
        tracker.recordSnapshot(RateLimitSnapshot.builder()
                .statusCode(200)
                .globalRemaining(0)
                .globalResetSeconds(4)
                .timestamp(start)
                .build())

        then:
        verifyAll {
            tracker.isApproachingLimit(1)
            tracker.getApproachingLimitWaitDuration(1, Duration.ofSeconds(60)) == Duration.ofSeconds(4)
        }

        when: "time advances past the 4s reset window"
        clock.advance(Duration.ofSeconds(5))

        then: "global approaching state is no longer stale"
        verifyAll {
            !tracker.isApproachingLimit(1)
            tracker.getApproachingLimitWaitDuration(1, Duration.ofSeconds(60)) == Duration.ZERO
        }

        when: "an endpoint limit is low with a 3s reset window"
        def t2 = clock.instant()
        tracker.recordSnapshot(RateLimitSnapshot.builder()
                .statusCode(200)
                .globalRemaining(500)
                .endpointLimits([
                        "search-index": EndpointRateLimit.builder()
                                .name("search-index")
                                .remaining(1L)
                                .resets(3)
                                .build()
                ])
                .timestamp(t2)
                .build())

        then:
        verifyAll {
            tracker.isApproachingLimit(1)
            tracker.getApproachingLimitWaitDuration(1, Duration.ofSeconds(60)) == Duration.ofSeconds(3)
        }

        when: "time advances past the 3s endpoint reset window"
        clock.advance(Duration.ofSeconds(4))

        then: "endpoint approaching state clears instead of staying stale indefinitely"
        verifyAll {
            !tracker.isApproachingLimit(1)
            tracker.getApproachingLimitWaitDuration(1, Duration.ofSeconds(60)) == Duration.ZERO
        }
    }

    def "isApproachingLimit expires after default window when no reset header is present"() {
        given:
        def start = Instant.parse("2026-01-01T00:00:00Z")
        def clock = new MutableClock(start)
        def tracker = new RateLimitTracker(clock)

        when:
        tracker.recordSnapshot(RateLimitSnapshot.builder()
                .statusCode(200)
                .globalRemaining(0)
                .timestamp(start)
                .build())

        then:
        verifyAll {
            tracker.isApproachingLimit(1)
            tracker.getApproachingLimitWaitDuration(1, Duration.ofSeconds(30)) == Duration.ofSeconds(30)
        }

        when: "61 seconds pass with no further updates"
        clock.advance(Duration.ofSeconds(61))

        then:
        !tracker.isApproachingLimit(1)
    }

    def "isApproachingLimit aligns stale window with fallbackWait"() {
        given:
        def start = Instant.parse("2026-01-01T00:00:00Z")
        def clock = new MutableClock(start)
        def tracker = new RateLimitTracker(clock)
        def fallback = Duration.ofSeconds(10)

        when: "snapshot has no reset header"
        tracker.recordSnapshot(RateLimitSnapshot.builder()
                .statusCode(200)
                .globalRemaining(0)
                .timestamp(start)
                .build())

        then:
        verifyAll {
            tracker.isApproachingLimit(1, fallback)
            tracker.getApproachingLimitWaitDuration(1, fallback) == Duration.ofSeconds(10)
        }

        when: "time advances past the 10s fallback wait"
        clock.advance(Duration.ofSeconds(11))

        then: "both methods agree the snapshot is stale"
        verifyAll {
            !tracker.isApproachingLimit(1, fallback)
            tracker.getApproachingLimitWaitDuration(1, fallback) == Duration.ZERO
        }
    }

    static class MutableClock extends Clock {
        private Instant current

        MutableClock(Instant initial) {
            this.current = initial
        }

        void advance(Duration delta) {
            this.current = this.current.plus(delta)
        }

        @Override
        ZoneOffset getZone() {
            return ZoneOffset.UTC
        }

        @Override
        Clock withZone(ZoneId zone) {
            return this
        }

        @Override
        Instant instant() {
            return current
        }
    }
}
