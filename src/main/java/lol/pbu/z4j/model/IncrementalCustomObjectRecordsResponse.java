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

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner;
import lol.pbu.z4j.model.IncrementalCustomObjectRecordsResponseFilter;
import lol.pbu.z4j.model.IncrementalCustomObjectRecordsResponseMeta;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import io.micronaut.core.annotation.Nullable;

/**
 * <p>Response for incremental export of custom object records. See <a href=\"/api-reference/custom-data/custom-objects/custom_object_records/\">Custom Object Records</a> for detailed information about custom object record properties.</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    IncrementalCustomObjectRecordsResponse.JSON_PROPERTY_AFTER_CURSOR,
    IncrementalCustomObjectRecordsResponse.JSON_PROPERTY_AFTER_URL,
    IncrementalCustomObjectRecordsResponse.JSON_PROPERTY_BEFORE_CURSOR,
    IncrementalCustomObjectRecordsResponse.JSON_PROPERTY_BEFORE_URL,
    IncrementalCustomObjectRecordsResponse.JSON_PROPERTY_CUSTOM_OBJECT_RECORDS,
    IncrementalCustomObjectRecordsResponse.JSON_PROPERTY_FILTER,
    IncrementalCustomObjectRecordsResponse.JSON_PROPERTY_META,
})
@Serdeable
public class IncrementalCustomObjectRecordsResponse {

    public static final String JSON_PROPERTY_AFTER_CURSOR = "after_cursor";
    public static final String JSON_PROPERTY_AFTER_URL = "after_url";
    public static final String JSON_PROPERTY_BEFORE_CURSOR = "before_cursor";
    public static final String JSON_PROPERTY_BEFORE_URL = "before_url";
    public static final String JSON_PROPERTY_CUSTOM_OBJECT_RECORDS = "custom_object_records";
    public static final String JSON_PROPERTY_FILTER = "filter";
    public static final String JSON_PROPERTY_META = "meta";

    /**
     * <p>Cursor value for the next page. Use this value for the cursor parameter in the next request.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AFTER_CURSOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String afterCursor;

    /**
     * <p>URL to fetch the next page of results. Null if this is the last page.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AFTER_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String afterUrl;

    /**
     * <p>Cursor value for the previous page. Null if this is the first page.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BEFORE_CURSOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String beforeCursor;

    /**
     * <p>URL to fetch the previous page of results. Null if this is the first page.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BEFORE_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String beforeUrl;

    /**
     * <p>Array of custom object records that have changed since the start time.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_OBJECT_RECORDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner> customObjectRecords;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_FILTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private IncrementalCustomObjectRecordsResponseFilter filter;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_META)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private IncrementalCustomObjectRecordsResponseMeta meta;

    /**
     * Add an item to the customObjectRecords property in a chainable fashion.
     *
     * @return The same instance of IncrementalCustomObjectRecordsResponse for chaining.
     */
    public IncrementalCustomObjectRecordsResponse addCustomObjectRecordsItem(IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner customObjectRecordsItem) {
        if (customObjectRecords == null) {
            customObjectRecords = new ArrayList<>();
        }
        customObjectRecords.add(customObjectRecordsItem);
        return this;
    }

}