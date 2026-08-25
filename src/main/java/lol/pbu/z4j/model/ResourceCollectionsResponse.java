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
import lol.pbu.z4j.model.ResourceCollectionObject;
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
 * ResourceCollectionsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ResourceCollectionsResponse.JSON_PROPERTY_COUNT,
    ResourceCollectionsResponse.JSON_PROPERTY_NEXT_PAGE,
    ResourceCollectionsResponse.JSON_PROPERTY_PREVIOUS_PAGE,
    ResourceCollectionsResponse.JSON_PROPERTY_RESOURCE_COLLECTIONS,
})
@Serdeable
public class ResourceCollectionsResponse {

    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";
    public static final String JSON_PROPERTY_RESOURCE_COLLECTIONS = "resource_collections";

    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer count;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String previousPage;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RESOURCE_COLLECTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ResourceCollectionObject> resourceCollections;

    /**
     * Add an item to the resourceCollections property in a chainable fashion.
     *
     * @return The same instance of ResourceCollectionsResponse for chaining.
     */
    public ResourceCollectionsResponse addResourceCollectionsItem(ResourceCollectionObject resourceCollectionsItem) {
        if (resourceCollections == null) {
            resourceCollections = new ArrayList<>();
        }
        resourceCollections.add(resourceCollectionsItem);
        return this;
    }

}