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
import java.util.List;

/**
 * Response payload returned when executing a Zendesk view.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.6
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        ViewExecuteResponse.JSON_PROPERTY_ROWS,
        ViewExecuteResponse.JSON_PROPERTY_COLUMNS,
        ViewExecuteResponse.JSON_PROPERTY_USERS,
        ViewExecuteResponse.JSON_PROPERTY_GROUPS,
        ViewExecuteResponse.JSON_PROPERTY_ORGANIZATIONS,
        OffsetPaginationResponse.JSON_PROPERTY_COUNT,
        OffsetPaginationResponse.JSON_PROPERTY_NEXT_PAGE,
        OffsetPaginationResponse.JSON_PROPERTY_PREVIOUS_PAGE
})
public record ViewExecuteResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_ROWS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<ViewRow> rows,
        @Nullable @JsonProperty(JSON_PROPERTY_COLUMNS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Object> columns,
        @Nullable @JsonProperty(JSON_PROPERTY_USERS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<User> users,
        @Nullable @JsonProperty(JSON_PROPERTY_GROUPS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Group> groups,
        @Nullable @JsonProperty(JSON_PROPERTY_ORGANIZATIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<Organization> organizations,
        @Nullable @JsonProperty(JSON_PROPERTY_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer count,
        @Nullable @JsonProperty(JSON_PROPERTY_NEXT_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String nextPage,
        @Nullable @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String previousPage
) implements OffsetPaginationResponse, ViewExecuteResponseBuilder.With {

    public static final String JSON_PROPERTY_ROWS = "rows";
    public static final String JSON_PROPERTY_COLUMNS = "columns";
    public static final String JSON_PROPERTY_USERS = "users";
    public static final String JSON_PROPERTY_GROUPS = "groups";
    public static final String JSON_PROPERTY_ORGANIZATIONS = "organizations";
}
