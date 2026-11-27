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
import lol.pbu.z4j.Generated;

/**
 * Group object in the Zendesk Support API.
 */
@Serdeable
@RecordBuilder
@Generated
@JsonPropertyOrder({
        Group.JSON_PROPERTY_ID,
        Group.JSON_PROPERTY_URL,
        Group.JSON_PROPERTY_NAME,
        Group.JSON_PROPERTY_DESCRIPTION,
        Group.JSON_PROPERTY_DEFAULT,
        Group.JSON_PROPERTY_DELETED,
        Group.JSON_PROPERTY_IS_PUBLIC,
        Group.JSON_PROPERTY_CREATED_AT,
        Group.JSON_PROPERTY_UPDATED_AT
})
public record Group(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String name,
        @Nullable @JsonProperty(JSON_PROPERTY_DESCRIPTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String description,
        @Nullable @JsonProperty(JSON_PROPERTY_DEFAULT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean isDefault,
        @Nullable @JsonProperty(JSON_PROPERTY_DELETED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean deleted,
        @Nullable @JsonProperty(JSON_PROPERTY_IS_PUBLIC) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean isPublic,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt
) implements Exportable, GroupBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_DELETED = "deleted";
    public static final String JSON_PROPERTY_IS_PUBLIC = "is_public";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    public Group() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public Group(String name) {
        this(null, null, name, null, null, null, null, null, null);
    }
}
