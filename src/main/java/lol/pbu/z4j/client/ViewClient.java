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

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.TicketsResponse;
import lol.pbu.z4j.model.ViewCountResponse;
import lol.pbu.z4j.model.ViewCreateRequest;
import lol.pbu.z4j.model.ViewExecuteResponse;
import lol.pbu.z4j.model.ViewResponse;
import lol.pbu.z4j.model.ViewUpdateRequest;
import lol.pbu.z4j.model.ViewsResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Views in Zendesk Support.</h1>
 * <ul>
 *     <li>List Views {@link #listViews}</li>
 *     <li>List Active Views {@link #listActiveViews}</li>
 *     <li>Show View {@link #showView}</li>
 *     <li>Create View {@link #createView}</li>
 *     <li>Update View {@link #updateView}</li>
 *     <li>Delete View {@link #deleteView}</li>
 *     <li>Execute View {@link #executeView}</li>
 *     <li>Count Tickets in View {@link #countView}</li>
 *     <li>List Tickets from a View {@link #listTicketsForView}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.2.4
 */
@Client("zendesk")
public interface ViewClient {

    /**
     * <h1>{@summary List Views}</h1>
     * <p>Lists shared and personal views available to the current user.</p>
     * <h4>Allowed for: Agents</h4>
     *
     * @return OK response (status code 200)
     */
    @Get("/api/v2/views")
    Mono<@Valid ViewsResponse> listViews();

    /**
     * <h1>{@summary List Active Views}</h1>
     * <p>Lists active shared and personal views available to the current user.</p>
     * <h4>Allowed for: Agents</h4>
     *
     * @return OK response (status code 200)
     */
    @Get("/api/v2/views/active")
    Mono<@Valid ViewsResponse> listActiveViews();

    /**
     * <h1>{@summary Show View}</h1>
     * <p>Returns a single view by ID.</p>
     * <h4>Allowed for: Agents</h4>
     *
     * @param id The unique ID of the view (required)
     * @return OK response (status code 200)
     */
    @Get("/api/v2/views/{id}")
    Mono<@Valid ViewResponse> showView(@PathVariable("id") @NotNull Long id);

    /**
     * <h1>{@summary Create View}</h1>
     * <p>Creates a new ticket view. The request must include at least one condition in the all array
     * checking status, type, group_id, assignee_id, or requester_id.</p>
     * <h4>Allowed for: Agents</h4>
     *
     * @param body {@link ViewCreateRequest} (required)
     * @return Created response (status code 201)
     */
    @Post("/api/v2/views")
    Mono<@Valid ViewResponse> createView(@Body @NotNull @Valid ViewCreateRequest body);

    /**
     * <h1>{@summary Update View}</h1>
     * <p>Updates an existing ticket view by ID.</p>
     * <h4>Allowed for: Agents</h4>
     *
     * @param id   The unique ID of the view to update (required)
     * @param body {@link ViewUpdateRequest} (required)
     * @return OK response (status code 200)
     */
    @Put("/api/v2/views/{id}")
    Mono<@Valid ViewResponse> updateView(
            @PathVariable("id") @NotNull Long id,
            @Body @NotNull @Valid ViewUpdateRequest body
    );

    /**
     * <h1>{@summary Delete View}</h1>
     * <p>Deletes an existing ticket view by ID.</p>
     * <h4>Allowed for: Agents</h4>
     *
     * @param id The unique ID of the view to delete (required)
     * @return No content response (status code 204)
     */
    @Delete("/api/v2/views/{id}")
    Mono<Void> deleteView(@PathVariable("id") @NotNull Long id);

    /**
     * <h1>{@summary Execute View}</h1>
     * <p>Returns the column titles and rows for the specified view.</p>
     * <h4>Allowed for: Agents</h4>
     *
     * @param id The unique ID of the view to execute (required)
     * @return OK response (status code 200)
     */
    @Get("/api/v2/views/{id}/execute")
    Mono<@Valid ViewExecuteResponse> executeView(@PathVariable("id") @NotNull Long id);

    /**
     * <h1>{@summary Count Tickets in View}</h1>
     * <p>Returns the ticket count for a single view.</p>
     * <h4>Allowed for: Agents</h4>
     *
     * @param id The unique ID of the view to count (required)
     * @return OK response (status code 200)
     */
    @Get("/api/v2/views/{id}/count")
    Mono<@Valid ViewCountResponse> countView(@PathVariable("id") @NotNull Long id);

    /**
     * <h1>{@summary List Tickets from a View}</h1>
     * <p>Returns tickets matching the specified view without cursor pagination parameters.</p>
     * <h4>Allowed for: Agents</h4>
     *
     * @param id The unique ID of the view (required)
     * @return OK response (status code 200)
     */
    default Mono<@Valid TicketsResponse> listTicketsForView(@PathVariable("id") @NotNull Long id) {
        return listTicketsForView(id, null, null);
    }

    /**
     * <h1>{@summary List Tickets from a View}</h1>
     * <p>Returns tickets matching the specified view with optional cursor pagination.</p>
     * <h4>Allowed for: Agents</h4>
     *
     * @param id        The unique ID of the view (required)
     * @param pageAfter Cursor token for fetching the next page (optional)
     * @param pageSize  Number of results per page, max 100 (optional)
     * @return OK response (status code 200)
     */
    @Get("/api/v2/views/{id}/tickets.json")
    Mono<@Valid TicketsResponse> listTicketsForView(
        @PathVariable("id") @NotNull Long id,
        @QueryValue("page[after]") @Nullable String pageAfter,
        @QueryValue("page[size]") @Nullable @Max(100) Integer pageSize
    );

}
