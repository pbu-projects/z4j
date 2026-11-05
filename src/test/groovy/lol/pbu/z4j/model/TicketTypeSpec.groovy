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

class TicketTypeSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    @Unroll
    def "#type toString() and getValue() should return #expectedString"() {
        expect:
        verifyAll {
            type.toString() == expectedString
            type.getValue() == expectedString
        }

        where:
        type                | expectedString
        TicketType.PROBLEM  | "problem"
        TicketType.INCIDENT | "incident"
        TicketType.QUESTION | "question"
        TicketType.TASK     | "task"
    }

    @Unroll
    def "fromValue(#stringValue) and JSON round-trip should return #expectedType"() {
        when:
        String json = objectMapper.writeValueAsString(expectedType)
        TicketType deserialized = objectMapper.readValue(json, TicketType.class)

        then:
        verifyAll {
            TicketType.fromValue(stringValue) == expectedType
            json == "\"${stringValue}\""
            deserialized == expectedType
        }

        where:
        stringValue | expectedType
        "problem"   | TicketType.PROBLEM
        "incident"  | TicketType.INCIDENT
        "question"  | TicketType.QUESTION
        "task"      | TicketType.TASK
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TicketType.fromValue("invalid_type")

        then:
        thrown(IllegalArgumentException)
    }
}

