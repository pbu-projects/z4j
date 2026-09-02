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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>Account's knowledge management and search capabilities. See <a href=\"#knowledge\">Knowledge</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsKnowledgeObject.JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_BRANDS,
    AccountSettingsKnowledgeObject.JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_CATEGORIES,
    AccountSettingsKnowledgeObject.JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_EXTERNAL_CONTENT_SOURCES,
    AccountSettingsKnowledgeObject.JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_LOCALES,
    AccountSettingsKnowledgeObject.JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_SECTIONS,
    AccountSettingsKnowledgeObject.JSON_PROPERTY_GENERATIVE_ANSWERS,
    AccountSettingsKnowledgeObject.JSON_PROPERTY_REQUIRE_ARTICLE_TEMPLATES,
    AccountSettingsKnowledgeObject.JSON_PROPERTY_SEARCH_ARTICLES,
    AccountSettingsKnowledgeObject.JSON_PROPERTY_SEARCH_COMMUNITY_POSTS,
    AccountSettingsKnowledgeObject.JSON_PROPERTY_SEARCH_EXTERNAL_CONTENT,
})
@Serdeable
public class AccountSettingsKnowledgeObject {

    public static final String JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_BRANDS = "default_search_filters_brands";
    public static final String JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_CATEGORIES = "default_search_filters_categories";
    public static final String JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_EXTERNAL_CONTENT_SOURCES = "default_search_filters_external_content_sources";
    public static final String JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_LOCALES = "default_search_filters_locales";
    public static final String JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_SECTIONS = "default_search_filters_sections";
    public static final String JSON_PROPERTY_GENERATIVE_ANSWERS = "generative_answers";
    public static final String JSON_PROPERTY_REQUIRE_ARTICLE_TEMPLATES = "require_article_templates";
    public static final String JSON_PROPERTY_SEARCH_ARTICLES = "search_articles";
    public static final String JSON_PROPERTY_SEARCH_COMMUNITY_POSTS = "search_community_posts";
    public static final String JSON_PROPERTY_SEARCH_EXTERNAL_CONTENT = "search_external_content";

    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_BRANDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String defaultSearchFiltersBrands;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_CATEGORIES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String defaultSearchFiltersCategories;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_EXTERNAL_CONTENT_SOURCES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String defaultSearchFiltersExternalContentSources;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_LOCALES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String defaultSearchFiltersLocales;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT_SEARCH_FILTERS_SECTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String defaultSearchFiltersSections;

    @Nullable
    @JsonProperty(JSON_PROPERTY_GENERATIVE_ANSWERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean generativeAnswers;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUIRE_ARTICLE_TEMPLATES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean requireArticleTemplates;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SEARCH_ARTICLES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean searchArticles;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SEARCH_COMMUNITY_POSTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean searchCommunityPosts;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SEARCH_EXTERNAL_CONTENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean searchExternalContent;

}