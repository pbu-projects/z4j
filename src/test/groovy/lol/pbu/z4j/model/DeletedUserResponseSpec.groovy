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

class DeletedUserResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create DeletedUserResponse with canonical constructor and builder"() {
        given:
        User expected = new User().setId(501L).setName("Deleted Person").setEmail("deleted@example.com")

        when:
        DeletedUserResponse direct = new DeletedUserResponse(expected)
        DeletedUserResponse built = DeletedUserResponseBuilder.builder().deletedUser(expected).build()

        then:
        verifyAll(direct) {
            deletedUser == expected
            deletedUser.id == 501L
            deletedUser.name == "Deleted Person"
            deletedUser.email == "deleted@example.com"
        }
        verifyAll(built) {
            deletedUser == expected
            deletedUser.id == 501L
            deletedUser.name == "Deleted Person"
            deletedUser.email == "deleted@example.com"
        }
    }

    def "should update deletedUser using record wither"() {
        given:
        User original = new User().setId(1L).setName("User One")
        User replacement = new User().setId(2L).setName("User Two")
        DeletedUserResponse response = new DeletedUserResponse(original)

        when:
        DeletedUserResponse updated = response.withDeletedUser(replacement)

        then:
        verifyAll(response) {
            deletedUser == original
            deletedUser.name == "User One"
        }
        verifyAll(updated) {
            deletedUser == replacement
            deletedUser.name == "User Two"
        }
    }

    def "should round-trip serialize and deserialize DeletedUserResponse"() {
        given:
        User expected = new User().setId(501L).setName("Deleted Person").setEmail("deleted@example.com")
        DeletedUserResponse response = new DeletedUserResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        DeletedUserResponse deserialized = objectMapper.readValue(json, DeletedUserResponse.class)

        then:
        verifyAll(map) {
            containsKey("deleted_user")
            get("deleted_user") instanceof Map
            ((Map) get("deleted_user")).id == 501
            ((Map) get("deleted_user")).name == "Deleted Person"
        }
        verifyAll(deserialized) {
            it == response
            deletedUser != null
            deletedUser.id == 501L
            deletedUser.name == "Deleted Person"
            deletedUser.email == "deleted@example.com"
        }
    }

    def "should handle null deletedUser component in constructor and wither"() {
        when:
        DeletedUserResponse response = new DeletedUserResponse(null)
        User replacement = new User().setId(99L).setName("Restored User")
        DeletedUserResponse updated = response.withDeletedUser(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        DeletedUserResponse deserialized = objectMapper.readValue(json, DeletedUserResponse.class)

        then:
        verifyAll(response) {
            deletedUser() == null
        }
        verifyAll(updated) {
            deletedUser() == replacement
            deletedUser().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("deleted_user")
        }
        verifyAll(deserialized) {
            it == response
            deletedUser() == null
        }
    }
}
