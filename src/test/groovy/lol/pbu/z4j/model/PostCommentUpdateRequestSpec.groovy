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

class PostCommentUpdateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create PostCommentUpdateRequest with canonical constructor and builder"() {
        given:
        PostComment expected = new PostComment("Updated feedback").withPostId(101L).withId(201L)

        when:
        PostCommentUpdateRequest direct = new PostCommentUpdateRequest(expected)
        PostCommentUpdateRequest built = PostCommentUpdateRequestBuilder.builder().comment(expected).build()

        then:
        verifyAll(direct) {
            comment() == expected
            comment().id == 201L
            comment().postId == 101L
            comment().body == "Updated feedback"
        }
        verifyAll(built) {
            it == direct
            comment() == expected
            comment().id == 201L
            comment().postId == 101L
            comment().body == "Updated feedback"
        }
    }

    def "should update comment using record wither"() {
        given:
        PostComment original = new PostComment("Original text").withPostId(1L).withId(10L)
        PostComment replacement = new PostComment("Replacement text").withPostId(2L).withId(20L)
        PostCommentUpdateRequest request = new PostCommentUpdateRequest(original)

        when:
        PostCommentUpdateRequest updated = request.withComment(replacement)

        then:
        verifyAll(request) {
            comment() == original
            comment().body == "Original text"
        }
        verifyAll(updated) {
            comment() == replacement
            comment().body == "Replacement text"
        }
    }

    def "should round-trip serialize and deserialize PostCommentUpdateRequest"() {
        given:
        PostComment expected = new PostComment("Edited community comment").withPostId(404L).withId(808L)
        PostCommentUpdateRequest request = new PostCommentUpdateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        PostCommentUpdateRequest deserialized = objectMapper.readValue(json, PostCommentUpdateRequest.class)

        then:
        verifyAll(map) {
            containsKey("comment")
            get("comment") instanceof Map
            ((Map) get("comment")).id == 808
            ((Map) get("comment")).body == "Edited community comment"
        }
        verifyAll(deserialized) {
            it == request
            comment() != null
            comment().id == 808L
            comment().postId == 404L
            comment().body == "Edited community comment"
        }
    }

    def "should handle null comment component in constructor and wither"() {
        when:
        PostCommentUpdateRequest request = new PostCommentUpdateRequest(null)
        PostComment replacement = new PostComment("Fresh edit").withId(99L)
        PostCommentUpdateRequest updated = request.withComment(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        PostCommentUpdateRequest deserialized = objectMapper.readValue(json, PostCommentUpdateRequest.class)

        then:
        verifyAll(request) {
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
            it == request
            comment() == null
        }
    }
}
