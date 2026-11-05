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

class UserRelatedResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create UserRelatedResponse with canonical constructor and builder"() {
        given:
        UserRelated expected = new UserRelated()
                .setAssignedTickets(5)
                .setCcdTickets(2)
                .setOrganizationSubscriptions(1)
                .setRequestedTickets(10)

        when:
        UserRelatedResponse direct = new UserRelatedResponse(expected)
        UserRelatedResponse built = UserRelatedResponseBuilder.builder().userRelated(expected).build()

        then:
        verifyAll(direct) {
            userRelated == expected
            userRelated.assignedTickets == 5
            userRelated.ccdTickets == 2
            userRelated.organizationSubscriptions == 1
            userRelated.requestedTickets == 10
        }
        verifyAll(built) {
            userRelated == expected
            userRelated.assignedTickets == 5
            userRelated.ccdTickets == 2
            userRelated.organizationSubscriptions == 1
            userRelated.requestedTickets == 10
        }
    }

    def "should update userRelated using record wither"() {
        given:
        UserRelated original = new UserRelated().setAssignedTickets(1)
        UserRelated replacement = new UserRelated().setAssignedTickets(8)
        UserRelatedResponse response = new UserRelatedResponse(original)

        when:
        UserRelatedResponse updated = response.withUserRelated(replacement)

        then:
        verifyAll(response) {
            userRelated == original
            userRelated.assignedTickets == 1
        }
        verifyAll(updated) {
            userRelated == replacement
            userRelated.assignedTickets == 8
        }
    }

    def "should round-trip serialize and deserialize UserRelatedResponse"() {
        given:
        UserRelated expected = new UserRelated()
                .setAssignedTickets(15)
                .setCcdTickets(4)
                .setOrganizationSubscriptions(3)
                .setRequestedTickets(25)
        UserRelatedResponse response = new UserRelatedResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UserRelatedResponse deserialized = objectMapper.readValue(json, UserRelatedResponse.class)

        then:
        verifyAll(map) {
            containsKey("user_related")
            get("user_related") instanceof Map
            ((Map) get("user_related")).assigned_tickets == 15
            ((Map) get("user_related")).requested_tickets == 25
        }
        verifyAll(deserialized) {
            it == response
            userRelated != null
            userRelated.assignedTickets == 15
            userRelated.ccdTickets == 4
            userRelated.organizationSubscriptions == 3
            userRelated.requestedTickets == 25
        }
    }

    def "should handle null userRelated component in constructor and wither"() {
        when:
        UserRelatedResponse response = new UserRelatedResponse(null)
        UserRelated replacement = new UserRelated().setAssignedTickets(42)
        UserRelatedResponse updated = response.withUserRelated(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UserRelatedResponse deserialized = objectMapper.readValue(json, UserRelatedResponse.class)

        then:
        verifyAll(response) {
            userRelated() == null
        }
        verifyAll(updated) {
            userRelated() == replacement
            userRelated().assignedTickets == 42
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("user_related")
        }
        verifyAll(deserialized) {
            it == response
            userRelated() == null
        }
    }
}
