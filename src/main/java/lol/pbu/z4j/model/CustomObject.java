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
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Custom object schema definition in Zendesk.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.2
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        CustomObject.JSON_PROPERTY_KEY,
        CustomObject.JSON_PROPERTY_TITLE,
        CustomObject.JSON_PROPERTY_TITLE_PLURALIZED,
        CustomObject.JSON_PROPERTY_DESCRIPTION,
        CustomObject.JSON_PROPERTY_RAW_TITLE,
        CustomObject.JSON_PROPERTY_RAW_TITLE_PLURALIZED,
        CustomObject.JSON_PROPERTY_RAW_DESCRIPTION,
        CustomObject.JSON_PROPERTY_INCLUDE_IN_LIST_VIEW,
        CustomObject.JSON_PROPERTY_ALLOWS_ATTACHMENTS,
        CustomObject.JSON_PROPERTY_ALLOWS_PHOTOS,
        CustomObject.JSON_PROPERTY_CREATED_BY_USER_ID,
        CustomObject.JSON_PROPERTY_UPDATED_BY_USER_ID,
        CustomObject.JSON_PROPERTY_URL,
        CustomObject.JSON_PROPERTY_CREATED_AT,
        CustomObject.JSON_PROPERTY_UPDATED_AT
})
public record CustomObject(
        @Nullable @JsonProperty(JSON_PROPERTY_KEY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String key,
        @Nullable @NotNull @JsonProperty(JSON_PROPERTY_TITLE) String title,
        @Nullable @NotNull @JsonProperty(JSON_PROPERTY_TITLE_PLURALIZED) String titlePluralized,
        @Nullable @JsonProperty(JSON_PROPERTY_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String description,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_TITLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawTitle,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_TITLE_PLURALIZED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawTitlePluralized,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawDescription,
        @Nullable @JsonProperty(JSON_PROPERTY_INCLUDE_IN_LIST_VIEW) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean includeInListView,
        @Nullable @JsonProperty(JSON_PROPERTY_ALLOWS_ATTACHMENTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean allowsAttachments,
        @Nullable @JsonProperty(JSON_PROPERTY_ALLOWS_PHOTOS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean allowsPhotos,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_BY_USER_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdByUserId,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_BY_USER_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedByUserId,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt
) implements CustomObjectBuilder.With {

    public static final String JSON_PROPERTY_KEY = "key";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_TITLE_PLURALIZED = "title_pluralized";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_RAW_TITLE = "raw_title";
    public static final String JSON_PROPERTY_RAW_TITLE_PLURALIZED = "raw_title_pluralized";
    public static final String JSON_PROPERTY_RAW_DESCRIPTION = "raw_description";
    public static final String JSON_PROPERTY_INCLUDE_IN_LIST_VIEW = "include_in_list_view";
    public static final String JSON_PROPERTY_ALLOWS_ATTACHMENTS = "allows_attachments";
    public static final String JSON_PROPERTY_ALLOWS_PHOTOS = "allows_photos";
    public static final String JSON_PROPERTY_CREATED_BY_USER_ID = "created_by_user_id";
    public static final String JSON_PROPERTY_UPDATED_BY_USER_ID = "updated_by_user_id";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    public CustomObject() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public CustomObject(String key, String title, String titlePluralized) {
        this(key, title, titlePluralized, null, null, null, null, null, null, null, null, null, null, null, null);
    }
}
