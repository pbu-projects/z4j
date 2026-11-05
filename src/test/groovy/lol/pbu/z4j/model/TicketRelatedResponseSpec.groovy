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

class TicketRelatedResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketRelatedResponse with canonical constructor and builder"() {
        given:
        TicketRelated expected = new TicketRelated()
                .withUrl("https://example.zendesk.com/api/v2/tickets/101/related.json")
                .withTopicId(42L)

        when:
        TicketRelatedResponse direct = new TicketRelatedResponse(expected)
        TicketRelatedResponse built = TicketRelatedResponseBuilder.builder().ticketRelated(expected).build()

        then:
        verifyAll(direct) {
            ticketRelated == expected
            ticketRelated.url == "https://example.zendesk.com/api/v2/tickets/101/related.json"
            ticketRelated.topicId == 42L
        }
        verifyAll(built) {
            ticketRelated == expected
            ticketRelated.url == "https://example.zendesk.com/api/v2/tickets/101/related.json"
            ticketRelated.topicId == 42L
        }
    }

    def "should update ticketRelated using record wither"() {
        given:
        TicketRelated original = new TicketRelated().withUrl("https://example.zendesk.com/1").withTopicId(1L)
        TicketRelated replacement = new TicketRelated().withUrl("https://example.zendesk.com/2").withTopicId(2L)
        TicketRelatedResponse response = new TicketRelatedResponse(original)

        when:
        TicketRelatedResponse updated = response.withTicketRelated(replacement)

        then:
        verifyAll(response) {
            ticketRelated == original
            ticketRelated.topicId == 1L
        }
        verifyAll(updated) {
            ticketRelated == replacement
            ticketRelated.topicId == 2L
        }
    }

    def "should round-trip serialize and deserialize TicketRelatedResponse"() {
        given:
        TicketRelated expected = new TicketRelated()
                .withUrl("https://example.zendesk.com/api/v2/tickets/555/related.json")
                .withTopicId(999L)
        TicketRelatedResponse response = new TicketRelatedResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketRelatedResponse deserialized = objectMapper.readValue(json, TicketRelatedResponse.class)

        then:
        verifyAll(map) {
            containsKey("ticket_related")
            get("ticket_related") instanceof Map
            ((Map) get("ticket_related")).topic_id == 999
        }
        verifyAll(deserialized) {
            it == response
            ticketRelated != null
            ticketRelated.url == "https://example.zendesk.com/api/v2/tickets/555/related.json"
            ticketRelated.topicId == 999L
        }
    }

    def "should handle null ticketRelated component in constructor and wither"() {
        when:
        TicketRelatedResponse response = new TicketRelatedResponse(null)
        TicketRelated replacement = new TicketRelated().withTopicId(77L)
        TicketRelatedResponse updated = response.withTicketRelated(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketRelatedResponse deserialized = objectMapper.readValue(json, TicketRelatedResponse.class)

        then:
        verifyAll(response) {
            ticketRelated() == null
        }
        verifyAll(updated) {
            ticketRelated() == replacement
            ticketRelated().topicId == 77L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("ticket_related")
        }
        verifyAll(deserialized) {
            it == response
            ticketRelated() == null
        }
    }
}
