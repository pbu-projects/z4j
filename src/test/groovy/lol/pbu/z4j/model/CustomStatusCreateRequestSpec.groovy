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

class CustomStatusCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CustomStatusCreateRequest with canonical constructor and builder"() {
        given:
        TicketFieldCustomStatusObject expected = new TicketFieldCustomStatusObject()
                .withId(101L)
                .withAgentLabel("Awaiting Vendor")
                .withEndUserLabel("In Progress")

        when:
        CustomStatusCreateRequest direct = new CustomStatusCreateRequest(expected)
        CustomStatusCreateRequest built = CustomStatusCreateRequestBuilder.builder()
                .customStatus(expected)
                .build()

        then:
        verifyAll(direct) {
            customStatus() == expected
            customStatus().id == 101L
            customStatus().agentLabel == "Awaiting Vendor"
            customStatus().endUserLabel == "In Progress"
        }
        verifyAll(built) {
            it == direct
            customStatus() == expected
            customStatus().id == 101L
            customStatus().agentLabel == "Awaiting Vendor"
            customStatus().endUserLabel == "In Progress"
        }
    }

    def "should update customStatus using record wither"() {
        given:
        TicketFieldCustomStatusObject original = new TicketFieldCustomStatusObject().withId(1L).withAgentLabel("Drafting")
        TicketFieldCustomStatusObject replacement = new TicketFieldCustomStatusObject().withId(2L).withAgentLabel("Reviewing")
        CustomStatusCreateRequest request = new CustomStatusCreateRequest(original)

        when:
        CustomStatusCreateRequest updated = request.withCustomStatus(replacement)

        then:
        verifyAll(request) {
            customStatus() == original
            customStatus().agentLabel == "Drafting"
        }
        verifyAll(updated) {
            customStatus() == replacement
            customStatus().agentLabel == "Reviewing"
        }
    }

    def "should round-trip serialize and deserialize CustomStatusCreateRequest"() {
        given:
        TicketFieldCustomStatusObject expected = new TicketFieldCustomStatusObject()
                .withId(555L)
                .withAgentLabel("Escalated")
                .withEndUserLabel("Under Review")
        CustomStatusCreateRequest request = new CustomStatusCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CustomStatusCreateRequest deserialized = objectMapper.readValue(json, CustomStatusCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("custom_status")
            get("custom_status") instanceof Map
            ((Map) get("custom_status")).id == 555
            ((Map) get("custom_status")).agent_label == "Escalated"
        }
        verifyAll(deserialized) {
            it == request
            customStatus() != null
            customStatus().id == 555L
            customStatus().agentLabel == "Escalated"
            customStatus().endUserLabel == "Under Review"
        }
    }
}
