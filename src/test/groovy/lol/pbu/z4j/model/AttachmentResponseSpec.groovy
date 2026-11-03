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

class AttachmentResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create AttachmentResponse with canonical constructor and builder"() {
        given:
        AttachmentObject expected = new AttachmentObject().setId(101L).setFileName("guide.pdf").setSize(2048L)

        when:
        AttachmentResponse direct = new AttachmentResponse(expected)
        AttachmentResponse built = AttachmentResponseBuilder.builder().attachment(expected).build()

        then:
        verifyAll(direct) {
            attachment == expected
            attachment.id == 101L
            attachment.fileName == "guide.pdf"
            attachment.size == 2048L
        }
        verifyAll(built) {
            attachment == expected
            attachment.id == 101L
            attachment.fileName == "guide.pdf"
            attachment.size == 2048L
        }
    }

    def "should update attachment using record wither"() {
        given:
        AttachmentObject original = new AttachmentObject().setId(1L).setFileName("old.pdf")
        AttachmentObject updatedAttachment = new AttachmentObject().setId(2L).setFileName("new.pdf")
        AttachmentResponse response = new AttachmentResponse(original)

        when:
        AttachmentResponse updated = response.withAttachment(updatedAttachment)

        then:
        verifyAll(response) {
            attachment == original
            attachment.fileName == "old.pdf"
        }
        verifyAll(updated) {
            attachment == updatedAttachment
            attachment.fileName == "new.pdf"
        }
    }

    def "should round-trip serialize and deserialize AttachmentResponse"() {
        given:
        AttachmentObject expected = new AttachmentObject().setId(555L).setFileName("manual.pdf").setSize(4096L)
        AttachmentResponse response = new AttachmentResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        AttachmentResponse deserialized = objectMapper.readValue(json, AttachmentResponse.class)

        then:
        verifyAll(map) {
            containsKey("attachment")
            get("attachment") instanceof Map
            ((Map) get("attachment")).id == 555
            ((Map) get("attachment")).file_name == "manual.pdf"
        }
        verifyAll(deserialized) {
            it == response
            attachment != null
            attachment.id == 555L
            attachment.fileName == "manual.pdf"
            attachment.size == 4096L
        }
    }

    def "should handle null attachment component in constructor and wither"() {
        when:
        AttachmentResponse response = new AttachmentResponse(null)
        AttachmentObject replacement = new AttachmentObject().setId(99L).setFileName("notes.txt")
        AttachmentResponse updated = response.withAttachment(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        AttachmentResponse deserialized = objectMapper.readValue(json, AttachmentResponse.class)

        then:
        verifyAll(response) {
            attachment() == null
        }
        verifyAll(updated) {
            attachment() == replacement
            attachment().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("attachment")
        }
        verifyAll(deserialized) {
            it == response
            attachment() == null
        }
    }
}
