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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner.JSON_PROPERTY_CREATED_AT,
    IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner.JSON_PROPERTY_CREATED_BY_USER_ID,
    IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner.JSON_PROPERTY_CUSTOM_OBJECT_FIELDS,
    IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner.JSON_PROPERTY_CUSTOM_OBJECT_KEY,
    IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner.JSON_PROPERTY_EXTERNAL_ID,
    IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner.JSON_PROPERTY_ID,
    IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner.JSON_PROPERTY_NAME,
    IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner.JSON_PROPERTY_UPDATED_AT,
    IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner.JSON_PROPERTY_UPDATED_BY_USER_ID,
    IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner.JSON_PROPERTY_URL,
})
@Serdeable
public class IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner {

    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CREATED_BY_USER_ID = "created_by_user_id";
    public static final String JSON_PROPERTY_CUSTOM_OBJECT_FIELDS = "custom_object_fields";
    public static final String JSON_PROPERTY_CUSTOM_OBJECT_KEY = "custom_object_key";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_UPDATED_BY_USER_ID = "updated_by_user_id";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The time the object was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Id of a user who created the object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_BY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdByUserId;

    /**
     * <p>Custom field values. For deleted records, field values will be \"[DELETED]\" unless exclude_deleted filter is used.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_OBJECT_FIELDS)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> customObjectFields;

    /**
     * <p>A user-defined unique identifier for the custom object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_OBJECT_KEY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String customObjectKey;

    /**
     * <p>An id you can use to link custom object records to external data</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>User-defined display name for the object. May be \"[DELETED]\" for deleted records.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    /**
     * <p>The time of the last update of the object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>Id of the last user who updated the object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_BY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedByUserId;

    /**
     * <p>Direct link to the specific custom object record</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * Set the value for the key for the customObjectFields map property in a chainable fashion.
     *
     * @return The same instance of IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner for chaining.
     */
    public IncrementalCustomObjectRecordsResponseCustomObjectRecordsInner putCustomObjectFieldsItem(String key, Object customObjectFieldsItem) {
        if (customObjectFields == null) {
            customObjectFields = new HashMap<>();
        }
        customObjectFields.put(key, customObjectFieldsItem);
        return this;
    }

}