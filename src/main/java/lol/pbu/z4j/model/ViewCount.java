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
 * Ticket count details for a specific view.
 *
 * @param url API URL for the view count resource
 * @param viewId unique ID of the view
 * @param value numeric ticket count for the view
 * @param pretty human-readable ticket count text
 * @param fresh true if the cached count is up to date
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        ViewCount.JSON_PROPERTY_URL,
        ViewCount.JSON_PROPERTY_VIEW_ID,
        ViewCount.JSON_PROPERTY_VALUE,
        ViewCount.JSON_PROPERTY_PRETTY,
        ViewCount.JSON_PROPERTY_FRESH,
})
public record ViewCount(
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_VIEW_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long viewId,
        @Nullable @JsonProperty(JSON_PROPERTY_VALUE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long value,
        @Nullable @JsonProperty(JSON_PROPERTY_PRETTY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String pretty,
        @Nullable @JsonProperty(JSON_PROPERTY_FRESH) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean fresh
) implements ViewCountBuilder.With {

    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VIEW_ID = "view_id";
    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_PRETTY = "pretty";
    public static final String JSON_PROPERTY_FRESH = "fresh";
}
