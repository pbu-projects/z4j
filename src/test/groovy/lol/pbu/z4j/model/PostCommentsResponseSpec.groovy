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

class PostCommentsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create PostCommentsResponse with canonical constructor and builder"() {
        given:
        PostComment item = new PostComment("Helpful feedback").setPostId(101L)
        item.setId(201L)
        List<PostComment> list = [item]

        when:
        PostCommentsResponse direct = new PostCommentsResponse(list)
        PostCommentsResponse built = PostCommentsResponseBuilder.builder()
                .comments(list)
                .build()

        then:
        verifyAll(direct) {
            comments() == list
            comments().size() == 1
            comments().first().id == 201L
            comments().first().body == "Helpful feedback"
        }
        verifyAll(built) {
            comments() == list
            comments().size() == 1
            comments().first().id == 201L
            comments().first().body == "Helpful feedback"
        }
    }

    def "should update comments using record wither and addCommentsItem method"() {
        given:
        PostComment first = new PostComment("First reply")
        first.setId(1L)
        PostComment second = new PostComment("Second reply")
        second.setId(2L)
        PostCommentsResponse response = new PostCommentsResponse([first])

        when:
        PostCommentsResponse withNewList = response.withComments([second])
        PostCommentsResponse withAdded = response.addCommentsItem(second)
        PostCommentsResponse fromNull = new PostCommentsResponse(null).addCommentsItem(first)

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

    def "should round-trip serialize and deserialize PostCommentsResponse"() {
        given:
        PostComment item = new PostComment("Community discussion comment").setPostId(404L)
        item.setId(555L)
        PostCommentsResponse response = new PostCommentsResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        PostCommentsResponse deserialized = objectMapper.readValue(json, PostCommentsResponse.class)

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
            comments().first().body == "Community discussion comment"
        }
    }

    def "should handle null comments component in constructor, wither, and serde"() {
        when:
        PostCommentsResponse response = new PostCommentsResponse(null)
        PostComment item = new PostComment("Fresh reply")
        item.setId(99L)
        PostCommentsResponse updated = response.withComments([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        PostCommentsResponse deserialized = objectMapper.readValue(json, PostCommentsResponse.class)

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
