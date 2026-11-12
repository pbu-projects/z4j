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
import java.util.ArrayList;
import java.util.List;

/**
 * Output configuration specifying columns, grouping, and sorting when creating or updating a view.
 *
 * @author Jonathan-Zollinger
 * @since 0.4.3
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        ViewOutput.JSON_PROPERTY_COLUMNS,
        ViewOutput.JSON_PROPERTY_GROUP_BY,
        ViewOutput.JSON_PROPERTY_GROUP_ORDER,
        ViewOutput.JSON_PROPERTY_SORT_BY,
        ViewOutput.JSON_PROPERTY_SORT_ORDER
})
public record ViewOutput(
        @Nullable @JsonProperty(JSON_PROPERTY_COLUMNS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<String> columns,
        @Nullable @JsonProperty(JSON_PROPERTY_GROUP_BY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String groupBy,
        @Nullable @JsonProperty(JSON_PROPERTY_GROUP_ORDER) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String groupOrder,
        @Nullable @JsonProperty(JSON_PROPERTY_SORT_BY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String sortBy,
        @Nullable @JsonProperty(JSON_PROPERTY_SORT_ORDER) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String sortOrder
) implements ViewOutputBuilder.With {

    public static final String JSON_PROPERTY_COLUMNS = "columns";
    public static final String JSON_PROPERTY_GROUP_BY = "group_by";
    public static final String JSON_PROPERTY_GROUP_ORDER = "group_order";
    public static final String JSON_PROPERTY_SORT_BY = "sort_by";
    public static final String JSON_PROPERTY_SORT_ORDER = "sort_order";

    public ViewOutput() {
        this(null, null, null, null, null);
    }

    public ViewOutput(List<String> columns) {
        this(columns, null, null, null, null);
    }

    public ViewOutput addColumnsItem(String columnItem) {
        List<String> newColumns = columns == null ? new ArrayList<>() : new ArrayList<>(columns);
        newColumns.add(columnItem);
        return withColumns(newColumns);
    }

    public ViewOutput withGroupOrder(SortOrder order) {
        return withGroupOrder(order != null ? order.getValue() : null);
    }

    public ViewOutput withSortOrder(SortOrder order) {
        return withSortOrder(order != null ? order.getValue() : null);
    }
}
