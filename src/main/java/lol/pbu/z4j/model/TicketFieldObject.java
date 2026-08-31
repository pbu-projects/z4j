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

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.CustomFieldOptionObject;
import lol.pbu.z4j.model.SystemFieldOptionObject;
import lol.pbu.z4j.model.TicketFieldCustomStatusObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import io.micronaut.core.annotation.Nullable;

/**
 * TicketFieldObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TicketFieldObject.JSON_PROPERTY_TITLE,
    TicketFieldObject.JSON_PROPERTY_TYPE,
    TicketFieldObject.JSON_PROPERTY_ACTIVE,
    TicketFieldObject.JSON_PROPERTY_AGENT_CAN_EDIT,
    TicketFieldObject.JSON_PROPERTY_AGENT_DESCRIPTION,
    TicketFieldObject.JSON_PROPERTY_COLLAPSED_FOR_AGENTS,
    TicketFieldObject.JSON_PROPERTY_CREATED_AT,
    TicketFieldObject.JSON_PROPERTY_CREATOR_APP_NAME,
    TicketFieldObject.JSON_PROPERTY_CREATOR_USER_ID,
    TicketFieldObject.JSON_PROPERTY_CUSTOM_FIELD_OPTIONS,
    TicketFieldObject.JSON_PROPERTY_CUSTOM_STATUSES,
    TicketFieldObject.JSON_PROPERTY_DESCRIPTION,
    TicketFieldObject.JSON_PROPERTY_EDITABLE_IN_PORTAL,
    TicketFieldObject.JSON_PROPERTY_ID,
    TicketFieldObject.JSON_PROPERTY_POSITION,
    TicketFieldObject.JSON_PROPERTY_RAW_DESCRIPTION,
    TicketFieldObject.JSON_PROPERTY_RAW_TITLE,
    TicketFieldObject.JSON_PROPERTY_RAW_TITLE_IN_PORTAL,
    TicketFieldObject.JSON_PROPERTY_REGEXP_FOR_VALIDATION,
    TicketFieldObject.JSON_PROPERTY_RELATIONSHIP_FILTER,
    TicketFieldObject.JSON_PROPERTY_RELATIONSHIP_TARGET_TYPE,
    TicketFieldObject.JSON_PROPERTY_REMOVABLE,
    TicketFieldObject.JSON_PROPERTY_REQUIRED,
    TicketFieldObject.JSON_PROPERTY_REQUIRED_IN_PORTAL,
    TicketFieldObject.JSON_PROPERTY_SUB_TYPE_ID,
    TicketFieldObject.JSON_PROPERTY_SYSTEM_FIELD_OPTIONS,
    TicketFieldObject.JSON_PROPERTY_TAG,
    TicketFieldObject.JSON_PROPERTY_TITLE_IN_PORTAL,
    TicketFieldObject.JSON_PROPERTY_UPDATED_AT,
    TicketFieldObject.JSON_PROPERTY_URL,
    TicketFieldObject.JSON_PROPERTY_VISIBLE_IN_PORTAL,
})
@Serdeable
public class TicketFieldObject {

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
     * <p>The title of the ticket field</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * <p>System or custom field type. Editable for custom field types and only on creation. See <a href=\"#create-ticket-field\">Create Ticket Field</a></p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TYPE)
    private String type;

    /**
     * <p>Whether this field is available</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>Whether this field is editable by agents</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_CAN_EDIT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentCanEdit;

    /**
     * <p>A description of the ticket field that only agents can see</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String agentDescription;

    /**
     * <p>If true, the field is shown to agents by default. If false, the field is hidden alongside infrequently used fields. Classic interface only</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COLLAPSED_FOR_AGENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean collapsedForAgents;

    /**
     * <p>The time the custom ticket field was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Name of the app that created the ticket field, or a null value if no app created the ticket field</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATOR_APP_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String creatorAppName;

    /**
     * <p>The id of the user that created the ticket field, or a value of \"-1\" if an app created the ticket field</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATOR_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long creatorUserId;

    /**
     * <p>Required and presented for a custom ticket field of type \"multiselect\" or \"tagger\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELD_OPTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CustomFieldOptionObject> customFieldOptions;

    /**
     * <p>List of customized ticket statuses. Only presented for a system ticket field of type \"custom_status\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUSES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TicketFieldCustomStatusObject> customStatuses;

    /**
     * <p>Describes the purpose of the ticket field to users</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Whether this field is editable by end users in Help Center</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EDITABLE_IN_PORTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean editableInPortal;

    /**
     * <p>Automatically assigned when created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>The relative position of the ticket field on a ticket. Note that for accounts with ticket forms, positions are controlled by the different forms</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    /**
     * <p>The dynamic content placeholder if present, or the <code>description</code> value if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawDescription;

    /**
     * <p>The dynamic content placeholder if present, or the <code>title</code> value if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitle;

    /**
     * <p>The dynamic content placeholder if present, or the \"title_in_portal\" value if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_TITLE_IN_PORTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitleInPortal;

    /**
     * <p>For \"regexp\" fields only. The validation pattern for a field value to be deemed valid</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REGEXP_FOR_VALIDATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String regexpForValidation;

    /**
     * <p>A filter definition that allows your autocomplete to filter down results</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RELATIONSHIP_FILTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object relationshipFilter;

    /**
     * <p>A representation of what type of object the field references. Options are \"zen:user\", \"zen:organization\", \"zen:ticket\", or \"zen:custom_object:{key}\" where key is a custom object key. For example \"zen:custom_object:apartment\".</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RELATIONSHIP_TARGET_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String relationshipTargetType;

    /**
     * <p>If false, this field is a system field that must be present on all tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REMOVABLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean removable;

    /**
     * <p>If true, agents must enter a value in the field to change the ticket status to solved</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUIRED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean required;

    /**
     * <p>If true, end users must enter a value in the field to create the request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUIRED_IN_PORTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean requiredInPortal;

    /**
     * <p>For system ticket fields of type \"priority\" and \"status\". Defaults to 0. A \"priority\" sub type of 1 removes the \"Low\" and \"Urgent\" options. A \"status\" sub type of 1 adds the \"On-Hold\" option</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUB_TYPE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long subTypeId;

    /**
     * <p>Presented for a system ticket field of type \"tickettype\", \"priority\" or \"status\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SYSTEM_FIELD_OPTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid SystemFieldOptionObject> systemFieldOptions;

    /**
     * <p>For \"checkbox\" fields only. A tag added to tickets when the checkbox field is selected</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAG)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String tag;

    /**
     * <p>The title of the ticket field for end users in Help Center</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE_IN_PORTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String titleInPortal;

    /**
     * <p>The time the custom ticket field was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The URL for this resource</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * <p>Whether this field is visible to end users in Help Center</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VISIBLE_IN_PORTAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean visibleInPortal;

    public TicketFieldObject(String title, String type) {
        this.title = title;
        this.type = type;
    }

    /**
     * Add an item to the customFieldOptions property in a chainable fashion.
     *
     * @return The same instance of TicketFieldObject for chaining.
     */
    public TicketFieldObject addCustomFieldOptionsItem(CustomFieldOptionObject customFieldOptionsItem) {
        if (customFieldOptions == null) {
            customFieldOptions = new ArrayList<>();
        }
        customFieldOptions.add(customFieldOptionsItem);
        return this;
    }

}