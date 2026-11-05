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

class GuideMediaUploadUrlRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create GuideMediaUploadUrlRequest with canonical constructor and builder"() {
        when:
        GuideMediaUploadUrlRequest direct = new GuideMediaUploadUrlRequest("image/png", 1024L)
        GuideMediaUploadUrlRequest built = GuideMediaUploadUrlRequestBuilder.builder()
                .contentType("image/png")
                .fileSize(1024L)
                .build()

        then:
        verifyAll(direct) {
            contentType() == "image/png"
            fileSize() == 1024L
        }
        verifyAll(built) {
            it == direct
            contentType() == "image/png"
            fileSize() == 1024L
        }
    }

    def "should update components using record withers"() {
        given:
        GuideMediaUploadUrlRequest original = new GuideMediaUploadUrlRequest("text/plain", 100L)

        when:
        GuideMediaUploadUrlRequest updated = original
                .withContentType("image/jpeg")
                .withFileSize(2048L)

        then:
        verifyAll(original) {
            contentType() == "text/plain"
            fileSize() == 100L
        }
        verifyAll(updated) {
            contentType() == "image/jpeg"
            fileSize() == 2048L
        }
    }

    def "should round-trip serialize and deserialize GuideMediaUploadUrlRequest"() {
        given:
        GuideMediaUploadUrlRequest request = new GuideMediaUploadUrlRequest("application/pdf", 4096L)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMediaUploadUrlRequest deserialized = objectMapper.readValue(json, GuideMediaUploadUrlRequest.class)

        then:
        verifyAll(map) {
            get("content_type") == "application/pdf"
            get("file_size") == 4096
        }
        verifyAll(deserialized) {
            it == request
            contentType() == "application/pdf"
            fileSize() == 4096L
        }
    }

    def "should handle null components in constructor, wither, and serde"() {
        when:
        GuideMediaUploadUrlRequest request = new GuideMediaUploadUrlRequest(null, null)
        GuideMediaUploadUrlRequest updated = request.withContentType("text/csv")
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        GuideMediaUploadUrlRequest deserialized = objectMapper.readValue(json, GuideMediaUploadUrlRequest.class)

        then:
        verifyAll(request) {
            contentType() == null
            fileSize() == null
        }
        verifyAll(updated) {
            contentType() == "text/csv"
            fileSize() == null
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("content_type")
            !containsKey("file_size")
        }
        verifyAll(deserialized) {
            it == request
            contentType() == null
            fileSize() == null
        }
    }
}
