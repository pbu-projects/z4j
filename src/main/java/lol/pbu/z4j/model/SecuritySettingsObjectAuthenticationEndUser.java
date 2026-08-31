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
import lol.pbu.z4j.model.RoleAuthenticationObjectSecurityPolicyName;
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
 * SecuritySettingsObjectAuthenticationEndUser
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_ENFORCE_SSO,
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_GOOGLE_LOGIN,
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_OFFICE365_LOGIN,
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_PRIMARY_EXTERNAL_AUTH,
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_REMOTE_LOGIN,
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_SECURITY_POLICY_ID,
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_SECURITY_POLICY_NAME,
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_SSO_AUTO_REDIRECT,
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_TWO_FACTOR_ENFORCE,
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_ZENDESK_LOGIN,
    SecuritySettingsObjectAuthenticationEndUser.JSON_PROPERTY_FACEBOOK_LOGIN,
})
@Serdeable
public class SecuritySettingsObjectAuthenticationEndUser {

    public static final String JSON_PROPERTY_ENFORCE_SSO = "enforce_sso";
    public static final String JSON_PROPERTY_GOOGLE_LOGIN = "google_login";
    public static final String JSON_PROPERTY_OFFICE365_LOGIN = "office_365_login";
    public static final String JSON_PROPERTY_PRIMARY_EXTERNAL_AUTH = "primary_external_auth";
    public static final String JSON_PROPERTY_REMOTE_LOGIN = "remote_login";
    public static final String JSON_PROPERTY_SECURITY_POLICY_ID = "security_policy_id";
    public static final String JSON_PROPERTY_SECURITY_POLICY_NAME = "security_policy_name";
    public static final String JSON_PROPERTY_SSO_AUTO_REDIRECT = "sso_auto_redirect";
    public static final String JSON_PROPERTY_TWO_FACTOR_ENFORCE = "two_factor_enforce";
    public static final String JSON_PROPERTY_ZENDESK_LOGIN = "zendesk_login";
    public static final String JSON_PROPERTY_FACEBOOK_LOGIN = "facebook_login";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ENFORCE_SSO)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean enforceSso;

    @Nullable
    @JsonProperty(JSON_PROPERTY_GOOGLE_LOGIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean googleLogin;

    @Nullable
    @JsonProperty(JSON_PROPERTY_OFFICE365_LOGIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean office365Login;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIMARY_EXTERNAL_AUTH)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String primaryExternalAuth;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REMOTE_LOGIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean remoteLogin;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SECURITY_POLICY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long securityPolicyId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SECURITY_POLICY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private RoleAuthenticationObjectSecurityPolicyName securityPolicyName;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SSO_AUTO_REDIRECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ssoAutoRedirect;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TWO_FACTOR_ENFORCE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean twoFactorEnforce;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ZENDESK_LOGIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean zendeskLogin;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FACEBOOK_LOGIN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean facebookLogin;

}