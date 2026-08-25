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
import lol.pbu.z4j.model.CursorPaginationMetaLinks;
import lol.pbu.z4j.model.CursorPaginationMetaMeta;
import lol.pbu.z4j.model.ItamAssetLocation;
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
 * ItamAssetLocationsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ItamAssetLocationsResponse.JSON_PROPERTY_LINKS,
    ItamAssetLocationsResponse.JSON_PROPERTY_META,
    ItamAssetLocationsResponse.JSON_PROPERTY_LOCATIONS,
})
@Serdeable
public class ItamAssetLocationsResponse {

    public static final String JSON_PROPERTY_LINKS = "links";
    public static final String JSON_PROPERTY_META = "meta";
    public static final String JSON_PROPERTY_LOCATIONS = "locations";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_LINKS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private CursorPaginationMetaLinks links;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_META)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private CursorPaginationMetaMeta meta;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCATIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ItamAssetLocation> locations;

    /**
     * Add an item to the locations property in a chainable fashion.
     *
     * @return The same instance of ItamAssetLocationsResponse for chaining.
     */
    public ItamAssetLocationsResponse addLocationsItem(ItamAssetLocation locationsItem) {
        if (locations == null) {
            locations = new ArrayList<>();
        }
        locations.add(locationsItem);
        return this;
    }

}