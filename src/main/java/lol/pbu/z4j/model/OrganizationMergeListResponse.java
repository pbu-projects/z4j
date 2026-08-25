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
import lol.pbu.z4j.model.OrganizationMergeListResponseOrganizationMergesInner;
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
 * OrganizationMergeListResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(OrganizationMergeListResponse.JSON_PROPERTY_ORGANIZATION_MERGES)
@Serdeable
public class OrganizationMergeListResponse {

    public static final String JSON_PROPERTY_ORGANIZATION_MERGES = "organization_merges";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_MERGES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid OrganizationMergeListResponseOrganizationMergesInner> organizationMerges;

    /**
     * Add an item to the organizationMerges property in a chainable fashion.
     *
     * @return The same instance of OrganizationMergeListResponse for chaining.
     */
    public OrganizationMergeListResponse addOrganizationMergesItem(OrganizationMergeListResponseOrganizationMergesInner organizationMergesItem) {
        if (organizationMerges == null) {
            organizationMerges = new ArrayList<>();
        }
        organizationMerges.add(organizationMergesItem);
        return this;
    }

}