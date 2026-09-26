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
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.CustomStatusCreateRequest;
import lol.pbu.z4j.model.CustomStatusResponse;
import lol.pbu.z4j.model.CustomStatusesResponse;
import lol.pbu.z4j.model.TicketFormStatusesResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Custom Ticket Statuses and Form Status Associations in Zendesk.</h1>
 *
 * @author Jonathan-Zollinger
 * @since 0.2.6
 */
@Retryable
@Client("zendesk")
public interface CustomStatusClient {

    /**
     * <h1>{@summary List Custom Ticket Statuses}</h1>
     * <p>Returns a list of all custom ticket statuses for your Zendesk account.</p>
     *
     * @param statusCategories Filter by comma-separated status categories (e.g. "open,pending") (optional)
     * @param active Filter by active state; true returns only active, false returns only inactive (optional)
     * @return Success response (status code 200)
     */
    @Get("/api/v2/custom_statuses.json{?status_categories,active}")
    Mono<@Valid CustomStatusesResponse> listCustomStatuses(
            @QueryValue("status_categories") @Nullable String statusCategories,
            @QueryValue("active") @Nullable Boolean active
    );

    /**
     * <h1>{@summary List Custom Ticket Statuses}</h1>
     * <p>Convenience method to return all custom ticket statuses without filtering.</p>
     *
     * @return Success response (status code 200)
     */
    default Mono<@Valid CustomStatusesResponse> listCustomStatuses() {
        return listCustomStatuses(null, null);
    }

    /**
     * <h1>{@summary Show Custom Ticket Status}</h1>
     * <p>Returns a single custom ticket status by ID.</p>
     *
     * @param id The ID of the custom status (required)
     * @return Success response (status code 200)
     */
    @Get("/api/v2/custom_statuses/{id}.json")
    Mono<@Valid CustomStatusResponse> showCustomStatus(
            @PathVariable("id") @NotNull Long id
    );

    /**
     * <h1>{@summary Create Custom Ticket Status}</h1>
     * <p>Creates a new custom ticket status for the account.</p>
     *
     * @param request The custom status creation request (required)
     * @return Created response (status code 201)
     */
    @Post("/api/v2/custom_statuses.json")
    Mono<@Valid CustomStatusResponse> createCustomStatus(
            @Body @NotNull @Valid CustomStatusCreateRequest request
    );

    /**
     * <h1>{@summary Update Custom Ticket Status}</h1>
     * <p>Updates an existing custom ticket status by ID.</p>
     *
     * @param id The ID of the custom status to update (required)
     * @param request The custom status update request (required)
     * @return Success response (status code 200)
     */
    @Put("/api/v2/custom_statuses/{id}.json")
    Mono<@Valid CustomStatusResponse> updateCustomStatus(
            @PathVariable("id") @NotNull Long id,
            @Body @NotNull @Valid CustomStatusCreateRequest request
    );

    /**
     * <h1>{@summary List Ticket Form Statuses}</h1>
     * <p>Returns association mappings between ticket forms and custom statuses.</p>
     *
     * @param ticketFormId Optional ticket form ID filter (optional)
     * @return Success response (status code 200)
     */
    @Get("/api/v2/ticket_form_statuses.json{?ticket_form_id}")
    Mono<@Valid TicketFormStatusesResponse> listTicketFormStatuses(
            @QueryValue("ticket_form_id") @Nullable Long ticketFormId
    );

    /**
     * <h1>{@summary List Ticket Form Statuses}</h1>
     * <p>Convenience method to return all ticket form status mappings.</p>
     *
     * @return Success response (status code 200)
     */
    default Mono<@Valid TicketFormStatusesResponse> listTicketFormStatuses() {
        return listTicketFormStatuses(null);
    }
}
