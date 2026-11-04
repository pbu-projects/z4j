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

class CategoriesResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CategoriesResponse with canonical constructor and builder"() {
        given:
        Category item = new Category("Billing").withId(101L)
        List<Category> list = [item]

        when:
        CategoriesResponse direct = new CategoriesResponse(list)
        CategoriesResponse built = CategoriesResponseBuilder.builder()
                .categories(list)
                .build()

        then:
        verifyAll(direct) {
            categories() == list
            categories().size() == 1
            categories().first().id == 101L
            categories().first().name == "Billing"
        }
        verifyAll(built) {
            categories() == list
            categories().size() == 1
            categories().first().id == 101L
            categories().first().name == "Billing"
        }
    }

    def "should update categories using record wither and addCategoriesItem method"() {
        given:
        Category first = new Category("Old Category").withId(1L)
        Category second = new Category("New Category").withId(2L)
        CategoriesResponse response = new CategoriesResponse([first])

        when:
        CategoriesResponse withNewList = response.withCategories([second])
        CategoriesResponse withAdded = response.addCategoriesItem(second)
        CategoriesResponse fromNull = new CategoriesResponse(null).addCategoriesItem(first)

        then:
        verifyAll(response) {
            categories().size() == 1
            categories().first().id == 1L
        }
        verifyAll(withNewList) {
            categories().size() == 1
            categories().first().id == 2L
        }
        verifyAll(withAdded) {
            categories().size() == 2
            categories().first().id == 1L
            categories().last().id == 2L
        }
        verifyAll(fromNull) {
            categories().size() == 1
            categories().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize CategoriesResponse"() {
        given:
        Category item = new Category("Support").withId(555L)
        CategoriesResponse response = new CategoriesResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CategoriesResponse deserialized = objectMapper.readValue(json, CategoriesResponse.class)

        then:
        verifyAll(map) {
            containsKey("categories")
            get("categories") instanceof List
            ((List) get("categories")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            categories() != null
            categories().size() == 1
            categories().first().id == 555L
            categories().first().name == "Support"
        }
    }

    def "should handle null categories component in constructor, wither, and serde"() {
        when:
        CategoriesResponse response = new CategoriesResponse(null)
        Category item = new Category("General").withId(99L)
        CategoriesResponse updated = response.withCategories([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CategoriesResponse deserialized = objectMapper.readValue(json, CategoriesResponse.class)

        then:
        verifyAll(response) {
            categories() == null
        }
        verifyAll(updated) {
            categories() != null
            categories().size() == 1
            categories().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("categories")
        }
        verifyAll(deserialized) {
            it == response
            categories() == null
        }
    }
}
