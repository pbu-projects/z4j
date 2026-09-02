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
import lol.pbu.z4j.model.TicketFormResponse;
import lol.pbu.z4j.model.TicketFormStatusesParams;
import lol.pbu.z4j.model.TicketFormStatusesResponse;
import lol.pbu.z4j.model.TicketFormsResponse;
import lol.pbu.z4j.model.UpdateTicketFormStatusesParams;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TicketFormsClient {

    /**
     * {@summary Clone an Already Existing Ticket Form}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketFormId <p>The ID of the ticket form</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/ticket_forms/{ticket_form_id}/clone")
    Mono<@Valid TicketFormResponse> cloneTicketForm(
        @PathVariable("ticket_form_id") @NotNull Long ticketFormId
    );

    /**
     * {@summary Create Ticket Form}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/ticket_forms")
    Mono<@Valid TicketFormResponse> createTicketForm();

    /**
     * {@summary Create Ticket Form Statuses}
     * <p>Creates one or many ticket form status associations</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketFormId <p>The ID of the ticket form</p> (required)
     * @param ticketFormStatusesParams (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses")
    Mono<@Valid TicketFormStatusesResponse> createTicketFormStatuses(
        @PathVariable("ticket_form_id") @NotNull Long ticketFormId,
        @Body @Nullable @Valid TicketFormStatusesParams ticketFormStatusesParams
    );

    /**
     * {@summary Delete Ticket Form}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketFormId <p>The ID of the ticket form</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/ticket_forms/{ticket_form_id}")
    Mono<Void> deleteTicketForm(
        @PathVariable("ticket_form_id") @NotNull Long ticketFormId
    );

    /**
     * {@summary List Ticket Forms}
     * <p>Returns a list of all ticket forms for your account if accessed as an admin or agent. End users only see ticket forms that have <code>end_user_visible</code> set to true.</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @param active <p>true returns active ticket forms; false returns inactive ticket forms. If not present, returns both</p> (optional)
     * @param endUserVisible <p>true returns ticket forms where <code>end_user_visible</code>; false returns ticket forms that are not end-user visible. If not present, returns both</p> (optional)
     * @param fallbackToDefault <p>true returns the default ticket form when the criteria defined by the parameters results in a set without active and end-user visible ticket forms</p> (optional)
     * @param associatedToBrand <p>true returns the ticket forms of the brand specified by the url's subdomain</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_forms")
    Mono<@Valid TicketFormsResponse> listTicketForms(
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("end_user_visible") @Nullable Boolean endUserVisible,
        @QueryValue("fallback_to_default") @Nullable Boolean fallbackToDefault,
        @QueryValue("associated_to_brand") @Nullable Boolean associatedToBrand
    );

    /**
     * {@summary Reorder Ticket Forms}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Request Parameters</h4> <p>You can pass in the following parameter in the payload:</p> <p>| Name                | Type   | Comment | ------------------- | ------ | -------- | ticket_form_ids     | array  | An array of ticket form ids. Example: \"[2, 23, 46, 50]\"</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/ticket_forms/reorder")
    Mono<@Valid TicketFormsResponse> reorderTicketForms();

    /**
     * {@summary Show Many Ticket Forms}
     * <p>Takes an <code>ids</code> query parameter that accepts a comma-separated list of up to 100 ticket form ids. This endpoint is used primarily by the <a href=\"/documentation/classic-web-widget-sdks/\">mobile SDK</a> and the <a href=\"/api-reference/widget/introduction/\">Web Widget</a>.</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @param ids <p>IDs of the ticket forms to be shown</p> (required)
     * @param active <p>true returns active ticket forms; false returns inactive ticket forms. If not present, returns both</p> (optional)
     * @param endUserVisible <p>true returns ticket forms where <code>end_user_visible</code>; false returns ticket forms that are not end-user visible. If not present, returns both</p> (optional)
     * @param fallbackToDefault <p>true returns the default ticket form when the criteria defined by the parameters results in a set without active and end-user visible ticket forms</p> (optional)
     * @param associatedToBrand <p>true returns the ticket forms of the brand specified by the url's subdomain</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
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
     * {@summary Show Ticket Form}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents, and End Users</li> </ul>
     *
     * @param ticketFormId <p>The ID of the ticket form</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_forms/{ticket_form_id}")
    Mono<@Valid TicketFormResponse> showTicketForm(
        @PathVariable("ticket_form_id") @NotNull Long ticketFormId
    );

    /**
     * {@summary List Ticket Form Statuses of a Ticket Form}
     * <p>Fetches all of the associated ticket form statuses of a ticket form.</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @param ticketFormId <p>The ID of the ticket form</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses")
    Mono<@Valid TicketFormStatusesResponse> ticketFormTicketFormStatuses(
        @PathVariable("ticket_form_id") @NotNull Long ticketFormId
    );

    /**
     * {@summary Update Ticket Form}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketFormId <p>The ID of the ticket form</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/ticket_forms/{ticket_form_id}")
    Mono<@Valid TicketFormResponse> updateTicketForm(
        @PathVariable("ticket_form_id") @NotNull Long ticketFormId
    );

    /**
     * {@summary Update Ticket Form Status By Id}
     * <p>Updates or deletes ticket form status association by id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketFormId <p>The ID of the ticket form</p> (required)
     * @param ticketFormStatusId <p>The id of the ticket form status</p> (required)
     * @param updateTicketFormStatusesParams (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses/{ticket_form_status_id}")
    Mono<@Valid TicketFormStatusesResponse> updateTicketFormStatusById(
        @PathVariable("ticket_form_id") @NotNull Long ticketFormId,
        @PathVariable("ticket_form_status_id") @NotNull String ticketFormStatusId,
        @Body @Nullable @Valid UpdateTicketFormStatusesParams updateTicketFormStatusesParams
    );

    /**
     * {@summary Bulk Update Ticket Form Statuses of a Ticket Form}
     * <p>Updates or deletes ticket form status associations. This is a bulk operation that can both add and remove ticket form status associations for a form in one call.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketFormId <p>The ID of the ticket form</p> (required)
     * @param updateTicketFormStatusesParams (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses")
    Mono<@Valid TicketFormStatusesResponse> updateTicketFormStatuses(
        @PathVariable("ticket_form_id") @NotNull Long ticketFormId,
        @Body @Nullable @Valid UpdateTicketFormStatusesParams updateTicketFormStatusesParams
    );
}