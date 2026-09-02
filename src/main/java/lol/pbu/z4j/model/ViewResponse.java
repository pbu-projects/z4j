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
import java.util.Map;

/**
 * ViewResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ViewResponse.JSON_PROPERTY_COLUMNS,
    ViewResponse.JSON_PROPERTY_GROUPS,
    ViewResponse.JSON_PROPERTY_ROWS,
    ViewResponse.JSON_PROPERTY_VIEW,
})
@Serdeable
public class ViewResponse {

    public static final String JSON_PROPERTY_COLUMNS = "columns";
    public static final String JSON_PROPERTY_GROUPS = "groups";
    public static final String JSON_PROPERTY_ROWS = "rows";
    public static final String JSON_PROPERTY_VIEW = "view";

    @Nullable
    @JsonProperty(JSON_PROPERTY_COLUMNS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Map<String, Object>> columns;

    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Map<String, Object>> groups;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ROWS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Map<String, Object>> rows;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VIEW)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ViewObject view;

    /**
     * Add an item to the columns property in a chainable fashion.
     *
     * @return The same instance of ViewResponse for chaining.
     */
    public ViewResponse addColumnsItem(Map<String, Object> columnsItem) {
        if (columns == null) {
            columns = new ArrayList<>();
        }
        columns.add(columnsItem);
        return this;
    }

    /**
     * Add an item to the groups property in a chainable fashion.
     *
     * @return The same instance of ViewResponse for chaining.
     */
    public ViewResponse addGroupsItem(Map<String, Object> groupsItem) {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        groups.add(groupsItem);
        return this;
    }

    /**
     * Add an item to the rows property in a chainable fashion.
     *
     * @return The same instance of ViewResponse for chaining.
     */
    public ViewResponse addRowsItem(Map<String, Object> rowsItem) {
        if (rows == null) {
            rows = new ArrayList<>();
        }
        rows.add(rowsItem);
        return this;
    }

}