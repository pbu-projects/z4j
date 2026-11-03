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

class EmailCCAllOfActionSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    @Unroll
    def "#action toString() and getValue() should return #expectedString"() {
        expect:
        verifyAll {
            action.toString() == expectedString
            action.getValue() == expectedString
        }

        where:
        action                    | expectedString
        EmailCCAllOfAction.PUT    | "put"
        EmailCCAllOfAction.DELETE | "delete"
    }

    @Unroll
    def "fromValue(#stringValue) and JSON round-trip should return #expectedAction"() {
        when:
        String json = objectMapper.writeValueAsString(expectedAction)
        EmailCCAllOfAction deserialized = objectMapper.readValue(json, EmailCCAllOfAction.class)

        then:
        verifyAll {
            EmailCCAllOfAction.fromValue(stringValue) == expectedAction
            json == "\"${stringValue}\""
            deserialized == expectedAction
        }

        where:
        stringValue | expectedAction
        "put"       | EmailCCAllOfAction.PUT
        "delete"    | EmailCCAllOfAction.DELETE
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        EmailCCAllOfAction.fromValue("invalid_action")

        then:
        thrown(IllegalArgumentException)
    }
}

