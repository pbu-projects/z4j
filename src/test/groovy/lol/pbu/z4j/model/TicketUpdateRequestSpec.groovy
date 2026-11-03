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

class TicketUpdateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketUpdateRequest with canonical constructor and builder"() {
        given:
        TicketUpdateInput expected = new TicketUpdateInput().setSubject("Updated Subject").setTicketFormId(101L)

        when:
        TicketUpdateRequest direct = new TicketUpdateRequest(expected)
        TicketUpdateRequest built = TicketUpdateRequestBuilder.builder()
                .ticket(expected)
                .build()

        then:
        verifyAll(direct) {
            ticket() == expected
            ticket().subject == "Updated Subject"
            ticket().ticketFormId == 101L
        }
        verifyAll(built) {
            it == direct
            ticket() == expected
            ticket().subject == "Updated Subject"
            ticket().ticketFormId == 101L
        }
    }

    def "should update ticket using record wither"() {
        given:
        TicketUpdateInput original = new TicketUpdateInput().setSubject("Original Update")
        TicketUpdateInput replacement = new TicketUpdateInput().setSubject("Replacement Update")
        TicketUpdateRequest request = new TicketUpdateRequest(original)

        when:
        TicketUpdateRequest updated = request.withTicket(replacement)

        then:
        verifyAll(request) {
            ticket() == original
            ticket().subject == "Original Update"
        }
        verifyAll(updated) {
            ticket() == replacement
            ticket().subject == "Replacement Update"
        }
    }

    def "should round-trip serialize and deserialize TicketUpdateRequest"() {
        given:
        TicketUpdateInput expected = new TicketUpdateInput().setSubject("Resolved Ticket").setTicketFormId(555L)
        TicketUpdateRequest request = new TicketUpdateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TicketUpdateRequest deserialized = objectMapper.readValue(json, TicketUpdateRequest.class)

        then:
        verifyAll(map) {
            containsKey("ticket")
            get("ticket") instanceof Map
            ((Map) get("ticket")).subject == "Resolved Ticket"
            ((Map) get("ticket")).ticket_form_id == 555
        }
        verifyAll(deserialized) {
            it == request
            ticket() != null
            ticket().subject == "Resolved Ticket"
            ticket().ticketFormId == 555L
        }
    }

    def "should handle null ticket component in constructor, wither, and serde"() {
        given:
        TicketUpdateInput replacement = new TicketUpdateInput().setSubject("Fallback Subject")

        when:
        TicketUpdateRequest request = new TicketUpdateRequest(null)
        TicketUpdateRequest updated = request.withTicket(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TicketUpdateRequest deserialized = objectMapper.readValue(json, TicketUpdateRequest.class)

        then:
        verifyAll(request) {
            ticket() == null
        }
        verifyAll(updated) {
            ticket() == replacement
            ticket().subject == "Fallback Subject"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("ticket")
        }
        verifyAll(deserialized) {
            it == request
            ticket() == null
        }
    }
}
