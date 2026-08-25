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
import lol.pbu.z4j.model.BrandObject;
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
 * BrandsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    BrandsResponse.JSON_PROPERTY_COUNT,
    BrandsResponse.JSON_PROPERTY_NEXT_PAGE,
    BrandsResponse.JSON_PROPERTY_PREVIOUS_PAGE,
    BrandsResponse.JSON_PROPERTY_BRANDS,
})
@Serdeable
public class BrandsResponse {

    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";
    public static final String JSON_PROPERTY_BRANDS = "brands";

    /**
     * <p>the total record count</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer count;

    /**
     * <p>the URL of the next page</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    /**
     * <p>the URL of the previous page</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String previousPage;

    /**
     * <p>Array of brands</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BRANDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid BrandObject> brands;

    /**
     * Add an item to the brands property in a chainable fashion.
     *
     * @return The same instance of BrandsResponse for chaining.
     */
    public BrandsResponse addBrandsItem(BrandObject brandsItem) {
        if (brands == null) {
            brands = new ArrayList<>();
        }
        brands.add(brandsItem);
        return this;
    }

}