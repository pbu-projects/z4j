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
import lol.pbu.z4j.model.UserIdentityObjectType;
import lol.pbu.z4j.model.UserIdentityObjectVerificationMethod;
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
 * UserIdentityObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    UserIdentityObject.JSON_PROPERTY_TYPE,
    UserIdentityObject.JSON_PROPERTY_USER_ID,
    UserIdentityObject.JSON_PROPERTY_VALUE,
    UserIdentityObject.JSON_PROPERTY_CREATED_AT,
    UserIdentityObject.JSON_PROPERTY_DELIVERABLE_STATE,
    UserIdentityObject.JSON_PROPERTY_ID,
    UserIdentityObject.JSON_PROPERTY_PRIMARY,
    UserIdentityObject.JSON_PROPERTY_UNDELIVERABLE_COUNT,
    UserIdentityObject.JSON_PROPERTY_UPDATED_AT,
    UserIdentityObject.JSON_PROPERTY_URL,
    UserIdentityObject.JSON_PROPERTY_VERIFICATION_METHOD,
    UserIdentityObject.JSON_PROPERTY_VERIFIED,
    UserIdentityObject.JSON_PROPERTY_VERIFIED_AT,
})
@Serdeable
public class UserIdentityObject {

    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_USER_ID = "user_id";
    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DELIVERABLE_STATE = "deliverable_state";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_PRIMARY = "primary";
    public static final String JSON_PROPERTY_UNDELIVERABLE_COUNT = "undeliverable_count";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VERIFICATION_METHOD = "verification_method";
    public static final String JSON_PROPERTY_VERIFIED = "verified";
    public static final String JSON_PROPERTY_VERIFIED_AT = "verified_at";

    @NotNull
    @JsonProperty(JSON_PROPERTY_TYPE)
    private UserIdentityObjectType type;

    /**
     * <p>The id of the user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long userId;

    /**
     * <p>The identifier for this identity, such as an email address</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String value;

    /**
     * <p>The time the identity was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Email identity type only. Indicates if Zendesk sends notifications to the email address. See <a href=\"#deliverable-state\">Deliverable state</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DELIVERABLE_STATE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String deliverableState;

    /**
     * <p>Automatically assigned on creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>If the identity is the primary identity. *Writable only when creating, not when updating. Use the <a href=\"#make-identity-primary\">Make Identity Primary</a> endpoint instead</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PRIMARY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean primary;

    /**
     * <p>The number of times a soft-bounce response was received at that address</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UNDELIVERABLE_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long undeliverableCount;

    /**
     * <p>The time the identity was updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of this identity</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @JsonProperty(JSON_PROPERTY_VERIFICATION_METHOD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserIdentityObjectVerificationMethod verificationMethod;

    /**
     * <p>If the identity has been verified. Deprecated. Use <code>verification_method</code> as a more accurate representation of a user's state of verification.</p>
     *
     * @deprecated
     */
    @Deprecated
    @Nullable
    @JsonProperty(JSON_PROPERTY_VERIFIED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean verified;

    /**
     * <p>The last time a full verification flow was completed for the identity</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VERIFIED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime verifiedAt;

    public UserIdentityObject(UserIdentityObjectType type) {
        this.type = type;
    }

}