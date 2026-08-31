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
 * CustomFieldOptionObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    CustomFieldOptionObject.JSON_PROPERTY_NAME,
    CustomFieldOptionObject.JSON_PROPERTY_VALUE,
    CustomFieldOptionObject.JSON_PROPERTY_ID,
    CustomFieldOptionObject.JSON_PROPERTY_POSITION,
    CustomFieldOptionObject.JSON_PROPERTY_RAW_NAME,
    CustomFieldOptionObject.JSON_PROPERTY_URL,
})
@Serdeable
public class CustomFieldOptionObject {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_RAW_NAME = "raw_name";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>Name of the dropdown option</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>Value of the dropdown option</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_VALUE)
    private String value;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>Position of the dropdown option</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    /**
     * <p>Raw name of the dropdown option</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawName;

    /**
     * <p>URL of the dropdown option</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public CustomFieldOptionObject(String name, String value) {
        this.name = name;
        this.value = value;
    }

}