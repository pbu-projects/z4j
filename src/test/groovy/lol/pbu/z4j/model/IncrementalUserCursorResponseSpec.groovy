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
import spock.lang.Shared
import spock.lang.Specification

class IncrementalUserCursorResponseSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create IncrementalUserCursorResponse with canonical constructor, convenience constructor, and builder"() {
        given:
        User user = new User().withId(456L).withName("Exported User")
        List<User> expectedUsers = [user]

        when:
        IncrementalUserCursorResponse direct = new IncrementalUserCursorResponse(
                expectedUsers, "user_cursor", "user_after", "user_before", true, 1
        )
        IncrementalUserCursorResponse convenience = new IncrementalUserCursorResponse(expectedUsers)
        IncrementalUserCursorResponse built = IncrementalUserCursorResponseBuilder.builder()
                .users(expectedUsers)
                .cursor("user_cursor")
                .afterCursor("user_after")
                .beforeCursor("user_before")
                .endOfStream(true)
                .count(1)
                .build()

        then:
        verifyAll(direct) {
            users() == expectedUsers
            results() == expectedUsers
            cursor() == "user_cursor"
            afterCursor() == "user_after"
            beforeCursor() == "user_before"
            endOfStream() == true
            count() == 1
        }
        verifyAll(convenience) {
            users() == expectedUsers
            results() == expectedUsers
            cursor() == null
            afterCursor() == null
            beforeCursor() == null
            endOfStream() == null
            count() == null
        }
        verifyAll(built) {
            it == direct
            users() == expectedUsers
            results() == expectedUsers
            cursor() == "user_cursor"
            afterCursor() == "user_after"
            beforeCursor() == "user_before"
            endOfStream() == true
            count() == 1
        }
    }

    def "should update IncrementalUserCursorResponse using record withers"() {
        given:
        User first = new User().withId(1L).withName("User 1")
        User second = new User().withId(2L).withName("User 2")
        IncrementalUserCursorResponse response = new IncrementalUserCursorResponse(
                [first], "c1", "a1", "b1", false, 1
        )

        when:
        IncrementalUserCursorResponse updated = response
                .withUsers([second])
                .withCursor("c2")
                .withAfterCursor("a2")
                .withBeforeCursor("b2")
                .withEndOfStream(true)
                .withCount(2)

        then:
        verifyAll(response) {
            users() == [first]
            results() == [first]
            cursor() == "c1"
            afterCursor() == "a1"
            beforeCursor() == "b1"
            endOfStream() == false
            count() == 1
        }
        verifyAll(updated) {
            users() == [second]
            results() == [second]
            cursor() == "c2"
            afterCursor() == "a2"
            beforeCursor() == "b2"
            endOfStream() == true
            count() == 2
        }
    }

    def "should round-trip serialize and deserialize IncrementalUserCursorResponse"() {
        given:
        User user = new User().withId(456L).withName("Jane Doe")
        IncrementalUserCursorResponse response = new IncrementalUserCursorResponse(
                [user], "cur_456", "after_456", "before_456", true, 1
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        IncrementalUserCursorResponse deserialized = objectMapper.readValue(json, IncrementalUserCursorResponse.class)

        then:
        verifyAll(map) {
            containsKey("users")
            containsKey("cursor")
            containsKey("after_cursor")
            containsKey("before_cursor")
            containsKey("end_of_stream")
            containsKey("count")
            !containsKey("results")
        }
        verifyAll(deserialized) {
            it == response
            users().size() == 1
            users().first().id == 456L
            results() == users()
            cursor() == "cur_456"
            afterCursor() == "after_456"
            beforeCursor() == "before_456"
            endOfStream() == true
            count() == 1
        }
    }

    def "should handle null components in IncrementalUserCursorResponse"() {
        when:
        IncrementalUserCursorResponse empty = new IncrementalUserCursorResponse(null, null, null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        IncrementalUserCursorResponse deserialized = objectMapper.readValue(json, IncrementalUserCursorResponse.class)

        then:
        verifyAll(empty) {
            users() == null
            results() == null
            cursor() == null
            afterCursor() == null
            beforeCursor() == null
            endOfStream() == null
            count() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            users() == null
            results() == null
            cursor() == null
            afterCursor() == null
            beforeCursor() == null
            endOfStream() == null
            count() == null
        }
    }
}
