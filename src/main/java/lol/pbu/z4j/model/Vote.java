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
 * Vote on a Help Center article or post.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        Vote.JSON_PROPERTY_ID,
        Vote.JSON_PROPERTY_URL,
        Vote.JSON_PROPERTY_USER_ID,
        Vote.JSON_PROPERTY_VALUE,
        Vote.JSON_PROPERTY_ITEM_ID,
        Vote.JSON_PROPERTY_ITEM_TYPE,
        Vote.JSON_PROPERTY_CREATED_AT,
        Vote.JSON_PROPERTY_UPDATED_AT
})
public record Vote(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_USER_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long userId,
        @NotNull @JsonProperty(JSON_PROPERTY_VALUE) Integer value,
        @Nullable @JsonProperty(JSON_PROPERTY_ITEM_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long itemId,
        @Nullable @JsonProperty(JSON_PROPERTY_ITEM_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String itemType,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedAt
) implements VoteBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_ID = "user_id";
    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_ITEM_ID = "item_id";
    public static final String JSON_PROPERTY_ITEM_TYPE = "item_type";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    public Vote(Integer value) {
        this(null, null, null, value, null, null, null, null);
    }

    public Vote() {
        this(null, null, null, null, null, null, null, null);
    }
}
