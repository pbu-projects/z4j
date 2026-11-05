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

class DeletedUsersResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create DeletedUsersResponse with canonical constructor and builder"() {
        given:
        User user = new User().withId(201L).withName("Deleted User")
        List<User> list = [user]
        Long expectedCount = 1L
        String expectedNext = "https://example.zendesk.com/api/v2/deleted_users.json?page=2"
        String expectedPrev = "https://example.zendesk.com/api/v2/deleted_users.json?page=1"

        when:
        DeletedUsersResponse direct = new DeletedUsersResponse(list, expectedCount, expectedNext, expectedPrev)
        DeletedUsersResponse built = DeletedUsersResponseBuilder.builder()
                .deletedUsers(list)
                .count(expectedCount)
                .nextPage(expectedNext)
                .previousPage(expectedPrev)
                .build()

        then:
        verifyAll(direct) {
            deletedUsers() == list
            deletedUsers().size() == 1
            deletedUsers().first().id == 201L
            deletedUsers().first().name == "Deleted User"
            count() == expectedCount
            nextPage() == expectedNext
            previousPage() == expectedPrev
        }
        verifyAll(built) {
            it == direct
            deletedUsers() == list
            count() == expectedCount
            nextPage() == expectedNext
            previousPage() == expectedPrev
        }
    }

    def "should update DeletedUsersResponse using record withers and addDeletedUsersItem method"() {
        given:
        User first = new User().withId(1L).withName("First User")
        User second = new User().withId(2L).withName("Second User")
        DeletedUsersResponse response = new DeletedUsersResponse([first], 1L, "next_1", "prev_1")

        when:
        DeletedUsersResponse updated = response
                .withDeletedUsers([second])
                .withCount(2L)
                .withNextPage("next_2")
                .withPreviousPage("prev_2")
        DeletedUsersResponse withAdded = response.addDeletedUsersItem(second)
        DeletedUsersResponse fromNull = new DeletedUsersResponse(null, 0L, null, null).addDeletedUsersItem(first)

        then:
        verifyAll(response) {
            deletedUsers().size() == 1
            deletedUsers().first().id == 1L
            count() == 1L
            nextPage() == "next_1"
            previousPage() == "prev_1"
        }
        verifyAll(updated) {
            deletedUsers() == [second]
            count() == 2L
            nextPage() == "next_2"
            previousPage() == "prev_2"
        }
        verifyAll(withAdded) {
            deletedUsers().size() == 2
            deletedUsers().first().id == 1L
            deletedUsers().last().id == 2L
            count() == 1L
            nextPage() == "next_1"
            previousPage() == "prev_1"
        }
        verifyAll(fromNull) {
            deletedUsers().size() == 1
            deletedUsers().first().id == 1L
            count() == 0L
            nextPage() == null
            previousPage() == null
        }
    }

    def "should round-trip serialize and deserialize DeletedUsersResponse"() {
        given:
        User user = new User().withId(601L).withName("Deleted One")
        DeletedUsersResponse response = new DeletedUsersResponse(
                [user],
                1L,
                "https://example.zendesk.com/api/v2/deleted_users.json?page=2",
                "https://example.zendesk.com/api/v2/deleted_users.json?page=1"
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        DeletedUsersResponse deserialized = objectMapper.readValue(json, DeletedUsersResponse.class)

        then:
        verifyAll(map) {
            containsKey("deleted_users")
            containsKey("count")
            containsKey("next_page")
            containsKey("previous_page")
            ((List) get("deleted_users")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            deletedUsers().size() == 1
            deletedUsers().first().id == 601L
            deletedUsers().first().name == "Deleted One"
            count() == 1L
            nextPage() == "https://example.zendesk.com/api/v2/deleted_users.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/deleted_users.json?page=1"
        }
    }

    def "should handle null components in DeletedUsersResponse"() {
        when:
        DeletedUsersResponse empty = new DeletedUsersResponse(null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        DeletedUsersResponse deserialized = objectMapper.readValue(json, DeletedUsersResponse.class)

        then:
        verifyAll(empty) {
            deletedUsers() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            deletedUsers() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
    }
}
