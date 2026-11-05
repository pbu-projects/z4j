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

class PostCommentResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create PostCommentResponse with canonical constructor and builder"() {
        given:
        PostComment expected = new PostComment("Helpful feedback").setPostId(101L)
        expected.setId(201L)

        when:
        PostCommentResponse direct = new PostCommentResponse(expected)
        PostCommentResponse built = PostCommentResponseBuilder.builder().comment(expected).build()

        then:
        verifyAll(direct) {
            comment == expected
            comment.id == 201L
            comment.postId == 101L
            comment.body == "Helpful feedback"
        }
        verifyAll(built) {
            comment == expected
            comment.id == 201L
            comment.postId == 101L
            comment.body == "Helpful feedback"
        }
    }

    def "should update comment using record wither"() {
        given:
        PostComment original = new PostComment("Original text").setPostId(1L)
        original.setId(10L)
        PostComment replacement = new PostComment("Replacement text").setPostId(2L)
        replacement.setId(20L)
        PostCommentResponse response = new PostCommentResponse(original)

        when:
        PostCommentResponse updated = response.withComment(replacement)

        then:
        verifyAll(response) {
            comment == original
            comment.body == "Original text"
        }
        verifyAll(updated) {
            comment == replacement
            comment.body == "Replacement text"
        }
    }

    def "should round-trip serialize and deserialize PostCommentResponse"() {
        given:
        PostComment expected = new PostComment("Community discussion comment").setPostId(404L)
        expected.setId(808L)
        PostCommentResponse response = new PostCommentResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        PostCommentResponse deserialized = objectMapper.readValue(json, PostCommentResponse.class)

        then:
        verifyAll(map) {
            containsKey("comment")
            get("comment") instanceof Map
            ((Map) get("comment")).id == 808
            ((Map) get("comment")).body == "Community discussion comment"
        }
        verifyAll(deserialized) {
            it == response
            comment != null
            comment.id == 808L
            comment.postId == 404L
            comment.body == "Community discussion comment"
        }
    }

    def "should handle null comment component in constructor and wither"() {
        when:
        PostCommentResponse response = new PostCommentResponse(null)
        PostComment replacement = new PostComment("Fresh reply")
        replacement.setId(99L)
        PostCommentResponse updated = response.withComment(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        PostCommentResponse deserialized = objectMapper.readValue(json, PostCommentResponse.class)

        then:
        verifyAll(response) {
            comment() == null
        }
        verifyAll(updated) {
            comment() == replacement
            comment().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("comment")
        }
        verifyAll(deserialized) {
            it == response
            comment() == null
        }
    }
}
