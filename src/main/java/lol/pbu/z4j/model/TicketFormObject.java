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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TicketFormObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TicketFormObject.JSON_PROPERTY_NAME,
    TicketFormObject.JSON_PROPERTY_ACTIVE,
    TicketFormObject.JSON_PROPERTY_AGENT_CONDITIONS,
    TicketFormObject.JSON_PROPERTY_CREATED_AT,
    TicketFormObject.JSON_PROPERTY_DEFAULT,
    TicketFormObject.JSON_PROPERTY_DELETED_AT,
    TicketFormObject.JSON_PROPERTY_DISPLAY_NAME,
    TicketFormObject.JSON_PROPERTY_END_USER_CONDITIONS,
    TicketFormObject.JSON_PROPERTY_END_USER_VISIBLE,
    TicketFormObject.JSON_PROPERTY_ID,
    TicketFormObject.JSON_PROPERTY_IN_ALL_BRANDS,
    TicketFormObject.JSON_PROPERTY_POSITION,
    TicketFormObject.JSON_PROPERTY_RAW_DISPLAY_NAME,
    TicketFormObject.JSON_PROPERTY_RAW_NAME,
    TicketFormObject.JSON_PROPERTY_RESTRICTED_BRAND_IDS,
    TicketFormObject.JSON_PROPERTY_TICKET_FIELD_IDS,
    TicketFormObject.JSON_PROPERTY_UPDATED_AT,
    TicketFormObject.JSON_PROPERTY_URL,
})
@Serdeable
public class TicketFormObject {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_AGENT_CONDITIONS = "agent_conditions";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_DELETED_AT = "deleted_at";
    public static final String JSON_PROPERTY_DISPLAY_NAME = "display_name";
    public static final String JSON_PROPERTY_END_USER_CONDITIONS = "end_user_conditions";
    public static final String JSON_PROPERTY_END_USER_VISIBLE = "end_user_visible";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IN_ALL_BRANDS = "in_all_brands";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_RAW_DISPLAY_NAME = "raw_display_name";
    public static final String JSON_PROPERTY_RAW_NAME = "raw_name";
    public static final String JSON_PROPERTY_RESTRICTED_BRAND_IDS = "restricted_brand_ids";
    public static final String JSON_PROPERTY_TICKET_FIELD_IDS = "ticket_field_ids";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The name of the form</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>If the form is set as active</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>Array of condition sets for agent workspaces</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_CONDITIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Map<String, Object>> agentConditions;

    /**
     * <p>The time the ticket form was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Is the form the default form for this account</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _default;

    /**
     * <p>The time the ticket form was deleted</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DELETED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime deletedAt;

    /**
     * <p>The name of the form that is displayed to an end user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DISPLAY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String displayName;

    /**
     * <p>Array of condition sets for end user products</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_CONDITIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Map<String, Object>> endUserConditions;

    /**
     * <p>Is the form visible to the end user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_VISIBLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean endUserVisible;

    /**
     * <p>Automatically assigned when creating ticket form</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>Is the form available for use in all brands on this account</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IN_ALL_BRANDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean inAllBrands;

    /**
     * <p>The position of this form among other forms in the account, i.e. dropdown</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    /**
     * <p>The dynamic content placeholder, if present, or the \"display_name\" value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_DISPLAY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawDisplayName;

    /**
     * <p>The dynamic content placeholder, if present, or the \"name\" value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawName;

    /**
     * <p>IDs of all brands that this ticket form is restricted to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RESTRICTED_BRAND_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> restrictedBrandIds;

    /**
     * <p>IDs of all ticket fields which are in this ticket form. The products use the order of the IDs to show the field values in the tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FIELD_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> ticketFieldIds;

    /**
     * <p>The time of the last update of the ticket form</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>URL of the ticket form</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public TicketFormObject(String name) {
        this.name = name;
    }

    /**
     * Add an item to the agentConditions property in a chainable fashion.
     *
     * @return The same instance of TicketFormObject for chaining.
     */
    public TicketFormObject addAgentConditionsItem(Map<String, Object> agentConditionsItem) {
        if (agentConditions == null) {
            agentConditions = new ArrayList<>();
        }
        agentConditions.add(agentConditionsItem);
        return this;
    }

    /**
     * Add an item to the endUserConditions property in a chainable fashion.
     *
     * @return The same instance of TicketFormObject for chaining.
     */
    public TicketFormObject addEndUserConditionsItem(Map<String, Object> endUserConditionsItem) {
        if (endUserConditions == null) {
            endUserConditions = new ArrayList<>();
        }
        endUserConditions.add(endUserConditionsItem);
        return this;
    }

    /**
     * Add an item to the ticketFieldIds property in a chainable fashion.
     *
     * @return The same instance of TicketFormObject for chaining.
     */
    public TicketFormObject addTicketFieldIdsItem(Long ticketFieldIdsItem) {
        if (ticketFieldIds == null) {
            ticketFieldIds = new ArrayList<>();
        }
        ticketFieldIds.add(ticketFieldIdsItem);
        return this;
    }

}