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
package lol.pbu.z4j.model

import spock.lang.Specification

class IncrementalTimePaginationResponseSpec extends Specification {

    static record SampleTimeResponse(
            List<String> results,
            Long endTime,
            String nextPage,
            Boolean endOfStream,
            Integer count
    ) implements IncrementalTimePaginationResponse<String> {}

    def "time pagination response interface methods work as expected"() {
        when:
        def response = new SampleTimeResponse(
                ["time_item1"],
                123456789L,
                "https://example.zendesk.com/api/v2/incremental/orgs.json?start_time=123456789",
                true,
                5
        )

        then:
        verifyAll(response) {
            endTime() == 123456789L
            nextPage() == "https://example.zendesk.com/api/v2/incremental/orgs.json?start_time=123456789"
            endOfStream() == true
            count() == 5
            results() == ["time_item1"]
        }
    }
}
