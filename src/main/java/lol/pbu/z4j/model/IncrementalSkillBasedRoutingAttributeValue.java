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
 * IncrementalSkillBasedRoutingAttributeValue
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    IncrementalSkillBasedRoutingAttributeValue.JSON_PROPERTY_ATTRIBUTE_ID,
    IncrementalSkillBasedRoutingAttributeValue.JSON_PROPERTY_ID,
    IncrementalSkillBasedRoutingAttributeValue.JSON_PROPERTY_NAME,
    IncrementalSkillBasedRoutingAttributeValue.JSON_PROPERTY_TIME,
    IncrementalSkillBasedRoutingAttributeValue.JSON_PROPERTY_TYPE,
})
@Serdeable
public class IncrementalSkillBasedRoutingAttributeValue {

    public static final String JSON_PROPERTY_ATTRIBUTE_ID = "attribute_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_TIME = "time";
    public static final String JSON_PROPERTY_TYPE = "type";

    /**
     * <p>Id of the associated attribute</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String attributeId;

    /**
     * <p>Automatically assigned when an attribute value is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>The name of the attribute value</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    /**
     * <p>The time the attribute value was created, updated, or deleted</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TIME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime time;

    /**
     * <p>One of \"create\", \"update\", or \"delete\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

}