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

class SectionResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create SectionResponse with canonical constructor and builder"() {
        given:
        Section expected = new Section().setName("FAQ").setId(303L)

        when:
        SectionResponse direct = new SectionResponse(expected)
        SectionResponse built = SectionResponseBuilder.builder().section(expected).build()

        then:
        verifyAll(direct) {
            section == expected
            section.id == 303L
            section.name == "FAQ"
        }
        verifyAll(built) {
            section == expected
            section.id == 303L
            section.name == "FAQ"
        }
    }

    def "should update section using record wither"() {
        given:
        Section originalSection = new Section().setName("Draft Section").setId(1L)
        Section newSection = new Section().setName("Active Section").setId(2L)
        SectionResponse response = new SectionResponse(originalSection)

        when:
        SectionResponse updated = response.withSection(newSection)

        then:
        verifyAll(response) {
            section == originalSection
            section.name == "Draft Section"
        }
        verifyAll(updated) {
            section == newSection
            section.name == "Active Section"
        }
    }

    def "should round-trip serialize and deserialize SectionResponse"() {
        given:
        Section expected = new Section().setName("Tutorials").setId(888L)
        SectionResponse response = new SectionResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        SectionResponse deserialized = objectMapper.readValue(json, SectionResponse.class)

        then:
        verifyAll(map) {
            containsKey("section")
            get("section") instanceof Map
            ((Map) get("section")).id == 888
            ((Map) get("section")).name == "Tutorials"
        }
        verifyAll(deserialized) {
            it == response
            section != null
            section.id == 888L
            section.name == "Tutorials"
        }
    }

    def "should handle null section component in constructor and wither"() {
        when:
        SectionResponse response = new SectionResponse(null)
        Section replacement = new Section().setName("Quickstarts").setId(77L)
        SectionResponse updated = response.withSection(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        SectionResponse deserialized = objectMapper.readValue(json, SectionResponse.class)

        then:
        verifyAll(response) {
            section() == null
        }
        verifyAll(updated) {
            section() == replacement
            section().id == 77L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("section")
        }
        verifyAll(deserialized) {
            it == response
            section() == null
        }
    }
}
