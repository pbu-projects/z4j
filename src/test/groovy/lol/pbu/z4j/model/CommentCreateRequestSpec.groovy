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

class CommentCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CommentCreateRequest with canonical constructor and builder"() {
        given:
        Comment expected = new Comment("Helpful article!", LocaleAbbreviation.ENGLISH_UNITED_STATES).setId(201L)

        when:
        CommentCreateRequest direct = new CommentCreateRequest(expected)
        CommentCreateRequest built = CommentCreateRequestBuilder.builder().comment(expected).build()

        then:
        verifyAll(direct) {
            comment() == expected
            comment().id == 201L
            comment().body == "Helpful article!"
        }
        verifyAll(built) {
            it == direct
            comment() == expected
            comment().id == 201L
            comment().body == "Helpful article!"
        }
    }

    def "should update comment using record wither"() {
        given:
        Comment originalComment = new Comment("Initial note", LocaleAbbreviation.ENGLISH_UNITED_STATES).setId(1L)
        Comment newComment = new Comment("Updated note", LocaleAbbreviation.ENGLISH_UNITED_STATES).setId(2L)
        CommentCreateRequest request = new CommentCreateRequest(originalComment)

        when:
        CommentCreateRequest updated = request.withComment(newComment)

        then:
        verifyAll(request) {
            comment() == originalComment
            comment().body == "Initial note"
        }
        verifyAll(updated) {
            comment() == newComment
            comment().body == "Updated note"
        }
    }

    def "should round-trip serialize and deserialize CommentCreateRequest"() {
        given:
        Comment expected = new Comment("Great guide!", LocaleAbbreviation.ENGLISH_UNITED_STATES).setId(555L)
        CommentCreateRequest request = new CommentCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CommentCreateRequest deserialized = objectMapper.readValue(json, CommentCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("comment")
            get("comment") instanceof Map
            ((Map) get("comment")).id == 555
            ((Map) get("comment")).body == "Great guide!"
        }
        verifyAll(deserialized) {
            it == request
            comment() != null
            comment().id == 555L
            comment().body == "Great guide!"
        }
    }

    def "should handle null comment component in constructor and wither"() {
        when:
        CommentCreateRequest request = new CommentCreateRequest(null)
        Comment replacement = new Comment("Added note", LocaleAbbreviation.ENGLISH_UNITED_STATES).setId(99L)
        CommentCreateRequest updated = request.withComment(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CommentCreateRequest deserialized = objectMapper.readValue(json, CommentCreateRequest.class)

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
