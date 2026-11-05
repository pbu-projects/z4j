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

class IncrementalCursorPaginationResponseSpec extends Specification {

    static record SampleCursorResponse(
            List<String> results,
            String cursor,
            String afterCursor,
            String beforeCursor,
            Boolean endOfStream,
            Integer count
    ) implements IncrementalCursorPaginationResponse<String> {}

    def "cursor pagination response interface methods work as expected"() {
        when:
        def response = new SampleCursorResponse(["item1", "item2"], "cur1", "after1", "before1", true, 10)

        then:
        verifyAll(response) {
            cursor() == "cur1"
            afterCursor() == "after1"
            beforeCursor() == "before1"
            endOfStream() == true
            count() == 10
            results() == ["item1", "item2"]
        }
    }
}
