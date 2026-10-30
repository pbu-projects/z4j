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
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.TicketFormCreateRequest;
import lol.pbu.z4j.model.TicketFormResponse;
import lol.pbu.z4j.model.TicketFormsResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Ticket Forms in Zendesk.</h1>
 *
 * @since 0.2.2
 */
@Client("zendesk")
public interface TicketFormsClient {

    /**
     * <h1>{@summary Create Ticket Form}</h1>
     *
     * @param ticketFormCreateRequest The request payload (required)
     * @return Created response (status code 201)
     */
    @Post("/api/v2/ticket_forms")
    Mono<@Valid TicketFormResponse> createTicketForm(
            @Body @NotNull @Valid TicketFormCreateRequest ticketFormCreateRequest
    );

    /**
     * <h1>{@summary Delete Ticket Form}</h1>
     *
     * @param ticketFormId The ID of the ticket form (required)
     * @return No Content response (status code 204)
     */
    @Delete("/api/v2/ticket_forms/{ticket_form_id}")
    Mono<Void> deleteTicketForm(
            @PathVariable("ticket_form_id") @NotNull Long ticketFormId
    );

    /**
     * <h1>{@summary List Ticket Forms}</h1>
     * <p>Returns a list of all ticket forms for your account if accessed as an admin or agent. End users only see ticket forms that have end_user_visible set to true.</p>
     *
     * @param active true returns active ticket forms; false returns inactive ticket forms. If not present, returns both (optional)
     * @param endUserVisible true returns ticket forms where end_user_visible is true (optional)
     * @param fallbackToDefault true returns the default ticket form when criteria results in empty set (optional)
     * @param associatedToBrand true returns ticket forms of the brand specified by URL subdomain (optional)
     * @return Success response (status code 200)
     */
    @Get("/api/v2/ticket_forms")
    Mono<@Valid TicketFormsResponse> listTicketForms(
            @QueryValue("active") @Nullable Boolean active,
            @QueryValue("end_user_visible") @Nullable Boolean endUserVisible,
            @QueryValue("fallback_to_default") @Nullable Boolean fallbackToDefault,
            @QueryValue("associated_to_brand") @Nullable Boolean associatedToBrand
    );

    default Mono<@Valid TicketFormsResponse> listTicketForms() {
        return listTicketForms(null, null, null, null);
    }

    /**
     * <h1>{@summary Show Many Ticket Forms}</h1>
     *
     * @param ids Comma-separated list of up to 100 ticket form IDs (required)
     * @param active true returns active ticket forms (optional)
     * @param endUserVisible true returns end user visible ticket forms (optional)
     * @param fallbackToDefault fallback to default if empty (optional)
     * @param associatedToBrand filter by brand (optional)
     * @return Success response (status code 200)
     */
    @Get("/api/v2/ticket_forms/show_many")
    Mono<@Valid TicketFormsResponse> showManyTicketForms(
            @QueryValue("ids") @NotNull String ids,
            @QueryValue("active") @Nullable Boolean active,
            @QueryValue("end_user_visible") @Nullable Boolean endUserVisible,
            @QueryValue("fallback_to_default") @Nullable Boolean fallbackToDefault,
            @QueryValue("associated_to_brand") @Nullable Boolean associatedToBrand
    );

    /**
     * <h1>{@summary Show Ticket Form}</h1>
     *
     * @param ticketFormId The ID of the ticket form (required)
     * @return Success response (status code 200)
     */
    @Get("/api/v2/ticket_forms/{ticket_form_id}")
    Mono<@Valid TicketFormResponse> showTicketForm(
            @PathVariable("ticket_form_id") @NotNull Long ticketFormId
    );

    /**
     * <h1>{@summary Update Ticket Form}</h1>
     *
     * @param ticketFormId The ID of the ticket form (required)
     * @param ticketFormCreateRequest The request payload (required)
     * @return Success response (status code 200)
     */
    @Put("/api/v2/ticket_forms/{ticket_form_id}")
    Mono<@Valid TicketFormResponse> updateTicketForm(
            @PathVariable("ticket_form_id") @NotNull Long ticketFormId,
            @Body @NotNull @Valid TicketFormCreateRequest ticketFormCreateRequest
    );
}
