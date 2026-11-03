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

class TicketFormResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketFormResponse with canonical constructor and builder"() {
        given:
        TicketForm expected = new TicketForm("Support Request Form").setId(101L).setEndUserVisible(true)

        when:
        TicketFormResponse direct = new TicketFormResponse(expected)
        TicketFormResponse built = TicketFormResponseBuilder.builder().ticketForm(expected).build()

        then:
        verifyAll(direct) {
            ticketForm == expected
            ticketForm.id == 101L
            ticketForm.name == "Support Request Form"
            ticketForm.endUserVisible == true
        }
        verifyAll(built) {
            ticketForm == expected
            ticketForm.id == 101L
            ticketForm.name == "Support Request Form"
            ticketForm.endUserVisible == true
        }
    }

    def "should update ticketForm using record wither"() {
        given:
        TicketForm original = new TicketForm("Old Form").setId(1L)
        TicketForm updatedForm = new TicketForm("New Form").setId(2L)
        TicketFormResponse response = new TicketFormResponse(original)

        when:
        TicketFormResponse updated = response.withTicketForm(updatedForm)

        then:
        verifyAll(response) {
            ticketForm == original
            ticketForm.name == "Old Form"
        }
        verifyAll(updated) {
            ticketForm == updatedForm
            ticketForm.name == "New Form"
        }
    }

    def "should round-trip serialize and deserialize TicketFormResponse"() {
        given:
        TicketForm expected = new TicketForm("Billing Inquiry").setId(555L).setEndUserVisible(false)
        TicketFormResponse response = new TicketFormResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFormResponse deserialized = objectMapper.readValue(json, TicketFormResponse.class)

        then:
        verifyAll(map) {
            containsKey("ticket_form")
            get("ticket_form") instanceof Map
            ((Map) get("ticket_form")).id == 555
            ((Map) get("ticket_form")).name == "Billing Inquiry"
        }
        verifyAll(deserialized) {
            it == response
            ticketForm != null
            ticketForm.id == 555L
            ticketForm.name == "Billing Inquiry"
            ticketForm.endUserVisible == false
        }
    }

    def "should handle null ticketForm component in constructor and wither"() {
        when:
        TicketFormResponse response = new TicketFormResponse(null)
        TicketForm replacement = new TicketForm("Replacement Form").setId(99L)
        TicketFormResponse updated = response.withTicketForm(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFormResponse deserialized = objectMapper.readValue(json, TicketFormResponse.class)

        then:
        verifyAll(response) {
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
            it == response
            ticketForm() == null
        }
    }
}
