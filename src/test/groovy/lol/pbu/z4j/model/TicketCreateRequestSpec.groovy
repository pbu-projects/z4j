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

class TicketCreateRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketCreateRequest with canonical constructor and builder"() {
        given:
        TicketCreateInput expected = new TicketCreateInput(new TicketComment("Help needed")).withSubject("Support Request")

        when:
        TicketCreateRequest direct = new TicketCreateRequest(expected)
        TicketCreateRequest built = TicketCreateRequestBuilder.builder()
                .ticket(expected)
                .build()

        then:
        verifyAll(direct) {
            ticket() == expected
            ticket().subject == "Support Request"
            ticket().comment.body == "Help needed"
        }
        verifyAll(built) {
            it == direct
            ticket() == expected
            ticket().subject == "Support Request"
            ticket().comment.body == "Help needed"
        }
    }

    def "should update ticket using record wither"() {
        given:
        TicketCreateInput original = new TicketCreateInput(new TicketComment("Original body")).withSubject("Original")
        TicketCreateInput replacement = new TicketCreateInput(new TicketComment("Updated body")).withSubject("Updated")
        TicketCreateRequest request = new TicketCreateRequest(original)

        when:
        TicketCreateRequest updated = request.withTicket(replacement)

        then:
        verifyAll(request) {
            ticket() == original
            ticket().subject == "Original"
        }
        verifyAll(updated) {
            ticket() == replacement
            ticket().subject == "Updated"
        }
    }

    def "should round-trip serialize and deserialize TicketCreateRequest"() {
        given:
        TicketCreateInput expected = new TicketCreateInput(new TicketComment("Printer is offline")).withSubject("Printer Issue")
        TicketCreateRequest request = new TicketCreateRequest(expected)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TicketCreateRequest deserialized = objectMapper.readValue(json, TicketCreateRequest.class)

        then:
        verifyAll(map) {
            containsKey("ticket")
            get("ticket") instanceof Map
            ((Map) get("ticket")).subject == "Printer Issue"
        }
        verifyAll(deserialized) {
            it == request
            ticket() != null
            ticket().subject == "Printer Issue"
            ticket().comment.body == "Printer is offline"
        }
    }

    def "should handle null ticket component in constructor, wither, and serde"() {
        given:
        TicketCreateInput replacement = new TicketCreateInput(new TicketComment("New body")).withSubject("New Subject")

        when:
        TicketCreateRequest request = new TicketCreateRequest(null)
        TicketCreateRequest updated = request.withTicket(replacement)
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        TicketCreateRequest deserialized = objectMapper.readValue(json, TicketCreateRequest.class)

        then:
        verifyAll(request) {
            ticket() == null
        }
        verifyAll(updated) {
            ticket() == replacement
            ticket().subject == "New Subject"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("ticket")
        }
        verifyAll(deserialized) {
            it == request
            ticket() == null
        }
    }
}
