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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DynamicContentObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    DynamicContentObject.JSON_PROPERTY_DEFAULT_LOCALE_ID,
    DynamicContentObject.JSON_PROPERTY_NAME,
    DynamicContentObject.JSON_PROPERTY_VARIANTS,
    DynamicContentObject.JSON_PROPERTY_CREATED_AT,
    DynamicContentObject.JSON_PROPERTY_ID,
    DynamicContentObject.JSON_PROPERTY_OUTDATED,
    DynamicContentObject.JSON_PROPERTY_PLACEHOLDER,
    DynamicContentObject.JSON_PROPERTY_UPDATED_AT,
    DynamicContentObject.JSON_PROPERTY_URL,
})
@Serdeable
public class DynamicContentObject {

    public static final String JSON_PROPERTY_DEFAULT_LOCALE_ID = "default_locale_id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_VARIANTS = "variants";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_OUTDATED = "outdated";
    public static final String JSON_PROPERTY_PLACEHOLDER = "placeholder";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The default locale for the item. Must be one of the <a href=\"/api-reference/ticketing/account-configuration/locales/#list-locales\">locales the account has active</a>.</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_DEFAULT_LOCALE_ID)
    private Long defaultLocaleId;

    /**
     * <p>The unique name of the item</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>All variants within this item. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content_item_variants/\">Dynamic Content Item Variants</a></p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_VARIANTS)
    private List<@Valid DynamicContentVariantObject> variants = new ArrayList<>();

    /**
     * <p>When this record was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Automatically assigned when creating items</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>Indicates the item has outdated variants within it</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OUTDATED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean outdated;

    /**
     * <p>Automatically generated placeholder for the item, derived from name</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PLACEHOLDER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String placeholder;

    /**
     * <p>When this record was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of this item</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public DynamicContentObject(Long defaultLocaleId, String name, List<@Valid DynamicContentVariantObject> variants) {
        this.defaultLocaleId = defaultLocaleId;
        this.name = name;
        this.variants = variants;
    }

    /**
     * Add an item to the variants property in a chainable fashion.
     *
     * @return The same instance of DynamicContentObject for chaining.
     */
    public DynamicContentObject addVariantsItem(DynamicContentVariantObject variantsItem) {
        variants.add(variantsItem);
        return this;
    }

}