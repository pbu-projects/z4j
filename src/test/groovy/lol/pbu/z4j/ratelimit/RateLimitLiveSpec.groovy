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

class RateLimitLiveSpec extends Z4jSpec {

    @Shared
    SearchClient searchClient

    @Shared
    RateLimitTracker tracker

    void setupSpec() {
        searchClient = adminCtx.getBean(SearchClient.class)
        tracker = adminCtx.getBean(RateLimitTracker.class)
    }

    def "Live sandbox API call updates RateLimitTracker with real headers"() {
        given: "a listener registered on the tracker"
        RateLimitSnapshot liveSnapshot = null
        RateLimitListener listener = { s -> liveSnapshot = s }
        tracker.addListener(listener)

        when: "making a live search count call against Zendesk sandbox"
        searchClient.count("type:ticket").block()

        then: "rate limit headers are intercepted and recorded"
        verifyAll {
            liveSnapshot != null
            liveSnapshot.statusCode == 200
            liveSnapshot.globalRemaining != null
            liveSnapshot.globalRemaining > 0
            liveSnapshot.globalLimit != null
            liveSnapshot.globalLimit > 0
            tracker.getLatestSnapshot() != null
            tracker.getGlobalRemaining() != null
        }

        cleanup:
        tracker.removeListener(listener)
    }
}
