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

class TicketEventSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create TicketEvent with constructors and builder"() {
        given:
        AuditEventsInner child = new AuditEventsInner().withId(10L).withType("Comment")

        when:
        TicketEvent direct = new TicketEvent(
                101L,
                202L,
                "Comment",
                "web",
                "2026-09-26T06:00:00Z",
                303L,
                "Test comment",
                [key: "value"],
                [child]
        )
        TicketEvent noArg = new TicketEvent()
        TicketEvent idOnly = new TicketEvent(555L)
        TicketEvent built = TicketEventBuilder.builder()
                .id(101L)
                .ticketId(202L)
                .eventType("Comment")
                .via("web")
                .createdAt("2026-09-26T06:00:00Z")
                .authorId(303L)
                .value("Test comment")
                .metadata([key: "value"])
                .auditEvents([child])
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
            ticketId() == 202L
            eventType() == "Comment"
            via() == "web"
            createdAt() == "2026-09-26T06:00:00Z"
            authorId() == 303L
            value() == "Test comment"
            metadata() == [key: "value"]
            auditEvents() == [child]
        }
        verifyAll(noArg) {
            id() == null
            ticketId() == null
            auditEvents() == null
        }
        verifyAll(idOnly) {
            id() == 555L
            ticketId() == null
        }
        verifyAll(built) {
            it == direct
            id() == 101L
            ticketId() == 202L
        }
    }

    def "should update TicketEvent using withers and addAuditEventsItem"() {
        given:
        AuditEventsInner e1 = new AuditEventsInner().withId(1L)
        AuditEventsInner e2 = new AuditEventsInner().withId(2L)
        TicketEvent initial = new TicketEvent()

        when:
        TicketEvent updated = initial
                .withId(400L)
                .withTicketId(500L)
                .withEventType("Audit")
                .withVia("mail")
                .withCreatedAt("2026-10-01T00:00:00Z")
                .withAuthorId(600L)
                .withValue("value1")
                .withMetadata([foo: "bar"])
                .withAuditEvents([e1])
        TicketEvent added = updated.addAuditEventsItem(e2)
        TicketEvent fromNull = new TicketEvent().addAuditEventsItem(e1)

        then:
        verifyAll(updated) {
            id() == 400L
            ticketId() == 500L
            eventType() == "Audit"
            via() == "mail"
            createdAt() == "2026-10-01T00:00:00Z"
            authorId() == 600L
            value() == "value1"
            metadata() == [foo: "bar"]
            auditEvents().size() == 1
        }
        verifyAll(added) {
            auditEvents().size() == 2
            auditEvents().first() == e1
            auditEvents().last() == e2
        }
        verifyAll(fromNull) {
            auditEvents().size() == 1
            auditEvents().first() == e1
        }
    }

    def "should round-trip serialize and deserialize TicketEvent with child_events alias"() {
        given:
        TicketEvent event = new TicketEvent()
                .withId(700L)
                .withTicketId(800L)
                .withEventType("Create")

        when:
        String json = objectMapper.writeValueAsString(event)
        Map map = objectMapper.readValue(json, Map.class)
        TicketEvent deserialized = objectMapper.readValue(json, TicketEvent.class)

        then:
        verifyAll(map) {
            get("id") == 700
            get("ticket_id") == 800
            get("event_type") == "Create"
        }
        verifyAll(deserialized) {
            it == event
            id() == 700L
            ticketId() == 800L
            eventType() == "Create"
        }

        when: "deserializing with child_events alias"
        String aliasJson = '{"id":701,"child_events":[{"id":10,"type":"Comment"}]}'
        TicketEvent fromAlias = objectMapper.readValue(aliasJson, TicketEvent.class)

        then:
        verifyAll(fromAlias) {
            id() == 701L
            auditEvents() != null
            auditEvents().size() == 1
            auditEvents().first().id() == 10L
        }
    }

    def "should verify TicketEvent constants"() {
        expect:
        verifyAll {
            TicketEvent.JSON_PROPERTY_ID == "id"
            TicketEvent.JSON_PROPERTY_TICKET_ID == "ticket_id"
            TicketEvent.JSON_PROPERTY_EVENT_TYPE == "event_type"
            TicketEvent.JSON_PROPERTY_VIA == "via"
            TicketEvent.JSON_PROPERTY_CREATED_AT == "created_at"
            TicketEvent.JSON_PROPERTY_AUTHOR_ID == "author_id"
            TicketEvent.JSON_PROPERTY_VALUE == "value"
            TicketEvent.JSON_PROPERTY_METADATA == "metadata"
            TicketEvent.JSON_PROPERTY_AUDIT_EVENTS == "audit_events"
            TicketEvent.JSON_PROPERTY_CHILD_EVENTS == "child_events"
        }
    }
}
