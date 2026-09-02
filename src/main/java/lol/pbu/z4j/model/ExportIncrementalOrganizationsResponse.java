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
 * ExportIncrementalOrganizationsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ExportIncrementalOrganizationsResponse.JSON_PROPERTY_COUNT,
    ExportIncrementalOrganizationsResponse.JSON_PROPERTY_END_OF_STREAM,
    ExportIncrementalOrganizationsResponse.JSON_PROPERTY_END_TIME,
    ExportIncrementalOrganizationsResponse.JSON_PROPERTY_NEXT_PAGE,
    ExportIncrementalOrganizationsResponse.JSON_PROPERTY_ORGANIZATIONS,
})
@Serdeable
public class ExportIncrementalOrganizationsResponse {

    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_END_OF_STREAM = "end_of_stream";
    public static final String JSON_PROPERTY_END_TIME = "end_time";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_ORGANIZATIONS = "organizations";

    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long count;

    @Nullable
    @JsonProperty(JSON_PROPERTY_END_OF_STREAM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean endOfStream;

    @Nullable
    @JsonProperty(JSON_PROPERTY_END_TIME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long endTime;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid OrganizationObject> organizations;

    /**
     * Add an item to the organizations property in a chainable fashion.
     *
     * @return The same instance of ExportIncrementalOrganizationsResponse for chaining.
     */
    public ExportIncrementalOrganizationsResponse addOrganizationsItem(OrganizationObject organizationsItem) {
        if (organizations == null) {
            organizations = new ArrayList<>();
        }
        organizations.add(organizationsItem);
        return this;
    }

}