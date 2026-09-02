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
import java.util.List;

/**
 * BrandObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    BrandObject.JSON_PROPERTY_NAME,
    BrandObject.JSON_PROPERTY_SUBDOMAIN,
    BrandObject.JSON_PROPERTY_ACTIVE,
    BrandObject.JSON_PROPERTY_BRAND_URL,
    BrandObject.JSON_PROPERTY_CREATED_AT,
    BrandObject.JSON_PROPERTY_DEFAULT,
    BrandObject.JSON_PROPERTY_HAS_HELP_CENTER,
    BrandObject.JSON_PROPERTY_HELP_CENTER_STATE,
    BrandObject.JSON_PROPERTY_HOST_MAPPING,
    BrandObject.JSON_PROPERTY_ID,
    BrandObject.JSON_PROPERTY_IS_DELETED,
    BrandObject.JSON_PROPERTY_LOGO,
    BrandObject.JSON_PROPERTY_SIGNATURE_TEMPLATE,
    BrandObject.JSON_PROPERTY_TICKET_FORM_IDS,
    BrandObject.JSON_PROPERTY_UPDATED_AT,
    BrandObject.JSON_PROPERTY_URL,
})
@Serdeable
public class BrandObject {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_SUBDOMAIN = "subdomain";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_BRAND_URL = "brand_url";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_HAS_HELP_CENTER = "has_help_center";
    public static final String JSON_PROPERTY_HELP_CENTER_STATE = "help_center_state";
    public static final String JSON_PROPERTY_HOST_MAPPING = "host_mapping";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IS_DELETED = "is_deleted";
    public static final String JSON_PROPERTY_LOGO = "logo";
    public static final String JSON_PROPERTY_SIGNATURE_TEMPLATE = "signature_template";
    public static final String JSON_PROPERTY_TICKET_FORM_IDS = "ticket_form_ids";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The name of the brand</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>The subdomain of the brand</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_SUBDOMAIN)
    private String subdomain;

    /**
     * <p>If the brand is set as active</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>The url of the brand</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BRAND_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String brandUrl;

    /**
     * <p>The time the brand was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Is the brand the default brand for this account</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _default;

    /**
     * <p>If the brand has a Help Center</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HAS_HELP_CENTER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean hasHelpCenter;

    @Nullable
    @JsonProperty(JSON_PROPERTY_HELP_CENTER_STATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private BrandObjectHelpCenterState helpCenterState;

    /**
     * <p>The hostmapping to this brand, if any. Only admins view this property.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HOST_MAPPING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String hostMapping;

    /**
     * <p>The ID automatically assigned when the brand is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>If the brand object is deleted or not</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_DELETED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isDeleted;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_LOGO)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AttachmentObject logo;

    /**
     * <p>The signature template for a brand</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SIGNATURE_TEMPLATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String signatureTemplate;

    /**
     * <p>The ids of ticket forms that are available for use by a brand</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> ticketFormIds;

    /**
     * <p>The time of the last update of the brand</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of this brand</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public BrandObject(String name, String subdomain) {
        this.name = name;
        this.subdomain = subdomain;
    }

}