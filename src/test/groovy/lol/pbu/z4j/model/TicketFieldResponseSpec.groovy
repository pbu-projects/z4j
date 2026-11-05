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

class TicketFieldResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketFieldResponse with canonical constructor and builder"() {
        given:
        TicketField expected = new TicketField("Priority Level", "dropdown").setId(101L)

        when:
        TicketFieldResponse direct = new TicketFieldResponse(expected)
        TicketFieldResponse built = TicketFieldResponseBuilder.builder().ticketField(expected).build()

        then:
        verifyAll(direct) {
            ticketField == expected
            ticketField.id == 101L
            ticketField.title == "Priority Level"
            ticketField.type == "dropdown"
        }
        verifyAll(built) {
            ticketField == expected
            ticketField.id == 101L
            ticketField.title == "Priority Level"
            ticketField.type == "dropdown"
        }
    }

    def "should update ticketField using record wither"() {
        given:
        TicketField original = new TicketField("Old Field", "text").setId(1L)
        TicketField updatedField = new TicketField("New Field", "text").setId(2L)
        TicketFieldResponse response = new TicketFieldResponse(original)

        when:
        TicketFieldResponse updated = response.withTicketField(updatedField)

        then:
        verifyAll(response) {
            ticketField == original
            ticketField.title == "Old Field"
        }
        verifyAll(updated) {
            ticketField == updatedField
            ticketField.title == "New Field"
        }
    }

    def "should round-trip serialize and deserialize TicketFieldResponse"() {
        given:
        TicketField expected = new TicketField("Department", "text").setId(555L)
        TicketFieldResponse response = new TicketFieldResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFieldResponse deserialized = objectMapper.readValue(json, TicketFieldResponse.class)

        then:
        verifyAll(map) {
            containsKey("ticket_field")
            get("ticket_field") instanceof Map
            ((Map) get("ticket_field")).id == 555
            ((Map) get("ticket_field")).title == "Department"
        }
        verifyAll(deserialized) {
            it == response
            ticketField != null
            ticketField.id == 555L
            ticketField.title == "Department"
        }
    }

    def "should handle null ticketField component in constructor and wither"() {
        when:
        TicketFieldResponse response = new TicketFieldResponse(null)
        TicketField replacement = new TicketField("Replacement", "text").setId(99L)
        TicketFieldResponse updated = response.withTicketField(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFieldResponse deserialized = objectMapper.readValue(json, TicketFieldResponse.class)

        then:
        verifyAll(response) {
            ticketField() == null
        }
        verifyAll(updated) {
            ticketField() == replacement
            ticketField().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("ticket_field")
        }
        verifyAll(deserialized) {
            it == response
            ticketField() == null
        }
    }
}
