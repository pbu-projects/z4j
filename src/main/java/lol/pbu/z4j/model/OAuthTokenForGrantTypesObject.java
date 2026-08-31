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
 * OAuthTokenForGrantTypesObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    OAuthTokenForGrantTypesObject.JSON_PROPERTY_ACCESS_TOKEN,
    OAuthTokenForGrantTypesObject.JSON_PROPERTY_EXPIRES_IN,
    OAuthTokenForGrantTypesObject.JSON_PROPERTY_REFRESH_TOKEN,
    OAuthTokenForGrantTypesObject.JSON_PROPERTY_REFRESH_TOKEN_EXPIRES_IN,
    OAuthTokenForGrantTypesObject.JSON_PROPERTY_SCOPE,
    OAuthTokenForGrantTypesObject.JSON_PROPERTY_TOKEN_TYPE,
})
@Serdeable
public class OAuthTokenForGrantTypesObject {

    public static final String JSON_PROPERTY_ACCESS_TOKEN = "access_token";
    public static final String JSON_PROPERTY_EXPIRES_IN = "expires_in";
    public static final String JSON_PROPERTY_REFRESH_TOKEN = "refresh_token";
    public static final String JSON_PROPERTY_REFRESH_TOKEN_EXPIRES_IN = "refresh_token_expires_in";
    public static final String JSON_PROPERTY_SCOPE = "scope";
    public static final String JSON_PROPERTY_TOKEN_TYPE = "token_type";

    /**
     * <p>The access token</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACCESS_TOKEN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String accessToken;

    /**
     * <p>Number of seconds the access token is valid. Must be more than 300 seconds (5 minutes) and less than 172,800 seconds (2 days), or less than <code>refresh_token_expires_in</code>, whichever is the smallest. Defaults to null</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXPIRES_IN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long expiresIn;

    /**
     * <p>The refresh token</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REFRESH_TOKEN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String refreshToken;

    /**
     * <p>Number of seconds the refresh token is valid. Must be more than 604,800 seconds (7 days) or <code>expires_in</code> (if given), and less than 7,776,000 seconds (90 days). Defaults to 2,592,000 seconds (30 days)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REFRESH_TOKEN_EXPIRES_IN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long refreshTokenExpiresIn;

    /**
     * <p>The valid scopes for this token. See <a href=\"#scope\">Scope</a> below</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SCOPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String scope;

    /**
     * <p>Type of the access token, for example \"bearer\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TOKEN_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String tokenType;

}