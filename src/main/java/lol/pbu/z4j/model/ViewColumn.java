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

/**
 * Column and sort metadata returned in a Zendesk view execution configuration or result.
 *
 * @author Jonathan-Zollinger
 * @since 0.4.3
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        ViewColumn.JSON_PROPERTY_ID,
        ViewColumn.JSON_PROPERTY_TITLE,
        ViewColumn.JSON_PROPERTY_TYPE,
        ViewColumn.JSON_PROPERTY_URL,
        ViewColumn.JSON_PROPERTY_FILTERABLE,
        ViewColumn.JSON_PROPERTY_SORTABLE,
        ViewColumn.JSON_PROPERTY_ORDER
})
public record ViewColumn(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String id,
        @Nullable @JsonProperty(JSON_PROPERTY_TITLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String title,
        @Nullable @JsonProperty(JSON_PROPERTY_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String type,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_FILTERABLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean filterable,
        @Nullable @JsonProperty(JSON_PROPERTY_SORTABLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean sortable,
        @Nullable @JsonProperty(JSON_PROPERTY_ORDER) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String order
) implements ViewColumnBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_FILTERABLE = "filterable";
    public static final String JSON_PROPERTY_SORTABLE = "sortable";
    public static final String JSON_PROPERTY_ORDER = "order";

    public ViewColumn() {
        this(null, null, null, null, null, null, null);
    }

    public ViewColumn(String id, String title) {
        this(id, title, null, null, null, null, null);
    }
}
