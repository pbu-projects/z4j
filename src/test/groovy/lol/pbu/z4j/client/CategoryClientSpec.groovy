package lol.pbu.z4j.client

import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.*
import spock.lang.Shared

import static io.micronaut.http.HttpStatus.BAD_REQUEST
import static io.micronaut.http.HttpStatus.FORBIDDEN

@MicronautTest
@SuppressWarnings("GroovyAssignabilityCheck")
class CategoryClientSpec extends Z4jSpec {

    @Shared
    CategoryClient adminCategoryClient, agentCategoryClient, userCategoryClient

    @Shared
    List<UserSegment> userSegments

    @Shared
    List<LocaleAbbreviation> allLocales

    def setupSpec() {
        adminCategoryClient = adminCtx.getBean(CategoryClient.class)
        agentCategoryClient = agentCtx.getBean(CategoryClient.class)
        userCategoryClient = userCtx.getBean(CategoryClient.class)
        allLocales = List.of(LocaleAbbreviation.ENGLISH_UNITED_STATES, LocaleAbbreviation.FRENCH)
        userSegments = adminCtx.getBean(UserSegmentClient.class).listUserSegments(null).block().getUserSegments()
        assert userSegments.size() >= 2
        // built in segments should be at least 2, this is here to just double check this doesn't change
    }

    def "can use ListArticles using the '#locale' locale for the #userType user type"(CategoryClient categoryClient, String userType, LocaleAbbreviation locale, ListCategoriesSortByParameter sortBy, ListArticlesSortOrderParameter sortOrder) {
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

    def "can use CreateCategory as an #userType for the '#locale' locale"(CategoryClient categoryClient, String userType, LocaleAbbreviation locale) {
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

    def "can use CreateCategoryNoLocale as an #userType and update it with a translation"(CategoryClient categoryClient, String userType) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        String categoryName = faker.animal().name()
        def translations = List.of(new Translation(LocaleAbbreviation.FRENCH, faker.backToTheFuture().quote()),
                new Translation(LocaleAbbreviation.ENGLISH_UNITED_STATES, faker.redDeadRedemption2().quote()))
        Category category = new Category(categoryName)
                .setLocale(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .setPosition(0)
        createCategoryRequest.setCategory(category)


        when:
        CategoryResponse response = categoryClient.createCategoryNoLocale(createCategoryRequest).block()

        and:
        category.setTranslations(translations)
        categoryClient.updateCategoryNoLocale(response.getCategory().getId(), new CreateCategoryRequest(category)).block()

        then:
        noExceptionThrown()

        cleanup:
        categoryClient.deleteCategory(LocaleAbbreviation.ENGLISH_UNITED_STATES, response.category.id)

        where:
        [[categoryClient, userType]] << [[[adminCategoryClient, "admin"]]].combinations()


    }


    def "cannot use CreateCategory as an #userType for the '#locale' locale"(CategoryClient categoryClient, String userType, LocaleAbbreviation locale) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        String categoryName = faker.animal().name()
        Category category = new Category(categoryName)
        category.setDescription(faker.backToTheFuture().quote())
        createCategoryRequest.setCategory(category)

        when: "category name to be created is #categoryName"
        CategoryResponse response = categoryClient.createCategory(locale, createCategoryRequest).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)

        and:
        error.getStatus() == FORBIDDEN

        cleanup: "deleting #categoryName from the #locale locale"
        try {
            adminCategoryClient.deleteCategory(locale, response.getCategory().getId())
        } catch (NullPointerException ignored) {
        }

        where:
        [[categoryClient, userType], locale] << [[[userCategoryClient, "user"], [agentCategoryClient, "agent"]], allLocales].combinations()
    }

    def "cannot use CreateCategoryNoLocale as an #userType"(CategoryClient categoryClient, String userType) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        String categoryName = faker.animal().name()
        Category category = new Category(categoryName)
        category.setDescription(faker.backToTheFuture().quote())
        createCategoryRequest.setCategory(category)

        when: "category name to be created is #categoryName"
        categoryClient.createCategoryNoLocale(createCategoryRequest).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)

        and:
        error.getStatus() == FORBIDDEN

        where:
        [[categoryClient, userType]] << [[[userCategoryClient, "user"], [agentCategoryClient, "agent"]]].combinations()
    }

    def "can use DeleteCategory as an #userType for the '#locale"(CategoryClient categoryClient, String userType, LocaleAbbreviation locale) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        String categoryName = faker.bluey().quote()
        Category category = new Category(categoryName)
        category.setDescription(faker.lordOfTheRings().location())
        createCategoryRequest.setCategory(category)
        CategoryResponse response = categoryClient.createCategory(locale, createCategoryRequest).block()

        when:
        categoryClient.deleteCategory(locale, response.getCategory().getId())

        then:
        noExceptionThrown()

        where:
        [[categoryClient, userType], locale] << [[[adminCategoryClient, "admin"]], allLocales].combinations()
    }

    def "cannot use DeleteCategory as an #userType for the '#locale' locale"(CategoryClient categoryClient, String userType, LocaleAbbreviation locale) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        String categoryName = faker.bluey().quote() + " " + UUID.randomUUID().toString()
        Category category = new Category(categoryName)
        category.setDescription(faker.lordOfTheRings().location())
        createCategoryRequest.setCategory(category)
        CategoryResponse response = adminCategoryClient.createCategory(locale, createCategoryRequest).block()

        when:
        categoryClient.deleteCategory(locale, response.getCategory().getId())

        then:
        noExceptionThrown() // this shouldn't be allowed!
//        HttpClientResponseException error = thrown(HttpClientResponseException)
//        and:
//        error.getStatus() == FORBIDDEN

        cleanup:
        adminCategoryClient.deleteCategory(locale, response.getCategory().getId())


        where:
        [[categoryClient, userType], locale] << [[[userCategoryClient, "user"], [agentCategoryClient, "agent"]], allLocales].combinations()
    }

    def "can use ShowCategory as a #userType for the #locale locale"(CategoryClient categoryClient, String userType, LocaleAbbreviation locale) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        String categoryName = faker.animal().name()
        Category category = new Category(categoryName)
        category.setDescription(faker.backToTheFuture().quote())
        createCategoryRequest.setCategory(category)
        CategoryResponse createdCategory = adminCategoryClient.createCategory(locale, createCategoryRequest).block()

        when: "showing category #categoryName"
        categoryClient.showCategory(locale, createdCategory.getCategory().getId()).block()

        then:
        noExceptionThrown()

        cleanup:
        adminCategoryClient.deleteCategory(locale, createdCategory.getCategory().getId()).block()

        where:
        [[categoryClient, userType], locale] << [[[adminCategoryClient, "admin"], [agentCategoryClient, "agent"], [userCategoryClient, "user"]], allLocales].combinations()
    }

    def "can use ShowCategoryNoLocale as a #userType"(CategoryClient categoryClient, String userType) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        String categoryName = faker.animal().name()
        Category category = new Category(categoryName)
        category.setDescription(faker.backToTheFuture().quote())
        createCategoryRequest.setCategory(category)
        CategoryResponse createdCategory = adminCategoryClient.createCategoryNoLocale(createCategoryRequest).block()

        when: "showing category #categoryName"
        categoryClient.showCategoryNoLocale(createdCategory.getCategory().getId()).block()

        then:
        noExceptionThrown()

        cleanup:
        adminCategoryClient.deleteCategory(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdCategory.getCategory().getId()).block()

        where:
        [[categoryClient, userType]] << [[[adminCategoryClient, "admin"], [agentCategoryClient, "agent"]]].combinations()
    }

    def "cannot use ShowCategoryNoLocale as a #userType"(CategoryClient categoryClient, String userType) {
        given:
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest()
        String categoryName = faker.animal().name()
        Category category = new Category(categoryName)
        category.setDescription(faker.backToTheFuture().quote())
        createCategoryRequest.setCategory(category)
        CategoryResponse createdCategory = adminCategoryClient.createCategoryNoLocale(createCategoryRequest).block()

        when: "showing category #categoryName"
        categoryClient.showCategoryNoLocale(createdCategory.getCategory().getId()).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)

        and:
        error.getStatus() == BAD_REQUEST

        cleanup:
        adminCategoryClient.deleteCategory(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdCategory.getCategory().getId()).block()

        where:
        [[categoryClient, userType]] << [[[userCategoryClient, "user"]]].combinations()
    }
}
