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

class MissingTranslationsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create MissingTranslationsResponse with canonical constructor and builder"() {
        given:
        List<LocaleAbbreviation> list = [LocaleAbbreviation.FRENCH]

        when:
        MissingTranslationsResponse direct = new MissingTranslationsResponse(list)
        MissingTranslationsResponse built = MissingTranslationsResponseBuilder.builder()
                .locales(list)
                .build()

        then:
        verifyAll(direct) {
            locales() == list
            locales().size() == 1
            locales().first() == LocaleAbbreviation.FRENCH
        }
        verifyAll(built) {
            locales() == list
            locales().size() == 1
            locales().first() == LocaleAbbreviation.FRENCH
        }
    }

    def "should update locales using record wither and addLocalesItem method"() {
        given:
        LocaleAbbreviation first = LocaleAbbreviation.ENGLISH_UNITED_STATES
        LocaleAbbreviation second = LocaleAbbreviation.SPANISH
        MissingTranslationsResponse response = new MissingTranslationsResponse([first])

        when:
        MissingTranslationsResponse withNewList = response.withLocales([second])
        MissingTranslationsResponse withAdded = response.addLocalesItem(second)
        MissingTranslationsResponse fromNull = new MissingTranslationsResponse(null).addLocalesItem(first)

        then:
        verifyAll(response) {
            locales().size() == 1
            locales().first() == first
        }
        verifyAll(withNewList) {
            locales().size() == 1
            locales().first() == second
        }
        verifyAll(withAdded) {
            locales().size() == 2
            locales().first() == first
            locales().last() == second
        }
        verifyAll(fromNull) {
            locales().size() == 1
            locales().first() == first
        }
    }

    def "should round-trip serialize and deserialize MissingTranslationsResponse"() {
        given:
        MissingTranslationsResponse response = new MissingTranslationsResponse([LocaleAbbreviation.GERMAN])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        MissingTranslationsResponse deserialized = objectMapper.readValue(json, MissingTranslationsResponse.class)

        then:
        verifyAll(map) {
            containsKey("locales")
            get("locales") instanceof List
            ((List) get("locales")).size() == 1
            ((List) get("locales")).first() == "de"
        }
        verifyAll(deserialized) {
            it == response
            locales() != null
            locales().size() == 1
            locales().first() == LocaleAbbreviation.GERMAN
        }
    }

    def "should handle null locales component in constructor, wither, and serde"() {
        when:
        MissingTranslationsResponse response = new MissingTranslationsResponse(null)
        MissingTranslationsResponse updated = response.withLocales([LocaleAbbreviation.ITALIAN])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        MissingTranslationsResponse deserialized = objectMapper.readValue(json, MissingTranslationsResponse.class)

        then:
        verifyAll(response) {
            locales() == null
        }
        verifyAll(updated) {
            locales() != null
            locales().size() == 1
            locales().first() == LocaleAbbreviation.ITALIAN
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("locales")
        }
        verifyAll(deserialized) {
            it == response
            locales() == null
        }
    }
}
