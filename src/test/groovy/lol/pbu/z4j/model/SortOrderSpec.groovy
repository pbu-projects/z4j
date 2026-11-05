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

class SortOrderSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    @Unroll
    def "#sortOrder toString() and getValue() should return #expectedString"() {
        expect:
        verifyAll {
            sortOrder.toString() == expectedString
            sortOrder.getValue() == expectedString
        }

        where:
        sortOrder            | expectedString
        SortOrder.ASCENDING  | "asc"
        SortOrder.DESCENDING | "desc"
    }

    @Unroll
    def "fromValue(#stringValue) and JSON round-trip should return #expectedSortOrder"() {
        when:
        String json = objectMapper.writeValueAsString(expectedSortOrder)
        SortOrder deserialized = objectMapper.readValue(json, SortOrder.class)

        then:
        verifyAll {
            SortOrder.fromValue(stringValue) == expectedSortOrder
            json == "\"${stringValue}\""
            deserialized == expectedSortOrder
        }

        where:
        stringValue | expectedSortOrder
        "asc"       | SortOrder.ASCENDING
        "desc"      | SortOrder.DESCENDING
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        SortOrder.fromValue("invalid_order")

        then:
        thrown(IllegalArgumentException)
    }
}
