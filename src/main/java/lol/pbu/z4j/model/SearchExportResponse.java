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

import java.util.List;

/**
 * SearchExportResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SearchExportResponse.JSON_PROPERTY_FACETS,
    SearchExportResponse.JSON_PROPERTY_LINKS,
    SearchExportResponse.JSON_PROPERTY_META,
    SearchExportResponse.JSON_PROPERTY_RESULTS,
})
@Serdeable
public class SearchExportResponse {

    public static final String JSON_PROPERTY_FACETS = "facets";
    public static final String JSON_PROPERTY_LINKS = "links";
    public static final String JSON_PROPERTY_META = "meta";
    public static final String JSON_PROPERTY_RESULTS = "results";

    /**
     * <p>The facets corresponding to the search query</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FACETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String facets;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_LINKS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SearchExportResponseLinks links;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_META)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SearchExportResponseMeta meta;

    /**
     * <p>May consist of tickets, users, groups, or organizations, as specified by the <code>result_type</code> property in each result object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RESULTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid SearchResultObject> results;

}