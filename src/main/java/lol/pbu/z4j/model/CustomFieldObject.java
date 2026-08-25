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
import lol.pbu.z4j.model.CustomFieldOptionObject;
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
 * CustomFieldObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    CustomFieldObject.JSON_PROPERTY_KEY,
    CustomFieldObject.JSON_PROPERTY_TITLE,
    CustomFieldObject.JSON_PROPERTY_TYPE,
    CustomFieldObject.JSON_PROPERTY_ACTIVE,
    CustomFieldObject.JSON_PROPERTY_CREATED_AT,
    CustomFieldObject.JSON_PROPERTY_CUSTOM_FIELD_OPTIONS,
    CustomFieldObject.JSON_PROPERTY_DESCRIPTION,
    CustomFieldObject.JSON_PROPERTY_ID,
    CustomFieldObject.JSON_PROPERTY_POSITION,
    CustomFieldObject.JSON_PROPERTY_RAW_DESCRIPTION,
    CustomFieldObject.JSON_PROPERTY_RAW_TITLE,
    CustomFieldObject.JSON_PROPERTY_REGEXP_FOR_VALIDATION,
    CustomFieldObject.JSON_PROPERTY_RELATIONSHIP_FILTER,
    CustomFieldObject.JSON_PROPERTY_SYSTEM,
    CustomFieldObject.JSON_PROPERTY_TAG,
    CustomFieldObject.JSON_PROPERTY_UPDATED_AT,
    CustomFieldObject.JSON_PROPERTY_URL,
})
@Serdeable
public class CustomFieldObject {

    public static final String JSON_PROPERTY_KEY = "key";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CUSTOM_FIELD_OPTIONS = "custom_field_options";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_RAW_DESCRIPTION = "raw_description";
    public static final String JSON_PROPERTY_RAW_TITLE = "raw_title";
    public static final String JSON_PROPERTY_REGEXP_FOR_VALIDATION = "regexp_for_validation";
    public static final String JSON_PROPERTY_RELATIONSHIP_FILTER = "relationship_filter";
    public static final String JSON_PROPERTY_SYSTEM = "system";
    public static final String JSON_PROPERTY_TAG = "tag";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>A unique key that identifies this custom field. This is used for updating the field and referencing in placeholders. The key must consist of only letters, numbers, and underscores. It can't be only numbers</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_KEY)
    private String key;

    /**
     * <p>The title of the custom field</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * <p>The custom field type: \"checkbox\", \"date\", \"decimal\", \"dropdown\", \"integer\", <a href=\"/api-reference/ticketing/lookup_relationships/lookup_relationships/\">\"lookup\"</a>, \"multiselect\", \"regexp\", \"text\", or \"textarea\"</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TYPE)
    private String type;

    /**
     * <p>If true, this field is available for use</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>The time the field was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Required and presented for a custom field of type \"dropdown\". Each option is represented by an object with a <code>name</code> and <code>value</code> property</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELD_OPTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid CustomFieldOptionObject> customFieldOptions;

    /**
     * <p>User-defined description of this field's purpose</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>Ordering of the field relative to other fields</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer position;

    /**
     * <p>The dynamic content placeholder, if present, or the <code>description</code> value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawDescription;

    /**
     * <p>The dynamic content placeholder, if present, or the <code>title</code> value, if not. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content/\">Dynamic Content Items</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitle;

    /**
     * <p>Regular expression field only. The validation pattern for a field value to be deemed valid</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REGEXP_FOR_VALIDATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String regexpForValidation;

    /**
     * <p>A filter definition that allows your autocomplete to filter down results</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RELATIONSHIP_FILTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object relationshipFilter;

    /**
     * <p>If true, only active and position values of this field can be changed</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SYSTEM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean system;

    /**
     * <p>Optional for custom field of type \"checkbox\"; not presented otherwise.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAG)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String tag;

    /**
     * <p>The time of the last update of the field</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The URL for this resource</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public CustomFieldObject(String key, String title, String type) {
        this.key = key;
        this.title = title;
        this.type = type;
    }

    /**
     * Add an item to the customFieldOptions property in a chainable fashion.
     *
     * @return The same instance of CustomFieldObject for chaining.
     */
    public CustomFieldObject addCustomFieldOptionsItem(CustomFieldOptionObject customFieldOptionsItem) {
        if (customFieldOptions == null) {
            customFieldOptions = new ArrayList<>();
        }
        customFieldOptions.add(customFieldOptionsItem);
        return this;
    }

}