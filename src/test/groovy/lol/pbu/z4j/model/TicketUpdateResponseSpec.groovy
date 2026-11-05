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

class TicketUpdateResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketUpdateResponse with canonical constructor and builder"() {
        given:
        Audit expectedAudit = new Audit().withId(201L).withTicketId(101L)
        Ticket expectedTicket = new Ticket().withId(101L).withSubject("Updated Issue")

        when:
        TicketUpdateResponse direct = new TicketUpdateResponse(expectedAudit, expectedTicket)
        TicketUpdateResponse built = TicketUpdateResponseBuilder.builder()
                .audit(expectedAudit)
                .ticket(expectedTicket)
                .build()

        then:
        verifyAll(direct) {
            audit == expectedAudit
            audit.id == 201L
            audit.ticketId == 101L
            ticket == expectedTicket
            ticket.id == 101L
            ticket.subject == "Updated Issue"
        }
        verifyAll(built) {
            audit == expectedAudit
            audit.id == 201L
            audit.ticketId == 101L
            ticket == expectedTicket
            ticket.id == 101L
            ticket.subject == "Updated Issue"
        }
    }

    def "should update audit and ticket using record withers"() {
        given:
        Audit originalAudit = new Audit().withId(1L)
        Ticket originalTicket = new Ticket().withId(10L).withSubject("Initial")
        TicketUpdateResponse response = new TicketUpdateResponse(originalAudit, originalTicket)

        Audit updatedAudit = new Audit().withId(2L)
        Ticket updatedTicket = new Ticket().withId(20L).withSubject("Modified")

        when:
        TicketUpdateResponse withNewAudit = response.withAudit(updatedAudit)
        TicketUpdateResponse withNewTicket = response.withTicket(updatedTicket)

        then:
        verifyAll(response) {
            audit == originalAudit
            ticket == originalTicket
        }
        verifyAll(withNewAudit) {
            audit == updatedAudit
            ticket == originalTicket
        }
        verifyAll(withNewTicket) {
            audit == originalAudit
            ticket == updatedTicket
        }
    }

    def "should round-trip serialize and deserialize TicketUpdateResponse"() {
        given:
        Audit expectedAudit = new Audit().withId(777L).withTicketId(333L)
        Ticket expectedTicket = new Ticket().withId(333L).withSubject("Server Restored")
        TicketUpdateResponse response = new TicketUpdateResponse(expectedAudit, expectedTicket)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketUpdateResponse deserialized = objectMapper.readValue(json, TicketUpdateResponse.class)

        then:
        verifyAll(map) {
            containsKey("audit")
            containsKey("ticket")
            get("audit") instanceof Map
            ((Map) get("audit")).id == 777
            get("ticket") instanceof Map
            ((Map) get("ticket")).id == 333
            ((Map) get("ticket")).subject == "Server Restored"
        }
        verifyAll(deserialized) {
            it == response
            audit != null
            audit.id == 777L
            ticket != null
            ticket.id == 333L
            ticket.subject == "Server Restored"
        }
    }

    def "should handle null components in constructor and wither"() {
        when:
        TicketUpdateResponse response = new TicketUpdateResponse(null, null)
        Audit replacementAudit = new Audit().withId(50L)
        Ticket replacementTicket = new Ticket().withId(60L)
        TicketUpdateResponse updated = response.withAudit(replacementAudit).withTicket(replacementTicket)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketUpdateResponse deserialized = objectMapper.readValue(json, TicketUpdateResponse.class)

        then:
        verifyAll(response) {
            audit() == null
            ticket() == null
        }
        verifyAll(updated) {
            audit() == replacementAudit
            ticket() == replacementTicket
            audit().id == 50L
            ticket().id == 60L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("audit")
            !containsKey("ticket")
        }
        verifyAll(deserialized) {
            it == response
            audit() == null
            ticket() == null
        }
    }
}
