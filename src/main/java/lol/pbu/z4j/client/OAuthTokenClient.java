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

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.OAuthTokenResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Zendesk OAuth Token Client.</h1>
 * <p>Requests OAuth access tokens from Zendesk token endpoint without service filters.</p>
 *
 * @author Jonathan-Zollinger
 * @since 0.4.0
 */
@Client("${micronaut.http.services.zendesk.url:`https://localhost`}")
public interface OAuthTokenClient {

    @Post("/oauth/tokens")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Mono<OAuthTokenResponse> requestToken(@Body @NotNull OAuthTokenRequest request);
}
