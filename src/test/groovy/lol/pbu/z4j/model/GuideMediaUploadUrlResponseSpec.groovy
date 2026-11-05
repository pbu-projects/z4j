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
package lol.pbu.z4j.model

import io.micronaut.serde.ObjectMapper
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class GuideMediaUploadUrlResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create GuideMediaUploadUrlResponse with constructors and builder"() {
        when:
        GuideMediaUploadUrlResponse twoArg = new GuideMediaUploadUrlResponse(
                "https://uploads.example.com/1",
                "asset-101"
        )
        GuideMediaUploadUrlResponse direct = new GuideMediaUploadUrlResponse(
                "https://uploads.example.com/2",
                "asset-102",
                '{"Content-Type":"image/png"}'
        )
        GuideMediaUploadUrlResponse built = GuideMediaUploadUrlResponseBuilder.builder()
                .uploadUrl("https://uploads.example.com/2")
                .assetUploadId("asset-102")
                .headers('{"Content-Type":"image/png"}')
                .build()

        then:
        verifyAll(twoArg) {
            uploadUrl() == "https://uploads.example.com/1"
            assetUploadId() == "asset-101"
            headers() == null
        }
        verifyAll(direct) {
            uploadUrl() == "https://uploads.example.com/2"
            assetUploadId() == "asset-102"
            headers() == '{"Content-Type":"image/png"}'
        }
        verifyAll(built) {
            it == direct
            uploadUrl() == "https://uploads.example.com/2"
            assetUploadId() == "asset-102"
            headers() == '{"Content-Type":"image/png"}'
        }
    }

    def "should update components using record withers"() {
        given:
        GuideMediaUploadUrlResponse original = new GuideMediaUploadUrlResponse(
                "https://uploads.example.com/old",
                "asset-old",
                '{"Content-Type":"text/plain"}'
        )

        when:
        GuideMediaUploadUrlResponse updated = original
                .withUploadUrl("https://uploads.example.com/new")
                .withAssetUploadId("asset-new")
                .withHeaders('{"Content-Type":"image/jpeg"}')

        then:
        verifyAll(original) {
            uploadUrl() == "https://uploads.example.com/old"
            assetUploadId() == "asset-old"
            headers() == '{"Content-Type":"text/plain"}'
        }
        verifyAll(updated) {
            uploadUrl() == "https://uploads.example.com/new"
            assetUploadId() == "asset-new"
            headers() == '{"Content-Type":"image/jpeg"}'
        }
    }

    def "should round-trip serialize and deserialize GuideMediaUploadUrlResponse"() {
        given:
        GuideMediaUploadUrlResponse response = new GuideMediaUploadUrlResponse(
                "https://uploads.example.com/555",
                "asset-555",
                '{"x-amz-acl":"private"}'
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMediaUploadUrlResponse deserialized = objectMapper.readValue(json, GuideMediaUploadUrlResponse.class)

        then:
        verifyAll(map) {
            get("upload_url") == "https://uploads.example.com/555"
            get("asset_upload_id") == "asset-555"
            get("headers") == '{"x-amz-acl":"private"}'
        }
        verifyAll(deserialized) {
            it == response
            uploadUrl() == "https://uploads.example.com/555"
            assetUploadId() == "asset-555"
            headers() == '{"x-amz-acl":"private"}'
        }
    }

    def "should handle null components in constructor, wither, and serde"() {
        when:
        GuideMediaUploadUrlResponse response = new GuideMediaUploadUrlResponse(null, null, null)
        GuideMediaUploadUrlResponse updated = response.withUploadUrl("https://uploads.example.com/99")
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMediaUploadUrlResponse deserialized = objectMapper.readValue(json, GuideMediaUploadUrlResponse.class)

        then:
        verifyAll(response) {
            uploadUrl() == null
            assetUploadId() == null
            headers() == null
        }
        verifyAll(updated) {
            uploadUrl() == "https://uploads.example.com/99"
            assetUploadId() == null
            headers() == null
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("upload_url")
            !containsKey("asset_upload_id")
            !containsKey("headers")
        }
        verifyAll(deserialized) {
            it == response
            uploadUrl() == null
            assetUploadId() == null
            headers() == null
        }
    }
}
