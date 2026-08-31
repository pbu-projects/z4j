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
 * ViewCountObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ViewCountObject.JSON_PROPERTY_ACTIVE,
    ViewCountObject.JSON_PROPERTY_FRESH,
    ViewCountObject.JSON_PROPERTY_PRETTY,
    ViewCountObject.JSON_PROPERTY_URL,
    ViewCountObject.JSON_PROPERTY_VALUE,
    ViewCountObject.JSON_PROPERTY_VIEW_ID,
})
@Serdeable
public class ViewCountObject {

    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_FRESH = "fresh";
    public static final String JSON_PROPERTY_PRETTY = "pretty";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_VIEW_ID = "view_id";

    /**
     * <p>Only active views if true, inactive views if false, all views if null.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>false if the cached data is stale and the system is still loading and caching new data</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FRESH)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean fresh;

    /**
     * <p>A pretty-printed text approximation of the view count</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PRETTY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String pretty;

    /**
     * <p>The API url of the count</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * <p>The cached number of tickets in the view. Can also be null if the system is loading and caching new data. Not to be confused with 0 tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long value;

    /**
     * <p>The id of the view</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VIEW_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long viewId;

}