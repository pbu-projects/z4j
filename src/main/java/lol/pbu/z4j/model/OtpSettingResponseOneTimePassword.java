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

/**
 * OtpSettingResponseOneTimePassword
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    OtpSettingResponseOneTimePassword.JSON_PROPERTY_CONFIGURED,
    OtpSettingResponseOneTimePassword.JSON_PROPERTY_ENFORCED,
    OtpSettingResponseOneTimePassword.JSON_PROPERTY_PHONE,
    OtpSettingResponseOneTimePassword.JSON_PROPERTY_TFA_CONFIGURED,
    OtpSettingResponseOneTimePassword.JSON_PROPERTY_TIME_BASED,
    OtpSettingResponseOneTimePassword.JSON_PROPERTY_URL,
})
@Serdeable
public class OtpSettingResponseOneTimePassword {

    public static final String JSON_PROPERTY_CONFIGURED = "configured";
    public static final String JSON_PROPERTY_ENFORCED = "enforced";
    public static final String JSON_PROPERTY_PHONE = "phone";
    public static final String JSON_PROPERTY_TFA_CONFIGURED = "tfa_configured";
    public static final String JSON_PROPERTY_TIME_BASED = "time_based";
    public static final String JSON_PROPERTY_URL = "url";

    @Nullable
    @JsonProperty(JSON_PROPERTY_CONFIGURED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean configured;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ENFORCED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean enforced;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PHONE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String phone;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TFA_CONFIGURED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean tfaConfigured;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TIME_BASED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean timeBased;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}