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

class TicketFieldCustomStatusObjectStatusCategorySpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    @Unroll
    def "#category toString() and getValue() should return #expectedString"() {
        expect:
        verifyAll {
            category.toString() == expectedString
            category.getValue() == expectedString
        }

        where:
        category                                            | expectedString
        TicketFieldCustomStatusObjectStatusCategory.NEW     | "new"
        TicketFieldCustomStatusObjectStatusCategory.OPEN    | "open"
        TicketFieldCustomStatusObjectStatusCategory.PENDING | "pending"
        TicketFieldCustomStatusObjectStatusCategory.HOLD    | "hold"
        TicketFieldCustomStatusObjectStatusCategory.SOLVED  | "solved"
    }

    @Unroll
    def "fromValue(#stringValue) and JSON round-trip should return #expectedCategory"() {
        when:
        String json = objectMapper.writeValueAsString(expectedCategory)
        TicketFieldCustomStatusObjectStatusCategory deserialized = objectMapper.readValue(json, TicketFieldCustomStatusObjectStatusCategory.class)

        then:
        verifyAll {
            TicketFieldCustomStatusObjectStatusCategory.fromValue(stringValue) == expectedCategory
            json == "\"${stringValue}\""
            deserialized == expectedCategory
        }

        where:
        stringValue | expectedCategory
        "new"       | TicketFieldCustomStatusObjectStatusCategory.NEW
        "open"      | TicketFieldCustomStatusObjectStatusCategory.OPEN
        "pending"   | TicketFieldCustomStatusObjectStatusCategory.PENDING
        "hold"      | TicketFieldCustomStatusObjectStatusCategory.HOLD
        "solved"    | TicketFieldCustomStatusObjectStatusCategory.SOLVED
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TicketFieldCustomStatusObjectStatusCategory.fromValue("invalid_category")

        then:
        thrown(IllegalArgumentException)
    }
}
