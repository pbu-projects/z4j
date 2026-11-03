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

class BulkAttachmentsRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create BulkAttachmentsRequest with canonical constructor and builder"() {
        given:
        List<Long> ids = [101L, 102L]

        when:
        BulkAttachmentsRequest direct = new BulkAttachmentsRequest(ids)
        BulkAttachmentsRequest built = BulkAttachmentsRequestBuilder.builder()
                .attachmentIds(ids)
                .build()

        then:
        verifyAll(direct) {
            attachmentIds() == ids
            attachmentIds().size() == 2
            attachmentIds().first() == 101L
            attachmentIds().last() == 102L
        }
        verifyAll(built) {
            it == direct
            attachmentIds() == ids
            attachmentIds().size() == 2
            attachmentIds().first() == 101L
            attachmentIds().last() == 102L
        }
    }

    def "should update attachmentIds using record wither and addAttachmentIdsItem method"() {
        given:
        BulkAttachmentsRequest request = new BulkAttachmentsRequest([1L])

        when:
        BulkAttachmentsRequest withNewList = request.withAttachmentIds([2L])
        BulkAttachmentsRequest withAdded = request.addAttachmentIdsItem(2L)
        BulkAttachmentsRequest fromNull = new BulkAttachmentsRequest(null).addAttachmentIdsItem(1L)

        then:
        verifyAll(request) {
            attachmentIds().size() == 1
            attachmentIds().first() == 1L
        }
        verifyAll(withNewList) {
            attachmentIds().size() == 1
            attachmentIds().first() == 2L
        }
        verifyAll(withAdded) {
            attachmentIds().size() == 2
            attachmentIds().first() == 1L
            attachmentIds().last() == 2L
        }
        verifyAll(fromNull) {
            attachmentIds().size() == 1
            attachmentIds().first() == 1L
        }
    }

    def "should round-trip serialize and deserialize BulkAttachmentsRequest"() {
        given:
        BulkAttachmentsRequest request = new BulkAttachmentsRequest([555L, 777L])

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        BulkAttachmentsRequest deserialized = objectMapper.readValue(json, BulkAttachmentsRequest.class)

        then:
        verifyAll(map) {
            containsKey("attachment_ids")
            get("attachment_ids") instanceof List
            ((List) get("attachment_ids")) == [555, 777]
        }
        verifyAll(deserialized) {
            it == request
            attachmentIds() != null
            attachmentIds() == [555L, 777L]
        }
    }

    def "should handle null attachmentIds component in constructor, wither, and serde"() {
        when:
        BulkAttachmentsRequest request = new BulkAttachmentsRequest(null)
        BulkAttachmentsRequest updated = request.withAttachmentIds([99L])
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        BulkAttachmentsRequest deserialized = objectMapper.readValue(json, BulkAttachmentsRequest.class)

        then:
        verifyAll(request) {
            attachmentIds() == null
        }
        verifyAll(updated) {
            attachmentIds() != null
            attachmentIds() == [99L]
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("attachment_ids")
        }
        verifyAll(deserialized) {
            it == request
            attachmentIds() == null
        }
    }
}
