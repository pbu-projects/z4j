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
 * Guide Media object representing media assets uploaded in Zendesk Guide.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
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
@Serdeable
@Generated
public class GuideMedia {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_ACCESS_KEY = "access_key";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_SIZE = "size";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_VERSION = "version";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ACCESS_KEY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String accessKey;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SIZE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long size;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentType;

    @Nullable
    @JsonProperty(JSON_PROPERTY_VERSION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer version;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    public GuideMedia(String name) {
        this.name = name;
    }
}
