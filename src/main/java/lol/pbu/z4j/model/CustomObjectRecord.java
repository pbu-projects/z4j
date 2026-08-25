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
import java.util.HashMap;
import java.util.Map;
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
 * CustomObjectRecord
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    CustomObjectRecord.JSON_PROPERTY_NAME,
    CustomObjectRecord.JSON_PROPERTY_CREATED_AT,
    CustomObjectRecord.JSON_PROPERTY_CREATED_BY_USER_ID,
    CustomObjectRecord.JSON_PROPERTY_CUSTOM_OBJECT_FIELDS,
    CustomObjectRecord.JSON_PROPERTY_CUSTOM_OBJECT_KEY,
    CustomObjectRecord.JSON_PROPERTY_EXTERNAL_ID,
    CustomObjectRecord.JSON_PROPERTY_ID,
    CustomObjectRecord.JSON_PROPERTY_PHOTO,
    CustomObjectRecord.JSON_PROPERTY_UPDATED_AT,
    CustomObjectRecord.JSON_PROPERTY_UPDATED_BY_USER_ID,
    CustomObjectRecord.JSON_PROPERTY_URL,
})
@Serdeable
public class CustomObjectRecord {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CREATED_BY_USER_ID = "created_by_user_id";
    public static final String JSON_PROPERTY_CUSTOM_OBJECT_FIELDS = "custom_object_fields";
    public static final String JSON_PROPERTY_CUSTOM_OBJECT_KEY = "custom_object_key";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_PHOTO = "photo";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_UPDATED_BY_USER_ID = "updated_by_user_id";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>User-defined display name for the object. If autonumbering is selected for the custom object's name field, the name isn't allowed because it's automatically generated. If uniqueness is enabled, the name must be unique.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

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

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_OBJECT_FIELDS)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> customObjectFields;

    /**
     * <p>A user-defined unique identifier</p>
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
     * <p>The record photo represented as an <a href=\"/api-reference/ticketing/tickets/ticket-attachments/\">Attachment</a>. The <code>allows_photos</code> property must be set to true for the object. Record photos are publicly accessible via the photo <code>content_url</code>.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PHOTO)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> photo;

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
     * <p>Direct link to the specific custom object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * Set the value for the key for the customObjectFields map property in a chainable fashion.
     *
     * @return The same instance of CustomObjectRecord for chaining.
     */
    public CustomObjectRecord putCustomObjectFieldsItem(String key, Object customObjectFieldsItem) {
        if (customObjectFields == null) {
            customObjectFields = new HashMap<>();
        }
        customObjectFields.put(key, customObjectFieldsItem);
        return this;
    }

    /**
     * Set the value for the key for the photo map property in a chainable fashion.
     *
     * @return The same instance of CustomObjectRecord for chaining.
     */
    public CustomObjectRecord putPhotoItem(String key, Object photoItem) {
        if (photo == null) {
            photo = new HashMap<>();
        }
        photo.put(key, photoItem);
        return this;
    }

}