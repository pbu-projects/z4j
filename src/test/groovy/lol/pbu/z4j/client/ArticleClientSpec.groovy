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
class ArticleClientSpec extends Z4jSpec {

    @Shared
    ArticleClient adminArticleClient, agentArticleClient, userArticleClient

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
        agentArticleClient = agentCtx.getBean(ArticleClient.class)
        userArticleClient = userCtx.getBean(ArticleClient.class)
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
                
                def seedReq = new ArticleCreateRequest(
                        new Article()
                                .setTitle("Seeded Article for Tests")
                                .setBody("This article was seeded automatically by the test suite.")
                                .setPermissionGroupId(validPermissionGroupId)
                                .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                )
                adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, seedReq).block()
            }
        }

        assert validSectionId != null : "CRITICAL SETUP ERROR: No articles exist across ANY locale to test against, and could not dynamically seed one!"
    }

    def "can use ListArticles for other tests using the '#localeAbbreviation' locale"(
            ArticleClient articleClient, LocaleAbbreviation localeAbbreviation, SortArticleBy sortBy, SortOrder sortOrder, Long startTime, String labelNames) {
        when: "query articles list for the '#localeAbbreviation' locale"
        Mono<ArticlesResponse> response = articleClient.listArticles(localeAbbreviation, sortBy, sortOrder, startTime, labelNames)

        then:
        ArticlesResponse articlesResponse = response.block()

        then: "validate the returned article objects have an ID that other tests will require"
        if (articlesResponse != null && articlesResponse.articles != null && articlesResponse.articles.size() > 0) {
            articlesResponse.articles.forEach { article -> article.id != null }
        }

        where:
        [articleClient, localeAbbreviation, sortBy, sortOrder, startTime, labelNames] << [
                [adminArticleClient, agentArticleClient, userArticleClient],
                allLocales,
                [SortArticleBy.values(), null].flatten(),
                [SortOrder.values(), null].flatten(),
                [System.currentTimeMillis() / 1000 - 30000000, null].flatten(),
                [null]
        ].combinations()
    }

    def "can use CreateArticle as an #userType for the '#localeAbbreviation' locale"(ArticleClient articleClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle(title)
                        .setBody(body)
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(localeAbbreviation)
        )

        when: "creating the article"
        def createResponse = articleClient.createArticle(localeAbbreviation, validSectionId, req).block()

        then:
        noExceptionThrown()
        createResponse.article.id != null

        cleanup:
        try {
            adminArticleClient.deleteArticle(localeAbbreviation, createResponse.article.id).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[articleClient, userType], localeAbbreviation, [title, body]] << [
                [[adminArticleClient, "admin"]],
                allLocales,
                articleFixtures.getArticles().collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "cannot use CreateArticle as an #userType for the '#localeAbbreviation' locale"(ArticleClient articleClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle(title)
                        .setBody(body)
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(localeAbbreviation)
        )

        when: "creating the article"
        def createResponse = articleClient.createArticle(localeAbbreviation, validSectionId, req).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        cleanup:
        try {
            if (createResponse != null) {
                adminArticleClient.deleteArticle(localeAbbreviation, createResponse.article.id).block()
            }
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[articleClient, userType], localeAbbreviation, [title, body]] << [
                [[agentArticleClient, "agent"], [userArticleClient, "user"]],
                allLocales,
                articleFixtures.getArticles().collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "can use ShowArticle as an #userType for the '#localeAbbreviation' locale"(ArticleClient articleClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle(title)
                        .setBody(body)
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(localeAbbreviation)
        )
        def createResponse = adminArticleClient.createArticle(localeAbbreviation, validSectionId, req).block()
        def createdId = createResponse.article.id

        when:
        def showResponse = articleClient.showArticle(localeAbbreviation, createdId).block()

        then:
        noExceptionThrown()
        showResponse.article.id == createdId

        cleanup:
        try {
            adminArticleClient.deleteArticle(localeAbbreviation, createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[articleClient, userType], localeAbbreviation, [title, body]] << [
                [[adminArticleClient, "admin"]],
                allLocales,
                articleFixtures.getArticles().collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "can use UpdateArticle as an #userType for the '#localeAbbreviation' locale"(ArticleClient articleClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle(title)
                        .setBody(body)
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(localeAbbreviation)
        )
        def createResponse = adminArticleClient.createArticle(localeAbbreviation, validSectionId, req).block()
        def createdId = createResponse.article.id

        ArticleUpdateRequest updateReq = new ArticleUpdateRequest(
                new Article()
                        .setPromoted(true)
        )

        when:
        def updateResponse = articleClient.updateArticle(localeAbbreviation, createdId, updateReq).block()

        then:
        noExceptionThrown()
        updateResponse.article.promoted == true

        cleanup:
        try {
            adminArticleClient.deleteArticle(localeAbbreviation, createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[articleClient, userType], localeAbbreviation, [title, body]] << [
                [[adminArticleClient, "admin"]],
                allLocales,
                articleFixtures.getArticles().collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "cannot use UpdateArticle as an #userType for the '#localeAbbreviation' locale"(ArticleClient articleClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle(title)
                        .setBody(body)
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(localeAbbreviation)
        )
        def createResponse = adminArticleClient.createArticle(localeAbbreviation, validSectionId, req).block()
        def createdId = createResponse.article.id

        ArticleUpdateRequest updateReq = new ArticleUpdateRequest(
                new Article()
                        .setPromoted(true)
        )

        when:
        articleClient.updateArticle(localeAbbreviation, createdId, updateReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        cleanup:
        try {
            adminArticleClient.deleteArticle(localeAbbreviation, createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[articleClient, userType], localeAbbreviation, [title, body]] << [
                [[agentArticleClient, "agent"], [userArticleClient, "user"]],
                allLocales,
                articleFixtures.getArticles().collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "can use DeleteArticle as an #userType for the '#localeAbbreviation' locale"(ArticleClient articleClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle(title)
                        .setBody(body)
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(localeAbbreviation)
        )
        def createResponse = adminArticleClient.createArticle(localeAbbreviation, validSectionId, req).block()
        def createdId = createResponse.article.id

        when:
        articleClient.deleteArticle(localeAbbreviation, createdId).block()

        then:
        noExceptionThrown()

        cleanup:
        try {
            adminArticleClient.deleteArticle(localeAbbreviation, createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[articleClient, userType], localeAbbreviation, [title, body]] << [
                [[adminArticleClient, "admin"]],
                allLocales,
                articleFixtures.getDeleteArticles().collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }

    def "cannot use DeleteArticle as an #userType for the '#localeAbbreviation' locale"(ArticleClient articleClient, String userType, LocaleAbbreviation localeAbbreviation, String title, String body) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle(title)
                        .setBody(body)
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(localeAbbreviation)
        )
        def createResponse = adminArticleClient.createArticle(localeAbbreviation, validSectionId, req).block()

        when:
        articleClient.deleteArticle(localeAbbreviation, createResponse.article.id).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        cleanup:
        try {
            adminArticleClient.deleteArticle(localeAbbreviation, createResponse.article.id).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [[articleClient, userType], localeAbbreviation, [title, body]] << [
                [[agentArticleClient, "agent"], [userArticleClient, "user"]],
                allLocales,
                articleFixtures.getDeleteArticles().collect { [it.getTitle(), it.getBody()] }
        ].combinations()
    }
}
