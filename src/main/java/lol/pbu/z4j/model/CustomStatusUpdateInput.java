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
 * CustomStatusUpdateInput
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    CustomStatusUpdateInput.JSON_PROPERTY_ACTIVE,
    CustomStatusUpdateInput.JSON_PROPERTY_AGENT_LABEL,
    CustomStatusUpdateInput.JSON_PROPERTY_DESCRIPTION,
    CustomStatusUpdateInput.JSON_PROPERTY_END_USER_DESCRIPTION,
    CustomStatusUpdateInput.JSON_PROPERTY_END_USER_LABEL,
})
@Serdeable
public class CustomStatusUpdateInput {

    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_AGENT_LABEL = "agent_label";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_END_USER_DESCRIPTION = "end_user_description";
    public static final String JSON_PROPERTY_END_USER_LABEL = "end_user_label";

    /**
     * <p>True if the custom status is set as active; inactive if false</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>The dynamic content placeholder, if present, or the \"agent_label\" value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_LABEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String agentLabel;

    /**
     * <p>The dynamic content placeholder, if present, or the \"description\" value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>The dynamic content placeholder, if present, or the \"end_user_description\" value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String endUserDescription;

    /**
     * <p>The dynamic content placeholder, if present, or the \"end_user_label\" value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_LABEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String endUserLabel;

}