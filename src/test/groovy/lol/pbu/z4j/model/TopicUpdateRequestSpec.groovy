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

class TopicUpdateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TopicUpdateRequest with canonical constructor and builder"() {
        given:
        Topic expected = new Topic("Updated Announcements").setDescription("Revised news").setId(101L)

        when:
        TopicUpdateRequest direct = new TopicUpdateRequest(expected)
        TopicUpdateRequest built = TopicUpdateRequestBuilder.builder().topic(expected).build()

        then:
        verifyAll(direct) {
            topic() == expected
            topic().id == 101L
            topic().name == "Updated Announcements"
            topic().description == "Revised news"
        }
        verifyAll(built) {
            it == direct
            topic() == expected
            topic().id == 101L
            topic().name == "Updated Announcements"
            topic().description == "Revised news"
        }
    }

    def "should update topic using record wither"() {
        given:
        Topic original = new Topic("Old News").setId(1L)
        Topic updatedTopic = new Topic("New News").setId(2L)
        TopicUpdateRequest request = new TopicUpdateRequest(original)

        when:
        TopicUpdateRequest updated = request.withTopic(updatedTopic)

        then:
        verifyAll(request) {
            topic() == original
            topic().name == "Old News"
        }
        verifyAll(updated) {
            topic() == updatedTopic
            topic().name == "New News"
        }
    }

    def "should round-trip serialize and deserialize TopicUpdateRequest"() {
        given:
        Topic expected = new Topic("Community Q&A v2").setDescription("Ask questions here").setId(555L)
        TopicUpdateRequest request = new TopicUpdateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TopicUpdateRequest deserialized = objectMapper.readValue(json, TopicUpdateRequest.class)

        then:
        verifyAll(map) {
            containsKey("topic")
            get("topic") instanceof Map
            ((Map) get("topic")).id == 555
            ((Map) get("topic")).name == "Community Q&A v2"
        }
        verifyAll(deserialized) {
            it == request
            topic() != null
            topic().id == 555L
            topic().name == "Community Q&A v2"
            topic().description == "Ask questions here"
        }
    }

    def "should handle null topic component in constructor and wither"() {
        when:
        TopicUpdateRequest request = new TopicUpdateRequest(null)
        Topic replacement = new Topic("Updates").setId(99L)
        TopicUpdateRequest updated = request.withTopic(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TopicUpdateRequest deserialized = objectMapper.readValue(json, TopicUpdateRequest.class)

        then:
        verifyAll(request) {
            topic() == null
        }
        verifyAll(updated) {
            topic() == replacement
            topic().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("topic")
        }
        verifyAll(deserialized) {
            it == request
            topic() == null
        }
    }
}
