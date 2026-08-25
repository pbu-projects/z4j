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
import lol.pbu.z4j.model.RenewSessionResponse;
import lol.pbu.z4j.model.SessionResponse;
import lol.pbu.z4j.model.SessionsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface SessionsClient {

    /**
     * {@summary Bulk Delete Sessions}
     * <p>Deletes all the sessions for a user.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents, End users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>No Content</p> (status code 204)
     */
    @Delete("/api/v2/users/{user_id}/sessions")
    Mono<Void> bulkDeleteSessionsByUserId(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Delete the Authenticated Session}
     * <p>Deletes the current session. In practice, this only works when using session auth for requests, such as client-side requests made from a Zendesk app. When using OAuth or basic authentication, you don't have a current session so this endpoint has no effect.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents, End users</li> </ul>
     *
     * @return <p>No Content</p> (status code 204)
     */
    @Delete("/api/v2/users/me/logout")
    Mono<Void> deleteAuthenticatedSession();

    /**
     * {@summary Delete Session}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents, End users</li> </ul>
     *
     * @param sessionId <p>The ID of the session</p> (required)
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>No Content</p> (status code 204)
     */
    @Delete("/api/v2/users/{user_id}/sessions/{session_id}")
    Mono<Void> deleteSession(
        @PathVariable("session_id") @NotNull Integer sessionId,
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary List Sessions}
     * <p>If authenticated as an admin, returns all the account's sessions. If authenticated as an agent or end user, returns only the sessions of the user making the request.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents, End users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/sessions")
    Mono<@Valid SessionsResponse> listSessions(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary List Sessions for User}
     * <p>Lists all sessions for a specific user.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents, End users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/sessions")
    Mono<@Valid SessionsResponse> listUserSessions(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Renew the current session}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents, End users</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/me/session/renew")
    Mono<@Valid RenewSessionResponse> renewCurrentSession();

    /**
     * {@summary Show the Currently Authenticated Session}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents, End users</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/me/session")
    Mono<@Valid SessionResponse> showCurrentlyAuthenticatedSession();

    /**
     * {@summary Show Session}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents, End users</li> </ul>
     *
     * @param sessionId <p>The ID of the session</p> (required)
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/sessions/{session_id}")
    Mono<@Valid SessionResponse> showSession(
        @PathVariable("session_id") @NotNull Integer sessionId,
        @PathVariable("user_id") @NotNull Integer userId
    );
}