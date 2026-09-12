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
import lol.pbu.z4j.Generated;

/**
 * CustomObjectRecord
 *
 * @since 0.2.2
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    CustomObjectRecord.JSON_PROPERTY_ID,
    CustomObjectRecord.JSON_PROPERTY_NAME,
    CustomObjectRecord.JSON_PROPERTY_CUSTOM_OBJECT_KEY,
    CustomObjectRecord.JSON_PROPERTY_CUSTOM_OBJECT_FIELDS,
    CustomObjectRecord.JSON_PROPERTY_EXTERNAL_ID,
    CustomObjectRecord.JSON_PROPERTY_PHOTO,
    CustomObjectRecord.JSON_PROPERTY_CREATED_BY_USER_ID,
    CustomObjectRecord.JSON_PROPERTY_UPDATED_BY_USER_ID,
    CustomObjectRecord.JSON_PROPERTY_URL,
    CustomObjectRecord.JSON_PROPERTY_CREATED_AT,
    CustomObjectRecord.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
@Generated
public class CustomObjectRecord {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_CUSTOM_OBJECT_KEY = "custom_object_key";
    public static final String JSON_PROPERTY_CUSTOM_OBJECT_FIELDS = "custom_object_fields";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_PHOTO = "photo";
    public static final String JSON_PROPERTY_CREATED_BY_USER_ID = "created_by_user_id";
    public static final String JSON_PROPERTY_UPDATED_BY_USER_ID = "updated_by_user_id";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_OBJECT_KEY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String customObjectKey;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_OBJECT_FIELDS)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> customObjectFields;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PHOTO)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> photo;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_BY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdByUserId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_BY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedByUserId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    public CustomObjectRecord putCustomObjectFieldsItem(String key, Object customObjectFieldsItem) {
        if (customObjectFields == null) {
            customObjectFields = new HashMap<>();
        }
        customObjectFields.put(key, customObjectFieldsItem);
        return this;
    }
}
