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

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class CategoriesResponseSpec extends Z4jSpec {

    @Unroll
    def "should add categories item"() {
        given:
        def categoriesResponse = new CategoriesResponse()
        categoriesResponse.categories == null
        def category = new Category(faker.lorem().word())

        when:
        categoriesResponse.addCategoriesItem(category)

        then:
        categoriesResponse.categories.size() == 1
        categoriesResponse.categories[0] == category
    }

    @Unroll
    def "add categories item to existing list"() {
        given:
        def existingCategory = new Category(faker.lorem().word())
        def categoriesResponse = new CategoriesResponse()
        categoriesResponse.categories = [existingCategory]
        def newCategory = new Category(faker.lorem().word())

        when:
        categoriesResponse.addCategoriesItem(newCategory)

        then:
        categoriesResponse.categories.size() == 2
        categoriesResponse.categories.containsAll([existingCategory, newCategory])
    }
}

