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
