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

class LabelCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create LabelCreateRequest with constructors and builder"() {
        given:
        Label expected = new Label("help-center").setId(101L)

        when:
        LabelCreateRequest byName = new LabelCreateRequest("help-center")
        LabelCreateRequest direct = new LabelCreateRequest(expected)
        LabelCreateRequest built = LabelCreateRequestBuilder.builder().label(expected).build()

        then:
        verifyAll(byName) {
            label() != null
            label().name == "help-center"
        }
        verifyAll(direct) {
            label() == expected
            label().id == 101L
            label().name == "help-center"
        }
        verifyAll(built) {
            it == direct
            label() == expected
            label().id == 101L
            label().name == "help-center"
        }
    }

    def "should update label using record wither"() {
        given:
        Label original = new Label("old-label").setId(1L)
        Label updatedLabel = new Label("new-label").setId(2L)
        LabelCreateRequest request = new LabelCreateRequest(original)

        when:
        LabelCreateRequest updated = request.withLabel(updatedLabel)

        then:
        verifyAll(request) {
            label() == original
            label().name == "old-label"
        }
        verifyAll(updated) {
            label() == updatedLabel
            label().name == "new-label"
        }
    }

    def "should round-trip serialize and deserialize LabelCreateRequest"() {
        given:
        Label expected = new Label("troubleshooting").setId(555L)
        LabelCreateRequest request = new LabelCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        LabelCreateRequest deserialized = objectMapper.readValue(json, LabelCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("label")
            get("label") instanceof Map
            ((Map) get("label")).id == 555
            ((Map) get("label")).name == "troubleshooting"
        }
        verifyAll(deserialized) {
            it == request
            label() != null
            label().id == 555L
            label().name == "troubleshooting"
        }
    }

    def "should handle null label component in constructor and wither"() {
        when:
        LabelCreateRequest request = new LabelCreateRequest((Label) null)
        Label replacement = new Label("faq").setId(99L)
        LabelCreateRequest updated = request.withLabel(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        LabelCreateRequest deserialized = objectMapper.readValue(json, LabelCreateRequest.class)

        then:
        verifyAll(request) {
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
            it == request
            label() == null
        }
    }
}
