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

class LocalesResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create LocalesResponse with canonical constructor and builder"() {
        given:
        Locale item = LocaleBuilder.builder().id(101L).name("English").localeAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES).build()
        List<Locale> list = [item]

        when:
        LocalesResponse direct = new LocalesResponse(list)
        LocalesResponse built = LocalesResponseBuilder.builder()
                .locales(list)
                .build()

        then:
        verifyAll(direct) {
            locales() == list
            locales().size() == 1
            locales().first().id == 101L
            locales().first().name == "English"
        }
        verifyAll(built) {
            locales() == list
            locales().size() == 1
            locales().first().id == 101L
            locales().first().name == "English"
        }
    }

    def "should update locales using record wither and addLocalesItem method"() {
        given:
        Locale first = LocaleBuilder.builder().id(1L).name("English").build()
        Locale second = LocaleBuilder.builder().id(2L).name("Spanish").build()
        LocalesResponse response = new LocalesResponse([first])

        when:
        LocalesResponse withNewList = response.withLocales([second])
        LocalesResponse withAdded = response.addLocalesItem(second)
        LocalesResponse fromNull = new LocalesResponse(null).addLocalesItem(first)

        then:
        verifyAll(response) {
            locales().size() == 1
            locales().first().id == 1L
        }
        verifyAll(withNewList) {
            locales().size() == 1
            locales().first().id == 2L
        }
        verifyAll(withAdded) {
            locales().size() == 2
            locales().first().id == 1L
            locales().last().id == 2L
        }
        verifyAll(fromNull) {
            locales().size() == 1
            locales().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize LocalesResponse"() {
        given:
        Locale item = LocaleBuilder.builder().id(555L).name("French").localeAbbreviation(LocaleAbbreviation.FRENCH).build()
        LocalesResponse response = new LocalesResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        LocalesResponse deserialized = objectMapper.readValue(json, LocalesResponse.class)

        then:
        verifyAll(map) {
            containsKey("locales")
            get("locales") instanceof List
            ((List) get("locales")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            locales() != null
            locales().size() == 1
            locales().first().id == 555L
            locales().first().name == "French"
        }
    }

    def "should handle null locales component in constructor, wither, and serde"() {
        when:
        LocalesResponse response = new LocalesResponse(null)
        Locale item = LocaleBuilder.builder().id(99L).name("German").build()
        LocalesResponse updated = response.withLocales([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        LocalesResponse deserialized = objectMapper.readValue(json, LocalesResponse.class)

        then:
        verifyAll(response) {
            locales() == null
        }
        verifyAll(updated) {
            locales() != null
            locales().size() == 1
            locales().first().id == 99L
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
