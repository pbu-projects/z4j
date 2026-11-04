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

class DeletedTicketsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create DeletedTicketsResponse with canonical constructor and builder"() {
        given:
        DeletedTicket ticket = new DeletedTicket().withId(101L).withSubject("Deleted Ticket")
        List<DeletedTicket> list = [ticket]
        Long expectedCount = 1L
        String expectedNext = "https://example.zendesk.com/api/v2/deleted_tickets.json?page=2"
        String expectedPrev = "https://example.zendesk.com/api/v2/deleted_tickets.json?page=1"

        when:
        DeletedTicketsResponse direct = new DeletedTicketsResponse(list, expectedCount, expectedNext, expectedPrev)
        DeletedTicketsResponse built = DeletedTicketsResponseBuilder.builder()
                .deletedTickets(list)
                .count(expectedCount)
                .nextPage(expectedNext)
                .previousPage(expectedPrev)
                .build()

        then:
        verifyAll(direct) {
            deletedTickets() == list
            deletedTickets().size() == 1
            deletedTickets().first().id == 101L
            deletedTickets().first().subject == "Deleted Ticket"
            count() == expectedCount
            nextPage() == expectedNext
            previousPage() == expectedPrev
        }
        verifyAll(built) {
            it == direct
            deletedTickets() == list
            count() == expectedCount
            nextPage() == expectedNext
            previousPage() == expectedPrev
        }
    }

    def "should update DeletedTicketsResponse using record withers and addDeletedTicketsItem method"() {
        given:
        DeletedTicket first = new DeletedTicket().withId(1L).withSubject("First")
        DeletedTicket second = new DeletedTicket().withId(2L).withSubject("Second")
        DeletedTicketsResponse response = new DeletedTicketsResponse([first], 1L, "next_1", "prev_1")

        when:
        DeletedTicketsResponse updated = response
                .withDeletedTickets([second])
                .withCount(2L)
                .withNextPage("next_2")
                .withPreviousPage("prev_2")
        DeletedTicketsResponse withAdded = response.addDeletedTicketsItem(second)
        DeletedTicketsResponse fromNull = new DeletedTicketsResponse(null, 0L, null, null).addDeletedTicketsItem(first)

        then:
        verifyAll(response) {
            deletedTickets().size() == 1
            deletedTickets().first().id == 1L
            count() == 1L
            nextPage() == "next_1"
            previousPage() == "prev_1"
        }
        verifyAll(updated) {
            deletedTickets() == [second]
            count() == 2L
            nextPage() == "next_2"
            previousPage() == "prev_2"
        }
        verifyAll(withAdded) {
            deletedTickets().size() == 2
            deletedTickets().first().id == 1L
            deletedTickets().last().id == 2L
            count() == 1L
            nextPage() == "next_1"
            previousPage() == "prev_1"
        }
        verifyAll(fromNull) {
            deletedTickets().size() == 1
            deletedTickets().first().id == 1L
            count() == 0L
            nextPage() == null
            previousPage() == null
        }
    }

    def "should round-trip serialize and deserialize DeletedTicketsResponse"() {
        given:
        DeletedTicket ticket = new DeletedTicket().withId(555L).withSubject("Trashed Ticket")
        DeletedTicketsResponse response = new DeletedTicketsResponse(
                [ticket],
                1L,
                "https://example.zendesk.com/api/v2/deleted_tickets.json?page=2",
                "https://example.zendesk.com/api/v2/deleted_tickets.json?page=1"
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        DeletedTicketsResponse deserialized = objectMapper.readValue(json, DeletedTicketsResponse.class)

        then:
        verifyAll(map) {
            containsKey("deleted_tickets")
            containsKey("count")
            containsKey("next_page")
            containsKey("previous_page")
            ((List) get("deleted_tickets")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            deletedTickets().size() == 1
            deletedTickets().first().id == 555L
            deletedTickets().first().subject == "Trashed Ticket"
            count() == 1L
            nextPage() == "https://example.zendesk.com/api/v2/deleted_tickets.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/deleted_tickets.json?page=1"
        }
    }

    def "should handle null components in DeletedTicketsResponse"() {
        when:
        DeletedTicketsResponse empty = new DeletedTicketsResponse(null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        DeletedTicketsResponse deserialized = objectMapper.readValue(json, DeletedTicketsResponse.class)

        then:
        verifyAll(empty) {
            deletedTickets() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            deletedTickets() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
    }
}
