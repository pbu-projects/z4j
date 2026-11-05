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

class TranslationResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TranslationResponse with canonical constructor and builder"() {
        given:
        Translation expected = new Translation()
                .setId(101L)
                .setTitle("Welcome")
                .setBody("Hello and welcome")
                .setLocale(LocaleAbbreviation.ENGLISH_UNITED_STATES)

        when:
        TranslationResponse direct = new TranslationResponse(expected)
        TranslationResponse built = TranslationResponseBuilder.builder().translation(expected).build()

        then:
        verifyAll(direct) {
            translation == expected
            translation.id == 101L
            translation.title == "Welcome"
            translation.body == "Hello and welcome"
            translation.locale == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
        verifyAll(built) {
            translation == expected
            translation.id == 101L
            translation.title == "Welcome"
            translation.body == "Hello and welcome"
            translation.locale == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
    }

    def "should update translation using record wither"() {
        given:
        Translation original = new Translation().setId(1L).setTitle("Draft Title")
        Translation updatedTranslation = new Translation().setId(2L).setTitle("Final Title")
        TranslationResponse response = new TranslationResponse(original)

        when:
        TranslationResponse updated = response.withTranslation(updatedTranslation)

        then:
        verifyAll(response) {
            translation == original
            translation.title == "Draft Title"
        }
        verifyAll(updated) {
            translation == updatedTranslation
            translation.title == "Final Title"
        }
    }

    def "should round-trip serialize and deserialize TranslationResponse"() {
        given:
        Translation expected = new Translation()
                .setId(555L)
                .setTitle("Overview")
                .setBody("Product overview")
                .setLocale(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        TranslationResponse response = new TranslationResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TranslationResponse deserialized = objectMapper.readValue(json, TranslationResponse.class)

        then:
        verifyAll(map) {
            containsKey("translation")
            get("translation") instanceof Map
            ((Map) get("translation")).id == 555
            ((Map) get("translation")).title == "Overview"
        }
        verifyAll(deserialized) {
            it == response
            translation != null
            translation.id == 555L
            translation.title == "Overview"
            translation.body == "Product overview"
            translation.locale == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
    }

    def "should handle null translation component in constructor and wither"() {
        when:
        TranslationResponse response = new TranslationResponse(null)
        Translation replacement = new Translation().setId(99L).setTitle("Published")
        TranslationResponse updated = response.withTranslation(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TranslationResponse deserialized = objectMapper.readValue(json, TranslationResponse.class)

        then:
        verifyAll(response) {
            translation() == null
        }
        verifyAll(updated) {
            translation() == replacement
            translation().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("translation")
        }
        verifyAll(deserialized) {
            it == response
            translation() == null
        }
    }
}
