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

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.fixture.ArticleFixtures
import lol.pbu.z4j.fixture.FixtureLoader
import lol.pbu.z4j.model.*
import spock.lang.Shared

@MicronautTest
class TranslationClientSpec extends Z4jSpec {

    @Shared
    ArticleClient adminArticleClient

    @Shared
    SectionClient adminSectionClient

    @Shared
    CategoryClient adminCategoryClient

    @Shared
    TranslationClient adminTranslationClient, agentTranslationClient, userTranslationClient, badTokenTranslationClient, badEmailTranslationClient

    @Shared
    List<LocaleAbbreviation> allLocales

    @Shared
    LocaleAbbreviation defaultLocale

    @Shared
    List<LocaleAbbreviation> targetLocales

    @Shared
    ArticleFixtures articleFixtures

    @Shared
    Long validSectionId

    @Shared
    Long validPermissionGroupId

    @Shared
    Category testCategory

    @Shared
    Section testSection

    @Shared
    Article testArticle

    def setupSpec() {
        adminArticleClient = adminCtx.getBean(ArticleClient.class)
        adminSectionClient = adminCtx.getBean(SectionClient.class)
        adminCategoryClient = adminCtx.getBean(CategoryClient.class)
        adminTranslationClient = adminCtx.getBean(TranslationClient.class)
        agentTranslationClient = agentCtx.getBean(TranslationClient.class)
        userTranslationClient = userCtx.getBean(TranslationClient.class)
        badTokenTranslationClient = badTokenCtx.getBean(TranslationClient.class)
        badEmailTranslationClient = badEmailCtx.getBean(TranslationClient.class)

        HelpCenterLocalesResponse hcLocales = adminTranslationClient.listHelpCenterLocales().block()
        defaultLocale = hcLocales?.defaultLocale ?: LocaleAbbreviation.ENGLISH_UNITED_STATES
        allLocales = hcLocales?.locales ?: userCtx.getBean(LocaleClient.class).listLocales().block().locales.collect { it.localeAbbreviation }
        targetLocales = allLocales.findAll { it != defaultLocale }
        assert !targetLocales.isEmpty() : "Test requires at least one non-default locale enabled in Help Center"

        articleFixtures = FixtureLoader.loadFixture("/fixtures/article_fixtures.yaml", ArticleFixtures.class)

        for (LocaleAbbreviation loc : allLocales) {
            def articles = adminArticleClient.listArticles(loc, null, null, null, null).block()?.articles
            if (articles != null && !articles.isEmpty()) {
                validSectionId = articles.get(0).sectionId
                validPermissionGroupId = articles.get(0).permissionGroupId
                break
            }
        }

        if (validSectionId == null) {
            def auth = "Basic " + (System.getenv("Z4J_ADMIN_EMAIL") + "/token:" + System.getenv("Z4J_TOKEN")).bytes.encodeBase64().toString()
            def sectionsConn = URI.create(System.getenv("Z4J_URL") + "/api/v2/help_center/en-us/sections.json").toURL().openConnection()
            sectionsConn.setRequestProperty("Authorization", auth)
            sectionsConn.setRequestProperty("Accept", "application/json")
            def sectionsJson = new ObjectMapper().readValue(sectionsConn.inputStream, Map.class)

            def pgConn = URI.create(System.getenv("Z4J_URL") + "/api/v2/guide/permission_groups.json").toURL().openConnection()
            pgConn.setRequestProperty("Authorization", auth)
            pgConn.setRequestProperty("Accept", "application/json")
            def pgJson = new ObjectMapper().readValue(pgConn.inputStream, Map.class)

            if (sectionsJson.sections && pgJson.permission_groups) {
                validSectionId = sectionsJson.sections[0].id as Long
                validPermissionGroupId = pgJson.permission_groups[0].id as Long
            }
        }

        assert validSectionId != null : "CRITICAL SETUP ERROR: No sections exist in sandbox!"

        CreateCategoryRequest catReq = new CreateCategoryRequest(
                new Category("Translation Spec Category " + UUID.randomUUID())
                        .setDescription("Category for TranslationClientSpec")
                        .setLocaleAbbreviation(defaultLocale)
        )
        testCategory = adminCategoryClient.createCategory(defaultLocale, catReq).block().category

        CreateSectionRequest secReq = new CreateSectionRequest(
                new Section("Translation Spec Section " + UUID.randomUUID())
                        .setDescription("Section for TranslationClientSpec")
                        .setLocaleAbbreviation(defaultLocale)
        )
        testSection = adminSectionClient.createSection(defaultLocale, testCategory.id, secReq).block().section

        ArticleCreateRequest artReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Translation Spec Article " + UUID.randomUUID())
                        .setBody("Body for TranslationClientSpec")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
        )
        testArticle = adminArticleClient.createArticle(defaultLocale, testSection.id, artReq).block().article
    }

    def cleanupSpec() {
        if (testArticle?.id != null) {
            try {
                adminArticleClient.deleteArticle(defaultLocale, testArticle.id).block()
            } catch (Exception ignored) {
            }
        }
        if (testSection?.id != null) {
            try {
                adminSectionClient.deleteSection(defaultLocale, testSection.id).block()
            } catch (Exception ignored) {
            }
        }
        if (testCategory?.id != null) {
            try {
                adminCategoryClient.deleteCategory(defaultLocale, testCategory.id).block()
            } catch (Exception ignored) {
            }
        }
    }

    def "can use CreateTranslation as an #userType for the '#localeAbbreviation' locale"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given: "an isolated test article created by admin"
        ArticleCreateRequest artReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Base Article for Translation")
                        .setBody("Base Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
        )
        def createResponse = adminArticleClient.createArticle(defaultLocale, validSectionId, artReq).block()
        def createdArticleId = createResponse.article.id

        and: "a new translation request"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(title)
                        .setBody(body)
        )

        when: "creating the translation"
        def response = translationClient.createTranslation("articles", createdArticleId, req).block()

        then:
        noExceptionThrown()
        verifyAll(response.translation) {
            id != null
            locale == localeAbbreviation
        }

        cleanup:
        try {
            adminArticleClient.deleteArticle(defaultLocale, createdArticleId).block()
        } catch (Exception ignored) {
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[adminTranslationClient, "admin"]],
                targetLocales.take(2),
                articleFixtures.getArticles().take(1).collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "cannot use CreateTranslation as an #userType for the '#localeAbbreviation' locale"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given: "an isolated test article created by admin"
        ArticleCreateRequest artReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Base Article for Translation")
                        .setBody("Base Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
        )
        def createResponse = adminArticleClient.createArticle(defaultLocale, validSectionId, artReq).block()
        def createdArticleId = createResponse.article.id

        and: "a new translation request"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(title)
                        .setBody(body)
        )

        when: "creating the translation"
        translationClient.createTranslation("articles", createdArticleId, req).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        cleanup:
        try {
            adminArticleClient.deleteArticle(defaultLocale, createdArticleId).block()
        } catch (Exception ignored) {
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[agentTranslationClient, "agent"], [userTranslationClient, "user"]],
                targetLocales.take(2),
                articleFixtures.getArticles().take(1).collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "can use ShowTranslation and ListTranslations as an admin for the '#localeAbbreviation' locale"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given: "an isolated test article created by admin"
        ArticleCreateRequest artReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Base Article for Translation")
                        .setBody("Base Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
        )
        def createResponse = adminArticleClient.createArticle(defaultLocale, validSectionId, artReq).block()
        def createdArticleId = createResponse.article.id

        and: "a new translation request"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(title)
                        .setBody(body)
        )
        def transResponse = adminTranslationClient.createTranslation("articles", createdArticleId, req).block()

        when: "showing the translation"
        def showResponse = translationClient.showTranslation("articles", createdArticleId, localeAbbreviation).block()

        then:
        noExceptionThrown()
        showResponse.translation.id == transResponse.translation.id

        when: "listing translations"
        def listResponse = translationClient.listTranslations("articles", createdArticleId).block()

        then:
        noExceptionThrown()
        listResponse.translations.size() >= 1

        cleanup:
        try {
            adminArticleClient.deleteArticle(defaultLocale, createdArticleId).block()
        } catch (Exception ignored) {
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[adminTranslationClient, "admin"]],
                targetLocales.take(2),
                articleFixtures.getArticles().take(1).collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "can use UpdateTranslation as an #userType for the '#localeAbbreviation' locale"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given: "an isolated test article created by admin"
        ArticleCreateRequest artReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Base Article for Translation")
                        .setBody("Base Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
        )
        def createResponse = adminArticleClient.createArticle(defaultLocale, validSectionId, artReq).block()
        def createdArticleId = createResponse.article.id

        and: "a new translation"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(title)
                        .setBody(body)
        )
        adminTranslationClient.createTranslation("articles", createdArticleId, req).block()

        when: "updating the translation"
        def updateReq = new TranslationUpdateRequest(
                new Translation()
                        .setTitle(title + " Updated")
        )
        def updateResponse = translationClient.updateTranslation("articles", createdArticleId, localeAbbreviation, updateReq).block()

        then:
        noExceptionThrown()
        updateResponse.translation.title == title + " Updated"

        cleanup:
        try {
            adminArticleClient.deleteArticle(defaultLocale, createdArticleId).block()
        } catch (Exception ignored) {
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[adminTranslationClient, "admin"]],
                targetLocales.take(2),
                articleFixtures.getArticles().take(1).collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "cannot use UpdateTranslation as an #userType for the '#localeAbbreviation' locale"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given: "an isolated test article created by admin"
        ArticleCreateRequest artReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Base Article for Translation")
                        .setBody("Base Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
        )
        def createResponse = adminArticleClient.createArticle(defaultLocale, validSectionId, artReq).block()
        def createdArticleId = createResponse.article.id

        and: "a new translation"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(title)
                        .setBody(body)
        )
        adminTranslationClient.createTranslation("articles", createdArticleId, req).block()

        when: "updating the translation"
        def updateReq = new TranslationUpdateRequest(
                new Translation()
                        .setTitle(title + " Updated")
        )
        translationClient.updateTranslation("articles", createdArticleId, localeAbbreviation, updateReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        cleanup:
        try {
            adminArticleClient.deleteArticle(defaultLocale, createdArticleId).block()
        } catch (Exception ignored) {
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[agentTranslationClient, "agent"], [userTranslationClient, "user"]],
                targetLocales.take(2),
                articleFixtures.getArticles().take(1).collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "can use DeleteTranslation as an #userType for the '#localeAbbreviation' locale"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given: "an isolated test article created by admin"
        ArticleCreateRequest artReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Base Article for Translation")
                        .setBody("Base Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
        )
        def createResponse = adminArticleClient.createArticle(defaultLocale, validSectionId, artReq).block()
        def createdArticleId = createResponse.article.id

        and: "a new translation"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(title)
                        .setBody(body)
        )
        def transResponse = adminTranslationClient.createTranslation("articles", createdArticleId, req).block()
        def createdTranslationId = transResponse.translation.id

        when: "deleting the translation"
        translationClient.deleteTranslation(createdTranslationId).block()

        then:
        noExceptionThrown()

        cleanup:
        try {
            adminArticleClient.deleteArticle(defaultLocale, createdArticleId).block()
        } catch (Exception ignored) {
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[adminTranslationClient, "admin"]],
                targetLocales.take(2),
                articleFixtures.getArticles().take(1).collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "cannot use DeleteTranslation as an #userType for the '#localeAbbreviation' locale"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given: "an isolated test article created by admin"
        ArticleCreateRequest artReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Base Article for Translation")
                        .setBody("Base Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
        )
        def createResponse = adminArticleClient.createArticle(defaultLocale, validSectionId, artReq).block()
        def createdArticleId = createResponse.article.id

        and: "a new translation"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(title)
                        .setBody(body)
        )
        def transResponse = adminTranslationClient.createTranslation("articles", createdArticleId, req).block()
        def createdTranslationId = transResponse.translation.id

        when: "deleting the translation"
        translationClient.deleteTranslation(createdTranslationId).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        cleanup:
        try {
            adminArticleClient.deleteArticle(defaultLocale, createdArticleId).block()
        } catch (Exception ignored) {
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[agentTranslationClient, "agent"], [userTranslationClient, "user"]],
                targetLocales.take(2),
                articleFixtures.getArticles().take(1).collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "can use ListHelpCenterLocales as an #userType"(TranslationClient translationClient, String userType) {
        when: "listing help center locales"
        def response = translationClient.listHelpCenterLocales().block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            locales != null
            !locales.isEmpty()
            defaultLocale != null
        }

        where:
        [translationClient, userType] << [
                [adminTranslationClient, "admin"],
                [agentTranslationClient, "agent"],
                [userTranslationClient, "user"]
        ]
    }

    def "can use ListMissingTranslations as an #userType"(TranslationClient translationClient, String userType) {
        when: "listing missing translations"
        def response = translationClient.listMissingTranslations("articles", testArticle.id).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            locales != null
            !locales.isEmpty()
        }

        where:
        [translationClient, userType] << [
                [adminTranslationClient, "admin"],
                [agentTranslationClient, "agent"]
        ]
    }

    def "cannot use ListMissingTranslations as an #userType"(TranslationClient translationClient, String userType) {
        when: "listing missing translations"
        translationClient.listMissingTranslations("articles", testArticle.id).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        where:
        [translationClient, userType] << [
                [userTranslationClient, "user"],
                [badTokenTranslationClient, "bad token"],
                [badEmailTranslationClient, "bad email"]
        ]
    }

    def "can list missing article translations as #userType"(TranslationClient translationClient, String userType) {
        when: "listing missing article translations"
        MissingTranslationsResponse response = translationClient.listMissingArticleTranslations(testArticle.id).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            locales != null
            !locales.isEmpty()
        }

        where:
        [translationClient, userType] << [
                [adminTranslationClient, "admin"],
                [agentTranslationClient, "agent"]
        ]
    }

    def "cannot list missing article translations as #userType"(TranslationClient translationClient, String userType) {
        when: "listing missing article translations"
        translationClient.listMissingArticleTranslations(testArticle.id).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        where:
        [translationClient, userType] << [
                [userTranslationClient, "user"],
                [badTokenTranslationClient, "bad token"],
                [badEmailTranslationClient, "bad email"]
        ]
    }

    def "can list missing section translations as #userType"(TranslationClient translationClient, String userType) {
        when: "listing missing section translations"
        MissingTranslationsResponse response = translationClient.listMissingSectionTranslations(testSection.id).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            locales != null
            !locales.isEmpty()
        }

        where:
        [translationClient, userType] << [
                [adminTranslationClient, "admin"],
                [agentTranslationClient, "agent"]
        ]
    }

    def "cannot list missing section translations as #userType"(TranslationClient translationClient, String userType) {
        when: "listing missing section translations"
        translationClient.listMissingSectionTranslations(testSection.id).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        where:
        [translationClient, userType] << [
                [userTranslationClient, "user"],
                [badTokenTranslationClient, "bad token"],
                [badEmailTranslationClient, "bad email"]
        ]
    }

    def "can list missing category translations as #userType"(TranslationClient translationClient, String userType) {
        when: "listing missing category translations"
        MissingTranslationsResponse response = translationClient.listMissingCategoryTranslations(testCategory.id).block()

        then:
        noExceptionThrown()
        verifyAll(response) {
            locales != null
            !locales.isEmpty()
        }

        where:
        [translationClient, userType] << [
                [adminTranslationClient, "admin"],
                [agentTranslationClient, "agent"]
        ]
    }

    def "cannot list missing category translations as #userType"(TranslationClient translationClient, String userType) {
        when: "listing missing category translations"
        translationClient.listMissingCategoryTranslations(testCategory.id).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        where:
        [translationClient, userType] << [
                [userTranslationClient, "user"],
                [badTokenTranslationClient, "bad token"],
                [badEmailTranslationClient, "bad email"]
        ]
    }

    def "can list and show article translations as #userType"(TranslationClient translationClient, String userType) {
        when: "listing article translations"
        TranslationsResponse listResp = translationClient.listArticleTranslations(testArticle.id).block()

        and: "showing article translation"
        TranslationResponse showResp = translationClient.showArticleTranslation(testArticle.id, defaultLocale).block()

        then:
        noExceptionThrown()
        verifyAll {
            listResp?.translations != null
            !listResp.translations.isEmpty()
            showResp?.translation != null
            showResp.translation.locale == defaultLocale
        }

        where:
        [translationClient, userType] << [
                [adminTranslationClient, "admin"],
                [agentTranslationClient, "agent"],
                [userTranslationClient, "user"]
        ]
    }

    def "can create and update article translation as #userType for '#localeAbbreviation'"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation) {
        given: "a translation request"
        String initialTitle = "Article Translation " + UUID.randomUUID()
        String updatedTitle = "Updated Article Translation " + UUID.randomUUID()
        TranslationCreateRequest createReq = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(initialTitle)
                        .setBody("Article translation body")
        )
        Long translationId = null

        when: "creating article translation"
        TranslationResponse created = translationClient.createArticleTranslation(testArticle.id, createReq).block()
        translationId = created?.translation?.id

        and: "updating article translation"
        TranslationUpdateRequest updateReq = new TranslationUpdateRequest(
                new Translation().setTitle(updatedTitle)
        )
        TranslationResponse updated = translationClient.updateArticleTranslation(testArticle.id, localeAbbreviation, updateReq).block()

        then:
        noExceptionThrown()
        verifyAll {
            created?.translation != null
            created.translation.id != null
            created.translation.locale == localeAbbreviation
            updated?.translation != null
            updated.translation.title == updatedTitle
        }

        cleanup:
        if (translationId != null) {
            try {
                adminTranslationClient.deleteTranslation(translationId).block()
            } catch (Exception ignored) {
            }
        }

        where:
        [[translationClient, userType], localeAbbreviation] << [
                [[adminTranslationClient, "admin"]],
                targetLocales.take(1)
        ].combinations()
    }

    def "cannot create article translation as #userType for '#localeAbbreviation'"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation) {
        given: "a translation request"
        TranslationCreateRequest createReq = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle("Unauthorized Article Translation")
                        .setBody("Unauthorized body")
        )

        when: "creating article translation"
        translationClient.createArticleTranslation(testArticle.id, createReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        where:
        [[translationClient, userType], localeAbbreviation] << [
                [[agentTranslationClient, "agent"], [userTranslationClient, "user"], [badTokenTranslationClient, "bad token"], [badEmailTranslationClient, "bad email"]],
                targetLocales.take(1)
        ].combinations()
    }

    def "cannot update article translation as #userType"(TranslationClient translationClient, String userType) {
        given: "an update request"
        TranslationUpdateRequest updateReq = new TranslationUpdateRequest(
                new Translation().setTitle("Unauthorized Article Update")
        )

        when: "updating article translation"
        translationClient.updateArticleTranslation(testArticle.id, defaultLocale, updateReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        where:
        [translationClient, userType] << [
                [agentTranslationClient, "agent"],
                [userTranslationClient, "user"],
                [badTokenTranslationClient, "bad token"],
                [badEmailTranslationClient, "bad email"]
        ]
    }

    def "can list and show section translations as #userType"(TranslationClient translationClient, String userType) {
        when: "listing section translations"
        TranslationsResponse listResp = translationClient.listSectionTranslations(testSection.id).block()

        and: "showing section translation"
        TranslationResponse showResp = translationClient.showSectionTranslation(testSection.id, defaultLocale).block()

        then:
        noExceptionThrown()
        verifyAll {
            listResp?.translations != null
            !listResp.translations.isEmpty()
            showResp?.translation != null
            showResp.translation.locale == defaultLocale
        }

        where:
        [translationClient, userType] << [
                [adminTranslationClient, "admin"],
                [agentTranslationClient, "agent"],
                [userTranslationClient, "user"]
        ]
    }

    def "can create and update section translation as #userType for '#localeAbbreviation'"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation) {
        given: "a translation request"
        String initialTitle = "Section Translation " + UUID.randomUUID()
        String updatedTitle = "Updated Section Translation " + UUID.randomUUID()
        TranslationCreateRequest createReq = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(initialTitle)
                        .setBody("Section translation body")
        )
        Long translationId = null

        when: "creating section translation"
        TranslationResponse created = translationClient.createSectionTranslation(testSection.id, createReq).block()
        translationId = created?.translation?.id

        and: "updating section translation"
        TranslationUpdateRequest updateReq = new TranslationUpdateRequest(
                new Translation().setTitle(updatedTitle)
        )
        TranslationResponse updated = translationClient.updateSectionTranslation(testSection.id, localeAbbreviation, updateReq).block()

        then:
        noExceptionThrown()
        verifyAll {
            created?.translation != null
            created.translation.id != null
            created.translation.locale == localeAbbreviation
            updated?.translation != null
            updated.translation.title == updatedTitle
        }

        cleanup:
        if (translationId != null) {
            try {
                adminTranslationClient.deleteTranslation(translationId).block()
            } catch (Exception ignored) {
            }
        }

        where:
        [[translationClient, userType], localeAbbreviation] << [
                [[adminTranslationClient, "admin"]],
                targetLocales.take(1)
        ].combinations()
    }

    def "cannot create section translation as #userType for '#localeAbbreviation'"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation) {
        given: "a translation request"
        TranslationCreateRequest createReq = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle("Unauthorized Section Translation")
                        .setBody("Unauthorized body")
        )

        when: "creating section translation"
        translationClient.createSectionTranslation(testSection.id, createReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        where:
        [[translationClient, userType], localeAbbreviation] << [
                [[agentTranslationClient, "agent"], [userTranslationClient, "user"], [badTokenTranslationClient, "bad token"], [badEmailTranslationClient, "bad email"]],
                targetLocales.take(1)
        ].combinations()
    }

    def "cannot update section translation as #userType"(TranslationClient translationClient, String userType) {
        given: "an update request"
        TranslationUpdateRequest updateReq = new TranslationUpdateRequest(
                new Translation().setTitle("Unauthorized Section Update")
        )

        when: "updating section translation"
        translationClient.updateSectionTranslation(testSection.id, defaultLocale, updateReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        where:
        [translationClient, userType] << [
                [agentTranslationClient, "agent"],
                [userTranslationClient, "user"],
                [badTokenTranslationClient, "bad token"],
                [badEmailTranslationClient, "bad email"]
        ]
    }

    def "can list and show category translations as #userType"(TranslationClient translationClient, String userType) {
        when: "listing category translations"
        TranslationsResponse listResp = translationClient.listCategoryTranslations(testCategory.id).block()

        and: "showing category translation"
        TranslationResponse showResp = translationClient.showCategoryTranslation(testCategory.id, defaultLocale).block()

        then:
        noExceptionThrown()
        verifyAll {
            listResp?.translations != null
            !listResp.translations.isEmpty()
            showResp?.translation != null
            showResp.translation.locale == defaultLocale
        }

        where:
        [translationClient, userType] << [
                [adminTranslationClient, "admin"],
                [agentTranslationClient, "agent"],
                [userTranslationClient, "user"]
        ]
    }

    def "can create and update category translation as #userType for '#localeAbbreviation'"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation) {
        given: "a translation request"
        String initialTitle = "Category Translation " + UUID.randomUUID()
        String updatedTitle = "Updated Category Translation " + UUID.randomUUID()
        TranslationCreateRequest createReq = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(initialTitle)
                        .setBody("Category translation body")
        )
        Long translationId = null

        when: "creating category translation"
        TranslationResponse created = translationClient.createCategoryTranslation(testCategory.id, createReq).block()
        translationId = created?.translation?.id

        and: "updating category translation"
        TranslationUpdateRequest updateReq = new TranslationUpdateRequest(
                new Translation().setTitle(updatedTitle)
        )
        TranslationResponse updated = translationClient.updateCategoryTranslation(testCategory.id, localeAbbreviation, updateReq).block()

        then:
        noExceptionThrown()
        verifyAll {
            created?.translation != null
            created.translation.id != null
            created.translation.locale == localeAbbreviation
            updated?.translation != null
            updated.translation.title == updatedTitle
        }

        cleanup:
        if (translationId != null) {
            try {
                adminTranslationClient.deleteTranslation(translationId).block()
            } catch (Exception ignored) {
            }
        }

        where:
        [[translationClient, userType], localeAbbreviation] << [
                [[adminTranslationClient, "admin"]],
                targetLocales.take(1)
        ].combinations()
    }

    def "cannot create category translation as #userType for '#localeAbbreviation'"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation) {
        given: "a translation request"
        TranslationCreateRequest createReq = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle("Unauthorized Category Translation")
                        .setBody("Unauthorized body")
        )

        when: "creating category translation"
        translationClient.createCategoryTranslation(testCategory.id, createReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        where:
        [[translationClient, userType], localeAbbreviation] << [
                [[agentTranslationClient, "agent"], [userTranslationClient, "user"], [badTokenTranslationClient, "bad token"], [badEmailTranslationClient, "bad email"]],
                targetLocales.take(1)
        ].combinations()
    }

    def "cannot update category translation as #userType"(TranslationClient translationClient, String userType) {
        given: "an update request"
        TranslationUpdateRequest updateReq = new TranslationUpdateRequest(
                new Translation().setTitle("Unauthorized Category Update")
        )

        when: "updating category translation"
        translationClient.updateCategoryTranslation(testCategory.id, defaultLocale, updateReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        where:
        [translationClient, userType] << [
                [agentTranslationClient, "agent"],
                [userTranslationClient, "user"],
                [badTokenTranslationClient, "bad token"],
                [badEmailTranslationClient, "bad email"]
        ]
    }
}
