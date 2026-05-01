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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TicketFieldObject
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
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
        TicketField.JSON_PROPERTY_VISIBLE_IN_PORTAL,
})
@Serdeable
public class TicketField {

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

    /**
     * The title of the ticket field
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * System or custom field type. Editable for custom field types and only on creation. See <a href=\"#create-ticket-field\">Create Ticket Field</a>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TYPE)
    private String type;

    /**
     * Whether this field is available
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * Whether this field is editable by agents
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_CAN_EDIT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentCanEdit;

    /**
     * A description of the ticket field that only agents can see
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String agentDescription;

    /**
     * If true, the field is shown to agents by default. If false, the field is hidden alongside infrequently used fields. Classic interface only
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLAPSED_FOR_AGENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean collapsedForAgents;

    /**
     * The time the custom ticket field was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * Name of the app that created the ticket field, or a null value if no app created the ticket field
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATOR_APP_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String creatorAppName;

    /**
     * The id of the user that created the ticket field, or a value of \"-1\" if an app created the ticket field
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATOR_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long creatorUserId;

    /**
     * Required and presented for a custom ticket field of type \"multiselect\" or \"tagger\"
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELD_OPTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CustomFieldOption> customFieldOptions;

    /**
     * List of customized ticket statuses. Only presented for a system ticket field of type \"custom_status\"
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUSES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TicketFieldCustomStatusObject> customStatuses;

    /**
     * Describes the purpose of the ticket field to users
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * Whether this field is editable by end users in Help Center
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EDITABLE_IN_PORTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean editableInPortal;

    /**
     * Automatically assigned when created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * The relative position of the ticket field on a ticket. Note that for accounts with ticket forms, positions are controlled by the different forms
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer position;

    /**
     * The dynamic content placeholder if present, or the <code>description</code> value if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawDescription;

    /**
     * The dynamic content placeholder if present, or the <code>title</code> value if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitle;

    /**
     * The dynamic content placeholder if present, or the \"title_in_portal\" value if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_TITLE_IN_PORTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitleInPortal;

    /**
     * For \"regexp\" fields only. The validation pattern for a field value to be deemed valid
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REGEXP_FOR_VALIDATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String regexpForValidation;

    /**
     * A filter definition that allows your autocomplete to filter down results
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RELATIONSHIP_FILTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object relationshipFilter;

    /**
     * A representation of what type of object the field references. Options are \"zen:user\", \"zen:organization\", \"zen:ticket\", or \"zen:custom_object:{key}\" where key is a custom object key. For example \"zen:custom_object:apartment\".
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RELATIONSHIP_TARGET_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String relationshipTargetType;

    /**
     * If false, this field is a system field that must be present on all tickets
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REMOVABLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean removable;

    /**
     * If true, agents must enter a value in the field to change the ticket status to solved
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUIRED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean required;

    /**
     * If true, end users must enter a value in the field to create the request
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUIRED_IN_PORTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean requiredInPortal;

    /**
     * For system ticket fields of type \"priority\" and \"status\". Defaults to 0. A \"priority\" sub type of 1 removes the \"Low\" and \"Urgent\" options. A \"status\" sub type of 1 adds the \"On-Hold\" option
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUB_TYPE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer subTypeId;

    /**
     * Presented for a system ticket field of type \"tickettype\", \"priority\" or \"status\"
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SYSTEM_FIELD_OPTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid SystemFieldOptionObject> systemFieldOptions;

    /**
     * For \"checkbox\" fields only. A tag added to tickets when the checkbox field is selected
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAG)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String tag;

    /**
     * The title of the ticket field for end users in Help Center
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE_IN_PORTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String titleInPortal;

    /**
     * The time the custom ticket field was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * The URL for this resource
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * Whether this field is visible to end users in Help Center
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VISIBLE_IN_PORTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean visibleInPortal;

    public TicketField(String title, String type) {
        this.title = title;
        this.type = type;
    }

    /**
     * Add an item to the customFieldOptions property in a chainable fashion.
     *
     * @return The same instance of TicketFieldObject for chaining.
     */
    public TicketField addCustomFieldOptionsItem(CustomFieldOption customFieldOptionsItem) {
        if (customFieldOptions == null) {
            customFieldOptions = new ArrayList<>();
        }
        customFieldOptions.add(customFieldOptionsItem);
        return this;
    }

}
