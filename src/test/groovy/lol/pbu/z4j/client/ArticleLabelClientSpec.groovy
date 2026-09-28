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
import lol.pbu.z4j.model.Label
import lol.pbu.z4j.model.LabelCreateRequest
import lol.pbu.z4j.model.LabelResponse
import lol.pbu.z4j.model.LabelsResponse
import lol.pbu.z4j.model.Locale
import lol.pbu.z4j.model.LocaleAbbreviation
import spock.lang.Shared

@MicronautTest
class ArticleLabelClientSpec extends Z4jSpec {

    @Shared
    ArticleLabelClient adminLabelClient, agentLabelClient, userLabelClient, badUrlLabelClient

    @Shared
    ArticleClient adminArticleClient

    @Shared
    Article testArticle

    @Shared
    LocaleAbbreviation defaultLocale = LocaleAbbreviation.ENGLISH_UNITED_STATES

    def setupSpec() {
        adminLabelClient = adminCtx.getBean(ArticleLabelClient.class)
        agentLabelClient = agentCtx.getBean(ArticleLabelClient.class)
        userLabelClient = userCtx.getBean(ArticleLabelClient.class)
        badUrlLabelClient = badUrlCtx.getBean(ArticleLabelClient.class)
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
                        .setTitle("Label Spec Test Article ${entropy}")
                        .setBody("<p>Test article for testing labels.</p>")
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

    def "can create label on article as admin"() {
        given: "a label name"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toLowerCase()
        String labelName = "lbl${entropy}"
        Long labelId = null

        when: "creating label on article"
        LabelResponse response = adminLabelClient.createLabel(
                testArticle.id,
                new LabelCreateRequest(new Label(labelName))
        ).block()
        labelId = response?.label?.id

        then: "label is created with correct name"
        noExceptionThrown()
        response != null
        response.label != null
        response.label.id != null
        response.label.name == labelName

        cleanup: "delete created label"
        if (labelId != null) {
            try {
                adminLabelClient.deleteLabel(labelId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can list labels on article"() {
        given: "a created label on the article"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toLowerCase()
        String labelName = "listlbl${entropy}"
        LabelResponse created = adminLabelClient.createLabel(
                testArticle.id,
                new LabelCreateRequest(new Label(labelName))
        ).block()
        Long labelId = created?.label?.id

        when: "listing labels for the article"
        LabelsResponse listResp = adminLabelClient.listLabelsOnArticle(testArticle.id).block()

        then: "the list contains the created label"
        noExceptionThrown()
        listResp?.labels != null
        listResp.labels.any { it.id == labelId }

        cleanup: "delete created label"
        if (labelId != null) {
            try {
                adminLabelClient.deleteLabel(labelId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can list all labels and show label by id"() {
        given: "a created label on the article"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toLowerCase()
        String labelName = "showlbl${entropy}"
        LabelResponse created = adminLabelClient.createLabel(
                testArticle.id,
                new LabelCreateRequest(new Label(labelName))
        ).block()
        Long labelId = created?.label?.id

        when: "listing all labels in help center"
        LabelsResponse allLabels = adminLabelClient.listLabels().block()

        and: "showing label by id"
        LabelResponse showResp = adminLabelClient.showLabel(labelId).block()

        then: "both calls return the expected label"
        noExceptionThrown()
        allLabels?.labels != null
        allLabels.labels.any { it.id == labelId }
        showResp?.label != null
        showResp.label.id == labelId
        showResp.label.name == labelName

        cleanup: "delete created label"
        if (labelId != null) {
            try {
                adminLabelClient.deleteLabel(labelId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can delete label from article without deleting the label itself"() {
        given: "a created label on the article"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toLowerCase()
        String labelName = "dellbl${entropy}"
        LabelResponse created = adminLabelClient.createLabel(
                testArticle.id,
                new LabelCreateRequest(new Label(labelName))
        ).block()
        Long labelId = created?.label?.id

        when: "removing label from the article"
        adminLabelClient.deleteLabelFromArticle(testArticle.id, labelId).block()

        and: "fetching labels on article"
        LabelsResponse articleLabels = adminLabelClient.listLabelsOnArticle(testArticle.id).block()

        then: "label is removed from article"
        noExceptionThrown()
        !articleLabels.labels.any { it.id == labelId }

        cleanup: "delete the label globally"
        if (labelId != null) {
            try {
                adminLabelClient.deleteLabel(labelId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can delete label globally"() {
        given: "a created label"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toLowerCase()
        String labelName = "delgloblbl${entropy}"
        LabelResponse created = adminLabelClient.createLabel(
                testArticle.id,
                new LabelCreateRequest(new Label(labelName))
        ).block()
        Long labelId = created?.label?.id

        when: "deleting the label globally"
        adminLabelClient.deleteLabel(labelId).block()

        then: "no exception is thrown"
        noExceptionThrown()

        cleanup: "defensive cleanup"
        if (labelId != null) {
            try {
                adminLabelClient.deleteLabel(labelId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "calling article label client with bad url fails"() {
        when: "calling with bad url"
        badUrlLabelClient.showLabel(12345L).block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
