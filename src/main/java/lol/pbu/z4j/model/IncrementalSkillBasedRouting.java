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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.IncrementalSkillBasedRoutingAttribute;
import lol.pbu.z4j.model.IncrementalSkillBasedRoutingAttributeValue;
import lol.pbu.z4j.model.IncrementalSkillBasedRoutingInstanceValue;
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
 * IncrementalSkillBasedRouting
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    IncrementalSkillBasedRouting.JSON_PROPERTY_ATTRIBUTE_VALUES,
    IncrementalSkillBasedRouting.JSON_PROPERTY_ATTRIBUTES,
    IncrementalSkillBasedRouting.JSON_PROPERTY_COUNT,
    IncrementalSkillBasedRouting.JSON_PROPERTY_END_TIME,
    IncrementalSkillBasedRouting.JSON_PROPERTY_INSTANCE_VALUES,
    IncrementalSkillBasedRouting.JSON_PROPERTY_NEXT_PAGE,
})
@Serdeable
public class IncrementalSkillBasedRouting {

    public static final String JSON_PROPERTY_ATTRIBUTE_VALUES = "attribute_values";
    public static final String JSON_PROPERTY_ATTRIBUTES = "attributes";
    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_END_TIME = "end_time";
    public static final String JSON_PROPERTY_INSTANCE_VALUES = "instance_values";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";

    /**
     * <p>Routing attribute values</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTE_VALUES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid IncrementalSkillBasedRoutingAttributeValue> attributeValues;

    /**
     * <p>Routing attributes</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTRIBUTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid IncrementalSkillBasedRoutingAttribute> attributes;

    /**
     * <p>The number of results returned for the current request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long count;

    /**
     * <p>The most recent resource creation time present in this result set in Unix epoch time</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_TIME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long endTime;

    /**
     * <p>Routing instance values</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_INSTANCE_VALUES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid IncrementalSkillBasedRoutingInstanceValue> instanceValues;

    /**
     * <p>The URL that should be called to get the next set of results</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    /**
     * Add an item to the attributeValues property in a chainable fashion.
     *
     * @return The same instance of IncrementalSkillBasedRouting for chaining.
     */
    public IncrementalSkillBasedRouting addAttributeValuesItem(IncrementalSkillBasedRoutingAttributeValue attributeValuesItem) {
        if (attributeValues == null) {
            attributeValues = new ArrayList<>();
        }
        attributeValues.add(attributeValuesItem);
        return this;
    }

    /**
     * Add an item to the attributes property in a chainable fashion.
     *
     * @return The same instance of IncrementalSkillBasedRouting for chaining.
     */
    public IncrementalSkillBasedRouting addAttributesItem(IncrementalSkillBasedRoutingAttribute attributesItem) {
        if (attributes == null) {
            attributes = new ArrayList<>();
        }
        attributes.add(attributesItem);
        return this;
    }

    /**
     * Add an item to the instanceValues property in a chainable fashion.
     *
     * @return The same instance of IncrementalSkillBasedRouting for chaining.
     */
    public IncrementalSkillBasedRouting addInstanceValuesItem(IncrementalSkillBasedRoutingInstanceValue instanceValuesItem) {
        if (instanceValues == null) {
            instanceValues = new ArrayList<>();
        }
        instanceValues.add(instanceValuesItem);
        return this;
    }

}