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

import java.time.OffsetDateTime
import java.time.ZoneOffset

class ViewSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create View with constructors and builder"() {
        given:
        OffsetDateTime now = OffsetDateTime.of(2026, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)

        when:
        View direct = new View(
                101L,
                "Open Tickets",
                true,
                now,
                now,
                false,
                1L,
                "All open tickets in the group",
                [group: "agents"],
                [all: []],
                [type: "Group"],
                "Open Tickets Raw"
        )
        View noArg = new View()
        View built = ViewBuilder.builder()
                .id(101L)
                .title("Open Tickets")
                .active(true)
                .updatedAt(now)
                .createdAt(now)
                .isDefault(false)
                .position(1L)
                .description("All open tickets in the group")
                .execution([group: "agents"])
                .conditions([all: []])
                .restriction([type: "Group"])
                .rawTitle("Open Tickets Raw")
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
            title() == "Open Tickets"
            active() == true
            updatedAt() == now
            createdAt() == now
            isDefault() == false
            position() == 1L
            description() == "All open tickets in the group"
            execution() == [group: "agents"]
            conditions() == [all: []]
            restriction() == [type: "Group"]
            rawTitle() == "Open Tickets Raw"
        }
        verifyAll(noArg) {
            id() == null
            title() == null
            active() == null
            updatedAt() == null
            createdAt() == null
            isDefault() == null
            position() == null
            description() == null
            execution() == null
            conditions() == null
            restriction() == null
            rawTitle() == null
        }
        verifyAll(built) {
            it == direct
            id() == 101L
            title() == "Open Tickets"
        }
    }

    def "should update View using withers"() {
        given:
        OffsetDateTime now = OffsetDateTime.of(2026, 10, 1, 8, 0, 0, 0, ZoneOffset.UTC)
        View initial = new View()

        when:
        View updated = initial
                .withId(202L)
                .withTitle("Pending Tickets")
                .withActive(true)
                .withUpdatedAt(now)
                .withCreatedAt(now)
                .withIsDefault(true)
                .withPosition(2L)
                .withDescription("Pending customer reply")
                .withExecution("exec")
                .withConditions("cond")
                .withRestriction("rest")
                .withRawTitle("Raw Pending")
        View withConvenienceDefault = updated.withDefault(false)

        then:
        verifyAll(updated) {
            id() == 202L
            title() == "Pending Tickets"
            active() == true
            updatedAt() == now
            createdAt() == now
            isDefault() == true
            position() == 2L
            description() == "Pending customer reply"
            execution() == "exec"
            conditions() == "cond"
            restriction() == "rest"
            rawTitle() == "Raw Pending"
        }
        verifyAll(withConvenienceDefault) {
            isDefault() == false
        }
    }

    def "should round-trip serialize and deserialize View"() {
        given:
        View view = new View()
                .withId(303L)
                .withTitle("Unassigned")
                .withActive(true)
                .withIsDefault(false)

        when:
        String json = objectMapper.writeValueAsString(view)
        Map map = objectMapper.readValue(json, Map.class)
        View deserialized = objectMapper.readValue(json, View.class)

        then:
        verifyAll(map) {
            get("id") == 303
            get("title") == "Unassigned"
            get("active") == true
            get("default") == false
        }
        verifyAll(deserialized) {
            it == view
            id() == 303L
            title() == "Unassigned"
            active() == true
            isDefault() == false
        }
    }

    def "should verify View constants"() {
        expect:
        verifyAll {
            View.JSON_PROPERTY_ID == "id"
            View.JSON_PROPERTY_TITLE == "title"
            View.JSON_PROPERTY_ACTIVE == "active"
            View.JSON_PROPERTY_UPDATED_AT == "updated_at"
            View.JSON_PROPERTY_CREATED_AT == "created_at"
            View.JSON_PROPERTY_DEFAULT == "default"
            View.JSON_PROPERTY_POSITION == "position"
            View.JSON_PROPERTY_DESCRIPTION == "description"
            View.JSON_PROPERTY_EXECUTION == "execution"
            View.JSON_PROPERTY_CONDITIONS == "conditions"
            View.JSON_PROPERTY_RESTRICTION == "restriction"
            View.JSON_PROPERTY_RAW_TITLE == "raw_title"
        }
    }
}
