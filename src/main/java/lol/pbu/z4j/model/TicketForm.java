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
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a Zendesk Ticket Form.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.2
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketForm.JSON_PROPERTY_ID,
        TicketForm.JSON_PROPERTY_NAME,
        TicketForm.JSON_PROPERTY_RAW_NAME,
        TicketForm.JSON_PROPERTY_DISPLAY_NAME,
        TicketForm.JSON_PROPERTY_RAW_DISPLAY_NAME,
        TicketForm.JSON_PROPERTY_POSITION,
        TicketForm.JSON_PROPERTY_ACTIVE,
        TicketForm.JSON_PROPERTY_DEFAULT,
        TicketForm.JSON_PROPERTY_END_USER_VISIBLE,
        TicketForm.JSON_PROPERTY_IN_ALL_BRANDS,
        TicketForm.JSON_PROPERTY_RESTRICTED_BRAND_IDS,
        TicketForm.JSON_PROPERTY_TICKET_FIELD_IDS,
        TicketForm.JSON_PROPERTY_AGENT_CONDITIONS,
        TicketForm.JSON_PROPERTY_END_USER_CONDITIONS,
        TicketForm.JSON_PROPERTY_URL,
        TicketForm.JSON_PROPERTY_CREATED_AT,
        TicketForm.JSON_PROPERTY_UPDATED_AT
})
public record TicketForm(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @NotNull @JsonProperty(JSON_PROPERTY_NAME) String name,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawName,
        @Nullable @JsonProperty(JSON_PROPERTY_DISPLAY_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String displayName,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_DISPLAY_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawDisplayName,
        @Nullable @JsonProperty(JSON_PROPERTY_POSITION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long position,
        @Nullable @JsonProperty(JSON_PROPERTY_ACTIVE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean active,
        @Nullable @JsonProperty(JSON_PROPERTY_DEFAULT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean defaultForm,
        @Nullable @JsonProperty(JSON_PROPERTY_END_USER_VISIBLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean endUserVisible,
        @Nullable @JsonProperty(JSON_PROPERTY_IN_ALL_BRANDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean inAllBrands,
        @Nullable @JsonProperty(JSON_PROPERTY_RESTRICTED_BRAND_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Long> restrictedBrandIds,
        @Nullable @JsonProperty(JSON_PROPERTY_TICKET_FIELD_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Long> ticketFieldIds,
        @Nullable @JsonProperty(JSON_PROPERTY_AGENT_CONDITIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Map<String, Object>> agentConditions,
        @Nullable @JsonProperty(JSON_PROPERTY_END_USER_CONDITIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Map<String, Object>> endUserConditions,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt
) implements TicketFormBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_RAW_NAME = "raw_name";
    public static final String JSON_PROPERTY_DISPLAY_NAME = "display_name";
    public static final String JSON_PROPERTY_RAW_DISPLAY_NAME = "raw_display_name";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_END_USER_VISIBLE = "end_user_visible";
    public static final String JSON_PROPERTY_IN_ALL_BRANDS = "in_all_brands";
    public static final String JSON_PROPERTY_RESTRICTED_BRAND_IDS = "restricted_brand_ids";
    public static final String JSON_PROPERTY_TICKET_FIELD_IDS = "ticket_field_ids";
    public static final String JSON_PROPERTY_AGENT_CONDITIONS = "agent_conditions";
    public static final String JSON_PROPERTY_END_USER_CONDITIONS = "end_user_conditions";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    public TicketForm() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public TicketForm(String name) {
        this(null, name, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public TicketForm addTicketFieldIdsItem(Long ticketFieldIdsItem) {
        List<Long> newItems = ticketFieldIds == null ? new ArrayList<>() : new ArrayList<>(ticketFieldIds);
        newItems.add(ticketFieldIdsItem);
        return withTicketFieldIds(newItems);
    }
}
