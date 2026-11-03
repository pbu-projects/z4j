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

class GuideMediaCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create GuideMediaCreateRequest with convenience constructors, canonical constructor, and builder"() {
        given:
        GuideMedia expectedMedia = new GuideMedia("diagram.png").setId("01ARZ3NDEK")

        when:
        GuideMediaCreateRequest fromMedia = new GuideMediaCreateRequest(expectedMedia)
        GuideMediaCreateRequest fromNullMedia = new GuideMediaCreateRequest((GuideMedia) null)
        GuideMediaCreateRequest fromFilename = new GuideMediaCreateRequest("banner.png")
        GuideMediaCreateRequest fromAssetAndFilename = new GuideMediaCreateRequest("asset-101", "banner.png")
        GuideMediaCreateRequest direct = new GuideMediaCreateRequest(expectedMedia, "asset-102", "diagram.png")
        GuideMediaCreateRequest built = GuideMediaCreateRequestBuilder.builder()
                .guideMedia(expectedMedia)
                .assetUploadId("asset-102")
                .filename("diagram.png")
                .build()

        then:
        verifyAll(fromMedia) {
            guideMedia() == expectedMedia
            assetUploadId() == null
            filename() == "diagram.png"
        }
        verifyAll(fromNullMedia) {
            guideMedia() == null
            assetUploadId() == null
            filename() == null
        }
        verifyAll(fromFilename) {
            guideMedia() == new GuideMedia("banner.png")
            assetUploadId() == null
            filename() == "banner.png"
        }
        verifyAll(fromAssetAndFilename) {
            guideMedia() == new GuideMedia("banner.png")
            assetUploadId() == "asset-101"
            filename() == "banner.png"
        }
        verifyAll(direct) {
            guideMedia() == expectedMedia
            assetUploadId() == "asset-102"
            filename() == "diagram.png"
        }
        verifyAll(built) {
            it == direct
            guideMedia() == expectedMedia
            assetUploadId() == "asset-102"
            filename() == "diagram.png"
        }
    }

    def "should update components using record withers"() {
        given:
        GuideMedia originalMedia = new GuideMedia("old.png")
        GuideMedia newMedia = new GuideMedia("new.png")
        GuideMediaCreateRequest original = new GuideMediaCreateRequest(originalMedia, "asset-old", "old.png")

        when:
        GuideMediaCreateRequest updated = original
                .withGuideMedia(newMedia)
                .withAssetUploadId("asset-new")
                .withFilename("new.png")

        then:
        verifyAll(original) {
            guideMedia() == originalMedia
            assetUploadId() == "asset-old"
            filename() == "old.png"
        }
        verifyAll(updated) {
            guideMedia() == newMedia
            assetUploadId() == "asset-new"
            filename() == "new.png"
        }
    }

    def "should round-trip serialize and deserialize GuideMediaCreateRequest"() {
        given:
        GuideMedia media = new GuideMedia("screenshot.png").setId("01ARZ3NDEK555")
        GuideMediaCreateRequest request = new GuideMediaCreateRequest(media, "asset-555", "screenshot.png")

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMediaCreateRequest deserialized = objectMapper.readValue(json, GuideMediaCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("guide_media")
            ((Map) get("guide_media")).id == "01ARZ3NDEK555"
            ((Map) get("guide_media")).name == "screenshot.png"
            get("asset_upload_id") == "asset-555"
            get("filename") == "screenshot.png"
        }
        verifyAll(deserialized) {
            it == request
            guideMedia() != null
            guideMedia().id == "01ARZ3NDEK555"
            guideMedia().name == "screenshot.png"
            assetUploadId() == "asset-555"
            filename() == "screenshot.png"
        }
    }

    def "should handle null components in constructor, wither, and serde"() {
        when:
        GuideMediaCreateRequest request = new GuideMediaCreateRequest(null, null, null)
        GuideMediaCreateRequest updated = request.withFilename("added.png")
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMediaCreateRequest deserialized = objectMapper.readValue(json, GuideMediaCreateRequest.class)

        then:
        verifyAll(request) {
            guideMedia() == null
            assetUploadId() == null
            filename() == null
        }
        verifyAll(updated) {
            guideMedia() == null
            assetUploadId() == null
            filename() == "added.png"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("guide_media")
            !containsKey("asset_upload_id")
            !containsKey("filename")
        }
        verifyAll(deserialized) {
            it == request
            guideMedia() == null
            assetUploadId() == null
            filename() == null
        }
    }
}
