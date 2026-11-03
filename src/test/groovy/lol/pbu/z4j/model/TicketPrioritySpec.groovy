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

class TicketPrioritySpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    @Unroll
    def "#priority toString() and getValue() should return #expectedString"() {
        expect:
        verifyAll {
            priority.toString() == expectedString
            priority.getValue() == expectedString
        }

        where:
        priority              | expectedString
        TicketPriority.URGENT | "urgent"
        TicketPriority.HIGH   | "high"
        TicketPriority.NORMAL | "normal"
        TicketPriority.LOW    | "low"
    }

    @Unroll
    def "fromValue(#stringValue) and JSON round-trip should return #expectedPriority"() {
        when:
        String json = objectMapper.writeValueAsString(expectedPriority)
        TicketPriority deserialized = objectMapper.readValue(json, TicketPriority.class)

        then:
        verifyAll {
            TicketPriority.fromValue(stringValue) == expectedPriority
            json == "\"${stringValue}\""
            deserialized == expectedPriority
        }

        where:
        stringValue | expectedPriority
        "urgent"    | TicketPriority.URGENT
        "high"      | TicketPriority.HIGH
        "normal"    | TicketPriority.NORMAL
        "low"       | TicketPriority.LOW
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TicketPriority.fromValue("invalid_priority")

        then:
        thrown(IllegalArgumentException)
    }
}

