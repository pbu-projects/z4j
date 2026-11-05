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

import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.client.SearchClient
import spock.lang.Shared

import java.time.Duration
import java.time.Instant

class RateLimitSpec extends Z4jSpec {

    @Shared
    SearchClient searchClient

    @Shared
    RateLimitTracker tracker

    void setupSpec() {
        searchClient = adminCtx.getBean(SearchClient.class)
        tracker = adminCtx.getBean(RateLimitTracker.class)
    }

    def "EndpointRateLimit can parse Zendesk rate limit header correctly"() {
        when: "parsing a search index rate limit header"
        def limit = EndpointRateLimit.parse("zendesk-ratelimit-search-index", "total=2500; remaining=2499; resets=30")

        then:
        limit != null
        limit.name == "search-index"
        limit.total == 2500L
        limit.remaining == 2499L
        limit.resets == 30
    }

    def "EndpointRateLimit handles header with tickets-index"() {
        when: "parsing tickets-index header"
        def limit = EndpointRateLimit.parse("zendesk-ratelimit-tickets-index", "total=100000; remaining=99999; resets=35")

        then:
        limit != null
        limit.name == "tickets-index"
        limit.total == 100000L
        limit.remaining == 99999L
        limit.resets == 35
    }

    def "RateLimitSnapshot evaluates rate limiting thresholds correctly"() {
        given: "a normal snapshot"
        def normal = RateLimitSnapshot.builder()
                .statusCode(200)
                .globalLimit(700)
                .globalRemaining(650)
                .globalResetSeconds(45)
                .endpointLimits(["search-index": EndpointRateLimit.builder().name("search-index").remaining(2400L).build()])
                .timestamp(Instant.now())
                .build()

        and: "a snapshot near the rate limit"
        def nearLimit = RateLimitSnapshot.builder()
                .statusCode(200)
                .globalLimit(700)
                .globalRemaining(10)
                .globalResetSeconds(15)
                .endpointLimits(["search-index": EndpointRateLimit.builder().name("search-index").remaining(5L).build()])
                .timestamp(Instant.now())
                .build()

        and: "a 429 throttled snapshot"
        def throttled = RateLimitSnapshot.builder()
                .statusCode(429)
                .retryAfterSeconds(60)
                .timestamp(Instant.now())
                .build()

        expect:
        !normal.isRateLimited()
        !normal.isApproachingLimit(50)
        normal.getEndpointLimit("search-index").isPresent()
        normal.getEndpointLimit("zendesk-ratelimit-search-index").isPresent()

        !nearLimit.isRateLimited()
        nearLimit.isApproachingLimit(50)

        throttled.isRateLimited()
        throttled.retryAfterSeconds == 60
    }

    def "RateLimitTracker notifies registered listeners upon receiving snapshots"() {
        given: "a custom listener"
        RateLimitSnapshot captured = null
        RateLimitListener listener = { s -> captured = s }
        tracker.addListener(listener)

        when: "recording a snapshot"
        def snapshot = RateLimitSnapshot.builder()
                .requestMethod("GET")
                .requestPath("/api/v2/search")
                .statusCode(200)
                .globalLimit(700)
                .globalRemaining(690)
                .globalResetSeconds(25)
                .build()
        tracker.recordSnapshot(snapshot)

        then: "the listener received the snapshot"
        captured != null
        captured.globalRemaining == 690
        tracker.getLatestSnapshot() == snapshot
        tracker.getGlobalRemaining() == 690

        cleanup:
        tracker.removeListener(listener)
    }

    def "Live sandbox API call updates RateLimitTracker with real headers"() {
        given: "a listener registered on the tracker"
        RateLimitSnapshot liveSnapshot = null
        RateLimitListener listener = { s -> liveSnapshot = s }
        tracker.addListener(listener)

        when: "making a live search count call against Zendesk sandbox"
        searchClient.count("type:ticket").block()

        then: "rate limit headers are intercepted and recorded"
        liveSnapshot != null
        liveSnapshot.statusCode == 200
        liveSnapshot.globalRemaining != null
        liveSnapshot.globalRemaining > 0
        liveSnapshot.globalLimit != null
        liveSnapshot.globalLimit > 0

        and: "tracker state is updated"
        tracker.getLatestSnapshot() != null
        tracker.getGlobalRemaining() != null

        cleanup:
        tracker.removeListener(listener)
    }

    def "RateLimitTracker handles edge cases and null inputs cleanly"() {
        given: "a new isolated tracker"
        def localTracker = new RateLimitTracker()

        expect: "initial state is empty"
        localTracker.getLatestSnapshot() == null
        localTracker.getGlobalRemaining() == null
        localTracker.getAllEndpointLimits().isEmpty()
        !localTracker.getEndpointLimit("search-index").isPresent()
        !localTracker.getEndpointLimit(null).isPresent()

        when: "recording null"
        localTracker.recordSnapshot(null)

        then: "state remains null"
        localTracker.getLatestSnapshot() == null

        when: "recording a snapshot with an endpoint limit"
        def limit = EndpointRateLimit.builder().name("search-index").remaining(10L).build()
        def snapshot = RateLimitSnapshot.builder()
                .requestMethod("GET")
                .requestPath("/api/v2/tickets")
                .globalRemaining(20)
                .endpointLimits(["search-index": limit])
                .build()
        localTracker.recordSnapshot(snapshot)

        then:
        localTracker.getLatestSnapshot() == snapshot
        localTracker.getGlobalRemaining() == 20
        localTracker.getAllEndpointLimits().containsKey("search-index")
        localTracker.getEndpointLimit("search-index").isPresent()
        localTracker.getEndpointLimit("zendesk-ratelimit-search-index").isPresent()
        localTracker.isApproachingLimit(50)

        when: "a listener throws an exception"
        RateLimitListener badListener = { s -> throw new RuntimeException("boom") }
        localTracker.addListener(badListener)
        localTracker.recordSnapshot(snapshot)

        then: "no exception escapes"
        noExceptionThrown()

        when: "removing listener"
        localTracker.removeListener(badListener)
        localTracker.removeListener(null)

        then:
        noExceptionThrown()
    }

    def "RateLimitTracker constructor with injected list initializes listeners"() {
        given:
        RateLimitSnapshot captured = null
        RateLimitListener l = { s -> captured = s }
        def trackerWithList = new RateLimitTracker([l])
        def trackerWithNull = new RateLimitTracker(null)

        when:
        def snapshot = RateLimitSnapshot.builder().globalRemaining(100).build()
        trackerWithList.recordSnapshot(snapshot)
        trackerWithNull.recordSnapshot(snapshot)

        then:
        captured == snapshot
        trackerWithNull.getLatestSnapshot() == snapshot
    }

    def "EndpointRateLimit handles edge cases in parsing"() {
        expect:
        EndpointRateLimit.parse(null, "total=100") == null
        EndpointRateLimit.parse("test", null).name == "test"
        EndpointRateLimit.parse("test", "").name == "test"
        EndpointRateLimit.parse("test", "total=not_a_number; remaining=abc; foo=bar").total == null
        EndpointRateLimit.parse("zendesk-ratelimit-foo", "key_without_value; =; total=50").total == 50L
    }

    def "RateLimitFilter handles null response and 429 status"() {
        given:
        def localTracker = new RateLimitTracker()
        def filter = new lol.pbu.z4j.client.RateLimitFilter(localTracker, new lol.pbu.z4j.ratelimit.RateLimitConfiguration())

        when: "handling null response"
        filter.handleResponse(null, null)

        then:
        localTracker.getLatestSnapshot() == null

        when: "handling 429 response"
        def req = io.micronaut.http.HttpRequest.GET("/api/v2/search")
        def resp = io.micronaut.http.HttpResponse.status(io.micronaut.http.HttpStatus.TOO_MANY_REQUESTS)
                .header("Retry-After", "30")
                .header("X-Rate-Limit-Remaining", "0")
        filter.handleResponse(req, resp)

        then:
        localTracker.getLatestSnapshot() != null
        localTracker.getLatestSnapshot().isRateLimited()
        localTracker.getLatestSnapshot().retryAfterSeconds == 30
    }

    def "RateLimitSnapshot calculates effective wait duration and approaching rate limit alias"() {
        given: "a snapshot with retry-after"
        def retrySnapshot = RateLimitSnapshot.builder()
                .statusCode(429)
                .retryAfterSeconds(30)
                .build()

        and: "a snapshot with global reset seconds"
        def resetSnapshot = RateLimitSnapshot.builder()
                .statusCode(200)
                .globalResetSeconds(45)
                .globalRemaining(5)
                .build()

        and: "a snapshot with neither"
        def emptySnapshot = new RateLimitSnapshot()

        expect:
        verifyAll {
            retrySnapshot.getEffectiveWaitDuration() == Duration.ofSeconds(30)
            resetSnapshot.getEffectiveWaitDuration() == Duration.ofSeconds(45)
            resetSnapshot.isApproachingRateLimit(10)
            !resetSnapshot.isApproachingRateLimit(2)
            emptySnapshot.getEffectiveWaitDuration() == Duration.ZERO
            !emptySnapshot.isRateLimited()
        }
    }

    def "RateLimitConfiguration retains default values and allows property updates"() {
        given:
        def config = new RateLimitConfiguration()

        expect:
        verifyAll(config) {
            autoWaitEnabled
            waitDurationSeconds == 60L
            approachThreshold == 1
            requestsPerSecond == 0.0d
            maxTransientRetries == 0
            transientRetryDelaySeconds == 1L
        }

        when:
        config.setAutoWaitEnabled(false)
        config.setWaitDurationSeconds(120L)
        config.setApproachThreshold(5)
        config.setRequestsPerSecond(10.0d)
        config.setMaxTransientRetries(3)
        config.setTransientRetryDelaySeconds(2L)

        then:
        verifyAll(config) {
            !autoWaitEnabled
            waitDurationSeconds == 120L
            approachThreshold == 5
            requestsPerSecond == 10.0d
            maxTransientRetries == 3
            transientRetryDelaySeconds == 2L
        }
    }
}
