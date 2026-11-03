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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.List;

/**
 * Cursor-based incremental export response containing users.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.7
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        IncrementalUserCursorResponse.JSON_PROPERTY_USERS,
        IncrementalCursorPaginationResponse.JSON_PROPERTY_CURSOR,
        IncrementalCursorPaginationResponse.JSON_PROPERTY_AFTER_CURSOR,
        IncrementalCursorPaginationResponse.JSON_PROPERTY_BEFORE_CURSOR,
        IncrementalCursorPaginationResponse.JSON_PROPERTY_END_OF_STREAM,
        IncrementalCursorPaginationResponse.JSON_PROPERTY_COUNT
})
public record IncrementalUserCursorResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_USERS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<User> users,
        @Nullable @JsonProperty(JSON_PROPERTY_CURSOR) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String cursor,
        @Nullable @JsonProperty(JSON_PROPERTY_AFTER_CURSOR) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String afterCursor,
        @Nullable @JsonProperty(JSON_PROPERTY_BEFORE_CURSOR) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String beforeCursor,
        @Nullable @JsonProperty(JSON_PROPERTY_END_OF_STREAM) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean endOfStream,
        @Nullable @JsonProperty(JSON_PROPERTY_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer count
) implements IncrementalCursorPaginationResponse<User>, IncrementalUserCursorResponseBuilder.With {

    public static final String JSON_PROPERTY_USERS = "users";

    public IncrementalUserCursorResponse(@Nullable List<User> users) {
        this(users, null, null, null, null, null);
    }

    @Override
    @JsonIgnore
    public List<User> results() {
        return users;
    }
}
