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
import lol.pbu.z4j.model.SearchExportResponseLinks;
import lol.pbu.z4j.model.SearchExportResponseMeta;
import lol.pbu.z4j.model.SearchResultObject;
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