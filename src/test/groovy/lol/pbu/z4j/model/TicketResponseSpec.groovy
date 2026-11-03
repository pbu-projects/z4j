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

class TicketResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketResponse with canonical constructor and builder"() {
        given:
        Ticket expected = new Ticket().setId(101L).setSubject("Printer offline").setDescription("Cannot print documents")

        when:
        TicketResponse direct = new TicketResponse(expected)
        TicketResponse built = TicketResponseBuilder.builder().ticket(expected).build()

        then:
        verifyAll(direct) {
            ticket == expected
            ticket.id == 101L
            ticket.subject == "Printer offline"
            ticket.description == "Cannot print documents"
        }
        verifyAll(built) {
            ticket == expected
            ticket.id == 101L
            ticket.subject == "Printer offline"
            ticket.description == "Cannot print documents"
        }
    }

    def "should update ticket using record wither"() {
        given:
        Ticket original = new Ticket().setId(1L).setSubject("Old Subject")
        Ticket updatedTicket = new Ticket().setId(2L).setSubject("New Subject")
        TicketResponse response = new TicketResponse(original)

        when:
        TicketResponse updated = response.withTicket(updatedTicket)

        then:
        verifyAll(response) {
            ticket == original
            ticket.subject == "Old Subject"
        }
        verifyAll(updated) {
            ticket == updatedTicket
            ticket.subject == "New Subject"
        }
    }

    def "should round-trip serialize and deserialize TicketResponse"() {
        given:
        Ticket expected = new Ticket().setId(555L).setSubject("Network Outage").setDescription("Main switch rebooted")
        TicketResponse response = new TicketResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketResponse deserialized = objectMapper.readValue(json, TicketResponse.class)

        then:
        verifyAll(map) {
            containsKey("ticket")
            get("ticket") instanceof Map
            ((Map) get("ticket")).id == 555
            ((Map) get("ticket")).subject == "Network Outage"
        }
        verifyAll(deserialized) {
            it == response
            ticket != null
            ticket.id == 555L
            ticket.subject == "Network Outage"
            ticket.description == "Main switch rebooted"
        }
    }

    def "should handle null ticket component in constructor and wither"() {
        when:
        TicketResponse response = new TicketResponse(null)
        Ticket replacement = new Ticket().setId(99L).setSubject("Resolved Ticket")
        TicketResponse updated = response.withTicket(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketResponse deserialized = objectMapper.readValue(json, TicketResponse.class)

        then:
        verifyAll(response) {
            ticket() == null
        }
        verifyAll(updated) {
            ticket() == replacement
            ticket().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("ticket")
        }
        verifyAll(deserialized) {
            it == response
            ticket() == null
        }
    }
}
