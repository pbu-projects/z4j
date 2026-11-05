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

class CreateSectionRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CreateSectionRequest with canonical constructor and builder"() {
        given:
        Section expected = new Section().withName("Getting Started").withId(101L)

        when:
        CreateSectionRequest direct = new CreateSectionRequest(expected)
        CreateSectionRequest built = CreateSectionRequestBuilder.builder().section(expected).build()

        then:
        verifyAll(direct) {
            section() == expected
            section().id == 101L
            section().name == "Getting Started"
        }
        verifyAll(built) {
            it == direct
            section() == expected
            section().id == 101L
            section().name == "Getting Started"
        }
    }

    def "should update section using record wither"() {
        given:
        Section originalSection = new Section().withName("Draft Section").withId(1L)
        Section newSection = new Section().withName("Published Section").withId(2L)
        CreateSectionRequest request = new CreateSectionRequest(originalSection)

        when:
        CreateSectionRequest updated = request.withSection(newSection)

        then:
        verifyAll(request) {
            section() == originalSection
            section().name == "Draft Section"
        }
        verifyAll(updated) {
            section() == newSection
            section().name == "Published Section"
        }
    }

    def "should round-trip serialize and deserialize CreateSectionRequest"() {
        given:
        Section expected = new Section().withName("Billing").withId(555L)
        CreateSectionRequest request = new CreateSectionRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CreateSectionRequest deserialized = objectMapper.readValue(json, CreateSectionRequest.class)

        then:
        verifyAll(map) {
            containsKey("section")
            get("section") instanceof Map
            ((Map) get("section")).id == 555
            ((Map) get("section")).name == "Billing"
        }
        verifyAll(deserialized) {
            it == request
            section() != null
            section().id == 555L
            section().name == "Billing"
        }
    }

    def "should handle null section component in constructor and wither"() {
        when:
        CreateSectionRequest request = new CreateSectionRequest(null)
        Section replacement = new Section().withName("Created").withId(99L)
        CreateSectionRequest updated = request.withSection(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CreateSectionRequest deserialized = objectMapper.readValue(json, CreateSectionRequest.class)

        then:
        verifyAll(request) {
            section() == null
        }
        verifyAll(updated) {
            section() == replacement
            section().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("section")
        }
        verifyAll(deserialized) {
            it == request
            section() == null
        }
    }
}
