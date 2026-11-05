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

class UsersResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create UsersResponse with canonical constructor and builder"() {
        given:
        User user = new User().withId(801L).withName("Active User")
        List<User> list = [user]
        Long expectedCount = 1L
        String expectedNext = "https://example.zendesk.com/api/v2/users.json?page=2"
        String expectedPrev = "https://example.zendesk.com/api/v2/users.json?page=1"

        when:
        UsersResponse direct = new UsersResponse(list, expectedCount, expectedNext, expectedPrev)
        UsersResponse built = UsersResponseBuilder.builder()
                .users(list)
                .count(expectedCount)
                .nextPage(expectedNext)
                .previousPage(expectedPrev)
                .build()

        then:
        verifyAll(direct) {
            users() == list
            users().size() == 1
            users().first().id == 801L
            users().first().name == "Active User"
            count() == expectedCount
            nextPage() == expectedNext
            previousPage() == expectedPrev
        }
        verifyAll(built) {
            it == direct
            users() == list
            count() == expectedCount
            nextPage() == expectedNext
            previousPage() == expectedPrev
        }
    }

    def "should update UsersResponse using record withers and addUsersItem method"() {
        given:
        User first = new User().withId(1L).withName("First User")
        User second = new User().withId(2L).withName("Second User")
        UsersResponse response = new UsersResponse([first], 1L, "next_1", "prev_1")

        when:
        UsersResponse updated = response
                .withUsers([second])
                .withCount(2L)
                .withNextPage("next_2")
                .withPreviousPage("prev_2")
        UsersResponse withAdded = response.addUsersItem(second)
        UsersResponse fromNull = new UsersResponse(null, 0L, null, null).addUsersItem(first)

        then:
        verifyAll(response) {
            users().size() == 1
            users().first().id == 1L
            count() == 1L
            nextPage() == "next_1"
            previousPage() == "prev_1"
        }
        verifyAll(updated) {
            users() == [second]
            count() == 2L
            nextPage() == "next_2"
            previousPage() == "prev_2"
        }
        verifyAll(withAdded) {
            users().size() == 2
            users().first().id == 1L
            users().last().id == 2L
            count() == 1L
            nextPage() == "next_1"
            previousPage() == "prev_1"
        }
        verifyAll(fromNull) {
            users().size() == 1
            users().first().id == 1L
            count() == 0L
            nextPage() == null
            previousPage() == null
        }
    }

    def "should round-trip serialize and deserialize UsersResponse"() {
        given:
        User user = new User().withId(901L).withName("Agent User")
        UsersResponse response = new UsersResponse(
                [user],
                1L,
                "https://example.zendesk.com/api/v2/users.json?page=2",
                "https://example.zendesk.com/api/v2/users.json?page=1"
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UsersResponse deserialized = objectMapper.readValue(json, UsersResponse.class)

        then:
        verifyAll(map) {
            containsKey("users")
            containsKey("count")
            containsKey("next_page")
            containsKey("previous_page")
            ((List) get("users")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            users().size() == 1
            users().first().id == 901L
            users().first().name == "Agent User"
            count() == 1L
            nextPage() == "https://example.zendesk.com/api/v2/users.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/users.json?page=1"
        }
    }

    def "should handle null components in UsersResponse"() {
        when:
        UsersResponse empty = new UsersResponse(null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        UsersResponse deserialized = objectMapper.readValue(json, UsersResponse.class)

        then:
        verifyAll(empty) {
            users() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            users() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
    }
}
