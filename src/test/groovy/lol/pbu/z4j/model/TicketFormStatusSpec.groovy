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

class TicketFormStatusSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create TicketFormStatus with constructors and builder"() {
        when:
        TicketFormStatus direct = new TicketFormStatus("fs_101", 202L, 303L)
        TicketFormStatus noArg = new TicketFormStatus()
        TicketFormStatus built = TicketFormStatusBuilder.builder()
                .id("fs_101")
                .customStatusId(202L)
                .ticketFormId(303L)
                .build()

        then:
        verifyAll(direct) {
            id() == "fs_101"
            customStatusId() == 202L
            ticketFormId() == 303L
        }
        verifyAll(noArg) {
            id() == null
            customStatusId() == null
            ticketFormId() == null
        }
        verifyAll(built) {
            it == direct
            id() == "fs_101"
            customStatusId() == 202L
            ticketFormId() == 303L
        }
    }

    def "should update TicketFormStatus with record withers"() {
        given:
        TicketFormStatus initial = new TicketFormStatus()

        when:
        TicketFormStatus updated = initial
                .withId("fs_999")
                .withCustomStatusId(888L)
                .withTicketFormId(777L)

        then:
        verifyAll(updated) {
            id() == "fs_999"
            customStatusId() == 888L
            ticketFormId() == 777L
        }
    }

    def "should round-trip serialize and deserialize TicketFormStatus"() {
        given:
        TicketFormStatus formStatus = new TicketFormStatus("form_stat_1", 11L, 22L)

        when:
        String json = objectMapper.writeValueAsString(formStatus)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFormStatus deserialized = objectMapper.readValue(json, TicketFormStatus.class)

        then:
        verifyAll(map) {
            containsKey("id")
            containsKey("custom_status_id")
            containsKey("ticket_form_id")
            get("id") == "form_stat_1"
            get("custom_status_id") == 11
            get("ticket_form_id") == 22
        }
        verifyAll(deserialized) {
            it == formStatus
            id() == "form_stat_1"
            customStatusId() == 11L
            ticketFormId() == 22L
        }
    }

    def "should handle null fields in serialization"() {
        when:
        TicketFormStatus empty = new TicketFormStatus()
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFormStatus deserialized = objectMapper.readValue(json, TicketFormStatus.class)

        then:
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            id() == null
            customStatusId() == null
            ticketFormId() == null
        }
    }
}
