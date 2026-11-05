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

class TicketFieldSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create TicketField with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.of(2026, 11, 4, 11, 0, 0, 0, ZoneOffset.UTC)
        CustomFieldOption option = new CustomFieldOption("Opt 1", "opt_1").withId(1L)
        TicketFieldCustomStatusObject status = new TicketFieldCustomStatusObject().withId(10L)
        SystemFieldOptionObject sysOpt = new SystemFieldOptionObject("High", "high")

        when:
        TicketField direct = new TicketField(
                "Priority Level",
                "dropdown",
                true,
                true,
                "Agent help text",
                false,
                now,
                "TicketApp",
                99L,
                [option],
                [status],
                "Select priority",
                true,
                101L,
                2,
                "raw_desc",
                "raw_title",
                "raw_portal_title",
                '^[A-Z]+$',
                [filter: "active"],
                "zen:user",
                true,
                false,
                false,
                1L,
                [sysOpt],
                "priority_tag",
                "Portal Title",
                now,
                "https://example.zendesk.com/fields/101",
                true
        )
        TicketField noArg = new TicketField()
        TicketField titleAndType = new TicketField("Department", "text")
        TicketField built = TicketFieldBuilder.builder()
                .title("Priority Level")
                .type("dropdown")
                .active(true)
                .agentCanEdit(true)
                .agentDescription("Agent help text")
                .collapsedForAgents(false)
                .createdAt(now)
                .creatorAppName("TicketApp")
                .creatorUserId(99L)
                .customFieldOptions([option])
                .customStatuses([status])
                .description("Select priority")
                .editableInPortal(true)
                .id(101L)
                .position(2)
                .rawDescription("raw_desc")
                .rawTitle("raw_title")
                .rawTitleInPortal("raw_portal_title")
                .regexpForValidation('^[A-Z]+$')
                .relationshipFilter([filter: "active"])
                .relationshipTargetType("zen:user")
                .removable(true)
                .required(false)
                .requiredInPortal(false)
                .subTypeId(1L)
                .systemFieldOptions([sysOpt])
                .tag("priority_tag")
                .titleInPortal("Portal Title")
                .updatedAt(now)
                .url("https://example.zendesk.com/fields/101")
                .visibleInPortal(true)
                .build()

        then:
        verifyAll(direct) {
            title() == "Priority Level"
            type() == "dropdown"
            active() == true
            agentCanEdit() == true
            agentDescription() == "Agent help text"
            collapsedForAgents() == false
            createdAt() == now
            creatorAppName() == "TicketApp"
            creatorUserId() == 99L
            customFieldOptions() == [option]
            customStatuses() == [status]
            description() == "Select priority"
            editableInPortal() == true
            id() == 101L
            position() == 2
            rawDescription() == "raw_desc"
            rawTitle() == "raw_title"
            rawTitleInPortal() == "raw_portal_title"
            regexpForValidation() == '^[A-Z]+$'
            relationshipFilter() == [filter: "active"]
            relationshipTargetType() == "zen:user"
            removable() == true
            required() == false
            requiredInPortal() == false
            subTypeId() == 1L
            systemFieldOptions() == [sysOpt]
            tag() == "priority_tag"
            titleInPortal() == "Portal Title"
            updatedAt() == now
            url() == "https://example.zendesk.com/fields/101"
            visibleInPortal() == true
        }
        verifyAll(noArg) {
            title() == null
            type() == null
            id() == null
            active() == null
        }
        verifyAll(titleAndType) {
            title() == "Department"
            type() == "text"
            id() == null
        }
        verifyAll(built) {
            it == direct
            title() == "Priority Level"
            id() == 101L
        }
    }

    def "should update TicketField using withers"() {
        given:
        TicketField initial = new TicketField()

        when:
        TicketField updated = initial
                .withTitle("Category")
                .withType("tagger")
                .withActive(true)
                .withAgentCanEdit(true)
                .withAgentDescription("Agent desc")
                .withCollapsedForAgents(true)
                .withCreatorAppName("App")
                .withCreatorUserId(44L)
                .withDescription("Category description")
                .withEditableInPortal(false)
                .withId(202L)
                .withPosition(5)
                .withRawDescription("raw_desc")
                .withRawTitle("raw_title")
                .withRawTitleInPortal("raw_portal")
                .withRegexpForValidation(".*")
                .withRelationshipFilter("filter")
                .withRelationshipTargetType("zen:ticket")
                .withRemovable(false)
                .withRequired(true)
                .withRequiredInPortal(true)
                .withSubTypeId(2L)
                .withTag("cat_tag")
                .withTitleInPortal("User Category")
                .withUrl("https://example.zendesk.com/fields/202")
                .withVisibleInPortal(false)

        then:
        verifyAll(updated) {
            title() == "Category"
            type() == "tagger"
            active() == true
            id() == 202L
            tag() == "cat_tag"
            required() == true
        }
    }

    def "should add items to list properties"() {
        given:
        TicketField field = new TicketField("Field", "text")
        CustomFieldOption option1 = new CustomFieldOption("Opt 1", "v1").withId(1L)
        CustomFieldOption option2 = new CustomFieldOption("Opt 2", "v2").withId(2L)
        TicketFieldCustomStatusObject status1 = new TicketFieldCustomStatusObject().withId(10L)
        TicketFieldCustomStatusObject status2 = new TicketFieldCustomStatusObject().withId(20L)
        SystemFieldOptionObject sysOpt1 = new SystemFieldOptionObject("Sys 1", "s1")
        SystemFieldOptionObject sysOpt2 = new SystemFieldOptionObject("Sys 2", "s2")

        when: "adding to null lists"
        TicketField withOpt = field.addCustomFieldOptionsItem(option1)
        TicketField withStatus = field.addCustomStatusesItem(status1)
        TicketField withSysOpt = field.addSystemFieldOptionsItem(sysOpt1)

        then:
        verifyAll(withOpt) {
            customFieldOptions() != null
            customFieldOptions().size() == 1
            customFieldOptions().first() == option1
        }
        verifyAll(withStatus) {
            customStatuses() != null
            customStatuses().size() == 1
            customStatuses().first() == status1
        }
        verifyAll(withSysOpt) {
            systemFieldOptions() != null
            systemFieldOptions().size() == 1
            systemFieldOptions().first() == sysOpt1
        }

        when: "adding to populated lists"
        TicketField withMoreOpts = withOpt.addCustomFieldOptionsItem(option2)
        TicketField withMoreStatuses = withStatus.addCustomStatusesItem(status2)
        TicketField withMoreSysOpts = withSysOpt.addSystemFieldOptionsItem(sysOpt2)

        then:
        verifyAll(withMoreOpts) {
            customFieldOptions().size() == 2
            customFieldOptions() == [option1, option2]
        }
        verifyAll(withMoreStatuses) {
            customStatuses().size() == 2
            customStatuses() == [status1, status2]
        }
        verifyAll(withMoreSysOpts) {
            systemFieldOptions().size() == 2
            systemFieldOptions() == [sysOpt1, sysOpt2]
        }
    }

    def "should round-trip serialize and deserialize TicketField"() {
        given:
        TicketField field = new TicketField()
                .withId(555L)
                .withTitle("Resolution")
                .withType("text")
                .withActive(true)
                .withRequired(false)

        when:
        String json = objectMapper.writeValueAsString(field)
        Map map = objectMapper.readValue(json, Map.class)
        TicketField deserialized = objectMapper.readValue(json, TicketField.class)

        then:
        verifyAll(map) {
            get("id") == 555
            get("title") == "Resolution"
            get("type") == "text"
            get("active") == true
            get("required") == false
        }
        verifyAll(deserialized) {
            it == field
            id() == 555L
            title() == "Resolution"
            type() == "text"
            active() == true
            required() == false
        }
    }

    def "should verify TicketField constants"() {
        expect:
        verifyAll {
            TicketField.JSON_PROPERTY_TITLE == "title"
            TicketField.JSON_PROPERTY_TYPE == "type"
            TicketField.JSON_PROPERTY_ACTIVE == "active"
            TicketField.JSON_PROPERTY_AGENT_CAN_EDIT == "agent_can_edit"
            TicketField.JSON_PROPERTY_AGENT_DESCRIPTION == "agent_description"
            TicketField.JSON_PROPERTY_COLLAPSED_FOR_AGENTS == "collapsed_for_agents"
            TicketField.JSON_PROPERTY_CREATED_AT == "created_at"
            TicketField.JSON_PROPERTY_CREATOR_APP_NAME == "creator_app_name"
            TicketField.JSON_PROPERTY_CREATOR_USER_ID == "creator_user_id"
            TicketField.JSON_PROPERTY_CUSTOM_FIELD_OPTIONS == "custom_field_options"
            TicketField.JSON_PROPERTY_CUSTOM_STATUSES == "custom_statuses"
            TicketField.JSON_PROPERTY_DESCRIPTION == "description"
            TicketField.JSON_PROPERTY_EDITABLE_IN_PORTAL == "editable_in_portal"
            TicketField.JSON_PROPERTY_ID == "id"
            TicketField.JSON_PROPERTY_POSITION == "position"
            TicketField.JSON_PROPERTY_RAW_DESCRIPTION == "raw_description"
            TicketField.JSON_PROPERTY_RAW_TITLE == "raw_title"
            TicketField.JSON_PROPERTY_RAW_TITLE_IN_PORTAL == "raw_title_in_portal"
            TicketField.JSON_PROPERTY_REGEXP_FOR_VALIDATION == "regexp_for_validation"
            TicketField.JSON_PROPERTY_RELATIONSHIP_FILTER == "relationship_filter"
            TicketField.JSON_PROPERTY_RELATIONSHIP_TARGET_TYPE == "relationship_target_type"
            TicketField.JSON_PROPERTY_REMOVABLE == "removable"
            TicketField.JSON_PROPERTY_REQUIRED == "required"
            TicketField.JSON_PROPERTY_REQUIRED_IN_PORTAL == "required_in_portal"
            TicketField.JSON_PROPERTY_SUB_TYPE_ID == "sub_type_id"
            TicketField.JSON_PROPERTY_SYSTEM_FIELD_OPTIONS == "system_field_options"
            TicketField.JSON_PROPERTY_TAG == "tag"
            TicketField.JSON_PROPERTY_TITLE_IN_PORTAL == "title_in_portal"
            TicketField.JSON_PROPERTY_UPDATED_AT == "updated_at"
            TicketField.JSON_PROPERTY_URL == "url"
            TicketField.JSON_PROPERTY_VISIBLE_IN_PORTAL == "visible_in_portal"
        }
    }
}
