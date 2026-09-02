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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * ManySkillBasedRoutingAttributeValuesResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ManySkillBasedRoutingAttributeValuesResponse.JSON_PROPERTY_COUNT,
    ManySkillBasedRoutingAttributeValuesResponse.JSON_PROPERTY_INSTANCE_VALUES,
    ManySkillBasedRoutingAttributeValuesResponse.JSON_PROPERTY_NEXT_PAGE,
    ManySkillBasedRoutingAttributeValuesResponse.JSON_PROPERTY_PREVIOUS_PAGE,
})
@Serdeable
public class ManySkillBasedRoutingAttributeValuesResponse {

    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_INSTANCE_VALUES = "instance_values";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";

    /**
     * <p>The number of instance values returned</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long count;

    @Nullable
    @JsonProperty(JSON_PROPERTY_INSTANCE_VALUES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ManySkillBasedRoutingAttributeValues> instanceValues;

    /**
     * <p>The URL for the next page of results</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    /**
     * <p>The URL for the previous page of results</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String previousPage;

    /**
     * Add an item to the instanceValues property in a chainable fashion.
     *
     * @return The same instance of ManySkillBasedRoutingAttributeValuesResponse for chaining.
     */
    public ManySkillBasedRoutingAttributeValuesResponse addInstanceValuesItem(ManySkillBasedRoutingAttributeValues instanceValuesItem) {
        if (instanceValues == null) {
            instanceValues = new ArrayList<>();
        }
        instanceValues.add(instanceValuesItem);
        return this;
    }

}