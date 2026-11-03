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

class ContentTagResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ContentTagResponse with canonical constructor and builder"() {
        given:
        ContentTag expected = new ContentTag().setId("tag-101").setName("billing")

        when:
        ContentTagResponse direct = new ContentTagResponse(expected)
        ContentTagResponse built = ContentTagResponseBuilder.builder().contentTag(expected).build()

        then:
        verifyAll(direct) {
            contentTag == expected
            contentTag.id == "tag-101"
            contentTag.name == "billing"
        }
        verifyAll(built) {
            contentTag == expected
            contentTag.id == "tag-101"
            contentTag.name == "billing"
        }
    }

    def "should update content tag using record wither"() {
        given:
        ContentTag original = new ContentTag().setId("tag-1").setName("support")
        ContentTag updatedTag = new ContentTag().setId("tag-2").setName("sales")
        ContentTagResponse response = new ContentTagResponse(original)

        when:
        ContentTagResponse updated = response.withContentTag(updatedTag)

        then:
        verifyAll(response) {
            contentTag == original
            contentTag.name == "support"
        }
        verifyAll(updated) {
            contentTag == updatedTag
            contentTag.name == "sales"
        }
    }

    def "should round-trip serialize and deserialize ContentTagResponse"() {
        given:
        ContentTag expected = new ContentTag().setId("tag-555").setName("general")
        ContentTagResponse response = new ContentTagResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagResponse deserialized = objectMapper.readValue(json, ContentTagResponse.class)

        then:
        verifyAll(map) {
            containsKey("content_tag")
            get("content_tag") instanceof Map
            ((Map) get("content_tag")).id == "tag-555"
            ((Map) get("content_tag")).name == "general"
        }
        verifyAll(deserialized) {
            it == response
            contentTag != null
            contentTag.id == "tag-555"
            contentTag.name == "general"
        }
    }

    def "should handle null content tag component in constructor and wither"() {
        when:
        ContentTagResponse response = new ContentTagResponse(null)
        ContentTag replacement = new ContentTag().setId("tag-99").setName("finance")
        ContentTagResponse updated = response.withContentTag(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagResponse deserialized = objectMapper.readValue(json, ContentTagResponse.class)

        then:
        verifyAll(response) {
            contentTag() == null
        }
        verifyAll(updated) {
            contentTag() == replacement
            contentTag().id == "tag-99"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("content_tag")
        }
        verifyAll(deserialized) {
            it == response
            contentTag() == null
        }
    }
}
