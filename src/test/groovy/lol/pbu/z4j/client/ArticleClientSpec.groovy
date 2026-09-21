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
        if (articlesResponse.articles.size() > 0) {
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
    def "can create, show, update, and delete an article"() {
        given: "an existing section and permission group from an existing article"
        def existingArticles = adminArticleClient.listArticles(LocaleAbbreviation.ENGLISH_UNITED_STATES, null, null, null, null).block().articles
        if (existingArticles == null || existingArticles.isEmpty()) throw new IllegalStateException("CRITICAL SETUP ERROR: No articles exist to test against! Contributors MUST configure their Help Center sandbox with articles and ensure another locale is added to the Help Center in order for integration tests to pass.")
        
        def sectionId = existingArticles.get(0).sectionId
        def permissionGroupId = existingArticles.get(0).permissionGroupId

        and: "a new article request"
        def articleReq = new lol.pbu.z4j.model.ArticleCreateRequest(
                new lol.pbu.z4j.model.Article()
                        .setTitle("Test Article")
                        .setBody("Test Body")
                        .setPermissionGroupId(permissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )

        when: "creating the article"
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, sectionId, articleReq).block()
        def createdArticle = createResponse.article
        def createdId = createdArticle?.id

        then: "it should be created"
        createdId != null
        createdArticle.title == "Test Article"

        when: "showing the article"
        def showResponse = adminArticleClient.showArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdId).block()

        then: "it should return the same article"
        showResponse.article.id == createdId
        showResponse.article.title == "Test Article"

        when: "updating the article"
        def updateReq = new lol.pbu.z4j.model.ArticleUpdateRequest(
                new lol.pbu.z4j.model.Article().setTitle("Updated Test Article")
        )
        def updateResponse = adminArticleClient.updateArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdId, updateReq).block()

        then: "it should be updated"
        updateResponse.article.title == "Updated Test Article"

        cleanup: "delete the article even if assertions failed"
        if (createdId != null) {
            try {
                adminArticleClient.deleteArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, createdId).block()
            } catch (Exception e) {
            }
        }
    }

}
