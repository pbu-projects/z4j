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
import reactor.core.publisher.Mono
import spock.lang.Shared

@MicronautTest
class TranslationClientSpec extends Z4jSpec {

    @Shared
    ArticleClient adminArticleClient

    @Shared
    TranslationClient adminTranslationClient, agentTranslationClient, userTranslationClient

    @Shared
    List<LocaleAbbreviation> allLocales

    @Shared
    ArticleFixtures articleFixtures

    @Shared
    Long validSectionId
    
    @Shared
    Long validPermissionGroupId

    def setupSpec() {
        adminArticleClient = adminCtx.getBean(ArticleClient.class)
        adminTranslationClient = adminCtx.getBean(TranslationClient.class)
        agentTranslationClient = agentCtx.getBean(TranslationClient.class)
        userTranslationClient = userCtx.getBean(TranslationClient.class)
        allLocales = userCtx.getBean(LocaleClient.class).listLocales().block().locales.collect { it.localeAbbreviation }
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
    }

    def "can use CreateTranslation as an #userType for the '#localeAbbreviation' locale"(TranslationClient translationClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given: "an isolated test article created by admin"
        ArticleCreateRequest artReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Base Article for Translation")
                        .setBody("Base Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, artReq).block()
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
        response.translation.id != null

        cleanup:
        try {
            adminArticleClient.deleteArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdArticleId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[adminTranslationClient, "admin"]],
                allLocales.findAll { it != LocaleAbbreviation.ENGLISH_UNITED_STATES }.take(2),
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
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, artReq).block()
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
            adminArticleClient.deleteArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdArticleId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[agentTranslationClient, "agent"], [userTranslationClient, "user"]],
                allLocales.findAll { it != LocaleAbbreviation.ENGLISH_UNITED_STATES }.take(2),
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
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, artReq).block()
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
            adminArticleClient.deleteArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdArticleId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[adminTranslationClient, "admin"]],
                allLocales.findAll { it != LocaleAbbreviation.ENGLISH_UNITED_STATES }.take(2),
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
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, artReq).block()
        def createdArticleId = createResponse.article.id
        
        and: "a new translation"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(title)
                        .setBody(body)
        )
        def transResponse = adminTranslationClient.createTranslation("articles", createdArticleId, req).block()

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
            adminArticleClient.deleteArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdArticleId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[adminTranslationClient, "admin"]],
                allLocales.findAll { it != LocaleAbbreviation.ENGLISH_UNITED_STATES }.take(2),
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
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, artReq).block()
        def createdArticleId = createResponse.article.id
        
        and: "a new translation"
        def req = new TranslationCreateRequest(
                new Translation()
                        .setLocale(localeAbbreviation)
                        .setTitle(title)
                        .setBody(body)
        )
        def transResponse = adminTranslationClient.createTranslation("articles", createdArticleId, req).block()

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
            adminArticleClient.deleteArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdArticleId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[agentTranslationClient, "agent"], [userTranslationClient, "user"]],
                allLocales.findAll { it != LocaleAbbreviation.ENGLISH_UNITED_STATES }.take(2),
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
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, artReq).block()
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
            adminArticleClient.deleteArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdArticleId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[adminTranslationClient, "admin"]],
                allLocales.findAll { it != LocaleAbbreviation.ENGLISH_UNITED_STATES }.take(2),
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
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, artReq).block()
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
            adminArticleClient.deleteArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdArticleId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[translationClient, userType], localeAbbreviation, [title, body]] << [
                [[agentTranslationClient, "agent"], [userTranslationClient, "user"]],
                allLocales.findAll { it != LocaleAbbreviation.ENGLISH_UNITED_STATES }.take(2),
                articleFixtures.getArticles().take(1).collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }
}
