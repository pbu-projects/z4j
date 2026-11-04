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

class ViewRowSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewRow with constructors and builder"() {
        given:
        Ticket expectedTicket = new Ticket().setId(505L).setSubject("View ticket")
        Map<String, Object> props = [custom_column: "custom_val"]

        when:
        ViewRow direct = new ViewRow(expectedTicket, props)
        ViewRow convenience = new ViewRow(expectedTicket)
        ViewRow noArg = new ViewRow()
        ViewRow built = ViewRowBuilder.builder()
                .ticket(expectedTicket)
                .additionalProperties(props)
                .build()

        then:
        verifyAll(direct) {
            it.ticket() == expectedTicket
            it.additionalProperties() == props
        }
        verifyAll(convenience) {
            it.ticket() == expectedTicket
            it.additionalProperties() == null
        }
        verifyAll(noArg) {
            it.ticket() == null
            it.additionalProperties() == null
        }
        verifyAll(built) {
            it == direct
            it.ticket() == expectedTicket
            it.additionalProperties() == props
        }
    }

    def "should update ViewRow with record withers"() {
        given:
        Ticket initialTicket = new Ticket().setId(1L)
        Ticket updatedTicket = new Ticket().setId(2L)
        ViewRow initial = new ViewRow(initialTicket)

        when:
        ViewRow updated = initial
                .withTicket(updatedTicket)
                .withAdditionalProperties([custom_flag: true])

        then:
        verifyAll(updated) {
            it.ticket() == updatedTicket
            it.additionalProperties() == [custom_flag: true]
        }
    }

    def "should round-trip serialize and deserialize ViewRow"() {
        given:
        Ticket ticket = new Ticket().setId(777L).setSubject("Serialization Test")
        ViewRow row = new ViewRow(ticket, [priority_score: 95])

        when:
        String json = objectMapper.writeValueAsString(row)
        Map map = objectMapper.readValue(json, Map.class)
        ViewRow deserialized = objectMapper.readValue(json, ViewRow.class)

        then:
        verifyAll(map) {
            containsKey("ticket")
            containsKey("priority_score")
            get("priority_score") == 95
        }
        verifyAll(deserialized) {
            it == row
            it.ticket() != null
            it.ticket().id == 777L
            it.ticket().subject == "Serialization Test"
            it.additionalProperties() == [priority_score: 95]
        }
    }

    def "should handle null fields in serialization"() {
        when:
        ViewRow empty = new ViewRow()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        ViewRow deserialized = objectMapper.readValue(json, ViewRow.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            it.ticket() == null
            it.additionalProperties() == null
        }
    }
}
