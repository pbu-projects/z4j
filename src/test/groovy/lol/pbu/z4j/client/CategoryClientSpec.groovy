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
package lol.pbu.z4j.client

import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.fixture.CategoryFixtures
import lol.pbu.z4j.fixture.FixtureLoader
import lol.pbu.z4j.model.*
import spock.lang.Shared

import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
class CategoryClientSpec extends Z4jSpec {

    @Shared
    CategoryClient adminCategoryClient, agentCategoryClient, userCategoryClient

    @Shared
    List<UserSegment> userSegments

    @Shared
    List<LocaleAbbreviation> allLocales

    @Shared
    CategoryFixtures categoryFixtures

    def setupSpec() {
        adminCategoryClient = adminCtx.getBean(CategoryClient.class)
        agentCategoryClient = agentCtx.getBean(CategoryClient.class)
        userCategoryClient = userCtx.getBean(CategoryClient.class)
        allLocales = adminCtx.getBean(LocaleClient.class).listLocales().block().locales.collect { it.localeAbbreviation }
        userSegments = adminCtx.getBean(UserSegmentClient.class).listUserSegments(null).block().getUserSegments()
        categoryFixtures = FixtureLoader.loadFixture("/fixtures/category_fixtures.yaml", CategoryFixtures.class)
        assert userSegments.size() >= 2
    }


    def "can use ListArticles using the '#localeAbbreviation' locale for the #userType user type"(
            CategoryClient categoryClient, String userType, LocaleAbbreviation localeAbbreviation, SortCategoryBy sortBy, SortOrder sortOrder) {
        when: "query Categories list for the '#localeAbbreviation' locale"
        categoryClient.listCategories(localeAbbreviation, sortBy, sortOrder).block()

        then:
        noExceptionThrown()

        where:
        [[categoryClient, userType], localeAbbreviation, sortBy, sortOrder] << [
                [[adminCategoryClient, "admin"], [agentCategoryClient, "agent"], [userCategoryClient, "user"]],
                allLocales,
                [SortCategoryBy.values(), null].flatten(),
                [SortOrder.values(), null].flatten()].combinations()
    }

    def "can use ListCategoriesNoLocale using for the #userType user type"(CategoryClient categoryClient, String userType, SortCategoryBy sortBy, SortOrder sortOrder) {
        when:
        categoryClient.listCategoriesNoLocale(sortBy, sortOrder).block()

        then:
        noExceptionThrown()

        where:
        [[categoryClient, userType], sortBy, sortOrder] << [
                [[adminCategoryClient, "admin"], [agentCategoryClient, "agent"]],
                [SortCategoryBy.values(), null].flatten(),
                [SortOrder.values(), null].flatten()
        ].combinations()
    }

    def "can use CreateCategory as an #userType for the '#localeAbbreviation' locale"(CategoryClient categoryClient, String userType, LocaleAbbreviation localeAbbreviation, String categoryName, String description) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        Category category = new Category(categoryName)
        category.setDescription(description)
        createCategoryRequest.setCategory(category)

        when: "category name to be created is #categoryName"
        CategoryResponse response = categoryClient.createCategory(localeAbbreviation, createCategoryRequest).block()

        then:
        noExceptionThrown()

        cleanup: "deleting #categoryName from the #localeAbbreviation locale"
        categoryClient.deleteCategory(localeAbbreviation, response.getCategory().getId())

        where:
        [[categoryClient, userType], localeAbbreviation, [categoryName, description]] << [
                [[adminCategoryClient, "admin"]],
                allLocales,
                categoryFixtures.getCategories().collect { [it.getCategoryName(), it.getDescription()] }
        ].combinations()
    }

    def "cannot use CreateCategory as an #userType for the '#localeAbbreviation' locale"(CategoryClient categoryClient, String userType, LocaleAbbreviation localeAbbreviation, String categoryName, String description) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        Category category = new Category(categoryName)
        category.setDescription(description)
        createCategoryRequest.setCategory(category)

        when: "category name to be created is #categoryName"
        CategoryResponse response = categoryClient.createCategory(localeAbbreviation, createCategoryRequest).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)

        and:
        error.getStatus() == FORBIDDEN

        cleanup: "deleting #categoryName from the #localeAbbreviation locale"
        try {
            adminCategoryClient.deleteCategory(localeAbbreviation, response.getCategory().getId())
        } catch (NullPointerException ignored) {
        }

        where:
        [[categoryClient, userType], localeAbbreviation, [categoryName, description]] << [
                [[userCategoryClient, "user"], [agentCategoryClient, "agent"]],
                allLocales,
                categoryFixtures.getCategories().collect { [it.getCategoryName(), it.getDescription()] }
        ].combinations()
    }

    def "can use DeleteCategory as an #userType for the '#localeAbbreviation"(CategoryClient categoryClient, String userType, LocaleAbbreviation localeAbbreviation, String categoryName, String description) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        Category category = new Category(categoryName)
        category.setDescription(description)
        createCategoryRequest.setCategory(category)
        CategoryResponse response = categoryClient.createCategory(localeAbbreviation, createCategoryRequest).block()

        when:
        categoryClient.deleteCategory(localeAbbreviation, response.getCategory().getId())

        then:
        noExceptionThrown()

        where:
        [[categoryClient, userType], localeAbbreviation, [categoryName, description]] << [
                [[adminCategoryClient, "admin"]],
                allLocales,
                categoryFixtures.getDeleteCategories().collect { [it.getCategoryName(), it.getDescription()] }
        ].combinations()
    }

    def "cannot use DeleteCategory as an #userType for the '#localeAbbreviation' locale"(CategoryClient categoryClient, String userType, LocaleAbbreviation localeAbbreviation, String categoryName, String description) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        Category category = new Category(categoryName)
        category.setDescription(description)
        createCategoryRequest.setCategory(category)
        CategoryResponse response = adminCategoryClient.createCategory(localeAbbreviation, createCategoryRequest).block()

        when:
        categoryClient.deleteCategory(localeAbbreviation, response.getCategory().getId())

        then:
        noExceptionThrown()

        cleanup:
        try {
            adminCategoryClient.deleteCategory(localeAbbreviation, response.getCategory().getId())
        } catch (NullPointerException ignored) {
        }

        where:
        [[categoryClient, userType], localeAbbreviation, [categoryName, description]] << [
                [[userCategoryClient, "user"], [agentCategoryClient, "agent"]],
                allLocales,
                categoryFixtures.getDeleteCategories().collect { [it.getCategoryName(), it.getDescription()] }
        ].combinations()
    }

}


