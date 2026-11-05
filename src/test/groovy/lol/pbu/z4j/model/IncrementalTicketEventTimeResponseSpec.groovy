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

class IncrementalTicketEventTimeResponseSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create IncrementalTicketEventTimeResponse with canonical constructor, convenience constructor, and builder"() {
        given:
        TicketEvent event = new TicketEvent().withId(999L).withTicketId(10L).withEventType("Audit")
        List<TicketEvent> events = [event]

        when:
        IncrementalTicketEventTimeResponse direct = new IncrementalTicketEventTimeResponse(
                events,
                1727330400L,
                "https://example.zendesk.com/api/v2/incremental/ticket_events.json?start_time=1727330400",
                true,
                1
        )
        IncrementalTicketEventTimeResponse convenience = new IncrementalTicketEventTimeResponse(events)
        IncrementalTicketEventTimeResponse built = IncrementalTicketEventTimeResponseBuilder.builder()
                .ticketEvents(events)
                .endTime(1727330400L)
                .nextPage("https://example.zendesk.com/api/v2/incremental/ticket_events.json?start_time=1727330400")
                .endOfStream(true)
                .count(1)
                .build()

        then:
        verifyAll(direct) {
            ticketEvents() == events
            results() == events
            endTime() == 1727330400L
            nextPage() == "https://example.zendesk.com/api/v2/incremental/ticket_events.json?start_time=1727330400"
            endOfStream() == true
            count() == 1
        }
        verifyAll(convenience) {
            ticketEvents() == events
            results() == events
            endTime() == null
            nextPage() == null
            endOfStream() == null
            count() == null
        }
        verifyAll(built) {
            it == direct
            ticketEvents() == events
            results() == events
            endTime() == 1727330400L
            nextPage() == "https://example.zendesk.com/api/v2/incremental/ticket_events.json?start_time=1727330400"
            endOfStream() == true
            count() == 1
        }
    }

    def "should update IncrementalTicketEventTimeResponse using record withers"() {
        given:
        TicketEvent first = new TicketEvent().withId(1L)
        TicketEvent second = new TicketEvent().withId(2L)
        IncrementalTicketEventTimeResponse response = new IncrementalTicketEventTimeResponse(
                [first], 1000L, "next1", false, 1
        )

        when:
        IncrementalTicketEventTimeResponse updated = response
                .withTicketEvents([second])
                .withEndTime(2000L)
                .withNextPage("next2")
                .withEndOfStream(true)
                .withCount(2)

        then:
        verifyAll(response) {
            ticketEvents() == [first]
            results() == [first]
            endTime() == 1000L
            nextPage() == "next1"
            endOfStream() == false
            count() == 1
        }
        verifyAll(updated) {
            ticketEvents() == [second]
            results() == [second]
            endTime() == 2000L
            nextPage() == "next2"
            endOfStream() == true
            count() == 2
        }
    }

    def "should round-trip serialize and deserialize IncrementalTicketEventTimeResponse"() {
        given:
        TicketEvent event = new TicketEvent().withId(999L).withTicketId(50L).withEventType("Audit")
        IncrementalTicketEventTimeResponse response = new IncrementalTicketEventTimeResponse(
                [event], 1727330400L, "https://example.com/next", true, 1
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        IncrementalTicketEventTimeResponse deserialized = objectMapper.readValue(json, IncrementalTicketEventTimeResponse.class)

        then:
        verifyAll(map) {
            containsKey("ticket_events")
            containsKey("end_time")
            containsKey("next_page")
            containsKey("end_of_stream")
            containsKey("count")
            !containsKey("results")
        }
        verifyAll(deserialized) {
            it == response
            ticketEvents().size() == 1
            ticketEvents().first().id == 999L
            results() == ticketEvents()
            endTime() == 1727330400L
            nextPage() == "https://example.com/next"
            endOfStream() == true
            count() == 1
        }
    }

    def "should handle null components in IncrementalTicketEventTimeResponse"() {
        when:
        IncrementalTicketEventTimeResponse empty = new IncrementalTicketEventTimeResponse(null, null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        IncrementalTicketEventTimeResponse deserialized = objectMapper.readValue(json, IncrementalTicketEventTimeResponse.class)

        then:
        verifyAll(empty) {
            ticketEvents() == null
            results() == null
            endTime() == null
            nextPage() == null
            endOfStream() == null
            count() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            ticketEvents() == null
            results() == null
            endTime() == null
            nextPage() == null
            endOfStream() == null
            count() == null
        }
    }
}
