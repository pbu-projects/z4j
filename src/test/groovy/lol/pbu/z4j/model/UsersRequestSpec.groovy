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

class UsersRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create UsersRequest with canonical constructor and builder"() {
        given:
        User user1 = new User().setId(101L).setName("User 1")
        User user2 = new User().setId(102L).setName("User 2")
        List<User> items = [user1, user2]

        when:
        UsersRequest direct = new UsersRequest(items)
        UsersRequest built = UsersRequestBuilder.builder()
                .users(items)
                .build()

        then:
        verifyAll(direct) {
            users() == items
            users().size() == 2
            users().first().name == "User 1"
            users().last().name == "User 2"
        }
        verifyAll(built) {
            it == direct
            users() == items
            users().size() == 2
            users().first().name == "User 1"
            users().last().name == "User 2"
        }
    }

    def "should update users using record wither and addUsersItem method"() {
        given:
        User user1 = new User().setId(1L).setName("User 1")
        User user2 = new User().setId(2L).setName("User 2")
        UsersRequest request = new UsersRequest([user1])

        when:
        UsersRequest withNewList = request.withUsers([user2])
        UsersRequest withAdded = request.addUsersItem(user2)
        UsersRequest fromNull = new UsersRequest(null).addUsersItem(user1)

        then:
        verifyAll(request) {
            users().size() == 1
            users().first() == user1
        }
        verifyAll(withNewList) {
            users().size() == 1
            users().first() == user2
        }
        verifyAll(withAdded) {
            users().size() == 2
            users().first() == user1
            users().last() == user2
        }
        verifyAll(fromNull) {
            users().size() == 1
            users().first() == user1
        }
    }

    def "should round-trip serialize and deserialize UsersRequest"() {
        given:
        User user1 = new User().setId(555L).setName("Batch User 1")
        User user2 = new User().setId(777L).setName("Batch User 2")
        UsersRequest request = new UsersRequest([user1, user2])

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        UsersRequest deserialized = objectMapper.readValue(json, UsersRequest.class)

        then:
        verifyAll(map) {
            containsKey("users")
            get("users") instanceof List
            ((List) get("users")).size() == 2
        }
        verifyAll(deserialized) {
            it == request
            users() != null
            users().size() == 2
            users().first().id == 555L
            users().last().id == 777L
        }
    }

    def "should handle null users component in constructor, wither, and serde"() {
        given:
        User replacement = new User().setId(99L).setName("Replacement User")

        when:
        UsersRequest request = new UsersRequest(null)
        UsersRequest updated = request.withUsers([replacement])
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        UsersRequest deserialized = objectMapper.readValue(json, UsersRequest.class)

        then:
        verifyAll(request) {
            users() == null
        }
        verifyAll(updated) {
            users() != null
            users() == [replacement]
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("users")
        }
        verifyAll(deserialized) {
            it == request
            users() == null
        }
    }
}
