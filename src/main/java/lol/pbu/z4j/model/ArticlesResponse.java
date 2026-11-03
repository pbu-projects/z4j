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
        ArticlesResponse.JSON_PROPERTY_ARTICLES,
        ArticlesResponse.JSON_PROPERTY_META,
        ArticlesResponse.JSON_PROPERTY_LINKS,
        ArticlesResponse.JSON_PROPERTY_COUNT,
        ArticlesResponse.JSON_PROPERTY_NEXT_PAGE,
        ArticlesResponse.JSON_PROPERTY_PREVIOUS_PAGE,
        ArticlesResponse.JSON_PROPERTY_PAGE,
        ArticlesResponse.JSON_PROPERTY_PAGE_COUNT,
        ArticlesResponse.JSON_PROPERTY_PER_PAGE
})
public record ArticlesResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_ARTICLES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Article> articles,
        @Nullable @JsonProperty(JSON_PROPERTY_META) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Meta meta,
        @Nullable @JsonProperty(JSON_PROPERTY_LINKS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Links links,
        @Nullable @JsonProperty(JSON_PROPERTY_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer count,
        @Nullable @JsonProperty(JSON_PROPERTY_NEXT_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String nextPage,
        @Nullable @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String previousPage,
        @Nullable @JsonProperty(JSON_PROPERTY_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer page,
        @Nullable @JsonProperty(JSON_PROPERTY_PAGE_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer pageCount,
        @Nullable @JsonProperty(JSON_PROPERTY_PER_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer perPage
) implements ArticlesResponseBuilder.With {

    public static final String JSON_PROPERTY_ARTICLES = "articles";
    public static final String JSON_PROPERTY_META = "meta";
    public static final String JSON_PROPERTY_LINKS = "links";
    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";
    public static final String JSON_PROPERTY_PAGE = "page";
    public static final String JSON_PROPERTY_PAGE_COUNT = "page_count";
    public static final String JSON_PROPERTY_PER_PAGE = "per_page";

    public ArticlesResponse addArticlesItem(Article articlesItem) {
        List<Article> list = new ArrayList<>(articles != null ? articles : List.of());
        list.add(articlesItem);
        return new ArticlesResponse(list, meta, links, count, nextPage, previousPage, page, pageCount, perPage);
    }
}
