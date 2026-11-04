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

class AttachmentUploadResponseUploadSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create AttachmentUploadResponseUpload with constructors and builder"() {
        given:
        AttachmentObject expectedAttachment = new AttachmentObject().withId(10L).withFileName("photo.png")
        List<AttachmentObject> expectedAttachments = [expectedAttachment]

        when:
        AttachmentUploadResponseUpload direct = new AttachmentUploadResponseUpload("tok_1", expectedAttachment, expectedAttachments)
        AttachmentUploadResponseUpload noArg = new AttachmentUploadResponseUpload()
        AttachmentUploadResponseUpload built = AttachmentUploadResponseUploadBuilder.builder()
                .token("tok_1")
                .attachment(expectedAttachment)
                .attachments(expectedAttachments)
                .build()

        then:
        verifyAll(direct) {
            it.token() == "tok_1"
            it.attachment() == expectedAttachment
            it.attachments() == expectedAttachments
        }
        verifyAll(noArg) {
            it.token() == null
            it.attachment() == null
            it.attachments() == null
        }
        verifyAll(built) {
            it == direct
            it.token() == "tok_1"
            it.attachment() == expectedAttachment
            it.attachments() == expectedAttachments
        }
    }

    def "should update AttachmentUploadResponseUpload with record withers and addAttachmentsItem"() {
        given:
        AttachmentObject item1 = new AttachmentObject().withId(1L).withFileName("one.pdf")
        AttachmentObject item2 = new AttachmentObject().withId(2L).withFileName("two.pdf")
        AttachmentUploadResponseUpload initial = new AttachmentUploadResponseUpload("tok_initial", null, null)

        when:
        AttachmentUploadResponseUpload updated = initial
                .withToken("tok_updated")
                .withAttachment(item1)
                .withAttachments([item1])
        AttachmentUploadResponseUpload addedToNull = new AttachmentUploadResponseUpload().addAttachmentsItem(item1)
        AttachmentUploadResponseUpload addedToExisting = addedToNull.addAttachmentsItem(item2)

        then:
        verifyAll(updated) {
            it.token() == "tok_updated"
            it.attachment() == item1
            it.attachments() == [item1]
        }
        verifyAll(addedToNull) {
            it.attachments() != null
            it.attachments().size() == 1
            it.attachments().first() == item1
        }
        verifyAll(addedToExisting) {
            it.attachments() != null
            it.attachments().size() == 2
            it.attachments()[0] == item1
            it.attachments()[1] == item2
        }
    }

    def "should round-trip serialize and deserialize AttachmentUploadResponseUpload"() {
        given:
        AttachmentObject expectedAttachment = new AttachmentObject().withId(99L).withFileName("scan.pdf")
        AttachmentUploadResponseUpload upload = new AttachmentUploadResponseUpload("tok_99", expectedAttachment, [expectedAttachment])

        when:
        String json = objectMapper.writeValueAsString(upload)
        Map map = objectMapper.readValue(json, Map.class)
        AttachmentUploadResponseUpload deserialized = objectMapper.readValue(json, AttachmentUploadResponseUpload.class)

        then:
        verifyAll(map) {
            containsKey("token")
            containsKey("attachment")
            containsKey("attachments")
            get("token") == "tok_99"
        }
        verifyAll(deserialized) {
            it == upload
            it.token() == "tok_99"
            it.attachment() != null
            it.attachment().id == 99L
            it.attachments() != null
            it.attachments().size() == 1
        }
    }

    def "should handle null fields in serialization"() {
        when:
        AttachmentUploadResponseUpload empty = new AttachmentUploadResponseUpload(null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        AttachmentUploadResponseUpload deserialized = objectMapper.readValue(json, AttachmentUploadResponseUpload.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            it.token() == null
            it.attachment() == null
            it.attachments() == null
        }
    }
}
