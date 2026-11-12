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
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Execution settings describing how a Zendesk view displays, groups, and sorts tickets.
 *
 * @author Jonathan-Zollinger
 * @since 0.4.3
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        ViewExecution.JSON_PROPERTY_GROUP_BY,
        ViewExecution.JSON_PROPERTY_GROUP_ORDER,
        ViewExecution.JSON_PROPERTY_SORT_BY,
        ViewExecution.JSON_PROPERTY_SORT_ORDER,
        ViewExecution.JSON_PROPERTY_GROUP,
        ViewExecution.JSON_PROPERTY_SORT,
        ViewExecution.JSON_PROPERTY_COLUMNS,
        ViewExecution.JSON_PROPERTY_FIELDS,
        ViewExecution.JSON_PROPERTY_CUSTOM_FIELDS
})
public record ViewExecution(
        @Nullable @JsonProperty(JSON_PROPERTY_GROUP_BY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String groupBy,
        @Nullable @JsonProperty(JSON_PROPERTY_GROUP_ORDER) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String groupOrder,
        @Nullable @JsonProperty(JSON_PROPERTY_SORT_BY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String sortBy,
        @Nullable @JsonProperty(JSON_PROPERTY_SORT_ORDER) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String sortOrder,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_GROUP) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ViewColumn group,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_SORT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ViewColumn sort,
        @Nullable @JsonProperty(JSON_PROPERTY_COLUMNS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid ViewColumn> columns,
        @Nullable @JsonProperty(JSON_PROPERTY_FIELDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid ViewColumn> fields,
        @Nullable @JsonProperty(JSON_PROPERTY_CUSTOM_FIELDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid ViewColumn> customFields
) implements ViewExecutionBuilder.With {

    public static final String JSON_PROPERTY_GROUP_BY = "group_by";
    public static final String JSON_PROPERTY_GROUP_ORDER = "group_order";
    public static final String JSON_PROPERTY_SORT_BY = "sort_by";
    public static final String JSON_PROPERTY_SORT_ORDER = "sort_order";
    public static final String JSON_PROPERTY_GROUP = "group";
    public static final String JSON_PROPERTY_SORT = "sort";
    public static final String JSON_PROPERTY_COLUMNS = "columns";
    public static final String JSON_PROPERTY_FIELDS = "fields";
    public static final String JSON_PROPERTY_CUSTOM_FIELDS = "custom_fields";

    public ViewExecution() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public ViewExecution addColumnsItem(ViewColumn columnItem) {
        List<ViewColumn> newColumns = columns == null ? new ArrayList<>() : new ArrayList<>(columns);
        newColumns.add(columnItem);
        return withColumns(newColumns);
    }

    public ViewExecution addFieldsItem(ViewColumn fieldItem) {
        List<ViewColumn> newFields = fields == null ? new ArrayList<>() : new ArrayList<>(fields);
        newFields.add(fieldItem);
        return withFields(newFields);
    }

    public ViewExecution addCustomFieldsItem(ViewColumn customFieldItem) {
        List<ViewColumn> newCustomFields = customFields == null ? new ArrayList<>() : new ArrayList<>(customFields);
        newCustomFields.add(customFieldItem);
        return withCustomFields(newCustomFields);
    }
}
