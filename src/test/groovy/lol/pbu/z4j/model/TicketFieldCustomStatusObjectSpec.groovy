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

import java.time.ZonedDateTime

class TicketFieldCustomStatusObjectSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketFieldCustomStatusObject with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.now()

        when:
        TicketFieldCustomStatusObject direct = new TicketFieldCustomStatusObject(
                true,
                "Pending Dev",
                now,
                false,
                "Waiting on developers",
                "Under investigation",
                "Investigating",
                1001L,
                TicketFieldCustomStatusObjectStatusCategory.OPEN,
                now
        )
        TicketFieldCustomStatusObject noArg = new TicketFieldCustomStatusObject()
        TicketFieldCustomStatusObject built = TicketFieldCustomStatusObjectBuilder.builder()
                .active(true)
                .agentLabel("Pending Dev")
                .createdAt(now)
                .isDefault(false)
                .description("Waiting on developers")
                .endUserDescription("Under investigation")
                .endUserLabel("Investigating")
                .id(1001L)
                .statusCategory(TicketFieldCustomStatusObjectStatusCategory.OPEN)
                .updatedAt(now)
                .build()

        then:
        verifyAll(direct) {
            active() == true
            agentLabel() == "Pending Dev"
            createdAt() == now
            isDefault() == false
            description() == "Waiting on developers"
            endUserDescription() == "Under investigation"
            endUserLabel() == "Investigating"
            id() == 1001L
            statusCategory() == TicketFieldCustomStatusObjectStatusCategory.OPEN
            updatedAt() == now
        }
        verifyAll(noArg) {
            active() == null
            agentLabel() == null
            createdAt() == null
            isDefault() == null
            description() == null
            endUserDescription() == null
            endUserLabel() == null
            id() == null
            statusCategory() == null
            updatedAt() == null
        }
        verifyAll(built) {
            it == direct
            active() == true
            agentLabel() == "Pending Dev"
            createdAt() == now
            isDefault() == false
            description() == "Waiting on developers"
            endUserDescription() == "Under investigation"
            endUserLabel() == "Investigating"
            id() == 1001L
            statusCategory() == TicketFieldCustomStatusObjectStatusCategory.OPEN
            updatedAt() == now
        }
    }

    def "should update TicketFieldCustomStatusObject with record withers"() {
        given:
        ZonedDateTime time1 = ZonedDateTime.parse("2026-01-01T10:00:00Z")
        ZonedDateTime time2 = ZonedDateTime.parse("2026-01-02T10:00:00Z")
        TicketFieldCustomStatusObject initial = new TicketFieldCustomStatusObject()

        when:
        TicketFieldCustomStatusObject updated = initial
                .withActive(false)
                .withAgentLabel("Closed Status")
                .withCreatedAt(time1)
                .withIsDefault(true)
                .withDescription("Ticket completed")
                .withEndUserDescription("Resolved description")
                .withEndUserLabel("Resolved")
                .withId(2002L)
                .withStatusCategory(TicketFieldCustomStatusObjectStatusCategory.SOLVED)
                .withUpdatedAt(time2)

        then:
        verifyAll(updated) {
            active() == false
            agentLabel() == "Closed Status"
            createdAt() == time1
            isDefault() == true
            description() == "Ticket completed"
            endUserDescription() == "Resolved description"
            endUserLabel() == "Resolved"
            id() == 2002L
            statusCategory() == TicketFieldCustomStatusObjectStatusCategory.SOLVED
            updatedAt() == time2
        }
    }

    def "should round-trip serialize and deserialize TicketFieldCustomStatusObject"() {
        given:
        TicketFieldCustomStatusObject status = new TicketFieldCustomStatusObject(
                true,
                "Escalated Agent",
                null,
                true,
                "Escalated description",
                "Escalated end-user description",
                "Escalated Label",
                3003L,
                TicketFieldCustomStatusObjectStatusCategory.HOLD,
                null
        )

        when:
        String json = objectMapper.writeValueAsString(status)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFieldCustomStatusObject deserialized = objectMapper.readValue(json, TicketFieldCustomStatusObject.class)

        then:
        verifyAll(map) {
            containsKey("active")
            containsKey("agent_label")
            containsKey("default")
            containsKey("description")
            containsKey("end_user_description")
            containsKey("end_user_label")
            containsKey("id")
            containsKey("status_category")
            get("id") == 3003
            get("agent_label") == "Escalated Agent"
            get("status_category") == "hold"
        }
        verifyAll(deserialized) {
            active() == true
            agentLabel() == "Escalated Agent"
            isDefault() == true
            description() == "Escalated description"
            endUserDescription() == "Escalated end-user description"
            endUserLabel() == "Escalated Label"
            id() == 3003L
            statusCategory() == TicketFieldCustomStatusObjectStatusCategory.HOLD
        }
    }

    def "should handle null fields in serialization"() {
        when:
        TicketFieldCustomStatusObject empty = new TicketFieldCustomStatusObject()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFieldCustomStatusObject deserialized = objectMapper.readValue(json, TicketFieldCustomStatusObject.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            active() == null
            agentLabel() == null
            createdAt() == null
            isDefault() == null
            description() == null
            endUserDescription() == null
            endUserLabel() == null
            id() == null
            statusCategory() == null
            updatedAt() == null
        }
    }
}
