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
}
