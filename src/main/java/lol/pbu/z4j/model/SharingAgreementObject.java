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
 * SharingAgreementObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SharingAgreementObject.JSON_PROPERTY_CREATED_AT,
    SharingAgreementObject.JSON_PROPERTY_ID,
    SharingAgreementObject.JSON_PROPERTY_NAME,
    SharingAgreementObject.JSON_PROPERTY_PARTNER_NAME,
    SharingAgreementObject.JSON_PROPERTY_REMOTE_SUBDOMAIN,
    SharingAgreementObject.JSON_PROPERTY_STATUS,
    SharingAgreementObject.JSON_PROPERTY_TYPE,
    SharingAgreementObject.JSON_PROPERTY_UPDATED_AT,
    SharingAgreementObject.JSON_PROPERTY_URL,
})
@Serdeable
public class SharingAgreementObject {

    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_PARTNER_NAME = "partner_name";
    public static final String JSON_PROPERTY_REMOTE_SUBDOMAIN = "remote_subdomain";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The time the record was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>Name of this sharing agreement</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    /**
     * <p>Can be one of the following: \"jira\", null</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PARTNER_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String partnerName;

    /**
     * <p>Subdomain of the remote account or null if not associated with an account</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REMOTE_SUBDOMAIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String remoteSubdomain;

    /**
     * <p>Can be one of the following: \"accepted\", \"declined\", \"pending\", \"inactive\", \"failed\", \"ssl_error\", \"configuration_error\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String status;

    /**
     * <p>Can be one of the following: \"inbound\", \"outbound\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

    /**
     * <p>The time the record was updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>URL of the sharing agreement record</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}