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

    def "calling guide media client with bad url fails"() {
        when: "calling with bad url"
        badUrlGuideMediaClient.searchGuideMedia(null, null).block()

        then: "HttpClientException is thrown"
        thrown(HttpClientException)
    }
}
