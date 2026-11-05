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

class UserRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create UserRequest with canonical constructor and builder"() {
        given:
        User expected = new User().withId(101L).withName("Alice Smith").withEmail("alice@example.com")

        when:
        UserRequest direct = new UserRequest(expected)
        UserRequest built = UserRequestBuilder.builder()
                .user(expected)
                .build()

        then:
        verifyAll(direct) {
            user() == expected
            user().id == 101L
            user().name == "Alice Smith"
            user().email == "alice@example.com"
        }
        verifyAll(built) {
            it == direct
            user() == expected
            user().id == 101L
            user().name == "Alice Smith"
            user().email == "alice@example.com"
        }
    }

    def "should update user using record wither"() {
        given:
        User original = new User().withId(1L).withName("Old User")
        User replacement = new User().withId(2L).withName("New User")
        UserRequest request = new UserRequest(original)

        when:
        UserRequest updated = request.withUser(replacement)

        then:
        verifyAll(request) {
            user() == original
            user().name == "Old User"
        }
        verifyAll(updated) {
            user() == replacement
            user().name == "New User"
        }
    }

    def "should round-trip serialize and deserialize UserRequest"() {
        given:
        User expected = new User().withId(555L).withName("Bob Jones").withEmail("bob@example.com")
        UserRequest request = new UserRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        UserRequest deserialized = objectMapper.readValue(json, UserRequest.class)

        then:
        verifyAll(map) {
            containsKey("user")
            get("user") instanceof Map
            ((Map) get("user")).id == 555
            ((Map) get("user")).name == "Bob Jones"
        }
        verifyAll(deserialized) {
            it == request
            user() != null
            user().id == 555L
            user().name == "Bob Jones"
            user().email == "bob@example.com"
        }
    }

    def "should handle null user component in constructor, wither, and serde"() {
        given:
        User replacement = new User().withId(99L).withName("Replacement User")

        when:
        UserRequest request = new UserRequest(null)
        UserRequest updated = request.withUser(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        UserRequest deserialized = objectMapper.readValue(json, UserRequest.class)

        then:
        verifyAll(request) {
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
            it == request
            user() == null
        }
    }
}
