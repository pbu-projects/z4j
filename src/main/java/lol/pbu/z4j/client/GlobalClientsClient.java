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
import lol.pbu.z4j.model.GlobalClientResponse;
import lol.pbu.z4j.model.GlobalClientsResponse;
import lol.pbu.z4j.model.GlobalClientsTokenSummaryResponse;
import reactor.core.publisher.Mono;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface GlobalClientsClient {

    /**
     * {@summary Show Token summary for Global OAuth Clients}
     * <p>Returns information about tokens for the global clients that your account has authorized.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param globalClientId <p>The id of the global OAuth client</p> (optional)
     * @param includeExpired <p>If true, includes expired tokens in summary</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/oauth/global_clients/token_summary")
    Mono<@Valid GlobalClientsTokenSummaryResponse> globalOAuthClientsTokenSummary(
        @QueryValue("global_client_id") @Nullable Long globalClientId,
        @QueryValue("include_expired") @Nullable Boolean includeExpired
    );

    /**
     * {@summary List Global OAuth Clients}
     * <p>Returns all the global OAuth clients that users on your account have authorized.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/oauth/global_clients")
    Mono<@Valid GlobalClientsResponse> listGlobalOAuthClients();

    /**
     * {@summary Show Global OAuth Client}
     * <p>Returns the global OAuth client associated with the ID sent on the request.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param globalClientId <p>The ID of the Global OAuth client</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/oauth/global_clients/{global_client_id}")
    Mono<@Valid GlobalClientResponse> showGlobalClient(
        @PathVariable("global_client_id") @NotNull Long globalClientId
    );
}