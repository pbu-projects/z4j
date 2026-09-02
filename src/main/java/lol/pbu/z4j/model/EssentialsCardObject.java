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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * EssentialsCardObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    EssentialsCardObject.JSON_PROPERTY_FIELDS,
    EssentialsCardObject.JSON_PROPERTY_CREATED_AT,
    EssentialsCardObject.JSON_PROPERTY_DEFAULT,
    EssentialsCardObject.JSON_PROPERTY_ID,
    EssentialsCardObject.JSON_PROPERTY_KEY,
    EssentialsCardObject.JSON_PROPERTY_LAYOUT,
    EssentialsCardObject.JSON_PROPERTY_MAX_COUNT,
    EssentialsCardObject.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
public class EssentialsCardObject {

    public static final String JSON_PROPERTY_FIELDS = "fields";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_KEY = "key";
    public static final String JSON_PROPERTY_LAYOUT = "layout";
    public static final String JSON_PROPERTY_MAX_COUNT = "max_count";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    /**
     * <p>Fields that are displayed in the essentials card details. The order is defined by the order of the fields in the array</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_FIELDS)
    private List<Map<String, Object>> fields = new ArrayList<>();

    /**
     * <p>Date and time the essentials card were created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>If true, the system has used the first twenty fields for the custom object type as the essentials card.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _default;

    /**
     * <p>id of the essentials card</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>Object type. Example: <code>zen:user</code> refers to <code>User</code> type</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_KEY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String key;

    /**
     * <p>layout type</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LAYOUT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String layout;

    /**
     * <p>Maximum number of fields allowed in the essentials card</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MAX_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long maxCount;

    /**
     * <p>Date and time the essentials card were last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    public EssentialsCardObject(List<Map<String, Object>> fields) {
        this.fields = fields;
    }

    /**
     * Add an item to the fields property in a chainable fashion.
     *
     * @return The same instance of EssentialsCardObject for chaining.
     */
    public EssentialsCardObject addFieldsItem(Map<String, Object> fieldsItem) {
        fields.add(fieldsItem);
        return this;
    }

}