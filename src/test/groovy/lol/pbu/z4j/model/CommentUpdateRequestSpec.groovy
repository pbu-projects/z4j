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

class CommentUpdateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CommentUpdateRequest with canonical constructor and builder"() {
        given:
        Comment expected = new Comment("Edited comment", LocaleAbbreviation.ENGLISH_UNITED_STATES).withId(202L)

        when:
        CommentUpdateRequest direct = new CommentUpdateRequest(expected)
        CommentUpdateRequest built = CommentUpdateRequestBuilder.builder().comment(expected).build()

        then:
        verifyAll(direct) {
            comment() == expected
            comment().id == 202L
            comment().body == "Edited comment"
        }
        verifyAll(built) {
            it == direct
            comment() == expected
            comment().id == 202L
            comment().body == "Edited comment"
        }
    }

    def "should update comment using record wither"() {
        given:
        Comment originalComment = new Comment("Draft comment", LocaleAbbreviation.ENGLISH_UNITED_STATES).withId(1L)
        Comment newComment = new Comment("Final comment", LocaleAbbreviation.ENGLISH_UNITED_STATES).withId(2L)
        CommentUpdateRequest request = new CommentUpdateRequest(originalComment)

        when:
        CommentUpdateRequest updated = request.withComment(newComment)

        then:
        verifyAll(request) {
            comment() == originalComment
            comment().body == "Draft comment"
        }
        verifyAll(updated) {
            comment() == newComment
            comment().body == "Final comment"
        }
    }

    def "should round-trip serialize and deserialize CommentUpdateRequest"() {
        given:
        Comment expected = new Comment("Revised comment", LocaleAbbreviation.ENGLISH_UNITED_STATES).withId(556L)
        CommentUpdateRequest request = new CommentUpdateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CommentUpdateRequest deserialized = objectMapper.readValue(json, CommentUpdateRequest.class)

        then:
        verifyAll(map) {
            containsKey("comment")
            get("comment") instanceof Map
            ((Map) get("comment")).id == 556
            ((Map) get("comment")).body == "Revised comment"
        }
        verifyAll(deserialized) {
            it == request
            comment() != null
            comment().id == 556L
            comment().body == "Revised comment"
        }
    }

    def "should handle null comment component in constructor and wither"() {
        when:
        CommentUpdateRequest request = new CommentUpdateRequest(null)
        Comment replacement = new Comment("Restored comment", LocaleAbbreviation.ENGLISH_UNITED_STATES).withId(99L)
        CommentUpdateRequest updated = request.withComment(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CommentUpdateRequest deserialized = objectMapper.readValue(json, CommentUpdateRequest.class)

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
