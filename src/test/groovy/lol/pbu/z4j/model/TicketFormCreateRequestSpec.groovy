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

class TicketFormCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketFormCreateRequest with canonical constructor and builder"() {
        given:
        TicketForm expected = new TicketForm().withId(101L).withName("Hardware Request Form").withDisplayName("Hardware Support")

        when:
        TicketFormCreateRequest direct = new TicketFormCreateRequest(expected)
        TicketFormCreateRequest built = TicketFormCreateRequestBuilder.builder()
                .ticketForm(expected)
                .build()

        then:
        verifyAll(direct) {
            ticketForm() == expected
            ticketForm().id == 101L
            ticketForm().name == "Hardware Request Form"
            ticketForm().displayName == "Hardware Support"
        }
        verifyAll(built) {
            it == direct
            ticketForm() == expected
            ticketForm().id == 101L
            ticketForm().name == "Hardware Request Form"
            ticketForm().displayName == "Hardware Support"
        }
    }

    def "should update ticketForm using record wither"() {
        given:
        TicketForm original = new TicketForm().withId(1L).withName("Old Form")
        TicketForm replacement = new TicketForm().withId(2L).withName("New Form")
        TicketFormCreateRequest request = new TicketFormCreateRequest(original)

        when:
        TicketFormCreateRequest updated = request.withTicketForm(replacement)

        then:
        verifyAll(request) {
            ticketForm() == original
            ticketForm().name == "Old Form"
        }
        verifyAll(updated) {
            ticketForm() == replacement
            ticketForm().name == "New Form"
        }
    }

    def "should round-trip serialize and deserialize TicketFormCreateRequest"() {
        given:
        TicketForm expected = new TicketForm().withId(555L).withName("Billing Form").withDisplayName("Billing Inquiry")
        TicketFormCreateRequest request = new TicketFormCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFormCreateRequest deserialized = objectMapper.readValue(json, TicketFormCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("ticket_form")
            get("ticket_form") instanceof Map
            ((Map) get("ticket_form")).id == 555
            ((Map) get("ticket_form")).name == "Billing Form"
        }
        verifyAll(deserialized) {
            it == request
            ticketForm() != null
            ticketForm().id == 555L
            ticketForm().name == "Billing Form"
            ticketForm().displayName == "Billing Inquiry"
        }
    }

    def "should handle null ticketForm component in constructor, wither, and serde"() {
        given:
        TicketForm replacement = new TicketForm().withId(99L).withName("Fallback Form")

        when:
        TicketFormCreateRequest request = new TicketFormCreateRequest(null)
        TicketFormCreateRequest updated = request.withTicketForm(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFormCreateRequest deserialized = objectMapper.readValue(json, TicketFormCreateRequest.class)

        then:
        verifyAll(request) {
            ticketForm() == null
        }
        verifyAll(updated) {
            ticketForm() == replacement
            ticketForm().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("ticket_form")
        }
        verifyAll(deserialized) {
            it == request
            ticketForm() == null
        }
    }
}
