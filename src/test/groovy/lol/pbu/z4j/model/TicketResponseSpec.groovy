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
import java.time.ZoneOffset
import java.time.ZonedDateTime
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class TicketResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketResponse with canonical constructor, convenience constructors, and builder"() {
        given:
        Ticket expectedTicket = new Ticket().withId(101L).withSubject("Printer offline").withDescription("Cannot print documents")
        User expectedUser = new User().withId(201L).withName("Alice Smith")
        Group expectedGroup = new Group()
        Organization expectedOrg = new Organization().withId(301L)
        TicketMetric expectedMetric = new TicketMetric().withId(401L).withTicketId(101L)
        Audit expectedAudit = new Audit().withId(501L).withTicketId(101L)

        when:
        TicketResponse direct = new TicketResponse(
                expectedTicket,
                [expectedUser],
                [expectedGroup],
                [expectedOrg],
                [expectedMetric],
                [expectedAudit]
        )
        TicketResponse singleArg = new TicketResponse(expectedTicket)
        TicketResponse noArg = new TicketResponse()
        TicketResponse built = TicketResponseBuilder.builder()
                .ticket(expectedTicket)
                .users([expectedUser])
                .groups([expectedGroup])
                .organizations([expectedOrg])
                .metricSets([expectedMetric])
                .audits([expectedAudit])
                .build()

        then:
        verifyAll(direct) {
            ticket == expectedTicket
            users() == [expectedUser]
            groups() == [expectedGroup]
            organizations() == [expectedOrg]
            metricSets() == [expectedMetric]
            audits() == [expectedAudit]
        }
        verifyAll(singleArg) {
            ticket == expectedTicket
            users() == null
            groups() == null
            organizations() == null
            metricSets() == null
            audits() == null
        }
        verifyAll(noArg) {
            ticket == null
            users() == null
            groups() == null
            organizations() == null
            metricSets() == null
            audits() == null
        }
        verifyAll(built) {
            it == direct
            ticket == expectedTicket
            users() == [expectedUser]
            groups() == [expectedGroup]
            organizations() == [expectedOrg]
            metricSets() == [expectedMetric]
            audits() == [expectedAudit]
        }
    }

    def "should satisfy SideloadableResponse interface contract"() {
        given:
        Ticket ticket = new Ticket().withId(10L)
        User user = new User().withId(20L)
        Group group = new Group()
        Organization org = new Organization().withId(30L)
        TicketMetric metric = new TicketMetric().withId(40L)
        Audit audit = new Audit().withId(50L)
        TicketResponse response = new TicketResponse(ticket, [user], [group], [org], [metric], [audit])

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

    def "should update sideloaded fields using record withers"() {
        given:
        Ticket original = new Ticket().withId(1L).withSubject("Old Subject")
        Ticket replacementTicket = new Ticket().withId(2L).withSubject("New Subject")
        User user = new User().withId(11L)
        Group group = new Group()
        Organization org = new Organization().withId(22L)
        TicketMetric metric = new TicketMetric().withId(33L)
        Audit audit = new Audit().withId(44L)
        TicketResponse response = new TicketResponse(original)

        when:
        TicketResponse updated = response
                .withTicket(replacementTicket)
                .withUsers([user])
                .withGroups([group])
                .withOrganizations([org])
                .withMetricSets([metric])
                .withAudits([audit])

        then:
        verifyAll(response) {
            ticket == original
            users() == null
            groups() == null
            organizations() == null
            metricSets() == null
            audits() == null
        }
        verifyAll(updated) {
            ticket == replacementTicket
            users() == [user]
            groups() == [group]
            organizations() == [org]
            metricSets() == [metric]
            audits() == [audit]
        }
    }

    def "should round-trip serialize and deserialize TicketResponse with sideloads"() {
        given:
        Ticket ticket = new Ticket().withId(555L).withSubject("Network Outage").withDescription("Main switch rebooted")
        User user = new User().withId(666L).withName("Network Admin")
        Organization org = new Organization().withId(777L)
        TicketMetric metric = new TicketMetric().withId(888L).withTicketId(555L)
        Audit audit = new Audit().withId(999L).withTicketId(555L)
        TicketResponse response = new TicketResponse(ticket, [user], null, [org], [metric], [audit])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketResponse deserialized = objectMapper.readValue(json, TicketResponse.class)

        then:
        verifyAll(map) {
            containsKey("ticket")
            containsKey("users")
            containsKey("organizations")
            containsKey("metric_sets")
            containsKey("audits")
            !containsKey("groups")
            ((Map) get("ticket")).get("id") == 555
            ((List) get("users")).size() == 1
            ((List) get("organizations")).size() == 1
            ((List) get("metric_sets")).size() == 1
            ((List) get("audits")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            ticket != null
            ticket.id == 555L
            users().size() == 1
            users().first().id == 666L
            organizations().size() == 1
            organizations().first().id == 777L
            metricSets().size() == 1
            metricSets().first().id == 888L
            audits().size() == 1
            audits().first().id == 999L
        }
    }

    def "should deserialize complex audit events and demonstrate interoperability with TicketAuditsResponse"() {
        given:
        String json = '''{
  "ticket": {
    "id": 1001,
    "subject": "System crash after upgrade"
  },
  "users": [
    {
      "id": 2001,
      "name": "Jane Support"
    }
  ],
  "metric_sets": [
    {
      "id": 3001,
      "ticket_id": 1001,
      "replies": 3
    }
  ],
  "audits": [
    {
      "id": 4001,
      "ticket_id": 1001,
      "author_id": 2001,
      "created_at": "2026-11-12T10:00:00Z",
      "events": [
        {
          "id": 5001,
          "type": "Comment",
          "body": "Investigating node logs."
        },
        {
          "id": 5002,
          "type": "Change",
          "field_name": "status",
          "value": "solved",
          "previous_value": "open"
        }
      ]
    }
  ]
}'''
        String auditsJson = '''{
  "audits": [
    {
      "id": 4001,
      "ticket_id": 1001,
      "author_id": 2001,
      "created_at": "2026-11-12T10:00:00Z",
      "events": [
        {
          "id": 5001,
          "type": "Comment",
          "body": "Investigating node logs."
        },
        {
          "id": 5002,
          "type": "Change",
          "field_name": "status",
          "value": "solved",
          "previous_value": "open"
        }
      ]
    }
  ],
  "count": 1
}'''

        when:
        TicketResponse response = objectMapper.readValue(json, TicketResponse.class)
        TicketAuditsResponse standaloneAudits = objectMapper.readValue(auditsJson, TicketAuditsResponse.class)
        TicketAuditsResponse interoperableAudits = new TicketAuditsResponse(
                response.audits(),
                response.audits().size(),
                null,
                null
        )

        then:
        verifyAll(response) {
            ticket != null
            ticket.id == 1001L
            users().size() == 1
            users().first().id == 2001L
            metricSets().size() == 1
            metricSets().first().replies == 3L
            audits() != null
            audits().size() == 1
        }

        Audit audit = response.audits().first()
        verifyAll(audit) {
            id() == 4001L
            ticketId() == 1001L
            authorId() == 2001L
            createdAt() == ZonedDateTime.of(2026, 11, 12, 10, 0, 0, 0, ZoneOffset.UTC)
            events() != null
            events().size() == 2
        }

        AuditEventsInner commentEvent = audit.events().find { it.type() == "Comment" }
        AuditEventsInner changeEvent = audit.events().find { it.type() == "Change" }
        verifyAll(commentEvent) {
            id() == 5001L
            type() == "Comment"
            body() == "Investigating node logs."
        }
        verifyAll(changeEvent) {
            id() == 5002L
            type() == "Change"
            fieldName() == "status"
            value() == "solved"
            previousValue() == "open"
        }

        verifyAll(interoperableAudits) {
            audits() == response.audits()
            audits() == standaloneAudits.audits()
            count() == 1
        }
    }

    def "should handle null ticket and sideload components in constructor and wither"() {
        when:
        TicketResponse response = new TicketResponse(null)
        Ticket replacement = new Ticket().withId(99L).withSubject("Resolved Ticket")
        TicketResponse updated = response.withTicket(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketResponse deserialized = objectMapper.readValue(json, TicketResponse.class)

        then:
        verifyAll(response) {
            ticket == null
            users() == null
            groups() == null
            organizations() == null
            metricSets() == null
            audits() == null
        }
        verifyAll(updated) {
            ticket == replacement
            ticket.id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("ticket")
            !containsKey("users")
            !containsKey("groups")
            !containsKey("organizations")
            !containsKey("metric_sets")
            !containsKey("audits")
        }
        verifyAll(deserialized) {
            it == response
            ticket == null
            users() == null
            groups() == null
            organizations() == null
            metricSets() == null
            audits() == null
        }
    }

    def "should deserialize last_audits json alias into audits"() {
        given:
        String json = '''{
  "ticket": { "id": 100 },
  "last_audits": [
    {
      "id": 999,
      "ticket_id": 100
    }
  ]
}'''

        when:
        TicketResponse response = objectMapper.readValue(json, TicketResponse.class)

        then:
        verifyAll(response) {
            ticket() != null
            ticket().id() == 100L
            audits() != null
            audits().size() == 1
            audits().first().id() == 999L
            audits().first().ticketId() == 100L
        }
    }
}
