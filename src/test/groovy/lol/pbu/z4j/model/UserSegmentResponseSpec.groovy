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

class UserSegmentResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create UserSegmentResponse with canonical constructor and builder"() {
        given:
        UserSegment expected = new UserSegment("Staff Only", "staff").withId(101L)

        when:
        UserSegmentResponse direct = new UserSegmentResponse(expected)
        UserSegmentResponse built = UserSegmentResponseBuilder.builder().userSegment(expected).build()

        then:
        verifyAll(direct) {
            userSegment == expected
            userSegment.id == 101L
            userSegment.name == "Staff Only"
            userSegment.userType == "staff"
        }
        verifyAll(built) {
            userSegment == expected
            userSegment.id == 101L
            userSegment.name == "Staff Only"
            userSegment.userType == "staff"
        }
    }

    def "should update user segment using record wither"() {
        given:
        UserSegment original = new UserSegment("Original", "staff").withId(1L)
        UserSegment updatedSegment = new UserSegment("Updated", "signed_in_users").withId(2L)
        UserSegmentResponse response = new UserSegmentResponse(original)

        when:
        UserSegmentResponse updated = response.withUserSegment(updatedSegment)

        then:
        verifyAll(response) {
            userSegment == original
            userSegment.name == "Original"
        }
        verifyAll(updated) {
            userSegment == updatedSegment
            userSegment.name == "Updated"
        }
    }

    def "should round-trip serialize and deserialize UserSegmentResponse"() {
        given:
        UserSegment expected = new UserSegment("VIP Customers", "signed_in_users").withId(555L)
        UserSegmentResponse response = new UserSegmentResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UserSegmentResponse deserialized = objectMapper.readValue(json, UserSegmentResponse.class)

        then:
        verifyAll(map) {
            containsKey("user_segment")
            get("user_segment") instanceof Map
            ((Map) get("user_segment")).id == 555
            ((Map) get("user_segment")).name == "VIP Customers"
        }
        verifyAll(deserialized) {
            it == response
            userSegment != null
            userSegment.id == 555L
            userSegment.name == "VIP Customers"
        }
    }

    def "should handle null user segment component in constructor and wither"() {
        when:
        UserSegmentResponse response = new UserSegmentResponse(null)
        UserSegment replacement = new UserSegment("Community", "signed_in_users").withId(99L)
        UserSegmentResponse updated = response.withUserSegment(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UserSegmentResponse deserialized = objectMapper.readValue(json, UserSegmentResponse.class)

        then:
        verifyAll(response) {
            userSegment() == null
        }
        verifyAll(updated) {
            userSegment() == replacement
            userSegment().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("user_segment")
        }
        verifyAll(deserialized) {
            it == response
            userSegment() == null
        }
    }
}
