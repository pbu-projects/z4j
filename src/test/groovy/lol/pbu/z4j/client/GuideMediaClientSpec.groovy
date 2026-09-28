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
        response != null
        response.records != null
    }

    def "can create upload url with default and custom params"() {
        when: "creating upload url with default parameters"
        GuideMediaUploadUrlResponse defaultResp = adminGuideMediaClient.createUploadUrl().block()

        then: "upload url response contains asset upload id or upload url"
        noExceptionThrown()
        defaultResp != null
        defaultResp.assetUploadId != null || defaultResp.uploadUrl != null

        when: "creating upload url with custom parameters"
        GuideMediaUploadUrlResponse customResp = adminGuideMediaClient.createUploadUrl(
                new GuideMediaUploadUrlRequest("image/png", 1024L)
        ).block()

        then: "custom upload url response is returned"
        noExceptionThrown()
        customResp != null
        customResp.assetUploadId != null || customResp.uploadUrl != null
    }

    def "can create, show, rename, and delete guide media"() {
        given: "an upload url provisioned for a media file"
        String entropy = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        byte[] payload = "Guide media test content ${entropy}".getBytes(StandardCharsets.UTF_8)
        GuideMediaUploadUrlResponse uploadUrlResp = adminGuideMediaClient.createUploadUrl(
                new GuideMediaUploadUrlRequest("text/plain", (long) payload.length)
        ).block()
        String uploadUrl = uploadUrlResp.getUploadUrl()
        String assetUploadId = uploadUrlResp.getAssetUploadId()
        String filename = "test-media-${entropy}.txt"
        String renamedFilename = "renamed-media-${entropy}.txt"
        String mediaId = null

        when: "uploading file content to the presigned upload url"
        HttpURLConnection conn = (HttpURLConnection) URI.create(uploadUrl).toURL().openConnection()
        conn.setRequestMethod("PUT")
        conn.setDoOutput(true)
        if (uploadUrlResp.getHeaders() != null) {
            Map<String, String> headerMap = new ObjectMapper().readValue(uploadUrlResp.getHeaders(), Map.class)
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
        mediaId = created?.guideMedia?.id

        then: "guide media is created"
        noExceptionThrown()
        created?.guideMedia != null
        mediaId != null
        created.guideMedia.name == filename

        when: "showing the guide media object"
        GuideMediaResponse shown = adminGuideMediaClient.showGuideMedia(mediaId).block()

        then: "shown media matches created media"
        noExceptionThrown()
        shown?.guideMedia != null
        shown.guideMedia.id == mediaId
        shown.guideMedia.name == filename

        when: "renaming the guide media object"
        GuideMediaResponse renamed = adminGuideMediaClient.renameGuideMedia(
                mediaId,
                new GuideMediaCreateRequest(renamedFilename)
        ).block()

        then: "media is renamed"
        noExceptionThrown()
        renamed?.guideMedia != null
        renamed.guideMedia.id == mediaId
        renamed.guideMedia.name == renamedFilename

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

    def "calling guide media client with bad url fails"() {
        when: "calling with bad url"
        badUrlGuideMediaClient.searchGuideMedia(null, null).block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
