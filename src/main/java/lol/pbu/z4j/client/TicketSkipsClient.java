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
import lol.pbu.z4j.model.ListDeletedTicketsSortOrderParameter;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.TicketSkipCreation;
import lol.pbu.z4j.model.TicketSkipsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TicketSkipsClient {

    /**
     * {@summary List All Skips}
     * <p>Lists all skips. Archived tickets are not included in the response. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203657756\">About archived tickets</a> in the Support Help Center.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents with \"View only\" or higher reports permissions in Support.   These permissions are distinct from Explore permissions.</li> <li>Agents retrieving their own skips</li> </ul>
     *
     * @param userId <p>User ID of an agent</p> (required)
     * @param sortOrder <p>Sort order. Defaults to \"asc\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/skips")
    Mono<@Valid TicketSkipsResponse> listSkips(
        @PathVariable("user_id") @NotNull Integer userId,
        @QueryValue("sort_order") @Nullable ListDeletedTicketsSortOrderParameter sortOrder
    );

    /**
     * {@summary List Ticket Skips}
     * <p>Archived tickets are not included in the response. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203657756\">About archived tickets</a> in the Support Help Center.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents with \"View only\" or higher reports permissions in Support.   These permissions are distinct from Explore permissions.</li> <li>Agents retrieving their own skips</li> </ul>
     *
     * @param userId <p>User ID of an agent</p> (required)
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param sortOrder <p>Sort order. Defaults to \"asc\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/skips")
    Mono<@Valid TicketSkipsResponse> listTicketSkips(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("ticket_id") @NotNull Integer ticketId,
        @QueryValue("sort_order") @Nullable ListDeletedTicketsSortOrderParameter sortOrder
    );

    /**
     * {@summary List Ticket Skips By Ticket}
     * <p>Returns the skips for a specific ticket.</p> <p>Archived tickets are not included in the response. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203657756\">About archived tickets</a> in the Support Help Center.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents with \"View only\" or higher reports permissions in Support.   These permissions are distinct from Explore permissions.</li> <li>Agents retrieving their own skips</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param userId <p>User ID of an agent</p> (required)
     * @param sortOrder <p>Sort order. Defaults to \"asc\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/skips")
    Mono<@Valid TicketSkipsResponse> listTicketSkipsByTicket(
        @PathVariable("ticket_id") @NotNull Integer ticketId,
        @PathVariable("user_id") @NotNull Integer userId,
        @QueryValue("sort_order") @Nullable ListDeletedTicketsSortOrderParameter sortOrder
    );

    /**
     * {@summary Record a New Skip for the Current User}
     * <p>Record a new ticket skip for the current user.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>User ID of an agent</p> (required)
     * @param sortOrder <p>Sort order. Defaults to \"asc\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 201)
     */
    @Post("/api/v2/skips")
    Mono<@Valid TicketSkipCreation> recordNewSkip(
        @PathVariable("user_id") @NotNull Integer userId,
        @QueryValue("sort_order") @Nullable ListDeletedTicketsSortOrderParameter sortOrder
    );
}