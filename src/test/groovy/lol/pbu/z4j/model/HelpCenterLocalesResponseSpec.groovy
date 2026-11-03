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

class HelpCenterLocalesResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create HelpCenterLocalesResponse with canonical constructor and builder"() {
        given:
        List<LocaleAbbreviation> expectedLocales = [LocaleAbbreviation.ENGLISH_UNITED_STATES, LocaleAbbreviation.FRENCH]
        LocaleAbbreviation expectedDefault = LocaleAbbreviation.ENGLISH_UNITED_STATES

        when:
        HelpCenterLocalesResponse direct = new HelpCenterLocalesResponse(expectedLocales, expectedDefault)
        HelpCenterLocalesResponse built = HelpCenterLocalesResponseBuilder.builder()
                .locales(expectedLocales)
                .defaultLocale(expectedDefault)
                .build()

        then:
        verifyAll(direct) {
            locales() == expectedLocales
            defaultLocale() == expectedDefault
        }
        verifyAll(built) {
            it == direct
            locales() == expectedLocales
            defaultLocale() == expectedDefault
        }
    }

    def "should update HelpCenterLocalesResponse using record withers and addLocalesItem method"() {
        given:
        LocaleAbbreviation enUs = LocaleAbbreviation.ENGLISH_UNITED_STATES
        LocaleAbbreviation fr = LocaleAbbreviation.FRENCH
        HelpCenterLocalesResponse response = new HelpCenterLocalesResponse([enUs], enUs)

        when:
        HelpCenterLocalesResponse updated = response.withLocales([fr]).withDefaultLocale(fr)
        HelpCenterLocalesResponse withAdded = response.addLocalesItem(fr)
        HelpCenterLocalesResponse fromNull = new HelpCenterLocalesResponse(null, enUs).addLocalesItem(enUs)

        then:
        verifyAll(response) {
            locales() == [enUs]
            defaultLocale() == enUs
        }
        verifyAll(updated) {
            locales() == [fr]
            defaultLocale() == fr
        }
        verifyAll(withAdded) {
            locales() == [enUs, fr]
            defaultLocale() == enUs
        }
        verifyAll(fromNull) {
            locales() == [enUs]
            defaultLocale() == enUs
        }
    }

    def "should round-trip serialize and deserialize HelpCenterLocalesResponse"() {
        given:
        HelpCenterLocalesResponse response = new HelpCenterLocalesResponse(
                [LocaleAbbreviation.ENGLISH_UNITED_STATES, LocaleAbbreviation.SPANISH],
                LocaleAbbreviation.ENGLISH_UNITED_STATES
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        HelpCenterLocalesResponse deserialized = objectMapper.readValue(json, HelpCenterLocalesResponse.class)

        then:
        verifyAll(map) {
            containsKey("locales")
            containsKey("default_locale")
            get("default_locale") == "en-us"
        }
        verifyAll(deserialized) {
            it == response
            locales() == [LocaleAbbreviation.ENGLISH_UNITED_STATES, LocaleAbbreviation.SPANISH]
            defaultLocale() == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
    }

    def "should handle null components in HelpCenterLocalesResponse"() {
        when:
        HelpCenterLocalesResponse empty = new HelpCenterLocalesResponse(null, null)
        String json = objectMapper.writeValueAsString(empty)
        HelpCenterLocalesResponse deserialized = objectMapper.readValue(json, HelpCenterLocalesResponse.class)

        then:
        verifyAll(empty) {
            locales() == null
            defaultLocale() == null
        }
        verifyAll(deserialized) {
            it == empty
            locales() == null
            defaultLocale() == null
        }
    }
}
