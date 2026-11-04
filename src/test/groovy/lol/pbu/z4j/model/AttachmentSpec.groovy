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
import spock.lang.Shared
import spock.lang.Specification

class AttachmentSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Attachment with canonical constructor, no-arg constructor, and builder"() {
        given:
        Attachment childThumb = new Attachment()
                .withId(102L)
                .withFileName("thumb.png")
                .withContentType("image/png")
                .withSize(512)

        when:
        Attachment direct = new Attachment(
                "image/png",
                "https://example.com/file.png",
                false,
                "file.png",
                "480",
                101L,
                true,
                false,
                AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND,
                "https://mapped.example.com/file.png",
                1024,
                "https://example.zendesk.com/api/v2/attachments/101.json",
                "640",
                [childThumb]
        )
        Attachment noArg = new Attachment()
        Attachment built = AttachmentBuilder.builder()
                .contentType("image/png")
                .contentUrl("https://example.com/file.png")
                .deleted(false)
                .fileName("file.png")
                .height("480")
                .id(101L)
                .inline(true)
                .malwareAccessOverride(false)
                .malwareScanResult(AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND)
                .mappedContentUrl("https://mapped.example.com/file.png")
                .size(1024)
                .url("https://example.zendesk.com/api/v2/attachments/101.json")
                .width("640")
                .thumbnails([childThumb])
                .build()

        then:
        verifyAll(direct) {
            contentType() == "image/png"
            contentUrl() == "https://example.com/file.png"
            deleted() == false
            fileName() == "file.png"
            height() == "480"
            id() == 101L
            inline() == true
            malwareAccessOverride() == false
            malwareScanResult() == AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND
            mappedContentUrl() == "https://mapped.example.com/file.png"
            size() == 1024
            url() == "https://example.zendesk.com/api/v2/attachments/101.json"
            width() == "640"
            thumbnails() == [childThumb]
        }
        verifyAll(noArg) {
            id() == null
            fileName() == null
            thumbnails() == null
        }
        verifyAll(built) {
            it == direct
            id() == 101L
            fileName() == "file.png"
        }
    }

    def "should update Attachment using record withers and addThumbnailsItem"() {
        given:
        Attachment child1 = new Attachment().withId(1L).withFileName("thumb1.png")
        Attachment child2 = new Attachment().withId(2L).withFileName("thumb2.png")
        Attachment initial = new Attachment()

        when:
        Attachment updated = initial
                .withContentType("text/plain")
                .withContentUrl("https://example.com/doc.txt")
                .withDeleted(true)
                .withFileName("doc.txt")
                .withHeight("100")
                .withId(500L)
                .withInline(false)
                .withMalwareAccessOverride(true)
                .withMalwareScanResult(AttachmentBaseMalwareScanResult.NOT_SCANNED)
                .withMappedContentUrl("https://mapped.example.com/doc.txt")
                .withSize(2048)
                .withUrl("https://example.zendesk.com/api/v2/attachments/500.json")
                .withWidth("200")
                .withThumbnails([child1])
        Attachment added = updated.addThumbnailsItem(child2)
        Attachment fromNull = new Attachment().addThumbnailsItem(child1)

        then:
        verifyAll(updated) {
            contentType() == "text/plain"
            contentUrl() == "https://example.com/doc.txt"
            deleted() == true
            fileName() == "doc.txt"
            height() == "100"
            id() == 500L
            inline() == false
            malwareAccessOverride() == true
            malwareScanResult() == AttachmentBaseMalwareScanResult.NOT_SCANNED
            mappedContentUrl() == "https://mapped.example.com/doc.txt"
            size() == 2048
            url() == "https://example.zendesk.com/api/v2/attachments/500.json"
            width() == "200"
            thumbnails().size() == 1
        }
        verifyAll(added) {
            thumbnails().size() == 2
            thumbnails().first() == child1
            thumbnails().last() == child2
        }
        verifyAll(fromNull) {
            thumbnails().size() == 1
            thumbnails().first() == child1
        }
    }

    def "should round-trip serialize and deserialize Attachment"() {
        given:
        Attachment item = new Attachment()
                .withId(777L)
                .withFileName("photo.jpeg")
                .withContentType("image/jpeg")
                .withSize(4096)
                .withInline(true)
                .withDeleted(false)
                .withMalwareScanResult(AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND)

        when:
        String json = objectMapper.writeValueAsString(item)
        Map map = objectMapper.readValue(json, Map.class)
        Attachment deserialized = objectMapper.readValue(json, Attachment.class)

        then:
        verifyAll(map) {
            get("id") == 777
            get("file_name") == "photo.jpeg"
            get("content_type") == "image/jpeg"
            get("size") == 4096
            get("inline") == true
            get("deleted") == false
            get("malware_scan_result") == "malware_not_found"
        }
        verifyAll(deserialized) {
            it == item
            id() == 777L
            fileName() == "photo.jpeg"
            contentType() == "image/jpeg"
            size() == 4096
            inline() == true
            deleted() == false
            malwareScanResult() == AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND
        }
    }

    def "should verify AttachmentBase constants"() {
        expect:
        verifyAll {
            AttachmentBase.JSON_PROPERTY_CONTENT_TYPE == "content_type"
            AttachmentBase.JSON_PROPERTY_CONTENT_URL == "content_url"
            AttachmentBase.JSON_PROPERTY_DELETED == "deleted"
            AttachmentBase.JSON_PROPERTY_FILE_NAME == "file_name"
            AttachmentBase.JSON_PROPERTY_HEIGHT == "height"
            AttachmentBase.JSON_PROPERTY_ID == "id"
            AttachmentBase.JSON_PROPERTY_INLINE == "inline"
            AttachmentBase.JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE == "malware_access_override"
            AttachmentBase.JSON_PROPERTY_MALWARE_SCAN_RESULT == "malware_scan_result"
            AttachmentBase.JSON_PROPERTY_MAPPED_CONTENT_URL == "mapped_content_url"
            AttachmentBase.JSON_PROPERTY_SIZE == "size"
            AttachmentBase.JSON_PROPERTY_URL == "url"
            AttachmentBase.JSON_PROPERTY_WIDTH == "width"
            Attachment.JSON_PROPERTY_THUMBNAILS == "thumbnails"
        }
    }
}
