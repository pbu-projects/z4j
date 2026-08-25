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
import lol.pbu.z4j.model.SecuritySettingsObjectAssumptionDuration;
import lol.pbu.z4j.model.SecuritySettingsObjectAuthentication;
import lol.pbu.z4j.model.SecuritySettingsObjectIp;
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
 * SecuritySettingsObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SecuritySettingsObject.JSON_PROPERTY_ADMINS_CAN_SET_USER_PASSWORDS,
    SecuritySettingsObject.JSON_PROPERTY_AGENT_SESSION_TIMEOUT,
    SecuritySettingsObject.JSON_PROPERTY_ASSUMABLE,
    SecuritySettingsObject.JSON_PROPERTY_ASSUMABLE_ACCOUNT_TYPE,
    SecuritySettingsObject.JSON_PROPERTY_ASSUMPTION_DURATION,
    SecuritySettingsObject.JSON_PROPERTY_ASSUMPTION_EXPIRATION,
    SecuritySettingsObject.JSON_PROPERTY_AUTHENTICATION,
    SecuritySettingsObject.JSON_PROPERTY_CSP_BLOCKING_ENABLED,
    SecuritySettingsObject.JSON_PROPERTY_EMAIL_AGENT_WHEN_SENSITIVE_FIELDS_CHANGED,
    SecuritySettingsObject.JSON_PROPERTY_END_USER_SESSION_TIMEOUT,
    SecuritySettingsObject.JSON_PROPERTY_IP,
    SecuritySettingsObject.JSON_PROPERTY_MAXIMUM_SESSION_DURATION,
    SecuritySettingsObject.JSON_PROPERTY_MAXIMUM_SESSION_DURATION_ENABLED,
    SecuritySettingsObject.JSON_PROPERTY_MOBILE_APP_ACCESS,
    SecuritySettingsObject.JSON_PROPERTY_MOBILE_APP_SESSION_TIMEOUT,
    SecuritySettingsObject.JSON_PROPERTY_TWO_FACTOR_LAST_UPDATE,
})
@Serdeable
public class SecuritySettingsObject {

    public static final String JSON_PROPERTY_ADMINS_CAN_SET_USER_PASSWORDS = "admins_can_set_user_passwords";
    public static final String JSON_PROPERTY_AGENT_SESSION_TIMEOUT = "agent_session_timeout";
    public static final String JSON_PROPERTY_ASSUMABLE = "assumable";
    public static final String JSON_PROPERTY_ASSUMABLE_ACCOUNT_TYPE = "assumable_account_type";
    public static final String JSON_PROPERTY_ASSUMPTION_DURATION = "assumption_duration";
    public static final String JSON_PROPERTY_ASSUMPTION_EXPIRATION = "assumption_expiration";
    public static final String JSON_PROPERTY_AUTHENTICATION = "authentication";
    public static final String JSON_PROPERTY_CSP_BLOCKING_ENABLED = "csp_blocking_enabled";
    public static final String JSON_PROPERTY_EMAIL_AGENT_WHEN_SENSITIVE_FIELDS_CHANGED = "email_agent_when_sensitive_fields_changed";
    public static final String JSON_PROPERTY_END_USER_SESSION_TIMEOUT = "end_user_session_timeout";
    public static final String JSON_PROPERTY_IP = "ip";
    public static final String JSON_PROPERTY_MAXIMUM_SESSION_DURATION = "maximum_session_duration";
    public static final String JSON_PROPERTY_MAXIMUM_SESSION_DURATION_ENABLED = "maximum_session_duration_enabled";
    public static final String JSON_PROPERTY_MOBILE_APP_ACCESS = "mobile_app_access";
    public static final String JSON_PROPERTY_MOBILE_APP_SESSION_TIMEOUT = "mobile_app_session_timeout";
    public static final String JSON_PROPERTY_TWO_FACTOR_LAST_UPDATE = "two_factor_last_update";

    /**
     * <p>If administrators are allowed to set passwords for users. When disabled, administrators can only reset passwords</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ADMINS_CAN_SET_USER_PASSWORDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean adminsCanSetUserPasswords;

    /**
     * <p>The period of inactivity in minutes, before a team member is automatically signed out</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_SESSION_TIMEOUT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer agentSessionTimeout;

    /**
     * <p>If account assumption is enabled</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSUMABLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean assumable;

    /**
     * <p>Indicates if an account is always assumable, based on account type (e.g. always true for a trial account)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSUMABLE_ACCOUNT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean assumableAccountType;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSUMPTION_DURATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SecuritySettingsObjectAssumptionDuration assumptionDuration;

    /**
     * <p>The time when assumption option expires</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSUMPTION_EXPIRATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime assumptionExpiration;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_AUTHENTICATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SecuritySettingsObjectAuthentication authentication;

    /**
     * <p>If Content Security Policy blocking is enabled</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CSP_BLOCKING_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean cspBlockingEnabled;

    /**
     * <p>If a notification is sent on password change for admins, agents and end users</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_AGENT_WHEN_SENSITIVE_FIELDS_CHANGED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean emailAgentWhenSensitiveFieldsChanged;

    /**
     * <p>The period of inactivity in minutes, before an end user is automatically signed out</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_SESSION_TIMEOUT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer endUserSessionTimeout;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_IP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SecuritySettingsObjectIp ip;

    /**
     * <p>The maximum session duration, which is the maximum amount of time in minutes a team member can stay signed in. The session will expire after this duration or the inactivity timeout</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MAXIMUM_SESSION_DURATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer maximumSessionDuration;

    /**
     * <p>If maximum session duration for team members is enabled</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MAXIMUM_SESSION_DURATION_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean maximumSessionDurationEnabled;

    /**
     * <p>If admins and agents can use the Zendesk Support mobile app</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MOBILE_APP_ACCESS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean mobileAppAccess;

    /**
     * <p>The period of inactivity in minutes, before a mobile app user gets signed out</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MOBILE_APP_SESSION_TIMEOUT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer mobileAppSessionTimeout;

    /**
     * <p>The time when the two-factor authentication setting was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TWO_FACTOR_LAST_UPDATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime twoFactorLastUpdate;

}