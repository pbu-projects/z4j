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
import reactor.test.StepVerifier
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

    @Shared
    LocaleAbbreviation validLocale

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
                validLocale = loc
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
                validLocale = LocaleAbbreviation.ENGLISH_UNITED_STATES
            }
        }

        if (validLocale == null) {
            validLocale = allLocales ? allLocales[0] : LocaleAbbreviation.ENGLISH_UNITED_STATES
        }

        assert validSectionId != null: "CRITICAL SETUP ERROR: No articles exist across ANY locale to test against, and could not dynamically seed one!"
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

    def "can use associateAttachmentsInBulkNoLocale as an #userType"(ArticleClient articleClient, String userType) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article for Bulk Attachments " + UUID.randomUUID())
                        .setBody("Body for Bulk Attachments")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id

        BulkAttachmentsRequest bulkReq = new BulkAttachmentsRequest(List.of(123456789L))

        when:
        articleClient.associateAttachmentsInBulkNoLocale(createdId, bulkReq).block()

        then:
        noExceptionThrown()

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [articleClient, userType] << [
                [adminArticleClient, "admin"]
        ]
    }

    def "cannot use associateAttachmentsInBulkNoLocale as an #userType"(ArticleClient articleClient, String userType) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article for Bulk Attachments " + UUID.randomUUID())
                        .setBody("Body for Bulk Attachments")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id

        BulkAttachmentsRequest bulkReq = new BulkAttachmentsRequest(List.of(123456789L))

        when:
        articleClient.associateAttachmentsInBulkNoLocale(createdId, bulkReq).block()

        then:
        HttpClientResponseException error = thrown(HttpClientResponseException)
        error.getStatus().getCode() >= 400

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [articleClient, userType] << [
                [agentArticleClient, "agent"],
                [userArticleClient, "user"]
        ]
    }

    def "can use archiveArticle without locale as an #userType"(ArticleClient articleClient, String userType) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article to Archive " + UUID.randomUUID())
                        .setBody("Body of article to archive")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id

        expect:
        StepVerifier.create(articleClient.archiveArticle(createdId))
                .verifyComplete()
        StepVerifier.create(adminArticleClient.showArticle(createdId))
                .verifyComplete()

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [articleClient, userType] << [
                [adminArticleClient, "admin"]
        ]
    }

    def "cannot use archiveArticle without locale as an #userType"(ArticleClient articleClient, String userType) {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article to Archive " + UUID.randomUUID())
                        .setBody("Body of article to archive")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id

        expect:
        StepVerifier.create(articleClient.archiveArticle(createdId))
                .verifyErrorSatisfies { error ->
                    verifyAll {
                        error instanceof HttpClientResponseException
                        ((HttpClientResponseException) error).status.code >= 400
                    }
                }

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }

        where:
        [articleClient, userType] << [
                [agentArticleClient, "agent"],
                [userArticleClient, "user"]
        ]
    }

    def "can use listArticles with cursor pagination"() {
        expect: "listing articles with cursor pagination"
        StepVerifier.create(adminArticleClient.listArticles(validLocale, (String) null, 10))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                        if (!articles.isEmpty()) {
                            meta != null
                        }
                    }
                }
                .verifyComplete()
    }

    def "can use listArticles with single locale argument"() {
        expect: "listing articles specifying only locale"
        StepVerifier.create(adminArticleClient.listArticles(validLocale))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                    }
                }
                .verifyComplete()
    }

    def "can use listArticlesNoLocale with cursor pagination"() {
        expect: "listing articles across all locales with cursor pagination"
        StepVerifier.create(adminArticleClient.listArticlesNoLocale((String) null, 10))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                        if (!articles.isEmpty()) {
                            meta != null
                        }
                    }
                }
                .verifyComplete()
    }

    def "can use listArticles with offset pagination"() {
        expect: "listing articles using offset pagination"
        StepVerifier.create(adminArticleClient.listArticles(validLocale, null, null, null, null, null, null, null, 1, 10))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                        if (!articles.isEmpty()) {
                            page != null || meta != null
                        }
                    }
                }
                .verifyComplete()
    }

    def "can use listArticles with offset pagination convenience overload"() {
        expect: "listing articles using offset pagination convenience overload"
        StepVerifier.create(adminArticleClient.listArticles(validLocale, 1, 10))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                        if (!articles.isEmpty()) {
                            page != null || meta != null
                        }
                    }
                }
                .verifyComplete()
    }

    def "can use listArticlesNoLocale without parameters"() {
        expect: "listing articles across all locales without parameters"
        StepVerifier.create(adminArticleClient.listArticlesNoLocale())
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                    }
                }
                .verifyComplete()
    }

    def "can use listArticlesNoLocale with offset pagination"() {
        expect: "listing articles across all locales with offset pagination"
        StepVerifier.create(adminArticleClient.listArticlesNoLocale(1, 10))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                        if (!articles.isEmpty()) {
                            page != null || meta != null
                        }
                    }
                }
                .verifyComplete()
    }

    def "support portal can browse articles sorted by position with cursor pagination"() {
        expect: "support portal retrieves articles sorted by position using cursor pagination within a locale"
        StepVerifier.create(adminArticleClient.listArticles(validLocale, SortArticleBy.POSITION, SortOrder.ASCENDING, (Long) null, (String) null, (String) null, 10))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                        if (!articles.isEmpty()) {
                            meta != null
                        }
                    }
                }
                .verifyComplete()
    }

    def "global audit service can list all articles sorted by updated date across locales"() {
        expect: "global audit service queries articles across all locales ordered by updated date"
        StepVerifier.create(adminArticleClient.listArticlesNoLocale(SortArticleBy.UPDATED_AT, SortOrder.DESCENDING, (Long) null, (String) null))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                    }
                }
                .verifyComplete()
    }

    def "knowledge base sync service can paginate through all articles globally with cursor pagination"() {
        expect: "sync service fetches pages of articles globally sorted by updated date using cursor pagination"
        StepVerifier.create(adminArticleClient.listArticlesNoLocale(SortArticleBy.UPDATED_AT, SortOrder.ASCENDING, (Long) null, (String) null, (String) null, 10))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                        if (!articles.isEmpty()) {
                            meta != null
                        }
                    }
                }
                .verifyComplete()
    }

    def "content analytics dashboard can query all articles globally with offset pagination and sorting"() {
        expect: "analytics dashboard requests articles globally using offset pagination and sorting options"
        StepVerifier.create(adminArticleClient.listArticlesNoLocale(SortArticleBy.CREATED_AT, SortOrder.ASCENDING, (Long) null, (String) null, (String) null, (String) null, (Integer) null, 1, 10))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                        if (!articles.isEmpty()) {
                            page != null || meta != null
                        }
                    }
                }
                .verifyComplete()
    }

    def "backup job can incrementally list articles modified since timestamp"() {
        given: "a timestamp from the past 24 hours"
        Long oneDayAgo = (System.currentTimeMillis() / 1000L) - 86400L

        expect: "backup job queries articles updated since the timestamp"
        StepVerifier.create(adminArticleClient.listArticles(validLocale, SortArticleBy.UPDATED_AT, SortOrder.ASCENDING, oneDayAgo, (String) null))
                .assertNext { response ->
                    verifyAll(response) {
                        articles != null
                    }
                }
                .verifyComplete()
    }

    def "can use showArticle without locale"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article for Show No Locale " + UUID.randomUUID())
                        .setBody("Body for show article without locale")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id

        expect: "fetching article using convenience method showArticle without locale"
        StepVerifier.create(adminArticleClient.showArticle(createdId))
                .assertNext { showResponse ->
                    verifyAll(showResponse) {
                        article != null
                        article.id == createdId
                    }
                }
                .verifyComplete()

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "can use showArticleNoLocale"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article for ShowNoLocale " + UUID.randomUUID())
                        .setBody("Body for showArticleNoLocale")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id

        expect: "fetching article using showArticleNoLocale"
        StepVerifier.create(adminArticleClient.showArticleNoLocale(createdId))
                .assertNext { showResponse ->
                    verifyAll(showResponse) {
                        article != null
                        article.id == createdId
                    }
                }
                .verifyComplete()

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "cannot use showArticle for nonexistent article"() {
        expect: "requesting nonexistent article without locale completes empty"
        StepVerifier.create(adminArticleClient.showArticle(999999999999L))
                .verifyComplete()
    }

    def "cannot use showArticle with locale for nonexistent article"() {
        expect: "requesting nonexistent article with locale completes empty"
        StepVerifier.create(adminArticleClient.showArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, 999999999999L))
                .verifyComplete()
    }

    def "can update article without locale as admin"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Initial Title " + UUID.randomUUID())
                        .setBody("Initial Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id
        ArticleUpdateRequest updateReq = new ArticleUpdateRequest(
                new Article().setPromoted(true)
        )

        expect: "updating article without locale using convenience method"
        StepVerifier.create(adminArticleClient.updateArticle(createdId, updateReq))
                .assertNext { updateResponse ->
                    verifyAll(updateResponse) {
                        article != null
                        article.promoted == true
                    }
                }
                .verifyComplete()

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "can update article using updateArticleNoLocale as admin"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Initial Title " + UUID.randomUUID())
                        .setBody("Initial Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id
        ArticleUpdateRequest updateReq = new ArticleUpdateRequest(
                new Article().setPromoted(true)
        )

        expect: "updating article using updateArticleNoLocale"
        StepVerifier.create(adminArticleClient.updateArticleNoLocale(createdId, updateReq))
                .assertNext { updateResponse ->
                    verifyAll(updateResponse) {
                        article != null
                        article.promoted == true
                    }
                }
                .verifyComplete()

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "can delete article without locale as admin"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article to Delete " + UUID.randomUUID())
                        .setBody("Body to delete")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id

        expect: "deleting article without locale using convenience method"
        StepVerifier.create(adminArticleClient.deleteArticle(createdId))
                .verifyComplete()
        StepVerifier.create(adminArticleClient.showArticleNoLocale(createdId))
                .verifyComplete()

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "cannot update article as unauthorized user"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article for Unauthorized Update " + UUID.randomUUID())
                        .setBody("Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id
        ArticleUpdateRequest updateReq = new ArticleUpdateRequest(
                new Article().setTitle("Unauthorized Updated Title")
        )

        expect: "unauthorized user attempts to update article"
        StepVerifier.create(userArticleClient.updateArticle(createdId, updateReq))
                .verifyErrorSatisfies { error ->
                    verifyAll {
                        error instanceof HttpClientResponseException
                        ((HttpClientResponseException) error).status.code >= 400
                    }
                }

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "cannot update nonexistent article"() {
        given:
        ArticleUpdateRequest updateReq = new ArticleUpdateRequest(
                new Article().setPromoted(true)
        )

        expect: "updating nonexistent article without locale completes empty"
        StepVerifier.create(adminArticleClient.updateArticle(999999999999L, updateReq))
                .verifyComplete()
    }

    def "cannot delete article as unauthorized user"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article for Unauthorized Delete " + UUID.randomUUID())
                        .setBody("Body")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id

        expect: "unauthorized user attempts to delete article"
        StepVerifier.create(userArticleClient.deleteArticle(createdId))
                .verifyErrorSatisfies { error ->
                    verifyAll {
                        error instanceof HttpClientResponseException
                        ((HttpClientResponseException) error).status.code >= 400
                    }
                }

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "cannot delete nonexistent article"() {
        expect: "deleting nonexistent article without locale completes empty"
        StepVerifier.create(adminArticleClient.deleteArticle(999999999999L))
                .verifyComplete()
    }

    def "can use associateAttachmentsInBulk without locale as admin"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article for Bulk Attachments " + UUID.randomUUID())
                        .setBody("Body for Bulk Attachments")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id
        BulkAttachmentsRequest bulkReq = new BulkAttachmentsRequest(List.of(123456789L))

        expect: "admin associates attachments in bulk without locale"
        StepVerifier.create(adminArticleClient.associateAttachmentsInBulk(createdId, bulkReq))
                .verifyComplete()

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "cannot use associateAttachmentsInBulk without locale as unauthorized user"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article for Bulk Attachments " + UUID.randomUUID())
                        .setBody("Body for Bulk Attachments")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
        )
        def createResponse = adminArticleClient.createArticle(LocaleAbbreviation.ENGLISH_UNITED_STATES, validSectionId, req).block()
        def createdId = createResponse.article.id
        BulkAttachmentsRequest bulkReq = new BulkAttachmentsRequest(List.of(123456789L))

        expect: "unauthorized user associates attachments in bulk without locale"
        StepVerifier.create(userArticleClient.associateAttachmentsInBulk(createdId, bulkReq))
                .verifyErrorSatisfies { error ->
                    verifyAll {
                        error instanceof HttpClientResponseException
                        ((HttpClientResponseException) error).status.code >= 400
                    }
                }

        cleanup:
        try {
            adminArticleClient.deleteArticleNoLocale(createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "can use associateAttachmentsInBulk with locale as admin"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article for Bulk Attachments " + UUID.randomUUID())
                        .setBody("Body for Bulk Attachments")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(validLocale)
        )
        def createResponse = adminArticleClient.createArticle(validLocale, validSectionId, req).block()
        def createdId = createResponse.article.id
        BulkAttachmentsRequest bulkReq = new BulkAttachmentsRequest(List.of(123456789L))

        expect: "admin associates attachments in bulk with locale"
        StepVerifier.create(adminArticleClient.associateAttachmentsInBulk(validLocale, createdId, bulkReq))
                .verifyComplete()

        cleanup:
        try {
            adminArticleClient.deleteArticle(validLocale, createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "cannot use associateAttachmentsInBulk with locale as unauthorized user"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article for Bulk Attachments " + UUID.randomUUID())
                        .setBody("Body for Bulk Attachments")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(validLocale)
        )
        def createResponse = adminArticleClient.createArticle(validLocale, validSectionId, req).block()
        def createdId = createResponse.article.id
        BulkAttachmentsRequest bulkReq = new BulkAttachmentsRequest(List.of(123456789L))

        expect: "unauthorized user associates attachments in bulk with locale"
        StepVerifier.create(userArticleClient.associateAttachmentsInBulk(validLocale, createdId, bulkReq))
                .verifyErrorSatisfies { error ->
                    verifyAll {
                        error instanceof HttpClientResponseException
                        ((HttpClientResponseException) error).status.code >= 400
                    }
                }

        cleanup:
        try {
            adminArticleClient.deleteArticle(validLocale, createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "can use archiveArticle with locale as admin"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article to Archive " + UUID.randomUUID())
                        .setBody("Body of article to archive")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(validLocale)
        )
        def createResponse = adminArticleClient.createArticle(validLocale, validSectionId, req).block()
        def createdId = createResponse.article.id

        expect: "admin archives article with locale"
        StepVerifier.create(adminArticleClient.archiveArticle(validLocale, createdId))
                .verifyComplete()
        StepVerifier.create(adminArticleClient.showArticle(validLocale, createdId))
                .verifyComplete()

        cleanup:
        try {
            adminArticleClient.deleteArticle(validLocale, createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }

    def "cannot use archiveArticle with locale as unauthorized user"() {
        given:
        ArticleCreateRequest req = new ArticleCreateRequest(
                new Article()
                        .setTitle("Article to Archive " + UUID.randomUUID())
                        .setBody("Body of article to archive")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(validLocale)
        )
        def createResponse = adminArticleClient.createArticle(validLocale, validSectionId, req).block()
        def createdId = createResponse.article.id

        expect: "unauthorized user archives article with locale"
        StepVerifier.create(userArticleClient.archiveArticle(validLocale, createdId))
                .verifyErrorSatisfies { error ->
                    verifyAll {
                        error instanceof HttpClientResponseException
                        ((HttpClientResponseException) error).status.code >= 400
                    }
                }

        cleanup:
        try {
            adminArticleClient.deleteArticle(validLocale, createdId).block()
        } catch (Exception ignored) {
            // Defensive cleanup - ignore if resource already deleted or not created
        }
    }
}
