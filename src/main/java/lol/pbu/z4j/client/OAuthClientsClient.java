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

import io.micronaut.http.annotation.*;
import io.micronaut.core.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.core.convert.format.Format;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.OAuthClientsResponse;
import lol.pbu.z4j.model.OauthClientResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface OAuthClientsClient {

    /**
     * {@summary Generate Secret}
     * <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param oauthClientId <p>The ID of the OAuth client</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/oauth/clients/{oauth_client_id}/generate_secret")
    Mono<@Valid OauthClientResponse> clientGenerateSecret(
        @PathVariable("oauth_client_id") @NotNull Integer oauthClientId
    );

    /**
     * {@summary Create Client}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/oauth/clients")
    Mono<@Valid OauthClientResponse> createOAuthClient();

    /**
     * {@summary Delete Client}
     * <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param oauthClientId <p>The ID of the OAuth client</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/oauth/clients/{oauth_client_id}")
    Mono<Void> deleteClient(
        @PathVariable("oauth_client_id") @NotNull Integer oauthClientId
    );

    /**
     * {@summary List Current User's Clients}
     * <p>Returns the OAuth clients owned by the current user.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/me/oauth/clients")
    Mono<@Valid OAuthClientsResponse> listCurrentUserOAuthClients();

    /**
     * {@summary List Clients}
     * <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/oauth/clients")
    Mono<@Valid OAuthClientsResponse> listOAuthClients();

    /**
     * {@summary Show Client}
     * <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param oauthClientId <p>The ID of the OAuth client</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/oauth/clients/{oauth_client_id}")
    Mono<@Valid OauthClientResponse> showClient(
        @PathVariable("oauth_client_id") @NotNull Integer oauthClientId
    );

    /**
     * {@summary Update Client}
     * <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param oauthClientId <p>The ID of the OAuth client</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/oauth/clients/{oauth_client_id}")
    Mono<@Valid OauthClientResponse> updateClient(
        @PathVariable("oauth_client_id") @NotNull Integer oauthClientId
    );
}