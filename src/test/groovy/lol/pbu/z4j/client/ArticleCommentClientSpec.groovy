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
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.Article
import lol.pbu.z4j.model.ArticleCreateRequest
import lol.pbu.z4j.model.Comment
import lol.pbu.z4j.model.CommentCreateRequest
import lol.pbu.z4j.model.CommentResponse
import lol.pbu.z4j.model.CommentsResponse
import lol.pbu.z4j.model.Locale
import lol.pbu.z4j.model.LocaleAbbreviation
import spock.lang.Shared

@MicronautTest
class ArticleCommentClientSpec extends Z4jSpec {

    @Shared
    ArticleCommentClient adminCommentClient, agentCommentClient, userCommentClient, badUrlCommentClient

    @Shared
    ArticleClient adminArticleClient

    @Shared
    Article testArticle

    @Shared
    LocaleAbbreviation defaultLocale = LocaleAbbreviation.ENGLISH_UNITED_STATES

    def setupSpec() {
        adminCommentClient = adminCtx.getBean(ArticleCommentClient.class)
        agentCommentClient = agentCtx.getBean(ArticleCommentClient.class)
        userCommentClient = userCtx.getBean(ArticleCommentClient.class)
        badUrlCommentClient = badUrlCtx.getBean(ArticleCommentClient.class)
        adminArticleClient = adminCtx.getBean(ArticleClient.class)

        Long validSectionId = null
        Long validPermissionGroupId = null

        def articles = adminArticleClient.listArticles(defaultLocale, null, null, null, null).block()?.articles
        if (articles != null && !articles.isEmpty()) {
            validSectionId = articles.get(0).sectionId
            validPermissionGroupId = articles.get(0).permissionGroupId
        }

        if (validSectionId == null) {
            for (Locale loc : accountLocales) {
                def locAbbr = loc.localeAbbreviation
                if (locAbbr != null) {
                    def locArticles = adminArticleClient.listArticles(locAbbr, null, null, null, null).block()?.articles
                    if (locArticles != null && !locArticles.isEmpty()) {
                        validSectionId = locArticles.get(0).sectionId
                        validPermissionGroupId = locArticles.get(0).permissionGroupId
                        defaultLocale = locAbbr
                        break
                    }
                }
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

        assert validSectionId != null : "Could not find a valid sectionId for article creation!"
        assert validPermissionGroupId != null : "Could not find a valid permissionGroupId for article creation!"

        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        def createReq = new ArticleCreateRequest(
                new Article()
                        .setTitle("Comment Spec Test Article ${entropy}")
                        .setBody("<p>Test article for testing comments.</p>")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
                        .setCommentsDisabled(false)
        )
        testArticle = adminArticleClient.createArticle(defaultLocale, validSectionId, createReq).block()?.article
        assert testArticle != null && testArticle.id != null : "Failed to seed test article!"
    }

    def cleanupSpec() {
        if (testArticle?.id != null) {
            try {
                adminArticleClient.deleteArticle(defaultLocale, testArticle.id).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can create comment with locale as admin"() {
        given: "a comment create request"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String commentBody = "Test comment with locale ${entropy}"
        def req = new CommentCreateRequest(new Comment(commentBody, defaultLocale))
        Long commentId = null

        when: "creating comment with locale"
        CommentResponse response = adminCommentClient.createComment(defaultLocale, testArticle.id, req).block()
        commentId = response?.comment?.id

        then: "comment is created with expected body"
        noExceptionThrown()
        response != null
        response.comment != null
        response.comment.id != null
        response.comment.body == commentBody

        cleanup: "delete created comment"
        if (commentId != null) {
            try {
                adminCommentClient.deleteComment(defaultLocale, testArticle.id, commentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can create comment without locale as admin"() {
        given: "a comment create request"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String commentBody = "Test comment without locale ${entropy}"
        def req = new CommentCreateRequest(new Comment(commentBody, defaultLocale))
        Long commentId = null

        when: "creating comment without locale"
        CommentResponse response = adminCommentClient.createCommentNoLocale(testArticle.id, req).block()
        commentId = response?.comment?.id

        then: "comment is created with expected body"
        noExceptionThrown()
        response != null
        response.comment != null
        response.comment.id != null
        response.comment.body == commentBody

        cleanup: "delete created comment"
        if (commentId != null) {
            try {
                adminCommentClient.deleteCommentNoLocale(testArticle.id, commentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can show comment with and without locale"() {
        given: "a created comment"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String commentBody = "Show test comment ${entropy}"
        CommentResponse created = adminCommentClient.createComment(
                defaultLocale,
                testArticle.id,
                new CommentCreateRequest(new Comment(commentBody, defaultLocale))
        ).block()
        Long commentId = created?.comment?.id

        when: "showing comment with locale"
        CommentResponse showWithLoc = adminCommentClient.showComment(defaultLocale, testArticle.id, commentId).block()

        and: "showing comment without locale"
        CommentResponse showNoLoc = adminCommentClient.showCommentNoLocale(testArticle.id, commentId).block()

        then: "both responses return the comment"
        noExceptionThrown()
        showWithLoc != null
        showWithLoc.comment != null
        showWithLoc.comment.id == commentId
        showWithLoc.comment.body == commentBody
        showNoLoc != null
        showNoLoc.comment != null
        showNoLoc.comment.id == commentId

        cleanup: "delete created comment"
        if (commentId != null) {
            try {
                adminCommentClient.deleteComment(defaultLocale, testArticle.id, commentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can update comment with and without locale"() {
        given: "a created comment"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        CommentResponse created = adminCommentClient.createComment(
                defaultLocale,
                testArticle.id,
                new CommentCreateRequest(new Comment("Initial comment ${entropy}", defaultLocale))
        ).block()
        Long commentId = created?.comment?.id
        String updatedBody1 = "Updated body with locale ${entropy}"
        String updatedBody2 = "Updated body without locale ${entropy}"

        when: "updating comment with locale"
        CommentResponse updatedWithLoc = adminCommentClient.updateComment(
                defaultLocale,
                testArticle.id,
                commentId,
                new CommentCreateRequest(new Comment(updatedBody1, defaultLocale))
        ).block()

        and: "updating comment without locale"
        CommentResponse updatedNoLoc = adminCommentClient.updateCommentNoLocale(
                testArticle.id,
                commentId,
                new CommentCreateRequest(new Comment(updatedBody2, defaultLocale))
        ).block()

        then: "comments are updated successfully"
        noExceptionThrown()
        updatedWithLoc?.comment?.body?.contains(updatedBody1)
        updatedNoLoc?.comment?.body?.contains(updatedBody2)

        cleanup: "delete created comment"
        if (commentId != null) {
            try {
                adminCommentClient.deleteComment(defaultLocale, testArticle.id, commentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can list comments on article with and without locale"() {
        given: "a comment created on the test article"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        CommentResponse created = adminCommentClient.createComment(
                defaultLocale,
                testArticle.id,
                new CommentCreateRequest(new Comment("List comment test ${entropy}", defaultLocale))
        ).block()
        Long commentId = created?.comment?.id

        when: "listing comments on article with locale"
        CommentsResponse listWithLoc = adminCommentClient.listCommentsOnArticle(defaultLocale, testArticle.id).block()

        and: "listing comments on article without locale"
        CommentsResponse listNoLoc = adminCommentClient.listCommentsOnArticleNoLocale(testArticle.id).block()

        then: "both lists contain the created comment"
        noExceptionThrown()
        listWithLoc?.comments != null
        listWithLoc.comments.any { it.id == commentId }
        listNoLoc?.comments != null
        listNoLoc.comments.any { it.id == commentId }

        cleanup: "delete created comment"
        if (commentId != null) {
            try {
                adminCommentClient.deleteComment(defaultLocale, testArticle.id, commentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can list comments by user"() {
        given: "a comment created by admin"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        CommentResponse created = adminCommentClient.createComment(
                defaultLocale,
                testArticle.id,
                new CommentCreateRequest(new Comment("User comment test ${entropy}", defaultLocale))
        ).block()
        Long commentId = created?.comment?.id
        Long userId = created?.comment?.authorId

        when: "listing comments by user"
        CommentsResponse userComments = adminCommentClient.listCommentsByUser(userId).block()

        then: "user comments list contains the created comment"
        noExceptionThrown()
        userComments?.comments != null
        userComments.comments.any { it.id == commentId }

        cleanup: "delete created comment"
        if (commentId != null) {
            try {
                adminCommentClient.deleteComment(defaultLocale, testArticle.id, commentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can delete comment with and without locale"() {
        given: "two comments created"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        CommentResponse created1 = adminCommentClient.createComment(
                defaultLocale,
                testArticle.id,
                new CommentCreateRequest(new Comment("Delete test 1 ${entropy}", defaultLocale))
        ).block()
        CommentResponse created2 = adminCommentClient.createComment(
                defaultLocale,
                testArticle.id,
                new CommentCreateRequest(new Comment("Delete test 2 ${entropy}", defaultLocale))
        ).block()
        Long commentId1 = created1?.comment?.id
        Long commentId2 = created2?.comment?.id

        when: "deleting comment 1 with locale"
        adminCommentClient.deleteComment(defaultLocale, testArticle.id, commentId1).block()

        and: "deleting comment 2 without locale"
        adminCommentClient.deleteCommentNoLocale(testArticle.id, commentId2).block()

        then: "no exception is thrown"
        noExceptionThrown()

        cleanup: "defensive cleanup"
        if (commentId1 != null) {
            try {
                adminCommentClient.deleteComment(defaultLocale, testArticle.id, commentId1).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
        if (commentId2 != null) {
            try {
                adminCommentClient.deleteCommentNoLocale(testArticle.id, commentId2).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "calling article comment client with bad url fails"() {
        when: "calling with bad url"
        badUrlCommentClient.listCommentsByUser(12345L).block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
