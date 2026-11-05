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

class CustomStatusesResponseSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create CustomStatusesResponse with canonical constructor, convenience constructor, and builder"() {
        given:
        TicketFieldCustomStatusObject status = new TicketFieldCustomStatusObject()
                .setId(101L)
                .setAgentLabel("In Review")
        List<TicketFieldCustomStatusObject> statuses = [status]

        when:
        CustomStatusesResponse direct = new CustomStatusesResponse(
                statuses,
                1,
                "https://example.zendesk.com/api/v2/custom_statuses.json?page=2",
                "https://example.zendesk.com/api/v2/custom_statuses.json?page=1"
        )
        CustomStatusesResponse convenience = new CustomStatusesResponse(statuses)
        CustomStatusesResponse built = CustomStatusesResponseBuilder.builder()
                .customStatuses(statuses)
                .count(1)
                .nextPage("https://example.zendesk.com/api/v2/custom_statuses.json?page=2")
                .previousPage("https://example.zendesk.com/api/v2/custom_statuses.json?page=1")
                .build()

        then:
        verifyAll(direct) {
            customStatuses() == statuses
            customStatuses().size() == 1
            customStatuses().first().id == 101L
            count() == 1
            nextPage() == "https://example.zendesk.com/api/v2/custom_statuses.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/custom_statuses.json?page=1"
        }
        verifyAll(convenience) {
            customStatuses() == statuses
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(built) {
            it == direct
            customStatuses() == statuses
            count() == 1
            nextPage() == "https://example.zendesk.com/api/v2/custom_statuses.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/custom_statuses.json?page=1"
        }
    }

    def "should update CustomStatusesResponse using record withers"() {
        given:
        TicketFieldCustomStatusObject first = new TicketFieldCustomStatusObject().setId(1L).setAgentLabel("First")
        TicketFieldCustomStatusObject second = new TicketFieldCustomStatusObject().setId(2L).setAgentLabel("Second")
        CustomStatusesResponse response = new CustomStatusesResponse([first], 1, "next1", "prev1")

        when:
        CustomStatusesResponse updated = response
                .withCustomStatuses([second])
                .withCount(2)
                .withNextPage("next2")
                .withPreviousPage("prev2")

        then:
        verifyAll(response) {
            customStatuses() == [first]
            count() == 1
            nextPage() == "next1"
            previousPage() == "prev1"
        }
        verifyAll(updated) {
            customStatuses() == [second]
            count() == 2
            nextPage() == "next2"
            previousPage() == "prev2"
        }
    }

    def "should round-trip serialize and deserialize CustomStatusesResponse"() {
        given:
        TicketFieldCustomStatusObject status = new TicketFieldCustomStatusObject()
                .setId(202L)
                .setAgentLabel("Awaiting Customer")
        CustomStatusesResponse response = new CustomStatusesResponse([status], 1, "https://example.com/next", "https://example.com/prev")

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomStatusesResponse deserialized = objectMapper.readValue(json, CustomStatusesResponse.class)

        then:
        verifyAll(map) {
            containsKey("custom_statuses")
            containsKey("count")
            containsKey("next_page")
            containsKey("previous_page")
            ((List) get("custom_statuses")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            customStatuses().size() == 1
            customStatuses().first().id == 202L
            customStatuses().first().agentLabel == "Awaiting Customer"
            count() == 1
            nextPage() == "https://example.com/next"
            previousPage() == "https://example.com/prev"
        }
    }

    def "should handle null components in CustomStatusesResponse"() {
        when:
        CustomStatusesResponse empty = new CustomStatusesResponse(null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        CustomStatusesResponse deserialized = objectMapper.readValue(json, CustomStatusesResponse.class)

        then:
        verifyAll(empty) {
            customStatuses() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            customStatuses() == null
            count() == null
            nextPage() == null
            previousPage() == null
        }
    }
}
