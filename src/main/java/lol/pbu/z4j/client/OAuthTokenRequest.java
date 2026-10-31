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
package lol.pbu.z4j.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

/**
 * <h1>OAuth Token Request.</h1>
 * <p>Payload for requesting client credentials tokens from Zendesk.</p>
 *
 * @author Jonathan-Zollinger
 * @since 0.4.0
 */
@Getter
@Setter
@Accessors(chain = true)
@Serdeable
@Generated
public class OAuthTokenRequest {

    @JsonProperty("grant_type")
    private String grantType = "client_credentials";

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @Nullable
    @JsonProperty("scope")
    private String scope;

    public OAuthTokenRequest() {
    }

    public OAuthTokenRequest(String clientId, String clientSecret, String scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }
}
