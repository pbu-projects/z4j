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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * TicketFieldCustomStatusObject
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
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
        TicketFieldCustomStatusObject.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
public class TicketFieldCustomStatusObject {

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

    /**
     * If true, if the custom status is set to active. If false, the custom status is set to inactive
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * The label displayed to agents
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_LABEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String agentLabel;

    /**
     * The date and time at which the custom ticket status was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * If true, the custom status is set to default. If false, the custom status is set to non-default
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isDefault;

    /**
     * The description of when the user should select this custom ticket status
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * The description displayed to end users
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String endUserDescription;

    /**
     * The label displayed to end users
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_LABEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String endUserLabel;

    /**
     * Automatically assigned when the custom ticket status is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS_CATEGORY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketFieldCustomStatusObjectStatusCategory statusCategory;

    /**
     * The date and time at which the custom ticket status was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

}
