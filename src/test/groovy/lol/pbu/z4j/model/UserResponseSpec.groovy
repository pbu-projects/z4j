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

class UserResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create UserResponse with canonical constructor and builder"() {
        given:
        User expected = new User().withId(101L).withName("Jane Doe").withEmail("jane@example.com")

        when:
        UserResponse direct = new UserResponse(expected)
        UserResponse built = UserResponseBuilder.builder().user(expected).build()

        then:
        verifyAll(direct) {
            user == expected
            user.id == 101L
            user.name == "Jane Doe"
            user.email == "jane@example.com"
        }
        verifyAll(built) {
            user == expected
            user.id == 101L
            user.name == "Jane Doe"
            user.email == "jane@example.com"
        }
    }

    def "should update user using record wither"() {
        given:
        User original = new User().withId(1L).withName("Old Name")
        User updatedUser = new User().withId(2L).withName("New Name")
        UserResponse response = new UserResponse(original)

        when:
        UserResponse updated = response.withUser(updatedUser)

        then:
        verifyAll(response) {
            user == original
            user.name == "Old Name"
        }
        verifyAll(updated) {
            user == updatedUser
            user.name == "New Name"
        }
    }

    def "should round-trip serialize and deserialize UserResponse"() {
        given:
        User expected = new User().withId(555L).withName("Alex Smith").withEmail("alex@example.com")
        UserResponse response = new UserResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UserResponse deserialized = objectMapper.readValue(json, UserResponse.class)

        then:
        verifyAll(map) {
            containsKey("user")
            get("user") instanceof Map
            ((Map) get("user")).id == 555
            ((Map) get("user")).name == "Alex Smith"
            ((Map) get("user")).email == "alex@example.com"
        }
        verifyAll(deserialized) {
            it == response
            user != null
            user.id == 555L
            user.name == "Alex Smith"
            user.email == "alex@example.com"
        }
    }

    def "should handle null user component in constructor and wither"() {
        when:
        UserResponse response = new UserResponse(null)
        User replacement = new User().withId(99L).withName("Replacement User")
        UserResponse updated = response.withUser(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UserResponse deserialized = objectMapper.readValue(json, UserResponse.class)

        then:
        verifyAll(response) {
            user() == null
        }
        verifyAll(updated) {
            user() == replacement
            user().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("user")
        }
        verifyAll(deserialized) {
            it == response
            user() == null
        }
    }
}
