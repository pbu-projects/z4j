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

class CustomStatusResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CustomStatusResponse with canonical constructor and builder"() {
        given:
        TicketFieldCustomStatusObject expected = new TicketFieldCustomStatusObject()
                .setId(101L)
                .setAgentLabel("Pending Approval")
                .setEndUserLabel("Awaiting Review")

        when:
        CustomStatusResponse direct = new CustomStatusResponse(expected)
        CustomStatusResponse built = CustomStatusResponseBuilder.builder().customStatus(expected).build()

        then:
        verifyAll(direct) {
            customStatus == expected
            customStatus.id == 101L
            customStatus.agentLabel == "Pending Approval"
            customStatus.endUserLabel == "Awaiting Review"
        }
        verifyAll(built) {
            customStatus == expected
            customStatus.id == 101L
            customStatus.agentLabel == "Pending Approval"
            customStatus.endUserLabel == "Awaiting Review"
        }
    }

    def "should update customStatus using record wither"() {
        given:
        TicketFieldCustomStatusObject original = new TicketFieldCustomStatusObject().setId(1L).setAgentLabel("Draft")
        TicketFieldCustomStatusObject updatedStatus = new TicketFieldCustomStatusObject().setId(2L).setAgentLabel("In Review")
        CustomStatusResponse response = new CustomStatusResponse(original)

        when:
        CustomStatusResponse updated = response.withCustomStatus(updatedStatus)

        then:
        verifyAll(response) {
            customStatus == original
            customStatus.agentLabel == "Draft"
        }
        verifyAll(updated) {
            customStatus == updatedStatus
            customStatus.agentLabel == "In Review"
        }
    }

    def "should round-trip serialize and deserialize CustomStatusResponse"() {
        given:
        TicketFieldCustomStatusObject expected = new TicketFieldCustomStatusObject()
                .setId(555L)
                .setAgentLabel("Investigating")
                .setEndUserLabel("We are looking into it")
        CustomStatusResponse response = new CustomStatusResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomStatusResponse deserialized = objectMapper.readValue(json, CustomStatusResponse.class)

        then:
        verifyAll(map) {
            containsKey("custom_status")
            get("custom_status") instanceof Map
            ((Map) get("custom_status")).id == 555
            ((Map) get("custom_status")).agent_label == "Investigating"
        }
        verifyAll(deserialized) {
            it == response
            customStatus != null
            customStatus.id == 555L
            customStatus.agentLabel == "Investigating"
            customStatus.endUserLabel == "We are looking into it"
        }
    }

    def "should handle null customStatus component in constructor and wither"() {
        when:
        CustomStatusResponse response = new CustomStatusResponse(null)
        TicketFieldCustomStatusObject replacement = new TicketFieldCustomStatusObject().setId(99L).setAgentLabel("Closed")
        CustomStatusResponse updated = response.withCustomStatus(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomStatusResponse deserialized = objectMapper.readValue(json, CustomStatusResponse.class)

        then:
        verifyAll(response) {
            customStatus() == null
        }
        verifyAll(updated) {
            customStatus() == replacement
            customStatus().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("custom_status")
        }
        verifyAll(deserialized) {
            it == response
            customStatus() == null
        }
    }
}
