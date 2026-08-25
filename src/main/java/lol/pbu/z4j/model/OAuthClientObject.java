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
 * OAuthClientObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    OAuthClientObject.JSON_PROPERTY_IDENTIFIER,
    OAuthClientObject.JSON_PROPERTY_NAME,
    OAuthClientObject.JSON_PROPERTY_USER_ID,
    OAuthClientObject.JSON_PROPERTY_COMPANY,
    OAuthClientObject.JSON_PROPERTY_CREATED_AT,
    OAuthClientObject.JSON_PROPERTY_DESCRIPTION,
    OAuthClientObject.JSON_PROPERTY_GLOBAL,
    OAuthClientObject.JSON_PROPERTY_ID,
    OAuthClientObject.JSON_PROPERTY_KIND,
    OAuthClientObject.JSON_PROPERTY_LOGO_URL,
    OAuthClientObject.JSON_PROPERTY_REDIRECT_URI,
    OAuthClientObject.JSON_PROPERTY_SECRET,
    OAuthClientObject.JSON_PROPERTY_UPDATED_AT,
    OAuthClientObject.JSON_PROPERTY_URL,
})
@Serdeable
public class OAuthClientObject {

    public static final String JSON_PROPERTY_IDENTIFIER = "identifier";
    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_USER_ID = "user_id";
    public static final String JSON_PROPERTY_COMPANY = "company";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_GLOBAL = "global";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_KIND = "kind";
    public static final String JSON_PROPERTY_LOGO_URL = "logo_url";
    public static final String JSON_PROPERTY_REDIRECT_URI = "redirect_uri";
    public static final String JSON_PROPERTY_SECRET = "secret";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The unique identifier for this client</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_IDENTIFIER)
    private String identifier;

    /**
     * <p>The name of this client</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * <p>The id of the admin who created the client</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_USER_ID)
    private Integer userId;

    /**
     * <p>The company name displayed when users are asked to grant access to your application.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COMPANY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String company;

    /**
     * <p>The time the client was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>A short description of your client that is displayed to users when they are considering approving access to your application</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Whether this client is globally accessible. See <a href=\"/documentation/apps/publish-your-app-or-theme/global_oauth_intro/\">Set up a global OAuth client</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GLOBAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean global;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>Either \"public\" or \"confidential\". Specifies whether the OAuth client operates in a public environment where credentials cannot be securely stored, or on secure servers that can safely store credentials. See <a href=\"/documentation/ticketing/working-with-oauth/oauth-pkce/#client-types\">Client types</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_KIND)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String kind;

    /**
     * <p>The API logo url of this record</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOGO_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String logoUrl;

    /**
     * <p>An array of the valid redirect URIs for this client</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REDIRECT_URI)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> redirectUri;

    /**
     * <p>The client secret. Generated automatically on creation and returned in full only at that time</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SECRET)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String secret;

    /**
     * <p>The time of the last update of the client</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of this record</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public OAuthClientObject(String identifier, String name, Integer userId) {
        this.identifier = identifier;
        this.name = name;
        this.userId = userId;
    }

    /**
     * Add an item to the redirectUri property in a chainable fashion.
     *
     * @return The same instance of OAuthClientObject for chaining.
     */
    public OAuthClientObject addRedirectUriItem(String redirectUriItem) {
        if (redirectUri == null) {
            redirectUri = new ArrayList<>();
        }
        redirectUri.add(redirectUriItem);
        return this;
    }

}