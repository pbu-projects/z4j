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

class TranslationUpdateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TranslationUpdateRequest with canonical constructor and builder"() {
        given:
        Translation expected = new Translation()
                .withId(101L)
                .withTitle("Updated Welcome")
                .withBody("Updated hello and welcome")
                .withLocale(LocaleAbbreviation.ENGLISH_UNITED_STATES)

        when:
        TranslationUpdateRequest direct = new TranslationUpdateRequest(expected)
        TranslationUpdateRequest built = TranslationUpdateRequestBuilder.builder().translation(expected).build()

        then:
        verifyAll(direct) {
            translation() == expected
            translation().id == 101L
            translation().title == "Updated Welcome"
            translation().body == "Updated hello and welcome"
            translation().locale == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
        verifyAll(built) {
            it == direct
            translation() == expected
            translation().id == 101L
            translation().title == "Updated Welcome"
            translation().body == "Updated hello and welcome"
            translation().locale == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
    }

    def "should update translation using record wither"() {
        given:
        Translation original = new Translation().withId(1L).withTitle("Draft Title")
        Translation updatedTranslation = new Translation().withId(2L).withTitle("Final Title")
        TranslationUpdateRequest request = new TranslationUpdateRequest(original)

        when:
        TranslationUpdateRequest updated = request.withTranslation(updatedTranslation)

        then:
        verifyAll(request) {
            translation() == original
            translation().title == "Draft Title"
        }
        verifyAll(updated) {
            translation() == updatedTranslation
            translation().title == "Final Title"
        }
    }

    def "should round-trip serialize and deserialize TranslationUpdateRequest"() {
        given:
        Translation expected = new Translation()
                .withId(555L)
                .withTitle("Overview v2")
                .withBody("Updated product overview")
                .withLocale(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        TranslationUpdateRequest request = new TranslationUpdateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TranslationUpdateRequest deserialized = objectMapper.readValue(json, TranslationUpdateRequest.class)

        then:
        verifyAll(map) {
            containsKey("translation")
            get("translation") instanceof Map
            ((Map) get("translation")).id == 555
            ((Map) get("translation")).title == "Overview v2"
        }
        verifyAll(deserialized) {
            it == request
            translation() != null
            translation().id == 555L
            translation().title == "Overview v2"
            translation().body == "Updated product overview"
            translation().locale == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
    }

    def "should handle null translation component in constructor and wither"() {
        when:
        TranslationUpdateRequest request = new TranslationUpdateRequest(null)
        Translation replacement = new Translation().withId(99L).withTitle("Published")
        TranslationUpdateRequest updated = request.withTranslation(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TranslationUpdateRequest deserialized = objectMapper.readValue(json, TranslationUpdateRequest.class)

        then:
        verifyAll(request) {
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
            it == request
            translation() == null
        }
    }
}
