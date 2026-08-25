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
import lol.pbu.z4j.model.AutocompleteUsersPostRequestFilter;
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
 * AutocompleteUsersPostRequest
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    AutocompleteUsersPostRequest.JSON_PROPERTY_NAME,
    AutocompleteUsersPostRequest.JSON_PROPERTY_FIELD_ID,
    AutocompleteUsersPostRequest.JSON_PROPERTY_FILTER,
    AutocompleteUsersPostRequest.JSON_PROPERTY_PHONE,
    AutocompleteUsersPostRequest.JSON_PROPERTY_SOURCE,
})
@Serdeable
public class AutocompleteUsersPostRequest {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_FIELD_ID = "field_id";
    public static final String JSON_PROPERTY_FILTER = "filter";
    public static final String JSON_PROPERTY_PHONE = "phone";
    public static final String JSON_PROPERTY_SOURCE = "source";

    /**
     * <p>The name to search for the user</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>Field ID for lookup relationship autocomplete</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FIELD_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer fieldId;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_FILTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AutocompleteUsersPostRequestFilter filter;

    /**
     * <p>The phone number to search for the user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PHONE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String phone;

    /**
     * <p>Source for lookup relationship autocomplete</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String source;

    public AutocompleteUsersPostRequest(String name) {
        this.name = name;
    }

}