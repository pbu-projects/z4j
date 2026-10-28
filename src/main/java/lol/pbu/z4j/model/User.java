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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User object in the Zendesk Support Api
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        User.JSON_PROPERTY_ID,
        User.JSON_PROPERTY_AGENT_BRAND_IDS,
        User.JSON_PROPERTY_EMAIL,
        User.JSON_PROPERTY_NAME,
        User.JSON_PROPERTY_ROLE,
        User.JSON_PROPERTY_ACTIVE,
        User.JSON_PROPERTY_ALIAS,
        User.JSON_PROPERTY_CHAT_ONLY,
        User.JSON_PROPERTY_CUSTOM_ROLE_ID,
        User.JSON_PROPERTY_DEFAULT_GROUP_ID,
        User.JSON_PROPERTY_DETAILS,
        User.JSON_PROPERTY_EXTERNAL_ID,
        User.JSON_PROPERTY_IANA_TIME_ZONE,
        User.JSON_PROPERTY_LAST_LOGIN_AT,
        User.JSON_PROPERTY_LOCALE,
        User.JSON_PROPERTY_LOCALE_ID,
        User.JSON_PROPERTY_MODERATOR,
        User.JSON_PROPERTY_NOTES,
        User.JSON_PROPERTY_ONLY_PRIVATE_COMMENTS,
        User.JSON_PROPERTY_ORGANIZATION_ID,
        User.JSON_PROPERTY_PHONE,
        User.JSON_PROPERTY_PHOTO,
        User.JSON_PROPERTY_REMOTE_PHOTO_URL,
        User.JSON_PROPERTY_REPORT_CSV,
        User.JSON_PROPERTY_RESTRICTED_AGENT,
        User.JSON_PROPERTY_ROLE_TYPE,
        User.JSON_PROPERTY_SHARED,
        User.JSON_PROPERTY_SHARED_AGENT,
        User.JSON_PROPERTY_SHARED_PHONE_NUMBER,
        User.JSON_PROPERTY_SIGNATURE,
        User.JSON_PROPERTY_SUSPENDED,
        User.JSON_PROPERTY_TAGS,
        User.JSON_PROPERTY_TICKET_RESTRICTION,
        User.JSON_PROPERTY_TIME_ZONE,
        User.JSON_PROPERTY_TWO_FACTOR_AUTH_ENABLED,
        User.JSON_PROPERTY_URL,
        User.JSON_PROPERTY_USER_FIELDS,
        User.JSON_PROPERTY_VERIFIED,
        User.JSON_PROPERTY_AUTHENTICITY_TOKEN,
        User.JSON_PROPERTY_CREATED_AT,
        User.JSON_PROPERTY_UPDATED_AT
})
@Serdeable
@Generated
public class User implements Exportable {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_AGENT_BRAND_IDS = "agent_brand_ids";
    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_ROLE = "role";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_ALIAS = "alias";
    public static final String JSON_PROPERTY_CHAT_ONLY = "chat_only";
    public static final String JSON_PROPERTY_CUSTOM_ROLE_ID = "custom_role_id";
    public static final String JSON_PROPERTY_DEFAULT_GROUP_ID = "default_group_id";
    public static final String JSON_PROPERTY_DETAILS = "details";
    public static final String JSON_PROPERTY_EXTERNAL_ID = "external_id";
    public static final String JSON_PROPERTY_IANA_TIME_ZONE = "iana_time_zone";
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
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_FIELDS = "user_fields";
    public static final String JSON_PROPERTY_VERIFIED = "verified";
    public static final String JSON_PROPERTY_AUTHENTICITY_TOKEN = "authenticity_token";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_BRAND_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Long> agentBrandIds;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String email;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String name;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ROLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String role;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ALIAS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String alias;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CHAT_ONLY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean chatOnly;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_ROLE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long customRoleId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT_GROUP_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long defaultGroupId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DETAILS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String details;

    @Nullable
    @JsonProperty(JSON_PROPERTY_EXTERNAL_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String externalId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_IANA_TIME_ZONE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ianaTimeZone;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LAST_LOGIN_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime lastLoginAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String locale;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long localeId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MODERATOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean moderator;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NOTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String notes;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ONLY_PRIVATE_COMMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean onlyPrivateComments;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long organizationId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PHONE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String phone;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_PHOTO)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Attachment photo;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REMOTE_PHOTO_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String remotePhotoUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REPORT_CSV)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean reportCsv;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RESTRICTED_AGENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean restrictedAgent;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ROLE_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer roleType;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean shared;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED_AGENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sharedAgent;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED_PHONE_NUMBER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sharedPhoneNumber;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SIGNATURE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String signature;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUSPENDED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean suspended;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<String> tags;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_RESTRICTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ticketRestriction;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TIME_ZONE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String timeZone;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TWO_FACTOR_AUTH_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean twoFactorAuthEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> userFields;

    @Nullable
    @JsonProperty(JSON_PROPERTY_VERIFIED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean verified;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTHENTICITY_TOKEN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String authenticityToken;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    public User addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }
}
