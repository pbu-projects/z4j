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
import java.time.ZoneOffset
import java.time.ZonedDateTime
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class TicketsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketsResponse with canonical constructor, convenience constructors, and builder"() {
        given:
        Ticket ticket = new Ticket().withId(101L).withSubject("Support Request")
        User user = new User().withId(201L).withName("Support Agent")
        Group group = new Group()
        Organization org = new Organization().withId(301L)
        TicketMetric metric = new TicketMetric().withId(401L).withTicketId(101L)
        Audit audit = new Audit().withId(501L).withTicketId(101L)
        Meta expectedMeta = MetaBuilder.builder().hasMore(true).afterCursor("cursor_after").beforeCursor("cursor_before").build()
        Links expectedLinks = LinksBuilder.builder()
                .next(URI.create("https://example.zendesk.com/api/v2/views/1/tickets.json?page[after]=cursor_after").toURL())
                .prev(URI.create("https://example.zendesk.com/api/v2/views/1/tickets.json?page[before]=cursor_before").toURL())
                .build()

        when:
        TicketsResponse direct = new TicketsResponse(
                [ticket],
                [user],
                [group],
                [org],
                [metric],
                [audit],
                expectedMeta,
                expectedLinks,
                42
        )
        TicketsResponse convenience = new TicketsResponse([ticket], expectedMeta, expectedLinks, 42)
        TicketsResponse noArg = new TicketsResponse()
        TicketsResponse built = TicketsResponseBuilder.builder()
                .tickets([ticket])
                .users([user])
                .groups([group])
                .organizations([org])
                .metricSets([metric])
                .audits([audit])
                .meta(expectedMeta)
                .links(expectedLinks)
                .count(42)
                .build()

        then:
        verifyAll(direct) {
            tickets() == [ticket]
            tickets().size() == 1
            tickets().first().id == 101L
            tickets().first().subject == "Support Request"
            users() == [user]
            groups() == [group]
            organizations() == [org]
            metricSets() == [metric]
            audits() == [audit]
            meta() == expectedMeta
            links() == expectedLinks
            count() == 42
        }
        verifyAll(convenience) {
            tickets() == [ticket]
            users() == null
            groups() == null
            organizations() == null
            metricSets() == null
            audits() == null
            meta() == expectedMeta
            links() == expectedLinks
            count() == 42
        }
        verifyAll(noArg) {
            tickets() == null
            users() == null
            groups() == null
            organizations() == null
            metricSets() == null
            audits() == null
            meta() == null
            links() == null
            count() == null
        }
        verifyAll(built) {
            it == direct
            tickets() == [ticket]
            users() == [user]
            groups() == [group]
            organizations() == [org]
            metricSets() == [metric]
            audits() == [audit]
            meta() == expectedMeta
            links() == expectedLinks
            count() == 42
        }
    }

    def "should satisfy SideloadableResponse contract"() {
        given:
        Ticket ticket = new Ticket().withId(10L)
        User user = new User().withId(20L)
        Group group = new Group()
        Organization org = new Organization().withId(30L)
        TicketMetric metric = new TicketMetric().withId(40L)
        Audit audit = new Audit().withId(50L)
        TicketsResponse response = new TicketsResponse([ticket], [user], [group], [org], [metric], [audit], null, null, 1)

        when:
        SideloadableResponse contract = response

        then:
        verifyAll(contract) {
            users() == [user]
            groups() == [group]
            organizations() == [org]
            metricSets() == [metric]
            audits() == [audit]
        }
    }

    def "should update TicketsResponse using record withers and addTicketsItem method"() {
        given:
        Ticket existingTicket = new Ticket(faker.number().randomNumber())
        Ticket newTicket = new Ticket(faker.number().randomNumber())
        User user1 = new User().withId(100L)
        User user2 = new User().withId(200L)
        Group group1 = new Group()
        Organization org1 = new Organization().withId(300L)
        TicketMetric metric1 = new TicketMetric().withId(400L)
        Audit audit1 = new Audit().withId(500L)
        Meta meta1 = MetaBuilder.builder().hasMore(false).build()
        Meta meta2 = MetaBuilder.builder().hasMore(true).afterCursor("after_2").build()
        Links links1 = LinksBuilder.builder().prev(URI.create("https://example.zendesk.com/prev").toURL()).build()
        Links links2 = LinksBuilder.builder().next(URI.create("https://example.zendesk.com/next").toURL()).build()
        TicketsResponse response = new TicketsResponse(
                [existingTicket],
                [user1],
                [group1],
                [org1],
                [metric1],
                [audit1],
                meta1,
                links1,
                1
        )

        when:
        TicketsResponse updated = response
                .withTickets([newTicket])
                .withUsers([user2])
                .withGroups([])
                .withOrganizations([])
                .withMetricSets([])
                .withAudits([])
                .withMeta(meta2)
                .withLinks(links2)
                .withCount(2)
        TicketsResponse withAdded = response.addTicketsItem(newTicket)
        TicketsResponse fromNull = new TicketsResponse(null, null, null, null).addTicketsItem(existingTicket)

        then:
        verifyAll(response) {
            tickets() == [existingTicket]
            users() == [user1]
            groups() == [group1]
            organizations() == [org1]
            metricSets() == [metric1]
            audits() == [audit1]
            meta() == meta1
            links() == links1
            count() == 1
        }
        verifyAll(updated) {
            tickets() == [newTicket]
            users() == [user2]
            groups() == []
            organizations() == []
            metricSets() == []
            audits() == []
            meta() == meta2
            links() == links2
            count() == 2
        }
        verifyAll(withAdded) {
            tickets().size() == 2
            tickets().containsAll([existingTicket, newTicket])
            users() == [user1]
            groups() == [group1]
            organizations() == [org1]
            metricSets() == [metric1]
            audits() == [audit1]
            meta() == meta1
            links() == links1
            count() == 1
        }
        verifyAll(fromNull) {
            tickets().size() == 1
            tickets().first() == existingTicket
            users() == null
            groups() == null
            organizations() == null
            metricSets() == null
            audits() == null
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

    def "should round-trip serde TicketsResponse with sideloaded resources"() {
        given:
        Ticket ticket = new Ticket().withId(111L).withSubject("Sideload Test")
        User user = new User().withId(222L).withName("Bob Agent")
        Organization org = new Organization().withId(333L)
        TicketMetric metric = new TicketMetric().withId(444L).withTicketId(111L)
        Audit audit = new Audit().withId(555L).withTicketId(111L)
        TicketsResponse response = new TicketsResponse(
                [ticket],
                [user],
                null,
                [org],
                [metric],
                [audit],
                null,
                null,
                1
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketsResponse deserialized = objectMapper.readValue(json, TicketsResponse.class)

        then:
        verifyAll(map) {
            containsKey("tickets")
            containsKey("users")
            containsKey("organizations")
            containsKey("metric_sets")
            containsKey("audits")
            containsKey("count")
            !containsKey("groups")
            ((List) get("tickets")).size() == 1
            ((List) get("users")).size() == 1
            ((List) get("organizations")).size() == 1
            ((List) get("metric_sets")).size() == 1
            ((List) get("audits")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            tickets().size() == 1
            users().size() == 1
            organizations().size() == 1
            metricSets().size() == 1
            audits().size() == 1
        }
    }

    def "should deserialize complex audit events in TicketsResponse and verify TicketAuditsResponse interoperability"() {
        given:
        String json = '''{
  "tickets": [
    {
      "id": 1001,
      "subject": "Hardware replacement request"
    }
  ],
  "users": [
    {
      "id": 2001,
      "name": "Alex Tech"
    }
  ],
  "metric_sets": [
    {
      "id": 3001,
      "ticket_id": 1001,
      "reopens": 1
    }
  ],
  "audits": [
    {
      "id": 4001,
      "ticket_id": 1001,
      "author_id": 2001,
      "created_at": "2026-11-12T11:00:00Z",
      "events": [
        {
          "id": 5001,
          "type": "Comment",
          "body": "Part has been ordered from supplier."
        },
        {
          "id": 5002,
          "type": "Change",
          "field_name": "status",
          "value": "pending",
          "previous_value": "open"
        }
      ]
    }
  ],
  "count": 1
}'''
        String auditsJson = '''{
  "audits": [
    {
      "id": 4001,
      "ticket_id": 1001,
      "author_id": 2001,
      "created_at": "2026-11-12T11:00:00Z",
      "events": [
        {
          "id": 5001,
          "type": "Comment",
          "body": "Part has been ordered from supplier."
        },
        {
          "id": 5002,
          "type": "Change",
          "field_name": "status",
          "value": "pending",
          "previous_value": "open"
        }
      ]
    }
  ],
  "count": 1
}'''

        when:
        TicketsResponse response = objectMapper.readValue(json, TicketsResponse.class)
        TicketAuditsResponse standaloneAudits = objectMapper.readValue(auditsJson, TicketAuditsResponse.class)
        TicketAuditsResponse interoperableAudits = new TicketAuditsResponse(
                response.audits(),
                response.audits().size(),
                null,
                null
        )

        then:
        verifyAll(response) {
            tickets() != null
            tickets().size() == 1
            tickets().first().id == 1001L
            users().size() == 1
            users().first().id == 2001L
            metricSets().size() == 1
            metricSets().first().reopens == 1L
            audits() != null
            audits().size() == 1
        }

        Audit audit = response.audits().first()
        verifyAll(audit) {
            id() == 4001L
            ticketId() == 1001L
            authorId() == 2001L
            createdAt() == ZonedDateTime.of(2026, 11, 12, 11, 0, 0, 0, ZoneOffset.UTC)
            events() != null
            events().size() == 2
        }

        AuditEventsInner commentEvent = audit.events().find { it.type() == "Comment" }
        AuditEventsInner changeEvent = audit.events().find { it.type() == "Change" }
        verifyAll(commentEvent) {
            id() == 5001L
            type() == "Comment"
            body() == "Part has been ordered from supplier."
        }
        verifyAll(changeEvent) {
            id() == 5002L
            type() == "Change"
            fieldName() == "status"
            value() == "pending"
            previousValue() == "open"
        }

        verifyAll(interoperableAudits) {
            audits() == response.audits()
            audits() == standaloneAudits.audits()
            count() == 1
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
            users() == null
            groups() == null
            organizations() == null
            metricSets() == null
            audits() == null
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
            users() == null
            groups() == null
            organizations() == null
            metricSets() == null
            audits() == null
            meta() == null
            links() == null
            count() == null
        }
    }
}
