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
import java.time.ZonedDateTime;

/**
 * Locale details for Zendesk Help Center and Support translations.
 *
 * @param createdAt the ISO 8601 formatted date-time the locale was created
 * @param id the unique ID of the locale
 * @param localeAbbreviation the code of the locale
 * @param name the name of the language
 * @param updatedAt the ISO 8601 formatted date-time when the locale was last updated
 * @param url the URL of the locale record
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        Locale.JSON_PROPERTY_CREATED_AT,
        Locale.JSON_PROPERTY_ID,
        Locale.JSON_PROPERTY_LOCALE,
        Locale.JSON_PROPERTY_NAME,
        Locale.JSON_PROPERTY_UPDATED_AT,
        Locale.JSON_PROPERTY_URL,
})
public record Locale(
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_LOCALE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) LocaleAbbreviation localeAbbreviation,
        @Nullable @JsonProperty(JSON_PROPERTY_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String name,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url
) implements LocaleBuilder.With {

    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
}
