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

class CreateUserSegmentRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CreateUserSegmentRequest with canonical constructor and builder"() {
        given:
        UserSegment expected = new UserSegment("VIP Customers", "signed_in_users").setId(101L)

        when:
        CreateUserSegmentRequest direct = new CreateUserSegmentRequest(expected)
        CreateUserSegmentRequest built = CreateUserSegmentRequestBuilder.builder().userSegment(expected).build()

        then:
        verifyAll(direct) {
            userSegment() == expected
            userSegment().id == 101L
            userSegment().name == "VIP Customers"
        }
        verifyAll(built) {
            it == direct
            userSegment() == expected
            userSegment().id == 101L
            userSegment().name == "VIP Customers"
        }
    }

    def "should update userSegment using record wither"() {
        given:
        UserSegment originalSegment = new UserSegment("Staff", "staff").setId(1L)
        UserSegment newSegment = new UserSegment("Members", "signed_in_users").setId(2L)
        CreateUserSegmentRequest request = new CreateUserSegmentRequest(originalSegment)

        when:
        CreateUserSegmentRequest updated = request.withUserSegment(newSegment)

        then:
        verifyAll(request) {
            userSegment() == originalSegment
            userSegment().name == "Staff"
        }
        verifyAll(updated) {
            userSegment() == newSegment
            userSegment().name == "Members"
        }
    }

    def "should round-trip serialize and deserialize CreateUserSegmentRequest"() {
        given:
        UserSegment expected = new UserSegment("Agents", "staff").setId(555L)
        CreateUserSegmentRequest request = new CreateUserSegmentRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CreateUserSegmentRequest deserialized = objectMapper.readValue(json, CreateUserSegmentRequest.class)

        then:
        verifyAll(map) {
            containsKey("user_segment")
            get("user_segment") instanceof Map
            ((Map) get("user_segment")).id == 555
            ((Map) get("user_segment")).name == "Agents"
        }
        verifyAll(deserialized) {
            it == request
            userSegment() != null
            userSegment().id == 555L
            userSegment().name == "Agents"
        }
    }

    def "should handle null userSegment component in constructor and wither"() {
        when:
        CreateUserSegmentRequest request = new CreateUserSegmentRequest(null)
        UserSegment replacement = new UserSegment("Created", "signed_in_users").setId(99L)
        CreateUserSegmentRequest updated = request.withUserSegment(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CreateUserSegmentRequest deserialized = objectMapper.readValue(json, CreateUserSegmentRequest.class)

        then:
        verifyAll(request) {
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
            it == request
            userSegment() == null
        }
    }
}
