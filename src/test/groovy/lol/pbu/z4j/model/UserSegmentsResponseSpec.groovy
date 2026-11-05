/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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

class UserSegmentsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create UserSegmentsResponse with canonical constructor and builder"() {
        given:
        UserSegment item = new UserSegment("Staff Only", "staff").withId(101L)
        List<UserSegment> list = [item]

        when:
        UserSegmentsResponse direct = new UserSegmentsResponse(list)
        UserSegmentsResponse built = UserSegmentsResponseBuilder.builder()
                .userSegments(list)
                .build()

        then:
        verifyAll(direct) {
            userSegments() == list
            userSegments().size() == 1
            userSegments().first().id == 101L
            userSegments().first().name == "Staff Only"
            userSegments().first().userType == "staff"
        }
        verifyAll(built) {
            userSegments() == list
            userSegments().size() == 1
            userSegments().first().id == 101L
            userSegments().first().name == "Staff Only"
            userSegments().first().userType == "staff"
        }
    }

    def "should update user segments using record wither and addUserSegmentsItem method"() {
        given:
        UserSegment first = new UserSegment("Original", "staff").withId(1L)
        UserSegment second = new UserSegment("Updated", "signed_in_users").withId(2L)
        UserSegmentsResponse response = new UserSegmentsResponse([first])

        when:
        UserSegmentsResponse withNewList = response.withUserSegments([second])
        UserSegmentsResponse withAdded = response.addUserSegmentsItem(second)
        UserSegmentsResponse fromNull = new UserSegmentsResponse(null).addUserSegmentsItem(first)

        then:
        verifyAll(response) {
            userSegments().size() == 1
            userSegments().first().id == 1L
        }
        verifyAll(withNewList) {
            userSegments().size() == 1
            userSegments().first().id == 2L
        }
        verifyAll(withAdded) {
            userSegments().size() == 2
            userSegments().first().id == 1L
            userSegments().last().id == 2L
        }
        verifyAll(fromNull) {
            userSegments().size() == 1
            userSegments().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize UserSegmentsResponse"() {
        given:
        UserSegment item = new UserSegment("VIP Customers", "signed_in_users").withId(555L)
        UserSegmentsResponse response = new UserSegmentsResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UserSegmentsResponse deserialized = objectMapper.readValue(json, UserSegmentsResponse.class)

        then:
        verifyAll(map) {
            containsKey("user_segments")
            get("user_segments") instanceof List
            ((List) get("user_segments")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            userSegments() != null
            userSegments().size() == 1
            userSegments().first().id == 555L
            userSegments().first().name == "VIP Customers"
        }
    }

    def "should handle null user segments component in constructor, wither, and serde"() {
        when:
        UserSegmentsResponse response = new UserSegmentsResponse(null)
        UserSegment item = new UserSegment("Community", "signed_in_users").withId(99L)
        UserSegmentsResponse updated = response.withUserSegments([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UserSegmentsResponse deserialized = objectMapper.readValue(json, UserSegmentsResponse.class)

        then:
        verifyAll(response) {
            userSegments() == null
        }
        verifyAll(updated) {
            userSegments() != null
            userSegments().size() == 1
            userSegments().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("user_segments")
        }
        verifyAll(deserialized) {
            it == response
            userSegments() == null
        }
    }
}
