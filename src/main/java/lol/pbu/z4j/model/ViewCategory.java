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
 * ViewCategory
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ViewCategory.JSON_PROPERTY_CREATED_AT,
    ViewCategory.JSON_PROPERTY_ID,
    ViewCategory.JSON_PROPERTY_NAME,
    ViewCategory.JSON_PROPERTY_PARENT_ID,
    ViewCategory.JSON_PROPERTY_POSITION,
    ViewCategory.JSON_PROPERTY_UPDATED_AT,
    ViewCategory.JSON_PROPERTY_URL,
})
@Serdeable
public class ViewCategory {

    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_PARENT_ID = "parent_id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The time the view category was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * <p>Automatically assigned when a view category is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>The name of the view category</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    /**
     * <p>The id of the parent category</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PARENT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String parentId;

    /**
     * <p>Position of the view category. Determines the order in which it will be displayed</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    /**
     * <p>The time of the last update of the view category</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * <p>The url of the view category</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}