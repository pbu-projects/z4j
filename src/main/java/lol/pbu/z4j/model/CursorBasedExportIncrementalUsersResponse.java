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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * CursorBasedExportIncrementalUsersResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    CursorBasedExportIncrementalUsersResponse.JSON_PROPERTY_AFTER_CURSOR,
    CursorBasedExportIncrementalUsersResponse.JSON_PROPERTY_AFTER_URL,
    CursorBasedExportIncrementalUsersResponse.JSON_PROPERTY_BEFORE_CURSOR,
    CursorBasedExportIncrementalUsersResponse.JSON_PROPERTY_BEFORE_URL,
    CursorBasedExportIncrementalUsersResponse.JSON_PROPERTY_END_OF_STREAM,
    CursorBasedExportIncrementalUsersResponse.JSON_PROPERTY_USERS,
})
@Serdeable
public class CursorBasedExportIncrementalUsersResponse {

    public static final String JSON_PROPERTY_AFTER_CURSOR = "after_cursor";
    public static final String JSON_PROPERTY_AFTER_URL = "after_url";
    public static final String JSON_PROPERTY_BEFORE_CURSOR = "before_cursor";
    public static final String JSON_PROPERTY_BEFORE_URL = "before_url";
    public static final String JSON_PROPERTY_END_OF_STREAM = "end_of_stream";
    public static final String JSON_PROPERTY_USERS = "users";

    @Nullable
    @JsonProperty(JSON_PROPERTY_AFTER_CURSOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String afterCursor;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AFTER_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String afterUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BEFORE_CURSOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String beforeCursor;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BEFORE_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String beforeUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_END_OF_STREAM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean endOfStream;

    @Nullable
    @JsonProperty(JSON_PROPERTY_USERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid UserObject> users;

    /**
     * Add an item to the users property in a chainable fashion.
     *
     * @return The same instance of CursorBasedExportIncrementalUsersResponse for chaining.
     */
    public CursorBasedExportIncrementalUsersResponse addUsersItem(UserObject usersItem) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(usersItem);
        return this;
    }

}