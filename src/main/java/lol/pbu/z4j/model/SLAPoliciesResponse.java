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
 * SLAPoliciesResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SLAPoliciesResponse.JSON_PROPERTY_COUNT,
    SLAPoliciesResponse.JSON_PROPERTY_NEXT_PAGE,
    SLAPoliciesResponse.JSON_PROPERTY_PREVIOUS_PAGE,
    SLAPoliciesResponse.JSON_PROPERTY_SLA_POLICIES,
})
@Serdeable
public class SLAPoliciesResponse {

    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";
    public static final String JSON_PROPERTY_SLA_POLICIES = "sla_policies";

    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long count;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String previousPage;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SLA_POLICIES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid SLAPolicyObject> slaPolicies;

    /**
     * Add an item to the slaPolicies property in a chainable fashion.
     *
     * @return The same instance of SLAPoliciesResponse for chaining.
     */
    public SLAPoliciesResponse addSlaPoliciesItem(SLAPolicyObject slaPoliciesItem) {
        if (slaPolicies == null) {
            slaPolicies = new ArrayList<>();
        }
        slaPolicies.add(slaPoliciesItem);
        return this;
    }

}