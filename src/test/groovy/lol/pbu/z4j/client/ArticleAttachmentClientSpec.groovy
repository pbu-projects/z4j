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
import io.micronaut.http.MediaType
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.client.multipart.MultipartBody
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.Article
import lol.pbu.z4j.model.ArticleAttachmentResponse
import lol.pbu.z4j.model.ArticleAttachmentsResponse
import lol.pbu.z4j.model.ArticleCreateRequest
import lol.pbu.z4j.model.Locale
import lol.pbu.z4j.model.LocaleAbbreviation
import spock.lang.Shared

import java.nio.charset.StandardCharsets

@MicronautTest
class ArticleAttachmentClientSpec extends Z4jSpec {

    @Shared
    ArticleAttachmentClient adminAttachmentClient, agentAttachmentClient, userAttachmentClient, badUrlAttachmentClient

    @Shared
    ArticleClient adminArticleClient

    @Shared
    Article testArticle

    @Shared
    LocaleAbbreviation defaultLocale = LocaleAbbreviation.ENGLISH_UNITED_STATES

    def setupSpec() {
        adminAttachmentClient = adminCtx.getBean(ArticleAttachmentClient.class)
        agentAttachmentClient = agentCtx.getBean(ArticleAttachmentClient.class)
        userAttachmentClient = userCtx.getBean(ArticleAttachmentClient.class)
        badUrlAttachmentClient = badUrlCtx.getBean(ArticleAttachmentClient.class)
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
                        .setTitle("Attachment Spec Test Article ${entropy}")
                        .setBody("<p>Test article for testing attachments.</p>")
                        .setPermissionGroupId(validPermissionGroupId)
                        .setLocaleAbbreviation(defaultLocale)
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

    def "can create unassociated attachment as admin"() {
        given: "a test file content"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String filename = "test-unassociated-${entropy}.txt"
        byte[] content = "Hello unassociated attachment ${entropy}".getBytes(StandardCharsets.UTF_8)
        Long attachmentId = null

        when: "creating an unassociated attachment"
        ArticleAttachmentResponse response = adminAttachmentClient.createUnassociatedAttachment(
                filename,
                content,
                true
        ).block()
        attachmentId = response?.articleAttachment?.id

        then: "attachment is created with metadata"
        noExceptionThrown()
        response != null
        response.articleAttachment != null
        response.articleAttachment.id != null
        response.articleAttachment.fileName == filename
        response.articleAttachment.size == (long) content.length
        response.articleAttachment.inline == true

        cleanup: "delete the created attachment"
        if (attachmentId != null) {
            try {
                adminAttachmentClient.deleteArticleAttachment(attachmentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can create unassociated attachment using MultipartBody"() {
        given: "a MultipartBody with file and inline parts"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String filename = "test-mp-${entropy}.txt"
        byte[] content = "Hello MultipartBody ${entropy}".getBytes(StandardCharsets.UTF_8)
        MultipartBody body = MultipartBody.builder()
                .addPart("file", filename, MediaType.APPLICATION_OCTET_STREAM_TYPE, content)
                .addPart("inline", "false")
                .build()
        Long attachmentId = null

        when: "posting unassociated attachment"
        ArticleAttachmentResponse response = adminAttachmentClient.createUnassociatedAttachment(body).block()
        attachmentId = response?.articleAttachment?.id

        then: "attachment is successfully created"
        noExceptionThrown()
        response != null
        response.articleAttachment != null
        response.articleAttachment.id != null
        response.articleAttachment.fileName == filename

        cleanup: "delete created attachment"
        if (attachmentId != null) {
            try {
                adminAttachmentClient.deleteArticleAttachment(attachmentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can show article attachment by id"() {
        given: "an uploaded attachment"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String filename = "test-show-${entropy}.txt"
        byte[] content = "Show attachment test ${entropy}".getBytes(StandardCharsets.UTF_8)
        ArticleAttachmentResponse created = adminAttachmentClient.createUnassociatedAttachment(
                filename,
                content,
                false
        ).block()
        Long attachmentId = created?.articleAttachment?.id

        when: "fetching the attachment by id"
        ArticleAttachmentResponse response = adminAttachmentClient.showArticleAttachment(attachmentId).block()

        then: "attachment details match"
        noExceptionThrown()
        response != null
        response.articleAttachment != null
        response.articleAttachment.id == attachmentId
        response.articleAttachment.fileName == filename

        cleanup: "delete created attachment"
        if (attachmentId != null) {
            try {
                adminAttachmentClient.deleteArticleAttachment(attachmentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can delete article attachment as admin"() {
        given: "an uploaded attachment"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String filename = "test-del-${entropy}.txt"
        byte[] content = "Delete attachment test ${entropy}".getBytes(StandardCharsets.UTF_8)
        ArticleAttachmentResponse created = adminAttachmentClient.createUnassociatedAttachment(
                filename,
                content,
                false
        ).block()
        Long attachmentId = created?.articleAttachment?.id

        when: "deleting the attachment"
        adminAttachmentClient.deleteArticleAttachment(attachmentId).block()

        then: "no exception is thrown"
        noExceptionThrown()

        cleanup: "delete created attachment defensively"
        if (attachmentId != null) {
            try {
                adminAttachmentClient.deleteArticleAttachment(attachmentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can create article attachment with locale"() {
        given: "a file for the test article"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String filename = "test-article-loc-${entropy}.txt"
        byte[] content = "Article attachment with locale ${entropy}".getBytes(StandardCharsets.UTF_8)
        Long attachmentId = null

        when: "creating attachment associated with article"
        ArticleAttachmentResponse response = adminAttachmentClient.createArticleAttachment(
                defaultLocale,
                testArticle.id,
                filename,
                content,
                false
        ).block()
        attachmentId = response?.articleAttachment?.id

        then: "response contains attachment linked to article"
        noExceptionThrown()
        response != null
        response.articleAttachment != null
        response.articleAttachment.id != null
        response.articleAttachment.articleId == testArticle.id
        response.articleAttachment.fileName == filename

        cleanup: "delete created attachment"
        if (attachmentId != null) {
            try {
                adminAttachmentClient.deleteArticleAttachment(attachmentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can create article attachment without locale"() {
        given: "a file for the test article"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String filename = "test-article-noloc-${entropy}.txt"
        byte[] content = "Article attachment without locale ${entropy}".getBytes(StandardCharsets.UTF_8)
        Long attachmentId = null

        when: "creating attachment associated with article without specifying locale"
        ArticleAttachmentResponse response = adminAttachmentClient.createArticleAttachmentNoLocale(
                testArticle.id,
                filename,
                content,
                false
        ).block()
        attachmentId = response?.articleAttachment?.id

        then: "response contains attachment linked to article"
        noExceptionThrown()
        response != null
        response.articleAttachment != null
        response.articleAttachment.id != null
        response.articleAttachment.articleId == testArticle.id
        response.articleAttachment.fileName == filename

        cleanup: "delete created attachment"
        if (attachmentId != null) {
            try {
                adminAttachmentClient.deleteArticleAttachment(attachmentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can list article attachments with and without locale"() {
        given: "an attachment created on the test article"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String filename = "test-list-${entropy}.txt"
        byte[] content = "List attachments test ${entropy}".getBytes(StandardCharsets.UTF_8)
        ArticleAttachmentResponse created = adminAttachmentClient.createArticleAttachment(
                defaultLocale,
                testArticle.id,
                filename,
                content,
                false
        ).block()
        Long attachmentId = created?.articleAttachment?.id

        when: "listing attachments for the article with locale"
        ArticleAttachmentsResponse listWithLoc = adminAttachmentClient.listArticleAttachments(
                defaultLocale,
                testArticle.id
        ).block()

        and: "listing attachments for the article without locale"
        ArticleAttachmentsResponse listNoLoc = adminAttachmentClient.listArticleAttachmentsNoLocale(
                testArticle.id
        ).block()

        then: "both lists contain the created attachment"
        noExceptionThrown()
        listWithLoc != null
        listWithLoc.articleAttachments != null
        listWithLoc.articleAttachments.any { it.id == attachmentId }
        listNoLoc != null
        listNoLoc.articleAttachments != null
        listNoLoc.articleAttachments.any { it.id == attachmentId }

        cleanup: "delete created attachment"
        if (attachmentId != null) {
            try {
                adminAttachmentClient.deleteArticleAttachment(attachmentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can list article block attachments"() {
        given: "a block attachment (inline = false) on the article"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String filename = "test-block-${entropy}.txt"
        byte[] content = "Block attachment test ${entropy}".getBytes(StandardCharsets.UTF_8)
        ArticleAttachmentResponse created = adminAttachmentClient.createArticleAttachment(
                defaultLocale,
                testArticle.id,
                filename,
                content,
                false
        ).block()
        Long attachmentId = created?.articleAttachment?.id

        when: "listing block attachments with and without locale"
        ArticleAttachmentsResponse blockListWithLoc = adminAttachmentClient.listArticleBlockAttachments(
                defaultLocale,
                testArticle.id
        ).block()
        ArticleAttachmentsResponse blockListNoLoc = adminAttachmentClient.listArticleBlockAttachmentsNoLocale(
                testArticle.id
        ).block()

        then: "both block lists contain the attachment and inline is false"
        noExceptionThrown()
        blockListWithLoc != null
        blockListWithLoc.articleAttachments != null
        def foundWithLoc = blockListWithLoc.articleAttachments.find { it.id == attachmentId }
        foundWithLoc != null
        !foundWithLoc.inline

        blockListNoLoc != null
        blockListNoLoc.articleAttachments != null
        def foundNoLoc = blockListNoLoc.articleAttachments.find { it.id == attachmentId }
        foundNoLoc != null
        !foundNoLoc.inline

        cleanup: "delete created attachment"
        if (attachmentId != null) {
            try {
                adminAttachmentClient.deleteArticleAttachment(attachmentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can list article inline attachments"() {
        given: "an inline attachment (inline = true) on the article"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String filename = "test-inline-${entropy}.png"
        byte[] content = "fake image content ${entropy}".getBytes(StandardCharsets.UTF_8)
        ArticleAttachmentResponse created = adminAttachmentClient.createArticleAttachment(
                defaultLocale,
                testArticle.id,
                filename,
                content,
                true
        ).block()
        Long attachmentId = created?.articleAttachment?.id

        when: "listing inline attachments with and without locale"
        ArticleAttachmentsResponse inlineListWithLoc = adminAttachmentClient.listArticleInlineAttachments(
                defaultLocale,
                testArticle.id
        ).block()
        ArticleAttachmentsResponse inlineListNoLoc = adminAttachmentClient.listArticleInlineAttachmentsNoLocale(
                testArticle.id
        ).block()

        then: "both inline lists contain the attachment and inline is true"
        noExceptionThrown()
        inlineListWithLoc != null
        inlineListWithLoc.articleAttachments != null
        def foundWithLoc = inlineListWithLoc.articleAttachments.find { it.id == attachmentId }
        foundWithLoc != null
        foundWithLoc.inline

        inlineListNoLoc != null
        inlineListNoLoc.articleAttachments != null
        def foundNoLoc = inlineListNoLoc.articleAttachments.find { it.id == attachmentId }
        foundNoLoc != null
        foundNoLoc.inline

        cleanup: "delete created attachment"
        if (attachmentId != null) {
            try {
                adminAttachmentClient.deleteArticleAttachment(attachmentId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "end user cannot create unassociated attachment"() {
        when: "end user attempts to create unassociated attachment"
        userAttachmentClient.createUnassociatedAttachment(
                "test.txt",
                "content".bytes,
                false
        ).block()

        then: "forbidden or unauthorized exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [401, 403, 404]
    }

    def "end user cannot delete article attachment"() {
        when: "end user attempts to delete an attachment"
        userAttachmentClient.deleteArticleAttachment(12345L).block()

        then: "forbidden or unauthorized exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [401, 403, 404]
    }

    def "calling article attachment client with bad url fails"() {
        when: "calling with bad url"
        badUrlAttachmentClient.showArticleAttachment(12345L).block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
