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

class TopicsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TopicsResponse with canonical constructor and builder"() {
        given:
        Topic item = new Topic("Announcements").withDescription("Important news").withId(101L)
        List<Topic> list = [item]

        when:
        TopicsResponse direct = new TopicsResponse(list)
        TopicsResponse built = TopicsResponseBuilder.builder()
                .topics(list)
                .build()

        then:
        verifyAll(direct) {
            topics() == list
            topics().size() == 1
            topics().first().id == 101L
            topics().first().name == "Announcements"
        }
        verifyAll(built) {
            topics() == list
            topics().size() == 1
            topics().first().id == 101L
            topics().first().name == "Announcements"
        }
    }

    def "should update topics using record wither and addTopicsItem method"() {
        given:
        Topic first = new Topic("Old News").withId(1L)
        Topic second = new Topic("New News").withId(2L)
        TopicsResponse response = new TopicsResponse([first])

        when:
        TopicsResponse withNewList = response.withTopics([second])
        TopicsResponse withAdded = response.addTopicsItem(second)
        TopicsResponse fromNull = new TopicsResponse(null).addTopicsItem(first)

        then:
        verifyAll(response) {
            topics().size() == 1
            topics().first().id == 1L
        }
        verifyAll(withNewList) {
            topics().size() == 1
            topics().first().id == 2L
        }
        verifyAll(withAdded) {
            topics().size() == 2
            topics().first().id == 1L
            topics().last().id == 2L
        }
        verifyAll(fromNull) {
            topics().size() == 1
            topics().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize TopicsResponse"() {
        given:
        Topic item = new Topic("Community Q&A").withDescription("Ask anything").withId(555L)
        TopicsResponse response = new TopicsResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TopicsResponse deserialized = objectMapper.readValue(json, TopicsResponse.class)

        then:
        verifyAll(map) {
            containsKey("topics")
            get("topics") instanceof List
            ((List) get("topics")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            topics() != null
            topics().size() == 1
            topics().first().id == 555L
            topics().first().name == "Community Q&A"
        }
    }

    def "should handle null topics component in constructor, wither, and serde"() {
        when:
        TopicsResponse response = new TopicsResponse(null)
        Topic item = new Topic("Feedback").withId(99L)
        TopicsResponse updated = response.withTopics([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TopicsResponse deserialized = objectMapper.readValue(json, TopicsResponse.class)

        then:
        verifyAll(response) {
            topics() == null
        }
        verifyAll(updated) {
            topics() != null
            topics().size() == 1
            topics().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("topics")
        }
        verifyAll(deserialized) {
            it == response
            topics() == null
        }
    }
}
