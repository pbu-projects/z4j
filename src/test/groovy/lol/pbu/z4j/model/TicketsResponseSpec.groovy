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
import java.net.URI
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class TicketsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketsResponse with canonical constructor and builder"() {
        given:
        Ticket ticket = new Ticket().withId(101L).withSubject("Support Request")
        List<Ticket> list = [ticket]
        Meta expectedMeta = MetaBuilder.builder().hasMore(true).afterCursor("cursor_after").beforeCursor("cursor_before").build()
        Links expectedLinks = LinksBuilder.builder()
                .next(URI.create("https://example.zendesk.com/api/v2/views/1/tickets.json?page[after]=cursor_after").toURL())
                .prev(URI.create("https://example.zendesk.com/api/v2/views/1/tickets.json?page[before]=cursor_before").toURL())
                .build()

        when:
        TicketsResponse direct = new TicketsResponse(list, expectedMeta, expectedLinks, 42)
        TicketsResponse built = TicketsResponseBuilder.builder()
                .tickets(list)
                .meta(expectedMeta)
                .links(expectedLinks)
                .count(42)
                .build()

        then:
        verifyAll(direct) {
            tickets() == list
            tickets().size() == 1
            tickets().first().id == 101L
            tickets().first().subject == "Support Request"
            meta() == expectedMeta
            links() == expectedLinks
            count() == 42
        }
        verifyAll(built) {
            it == direct
            tickets() == list
            meta() == expectedMeta
            links() == expectedLinks
            count() == 42
        }
    }

    def "should update TicketsResponse using record withers and addTicketsItem method"() {
        given:
        Ticket existingTicket = new Ticket(faker.number().randomNumber())
        Ticket newTicket = new Ticket(faker.number().randomNumber())
        Meta meta1 = MetaBuilder.builder().hasMore(false).build()
        Meta meta2 = MetaBuilder.builder().hasMore(true).afterCursor("after_2").build()
        Links links1 = LinksBuilder.builder().prev(URI.create("https://example.zendesk.com/prev").toURL()).build()
        Links links2 = LinksBuilder.builder().next(URI.create("https://example.zendesk.com/next").toURL()).build()
        TicketsResponse response = new TicketsResponse([existingTicket], meta1, links1, 1)

        when:
        TicketsResponse updated = response
                .withTickets([newTicket])
                .withMeta(meta2)
                .withLinks(links2)
                .withCount(2)
        TicketsResponse withAdded = response.addTicketsItem(newTicket)
        TicketsResponse fromNull = new TicketsResponse(null, null, null, null).addTicketsItem(existingTicket)

        then:
        verifyAll(response) {
            tickets() == [existingTicket]
            meta() == meta1
            links() == links1
            count() == 1
        }
        verifyAll(updated) {
            tickets() == [newTicket]
            meta() == meta2
            links() == links2
            count() == 2
        }
        verifyAll(withAdded) {
            tickets().size() == 2
            tickets().containsAll([existingTicket, newTicket])
            meta() == meta1
            links() == links1
            count() == 1
        }
        verifyAll(fromNull) {
            tickets().size() == 1
            tickets().first() == existingTicket
            meta() == null
            links() == null
            count() == null
        }
    }

    def "should round-trip serde TicketsResponse with meta, links, and count"() {
        given:
        String json = '''{
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
        TicketsResponse response = objectMapper.readValue(json, TicketsResponse)
        String serialized = objectMapper.writeValueAsString(response)
        TicketsResponse deserialized = objectMapper.readValue(serialized, TicketsResponse)

        then:
        verifyAll(response) {
            tickets() != null
            tickets().size() == 1
            tickets()[0].id == 12345L
            meta() != null
            meta().hasMore == true
            meta().afterCursor == "cursor_after"
            meta().beforeCursor == "cursor_before"
            links() != null
            links().next.toString() == "https://example.zendesk.com/api/v2/views/1/tickets.json?page[after]=cursor_after"
            links().prev.toString() == "https://example.zendesk.com/api/v2/views/1/tickets.json?page[before]=cursor_before"
            count() == 1
        }
        verifyAll(deserialized) {
            it == response
        }
    }

    def "should handle null components in TicketsResponse"() {
        when:
        TicketsResponse empty = new TicketsResponse(null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketsResponse deserialized = objectMapper.readValue(json, TicketsResponse.class)

        then:
        verifyAll(empty) {
            tickets() == null
            meta() == null
            links() == null
            count() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            tickets() == null
            meta() == null
            links() == null
            count() == null
        }
    }
}
