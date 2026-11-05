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

import java.time.ZoneOffset
import java.time.ZonedDateTime

class TicketFormSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create TicketForm with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.of(2026, 11, 4, 10, 0, 0, 0, ZoneOffset.UTC)

        when:
        TicketForm direct = new TicketForm(
                101L,
                "Support Form",
                "support_form_raw",
                "Customer Support",
                "customer_support_raw",
                1L,
                true,
                true,
                true,
                true,
                [10L, 20L],
                [100L, 200L],
                [[agent: "cond"]],
                [[user: "cond"]],
                "https://example.zendesk.com/api/v2/ticket_forms/101.json",
                now,
                now
        )
        TicketForm noArg = new TicketForm()
        TicketForm nameOnly = new TicketForm("Basic Form")
        TicketForm built = TicketFormBuilder.builder()
                .id(101L)
                .name("Support Form")
                .rawName("support_form_raw")
                .displayName("Customer Support")
                .rawDisplayName("customer_support_raw")
                .position(1L)
                .active(true)
                .defaultForm(true)
                .endUserVisible(true)
                .inAllBrands(true)
                .restrictedBrandIds([10L, 20L])
                .ticketFieldIds([100L, 200L])
                .agentConditions([[agent: "cond"]])
                .endUserConditions([[user: "cond"]])
                .url("https://example.zendesk.com/api/v2/ticket_forms/101.json")
                .createdAt(now)
                .updatedAt(now)
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
            name() == "Support Form"
            rawName() == "support_form_raw"
            displayName() == "Customer Support"
            rawDisplayName() == "customer_support_raw"
            position() == 1L
            active() == true
            defaultForm() == true
            endUserVisible() == true
            inAllBrands() == true
            restrictedBrandIds() == [10L, 20L]
            ticketFieldIds() == [100L, 200L]
            agentConditions() == [[agent: "cond"]]
            endUserConditions() == [[user: "cond"]]
            url() == "https://example.zendesk.com/api/v2/ticket_forms/101.json"
            createdAt() == now
            updatedAt() == now
        }
        verifyAll(noArg) {
            id() == null
            name() == null
            rawName() == null
            displayName() == null
            rawDisplayName() == null
            position() == null
            active() == null
            defaultForm() == null
            endUserVisible() == null
            inAllBrands() == null
            restrictedBrandIds() == null
            ticketFieldIds() == null
            agentConditions() == null
            endUserConditions() == null
            url() == null
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(nameOnly) {
            name() == "Basic Form"
            id() == null
        }
        verifyAll(built) {
            it == direct
            id() == 101L
            name() == "Support Form"
        }
    }

    def "should update TicketForm using withers"() {
        given:
        ZonedDateTime now = ZonedDateTime.of(2026, 10, 1, 9, 0, 0, 0, ZoneOffset.UTC)
        TicketForm initial = new TicketForm()

        when:
        TicketForm updated = initial
                .withId(202L)
                .withName("Updated Form")
                .withRawName("raw_updated")
                .withDisplayName("Updated Display")
                .withRawDisplayName("raw_display")
                .withPosition(3L)
                .withActive(false)
                .withDefaultForm(false)
                .withEndUserVisible(false)
                .withInAllBrands(false)
                .withRestrictedBrandIds([30L])
                .withTicketFieldIds([300L])
                .withAgentConditions([])
                .withEndUserConditions([])
                .withUrl("https://example.zendesk.com/forms/202")
                .withCreatedAt(now)
                .withUpdatedAt(now)

        then:
        verifyAll(updated) {
            id() == 202L
            name() == "Updated Form"
            rawName() == "raw_updated"
            displayName() == "Updated Display"
            rawDisplayName() == "raw_display"
            position() == 3L
            active() == false
            defaultForm() == false
            endUserVisible() == false
            inAllBrands() == false
            restrictedBrandIds() == [30L]
            ticketFieldIds() == [300L]
            agentConditions() == []
            endUserConditions() == []
            url() == "https://example.zendesk.com/forms/202"
            createdAt() == now
            updatedAt() == now
        }
    }

    def "should add ticket field IDs to null and populated lists"() {
        given:
        TicketForm form = new TicketForm("Field Form")

        when: "adding to null list"
        TicketForm withFirst = form.addTicketFieldIdsItem(400L)

        then:
        verifyAll(withFirst) {
            ticketFieldIds() != null
            ticketFieldIds().size() == 1
            ticketFieldIds().first() == 400L
        }

        when: "adding to populated list"
        TicketForm withSecond = withFirst.addTicketFieldIdsItem(500L)

        then:
        verifyAll(withSecond) {
            ticketFieldIds().size() == 2
            ticketFieldIds() == [400L, 500L]
        }
    }

    def "should round-trip serialize and deserialize TicketForm"() {
        given:
        TicketForm form = new TicketForm()
                .withId(707L)
                .withName("Feedback Form")
                .withActive(true)
                .withDefaultForm(true)
                .withTicketFieldIds([11L, 22L])

        when:
        String json = objectMapper.writeValueAsString(form)
        Map map = objectMapper.readValue(json, Map.class)
        TicketForm deserialized = objectMapper.readValue(json, TicketForm.class)

        then:
        verifyAll(map) {
            get("id") == 707
            get("name") == "Feedback Form"
            get("active") == true
            get("default") == true
            get("ticket_field_ids") == [11, 22]
        }
        verifyAll(deserialized) {
            it == form
            id() == 707L
            name() == "Feedback Form"
            active() == true
            defaultForm() == true
            ticketFieldIds() == [11L, 22L]
        }
    }

    def "should verify TicketForm constants"() {
        expect:
        verifyAll {
            TicketForm.JSON_PROPERTY_ID == "id"
            TicketForm.JSON_PROPERTY_NAME == "name"
            TicketForm.JSON_PROPERTY_RAW_NAME == "raw_name"
            TicketForm.JSON_PROPERTY_DISPLAY_NAME == "display_name"
            TicketForm.JSON_PROPERTY_RAW_DISPLAY_NAME == "raw_display_name"
            TicketForm.JSON_PROPERTY_POSITION == "position"
            TicketForm.JSON_PROPERTY_ACTIVE == "active"
            TicketForm.JSON_PROPERTY_DEFAULT == "default"
            TicketForm.JSON_PROPERTY_END_USER_VISIBLE == "end_user_visible"
            TicketForm.JSON_PROPERTY_IN_ALL_BRANDS == "in_all_brands"
            TicketForm.JSON_PROPERTY_RESTRICTED_BRAND_IDS == "restricted_brand_ids"
            TicketForm.JSON_PROPERTY_TICKET_FIELD_IDS == "ticket_field_ids"
            TicketForm.JSON_PROPERTY_AGENT_CONDITIONS == "agent_conditions"
            TicketForm.JSON_PROPERTY_END_USER_CONDITIONS == "end_user_conditions"
            TicketForm.JSON_PROPERTY_URL == "url"
            TicketForm.JSON_PROPERTY_CREATED_AT == "created_at"
            TicketForm.JSON_PROPERTY_UPDATED_AT == "updated_at"
        }
    }
}
