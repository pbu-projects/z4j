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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * OauthTokenObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    OauthTokenObject.JSON_PROPERTY_CLIENT_ID,
    OauthTokenObject.JSON_PROPERTY_CREATED_AT,
    OauthTokenObject.JSON_PROPERTY_EXPIRES_AT,
    OauthTokenObject.JSON_PROPERTY_ID,
    OauthTokenObject.JSON_PROPERTY_REFRESH_TOKEN,
    OauthTokenObject.JSON_PROPERTY_REFRESH_TOKEN_EXPIRES_AT,
    OauthTokenObject.JSON_PROPERTY_SCOPES,
    OauthTokenObject.JSON_PROPERTY_TOKEN,
    OauthTokenObject.JSON_PROPERTY_URL,
    OauthTokenObject.JSON_PROPERTY_USED_AT,
    OauthTokenObject.JSON_PROPERTY_USER_ID,
})
@Serdeable
public class OauthTokenObject {

    public static final String JSON_PROPERTY_CLIENT_ID = "client_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_EXPIRES_AT = "expires_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_REFRESH_TOKEN = "refresh_token";
    public static final String JSON_PROPERTY_REFRESH_TOKEN_EXPIRES_AT = "refresh_token_expires_at";
    public static final String JSON_PROPERTY_SCOPES = "scopes";
    public static final String JSON_PROPERTY_TOKEN = "token";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USED_AT = "used_at";
    public static final String JSON_PROPERTY_USER_ID = "user_id";

    /**
     * <p>The id of the client this token belongs to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CLIENT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer clientId;

    /**
     * <p>The time the token was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The time the token will expire</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXPIRES_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime expiresAt;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The refresh token, if generated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REFRESH_TOKEN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String refreshToken;

    /**
     * <p>The time the refresh token will expire</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REFRESH_TOKEN_EXPIRES_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime refreshTokenExpiresAt;

    /**
     * <p>An array of the valid scopes for this token. See <a href=\"#scopes\">Scopes</a> below</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SCOPES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> scopes;

    /**
     * <p>The access token</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TOKEN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String token;

    /**
     * <p>The API url of this record</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * <p>The latest time this token was used for authentication</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime usedAt;

    /**
     * <p>The id of the user this token authenticates as</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer userId;

}