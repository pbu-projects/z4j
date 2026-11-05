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
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import java.net.HttpURLConnection
import java.net.URI
import java.nio.charset.StandardCharsets
import lol.pbu.z4j.Z4jSpec
import lol.pbu.z4j.model.GuideMediaCreateRequest
import lol.pbu.z4j.model.GuideMediaResponse
import lol.pbu.z4j.model.GuideMediaUploadUrlRequest
import lol.pbu.z4j.model.GuideMediaUploadUrlResponse
import lol.pbu.z4j.model.GuideMediasResponse
import spock.lang.Shared

@MicronautTest
class GuideMediaClientSpec extends Z4jSpec {

    @Shared
    GuideMediaClient adminGuideMediaClient, agentGuideMediaClient, userGuideMediaClient, badUrlGuideMediaClient

    @Shared
    ObjectMapper objectMapper = new ObjectMapper()

    def setupSpec() {
        adminGuideMediaClient = adminCtx.getBean(GuideMediaClient.class)
        agentGuideMediaClient = agentCtx.getBean(GuideMediaClient.class)
        userGuideMediaClient = userCtx.getBean(GuideMediaClient.class)
        badUrlGuideMediaClient = badUrlCtx.getBean(GuideMediaClient.class)
    }

    def "can search guide media"() {
        when: "searching guide media"
        GuideMediasResponse response = adminGuideMediaClient.searchGuideMedia(null, null).block()

        then: "response is returned"
        noExceptionThrown()
        assert response != null
        verifyAll(response) {
            records() != null
        }
    }

    def "can create upload url with default and custom params"() {
        when: "creating upload url with default parameters"
        GuideMediaUploadUrlResponse defaultResp = adminGuideMediaClient.createUploadUrl().block()

        then: "upload url response contains asset upload id and upload url"
        noExceptionThrown()
        assert defaultResp != null
        verifyAll(defaultResp) {
            assetUploadId() != null
            uploadUrl() != null
        }

        when: "creating upload url with custom parameters"
        GuideMediaUploadUrlResponse customResp = adminGuideMediaClient.createUploadUrl(
                new GuideMediaUploadUrlRequest("image/png", 1024L)
        ).block()

        then: "custom upload url response is returned"
        noExceptionThrown()
        assert customResp != null
        verifyAll(customResp) {
            assetUploadId() != null
            uploadUrl() != null
        }
    }

    def "can create, show, rename, and delete guide media"() {
        given: "an upload url provisioned for a media file"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        byte[] payload = "Guide media test content ${entropy}".getBytes(StandardCharsets.UTF_8)
        GuideMediaUploadUrlResponse uploadUrlResp = adminGuideMediaClient.createUploadUrl(
                new GuideMediaUploadUrlRequest("text/plain", (long) payload.length)
        ).block()
        String uploadUrl = uploadUrlResp.uploadUrl()
        String assetUploadId = uploadUrlResp.assetUploadId()
        String filename = "test-media-${entropy}.txt"
        String renamedFilename = "renamed-media-${entropy}.txt"
        String mediaId = null

        when: "uploading file content to the presigned upload url"
        HttpURLConnection conn = (HttpURLConnection) URI.create(uploadUrl).toURL().openConnection()
        conn.setRequestMethod("PUT")
        conn.setDoOutput(true)
        if (uploadUrlResp.headers() != null) {
            Map<String, String> headerMap = objectMapper.readValue(uploadUrlResp.headers(), Map.class)
            headerMap.each { k, v -> conn.setRequestProperty(k, v) }
        } else {
            conn.setRequestProperty("Content-Type", "text/plain")
        }
        conn.getOutputStream().withCloseable { it.write(payload) }
        int uploadStatus = conn.getResponseCode()

        then: "upload succeeds"
        uploadStatus >= 200 && uploadStatus < 300

        when: "creating the guide media object"
        GuideMediaResponse created = adminGuideMediaClient.createGuideMedia(
                new GuideMediaCreateRequest(assetUploadId, filename)
        ).block()
        mediaId = created?.media()?.id

        then: "guide media is created"
        noExceptionThrown()
        assert created?.media() != null
        verifyAll(created.media()) {
            id != null
            name == filename
        }

        when: "showing the guide media object"
        GuideMediaResponse shown = adminGuideMediaClient.showGuideMedia(mediaId).block()

        then: "shown media matches created media"
        noExceptionThrown()
        assert shown?.media() != null
        verifyAll(shown.media()) {
            id == mediaId
            name == filename
        }

        when: "renaming the guide media object"
        GuideMediaResponse renamed = adminGuideMediaClient.renameGuideMedia(
                mediaId,
                new GuideMediaCreateRequest(renamedFilename)
        ).block()

        then: "media is renamed"
        noExceptionThrown()
        assert renamed?.media() != null
        verifyAll(renamed.media()) {
            id == mediaId
            name == renamedFilename
        }

        when: "deleting the guide media object"
        adminGuideMediaClient.deleteGuideMedia(mediaId).block()

        then: "deletion succeeds"
        noExceptionThrown()

        cleanup: "defensively delete guide media"
        if (mediaId != null) {
            try {
                adminGuideMediaClient.deleteGuideMedia(mediaId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "can replace guide media"() {
        given: "an initial guide media item"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        byte[] payload1 = "Initial guide media ${entropy}".getBytes(StandardCharsets.UTF_8)
        GuideMediaUploadUrlResponse uploadResp1 = adminGuideMediaClient.createUploadUrl(
                new GuideMediaUploadUrlRequest("text/plain", (long) payload1.length)
        ).block()
        HttpURLConnection conn1 = (HttpURLConnection) URI.create(uploadResp1.uploadUrl()).toURL().openConnection()
        conn1.setRequestMethod("PUT")
        conn1.setDoOutput(true)
        if (uploadResp1.headers() != null) {
            Map<String, String> headerMap = objectMapper.readValue(uploadResp1.headers(), Map.class)
            headerMap.each { k, v -> conn1.setRequestProperty(k, v) }
        } else {
            conn1.setRequestProperty("Content-Type", "text/plain")
        }
        conn1.getOutputStream().withCloseable { it.write(payload1) }
        conn1.getResponseCode()

        String initialFilename = "initial-media-${entropy}.txt"
        GuideMediaResponse created = adminGuideMediaClient.createGuideMedia(
                new GuideMediaCreateRequest(uploadResp1.assetUploadId(), initialFilename)
        ).block()
        String mediaId = created?.media()?.id

        and: "a replacement upload asset"
        byte[] payload2 = "Replaced guide media ${entropy}".getBytes(StandardCharsets.UTF_8)
        GuideMediaUploadUrlResponse uploadResp2 = adminGuideMediaClient.createUploadUrl(
                new GuideMediaUploadUrlRequest("text/plain", (long) payload2.length)
        ).block()
        HttpURLConnection conn2 = (HttpURLConnection) URI.create(uploadResp2.uploadUrl()).toURL().openConnection()
        conn2.setRequestMethod("PUT")
        conn2.setDoOutput(true)
        if (uploadResp2.headers() != null) {
            Map<String, String> headerMap = objectMapper.readValue(uploadResp2.headers(), Map.class)
            headerMap.each { k, v -> conn2.setRequestProperty(k, v) }
        } else {
            conn2.setRequestProperty("Content-Type", "text/plain")
        }
        conn2.getOutputStream().withCloseable { it.write(payload2) }
        conn2.getResponseCode()
        String replacedFilename = "replaced-media-${entropy}.txt"

        when: "replacing the guide media"
        GuideMediaResponse replaced = adminGuideMediaClient.replaceGuideMedia(
                mediaId,
                new GuideMediaCreateRequest(uploadResp2.assetUploadId(), replacedFilename)
        ).block()

        then: "replacement response returns updated media"
        noExceptionThrown()
        assert replaced?.media() != null
        verifyAll(replaced.media()) {
            id == mediaId
            name == replacedFilename
        }

        cleanup: "defensively delete guide media"
        if (mediaId != null) {
            try {
                adminGuideMediaClient.deleteGuideMedia(mediaId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }
    }

    def "cannot search guide media as end user"() {
        when: "end user client searches guide media"
        userGuideMediaClient.searchGuideMedia(null, null).block()

        then: "unauthorized or forbidden exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [401, 403, 404]
    }

    def "cannot delete guide media as unauthorized role"(GuideMediaClient client, String role) {
        given: "a media item created by admin"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        byte[] payload = "Role rejection test ${entropy}".getBytes(StandardCharsets.UTF_8)
        GuideMediaUploadUrlResponse uploadResp = adminGuideMediaClient.createUploadUrl(
                new GuideMediaUploadUrlRequest("text/plain", (long) payload.length)
        ).block()
        HttpURLConnection conn = (HttpURLConnection) URI.create(uploadResp.uploadUrl()).toURL().openConnection()
        conn.setRequestMethod("PUT")
        conn.setDoOutput(true)
        if (uploadResp.headers() != null) {
            Map<String, String> headerMap = objectMapper.readValue(uploadResp.headers(), Map.class)
            headerMap.each { k, v -> conn.setRequestProperty(k, v) }
        } else {
            conn.setRequestProperty("Content-Type", "text/plain")
        }
        conn.getOutputStream().withCloseable { it.write(payload) }
        conn.getResponseCode()

        String filename = "role-reject-${entropy}.txt"
        GuideMediaResponse created = adminGuideMediaClient.createGuideMedia(
                new GuideMediaCreateRequest(uploadResp.assetUploadId(), filename)
        ).block()
        String mediaId = created?.media()?.id

        when: "unauthorized client attempts to delete media"
        client.deleteGuideMedia(mediaId).block()

        then: "unauthorized or forbidden exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [401, 403, 404]

        cleanup: "defensively delete media item as admin"
        if (mediaId != null) {
            try {
                adminGuideMediaClient.deleteGuideMedia(mediaId).block()
            } catch (Exception ignored) {
                // Defensive cleanup
            }
        }

        where:
        [client, role] << [
                [agentGuideMediaClient, "agent"],
                [userGuideMediaClient, "user"]
        ]
    }

    def "fetching non-existent guide media returns null or throws exception"() {
        when: "fetching guide media with non-existent ulid"
        GuideMediaResponse response = adminGuideMediaClient.showGuideMedia("01ARZ3NDEK0000000000000000").block()

        then: "empty response is returned"
        noExceptionThrown()
        response == null

        when: "fetching guide media with invalid id format"
        adminGuideMediaClient.showGuideMedia("invalid-id").block()

        then: "bad request or not found exception is thrown"
        HttpClientResponseException ex = thrown(HttpClientResponseException)
        ex.status.code in [400, 404]
    }

    def "calling guide media client with bad url fails"() {
        when: "calling with bad url"
        badUrlGuideMediaClient.searchGuideMedia(null, null).block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
