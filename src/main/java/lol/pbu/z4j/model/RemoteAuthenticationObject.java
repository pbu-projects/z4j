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
import lol.pbu.z4j.model.RemoteAuthenticationObjectAuthFlow;
import lol.pbu.z4j.model.RemoteAuthenticationObjectAuthMode;
import lol.pbu.z4j.model.RemoteAuthenticationObjectAuthModeName;
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
 * RemoteAuthenticationObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    RemoteAuthenticationObject.JSON_PROPERTY_AGENT,
    RemoteAuthenticationObject.JSON_PROPERTY_AGENT_PRIMARY,
    RemoteAuthenticationObject.JSON_PROPERTY_AUTH_MODE,
    RemoteAuthenticationObject.JSON_PROPERTY_CAN_DISPLAY_BUTTON_TO_END_USERS,
    RemoteAuthenticationObject.JSON_PROPERTY_CAN_DISPLAY_BUTTON_TO_TEAM_MEMBERS,
    RemoteAuthenticationObject.JSON_PROPERTY_END_USER,
    RemoteAuthenticationObject.JSON_PROPERTY_END_USER_PRIMARY,
    RemoteAuthenticationObject.JSON_PROPERTY_NAME,
    RemoteAuthenticationObject.JSON_PROPERTY_REMOTE_LOGIN_URL,
    RemoteAuthenticationObject.JSON_PROPERTY_REMOTE_LOGOUT_URL,
    RemoteAuthenticationObject.JSON_PROPERTY_AUTH_FLOW,
    RemoteAuthenticationObject.JSON_PROPERTY_AUTH_MODE_NAME,
    RemoteAuthenticationObject.JSON_PROPERTY_AUTH_URL,
    RemoteAuthenticationObject.JSON_PROPERTY_AUTO_DISCOVERY,
    RemoteAuthenticationObject.JSON_PROPERTY_CLIENT_ID,
    RemoteAuthenticationObject.JSON_PROPERTY_FINGERPRINT,
    RemoteAuthenticationObject.JSON_PROPERTY_ID,
    RemoteAuthenticationObject.JSON_PROPERTY_IP_RANGES,
    RemoteAuthenticationObject.JSON_PROPERTY_IS_ACTIVE,
    RemoteAuthenticationObject.JSON_PROPERTY_ISSUER_URL,
    RemoteAuthenticationObject.JSON_PROPERTY_JWKS_URL,
    RemoteAuthenticationObject.JSON_PROPERTY_LABEL,
    RemoteAuthenticationObject.JSON_PROPERTY_MASKED_CLIENT_SECRET,
    RemoteAuthenticationObject.JSON_PROPERTY_MASKED_SECRET,
    RemoteAuthenticationObject.JSON_PROPERTY_PRIORITY,
    RemoteAuthenticationObject.JSON_PROPERTY_SCOPE,
    RemoteAuthenticationObject.JSON_PROPERTY_TOKEN_URL,
    RemoteAuthenticationObject.JSON_PROPERTY_UPDATE_EXTERNAL_IDS,
    RemoteAuthenticationObject.JSON_PROPERTY_USER_INFO_URL,
})
@Serdeable
public class RemoteAuthenticationObject {

    public static final String JSON_PROPERTY_AGENT = "agent";
    public static final String JSON_PROPERTY_AGENT_PRIMARY = "agent_primary";
    public static final String JSON_PROPERTY_AUTH_MODE = "auth_mode";
    public static final String JSON_PROPERTY_CAN_DISPLAY_BUTTON_TO_END_USERS = "can_display_button_to_end_users";
    public static final String JSON_PROPERTY_CAN_DISPLAY_BUTTON_TO_TEAM_MEMBERS = "can_display_button_to_team_members";
    public static final String JSON_PROPERTY_END_USER = "end_user";
    public static final String JSON_PROPERTY_END_USER_PRIMARY = "end_user_primary";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_REMOTE_LOGIN_URL = "remote_login_url";
    public static final String JSON_PROPERTY_REMOTE_LOGOUT_URL = "remote_logout_url";
    public static final String JSON_PROPERTY_AUTH_FLOW = "auth_flow";
    public static final String JSON_PROPERTY_AUTH_MODE_NAME = "auth_mode_name";
    public static final String JSON_PROPERTY_AUTH_URL = "auth_url";
    public static final String JSON_PROPERTY_AUTO_DISCOVERY = "auto_discovery";
    public static final String JSON_PROPERTY_CLIENT_ID = "client_id";
    public static final String JSON_PROPERTY_FINGERPRINT = "fingerprint";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IP_RANGES = "ip_ranges";
    public static final String JSON_PROPERTY_IS_ACTIVE = "is_active";
    public static final String JSON_PROPERTY_ISSUER_URL = "issuer_url";
    public static final String JSON_PROPERTY_JWKS_URL = "jwks_url";
    public static final String JSON_PROPERTY_LABEL = "label";
    public static final String JSON_PROPERTY_MASKED_CLIENT_SECRET = "masked_client_secret";
    public static final String JSON_PROPERTY_MASKED_SECRET = "masked_secret";
    public static final String JSON_PROPERTY_PRIORITY = "priority";
    public static final String JSON_PROPERTY_SCOPE = "scope";
    public static final String JSON_PROPERTY_TOKEN_URL = "token_url";
    public static final String JSON_PROPERTY_UPDATE_EXTERNAL_IDS = "update_external_ids";
    public static final String JSON_PROPERTY_USER_INFO_URL = "user_info_url";

    /**
     * <p>If true, the method is used for the team member remote authentication</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_AGENT)
    private Boolean agent;

    /**
     * <p>If team members for sign-in are redirected to a remote authentication, this is the default method shown to a team member</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_AGENT_PRIMARY)
    private Boolean agentPrimary;

    @NotNull
    @JsonProperty(JSON_PROPERTY_AUTH_MODE)
    private RemoteAuthenticationObjectAuthMode authMode;

    /**
     * <p>If users can choose how they sign in, this remote authentication method appears as an option when it's active</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_CAN_DISPLAY_BUTTON_TO_END_USERS)
    private Boolean canDisplayButtonToEndUsers;

    /**
     * <p>If team members can choose how they sign in, this remote authentication method appears as an option when it's active</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_CAN_DISPLAY_BUTTON_TO_TEAM_MEMBERS)
    private Boolean canDisplayButtonToTeamMembers;

    /**
     * <p>If true, the method is used for the end-user remote authentication</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_END_USER)
    private Boolean endUser;

    /**
     * <p>If end users for sign-in are redirected to a remote authentication, this is the default method shown to an end user</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_END_USER_PRIMARY)
    private Boolean endUserPrimary;

    /**
     * <p>The name of the remote configuration. It's good to use something recognizable like the identity provider's name</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>The URL that Zendesk invokes to redirect users to the identity provider</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_REMOTE_LOGIN_URL)
    private String remoteLoginUrl;

    /**
     * <p>The URL that Zendesk uses to redirect users after they sign out</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_REMOTE_LOGOUT_URL)
    private String remoteLogoutUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTH_FLOW)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private RemoteAuthenticationObjectAuthFlow authFlow;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTH_MODE_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private RemoteAuthenticationObjectAuthModeName authModeName;

    /**
     * <p>For the \"oidc\" auth mode only. The authorization endpoint to use for the request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTH_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String authUrl;

    /**
     * <p>For the \"oidc\" auth mode only. When turned on, Zendesk will automatically extract the configuration details from the OIDC Configuration Document. Only the Issuer URL and Authentication Mode need to be provided</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTO_DISCOVERY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean autoDiscovery;

    /**
     * <p>For the \"oidc\" auth mode only.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CLIENT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String clientId;

    /**
     * <p>For the \"saml\" auth mode only. The SHA-256 certificate fingerprint.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FINGERPRINT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String fingerprint;

    /**
     * <p>Uniquely identifies a remote authentication. Automatically assigned on creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>Requests from these IP ranges will always be routed via remote authentication. Requests from IP addresses outside these ranges will be routed to the normal sign-in form. When this is blank, all requests are routed through remote authentication. An IP range is in the format n.n.n.n, where n is a number or an asterisk (*) wild card. Multiple IP ranges are separated with spaces</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IP_RANGES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ipRanges;

    /**
     * <p>If true, the method is enabled for end users or team members</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isActive;

    /**
     * <p>For the \"oidc\" auth mode only. This is the URL that is used as the logical identifier for your provider's connection</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ISSUER_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String issuerUrl;

    /**
     * <p>For the \"oidc\" auth mode only. This is the URL that returns the provider's JSON Web Key Set</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_JWKS_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String jwksUrl;

    /**
     * <p>The sign-in button label</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LABEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String label;

    /**
     * <p>For the \"oidc\" auth mode only.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MASKED_CLIENT_SECRET)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String maskedClientSecret;

    /**
     * <p>For the \"jwt\" auth mode only. The token is a shared secret between you and Zendesk. It must never be publicized</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MASKED_SECRET)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String maskedSecret;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIORITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer priority;

    /**
     * <p>For the \"oidc\" auth mode only. These are the user details your account can access, like name and email address. Supported scopes within the OIDC standard include <code>openid</code>, <code>profile</code>, <code>email</code>, <code>address</code>, and <code>phone</code>. It must contain at least <code>openid</code> and <code>email</code>. Scopes are separated with spaces</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SCOPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String scope;

    /**
     * <p>For the \"oidc\" auth mode only. Your account uses this URL to request access tokens for users</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TOKEN_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String tokenUrl;

    /**
     * <p>For the \"jwt\" auth mode only. When enabled, the external id of the user being signed in can be updated. This only happens when a user with the external id is not found, but the user's email address is found. The external id is unique for an account. Users without an external id will have one added if it is present in the authentication request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATE_EXTERNAL_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean updateExternalIds;

    /**
     * <p>For the \"oidc\" auth mode only. This the URL that returns Claims about the authenticated user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_INFO_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String userInfoUrl;

    public RemoteAuthenticationObject(Boolean agent, Boolean agentPrimary, RemoteAuthenticationObjectAuthMode authMode, Boolean canDisplayButtonToEndUsers, Boolean canDisplayButtonToTeamMembers, Boolean endUser, Boolean endUserPrimary, String name, String remoteLoginUrl, String remoteLogoutUrl) {
        this.agent = agent;
        this.agentPrimary = agentPrimary;
        this.authMode = authMode;
        this.canDisplayButtonToEndUsers = canDisplayButtonToEndUsers;
        this.canDisplayButtonToTeamMembers = canDisplayButtonToTeamMembers;
        this.endUser = endUser;
        this.endUserPrimary = endUserPrimary;
        this.name = name;
        this.remoteLoginUrl = remoteLoginUrl;
        this.remoteLogoutUrl = remoteLogoutUrl;
    }

}