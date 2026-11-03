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

class ContentTagCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ContentTagCreateRequest with constructors and builder"() {
        given:
        ContentTag expected = new ContentTag("troubleshooting").setId("01ARZ3NDEK")

        when:
        ContentTagCreateRequest byName = new ContentTagCreateRequest("troubleshooting")
        ContentTagCreateRequest direct = new ContentTagCreateRequest(expected)
        ContentTagCreateRequest built = ContentTagCreateRequestBuilder.builder().contentTag(expected).build()

        then:
        verifyAll(byName) {
            contentTag() != null
            contentTag().name == "troubleshooting"
        }
        verifyAll(direct) {
            contentTag() == expected
            contentTag().id == "01ARZ3NDEK"
            contentTag().name == "troubleshooting"
        }
        verifyAll(built) {
            it == direct
            contentTag() == expected
            contentTag().id == "01ARZ3NDEK"
            contentTag().name == "troubleshooting"
        }
    }

    def "should update contentTag using record wither"() {
        given:
        ContentTag originalTag = new ContentTag("billing")
        ContentTag newTag = new ContentTag("account")
        ContentTagCreateRequest request = new ContentTagCreateRequest(originalTag)

        when:
        ContentTagCreateRequest updated = request.withContentTag(newTag)

        then:
        verifyAll(request) {
            contentTag() == originalTag
            contentTag().name == "billing"
        }
        verifyAll(updated) {
            contentTag() == newTag
            contentTag().name == "account"
        }
    }

    def "should round-trip serialize and deserialize ContentTagCreateRequest"() {
        given:
        ContentTag expected = new ContentTag("security").setId("01ARZ3NDEK001")
        ContentTagCreateRequest request = new ContentTagCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagCreateRequest deserialized = objectMapper.readValue(json, ContentTagCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("content_tag")
            get("content_tag") instanceof Map
            ((Map) get("content_tag")).id == "01ARZ3NDEK001"
            ((Map) get("content_tag")).name == "security"
        }
        verifyAll(deserialized) {
            it == request
            contentTag() != null
            contentTag().id == "01ARZ3NDEK001"
            contentTag().name == "security"
        }
    }

    def "should handle null contentTag component in constructor and wither"() {
        when:
        ContentTagCreateRequest request = new ContentTagCreateRequest((ContentTag) null)
        ContentTag replacement = new ContentTag("created").setId("01ARZ3NDEK099")
        ContentTagCreateRequest updated = request.withContentTag(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagCreateRequest deserialized = objectMapper.readValue(json, ContentTagCreateRequest.class)

        then:
        verifyAll(request) {
            contentTag() == null
        }
        verifyAll(updated) {
            contentTag() == replacement
            contentTag().id == "01ARZ3NDEK099"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("content_tag")
        }
        verifyAll(deserialized) {
            it == request
            contentTag() == null
        }
    }
}
