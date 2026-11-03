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
import spock.lang.Shared
import spock.lang.Specification

class TicketFormStatusesResponseSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create TicketFormStatusesResponse with canonical constructor, convenience constructor, and builder"() {
        given:
        TicketFormStatus formStatus = new TicketFormStatus("fs-1", 10L, 20L)
        List<TicketFormStatus> list = [formStatus]

        when:
        TicketFormStatusesResponse direct = new TicketFormStatusesResponse(
                list,
                1,
                "https://example.zendesk.com/api/v2/ticket_form_statuses.json?page=2",
                "https://example.zendesk.com/api/v2/ticket_form_statuses.json?page=1"
        )
        TicketFormStatusesResponse convenience = new TicketFormStatusesResponse(list)
        TicketFormStatusesResponse built = TicketFormStatusesResponseBuilder.builder()
                .ticketFormStatuses(list)
                .count(1)
                .nextPage("https://example.zendesk.com/api/v2/ticket_form_statuses.json?page=2")
                .previousPage("https://example.zendesk.com/api/v2/ticket_form_statuses.json?page=1")
                .build()

        then:
        verifyAll(direct) {
            ticketFormStatuses() == list
            ticketFormStatuses().size() == 1
            ticketFormStatuses().first().id == "fs-1"
            count() == 1
            nextPage() == "https://example.zendesk.com/api/v2/ticket_form_statuses.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/ticket_form_statuses.json?page=1"
        }
        verifyAll(convenience) {
            ticketFormStatuses() == list
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(built) {
            it == direct
            ticketFormStatuses() == list
            count() == 1
            nextPage() == "https://example.zendesk.com/api/v2/ticket_form_statuses.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/ticket_form_statuses.json?page=1"
        }
    }

    def "should update TicketFormStatusesResponse using record withers"() {
        given:
        TicketFormStatus first = new TicketFormStatus("fs-1", 10L, 20L)
        TicketFormStatus second = new TicketFormStatus("fs-2", 11L, 21L)
        TicketFormStatusesResponse response = new TicketFormStatusesResponse([first], 1, "next1", "prev1")

        when:
        TicketFormStatusesResponse updated = response
                .withTicketFormStatuses([second])
                .withCount(2)
                .withNextPage("next2")
                .withPreviousPage("prev2")

        then:
        verifyAll(response) {
            ticketFormStatuses() == [first]
            count() == 1
            nextPage() == "next1"
            previousPage() == "prev1"
        }
        verifyAll(updated) {
            ticketFormStatuses() == [second]
            count() == 2
            nextPage() == "next2"
            previousPage() == "prev2"
        }
    }

    def "should round-trip serialize and deserialize TicketFormStatusesResponse"() {
        given:
        TicketFormStatus formStatus = new TicketFormStatus("fs-99", 99L, 199L)
        TicketFormStatusesResponse response = new TicketFormStatusesResponse([formStatus], 1, "https://example.com/next", "https://example.com/prev")

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFormStatusesResponse deserialized = objectMapper.readValue(json, TicketFormStatusesResponse.class)

        then:
        verifyAll(map) {
            containsKey("ticket_form_statuses")
            containsKey("count")
            containsKey("next_page")
            containsKey("previous_page")
            ((List) get("ticket_form_statuses")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            ticketFormStatuses().size() == 1
            ticketFormStatuses().first().id == "fs-99"
            ticketFormStatuses().first().customStatusId == 99L
            ticketFormStatuses().first().ticketFormId == 199L
            count() == 1
            nextPage() == "https://example.com/next"
            previousPage() == "https://example.com/prev"
        }
    }

    def "should handle null components in TicketFormStatusesResponse"() {
        when:
        TicketFormStatusesResponse empty = new TicketFormStatusesResponse(null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFormStatusesResponse deserialized = objectMapper.readValue(json, TicketFormStatusesResponse.class)

        then:
        verifyAll(empty) {
            ticketFormStatuses() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            ticketFormStatuses() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
    }
}
