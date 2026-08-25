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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lol.pbu.z4j.model.ResolveOrganizationNames200ResponseMatchedValue;
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
 * ResolveOrganizationNames200Response
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    ResolveOrganizationNames200Response.JSON_PROPERTY_MATCHED,
    ResolveOrganizationNames200Response.JSON_PROPERTY_UNMATCHED,
})
@Serdeable
public class ResolveOrganizationNames200Response {

    public static final String JSON_PROPERTY_MATCHED = "matched";
    public static final String JSON_PROPERTY_UNMATCHED = "unmatched";

    /**
     * <p>Hash of matched organization names to their details. Keys preserve original input casing.</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_MATCHED)
    private Map<String, @Valid ResolveOrganizationNames200ResponseMatchedValue> matched;

    /**
     * <p>Array of organization names that could not be matched</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_UNMATCHED)
    private List<@NotNull String> unmatched = new ArrayList<>();

    public ResolveOrganizationNames200Response(Map<String, @Valid ResolveOrganizationNames200ResponseMatchedValue> matched, List<@NotNull String> unmatched) {
        this.matched = matched;
        this.unmatched = unmatched;
    }

    /**
     * Set the value for the key for the matched map property in a chainable fashion.
     *
     * @return The same instance of ResolveOrganizationNames200Response for chaining.
     */
    public ResolveOrganizationNames200Response putMatchedItem(String key, ResolveOrganizationNames200ResponseMatchedValue matchedItem) {
        matched.put(key, matchedItem);
        return this;
    }

    /**
     * Add an item to the unmatched property in a chainable fashion.
     *
     * @return The same instance of ResolveOrganizationNames200Response for chaining.
     */
    public ResolveOrganizationNames200Response addUnmatchedItem(String unmatchedItem) {
        unmatched.add(unmatchedItem);
        return this;
    }

}