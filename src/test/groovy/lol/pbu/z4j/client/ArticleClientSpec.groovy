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

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.ArticlesResponse
import lol.pbu.z4j.model.LocaleAbbreviation
import lol.pbu.z4j.model.SortArticleBy
import lol.pbu.z4j.model.SortOrder
import reactor.core.publisher.Mono
import spock.lang.Shared


@MicronautTest
class ArticleClientSpec extends Z4jSpec {

    @Shared
    ArticleClient adminArticleClient, agentArticleClient, userArticleClient

    @Shared
    List<LocaleAbbreviation> allLocales

    def setupSpec() {
        adminArticleClient = adminCtx.getBean(ArticleClient.class)
        agentArticleClient = agentCtx.getBean(ArticleClient.class)
        userArticleClient = userCtx.getBean(ArticleClient.class)
        allLocales = userCtx.getBean(LocaleClient.class).listLocales().block().locales.collect { it.localeAbbreviation }
    }

    def "can use ListArticles for other tests using the '#localeAbbreviation' locale"(
            ArticleClient articleClient, LocaleAbbreviation localeAbbreviation, SortArticleBy sortBy, SortOrder sortOrder, Long startTime, String labelNames) {
        // https://github.com/PeanutButter-Unicorn/z4j/issues/31
        when: "query articles list for the '#localeAbbreviation' locale"
        Mono<ArticlesResponse> response = articleClient.listArticles(localeAbbreviation, sortBy, sortOrder, startTime, labelNames)

        then:
        ArticlesResponse articlesResponse = response.block()

        then: "validate the returned article objects have an ID that other tests will require"
        if (articlesResponse != null && articlesResponse.articles != null && articlesResponse.articles.size() > 0) {
            // this tests ability needed in https://github.com/PeanutButter-Unicorn/z4j/issues/30
            articlesResponse.articles.forEach { article -> article.id != null }
        }

        where:
        [articleClient, localeAbbreviation, sortBy, sortOrder, startTime, labelNames] << [
                [adminArticleClient, agentArticleClient, userArticleClient],
                allLocales,
                [SortArticleBy.values(), null].flatten(),
                [SortOrder.values(), null].flatten(),
                [System.currentTimeMillis() / 1000 - 30000000, null].flatten(),
                [null] //TODO add article labels and validate 1 or more labels can be passed
        ].combinations()
    }
    def "can create, show, update, and delete an article in #locale"() {
        given: "a valid sectionId and permissionGroupId found from existing articles across all locales"
        Long validSectionId = null
        Long validPermissionGroupId = null

        for (LocaleAbbreviation loc : allLocales) {
            def articles = adminArticleClient.listArticles(loc, null, null, null, null).block()?.articles
            if (articles != null && !articles.isEmpty()) {
                validSectionId = articles.get(0).sectionId
                validPermissionGroupId = articles.get(0).permissionGroupId
                break
            }
        }

        if (validSectionId == null) {
            System.out.println("No articles found! Seeding via raw HTTP...")
            def auth = "Basic " + (System.getenv("Z4J_ADMIN_EMAIL") + "/token:" + System.getenv("Z4J_TOKEN")).bytes.encodeBase64().toString()
            
            def sectionsConn = new URL(System.getenv("Z4J_URL") + "/api/v2/help_center/en-us/sections.json").openConnection()
            sectionsConn.setRequestProperty("Authorization", auth)
            sectionsConn.setRequestProperty("Accept", "application/json")
            def sectionsJson = new com.fasterxml.jackson.databind.ObjectMapper().readValue(sectionsConn.inputStream, Map.class)
            
            def pgConn = new URL(System.getenv("Z4J_URL") + "/api/v2/guide/permission_groups.json").openConnection()
            pgConn.setRequestProperty("Authorization", auth)
            pgConn.setRequestProperty("Accept", "application/json")
            def pgJson = new com.fasterxml.jackson.databind.ObjectMapper().readValue(pgConn.inputStream, Map.class)
            
            if (sectionsJson.sections && pgJson.permission_groups) {
                validSectionId = sectionsJson.sections[0].id as Long
                validPermissionGroupId = pgJson.permission_groups[0].id as Long
                
                // Seed an article so future tests (like TranslationClientSpec) don't fail
                def seedReq = new lol.pbu.z4j.model.ArticleCreateRequest(
                        new lol.pbu.z4j.model.Article()
                                .setTitle("Seeded Article for Tests")
                                .setBody("This article was seeded automatically by the test suite.")
                                .setPermissionGroupId(validPermissionGroupId)
                                .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                )
                adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, seedReq).block()
            }
        }

        assert validSectionId != null : "CRITICAL SETUP ERROR: No articles exist across ANY locale to test against, and could not dynamically seed one!"

        and: "a new article request"
        def articleReq = new lol.pbu.z4j.model.ArticleCreateRequest(
                new lol.pbu.z4j.model.Article()
                        .setTitle("Test Article in " + locale.getValue())
                        .setBody("Test Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(locale)
        )

        when: "creating the article"
        def createResponse = adminArticleClient.createArticle(locale, validSectionId, articleReq).block()
        def createdArticle = createResponse.article
        def createdId = createdArticle?.id

        then: "it should be created"
        createdId != null
        createdArticle.title == "Test Article in " + locale.getValue()

        when: "showing the article"
        def showResponse = adminArticleClient.showArticle(locale, createdId).block()

        then: "it should return the same article"
        showResponse.article.id == createdId
        showResponse.article.title == "Test Article in " + locale.getValue()

        when: "updating the article metadata"
        def updateReq = new lol.pbu.z4j.model.ArticleUpdateRequest(
                new lol.pbu.z4j.model.Article()
                        .setPromoted(true)
                        .setTitle("Test Title Ignored By Update API")
                        .setLocaleAbbreviation(locale)
                        .setPermissionGroupId(validPermissionGroupId)
        )
        def updateResponse = adminArticleClient.updateArticle(locale, createdId, updateReq).block()

        then: "metadata should be updated"
        updateResponse.article.promoted == true

        cleanup: "delete the article even if assertions failed"
        if (createdId != null) {
            try {
                adminArticleClient.deleteArticle(locale, createdId).block()
            } catch (Exception e) {
            }
        }
        
        where:
        locale << allLocales
    }

}
