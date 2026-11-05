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

class TicketFieldsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketFieldsResponse with canonical constructor and builder"() {
        given:
        TicketField field = new TicketField().withId(301L).withTitle("Priority Field")
        List<TicketField> list = [field]
        Meta expectedMeta = MetaBuilder.builder().hasMore(true).afterCursor("cursor_1").build()
        Links expectedLinks = LinksBuilder.builder().next(URI.create("https://example.zendesk.com/api/v2/ticket_fields.json?page[after]=cursor_1").toURL()).build()

        when:
        TicketFieldsResponse direct = new TicketFieldsResponse(list, expectedMeta, expectedLinks)
        TicketFieldsResponse built = TicketFieldsResponseBuilder.builder()
                .ticketFields(list)
                .meta(expectedMeta)
                .links(expectedLinks)
                .build()

        then:
        verifyAll(direct) {
            ticketFields() == list
            ticketFields().size() == 1
            ticketFields().first().id == 301L
            ticketFields().first().title == "Priority Field"
            meta() == expectedMeta
            links() == expectedLinks
        }
        verifyAll(built) {
            it == direct
            ticketFields() == list
            meta() == expectedMeta
            links() == expectedLinks
        }
    }

    def "should update TicketFieldsResponse using record withers and addTicketFieldsItem method"() {
        given:
        TicketField first = new TicketField().withId(1L).withTitle("First Field")
        TicketField second = new TicketField().withId(2L).withTitle("Second Field")
        Meta meta1 = MetaBuilder.builder().hasMore(false).build()
        Meta meta2 = MetaBuilder.builder().hasMore(true).afterCursor("after_2").build()
        Links links1 = LinksBuilder.builder().prev(URI.create("https://example.zendesk.com/prev").toURL()).build()
        Links links2 = LinksBuilder.builder().next(URI.create("https://example.zendesk.com/next").toURL()).build()
        TicketFieldsResponse response = new TicketFieldsResponse([first], meta1, links1)

        when:
        TicketFieldsResponse updated = response
                .withTicketFields([second])
                .withMeta(meta2)
                .withLinks(links2)
        TicketFieldsResponse withAdded = response.addTicketFieldsItem(second)
        TicketFieldsResponse fromNull = new TicketFieldsResponse(null, meta1, links1).addTicketFieldsItem(first)

        then:
        verifyAll(response) {
            ticketFields().size() == 1
            ticketFields().first().id == 1L
            meta() == meta1
            links() == links1
        }
        verifyAll(updated) {
            ticketFields() == [second]
            meta() == meta2
            links() == links2
        }
        verifyAll(withAdded) {
            ticketFields().size() == 2
            ticketFields().first().id == 1L
            ticketFields().last().id == 2L
            meta() == meta1
            links() == links1
        }
        verifyAll(fromNull) {
            ticketFields().size() == 1
            ticketFields().first().id == 1L
            meta() == meta1
            links() == links1
        }
    }

    def "should round-trip serialize and deserialize TicketFieldsResponse"() {
        given:
        TicketField field = new TicketField().withId(444L).withTitle("Custom Text")
        Meta expectedMeta = MetaBuilder.builder().hasMore(false).afterCursor("cursor_444").build()
        Links expectedLinks = LinksBuilder.builder().next(URI.create("https://example.zendesk.com/api/v2/ticket_fields.json?page[after]=cursor_444").toURL()).build()
        TicketFieldsResponse response = new TicketFieldsResponse([field], expectedMeta, expectedLinks)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFieldsResponse deserialized = objectMapper.readValue(json, TicketFieldsResponse.class)

        then:
        verifyAll(map) {
            containsKey("ticket_fields")
            containsKey("meta")
            containsKey("links")
            ((List) get("ticket_fields")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            ticketFields().size() == 1
            ticketFields().first().id == 444L
            ticketFields().first().title == "Custom Text"
            meta() == expectedMeta
            links() == expectedLinks
        }
    }

    def "should handle null components in TicketFieldsResponse"() {
        when:
        TicketFieldsResponse empty = new TicketFieldsResponse(null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFieldsResponse deserialized = objectMapper.readValue(json, TicketFieldsResponse.class)

        then:
        verifyAll(empty) {
            ticketFields() == null
            meta() == null
            links() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            ticketFields() == null
            meta() == null
            links() == null
        }
    }
}
