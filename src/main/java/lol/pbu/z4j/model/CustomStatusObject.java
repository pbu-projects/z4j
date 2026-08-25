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
import lol.pbu.z4j.model.CustomStatusCreateInputAllOfStatusCategory;
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
 * CustomStatusObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    CustomStatusObject.JSON_PROPERTY_AGENT_LABEL,
    CustomStatusObject.JSON_PROPERTY_STATUS_CATEGORY,
    CustomStatusObject.JSON_PROPERTY_ACTIVE,
    CustomStatusObject.JSON_PROPERTY_CREATED_AT,
    CustomStatusObject.JSON_PROPERTY_DEFAULT,
    CustomStatusObject.JSON_PROPERTY_DESCRIPTION,
    CustomStatusObject.JSON_PROPERTY_END_USER_DESCRIPTION,
    CustomStatusObject.JSON_PROPERTY_END_USER_LABEL,
    CustomStatusObject.JSON_PROPERTY_ID,
    CustomStatusObject.JSON_PROPERTY_RAW_AGENT_LABEL,
    CustomStatusObject.JSON_PROPERTY_RAW_DESCRIPTION,
    CustomStatusObject.JSON_PROPERTY_RAW_END_USER_DESCRIPTION,
    CustomStatusObject.JSON_PROPERTY_RAW_END_USER_LABEL,
    CustomStatusObject.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
public class CustomStatusObject {

    public static final String JSON_PROPERTY_AGENT_LABEL = "agent_label";
    public static final String JSON_PROPERTY_STATUS_CATEGORY = "status_category";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_END_USER_DESCRIPTION = "end_user_description";
    public static final String JSON_PROPERTY_END_USER_LABEL = "end_user_label";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_RAW_AGENT_LABEL = "raw_agent_label";
    public static final String JSON_PROPERTY_RAW_DESCRIPTION = "raw_description";
    public static final String JSON_PROPERTY_RAW_END_USER_DESCRIPTION = "raw_end_user_description";
    public static final String JSON_PROPERTY_RAW_END_USER_LABEL = "raw_end_user_label";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    /**
     * <p>The label displayed to agents. Maximum length is 48 characters</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_AGENT_LABEL)
    private String agentLabel;

    @NotNull
    @JsonProperty(JSON_PROPERTY_STATUS_CATEGORY)
    private CustomStatusCreateInputAllOfStatusCategory statusCategory;

    /**
     * <p>If true, the custom status is set to active, If false, the custom status is set to inactive</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>The date and time the custom ticket status was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>If true, the custom status is set to default. If false, the custom status is set to non-default</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _default;

    /**
     * <p>The description of when the user should select this custom ticket status</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>The description displayed to end users</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String endUserDescription;

    /**
     * <p>The label displayed to end users. Maximum length is 48 characters</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_LABEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String endUserLabel;

    /**
     * <p>Automatically assigned when the custom ticket status is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The dynamic content placeholder. If the dynamic content placeholder is not available, this is the \"agent_label\" value. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_AGENT_LABEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawAgentLabel;

    /**
     * <p>The dynamic content placeholder. If the dynamic content placeholder is not available, this is the \"description\" value. <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawDescription;

    /**
     * <p>The dynamic content placeholder. If the dynamic content placeholder is not available, this is the \"end_user_description\" value. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_END_USER_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawEndUserDescription;

    /**
     * <p>The dynamic content placeholder. If the dynamic content placeholder is not available, this is the \"end_user_label\" value. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_END_USER_LABEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawEndUserLabel;

    /**
     * <p>The date and time the custom ticket status was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    public CustomStatusObject(String agentLabel, CustomStatusCreateInputAllOfStatusCategory statusCategory) {
        this.agentLabel = agentLabel;
        this.statusCategory = statusCategory;
    }

}