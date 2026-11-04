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

class LabelsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create LabelsResponse with canonical constructor and builder"() {
        given:
        Label item = new Label("help-center").withId(101L)
        List<Label> list = [item]

        when:
        LabelsResponse direct = new LabelsResponse(list)
        LabelsResponse built = LabelsResponseBuilder.builder()
                .labels(list)
                .build()

        then:
        verifyAll(direct) {
            labels() == list
            labels().size() == 1
            labels().first().id == 101L
            labels().first().name == "help-center"
        }
        verifyAll(built) {
            labels() == list
            labels().size() == 1
            labels().first().id == 101L
            labels().first().name == "help-center"
        }
    }

    def "should update labels using record wither and addLabelsItem method"() {
        given:
        Label first = new Label("old-label").withId(1L)
        Label second = new Label("new-label").withId(2L)
        LabelsResponse response = new LabelsResponse([first])

        when:
        LabelsResponse withNewList = response.withLabels([second])
        LabelsResponse withAdded = response.addLabelsItem(second)
        LabelsResponse fromNull = new LabelsResponse(null).addLabelsItem(first)

        then:
        verifyAll(response) {
            labels().size() == 1
            labels().first().id == 1L
        }
        verifyAll(withNewList) {
            labels().size() == 1
            labels().first().id == 2L
        }
        verifyAll(withAdded) {
            labels().size() == 2
            labels().first().id == 1L
            labels().last().id == 2L
        }
        verifyAll(fromNull) {
            labels().size() == 1
            labels().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize LabelsResponse"() {
        given:
        Label item = new Label("troubleshooting").withId(555L)
        LabelsResponse response = new LabelsResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        LabelsResponse deserialized = objectMapper.readValue(json, LabelsResponse.class)

        then:
        verifyAll(map) {
            containsKey("labels")
            get("labels") instanceof List
            ((List) get("labels")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            labels() != null
            labels().size() == 1
            labels().first().id == 555L
            labels().first().name == "troubleshooting"
        }
    }

    def "should handle null labels component in constructor, wither, and serde"() {
        when:
        LabelsResponse response = new LabelsResponse(null)
        Label item = new Label("faq").withId(99L)
        LabelsResponse updated = response.withLabels([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        LabelsResponse deserialized = objectMapper.readValue(json, LabelsResponse.class)

        then:
        verifyAll(response) {
            labels() == null
        }
        verifyAll(updated) {
            labels() != null
            labels().size() == 1
            labels().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("labels")
        }
        verifyAll(deserialized) {
            it == response
            labels() == null
        }
    }
}
