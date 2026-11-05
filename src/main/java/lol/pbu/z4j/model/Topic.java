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

/**
 * Community topic in Zendesk Help Center.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        Topic.JSON_PROPERTY_NAME,
        Topic.JSON_PROPERTY_CREATED_AT,
        Topic.JSON_PROPERTY_DESCRIPTION,
        Topic.JSON_PROPERTY_FOLLOWER_COUNT,
        Topic.JSON_PROPERTY_HTML_URL,
        Topic.JSON_PROPERTY_ID,
        Topic.JSON_PROPERTY_MANAGEABLE_BY,
        Topic.JSON_PROPERTY_POSITION,
        Topic.JSON_PROPERTY_UPDATED_AT,
        Topic.JSON_PROPERTY_URL,
        Topic.JSON_PROPERTY_USER_SEGMENT_ID
})
public record Topic(
        @NotNull @JsonProperty(JSON_PROPERTY_NAME) String name,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String description,
        @Nullable @JsonProperty(JSON_PROPERTY_FOLLOWER_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long followerCount,
        @Nullable @JsonProperty(JSON_PROPERTY_HTML_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String htmlUrl,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_MANAGEABLE_BY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TopicManageableBy manageableBy,
        @Nullable @JsonProperty(JSON_PROPERTY_POSITION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long position,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_USER_SEGMENT_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long userSegmentId
) implements TopicBuilder.With {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_FOLLOWER_COUNT = "follower_count";
    public static final String JSON_PROPERTY_HTML_URL = "html_url";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_MANAGEABLE_BY = "manageable_by";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_SEGMENT_ID = "user_segment_id";

    public Topic(String name) {
        this(name, null, null, null, null, null, null, null, null, null, null);
    }

    public Topic() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }
}
