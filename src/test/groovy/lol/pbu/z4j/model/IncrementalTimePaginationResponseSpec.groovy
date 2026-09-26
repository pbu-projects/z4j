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

    static class SampleTimeResponse extends IncrementalTimePaginationResponse<String> {
        List<String> items = []

        @Override
        List<String> getResults() {
            return items
        }
    }

    def "time pagination response properties work as expected"() {
        given:
        def response = new SampleTimeResponse()
        response.items = ["time_item1"]

        when:
        response.setEndTime(123456789L)
                .setNextPage("https://example.zendesk.com/api/v2/incremental/orgs.json?start_time=123456789")
                .setEndOfStream(true)
                .setCount(5)

        then:
        response.getEndTime() == 123456789L
        response.getNextPage() == "https://example.zendesk.com/api/v2/incremental/orgs.json?start_time=123456789"
        response.getEndOfStream() == true
        response.getCount() == 5
        response.getResults() == ["time_item1"]
    }
}
