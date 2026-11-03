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

class CommentsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CommentsResponse with canonical constructor and builder"() {
        given:
        Comment item = new Comment().setId(101L).setBody("Great tutorial")
        List<Comment> list = [item]

        when:
        CommentsResponse direct = new CommentsResponse(list)
        CommentsResponse built = CommentsResponseBuilder.builder()
                .comments(list)
                .build()

        then:
        verifyAll(direct) {
            comments() == list
            comments().size() == 1
            comments().first().id == 101L
            comments().first().body == "Great tutorial"
        }
        verifyAll(built) {
            comments() == list
            comments().size() == 1
            comments().first().id == 101L
            comments().first().body == "Great tutorial"
        }
    }

    def "should update comments using record wither and addCommentsItem method"() {
        given:
        Comment first = new Comment().setId(1L).setBody("First draft")
        Comment second = new Comment().setId(2L).setBody("Second draft")
        CommentsResponse response = new CommentsResponse([first])

        when:
        CommentsResponse withNewList = response.withComments([second])
        CommentsResponse withAdded = response.addCommentsItem(second)
        CommentsResponse fromNull = new CommentsResponse(null).addCommentsItem(first)

        then:
        verifyAll(response) {
            comments().size() == 1
            comments().first().id == 1L
        }
        verifyAll(withNewList) {
            comments().size() == 1
            comments().first().id == 2L
        }
        verifyAll(withAdded) {
            comments().size() == 2
            comments().first().id == 1L
            comments().last().id == 2L
        }
        verifyAll(fromNull) {
            comments().size() == 1
            comments().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize CommentsResponse"() {
        given:
        Comment item = new Comment().setId(555L).setBody("Very helpful tips")
        CommentsResponse response = new CommentsResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CommentsResponse deserialized = objectMapper.readValue(json, CommentsResponse.class)

        then:
        verifyAll(map) {
            containsKey("comments")
            get("comments") instanceof List
            ((List) get("comments")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            comments() != null
            comments().size() == 1
            comments().first().id == 555L
            comments().first().body == "Very helpful tips"
        }
    }

    def "should handle null comments component in constructor, wither, and serde"() {
        when:
        CommentsResponse response = new CommentsResponse(null)
        Comment item = new Comment().setId(99L).setBody("Resolved")
        CommentsResponse updated = response.withComments([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CommentsResponse deserialized = objectMapper.readValue(json, CommentsResponse.class)

        then:
        verifyAll(response) {
            comments() == null
        }
        verifyAll(updated) {
            comments() != null
            comments().size() == 1
            comments().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("comments")
        }
        verifyAll(deserialized) {
            it == response
            comments() == null
        }
    }
}
