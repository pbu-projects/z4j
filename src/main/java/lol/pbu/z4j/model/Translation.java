/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Rules, obligations, or licenses.
 */
package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

/**
 * Translation of an article, section, or category in Help Center.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.5
 */
@Serdeable
@RecordBuilder
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
public record Translation(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_HTML_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String htmlUrl,
        @Nullable @JsonProperty(JSON_PROPERTY_SOURCE_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long sourceId,
        @Nullable @JsonProperty(JSON_PROPERTY_SOURCE_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String sourceType,
        @Nullable @JsonProperty(JSON_PROPERTY_LOCALE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) LocaleAbbreviation locale,
        @Nullable @JsonProperty(JSON_PROPERTY_TITLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String title,
        @Nullable @JsonProperty(JSON_PROPERTY_BODY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String body,
        @Nullable @JsonProperty(JSON_PROPERTY_OUTDATED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean outdated,
        @Nullable @JsonProperty(JSON_PROPERTY_DRAFT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean draft,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_BY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long updatedById,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_BY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long createdById
) implements TranslationBuilder.With {

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

    public Translation() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }
}
