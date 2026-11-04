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

class AttachmentUploadResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create AttachmentUploadResponse with canonical constructor and builder"() {
        given:
        AttachmentUploadResponseUpload expected = new AttachmentUploadResponseUpload()
                .withToken("tok_101")
                .withAttachment(new AttachmentObject().withId(201L).withFileName("report.pdf"))

        when:
        AttachmentUploadResponse direct = new AttachmentUploadResponse(expected)
        AttachmentUploadResponse built = AttachmentUploadResponseBuilder.builder().upload(expected).build()

        then:
        verifyAll(direct) {
            upload == expected
            upload.token == "tok_101"
            upload.attachment.id == 201L
            upload.attachment.fileName == "report.pdf"
        }
        verifyAll(built) {
            upload == expected
            upload.token == "tok_101"
            upload.attachment.id == 201L
            upload.attachment.fileName == "report.pdf"
        }
    }

    def "should update upload using record wither"() {
        given:
        AttachmentUploadResponseUpload original = new AttachmentUploadResponseUpload().withToken("tok_1")
        AttachmentUploadResponseUpload updatedUpload = new AttachmentUploadResponseUpload().withToken("tok_2")
        AttachmentUploadResponse response = new AttachmentUploadResponse(original)

        when:
        AttachmentUploadResponse updated = response.withUpload(updatedUpload)

        then:
        verifyAll(response) {
            upload == original
            upload.token == "tok_1"
        }
        verifyAll(updated) {
            upload == updatedUpload
            upload.token == "tok_2"
        }
    }

    def "should round-trip serialize and deserialize AttachmentUploadResponse"() {
        given:
        AttachmentUploadResponseUpload expected = new AttachmentUploadResponseUpload()
                .withToken("tok_555")
                .withAttachment(new AttachmentObject().withId(777L).withFileName("photo.jpg"))
        AttachmentUploadResponse response = new AttachmentUploadResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        AttachmentUploadResponse deserialized = objectMapper.readValue(json, AttachmentUploadResponse.class)

        then:
        verifyAll(map) {
            containsKey("upload")
            get("upload") instanceof Map
            ((Map) get("upload")).token == "tok_555"
        }
        verifyAll(deserialized) {
            it == response
            upload != null
            upload.token == "tok_555"
            upload.attachment != null
            upload.attachment.id == 777L
            upload.attachment.fileName == "photo.jpg"
        }
    }

    def "should handle null upload component in constructor and wither"() {
        when:
        AttachmentUploadResponse response = new AttachmentUploadResponse(null)
        AttachmentUploadResponseUpload replacement = new AttachmentUploadResponseUpload().withToken("tok_99")
        AttachmentUploadResponse updated = response.withUpload(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        AttachmentUploadResponse deserialized = objectMapper.readValue(json, AttachmentUploadResponse.class)

        then:
        verifyAll(response) {
            upload() == null
        }
        verifyAll(updated) {
            upload() == replacement
            upload().token == "tok_99"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("upload")
        }
        verifyAll(deserialized) {
            it == response
            upload() == null
        }
    }
}
