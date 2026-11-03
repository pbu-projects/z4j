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
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import java.util.List;

@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        SearchResponse.JSON_PROPERTY_COUNT,
        SearchResponse.JSON_PROPERTY_FACETS,
        SearchResponse.JSON_PROPERTY_NEXT_PAGE,
        SearchResponse.JSON_PROPERTY_PREVIOUS_PAGE,
        SearchResponse.JSON_PROPERTY_RESULTS,
        SearchResponse.JSON_PROPERTY_USERS,
        SearchResponse.JSON_PROPERTY_ORGANIZATIONS,
        SearchResponse.JSON_PROPERTY_GROUPS,
})
public record SearchResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer count,
        @Nullable @JsonProperty(JSON_PROPERTY_FACETS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String facets,
        @Nullable @JsonProperty(JSON_PROPERTY_NEXT_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String nextPage,
        @Nullable @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String previousPage,
        @Nullable @JsonProperty(JSON_PROPERTY_RESULTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid SearchResult> results,
        @Nullable @JsonProperty(JSON_PROPERTY_USERS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid User> users,
        @Nullable @JsonProperty(JSON_PROPERTY_ORGANIZATIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Organization> organizations,
        @Nullable @JsonProperty(JSON_PROPERTY_GROUPS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Group> groups
) implements SearchResponseBuilder.With {

    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_FACETS = "facets";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";
    public static final String JSON_PROPERTY_RESULTS = "results";
    public static final String JSON_PROPERTY_USERS = "users";
    public static final String JSON_PROPERTY_ORGANIZATIONS = "organizations";
    public static final String JSON_PROPERTY_GROUPS = "groups";
}
