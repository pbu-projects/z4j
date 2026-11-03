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
import spock.lang.Shared

class TicketAuditsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketAuditsResponse with canonical constructor and builder"() {
        given:
        Audit audit = new Audit().setId(1001L).setTicketId(500L)
        List<Audit> list = [audit]
        Integer expectedCount = 1
        String expectedNext = "https://example.zendesk.com/api/v2/tickets/500/audits.json?page=2"
        String expectedPrev = "https://example.zendesk.com/api/v2/tickets/500/audits.json?page=1"

        when:
        TicketAuditsResponse direct = new TicketAuditsResponse(list, expectedCount, expectedNext, expectedPrev)
        TicketAuditsResponse built = TicketAuditsResponseBuilder.builder()
                .audits(list)
                .count(expectedCount)
                .nextPage(expectedNext)
                .previousPage(expectedPrev)
                .build()

        then:
        verifyAll(direct) {
            audits() == list
            audits().size() == 1
            audits().first().id == 1001L
            audits().first().ticketId == 500L
            count() == expectedCount
            nextPage() == expectedNext
            previousPage() == expectedPrev
        }
        verifyAll(built) {
            it == direct
            audits() == list
            count() == expectedCount
            nextPage() == expectedNext
            previousPage() == expectedPrev
        }
    }

    def "should update TicketAuditsResponse using record withers"() {
        given:
        Audit first = new Audit().setId(1L).setTicketId(10L)
        Audit second = new Audit().setId(2L).setTicketId(20L)
        TicketAuditsResponse response = new TicketAuditsResponse([first], 1, "next_1", "prev_1")

        when:
        TicketAuditsResponse updated = response
                .withAudits([second])
                .withCount(2)
                .withNextPage("next_2")
                .withPreviousPage("prev_2")

        then:
        verifyAll(response) {
            audits() == [first]
            count() == 1
            nextPage() == "next_1"
            previousPage() == "prev_1"
        }
        verifyAll(updated) {
            audits() == [second]
            count() == 2
            nextPage() == "next_2"
            previousPage() == "prev_2"
        }
    }

    def "should round-trip serialize and deserialize TicketAuditsResponse"() {
        given:
        Audit audit = new Audit().setId(777L).setTicketId(888L)
        TicketAuditsResponse response = new TicketAuditsResponse(
                [audit],
                1,
                "https://example.zendesk.com/api/v2/tickets/888/audits.json?page=2",
                "https://example.zendesk.com/api/v2/tickets/888/audits.json?page=1"
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketAuditsResponse deserialized = objectMapper.readValue(json, TicketAuditsResponse.class)

        then:
        verifyAll(map) {
            containsKey("audits")
            containsKey("count")
            containsKey("next_page")
            containsKey("previous_page")
            ((List) get("audits")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            audits().size() == 1
            audits().first().id == 777L
            audits().first().ticketId == 888L
            count() == 1
            nextPage() == "https://example.zendesk.com/api/v2/tickets/888/audits.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/tickets/888/audits.json?page=1"
        }
    }

    def "should handle null components in TicketAuditsResponse"() {
        when:
        TicketAuditsResponse empty = new TicketAuditsResponse(null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketAuditsResponse deserialized = objectMapper.readValue(json, TicketAuditsResponse.class)

        then:
        verifyAll(empty) {
            audits() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            audits() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
    }
}
