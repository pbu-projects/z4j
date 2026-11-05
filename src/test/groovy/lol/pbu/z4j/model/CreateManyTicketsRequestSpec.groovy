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

class CreateManyTicketsRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CreateManyTicketsRequest with canonical constructor and builder"() {
        given:
        TicketCreateInput ticket1 = new TicketCreateInput(new TicketComment("First ticket")).withSubject("Ticket 1")
        TicketCreateInput ticket2 = new TicketCreateInput(new TicketComment("Second ticket")).withSubject("Ticket 2")
        List<TicketCreateInput> items = [ticket1, ticket2]

        when:
        CreateManyTicketsRequest direct = new CreateManyTicketsRequest(items)
        CreateManyTicketsRequest built = CreateManyTicketsRequestBuilder.builder()
                .tickets(items)
                .build()

        then:
        verifyAll(direct) {
            tickets() == items
            tickets().size() == 2
            tickets().first().subject == "Ticket 1"
            tickets().last().subject == "Ticket 2"
        }
        verifyAll(built) {
            it == direct
            tickets() == items
            tickets().size() == 2
            tickets().first().subject == "Ticket 1"
            tickets().last().subject == "Ticket 2"
        }
    }

    def "should update tickets using record wither and addTicketsItem method"() {
        given:
        TicketCreateInput ticket1 = new TicketCreateInput(new TicketComment("First ticket")).withSubject("Ticket 1")
        TicketCreateInput ticket2 = new TicketCreateInput(new TicketComment("Second ticket")).withSubject("Ticket 2")
        CreateManyTicketsRequest request = new CreateManyTicketsRequest([ticket1])

        when:
        CreateManyTicketsRequest withNewList = request.withTickets([ticket2])
        CreateManyTicketsRequest withAdded = request.addTicketsItem(ticket2)
        CreateManyTicketsRequest fromNull = new CreateManyTicketsRequest(null).addTicketsItem(ticket1)

        then:
        verifyAll(request) {
            tickets().size() == 1
            tickets().first() == ticket1
        }
        verifyAll(withNewList) {
            tickets().size() == 1
            tickets().first() == ticket2
        }
        verifyAll(withAdded) {
            tickets().size() == 2
            tickets().first() == ticket1
            tickets().last() == ticket2
        }
        verifyAll(fromNull) {
            tickets().size() == 1
            tickets().first() == ticket1
        }
    }

    def "should round-trip serialize and deserialize CreateManyTicketsRequest"() {
        given:
        TicketCreateInput ticket1 = new TicketCreateInput(new TicketComment("Batch ticket 1")).withSubject("Batch 1")
        TicketCreateInput ticket2 = new TicketCreateInput(new TicketComment("Batch ticket 2")).withSubject("Batch 2")
        CreateManyTicketsRequest request = new CreateManyTicketsRequest([ticket1, ticket2])

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CreateManyTicketsRequest deserialized = objectMapper.readValue(json, CreateManyTicketsRequest.class)

        then:
        verifyAll(map) {
            containsKey("tickets")
            get("tickets") instanceof List
            ((List) get("tickets")).size() == 2
        }
        verifyAll(deserialized) {
            it == request
            tickets() != null
            tickets().size() == 2
            tickets().first().subject == "Batch 1"
            tickets().last().subject == "Batch 2"
        }
    }

    def "should handle null tickets component in constructor, wither, and serde"() {
        given:
        TicketCreateInput replacement = new TicketCreateInput(new TicketComment("Replacement")).withSubject("Subject")

        when:
        CreateManyTicketsRequest request = new CreateManyTicketsRequest(null)
        CreateManyTicketsRequest updated = request.withTickets([replacement])
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CreateManyTicketsRequest deserialized = objectMapper.readValue(json, CreateManyTicketsRequest.class)

        then:
        verifyAll(request) {
            tickets() == null
        }
        verifyAll(updated) {
            tickets() != null
            tickets() == [replacement]
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("tickets")
        }
        verifyAll(deserialized) {
            it == request
            tickets() == null
        }
    }
}
