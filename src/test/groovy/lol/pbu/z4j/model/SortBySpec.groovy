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

import io.micronaut.serde.ObjectMapper
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

class SortBySpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    @Unroll
    def "#sortBy toString() and getValue() should return #expectedString"() {
        expect:
        verifyAll {
            sortBy.toString() == expectedString
            sortBy.getValue() == expectedString
        }

        where:
        sortBy             | expectedString
        SortBy.UPDATED_AT  | "updated_at"
        SortBy.CREATED_AT  | "created_at"
        SortBy.PRIORITY    | "priority"
        SortBy.STATUS      | "status"
        SortBy.TICKET_TYPE | "ticket_type"
    }

    @Unroll
    def "fromValue(#stringValue) and JSON round-trip should return #expectedSortBy"() {
        when:
        String json = objectMapper.writeValueAsString(expectedSortBy)
        SortBy deserialized = objectMapper.readValue(json, SortBy.class)

        then:
        verifyAll {
            SortBy.fromValue(stringValue) == expectedSortBy
            json == "\"${stringValue}\""
            deserialized == expectedSortBy
        }

        where:
        stringValue   | expectedSortBy
        "updated_at"  | SortBy.UPDATED_AT
        "created_at"  | SortBy.CREATED_AT
        "priority"    | SortBy.PRIORITY
        "status"      | SortBy.STATUS
        "ticket_type" | SortBy.TICKET_TYPE
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        SortBy.fromValue("invalid_sort")

        then:
        thrown(IllegalArgumentException)
    }
}
