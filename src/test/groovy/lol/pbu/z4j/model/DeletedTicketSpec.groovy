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

class DeletedTicketSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create DeletedTicket with constructors and builder"() {
        when:
        DeletedTicket direct = new DeletedTicket(
                101L,
                "Broken laptop",
                "Screen is flickering",
                [id: 50L, name: "Admin"],
                "2026-10-01T12:00:00Z",
                "open"
        )
        DeletedTicket noArg = new DeletedTicket()
        DeletedTicket built = DeletedTicketBuilder.builder()
                .id(101L)
                .subject("Broken laptop")
                .description("Screen is flickering")
                .actor([id: 50L, name: "Admin"])
                .deletedAt("2026-10-01T12:00:00Z")
                .previousState("open")
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
            subject() == "Broken laptop"
            description() == "Screen is flickering"
            actor() == [id: 50L, name: "Admin"]
            deletedAt() == "2026-10-01T12:00:00Z"
            previousState() == "open"
        }
        verifyAll(noArg) {
            id() == null
            subject() == null
            description() == null
            actor() == null
            deletedAt() == null
            previousState() == null
        }
        verifyAll(built) {
            it == direct
            id() == 101L
            subject() == "Broken laptop"
        }
    }

    def "should update DeletedTicket using withers"() {
        given:
        DeletedTicket initial = new DeletedTicket()

        when:
        DeletedTicket updated = initial
                .withId(202L)
                .withSubject("Printer issue")
                .withDescription("Out of paper")
                .withActor([id: 99L])
                .withDeletedAt("2026-10-02T15:30:00Z")
                .withPreviousState("solved")

        then:
        verifyAll(updated) {
            id() == 202L
            subject() == "Printer issue"
            description() == "Out of paper"
            actor() == [id: 99L]
            deletedAt() == "2026-10-02T15:30:00Z"
            previousState() == "solved"
        }
    }

    def "should round-trip serialize and deserialize DeletedTicket"() {
        given:
        DeletedTicket ticket = new DeletedTicket()
                .withId(303L)
                .withSubject("Network down")
                .withPreviousState("pending")

        when:
        String json = objectMapper.writeValueAsString(ticket)
        Map map = objectMapper.readValue(json, Map.class)
        DeletedTicket deserialized = objectMapper.readValue(json, DeletedTicket.class)

        then:
        verifyAll(map) {
            get("id") == 303
            get("subject") == "Network down"
            get("previous_state") == "pending"
        }
        verifyAll(deserialized) {
            it == ticket
            id() == 303L
            subject() == "Network down"
            previousState() == "pending"
        }
    }

    def "should verify DeletedTicket constants"() {
        expect:
        verifyAll {
            DeletedTicket.JSON_PROPERTY_ID == "id"
            DeletedTicket.JSON_PROPERTY_SUBJECT == "subject"
            DeletedTicket.JSON_PROPERTY_DESCRIPTION == "description"
            DeletedTicket.JSON_PROPERTY_ACTOR == "actor"
            DeletedTicket.JSON_PROPERTY_DELETED_AT == "deleted_at"
            DeletedTicket.JSON_PROPERTY_PREVIOUS_STATE == "previous_state"
        }
    }
}
