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

import java.time.ZoneOffset
import java.time.ZonedDateTime

class AuditSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Audit with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC)
        AuditEventsInner event = new AuditEventsInner().withId(101L).withType("Comment")
        Via expectedVia = new Via()

        when:
        Audit direct = new Audit(
                1001L,
                now,
                [event],
                2001L,
                [customKey: "customValue"],
                3001L,
                expectedVia
        )
        Audit noArg = new Audit()
        Audit built = AuditBuilder.builder()
                .authorId(1001L)
                .createdAt(now)
                .events([event])
                .id(2001L)
                .metadata([customKey: "customValue"])
                .ticketId(3001L)
                .via(expectedVia)
                .build()

        then:
        verifyAll(direct) {
            authorId() == 1001L
            createdAt() == now
            events() == [event]
            id() == 2001L
            metadata() == [customKey: "customValue"]
            ticketId() == 3001L
            via == expectedVia
        }
        verifyAll(noArg) {
            authorId() == null
            createdAt() == null
            events() == null
            id() == null
            metadata() == null
            ticketId() == null
            via() == null
        }
        verifyAll(built) {
            it == direct
            authorId() == 1001L
            id() == 2001L
        }
    }

    def "should update Audit using withers and addEventsItem"() {
        given:
        AuditEventsInner event1 = new AuditEventsInner().withId(1L).withType("Comment")
        AuditEventsInner event2 = new AuditEventsInner().withId(2L).withType("Change")
        Audit initial = new Audit()

        when:
        Audit updated = initial
                .withAuthorId(55L)
                .withId(77L)
                .withTicketId(99L)
                .withEvents([event1])
        Audit added = updated.addEventsItem(event2)
        Audit fromNull = new Audit().addEventsItem(event1)

        then:
        verifyAll(updated) {
            authorId() == 55L
            id() == 77L
            ticketId() == 99L
            events().size() == 1
            events().first() == event1
        }
        verifyAll(added) {
            events().size() == 2
            events().first() == event1
            events().last() == event2
        }
        verifyAll(fromNull) {
            events().size() == 1
            events().first() == event1
        }
    }

    def "should round-trip serialize and deserialize Audit"() {
        given:
        Audit audit = new Audit()
                .withId(500L)
                .withTicketId(600L)
                .withAuthorId(700L)

        when:
        String json = objectMapper.writeValueAsString(audit)
        Map map = objectMapper.readValue(json, Map.class)
        Audit deserialized = objectMapper.readValue(json, Audit.class)

        then:
        verifyAll(map) {
            get("id") == 500
            get("ticket_id") == 600
            get("author_id") == 700
        }
        verifyAll(deserialized) {
            it == audit
            id() == 500L
            ticketId() == 600L
            authorId() == 700L
        }
    }

    def "should verify Audit constants"() {
        expect:
        verifyAll {
            Audit.JSON_PROPERTY_AUTHOR_ID == "author_id"
            Audit.JSON_PROPERTY_CREATED_AT == "created_at"
            Audit.JSON_PROPERTY_EVENTS == "events"
            Audit.JSON_PROPERTY_ID == "id"
            Audit.JSON_PROPERTY_METADATA == "metadata"
            Audit.JSON_PROPERTY_TICKET_ID == "ticket_id"
            Audit.JSON_PROPERTY_VIA == "via"
        }
    }
}
