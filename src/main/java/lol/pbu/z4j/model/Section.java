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

/**
 * Section in Zendesk Guide Help Center.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        Section.JSON_PROPERTY_LOCALE,
        Section.JSON_PROPERTY_NAME,
        Section.JSON_PROPERTY_CATEGORY_ID,
        Section.JSON_PROPERTY_CREATED_AT,
        Section.JSON_PROPERTY_DESCRIPTION,
        Section.JSON_PROPERTY_HTML_URL,
        Section.JSON_PROPERTY_ID,
        Section.JSON_PROPERTY_OUTDATED,
        Section.JSON_PROPERTY_PARENT_SECTION_ID,
        Section.JSON_PROPERTY_POSITION,
        Section.JSON_PROPERTY_SOURCE_LOCALE,
        Section.JSON_PROPERTY_THEME_TEMPLATE,
        Section.JSON_PROPERTY_UPDATED_AT,
        Section.JSON_PROPERTY_URL
})
public record Section(
        @Nullable @JsonProperty(JSON_PROPERTY_LOCALE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) LocaleAbbreviation localeAbbreviation,
        @Nullable @JsonProperty(JSON_PROPERTY_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String name,
        @Nullable @JsonProperty(JSON_PROPERTY_CATEGORY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long categoryId,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String description,
        @Nullable @JsonProperty(JSON_PROPERTY_HTML_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String htmlUrl,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_OUTDATED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean outdated,
        @Nullable @JsonProperty(JSON_PROPERTY_PARENT_SECTION_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long parentSectionId,
        @Nullable @JsonProperty(JSON_PROPERTY_POSITION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long position,
        @Nullable @JsonProperty(JSON_PROPERTY_SOURCE_LOCALE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String sourceLocale,
        @Nullable @JsonProperty(JSON_PROPERTY_THEME_TEMPLATE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String themeTemplate,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url
) implements SectionBuilder.With {

    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_CATEGORY_ID = "category_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_HTML_URL = "html_url";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_OUTDATED = "outdated";
    public static final String JSON_PROPERTY_PARENT_SECTION_ID = "parent_section_id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_SOURCE_LOCALE = "source_locale";
    public static final String JSON_PROPERTY_THEME_TEMPLATE = "theme_template";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    public Section(String name) {
        this(null, name, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Section(LocaleAbbreviation localeAbbreviation, String name) {
        this(localeAbbreviation, name, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Section() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }
}
