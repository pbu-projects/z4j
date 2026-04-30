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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Section
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
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
        Section.JSON_PROPERTY_URL,
})
@Serdeable
public class Section {

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

    /**
     * The locale in which the section is displayed
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_LOCALE)
    private String locale;

    /**
     * The name of the section
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * The id of the category to which this section belongs
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CATEGORY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long categoryId;

    /**
     * The time at which the section was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * The description of the section
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * The url of this section in HC
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HTML_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String htmlUrl;

    /**
     * Automatically assigned when creating subscriptions
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * Whether the section is out of date
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OUTDATED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean outdated;

    /**
     * The id of the section to which this section belongs. Only writable for Guide Enterprise customers
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PARENT_SECTION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long parentSectionId;

    /**
     * The position of this section in the section list. Used when sorting is set to ´manual´. By default the section is added to the end of the list
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    /**
     * The source (default) locale of the section
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String sourceLocale;

    /**
     * The theme template name used to display this section in Help Center.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_THEME_TEMPLATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String themeTemplate;

    /**
     * The time at which the section was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * The API url of this section
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public Section(String locale, String name) {
        this.locale = locale;
        this.name = name;
    }

}
