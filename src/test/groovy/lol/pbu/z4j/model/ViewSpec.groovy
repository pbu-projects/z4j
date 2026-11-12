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
        ViewExecution exec = new ViewExecution().withGroupBy("status")
        ViewCondition cond = new ViewCondition("status", "is", "open")
        ViewConditions conds = new ViewConditions([cond], [])
        ViewRestriction rest = new ViewRestriction("Group", 10L)
        ViewOutput expectedOutput = new ViewOutput(["status", "requester"])

        when:
        View direct12 = new View(
                101L,
                "Open Tickets",
                true,
                now,
                now,
                false,
                1L,
                "All open tickets in the group",
                exec,
                conds,
                rest,
                "Open Tickets Raw"
        )
        View direct15 = new View(
                101L,
                "Open Tickets",
                true,
                now,
                now,
                false,
                1L,
                "All open tickets in the group",
                exec,
                conds,
                rest,
                "Open Tickets Raw",
                expectedOutput,
                [cond],
                []
        )
        View titleOnly = new View("Simple View")
        View titleAndConds = new View("Filtered View", conds)
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
                .execution(exec)
                .conditions(conds)
                .restriction(rest)
                .rawTitle("Open Tickets Raw")
                .build()

        then:
        verifyAll(direct12) {
            it.id() == 101L
            it.title() == "Open Tickets"
            it.active() == true
            it.updatedAt() == now
            it.createdAt() == now
            it.isDefault() == false
            it.position() == 1L
            it.description() == "All open tickets in the group"
            it.execution() == exec
            it.conditions() == conds
            it.restriction() == rest
            it.rawTitle() == "Open Tickets Raw"
            it.output() == null
            it.all() == null
            it.any() == null
        }
        verifyAll(direct15) {
            it.id() == 101L
            it.output() == expectedOutput
            it.all() == [cond]
            it.any() == []
        }
        verifyAll(titleOnly) {
            it.title() == "Simple View"
            it.conditions() == null
        }
        verifyAll(titleAndConds) {
            it.title() == "Filtered View"
            it.conditions() == conds
        }
        verifyAll(noArg) {
            it.id() == null
            it.title() == null
            it.active() == null
            it.updatedAt() == null
            it.createdAt() == null
            it.isDefault() == null
            it.position() == null
            it.description() == null
            it.execution() == null
            it.conditions() == null
            it.restriction() == null
            it.rawTitle() == null
            it.output() == null
            it.all() == null
            it.any() == null
        }
        verifyAll(built) {
            it == direct12
            it.id() == 101L
            it.title() == "Open Tickets"
        }
    }

    def "should update View using withers and list helpers"() {
        given:
        OffsetDateTime now = OffsetDateTime.of(2026, 10, 1, 8, 0, 0, 0, ZoneOffset.UTC)
        ViewExecution exec = new ViewExecution().withSortBy("updated")
        ViewCondition c1 = new ViewCondition("status", "is", "pending")
        ViewCondition c2 = new ViewCondition("priority", "is", "high")
        ViewConditions conds = new ViewConditions([c1])
        ViewRestriction rest = new ViewRestriction("User", 55L)
        ViewOutput out = new ViewOutput(["status"])
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
                .withExecution(exec)
                .withConditions(conds)
                .withRestriction(rest)
                .withRawTitle("Raw Pending")
                .withOutput(out)
                .addAllItem(c1)
                .addAnyItem(c2)
        View withMoreItems = updated.addAllItem(c2).addAnyItem(c1)
        View withConvenienceDefault = withMoreItems.withDefault(false)

        then:
        verifyAll(updated) {
            it.id() == 202L
            it.title() == "Pending Tickets"
            it.active() == true
            it.updatedAt() == now
            it.createdAt() == now
            it.isDefault() == true
            it.position() == 2L
            it.description() == "Pending customer reply"
            it.execution() == exec
            it.conditions() == conds
            it.restriction() == rest
            it.rawTitle() == "Raw Pending"
            it.output() == out
            it.all() == [c1]
            it.any() == [c2]
        }
        verifyAll(withMoreItems) {
            it.all() == [c1, c2]
            it.any() == [c2, c1]
        }
        verifyAll(withConvenienceDefault) {
            it.isDefault() == false
        }
    }

    def "should round-trip serialize and deserialize View"() {
        given:
        View expectedView = new View()
                .withId(303L)
                .withTitle("Unassigned")
                .withActive(true)
                .withIsDefault(false)
                .withConditions(new ViewConditions([new ViewCondition("status", "less_than", "solved")]))
                .withExecution(new ViewExecution().addColumnsItem(new ViewColumn("status", "Status")))
                .withRestriction(new ViewRestriction("User", 99L))

        when:
        String json = objectMapper.writeValueAsString(expectedView)
        Map map = objectMapper.readValue(json, Map.class)
        View deserialized = objectMapper.readValue(json, View.class)

        then:
        verifyAll(map) {
            get("id") == 303
            get("title") == "Unassigned"
            get("active") == true
            get("default") == false
            containsKey("conditions")
            containsKey("execution")
            containsKey("restriction")
        }
        verifyAll(deserialized) {
            it == expectedView
            it.id() == 303L
            it.title() == "Unassigned"
            it.active() == true
            it.isDefault() == false
            it.conditions().all().first().field() == "status"
            it.execution().columns().first().id() == "status"
            it.restriction().id() == 99L
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
            View.JSON_PROPERTY_OUTPUT == "output"
            View.JSON_PROPERTY_ALL == "all"
            View.JSON_PROPERTY_ANY == "any"
        }
    }
}
