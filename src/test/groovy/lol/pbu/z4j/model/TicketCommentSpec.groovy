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

import java.time.ZoneOffset
import java.time.ZonedDateTime

class TicketCommentSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create TicketComment with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC)
        Attachment attachment = new Attachment().withId(10L).withFileName("file.png")
        TicketAuditVia expectedVia = new TicketAuditVia()

        when:
        TicketComment direct = new TicketComment(
                [attachment],
                100L,
                200L,
                "Comment body",
                now,
                "<p>Comment body</p>",
                300L,
                [key: "val"],
                "Comment body",
                true,
                "Comment",
                ["token_123"],
                expectedVia
        )
        TicketComment noArg = new TicketComment()
        TicketComment bodyOnly = new TicketComment("Just body")
        TicketComment bodyAndPublic = new TicketComment("Private note", false)
        TicketComment built = TicketCommentBuilder.builder()
                .attachments([attachment])
                .auditId(100L)
                .authorId(200L)
                .body("Comment body")
                .createdAt(now)
                .htmlBody("<p>Comment body</p>")
                .id(300L)
                .metadata([key: "val"])
                .plainBody("Comment body")
                .isPublic(true)
                .type("Comment")
                .uploads(["token_123"])
                .via(expectedVia)
                .build()

        then:
        verifyAll(direct) {
            attachments() == [attachment]
            auditId() == 100L
            authorId() == 200L
            body() == "Comment body"
            createdAt() == now
            htmlBody() == "<p>Comment body</p>"
            id() == 300L
            metadata() == [key: "val"]
            plainBody() == "Comment body"
            isPublic() == true
            type() == "Comment"
            uploads() == ["token_123"]
            via == expectedVia
        }
        verifyAll(noArg) {
            body() == null
            id() == null
            isPublic() == null
        }
        verifyAll(bodyOnly) {
            body() == "Just body"
            isPublic() == null
        }
        verifyAll(bodyAndPublic) {
            body() == "Private note"
            isPublic() == false
        }
        verifyAll(built) {
            it == direct
            id() == 300L
            body() == "Comment body"
        }
    }

    def "should update TicketComment using withers, addUploadsItem, and addAttachmentsItem"() {
        given:
        Attachment att1 = new Attachment().withId(1L)
        Attachment att2 = new Attachment().withId(2L)
        TicketComment initial = new TicketComment()

        when:
        TicketComment updated = initial
                .withBody("Updated comment")
                .withIsPublic(false)
                .withUploads(["tok_1"])
                .withAttachments([att1])
        TicketComment addedUpload = updated.addUploadsItem("tok_2")
        TicketComment addedAttachment = updated.addAttachmentsItem(att2)
        TicketComment fromNullUpload = new TicketComment().addUploadsItem("tok_first")
        TicketComment fromNullAttachment = new TicketComment().addAttachmentsItem(att1)

        then:
        verifyAll(updated) {
            body() == "Updated comment"
            isPublic() == false
            uploads().size() == 1
            attachments().size() == 1
        }
        verifyAll(addedUpload) {
            uploads().size() == 2
            uploads().first() == "tok_1"
            uploads().last() == "tok_2"
        }
        verifyAll(addedAttachment) {
            attachments().size() == 2
            attachments().first() == att1
            attachments().last() == att2
        }
        verifyAll(fromNullUpload) {
            uploads().size() == 1
            uploads().first() == "tok_first"
        }
        verifyAll(fromNullAttachment) {
            attachments().size() == 1
            attachments().first() == att1
        }
    }

    def "should round-trip serialize and deserialize TicketComment"() {
        given:
        TicketComment comment = new TicketComment()
                .withId(999L)
                .withBody("Roundtrip comment")
                .withIsPublic(true)
                .withType("Comment")

        when:
        String json = objectMapper.writeValueAsString(comment)
        Map map = objectMapper.readValue(json, Map.class)
        TicketComment deserialized = objectMapper.readValue(json, TicketComment.class)

        then:
        verifyAll(map) {
            get("id") == 999
            get("body") == "Roundtrip comment"
            get("public") == true
            get("type") == "Comment"
        }
        verifyAll(deserialized) {
            it == comment
            id() == 999L
            body() == "Roundtrip comment"
            isPublic() == true
            type() == "Comment"
        }
    }

    def "should verify TicketComment constants"() {
        expect:
        verifyAll {
            TicketComment.JSON_PROPERTY_ATTACHMENTS == "attachments"
            TicketComment.JSON_PROPERTY_AUDIT_ID == "audit_id"
            TicketComment.JSON_PROPERTY_AUTHOR_ID == "author_id"
            TicketComment.JSON_PROPERTY_BODY == "body"
            TicketComment.JSON_PROPERTY_CREATED_AT == "created_at"
            TicketComment.JSON_PROPERTY_HTML_BODY == "html_body"
            TicketComment.JSON_PROPERTY_ID == "id"
            TicketComment.JSON_PROPERTY_METADATA == "metadata"
            TicketComment.JSON_PROPERTY_PLAIN_BODY == "plain_body"
            TicketComment.JSON_PROPERTY_PUBLIC == "public"
            TicketComment.JSON_PROPERTY_TYPE == "type"
            TicketComment.JSON_PROPERTY_UPLOADS == "uploads"
            TicketComment.JSON_PROPERTY_VIA == "via"
        }
    }
}
