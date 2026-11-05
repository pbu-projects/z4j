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

class SectionsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create SectionsResponse with canonical constructor and builder"() {
        given:
        Section item = new Section().withName("FAQ").withId(101L)
        List<Section> list = [item]

        when:
        SectionsResponse direct = new SectionsResponse(list)
        SectionsResponse built = SectionsResponseBuilder.builder()
                .sections(list)
                .build()

        then:
        verifyAll(direct) {
            sections() == list
            sections().size() == 1
            sections().first().id == 101L
            sections().first().name == "FAQ"
        }
        verifyAll(built) {
            sections() == list
            sections().size() == 1
            sections().first().id == 101L
            sections().first().name == "FAQ"
        }
    }

    def "should update sections using record wither and addSectionsItem method"() {
        given:
        Section first = new Section().withName("Draft Section").withId(1L)
        Section second = new Section().withName("Active Section").withId(2L)
        SectionsResponse response = new SectionsResponse([first])

        when:
        SectionsResponse withNewList = response.withSections([second])
        SectionsResponse withAdded = response.addSectionsItem(second)
        SectionsResponse fromNull = new SectionsResponse(null).addSectionsItem(first)

        then:
        verifyAll(response) {
            sections().size() == 1
            sections().first().id == 1L
        }
        verifyAll(withNewList) {
            sections().size() == 1
            sections().first().id == 2L
        }
        verifyAll(withAdded) {
            sections().size() == 2
            sections().first().id == 1L
            sections().last().id == 2L
        }
        verifyAll(fromNull) {
            sections().size() == 1
            sections().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize SectionsResponse"() {
        given:
        Section item = new Section().withName("Tutorials").withId(555L)
        SectionsResponse response = new SectionsResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        SectionsResponse deserialized = objectMapper.readValue(json, SectionsResponse.class)

        then:
        verifyAll(map) {
            containsKey("sections")
            get("sections") instanceof List
            ((List) get("sections")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            sections() != null
            sections().size() == 1
            sections().first().id == 555L
            sections().first().name == "Tutorials"
        }
    }

    def "should handle null sections component in constructor, wither, and serde"() {
        when:
        SectionsResponse response = new SectionsResponse(null)
        Section item = new Section().withName("Quickstarts").withId(99L)
        SectionsResponse updated = response.withSections([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        SectionsResponse deserialized = objectMapper.readValue(json, SectionsResponse.class)

        then:
        verifyAll(response) {
            sections() == null
        }
        verifyAll(updated) {
            sections() != null
            sections().size() == 1
            sections().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("sections")
        }
        verifyAll(deserialized) {
            it == response
            sections() == null
        }
    }
}
