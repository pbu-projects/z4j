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

class CategoryResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CategoryResponse with canonical constructor and builder"() {
        given:
        Category expected = new Category("Billing").setId(202L)

        when:
        CategoryResponse direct = new CategoryResponse(expected)
        CategoryResponse built = CategoryResponseBuilder.builder().category(expected).build()

        then:
        verifyAll(direct) {
            category == expected
            category.id == 202L
            category.name == "Billing"
        }
        verifyAll(built) {
            category == expected
            category.id == 202L
            category.name == "Billing"
        }
    }

    def "should update category using record wither"() {
        given:
        Category originalCategory = new Category("Old Name").setId(1L)
        Category newCategory = new Category("New Name").setId(2L)
        CategoryResponse response = new CategoryResponse(originalCategory)

        when:
        CategoryResponse updated = response.withCategory(newCategory)

        then:
        verifyAll(response) {
            category == originalCategory
            category.name == "Old Name"
        }
        verifyAll(updated) {
            category == newCategory
            category.name == "New Name"
        }
    }

    def "should round-trip serialize and deserialize CategoryResponse"() {
        given:
        Category expected = new Category("Support").setId(777L)
        CategoryResponse response = new CategoryResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CategoryResponse deserialized = objectMapper.readValue(json, CategoryResponse.class)

        then:
        verifyAll(map) {
            containsKey("category")
            get("category") instanceof Map
            ((Map) get("category")).id == 777
            ((Map) get("category")).name == "Support"
        }
        verifyAll(deserialized) {
            it == response
            category != null
            category.id == 777L
            category.name == "Support"
        }
    }

    def "should handle null category component in constructor and wither"() {
        when:
        CategoryResponse response = new CategoryResponse(null)
        Category replacement = new Category("General").setId(88L)
        CategoryResponse updated = response.withCategory(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CategoryResponse deserialized = objectMapper.readValue(json, CategoryResponse.class)

        then:
        verifyAll(response) {
            category() == null
        }
        verifyAll(updated) {
            category() == replacement
            category().id == 88L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("category")
        }
        verifyAll(deserialized) {
            it == response
            category() == null
        }
    }
}
