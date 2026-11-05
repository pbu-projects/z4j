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

class CreateCategoryRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CreateCategoryRequest with canonical constructor and builder"() {
        given:
        Category expected = new Category("General").withId(101L)

        when:
        CreateCategoryRequest direct = new CreateCategoryRequest(expected)
        CreateCategoryRequest built = CreateCategoryRequestBuilder.builder().category(expected).build()

        then:
        verifyAll(direct) {
            category() == expected
            category().id == 101L
            category().name == "General"
        }
        verifyAll(built) {
            it == direct
            category() == expected
            category().id == 101L
            category().name == "General"
        }
    }

    def "should update category using record wither"() {
        given:
        Category originalCategory = new Category("Draft").withId(1L)
        Category newCategory = new Category("Published").withId(2L)
        CreateCategoryRequest request = new CreateCategoryRequest(originalCategory)

        when:
        CreateCategoryRequest updated = request.withCategory(newCategory)

        then:
        verifyAll(request) {
            category() == originalCategory
            category().name == "Draft"
        }
        verifyAll(updated) {
            category() == newCategory
            category().name == "Published"
        }
    }

    def "should round-trip serialize and deserialize CreateCategoryRequest"() {
        given:
        Category expected = new Category("FAQ").withId(555L)
        CreateCategoryRequest request = new CreateCategoryRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CreateCategoryRequest deserialized = objectMapper.readValue(json, CreateCategoryRequest.class)

        then:
        verifyAll(map) {
            containsKey("category")
            get("category") instanceof Map
            ((Map) get("category")).id == 555
            ((Map) get("category")).name == "FAQ"
        }
        verifyAll(deserialized) {
            it == request
            category() != null
            category().id == 555L
            category().name == "FAQ"
        }
    }

    def "should handle null category component in constructor and wither"() {
        when:
        CreateCategoryRequest request = new CreateCategoryRequest(null)
        Category replacement = new Category("Created").withId(99L)
        CreateCategoryRequest updated = request.withCategory(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CreateCategoryRequest deserialized = objectMapper.readValue(json, CreateCategoryRequest.class)

        then:
        verifyAll(request) {
            category() == null
        }
        verifyAll(updated) {
            category() == replacement
            category().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("category")
        }
        verifyAll(deserialized) {
            it == request
            category() == null
        }
    }
}
