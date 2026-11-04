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
 * Guide Media asset uploaded in Zendesk Guide.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        GuideMedia.JSON_PROPERTY_ID,
        GuideMedia.JSON_PROPERTY_ACCESS_KEY,
        GuideMedia.JSON_PROPERTY_NAME,
        GuideMedia.JSON_PROPERTY_SIZE,
        GuideMedia.JSON_PROPERTY_URL,
        GuideMedia.JSON_PROPERTY_CONTENT_TYPE,
        GuideMedia.JSON_PROPERTY_VERSION,
        GuideMedia.JSON_PROPERTY_CREATED_AT,
        GuideMedia.JSON_PROPERTY_UPDATED_AT
})
public record GuideMedia(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String id,
        @Nullable @JsonProperty(JSON_PROPERTY_ACCESS_KEY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String accessKey,
        @Nullable @JsonProperty(JSON_PROPERTY_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String name,
        @Nullable @JsonProperty(JSON_PROPERTY_SIZE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long size,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_CONTENT_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String contentType,
        @Nullable @JsonProperty(JSON_PROPERTY_VERSION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer version,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedAt
) implements GuideMediaBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_ACCESS_KEY = "access_key";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_SIZE = "size";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_VERSION = "version";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    public GuideMedia(String name) {
        this(null, null, name, null, null, null, null, null, null);
    }

    public GuideMedia() {
        this(null, null, null, null, null, null, null, null, null);
    }
}
