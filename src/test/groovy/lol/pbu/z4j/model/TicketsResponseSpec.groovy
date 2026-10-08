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
import spock.lang.Unroll

import java.net.URI

class TicketsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add tickets item"() {
        given:
        def ticketsResponse = new TicketsResponse()
        ticketsResponse.tickets == null
        def ticket = new Ticket(faker.number().randomNumber())

        when:
        ticketsResponse.addTicketsItem(ticket)

        then:
        ticketsResponse.tickets.size() == 1
        ticketsResponse.tickets.getAt(0) == ticket
    }

    @Unroll
    def "add tickets item to existing list"() {
        given:
        def existingTicket = new Ticket(faker.number().randomNumber())
        def ticketsResponse = new TicketsResponse(tickets: [existingTicket])
        def newTicket = new Ticket(faker.number().randomNumber())

        when:
        ticketsResponse.addTicketsItem(newTicket)

        then:
        ticketsResponse.tickets.size() == 2
        ticketsResponse.tickets.containsAll([existingTicket, newTicket])
    }

    def "should instantiate and set meta, links, and count on TicketsResponse"() {
        given:
        def response = new TicketsResponse()
        def meta = new Meta().setHasMore(true).setAfterCursor("cursor_after").setBeforeCursor("cursor_before")
        def links = new Links().setNext(new URI("https://example.zendesk.com/api/v2/views/1/tickets.json?page[after]=cursor_after").toURL())
                               .setPrev(new URI("https://example.zendesk.com/api/v2/views/1/tickets.json?page[before]=cursor_before").toURL())

        when:
        response.setMeta(meta)
                .setLinks(links)
                .setCount(42)

        then:
        response.meta == meta
        response.links == links
        response.count == 42
    }

    def "should round-trip serde TicketsResponse with meta, links, and count"() {
        given:
        def objectMapper = adminCtx.getBean(ObjectMapper)
        def json = '''{
  "tickets": [
    {
      "id": 12345
    }
  ],
  "meta": {
    "has_more": true,
    "after_cursor": "cursor_after",
    "before_cursor": "cursor_before"
  },
  "links": {
    "next": "https://example.zendesk.com/api/v2/views/1/tickets.json?page[after]=cursor_after",
    "prev": "https://example.zendesk.com/api/v2/views/1/tickets.json?page[before]=cursor_before"
  },
  "count": 1
}'''

        when:
        def response = objectMapper.readValue(json, TicketsResponse)

        then:
        response != null
        response.tickets != null
        response.tickets.size() == 1
        response.tickets[0].id == 12345L
        response.meta != null
        response.meta.hasMore == true
        response.meta.afterCursor == "cursor_after"
        response.meta.beforeCursor == "cursor_before"
        response.links != null
        response.links.next.toString() == "https://example.zendesk.com/api/v2/views/1/tickets.json?page[after]=cursor_after"
        response.links.prev.toString() == "https://example.zendesk.com/api/v2/views/1/tickets.json?page[before]=cursor_before"
        response.count == 1

        when:
        def serialized = objectMapper.writeValueAsString(response)
        def deserialized = objectMapper.readValue(serialized, TicketsResponse)

        then:
        deserialized == response
    }
}

