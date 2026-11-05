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
import java.time.ZonedDateTime;

/**
 * Custom ticket status details configured for an account.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketFieldCustomStatusObject.JSON_PROPERTY_ACTIVE,
        TicketFieldCustomStatusObject.JSON_PROPERTY_AGENT_LABEL,
        TicketFieldCustomStatusObject.JSON_PROPERTY_CREATED_AT,
        TicketFieldCustomStatusObject.JSON_PROPERTY_DEFAULT,
        TicketFieldCustomStatusObject.JSON_PROPERTY_DESCRIPTION,
        TicketFieldCustomStatusObject.JSON_PROPERTY_END_USER_DESCRIPTION,
        TicketFieldCustomStatusObject.JSON_PROPERTY_END_USER_LABEL,
        TicketFieldCustomStatusObject.JSON_PROPERTY_ID,
        TicketFieldCustomStatusObject.JSON_PROPERTY_STATUS_CATEGORY,
        TicketFieldCustomStatusObject.JSON_PROPERTY_UPDATED_AT
})
public record TicketFieldCustomStatusObject(
        @Nullable @JsonProperty(JSON_PROPERTY_ACTIVE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean active,
        @Nullable @JsonProperty(JSON_PROPERTY_AGENT_LABEL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String agentLabel,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_DEFAULT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean isDefault,
        @Nullable @JsonProperty(JSON_PROPERTY_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String description,
        @Nullable @JsonProperty(JSON_PROPERTY_END_USER_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String endUserDescription,
        @Nullable @JsonProperty(JSON_PROPERTY_END_USER_LABEL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String endUserLabel,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_STATUS_CATEGORY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketFieldCustomStatusObjectStatusCategory statusCategory,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt
) implements TicketFieldCustomStatusObjectBuilder.With {

    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_AGENT_LABEL = "agent_label";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_END_USER_DESCRIPTION = "end_user_description";
    public static final String JSON_PROPERTY_END_USER_LABEL = "end_user_label";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_STATUS_CATEGORY = "status_category";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    public TicketFieldCustomStatusObject() {
        this(null, null, null, null, null, null, null, null, null, null);
    }
}
