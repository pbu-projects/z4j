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

class AttachmentObjectSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create AttachmentObject with constructors and builder"() {
        given:
        AttachmentObject child = new AttachmentObject()
                .withId(102L)
                .withFileName("thumb.png")
                .withSize(512)

        when:
        AttachmentObject direct = new AttachmentObject(
                101L,
                "photo.png",
                "https://example.com/photo.png",
                "image/png",
                1024,
                [child],
                true,
                false,
                "https://example.zendesk.com/api/v2/attachments/101.json",
                "640",
                "480",
                AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND,
                false,
                "https://mapped.example.com/photo.png"
        )
        AttachmentObject noArg = new AttachmentObject()
        AttachmentObject built = AttachmentObjectBuilder.builder()
                .id(101L)
                .fileName("photo.png")
                .contentUrl("https://example.com/photo.png")
                .contentType("image/png")
                .size(1024)
                .thumbnails([child])
                .inline(true)
                .deleted(false)
                .url("https://example.zendesk.com/api/v2/attachments/101.json")
                .width("640")
                .height("480")
                .malwareScanResult(AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND)
                .malwareAccessOverride(false)
                .mappedContentUrl("https://mapped.example.com/photo.png")
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
            fileName() == "photo.png"
            contentUrl() == "https://example.com/photo.png"
            contentType() == "image/png"
            size() == 1024
            thumbnails() == [child]
            inline() == true
            deleted() == false
            url() == "https://example.zendesk.com/api/v2/attachments/101.json"
            width() == "640"
            height() == "480"
            malwareScanResult() == AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND
            malwareAccessOverride() == false
            mappedContentUrl() == "https://mapped.example.com/photo.png"
        }
        verifyAll(noArg) {
            id() == null
            fileName() == null
            thumbnails() == null
        }
        verifyAll(built) {
            it == direct
            id() == 101L
            fileName() == "photo.png"
        }
    }

    def "should update AttachmentObject using withers and addThumbnailsItem"() {
        given:
        AttachmentObject child1 = new AttachmentObject().withId(1L).withFileName("t1.png")
        AttachmentObject child2 = new AttachmentObject().withId(2L).withFileName("t2.png")
        AttachmentObject initial = new AttachmentObject()

        when:
        AttachmentObject updated = initial
                .withId(200L)
                .withFileName("report.pdf")
                .withContentUrl("https://example.com/report.pdf")
                .withContentType("application/pdf")
                .withSize(2048L)
                .withWidth(800L)
                .withHeight(600L)
                .withInline(false)
                .withDeleted(true)
                .withUrl("https://example.com/api/v2/attachments/200.json")
                .withMalwareScanResult("malware_not_found")
                .withMalwareAccessOverride(true)
                .withMappedContentUrl("https://mapped.example.com/report.pdf")
                .withThumbnails([child1])
        AttachmentObject added = updated.addThumbnailsItem(child2)
        AttachmentObject fromNull = new AttachmentObject().addThumbnailsItem(child1)

        then:
        verifyAll(updated) {
            id() == 200L
            fileName() == "report.pdf"
            contentUrl() == "https://example.com/report.pdf"
            contentType() == "application/pdf"
            size() == 2048
            width() == "800"
            height() == "600"
            inline() == false
            deleted() == true
            url() == "https://example.com/api/v2/attachments/200.json"
            malwareScanResult() == AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND
            malwareAccessOverride() == true
            mappedContentUrl() == "https://mapped.example.com/report.pdf"
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

    def "should round-trip serialize and deserialize AttachmentObject"() {
        given:
        AttachmentObject obj = new AttachmentObject()
                .withId(300L)
                .withFileName("scan.pdf")
                .withContentType("application/pdf")
                .withSize(1024)
                .withInline(true)
                .withDeleted(false)
                .withMalwareScanResult(AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND)

        when:
        String json = objectMapper.writeValueAsString(obj)
        Map map = objectMapper.readValue(json, Map.class)
        AttachmentObject deserialized = objectMapper.readValue(json, AttachmentObject.class)

        then:
        verifyAll(map) {
            get("id") == 300
            get("file_name") == "scan.pdf"
            get("content_type") == "application/pdf"
            get("size") == 1024
            get("inline") == true
            get("deleted") == false
            get("malware_scan_result") == "malware_not_found"
        }
        verifyAll(deserialized) {
            it == obj
            id() == 300L
            fileName() == "scan.pdf"
            contentType() == "application/pdf"
            size() == 1024
            inline() == true
            deleted() == false
            malwareScanResult() == AttachmentBaseMalwareScanResult.MALWARE_NOT_FOUND
        }
    }

    def "should handle null overloaded withers on AttachmentObject"() {
        when:
        AttachmentObject obj = new AttachmentObject()
                .withSize((Long) null)
                .withWidth((Long) null)
                .withHeight((Long) null)
                .withMalwareScanResult((String) null)

        then:
        verifyAll(obj) {
            size() == null
            width() == null
            height() == null
            malwareScanResult() == null
        }
    }
}
