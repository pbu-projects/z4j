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
 * ItamAssetType
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    ItamAssetType.JSON_PROPERTY_NAME,
    ItamAssetType.JSON_PROPERTY_PARENT_ID,
    ItamAssetType.JSON_PROPERTY_CREATED_AT,
    ItamAssetType.JSON_PROPERTY_CREATED_BY_USER_ID,
    ItamAssetType.JSON_PROPERTY_DESCRIPTION,
    ItamAssetType.JSON_PROPERTY_EXTERNAL_ID,
    ItamAssetType.JSON_PROPERTY_FIELD_KEYS,
    ItamAssetType.JSON_PROPERTY_HIERARCHY_DEPTH,
    ItamAssetType.JSON_PROPERTY_ID,
    ItamAssetType.JSON_PROPERTY_IS_STANDARD,
    ItamAssetType.JSON_PROPERTY_UPDATED_AT,
    ItamAssetType.JSON_PROPERTY_UPDATED_BY_USER_ID,
    ItamAssetType.JSON_PROPERTY_URL,
})
@Serdeable
public class ItamAssetType {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_PARENT_ID = "parent_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CREATED_BY_USER_ID = "created_by_user_id";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_FIELD_KEYS = "field_keys";
    public static final String JSON_PROPERTY_HIERARCHY_DEPTH = "hierarchy_depth";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IS_STANDARD = "is_standard";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_UPDATED_BY_USER_ID = "updated_by_user_id";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>A unique display name for the asset type</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    /**
     * <p>The id of the parent asset type within the hierarchy tree</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_PARENT_ID)
    private String parentId;

    /**
     * <p>The time the asset type was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The id of the user who created the asset type</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_BY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer createdByUserId;

    /**
     * <p>A description of the asset type</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>An id you can use to link asset types to external data</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    /**
     * <p>Custom field keys associated with the asset type</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FIELD_KEYS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> fieldKeys;

    /**
     * <p>The depth within the hierarchy tree. Valid values: 1, 2, and 3</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HIERARCHY_DEPTH)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer hierarchyDepth;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>Whether this asset type is a standard asset type. Standard asset types cannot be modified.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_STANDARD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isStandard;

    /**
     * <p>The time of the asset type's last update</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The id of the user who last the asset type</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_BY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer updatedByUserId;

    /**
     * <p>Direct link to the specific asset type</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public ItamAssetType(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Add an item to the fieldKeys property in a chainable fashion.
     *
     * @return The same instance of ItamAssetType for chaining.
     */
    public ItamAssetType addFieldKeysItem(String fieldKeysItem) {
        if (fieldKeys == null) {
            fieldKeys = new ArrayList<>();
        }
        fieldKeys.add(fieldKeysItem);
        return this;
    }

}