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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Serdeable
@RecordBuilder
public record OAuthTokenResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_ACCESS_TOKEN) String accessToken,
        @Nullable @JsonProperty(JSON_PROPERTY_TOKEN_TYPE) String tokenType,
        @Nullable @JsonProperty(JSON_PROPERTY_EXPIRES_IN) Long expiresIn,
        @Nullable @JsonProperty(JSON_PROPERTY_SCOPE) String scope
) implements OAuthTokenResponseBuilder.With {

    public static final String JSON_PROPERTY_ACCESS_TOKEN = "access_token";
    public static final String JSON_PROPERTY_TOKEN_TYPE = "token_type";
    public static final String JSON_PROPERTY_EXPIRES_IN = "expires_in";
    public static final String JSON_PROPERTY_SCOPE = "scope";
}
