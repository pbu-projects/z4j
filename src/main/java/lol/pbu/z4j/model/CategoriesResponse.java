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
import java.util.ArrayList;
import java.util.List;

@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        CategoriesResponse.JSON_PROPERTY_CATEGORIES,
        CategoriesResponse.JSON_PROPERTY_COUNT,
        CategoriesResponse.JSON_PROPERTY_PAGE,
        CategoriesResponse.JSON_PROPERTY_PAGE_COUNT,
        CategoriesResponse.JSON_PROPERTY_NEXT_PAGE,
        CategoriesResponse.JSON_PROPERTY_PREVIOUS_PAGE
})
public record CategoriesResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_CATEGORIES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Category> categories,
        @Nullable @JsonProperty(JSON_PROPERTY_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer count,
        @Nullable @JsonProperty(JSON_PROPERTY_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer page,
        @Nullable @JsonProperty(JSON_PROPERTY_PAGE_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer pageCount,
        @Nullable @JsonProperty(JSON_PROPERTY_NEXT_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String nextPage,
        @Nullable @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String previousPage
) implements OffsetPaginationResponse, CategoriesResponseBuilder.With {

    public static final String JSON_PROPERTY_CATEGORIES = "categories";
    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_PAGE = "page";
    public static final String JSON_PROPERTY_PAGE_COUNT = "page_count";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";

    public CategoriesResponse(@Nullable List<@Valid Category> categories) {
        this(categories, null, null, null, null, null);
    }

    public CategoriesResponse addCategoriesItem(Category item) {
        List<Category> list = new ArrayList<>(categories != null ? categories : List.of());
        list.add(item);
        return new CategoriesResponse(list, count, page, pageCount, nextPage, previousPage);
    }
}
