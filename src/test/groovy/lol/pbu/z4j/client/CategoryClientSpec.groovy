package lol.pbu.z4j.client

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.*
import spock.lang.Shared

@MicronautTest
class CategoryClientSpec extends Z4jSpec {

    @Shared
    CategoryClient adminCategoryClient, agentCategoryClient, userCategoryClient

    @Shared
    List<UserSegment> userSegments

    @Shared
    List<String> allLocales

    def setupSpec() {
        adminCategoryClient = adminCtx.getBean(CategoryClient.class)
        agentCategoryClient = agentCtx.getBean(CategoryClient.class)
        userCategoryClient = userCtx.getBean(CategoryClient.class)
        allLocales = adminCtx.getBean(LocaleClient.class).listLocales().block().locales.collect { it.locale.toLowerCase() }
        userSegments = adminCtx.getBean(UserSegmentClient.class).listUserSegments(null).block().getUserSegments()
        assert userSegments.size() >= 2
        // built in segments should be at least 2, this is here to just double check this doesn't change
    }

    def "can use ListArticles using the '#locale' locale for the #userType user type"(CategoryClient categoryClient, String userType, String locale, ListCategoriesSortByParameter sortBy, ListArticlesSortOrderParameter sortOrder) {
        when: "query Categories list for the '#locale' locale"
        categoryClient.listCategories(locale, sortBy, sortOrder).block()

        then:
        noExceptionThrown()

        where:
        [[categoryClient, userType], locale, sortBy, sortOrder, startTime, labelNames] << [[[adminCategoryClient, "admin"], [agentCategoryClient, "agent"], [userCategoryClient, "user"]],
                                                                                           allLocales,
                                                                                           [ListCategoriesSortByParameter.values(), null].flatten(),
                                                                                           [ListArticlesSortOrderParameter.values(), null].flatten()].combinations()
    }

    def "can use ListCategoriesNoLocale using for the #userType user type"(CategoryClient categoryClient, String userType, ListCategoriesSortByParameter sortBy, ListArticlesSortOrderParameter sortOrder) {
        when:
        categoryClient.listCategoriesNoLocale(sortBy, sortOrder).block()

        then:
        noExceptionThrown()

        where:
        [[categoryClient, userType], sortBy, sortOrder] << [
                [[adminCategoryClient, "admin"], [agentCategoryClient, "agent"]],
                [ListCategoriesSortByParameter.values(), null].flatten(),
                [ListArticlesSortOrderParameter.values(), null].flatten()
        ].combinations()
    }

    def "can use CreateCategory as an #userType for the '#locale' locale"(CategoryClient categoryClient, String userType, String locale) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        String categoryName = faker.animal().name()
        Category category = new Category(categoryName)
        category.setDescription(faker.backToTheFuture().quote())
        createCategoryRequest.setCategory(category)

        when: "category name to be created is #categoryName"
        CategoryResponse response = categoryClient.createCategory(locale, createCategoryRequest).block()

        then:
        noExceptionThrown()

        cleanup: "deleting #categoryName from the #locale locale"
        categoryClient.deleteCategory(locale, response.getCategory().getId())

        where:
        [[categoryClient, userType], locale] << [[[adminCategoryClient, "admin"]], allLocales].combinations()
    }
}
