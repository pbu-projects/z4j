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

class TicketFieldCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketFieldCreateRequest with canonical constructor and builder"() {
        given:
        TicketField expected = new TicketField("Order Number", "text").withId(101L)

        when:
        TicketFieldCreateRequest direct = new TicketFieldCreateRequest(expected)
        TicketFieldCreateRequest built = TicketFieldCreateRequestBuilder.builder()
                .ticketField(expected)
                .build()

        then:
        verifyAll(direct) {
            ticketField() == expected
            ticketField().id == 101L
            ticketField().title == "Order Number"
            ticketField().type == "text"
        }
        verifyAll(built) {
            it == direct
            ticketField() == expected
            ticketField().id == 101L
            ticketField().title == "Order Number"
            ticketField().type == "text"
        }
    }

    def "should update ticketField using record wither"() {
        given:
        TicketField original = new TicketField("Old Field", "text").withId(1L)
        TicketField replacement = new TicketField("New Field", "integer").withId(2L)
        TicketFieldCreateRequest request = new TicketFieldCreateRequest(original)

        when:
        TicketFieldCreateRequest updated = request.withTicketField(replacement)

        then:
        verifyAll(request) {
            ticketField() == original
            ticketField().title == "Old Field"
        }
        verifyAll(updated) {
            ticketField() == replacement
            ticketField().title == "New Field"
        }
    }

    def "should round-trip serialize and deserialize TicketFieldCreateRequest"() {
        given:
        TicketField expected = new TicketField("Serial Number", "text").withId(555L)
        TicketFieldCreateRequest request = new TicketFieldCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFieldCreateRequest deserialized = objectMapper.readValue(json, TicketFieldCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("ticket_field")
            get("ticket_field") instanceof Map
            ((Map) get("ticket_field")).id == 555
            ((Map) get("ticket_field")).title == "Serial Number"
        }
        verifyAll(deserialized) {
            it == request
            ticketField() != null
            ticketField().id == 555L
            ticketField().title == "Serial Number"
            ticketField().type == "text"
        }
    }

    def "should handle null ticketField component in constructor, wither, and serde"() {
        given:
        TicketField replacement = new TicketField("Fallback Field", "text").withId(99L)

        when:
        TicketFieldCreateRequest request = new TicketFieldCreateRequest(null)
        TicketFieldCreateRequest updated = request.withTicketField(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFieldCreateRequest deserialized = objectMapper.readValue(json, TicketFieldCreateRequest.class)

        then:
        verifyAll(request) {
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
            it == request
            ticketField() == null
        }
    }
}
