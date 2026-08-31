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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
 * UserForAdmin
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    UserForAdmin.JSON_PROPERTY_NAME,
    UserForAdmin.JSON_PROPERTY_ACTIVE,
    UserForAdmin.JSON_PROPERTY_AGENT_BRAND_IDS,
    UserForAdmin.JSON_PROPERTY_ALIAS,
    UserForAdmin.JSON_PROPERTY_CHAT_ONLY,
    UserForAdmin.JSON_PROPERTY_CREATED_AT,
    UserForAdmin.JSON_PROPERTY_CUSTOM_ROLE_ID,
    UserForAdmin.JSON_PROPERTY_DEFAULT_GROUP_ID,
    UserForAdmin.JSON_PROPERTY_DETAILS,
    UserForAdmin.JSON_PROPERTY_EMAIL,
    UserForAdmin.JSON_PROPERTY_EXTERNAL_ID,
    UserForAdmin.JSON_PROPERTY_IANA_TIME_ZONE,
    UserForAdmin.JSON_PROPERTY_ID,
    UserForAdmin.JSON_PROPERTY_LAST_LOGIN_AT,
    UserForAdmin.JSON_PROPERTY_LOCALE,
    UserForAdmin.JSON_PROPERTY_LOCALE_ID,
    UserForAdmin.JSON_PROPERTY_MODERATOR,
    UserForAdmin.JSON_PROPERTY_NOTES,
    UserForAdmin.JSON_PROPERTY_ONLY_PRIVATE_COMMENTS,
    UserForAdmin.JSON_PROPERTY_ORGANIZATION_ID,
    UserForAdmin.JSON_PROPERTY_PHONE,
    UserForAdmin.JSON_PROPERTY_PHOTO,
    UserForAdmin.JSON_PROPERTY_REMOTE_PHOTO_URL,
    UserForAdmin.JSON_PROPERTY_REPORT_CSV,
    UserForAdmin.JSON_PROPERTY_RESTRICTED_AGENT,
    UserForAdmin.JSON_PROPERTY_ROLE,
    UserForAdmin.JSON_PROPERTY_ROLE_TYPE,
    UserForAdmin.JSON_PROPERTY_SHARED,
    UserForAdmin.JSON_PROPERTY_SHARED_AGENT,
    UserForAdmin.JSON_PROPERTY_SHARED_PHONE_NUMBER,
    UserForAdmin.JSON_PROPERTY_SIGNATURE,
    UserForAdmin.JSON_PROPERTY_SUSPENDED,
    UserForAdmin.JSON_PROPERTY_TAGS,
    UserForAdmin.JSON_PROPERTY_TICKET_RESTRICTION,
    UserForAdmin.JSON_PROPERTY_TIME_ZONE,
    UserForAdmin.JSON_PROPERTY_TWO_FACTOR_AUTH_ENABLED,
    UserForAdmin.JSON_PROPERTY_UPDATED_AT,
    UserForAdmin.JSON_PROPERTY_URL,
    UserForAdmin.JSON_PROPERTY_USER_FIELDS,
    UserForAdmin.JSON_PROPERTY_VERIFIED,
})
@Serdeable
public class UserForAdmin {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_AGENT_BRAND_IDS = "agent_brand_ids";
    public static final String JSON_PROPERTY_ALIAS = "alias";
    public static final String JSON_PROPERTY_CHAT_ONLY = "chat_only";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CUSTOM_ROLE_ID = "custom_role_id";
    public static final String JSON_PROPERTY_DEFAULT_GROUP_ID = "default_group_id";
    public static final String JSON_PROPERTY_DETAILS = "details";
    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_IANA_TIME_ZONE = "iana_time_zone";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_LAST_LOGIN_AT = "last_login_at";
    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_LOCALE_ID = "locale_id";
    public static final String JSON_PROPERTY_MODERATOR = "moderator";
    public static final String JSON_PROPERTY_NOTES = "notes";
    public static final String JSON_PROPERTY_ONLY_PRIVATE_COMMENTS = "only_private_comments";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_PHONE = "phone";
    public static final String JSON_PROPERTY_PHOTO = "photo";
    public static final String JSON_PROPERTY_REMOTE_PHOTO_URL = "remote_photo_url";
    public static final String JSON_PROPERTY_REPORT_CSV = "report_csv";
    public static final String JSON_PROPERTY_RESTRICTED_AGENT = "restricted_agent";
    public static final String JSON_PROPERTY_ROLE = "role";
    public static final String JSON_PROPERTY_ROLE_TYPE = "role_type";
    public static final String JSON_PROPERTY_SHARED = "shared";
    public static final String JSON_PROPERTY_SHARED_AGENT = "shared_agent";
    public static final String JSON_PROPERTY_SHARED_PHONE_NUMBER = "shared_phone_number";
    public static final String JSON_PROPERTY_SIGNATURE = "signature";
    public static final String JSON_PROPERTY_SUSPENDED = "suspended";
    public static final String JSON_PROPERTY_TAGS = "tags";
    public static final String JSON_PROPERTY_TICKET_RESTRICTION = "ticket_restriction";
    public static final String JSON_PROPERTY_TIME_ZONE = "time_zone";
    public static final String JSON_PROPERTY_TWO_FACTOR_AUTH_ENABLED = "two_factor_auth_enabled";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_FIELDS = "user_fields";
    public static final String JSON_PROPERTY_VERIFIED = "verified";

    /**
     * <p>The user's name</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>false if the user has been deleted</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>PUT or POST requests only. Assigns agent or agents to a brand. For more information, see <a href=\"#agent-brand-ids\">Agent brand ids</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_BRAND_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> agentBrandIds;

    /**
     * <p>An alias displayed to end users</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ALIAS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String alias;

    /**
     * <p>Whether or not the user is a chat-only agent</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CHAT_ONLY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean chatOnly;

    /**
     * <p>The time the user was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * <p>A custom role if the user is an agent on the Enterprise plan or above</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_ROLE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long customRoleId;

    /**
     * <p>The id of the user's default group</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long defaultGroupId;

    /**
     * <p>Any details you want to store about the user, such as an address</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DETAILS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String details;

    /**
     * <p>The user's primary email address. *Writeable on create only. On update, a secondary email is added. See <a href=\"#email-address\">Email Address</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String email;

    /**
     * <p>A unique identifier from another system. The API treats the id as case insensitive. Example: \"ian1\" and \"IAN1\" are the same value.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    /**
     * <p>The time zone for the user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IANA_TIME_ZONE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ianaTimeZone;

    /**
     * <p>Automatically assigned when the user is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>Last time the user signed in to Zendesk Support or made an API request using an API token</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LAST_LOGIN_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String lastLoginAt;

    /**
     * <p>The user's locale. A BCP-47 compliant tag for the locale. If both \"locale\" and \"locale_id\" are present on create or update, \"locale_id\" is ignored and only \"locale\" is used.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String locale;

    /**
     * <p>The user's language identifier</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long localeId;

    /**
     * <p>Designates whether the user has forum moderation capabilities</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MODERATOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean moderator;

    /**
     * <p>Any notes you want to store about the user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NOTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String notes;

    /**
     * <p>true if the user can only create private comments</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ONLY_PRIVATE_COMMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean onlyPrivateComments;

    /**
     * <p>The id of the user's organization. If the user has more than one <a href=\"/api-reference/ticketing/organizations/organization_memberships/\">organization memberships</a>, the id of the user's default organization. If updating, see <a href=\"#organization-id\">Organization ID</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long organizationId;

    /**
     * <p>The user's primary phone number. See <a href=\"#phone-number\">Phone Number</a> below</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PHONE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String phone;

    /**
     * <p>The user's profile picture represented as an <a href=\"/api-reference/ticketing/tickets/ticket-attachments/\">Attachment</a> object</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PHOTO)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> photo;

    /**
     * <p>A URL pointing to the user's profile picture.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REMOTE_PHOTO_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String remotePhotoUrl;

    /**
     * <p>This parameter is inert and has no effect. It may be deprecated in the future.</p> <p>Previously, this parameter determined whether a user could access a CSV report in a legacy Guide dashboard. This dashboard has been removed. See <a href=\"https://support.zendesk.com/hc/en-us/articles/4762263171610-Announcing-Guide-legacy-reporting-upgrade-to-Explore-\">Announcing Guide legacy reporting upgrade to Explore</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REPORT_CSV)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean reportCsv;

    /**
     * <p>If the agent has any restrictions; false for admins and unrestricted agents, true for other agents</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RESTRICTED_AGENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean restrictedAgent;

    /**
     * <p>The user's role. Possible values are \"end-user\", \"agent\", or \"admin\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ROLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String role;

    /**
     * <p>The user's role id. 0 for a custom agent, 1 for a light agent, 2 for a chat agent, 3 for a chat agent added to the Support account as a contributor (<a href=\"https://support.zendesk.com/hc/en-us/articles/360022365373#topic_djh_1zk_4fb\">Chat Phase 4</a>), 4 for an admin, and 5 for a billing admin</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ROLE_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long roleType;

    /**
     * <p>If the user is shared from a different Zendesk Support instance. Shared users can be added to organizations but cannot be modified through update requests. Any attempt to update a shared user results in a 403 Forbidden error. Ticket sharing accounts only</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean shared;

    /**
     * <p>If the user is a shared agent from a different Zendesk Support instance. Ticket sharing accounts only</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED_AGENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sharedAgent;

    /**
     * <p>Whether the <code>phone</code> number is shared or not. See <a href=\"#phone-number\">Phone Number</a> below</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED_PHONE_NUMBER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sharedPhoneNumber;

    /**
     * <p>The user's signature. Only agents and admins can have signatures</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SIGNATURE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String signature;

    /**
     * <p>If the agent is suspended. Tickets from suspended users are also suspended, and these users cannot sign in to the end user portal</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUSPENDED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean suspended;

    /**
     * <p>The user's tags. Only present if your account has user tagging enabled</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> tags;

    /**
     * <p>Specifies which tickets the user has access to. Possible values are: \"organization\", \"groups\", \"assigned\", \"requested\", null. \"groups\" and \"assigned\" are valid only for agents. If you pass an invalid value to an end user (for example, \"groups\"), they will be assigned to \"requested\", regardless of their previous access</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_RESTRICTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ticketRestriction;

    /**
     * <p>The user's time zone. See <a href=\"#time-zone\">Time Zone</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TIME_ZONE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String timeZone;

    /**
     * <p>If two factor authentication is enabled</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TWO_FACTOR_AUTH_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean twoFactorAuthEnabled;

    /**
     * <p>The time the user was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * <p>The user's API url</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * <p>Values of custom fields in the user's profile. See <a href=\"#user-fields\">User Fields</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_FIELDS)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> userFields;

    /**
     * <p>Any of the user's identities is verified. See <a href=\"/api-reference/ticketing/users/user_identities\">User Identities</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VERIFIED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean verified;

    public UserForAdmin(String name) {
        this.name = name;
    }

    /**
     * Add an item to the agentBrandIds property in a chainable fashion.
     *
     * @return The same instance of UserForAdmin for chaining.
     */
    public UserForAdmin addAgentBrandIdsItem(Long agentBrandIdsItem) {
        if (agentBrandIds == null) {
            agentBrandIds = new ArrayList<>();
        }
        agentBrandIds.add(agentBrandIdsItem);
        return this;
    }

    /**
     * Set the value for the key for the photo map property in a chainable fashion.
     *
     * @return The same instance of UserForAdmin for chaining.
     */
    public UserForAdmin putPhotoItem(String key, Object photoItem) {
        if (photo == null) {
            photo = new HashMap<>();
        }
        photo.put(key, photoItem);
        return this;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of UserForAdmin for chaining.
     */
    public UserForAdmin addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }

    /**
     * Set the value for the key for the userFields map property in a chainable fashion.
     *
     * @return The same instance of UserForAdmin for chaining.
     */
    public UserForAdmin putUserFieldsItem(String key, Object userFieldsItem) {
        if (userFields == null) {
            userFields = new HashMap<>();
        }
        userFields.put(key, userFieldsItem);
        return this;
    }

}