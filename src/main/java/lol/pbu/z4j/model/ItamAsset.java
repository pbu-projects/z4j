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
import java.time.LocalDate;
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
 * ItamAsset
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    ItamAsset.JSON_PROPERTY_ASSET_TYPE_ID,
    ItamAsset.JSON_PROPERTY_NAME,
    ItamAsset.JSON_PROPERTY_STATUS_ID,
    ItamAsset.JSON_PROPERTY_ASSET_TAG,
    ItamAsset.JSON_PROPERTY_CREATED_AT,
    ItamAsset.JSON_PROPERTY_CUSTOM_FIELD_VALUES,
    ItamAsset.JSON_PROPERTY_EXTERNAL_ID,
    ItamAsset.JSON_PROPERTY_ID,
    ItamAsset.JSON_PROPERTY_LOCATION_ID,
    ItamAsset.JSON_PROPERTY_MANUFACTURER,
    ItamAsset.JSON_PROPERTY_MODEL,
    ItamAsset.JSON_PROPERTY_NOTES,
    ItamAsset.JSON_PROPERTY_ORGANIZATION_ID,
    ItamAsset.JSON_PROPERTY_PURCHASE_COST,
    ItamAsset.JSON_PROPERTY_PURCHASE_DATE,
    ItamAsset.JSON_PROPERTY_SERIAL_NUMBER,
    ItamAsset.JSON_PROPERTY_UPDATED_AT,
    ItamAsset.JSON_PROPERTY_URL,
    ItamAsset.JSON_PROPERTY_USER_ID,
    ItamAsset.JSON_PROPERTY_VENDOR,
    ItamAsset.JSON_PROPERTY_WARRANTY_EXPIRATION,
})
@Serdeable
public class ItamAsset implements ItamAssetBulkJobRequestJobItemsInner {

    public static final String JSON_PROPERTY_ASSET_TYPE_ID = "asset_type_id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_STATUS_ID = "status_id";
    public static final String JSON_PROPERTY_ASSET_TAG = "asset_tag";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CUSTOM_FIELD_VALUES = "custom_field_values";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_LOCATION_ID = "location_id";
    public static final String JSON_PROPERTY_MANUFACTURER = "manufacturer";
    public static final String JSON_PROPERTY_MODEL = "model";
    public static final String JSON_PROPERTY_NOTES = "notes";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_PURCHASE_COST = "purchase_cost";
    public static final String JSON_PROPERTY_PURCHASE_DATE = "purchase_date";
    public static final String JSON_PROPERTY_SERIAL_NUMBER = "serial_number";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_ID = "user_id";
    public static final String JSON_PROPERTY_VENDOR = "vendor";
    public static final String JSON_PROPERTY_WARRANTY_EXPIRATION = "warranty_expiration";

    /**
     * <p>Id of the asset type</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSET_TYPE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String assetTypeId;

    /**
     * <p>Display name for the asset</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>Id of current status of the asset</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_STATUS_ID)
    private String statusId;

    /**
     * <p>The tag for the asset</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSET_TAG)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String assetTag;

    /**
     * <p>The time the asset record was added</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>User-defined custom asset fields and values</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_FIELD_VALUES)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> customFieldValues;

    /**
     * <p>An id you can use to link an asset to external data</p>
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
     * <p>Id of the asset location</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String locationId;

    /**
     * <p>The asset's manufacturer name</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MANUFACTURER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String manufacturer;

    /**
     * <p>The asset's model name</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MODEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String model;

    /**
     * <p>The asset's notes</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NOTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String notes;

    /**
     * <p>Id of the organization the asset is associated with</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long organizationId;

    /**
     * <p>The asset's purchase cost</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PURCHASE_COST)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Float purchaseCost;

    /**
     * <p>The asset's purchase date</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PURCHASE_DATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private LocalDate purchaseDate;

    /**
     * <p>The asset's serial number</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SERIAL_NUMBER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String serialNumber;

    /**
     * <p>The time of the asset's last update</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>Direct link to the specific asset</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * <p>Id of the user the asset is assigned to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long userId;

    /**
     * <p>The asset's vendor name</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VENDOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String vendor;

    /**
     * <p>The asset's warranty expiration date</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_WARRANTY_EXPIRATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private LocalDate warrantyExpiration;

    public ItamAsset(String name, String statusId) {
        this.name = name;
        this.statusId = statusId;
    }

    /**
     * Set the value for the key for the customFieldValues map property in a chainable fashion.
     *
     * @return The same instance of ItamAsset for chaining.
     */
    public ItamAsset putCustomFieldValuesItem(String key, Object customFieldValuesItem) {
        if (customFieldValues == null) {
            customFieldValues = new HashMap<>();
        }
        customFieldValues.put(key, customFieldValuesItem);
        return this;
    }

}