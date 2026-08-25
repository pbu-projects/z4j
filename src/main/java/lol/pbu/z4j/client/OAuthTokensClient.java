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
import lol.pbu.z4j.model.OAuthTokenResponse;
import lol.pbu.z4j.model.OAuthTokensResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface OAuthTokensClient {

    /**
     * {@summary Create Token}
     * <p>Returns an OAuth access token with a specified <a href=\"#scopes\">scope</a>.</p> <p>Refresh tokens aren't used. An access token doesn't expire but it can be <a href=\"#revoke-token\">revoked</a>.</p> <p>For a tutorial, see <a href=\"/documentation/ticketing/working-with-oauth/creating-and-using-oauth-tokens-with-the-api/\">Creating and using OAuth tokens with the API</a>.</p> <p><strong>Note</strong>: For OAuth authorization code, use the <a href=\"/api-reference/ticketing/oauth/grant_type_tokens/#create-token-for-grant-type\">Create Token for Grant Type</a> endpoint. The two APIs don't share the same path, JSON format, or request parameters. However, both APIs return access tokens that can be used to <a href=\"/api-reference/ticketing/introduction/#oauth-access-token\">authenticate API requests</a>.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Request parameters</h4> <p>The POST request takes a \"token\" object that contains an OAuth client's resource id and scopes.</p> <p>| Name      | Type    | Description | --------- | ------- | -------------------------------------------------- | client_id | integer | The resource <code>id</code> of an <a href=\"/api-reference/ticketing/oauth/oauth_clients/#json-format\">OAuth client</a> (not the client's unique identifier). For the ids, see <a href=\"/api-reference/ticketing/oauth/oauth_clients/#list-clients\">List Clients</a> | scopes    | array   | Valid scopes for the token. See <a href=\"#scopes\">Scopes</a> below</p> <h4>Scopes</h4> <p>The <strong>scopes</strong> parameter defines whether requests authenticated with the token can post, put, and delete data, or only get data.</p> <p><strong>Note</strong>: Don't confuse the <strong>scopes</strong> parameter (plural) with the <strong>scope</strong> parameter (singular) for <a href=\"/api-reference/ticketing/oauth/grant_type_tokens/\">grant-type tokens</a>.</p> <p>The <strong>scopes</strong> parameter is an array of strings, each specifying a resource name and an access setting. Access is either \"read\" or \"write\". If you don't specify a resource, access to all resources is assumed. If you don't specify the access, read and write access are assumed.</p> <p>The syntax is as follows:</p> <p><code>\"scopes\": [resource:scope, ...]</code></p> <p>where <code>resource</code> is optional.</p> <p><strong>Examples</strong></p> <p><code>\"scopes\": [\"read\"]</code></p> <p><code>\"scopes\": [\"tickets:read\"]</code></p> <p>To give read and write access to a resource, specify both scopes:</p> <p><code>\"scopes\": [\"users:read\", \"users:write\"]</code></p> <p>To give write access only to one resource and read access to everything else:</p> <p><code>\"scopes\": [\"organizations:write\", \"read\"]</code></p> <p><strong>Note</strong>: The endpoint returns an access token even if you specify an invalid scope. Any request you make with the token will return a \"Forbidden\" error.</p> <p><strong>Available scopes</strong></p> <ul> <li><code>read</code> - gives access to GET endpoints. Includes permission to sideload related resources</li> <li><code>write</code> - gives access to POST, PUT, and DELETE endpoints</li> <li><code>impersonate</code> - allows Zendesk Support admins to make requests on behalf of end users. See <a href=\"/documentation/ticketing/using-the-zendesk-api/making-api-requests-on-behalf-of-end-users/\">Making API requests on behalf of end users</a></li> </ul> <p><strong>Resources that can be scoped</strong></p> <ul> <li>tickets</li> <li>users</li> <li>auditlogs (read only)</li> <li>organizations</li> <li>hc</li> <li>apps</li> <li>triggers</li> <li>automations</li> <li>targets</li> <li>webhooks</li> <li>macros</li> <li>requests</li> <li>satisfaction_ratings</li> <li>dynamic_content</li> <li>any_channel (write only)</li> <li>web_widget (write only)</li> <li>security (read only)</li> </ul>
     *
     * @param clientId <p>The id of the OAuth client</p> (optional)
     * @param globalClientId <p>The id of the global OAuth client</p> (optional)
     * @param all <p>A boolean that returns all OAuth tokens in the account. Requires admin role</p> (optional)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/oauth/tokens")
    Mono<@Valid OAuthTokenResponse> createOAuthToken(
        @QueryValue("client_id") @Nullable Integer clientId,
        @QueryValue("global_client_id") @Nullable Integer globalClientId,
        @QueryValue("all") @Nullable Boolean all
    );

    /**
     * {@summary List Tokens}
     * <p>Returns the properties of the tokens for the current user. Admins can view OAuth token properties for all users using the <a href=\"/api-reference/ticketing/oauth/oauth_tokens/#parameters\">all</a> parameter. To filter the list by OAuth client, use the <a href=\"/api-reference/ticketing/oauth/oauth_tokens/#parameters\">client_id</a> parameter for a local OAuth client ID, or the <a href=\"/api-reference/ticketing/oauth/oauth_tokens/#parameters\">global_client_id</a> parameter for a global OAuth client ID. For security reasons, only the first 10 characters of each access token are included.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param clientId <p>The id of the OAuth client</p> (optional)
     * @param globalClientId <p>The id of the global OAuth client</p> (optional)
     * @param all <p>A boolean that returns all OAuth tokens in the account. Requires admin role</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/oauth/tokens")
    Mono<@Valid OAuthTokensResponse> listOAuthTokens(
        @QueryValue("client_id") @Nullable Integer clientId,
        @QueryValue("global_client_id") @Nullable Integer globalClientId,
        @QueryValue("all") @Nullable Boolean all
    );

    /**
     * {@summary Revoke Current Token}
     * <p>Revokes the current OAuth token. Include an <code>Authorization: Bearer</code> header with the full token.</p> <h4>Allowed for</h4> <ul> <li>Admins, Agents, End Users</li> </ul>
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/oauth/tokens/current")
    Mono<Void> revokeCurrentOAuthToken();

    /**
     * {@summary Revoke Token}
     * <h4>Allowed for</h4> <ul> <li>Admins, Agents, End Users</li> </ul>
     *
     * @param oauthTokenId <p>The ID of the OAuth token</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/oauth/tokens/{oauth_token_id}")
    Mono<Void> revokeOAuthToken(
        @PathVariable("oauth_token_id") @NotNull Integer oauthTokenId
    );

    /**
     * {@summary Show Current Token}
     * <p>Returns the properties of the current token. Include an <code>Authorization: Bearer</code> header with the full token to get its associated properties.</p> <p>For security reasons, only the first 10 characters of the access token are included.</p> <h4>Allowed for</h4> <ul> <li>Admins, Agents, End Users</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/oauth/tokens/current")
    Mono<@Valid OAuthTokenResponse> showCurrentToken();

    /**
     * {@summary Show Token}
     * <p>Returns the properties of the specified token. For security reasons, only the first 10 characters of the access token are included.</p> <p>In the first endpoint, <code>id</code> is a token id, not the full token.</p> <p>In the second endpoint, include an <code>Authorization: Bearer</code> header with the full token to get its associated properties. Example:</p> <p><code>sh curl https://{subdomain}.zendesk.com/api/v2/oauth/tokens/current.json \\   -H 'Authorization: Bearer ${authToken}' \\   -v -u {email_address}/token:{api_token}</code></p> <h4>Allowed for</h4> <ul> <li>Admins, Agents, End Users</li> </ul>
     *
     * @param oauthTokenId <p>The ID of the OAuth token</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/oauth/tokens/{oauth_token_id}")
    Mono<@Valid OAuthTokenResponse> showToken(
        @PathVariable("oauth_token_id") @NotNull Integer oauthTokenId
    );
}