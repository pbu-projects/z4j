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

class TicketFormsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketFormsResponse with canonical constructor and builder"() {
        given:
        TicketForm form = new TicketForm("Support Form").withId(501L)
        List<TicketForm> list = [form]

        when:
        TicketFormsResponse direct = new TicketFormsResponse(list)
        TicketFormsResponse built = TicketFormsResponseBuilder.builder()
                .ticketForms(list)
                .build()

        then:
        verifyAll(direct) {
            ticketForms() == list
            ticketForms().size() == 1
            ticketForms().first().id == 501L
            ticketForms().first().name == "Support Form"
        }
        verifyAll(built) {
            it == direct
            ticketForms() == list
        }
    }

    def "should update TicketFormsResponse using record withers and addTicketFormsItem method"() {
        given:
        TicketForm form1 = new TicketForm("Form 1").withId(1L)
        TicketForm form2 = new TicketForm("Form 2").withId(2L)
        TicketFormsResponse response = new TicketFormsResponse([form1])

        when:
        TicketFormsResponse updated = response.withTicketForms([form2])
        TicketFormsResponse withAdded = response.addTicketFormsItem(form2)
        TicketFormsResponse fromNull = new TicketFormsResponse(null).addTicketFormsItem(form1)

        then:
        verifyAll(response) {
            ticketForms().size() == 1
            ticketForms().first().id == 1L
        }
        verifyAll(updated) {
            ticketForms() == [form2]
        }
        verifyAll(withAdded) {
            ticketForms().size() == 2
            ticketForms().containsAll([form1, form2])
        }
        verifyAll(fromNull) {
            ticketForms().size() == 1
            ticketForms().first() == form1
        }
    }

    def "should round-trip serialize and deserialize TicketFormsResponse"() {
        given:
        TicketForm form = new TicketForm("Billing Form").withId(701L)
        TicketFormsResponse response = new TicketFormsResponse([form])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFormsResponse deserialized = objectMapper.readValue(json, TicketFormsResponse.class)

        then:
        verifyAll(map) {
            containsKey("ticket_forms")
            ((List) get("ticket_forms")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            ticketForms().size() == 1
            ticketForms().first().id == 701L
            ticketForms().first().name == "Billing Form"
        }
    }

    def "should handle null components in TicketFormsResponse"() {
        when:
        TicketFormsResponse empty = new TicketFormsResponse(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketFormsResponse deserialized = objectMapper.readValue(json, TicketFormsResponse.class)

        then:
        verifyAll(empty) {
            ticketForms() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            ticketForms() == null
        }
    }
}
