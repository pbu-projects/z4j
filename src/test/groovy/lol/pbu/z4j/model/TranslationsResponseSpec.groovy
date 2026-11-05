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

class TranslationsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TranslationsResponse with canonical constructor and builder"() {
        given:
        Translation item = new Translation().setId(101L).setTitle("Guide translation")
        List<Translation> list = [item]

        when:
        TranslationsResponse direct = new TranslationsResponse(list)
        TranslationsResponse built = TranslationsResponseBuilder.builder()
                .translations(list)
                .build()

        then:
        verifyAll(direct) {
            translations() == list
            translations().size() == 1
            translations().first().id == 101L
            translations().first().title == "Guide translation"
        }
        verifyAll(built) {
            translations() == list
            translations().size() == 1
            translations().first().id == 101L
            translations().first().title == "Guide translation"
        }
    }

    def "should update translations using record wither and addTranslationsItem method"() {
        given:
        Translation first = new Translation().setId(1L).setTitle("Draft Title")
        Translation second = new Translation().setId(2L).setTitle("Final Title")
        TranslationsResponse response = new TranslationsResponse([first])

        when:
        TranslationsResponse withNewList = response.withTranslations([second])
        TranslationsResponse withAdded = response.addTranslationsItem(second)
        TranslationsResponse fromNull = new TranslationsResponse(null).addTranslationsItem(first)

        then:
        verifyAll(response) {
            translations().size() == 1
            translations().first().id == 1L
        }
        verifyAll(withNewList) {
            translations().size() == 1
            translations().first().id == 2L
        }
        verifyAll(withAdded) {
            translations().size() == 2
            translations().first().id == 1L
            translations().last().id == 2L
        }
        verifyAll(fromNull) {
            translations().size() == 1
            translations().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize TranslationsResponse"() {
        given:
        Translation item = new Translation().setId(555L).setTitle("Published Title")
        TranslationsResponse response = new TranslationsResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TranslationsResponse deserialized = objectMapper.readValue(json, TranslationsResponse.class)

        then:
        verifyAll(map) {
            containsKey("translations")
            get("translations") instanceof List
            ((List) get("translations")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            translations() != null
            translations().size() == 1
            translations().first().id == 555L
            translations().first().title == "Published Title"
        }
    }

    def "should handle null translations component in constructor, wither, and serde"() {
        when:
        TranslationsResponse response = new TranslationsResponse(null)
        Translation item = new Translation().setId(99L).setTitle("Updated Title")
        TranslationsResponse updated = response.withTranslations([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TranslationsResponse deserialized = objectMapper.readValue(json, TranslationsResponse.class)

        then:
        verifyAll(response) {
            translations() == null
        }
        verifyAll(updated) {
            translations() != null
            translations().size() == 1
            translations().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("translations")
        }
        verifyAll(deserialized) {
            it == response
            translations() == null
        }
    }
}
