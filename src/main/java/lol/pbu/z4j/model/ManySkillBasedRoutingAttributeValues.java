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
import lol.pbu.z4j.model.ManySkillBasedRoutingAttributeValuesAgentSkillPriority;
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
 * ManySkillBasedRoutingAttributeValues
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ManySkillBasedRoutingAttributeValues.JSON_PROPERTY_AGENT_ID,
    ManySkillBasedRoutingAttributeValues.JSON_PROPERTY_AGENT_SKILL_PRIORITY,
    ManySkillBasedRoutingAttributeValues.JSON_PROPERTY_ATTRIBUTE_ID,
    ManySkillBasedRoutingAttributeValues.JSON_PROPERTY_ATTRIBUTE_VALUE_ID,
    ManySkillBasedRoutingAttributeValues.JSON_PROPERTY_CREATED_AT,
    ManySkillBasedRoutingAttributeValues.JSON_PROPERTY_ID,
    ManySkillBasedRoutingAttributeValues.JSON_PROPERTY_NAME,
    ManySkillBasedRoutingAttributeValues.JSON_PROPERTY_UPDATED_AT,
    ManySkillBasedRoutingAttributeValues.JSON_PROPERTY_URL,
})
@Serdeable
public class ManySkillBasedRoutingAttributeValues {

    public static final String JSON_PROPERTY_AGENT_ID = "agent_id";
    public static final String JSON_PROPERTY_AGENT_SKILL_PRIORITY = "agent_skill_priority";
    public static final String JSON_PROPERTY_ATTRIBUTE_ID = "attribute_id";
    public static final String JSON_PROPERTY_ATTRIBUTE_VALUE_ID = "attribute_value_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>Id of the associated agent</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer agentId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_SKILL_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ManySkillBasedRoutingAttributeValuesAgentSkillPriority agentSkillPriority;

    /**
     * <p>Id of the associated attribute</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String attributeId;

    /**
     * <p>Id of the associated attribute value</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE_VALUE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String attributeValueId;

    /**
     * <p>The time of creation of the instance value</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Automatically assigned when an instance value is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>Name of the associated attribute value</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    /**
     * <p>The time of the last update of the instance value</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The URL of the associated attribute value</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}