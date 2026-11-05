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
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

/**
 * Individual result entry within a background job status.
 *
 * @author Graeme-Rocher
 * @since 0.3.2
 */
@Serdeable
@Introspected
@RecordBuilder
@JsonPropertyOrder({
        JobStatusResult.JSON_PROPERTY_ID,
        JobStatusResult.JSON_PROPERTY_ACTION,
        JobStatusResult.JSON_PROPERTY_STATUS,
        JobStatusResult.JSON_PROPERTY_SUCCESS,
        JobStatusResult.JSON_PROPERTY_TITLE,
        JobStatusResult.JSON_PROPERTY_ERROR,
        JobStatusResult.JSON_PROPERTY_DETAILS
})
public record JobStatusResult(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_ACTION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String action,
        @Nullable @JsonProperty(JSON_PROPERTY_STATUS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String status,
        @Nullable @JsonProperty(JSON_PROPERTY_SUCCESS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean success,
        @Nullable @JsonProperty(JSON_PROPERTY_TITLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String title,
        @Nullable @JsonProperty(JSON_PROPERTY_ERROR) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String error,
        @Nullable @JsonProperty(JSON_PROPERTY_DETAILS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String details
) implements JobStatusResultBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_ACTION = "action";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_SUCCESS = "success";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_ERROR = "error";
    public static final String JSON_PROPERTY_DETAILS = "details";

    public JobStatusResult() {
        this(null, null, null, null, null, null, null);
    }
}
