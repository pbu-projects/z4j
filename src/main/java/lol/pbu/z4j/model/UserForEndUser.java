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
import java.util.HashMap;
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
 * UserForEndUser
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    UserForEndUser.JSON_PROPERTY_NAME,
    UserForEndUser.JSON_PROPERTY_CREATED_AT,
    UserForEndUser.JSON_PROPERTY_EMAIL,
    UserForEndUser.JSON_PROPERTY_IANA_TIME_ZONE,
    UserForEndUser.JSON_PROPERTY_ID,
    UserForEndUser.JSON_PROPERTY_LOCALE,
    UserForEndUser.JSON_PROPERTY_LOCALE_ID,
    UserForEndUser.JSON_PROPERTY_ORGANIZATION_ID,
    UserForEndUser.JSON_PROPERTY_PHONE,
    UserForEndUser.JSON_PROPERTY_PHOTO,
    UserForEndUser.JSON_PROPERTY_ROLE,
    UserForEndUser.JSON_PROPERTY_SHARED_PHONE_NUMBER,
    UserForEndUser.JSON_PROPERTY_TIME_ZONE,
    UserForEndUser.JSON_PROPERTY_UPDATED_AT,
    UserForEndUser.JSON_PROPERTY_URL,
    UserForEndUser.JSON_PROPERTY_VERIFIED,
})
@Serdeable
public class UserForEndUser {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_EMAIL = "email";
    public static final String JSON_PROPERTY_IANA_TIME_ZONE = "iana_time_zone";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_LOCALE_ID = "locale_id";
    public static final String JSON_PROPERTY_ORGANIZATION_ID = "organization_id";
    public static final String JSON_PROPERTY_PHONE = "phone";
    public static final String JSON_PROPERTY_PHOTO = "photo";
    public static final String JSON_PROPERTY_ROLE = "role";
    public static final String JSON_PROPERTY_SHARED_PHONE_NUMBER = "shared_phone_number";
    public static final String JSON_PROPERTY_TIME_ZONE = "time_zone";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VERIFIED = "verified";

    /**
     * <p>The name of the user</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>The time the user was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * <p>The primary email address of this user. If the primary email address is not <a href=\"https://support.zendesk.com/hc/en-us/articles/4408886752410\">verified</a>, the secondary email address is used</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String email;

    /**
     * <p>The time zone for the user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IANA_TIME_ZONE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ianaTimeZone;

    /**
     * <p>Automatically assigned when creating users</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The locale for this user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String locale;

    /**
     * <p>The language identifier for this user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer localeId;

    /**
     * <p>The id of the user's organization. If the user has more than one <a href=\"/api-reference/ticketing/organizations/organization_memberships/\">organization memberships</a>, the id of the user's default organization. If updating, see <a href=\"/api-reference/ticketing/users/users/#organization-id\">Organization ID</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer organizationId;

    /**
     * <p>The primary phone number of this user. See <a href=\"/api-reference/ticketing/users/users/#phone-number\">Phone Number</a> in the Users API</p>
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
     * <p>The role of the user. Possible values: <code>\"end-user\"</code>, <code>\"agent\"</code>, <code>\"admin\"</code></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ROLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String role;

    /**
     * <p>Whether the <code>phone</code> number is shared or not. See <a href=\"/api-reference/ticketing/users/users/#phone-number\">Phone Number</a> in the Users API</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SHARED_PHONE_NUMBER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean sharedPhoneNumber;

    /**
     * <p>The time-zone of this user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TIME_ZONE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String timeZone;

    /**
     * <p>The time of the last update of the user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * <p>The API url of this user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * <p>Any of the user's identities is verified. See <a href=\"/api-reference/ticketing/users/user_identities\">User Identities</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VERIFIED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean verified;

    public UserForEndUser(String name) {
        this.name = name;
    }

    /**
     * Set the value for the key for the photo map property in a chainable fashion.
     *
     * @return The same instance of UserForEndUser for chaining.
     */
    public UserForEndUser putPhotoItem(String key, Object photoItem) {
        if (photo == null) {
            photo = new HashMap<>();
        }
        photo.put(key, photoItem);
        return this;
    }

}