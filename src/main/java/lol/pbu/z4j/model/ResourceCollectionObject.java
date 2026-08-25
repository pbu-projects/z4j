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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.ResourceCollectionObjectResourcesInner;
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
 * ResourceCollectionObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ResourceCollectionObject.JSON_PROPERTY_CREATED_AT,
    ResourceCollectionObject.JSON_PROPERTY_ID,
    ResourceCollectionObject.JSON_PROPERTY_RESOURCES,
    ResourceCollectionObject.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
public class ResourceCollectionObject {

    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_RESOURCES = "resources";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    /**
     * <p>When the resource collection was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>id for the resource collection. Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>Array of resource metadata objects. See <a href=\"#resource-objects\">Resource objects</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RESOURCES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ResourceCollectionObjectResourcesInner> resources;

    /**
     * <p>Last time the resource collection was updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

}