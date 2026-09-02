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
 * IncrementalSkillBasedRoutingInstanceValue
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    IncrementalSkillBasedRoutingInstanceValue.JSON_PROPERTY_ATTRIBUTE_VALUE_ID,
    IncrementalSkillBasedRoutingInstanceValue.JSON_PROPERTY_ID,
    IncrementalSkillBasedRoutingInstanceValue.JSON_PROPERTY_INSTANCE_ID,
    IncrementalSkillBasedRoutingInstanceValue.JSON_PROPERTY_TIME,
    IncrementalSkillBasedRoutingInstanceValue.JSON_PROPERTY_TYPE,
})
@Serdeable
public class IncrementalSkillBasedRoutingInstanceValue {

    public static final String JSON_PROPERTY_ATTRIBUTE_VALUE_ID = "attribute_value_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_INSTANCE_ID = "instance_id";
    public static final String JSON_PROPERTY_TIME = "time";
    public static final String JSON_PROPERTY_TYPE = "type";

    /**
     * <p>Id of the associated attribute value</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE_VALUE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String attributeValueId;

    /**
     * <p>Automatically assigned when an instance value is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>Id of the associated agent or ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_INSTANCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String instanceId;

    /**
     * <p>The time the instance value was created or deleted</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TIME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime time;

    /**
     * <p>One of \"associate_agent\", \"unassociate_agent\", \"associate_ticket\", or \"unassociate_ticket\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

}