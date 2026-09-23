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
import lol.pbu.z4j.Generated;

/**
 * Translation
 *
 * @author Jonathan-Zollinger
 * @since 0.2.5
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        Translation.JSON_PROPERTY_ID,
        Translation.JSON_PROPERTY_URL,
        Translation.JSON_PROPERTY_HTML_URL,
        Translation.JSON_PROPERTY_SOURCE_ID,
        Translation.JSON_PROPERTY_SOURCE_TYPE,
        Translation.JSON_PROPERTY_LOCALE,
        Translation.JSON_PROPERTY_TITLE,
        Translation.JSON_PROPERTY_BODY,
        Translation.JSON_PROPERTY_OUTDATED,
        Translation.JSON_PROPERTY_DRAFT,
        Translation.JSON_PROPERTY_CREATED_AT,
        Translation.JSON_PROPERTY_UPDATED_AT,
        Translation.JSON_PROPERTY_UPDATED_BY_ID,
        Translation.JSON_PROPERTY_CREATED_BY_ID
})
@Serdeable
@Generated
public class Translation {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_HTML_URL = "html_url";
    public static final String JSON_PROPERTY_SOURCE_ID = "source_id";
    public static final String JSON_PROPERTY_SOURCE_TYPE = "source_type";
    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_BODY = "body";
    public static final String JSON_PROPERTY_OUTDATED = "outdated";
    public static final String JSON_PROPERTY_DRAFT = "draft";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_UPDATED_BY_ID = "updated_by_id";
    public static final String JSON_PROPERTY_CREATED_BY_ID = "created_by_id";

    /**
     * Automatically assigned when creating translations
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * The API url of this translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * The url of this translation in Help Center
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HTML_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String htmlUrl;

    /**
     * The id of the item that is translated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long sourceId;

    /**
     * The type of the item that is translated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String sourceType;

    /**
     * The locale of the translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private LocaleAbbreviation locale;

    /**
     * The title of the translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    /**
     * The body of the translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String body;

    /**
     * True if the translation is outdated; false otherwise
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OUTDATED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean outdated;

    /**
     * True if the translation is in draft status; false otherwise
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DRAFT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean draft;

    /**
     * The time at which the translation was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * The time at which the translation was updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * The id of the user who updated the translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_BY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long updatedById;

    /**
     * The id of the user who created the translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_BY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long createdById;
}
