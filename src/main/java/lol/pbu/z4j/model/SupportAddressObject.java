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
import lol.pbu.z4j.model.SupportAddressObjectCnameStatus;
import lol.pbu.z4j.model.SupportAddressObjectDnsResults;
import lol.pbu.z4j.model.SupportAddressObjectDomainVerificationStatus;
import lol.pbu.z4j.model.SupportAddressObjectForwardingStatus;
import lol.pbu.z4j.model.SupportAddressObjectSpfStatus;
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
 * SupportAddressObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    SupportAddressObject.JSON_PROPERTY_EMAIL,
    SupportAddressObject.JSON_PROPERTY_BRAND_ID,
    SupportAddressObject.JSON_PROPERTY_CNAME_STATUS,
    SupportAddressObject.JSON_PROPERTY_CREATED_AT,
    SupportAddressObject.JSON_PROPERTY_DEFAULT,
    SupportAddressObject.JSON_PROPERTY_DNS_RESULTS,
    SupportAddressObject.JSON_PROPERTY_DOMAIN_VERIFICATION_CODE,
    SupportAddressObject.JSON_PROPERTY_DOMAIN_VERIFICATION_STATUS,
    SupportAddressObject.JSON_PROPERTY_FORWARDING_STATUS,
    SupportAddressObject.JSON_PROPERTY_ID,
    SupportAddressObject.JSON_PROPERTY_NAME,
    SupportAddressObject.JSON_PROPERTY_SPF_STATUS,
    SupportAddressObject.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
public class SupportAddressObject {

    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_BRAND_ID = "brand_id";
    public static final String JSON_PROPERTY_CNAME_STATUS = "cname_status";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_DNS_RESULTS = "dns_results";
    public static final String JSON_PROPERTY_DOMAIN_VERIFICATION_CODE = "domain_verification_code";
    public static final String JSON_PROPERTY_DOMAIN_VERIFICATION_STATUS = "domain_verification_status";
    public static final String JSON_PROPERTY_FORWARDING_STATUS = "forwarding_status";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_SPF_STATUS = "spf_status";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    /**
     * <p>The email address. You can't change the email address of an existing support address.</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_EMAIL)
    private String email;

    /**
     * <p>The ID of the <a href=\"/api-reference/ticketing/account-configuration/brands/\">brand</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BRAND_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer brandId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CNAME_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SupportAddressObjectCnameStatus cnameStatus;

    /**
     * <p>When the address was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Whether the address is the account's default support address</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _default;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DNS_RESULTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SupportAddressObjectDnsResults dnsResults;

    /**
     * <p>Verification string to be added as a TXT record to the domain. Possible types: string or null.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DOMAIN_VERIFICATION_CODE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String domainVerificationCode;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DOMAIN_VERIFICATION_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SupportAddressObjectDomainVerificationStatus domainVerificationStatus;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FORWARDING_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SupportAddressObjectForwardingStatus forwardingStatus;

    /**
     * <p>Automatically assigned when created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The name for the address</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SPF_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SupportAddressObjectSpfStatus spfStatus;

    /**
     * <p>When the address was updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    public SupportAddressObject(String email) {
        this.email = email;
    }

}