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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * CustomFieldOptionObject
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
        CustomFieldOption.JSON_PROPERTY_NAME,
        CustomFieldOption.JSON_PROPERTY_VALUE,
        CustomFieldOption.JSON_PROPERTY_ID,
        CustomFieldOption.JSON_PROPERTY_POSITION,
        CustomFieldOption.JSON_PROPERTY_RAW_NAME,
        CustomFieldOption.JSON_PROPERTY_URL,
})
@Serdeable
public class CustomFieldOption {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_RAW_NAME = "raw_name";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * Name of the dropdown option
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * Value of the dropdown option
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_VALUE)
    private String value;

    /**
     * Automatically assigned upon creation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * Position of the dropdown option
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer position;

    /**
     * Raw name of the dropdown option
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawName;

    /**
     * URL of the dropdown option
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public CustomFieldOption(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
