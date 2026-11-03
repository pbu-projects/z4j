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

class IncrementalTicketCursorResponseSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create IncrementalTicketCursorResponse with canonical constructor, convenience constructor, and builder"() {
        given:
        Ticket ticket = new Ticket().setId(123L).setSubject("Sample Ticket")
        List<Ticket> expectedTickets = [ticket]

        when:
        IncrementalTicketCursorResponse direct = new IncrementalTicketCursorResponse(
                expectedTickets, "cursor_abc", "after_abc", "before_abc", false, 1
        )
        IncrementalTicketCursorResponse convenience = new IncrementalTicketCursorResponse(expectedTickets)
        IncrementalTicketCursorResponse built = IncrementalTicketCursorResponseBuilder.builder()
                .tickets(expectedTickets)
                .cursor("cursor_abc")
                .afterCursor("after_abc")
                .beforeCursor("before_abc")
                .endOfStream(false)
                .count(1)
                .build()

        then:
        verifyAll(direct) {
            tickets() == expectedTickets
            results() == expectedTickets
            cursor() == "cursor_abc"
            afterCursor() == "after_abc"
            beforeCursor() == "before_abc"
            endOfStream() == false
            count() == 1
        }
        verifyAll(convenience) {
            tickets() == expectedTickets
            results() == expectedTickets
            cursor() == null
            afterCursor() == null
            beforeCursor() == null
            endOfStream() == null
            count() == null
        }
        verifyAll(built) {
            it == direct
            tickets() == expectedTickets
            results() == expectedTickets
            cursor() == "cursor_abc"
            afterCursor() == "after_abc"
            beforeCursor() == "before_abc"
            endOfStream() == false
            count() == 1
        }
    }

    def "should update IncrementalTicketCursorResponse using record withers"() {
        given:
        Ticket first = new Ticket().setId(1L)
        Ticket second = new Ticket().setId(2L)
        IncrementalTicketCursorResponse response = new IncrementalTicketCursorResponse(
                [first], "c1", "a1", "b1", false, 1
        )

        when:
        IncrementalTicketCursorResponse updated = response
                .withTickets([second])
                .withCursor("c2")
                .withAfterCursor("a2")
                .withBeforeCursor("b2")
                .withEndOfStream(true)
                .withCount(2)

        then:
        verifyAll(response) {
            tickets() == [first]
            results() == [first]
            cursor() == "c1"
            afterCursor() == "a1"
            beforeCursor() == "b1"
            endOfStream() == false
            count() == 1
        }
        verifyAll(updated) {
            tickets() == [second]
            results() == [second]
            cursor() == "c2"
            afterCursor() == "a2"
            beforeCursor() == "b2"
            endOfStream() == true
            count() == 2
        }
    }

    def "should round-trip serialize and deserialize IncrementalTicketCursorResponse"() {
        given:
        Ticket ticket = new Ticket().setId(123L).setSubject("Exported Ticket")
        IncrementalTicketCursorResponse response = new IncrementalTicketCursorResponse(
                [ticket], "cur_123", "after_123", "before_123", true, 1
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        IncrementalTicketCursorResponse deserialized = objectMapper.readValue(json, IncrementalTicketCursorResponse.class)

        then:
        verifyAll(map) {
            containsKey("tickets")
            containsKey("cursor")
            containsKey("after_cursor")
            containsKey("before_cursor")
            containsKey("end_of_stream")
            containsKey("count")
            !containsKey("results")
        }
        verifyAll(deserialized) {
            it == response
            tickets().size() == 1
            tickets().first().id == 123L
            results() == tickets()
            cursor() == "cur_123"
            afterCursor() == "after_123"
            beforeCursor() == "before_123"
            endOfStream() == true
            count() == 1
        }
    }

    def "should handle null components in IncrementalTicketCursorResponse"() {
        when:
        IncrementalTicketCursorResponse empty = new IncrementalTicketCursorResponse(null, null, null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        IncrementalTicketCursorResponse deserialized = objectMapper.readValue(json, IncrementalTicketCursorResponse.class)

        then:
        verifyAll(empty) {
            tickets() == null
            results() == null
            cursor() == null
            afterCursor() == null
            beforeCursor() == null
            endOfStream() == null
            count() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            tickets() == null
            results() == null
            cursor() == null
            afterCursor() == null
            beforeCursor() == null
            endOfStream() == null
            count() == null
        }
    }
}
