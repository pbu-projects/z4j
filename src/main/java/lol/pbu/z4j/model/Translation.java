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
 * Translation
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
        Translation.JSON_PROPERTY_LOCALE,
        Translation.JSON_PROPERTY_TITLE,
        Translation.JSON_PROPERTY_BODY,
        Translation.JSON_PROPERTY_CREATED_AT,
        Translation.JSON_PROPERTY_CREATED_BY_ID,
        Translation.JSON_PROPERTY_DRAFT,
        Translation.JSON_PROPERTY_HTML_URL,
        Translation.JSON_PROPERTY_ID,
        Translation.JSON_PROPERTY_OUTDATED,
        Translation.JSON_PROPERTY_SOURCE_ID,
        Translation.JSON_PROPERTY_SOURCE_TYPE,
        Translation.JSON_PROPERTY_UPDATED_AT,
        Translation.JSON_PROPERTY_UPDATED_BY_ID,
        Translation.JSON_PROPERTY_URL,
})
@Serdeable
public class Translation {

    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_BODY = "body";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CREATED_BY_ID = "created_by_id";
    public static final String JSON_PROPERTY_DRAFT = "draft";
    public static final String JSON_PROPERTY_HTML_URL = "html_url";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_OUTDATED = "outdated";
    public static final String JSON_PROPERTY_SOURCE_ID = "source_id";
    public static final String JSON_PROPERTY_SOURCE_TYPE = "source_type";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_UPDATED_BY_ID = "updated_by_id";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * The locale of the translation
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_LOCALE)
    private String locale;

    /**
     * The title of the translation
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * HTML body of the translation. Empty by default
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String body;

    /**
     * The time at which the translation was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * The id of the user who created the translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_BY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long createdById;

    /**
     * True if the translation is a draft; false otherwise. False by default
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DRAFT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean draft;

    /**
     * The url of the translation in Help Center
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HTML_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String htmlUrl;

    /**
     * Automatically assigned when a translation is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * True if the translation is outdated; false otherwise. False by default
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OUTDATED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean outdated;

    /**
     * The id of the item that has this translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long sourceId;

    /**
     * The type of the item that has this translation. Can be \"article\", \"section\", or \"category\".
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String sourceType;

    /**
     * The time at which the translation was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * The id of the user who last updated the translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_BY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long updatedById;

    /**
     * The API url of the translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public Translation(String locale, String title) {
        this.locale = locale;
        this.title = title;
    }

}
