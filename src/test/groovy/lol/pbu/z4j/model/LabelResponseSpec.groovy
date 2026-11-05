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

class LabelResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create LabelResponse with canonical constructor and builder"() {
        given:
        Label expected = new Label("help-center").withId(101L)

        when:
        LabelResponse direct = new LabelResponse(expected)
        LabelResponse built = LabelResponseBuilder.builder().label(expected).build()

        then:
        verifyAll(direct) {
            label == expected
            label.id == 101L
            label.name == "help-center"
        }
        verifyAll(built) {
            label == expected
            label.id == 101L
            label.name == "help-center"
        }
    }

    def "should update label using record wither"() {
        given:
        Label original = new Label("old-label").withId(1L)
        Label updatedLabel = new Label("new-label").withId(2L)
        LabelResponse response = new LabelResponse(original)

        when:
        LabelResponse updated = response.withLabel(updatedLabel)

        then:
        verifyAll(response) {
            label == original
            label.name == "old-label"
        }
        verifyAll(updated) {
            label == updatedLabel
            label.name == "new-label"
        }
    }

    def "should round-trip serialize and deserialize LabelResponse"() {
        given:
        Label expected = new Label("troubleshooting").withId(555L)
        LabelResponse response = new LabelResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        LabelResponse deserialized = objectMapper.readValue(json, LabelResponse.class)

        then:
        verifyAll(map) {
            containsKey("label")
            get("label") instanceof Map
            ((Map) get("label")).id == 555
            ((Map) get("label")).name == "troubleshooting"
        }
        verifyAll(deserialized) {
            it == response
            label != null
            label.id == 555L
            label.name == "troubleshooting"
        }
    }

    def "should handle null label component in constructor and wither"() {
        when:
        LabelResponse response = new LabelResponse(null)
        Label replacement = new Label("faq").withId(99L)
        LabelResponse updated = response.withLabel(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        LabelResponse deserialized = objectMapper.readValue(json, LabelResponse.class)

        then:
        verifyAll(response) {
            label() == null
        }
        verifyAll(updated) {
            label() == replacement
            label().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("label")
        }
        verifyAll(deserialized) {
            it == response
            label() == null
        }
    }
}
