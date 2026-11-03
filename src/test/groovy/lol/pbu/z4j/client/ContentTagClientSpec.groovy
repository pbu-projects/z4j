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
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.ContentTag
import lol.pbu.z4j.model.ContentTagCountResponse
import lol.pbu.z4j.model.ContentTagCreateRequest
import lol.pbu.z4j.model.ContentTagResponse
import lol.pbu.z4j.model.ContentTagsJobRequest
import lol.pbu.z4j.model.ContentTagsResponse
import lol.pbu.z4j.model.JobStatusResponse
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
        assert countResp != null
        verifyAll(countResp) {
            value != null
        }
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
        assert created?.contentTag != null
        verifyAll(created.contentTag) {
            id != null
            name == initialName
        }

        when: "showing content tag"
        ContentTagResponse shown = adminContentTagClient.showContentTag(tagId).block()

        then: "tag is returned with same properties"
        noExceptionThrown()
        assert shown?.contentTag != null
        verifyAll(shown.contentTag) {
            id == tagId
            name == initialName
        }

        when: "searching content tags"
        ContentTagsResponse searchResp = adminContentTagClient.searchContentTags().block()

        then: "the created tag is found in the search results"
        noExceptionThrown()
        assert searchResp != null
        verifyAll(searchResp) {
            records != null
            records.any { it.id == tagId }
        }

        when: "updating content tag"
        ContentTagResponse updated = adminContentTagClient.updateContentTag(
                tagId,
                new ContentTagCreateRequest(updatedName)
        ).block()

        then: "tag name is updated"
        noExceptionThrown()
        assert updated?.contentTag != null
        verifyAll(updated.contentTag) {
            name == updatedName
        }

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

    def "can create content tags job"() {
        given: "two temporary content tags"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        ContentTagResponse tag1 = adminContentTagClient.createContentTag(
                new ContentTagCreateRequest("job_a_${entropy}")
        ).block()
        ContentTagResponse tag2 = adminContentTagClient.createContentTag(
                new ContentTagCreateRequest("job_b_${entropy}")
        ).block()
        String tag1Id = tag1?.contentTag?.id
        String tag2Id = tag2?.contentTag?.id

        when: "creating a content tags job to delete the tags"
        JobStatusResponse response = adminContentTagClient.createContentTagsJob(
                new ContentTagsJobRequest("delete", [tag1Id, tag2Id])
        ).block()

        then: "job request succeeds with non-null response"
        noExceptionThrown()
        assert response != null
        verifyAll(response) {
            jobStatus != null
        }

        cleanup: "clean up tags defensively"
        [tag1Id, tag2Id].each { id ->
            if (id != null) {
                try {
                    adminContentTagClient.deleteContentTag(id).block()
                } catch (Exception ignored) {
                }
            }
        }
    }

    def "creating content tags job with invalid action fails"() {
        when: "submitting a job with an unsupported action"
        adminContentTagClient.createContentTagsJob(
                new ContentTagsJobRequest("invalid_action", ["invalid_tag_id"])
        ).block()

        then: "bad request or unprocessable entity exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [400, 422]
    }

    def "fetching non-existent content tag returns null or throws exception"() {
        when: "requesting a missing content tag ulid"
        ContentTagResponse resp = adminContentTagClient.showContentTag("01ARZ3NDEK0000000000000000").block()

        then: "empty response is returned"
        noExceptionThrown()
        resp == null

        when: "requesting with invalid id format"
        adminContentTagClient.showContentTag("invalid-id").block()

        then: "bad request or not found exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [400, 404]
    }

    def "cannot create content tags job as unauthorized user"() {
        when: "end user attempts to create content tags job"
        userContentTagClient.createContentTagsJob(
                new ContentTagsJobRequest("delete", ["01ARZ3NDEK0000000000000000"])
        ).block()

        then: "unauthorized or forbidden exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [401, 403, 404]
    }

    def "cannot create content tag as unauthorized user"() {
        when: "end user creates a content tag"
        userContentTagClient.createContentTag(new ContentTagCreateRequest("unauthorized_tag")).block()

        then: "unauthorized or forbidden exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [401, 403, 404]
    }

    def "calling content tag client with bad url fails"() {
        when: "calling with bad url"
        badUrlContentTagClient.countContentTags().block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
