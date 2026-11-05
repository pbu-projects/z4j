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
package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom or system ticket field configuration in Zendesk Support.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketField.JSON_PROPERTY_TITLE,
        TicketField.JSON_PROPERTY_TYPE,
        TicketField.JSON_PROPERTY_ACTIVE,
        TicketField.JSON_PROPERTY_AGENT_CAN_EDIT,
        TicketField.JSON_PROPERTY_AGENT_DESCRIPTION,
        TicketField.JSON_PROPERTY_COLLAPSED_FOR_AGENTS,
        TicketField.JSON_PROPERTY_CREATED_AT,
        TicketField.JSON_PROPERTY_CREATOR_APP_NAME,
        TicketField.JSON_PROPERTY_CREATOR_USER_ID,
        TicketField.JSON_PROPERTY_CUSTOM_FIELD_OPTIONS,
        TicketField.JSON_PROPERTY_CUSTOM_STATUSES,
        TicketField.JSON_PROPERTY_DESCRIPTION,
        TicketField.JSON_PROPERTY_EDITABLE_IN_PORTAL,
        TicketField.JSON_PROPERTY_ID,
        TicketField.JSON_PROPERTY_POSITION,
        TicketField.JSON_PROPERTY_RAW_DESCRIPTION,
        TicketField.JSON_PROPERTY_RAW_TITLE,
        TicketField.JSON_PROPERTY_RAW_TITLE_IN_PORTAL,
        TicketField.JSON_PROPERTY_REGEXP_FOR_VALIDATION,
        TicketField.JSON_PROPERTY_RELATIONSHIP_FILTER,
        TicketField.JSON_PROPERTY_RELATIONSHIP_TARGET_TYPE,
        TicketField.JSON_PROPERTY_REMOVABLE,
        TicketField.JSON_PROPERTY_REQUIRED,
        TicketField.JSON_PROPERTY_REQUIRED_IN_PORTAL,
        TicketField.JSON_PROPERTY_SUB_TYPE_ID,
        TicketField.JSON_PROPERTY_SYSTEM_FIELD_OPTIONS,
        TicketField.JSON_PROPERTY_TAG,
        TicketField.JSON_PROPERTY_TITLE_IN_PORTAL,
        TicketField.JSON_PROPERTY_UPDATED_AT,
        TicketField.JSON_PROPERTY_URL,
        TicketField.JSON_PROPERTY_VISIBLE_IN_PORTAL
})
public record TicketField(
        @NotNull @JsonProperty(JSON_PROPERTY_TITLE) String title,
        @NotNull @JsonProperty(JSON_PROPERTY_TYPE) String type,
        @Nullable @JsonProperty(JSON_PROPERTY_ACTIVE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean active,
        @Nullable @JsonProperty(JSON_PROPERTY_AGENT_CAN_EDIT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean agentCanEdit,
        @Nullable @JsonProperty(JSON_PROPERTY_AGENT_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String agentDescription,
        @Nullable @JsonProperty(JSON_PROPERTY_COLLAPSED_FOR_AGENTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean collapsedForAgents,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATOR_APP_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String creatorAppName,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATOR_USER_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long creatorUserId,
        @Nullable @JsonProperty(JSON_PROPERTY_CUSTOM_FIELD_OPTIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid CustomFieldOption> customFieldOptions,
        @Nullable @JsonProperty(JSON_PROPERTY_CUSTOM_STATUSES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid TicketFieldCustomStatusObject> customStatuses,
        @Nullable @JsonProperty(JSON_PROPERTY_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String description,
        @Nullable @JsonProperty(JSON_PROPERTY_EDITABLE_IN_PORTAL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean editableInPortal,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_POSITION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer position,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawDescription,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_TITLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawTitle,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_TITLE_IN_PORTAL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawTitleInPortal,
        @Nullable @JsonProperty(JSON_PROPERTY_REGEXP_FOR_VALIDATION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String regexpForValidation,
        @Nullable @JsonProperty(JSON_PROPERTY_RELATIONSHIP_FILTER) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Object relationshipFilter,
        @Nullable @JsonProperty(JSON_PROPERTY_RELATIONSHIP_TARGET_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String relationshipTargetType,
        @Nullable @JsonProperty(JSON_PROPERTY_REMOVABLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean removable,
        @Nullable @JsonProperty(JSON_PROPERTY_REQUIRED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean required,
        @Nullable @JsonProperty(JSON_PROPERTY_REQUIRED_IN_PORTAL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean requiredInPortal,
        @Nullable @JsonProperty(JSON_PROPERTY_SUB_TYPE_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long subTypeId,
        @Nullable @JsonProperty(JSON_PROPERTY_SYSTEM_FIELD_OPTIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid SystemFieldOptionObject> systemFieldOptions,
        @Nullable @JsonProperty(JSON_PROPERTY_TAG) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String tag,
        @Nullable @JsonProperty(JSON_PROPERTY_TITLE_IN_PORTAL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String titleInPortal,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_VISIBLE_IN_PORTAL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean visibleInPortal
) implements TicketFieldBuilder.With {

    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_AGENT_CAN_EDIT = "agent_can_edit";
    public static final String JSON_PROPERTY_AGENT_DESCRIPTION = "agent_description";
    public static final String JSON_PROPERTY_COLLAPSED_FOR_AGENTS = "collapsed_for_agents";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CREATOR_APP_NAME = "creator_app_name";
    public static final String JSON_PROPERTY_CREATOR_USER_ID = "creator_user_id";
    public static final String JSON_PROPERTY_CUSTOM_FIELD_OPTIONS = "custom_field_options";
    public static final String JSON_PROPERTY_CUSTOM_STATUSES = "custom_statuses";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_EDITABLE_IN_PORTAL = "editable_in_portal";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_RAW_DESCRIPTION = "raw_description";
    public static final String JSON_PROPERTY_RAW_TITLE = "raw_title";
    public static final String JSON_PROPERTY_RAW_TITLE_IN_PORTAL = "raw_title_in_portal";
    public static final String JSON_PROPERTY_REGEXP_FOR_VALIDATION = "regexp_for_validation";
    public static final String JSON_PROPERTY_RELATIONSHIP_FILTER = "relationship_filter";
    public static final String JSON_PROPERTY_RELATIONSHIP_TARGET_TYPE = "relationship_target_type";
    public static final String JSON_PROPERTY_REMOVABLE = "removable";
    public static final String JSON_PROPERTY_REQUIRED = "required";
    public static final String JSON_PROPERTY_REQUIRED_IN_PORTAL = "required_in_portal";
    public static final String JSON_PROPERTY_SUB_TYPE_ID = "sub_type_id";
    public static final String JSON_PROPERTY_SYSTEM_FIELD_OPTIONS = "system_field_options";
    public static final String JSON_PROPERTY_TAG = "tag";
    public static final String JSON_PROPERTY_TITLE_IN_PORTAL = "title_in_portal";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VISIBLE_IN_PORTAL = "visible_in_portal";

    public TicketField() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public TicketField(String title, String type) {
        this(title, type, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public TicketField addCustomFieldOptionsItem(CustomFieldOption customFieldOptionsItem) {
        List<CustomFieldOption> newItems = customFieldOptions == null ? new ArrayList<>() : new ArrayList<>(customFieldOptions);
        newItems.add(customFieldOptionsItem);
        return withCustomFieldOptions(newItems);
    }

    public TicketField addCustomStatusesItem(TicketFieldCustomStatusObject customStatusesItem) {
        List<TicketFieldCustomStatusObject> newItems = customStatuses == null ? new ArrayList<>() : new ArrayList<>(customStatuses);
        newItems.add(customStatusesItem);
        return withCustomStatuses(newItems);
    }

    public TicketField addSystemFieldOptionsItem(SystemFieldOptionObject systemFieldOptionsItem) {
        List<SystemFieldOptionObject> newItems = systemFieldOptions == null ? new ArrayList<>() : new ArrayList<>(systemFieldOptions);
        newItems.add(systemFieldOptionsItem);
        return withSystemFieldOptions(newItems);
    }
}
