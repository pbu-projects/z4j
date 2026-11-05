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

class LocaleAbbreviationSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    @Unroll
    def "#locale getValue(), toString(), fromValue(), and JSON round-trip should match"() {
        when:
        String expectedValue = locale.getValue()
        String json = objectMapper.writeValueAsString(locale)
        LocaleAbbreviation deserialized = objectMapper.readValue(json, LocaleAbbreviation.class)

        then:
        verifyAll {
            expectedValue != null
            !expectedValue.isEmpty()
            locale.toString() == expectedValue
            LocaleAbbreviation.fromValue(expectedValue) == locale
            LocaleAbbreviation.fromValue(expectedValue.toUpperCase()) == locale
            json == "\"${expectedValue}\""
            deserialized == locale
        }

        where:
        locale << LocaleAbbreviation.values()
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        LocaleAbbreviation.fromValue("invalid_locale")

        then:
        thrown(IllegalArgumentException)
    }
}
