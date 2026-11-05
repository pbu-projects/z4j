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

class TopicResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TopicResponse with canonical constructor and builder"() {
        given:
        Topic expected = new Topic("Announcements").withDescription("Important news").withId(101L)

        when:
        TopicResponse direct = new TopicResponse(expected)
        TopicResponse built = TopicResponseBuilder.builder().topic(expected).build()

        then:
        verifyAll(direct) {
            topic == expected
            topic.id == 101L
            topic.name == "Announcements"
            topic.description == "Important news"
        }
        verifyAll(built) {
            topic == expected
            topic.id == 101L
            topic.name == "Announcements"
            topic.description == "Important news"
        }
    }

    def "should update topic using record wither"() {
        given:
        Topic original = new Topic("Old News").withId(1L)
        Topic updatedTopic = new Topic("New News").withId(2L)
        TopicResponse response = new TopicResponse(original)

        when:
        TopicResponse updated = response.withTopic(updatedTopic)

        then:
        verifyAll(response) {
            topic == original
            topic.name == "Old News"
        }
        verifyAll(updated) {
            topic == updatedTopic
            topic.name == "New News"
        }
    }

    def "should round-trip serialize and deserialize TopicResponse"() {
        given:
        Topic expected = new Topic("Community Q&A").withDescription("Ask anything").withId(555L)
        TopicResponse response = new TopicResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TopicResponse deserialized = objectMapper.readValue(json, TopicResponse.class)

        then:
        verifyAll(map) {
            containsKey("topic")
            get("topic") instanceof Map
            ((Map) get("topic")).id == 555
            ((Map) get("topic")).name == "Community Q&A"
        }
        verifyAll(deserialized) {
            it == response
            topic != null
            topic.id == 555L
            topic.name == "Community Q&A"
            topic.description == "Ask anything"
        }
    }

    def "should handle null topic component in constructor and wither"() {
        when:
        TopicResponse response = new TopicResponse(null)
        Topic replacement = new Topic("Updates").withId(99L)
        TopicResponse updated = response.withTopic(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TopicResponse deserialized = objectMapper.readValue(json, TopicResponse.class)

        then:
        verifyAll(response) {
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
            it == response
            topic() == null
        }
    }
}
