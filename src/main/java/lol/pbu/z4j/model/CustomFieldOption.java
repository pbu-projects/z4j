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
import jakarta.validation.constraints.NotNull;

/**
 * Option item for custom dropdown and multi-select ticket fields.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        CustomFieldOption.JSON_PROPERTY_NAME,
        CustomFieldOption.JSON_PROPERTY_VALUE,
        CustomFieldOption.JSON_PROPERTY_ID,
        CustomFieldOption.JSON_PROPERTY_POSITION,
        CustomFieldOption.JSON_PROPERTY_RAW_NAME,
        CustomFieldOption.JSON_PROPERTY_URL
})
public record CustomFieldOption(
        @NotNull @JsonProperty(JSON_PROPERTY_NAME) String name,
        @NotNull @JsonProperty(JSON_PROPERTY_VALUE) String value,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_POSITION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer position,
        @Nullable @JsonProperty(JSON_PROPERTY_RAW_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String rawName,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url
) implements CustomFieldOptionBuilder.With {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_RAW_NAME = "raw_name";
    public static final String JSON_PROPERTY_URL = "url";

    public CustomFieldOption(String name, String value) {
        this(name, value, null, null, null, null);
    }
}
