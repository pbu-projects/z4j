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

import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.ContentTag
import lol.pbu.z4j.model.ContentTagCountResponse
import lol.pbu.z4j.model.ContentTagCreateRequest
import lol.pbu.z4j.model.ContentTagResponse
import lol.pbu.z4j.model.ContentTagsResponse
import spock.lang.Shared

@MicronautTest
class ContentTagClientSpec extends Z4jSpec {

    @Shared
    ContentTagClient adminContentTagClient, agentContentTagClient, userContentTagClient, badUrlContentTagClient

    def setupSpec() {
        adminContentTagClient = adminCtx.getBean(ContentTagClient.class)
        agentContentTagClient = agentCtx.getBean(ContentTagClient.class)
        userContentTagClient = userCtx.getBean(ContentTagClient.class)
        badUrlContentTagClient = badUrlCtx.getBean(ContentTagClient.class)
    }

    def "can count content tags"() {
        when: "counting content tags"
        ContentTagCountResponse countResp = adminContentTagClient.countContentTags().block()

        then: "count response is returned"
        noExceptionThrown()
        countResp != null
        countResp.value != null
    }

    def "can create, show, search, update, and delete content tag"() {
        given: "a unique tag name"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        String initialName = "tag_${entropy}"
        String updatedName = "tag_upd_${entropy}"
        String tagId = null

        when: "creating content tag"
        ContentTagResponse created = adminContentTagClient.createContentTag(
                new ContentTagCreateRequest(initialName)
        ).block()
        tagId = created?.contentTag?.id

        then: "tag is created successfully"
        noExceptionThrown()
        created?.contentTag != null
        created.contentTag.id != null
        created.contentTag.name == initialName

        when: "showing content tag"
        ContentTagResponse shown = adminContentTagClient.showContentTag(tagId).block()

        then: "tag is returned with same properties"
        noExceptionThrown()
        shown?.contentTag != null
        shown.contentTag.id == tagId
        shown.contentTag.name == initialName

        when: "searching content tags"
        ContentTagsResponse searchResp = adminContentTagClient.searchContentTags().block()

        then: "the created tag is found in the search results"
        noExceptionThrown()
        searchResp?.records != null
        searchResp.records.any { it.id == tagId }

        when: "updating content tag"
        ContentTagResponse updated = adminContentTagClient.updateContentTag(
                tagId,
                new ContentTagCreateRequest(updatedName)
        ).block()

        then: "tag name is updated"
        noExceptionThrown()
        updated?.contentTag != null
        updated.contentTag.name == updatedName

        when: "deleting content tag"
        adminContentTagClient.deleteContentTag(tagId).block()

        then: "no exception is thrown"
        noExceptionThrown()

        cleanup: "defensive cleanup"
        if (tagId != null) {
            try {
                adminContentTagClient.deleteContentTag(tagId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "calling content tag client with bad url fails"() {
        when: "calling with bad url"
        badUrlContentTagClient.countContentTags().block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
