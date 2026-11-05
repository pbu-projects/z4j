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

class CommentResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CommentResponse with canonical constructor and builder"() {
        given:
        Comment expected = new Comment().setId(101L).setBody("Great tutorial")

        when:
        CommentResponse direct = new CommentResponse(expected)
        CommentResponse built = CommentResponseBuilder.builder().comment(expected).build()

        then:
        verifyAll(direct) {
            comment == expected
            comment.id == 101L
            comment.body == "Great tutorial"
        }
        verifyAll(built) {
            comment == expected
            comment.id == 101L
            comment.body == "Great tutorial"
        }
    }

    def "should update comment using record wither"() {
        given:
        Comment original = new Comment().setId(1L).setBody("First draft")
        Comment updatedComment = new Comment().setId(2L).setBody("Second draft")
        CommentResponse response = new CommentResponse(original)

        when:
        CommentResponse updated = response.withComment(updatedComment)

        then:
        verifyAll(response) {
            comment == original
            comment.body == "First draft"
        }
        verifyAll(updated) {
            comment == updatedComment
            comment.body == "Second draft"
        }
    }

    def "should round-trip serialize and deserialize CommentResponse"() {
        given:
        Comment expected = new Comment().setId(555L).setBody("Very helpful tips")
        CommentResponse response = new CommentResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CommentResponse deserialized = objectMapper.readValue(json, CommentResponse.class)

        then:
        verifyAll(map) {
            containsKey("comment")
            get("comment") instanceof Map
            ((Map) get("comment")).id == 555
            ((Map) get("comment")).body == "Very helpful tips"
        }
        verifyAll(deserialized) {
            it == response
            comment != null
            comment.id == 555L
            comment.body == "Very helpful tips"
        }
    }

    def "should handle null comment component in constructor and wither"() {
        when:
        CommentResponse response = new CommentResponse(null)
        Comment replacement = new Comment().setId(99L).setBody("Resolved")
        CommentResponse updated = response.withComment(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CommentResponse deserialized = objectMapper.readValue(json, CommentResponse.class)

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
