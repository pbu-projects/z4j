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
import lol.pbu.z4j.model.DeleteTicketFormStatusesRequest;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.TicketFormStatusesResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TicketFormStatusesClient {

    /**
     * {@summary Delete Ticket Form Status By Id}
     * <p>Deletes a ticket form status by id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketFormId <p>The ID of the ticket form</p> (required)
     * @param ticketFormStatusId <p>The id of the ticket form status</p> (required)
     *
     * @return <p>Success response</p> (status code 204)
     */
    @Delete("/api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses/{ticket_form_status_id}")
    Mono<Void> deleteTicketFormStatusById(
        @PathVariable("ticket_form_id") @NotNull Integer ticketFormId,
        @PathVariable("ticket_form_status_id") @NotNull String ticketFormStatusId
    );

    /**
     * {@summary Delete Ticket Form Statuses}
     * <p>Deletes all of of the ticket form statuses by id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @param ticketFormId <p>The ID of the ticket form</p> (required)
     * @param deleteTicketFormStatusesRequest (optional)
     *
     * @return <p>Success response</p> (status code 204)
     */
    @Delete("/api/v2/ticket_forms/{ticket_form_id}/ticket_form_statuses")
    Mono<Void> deleteTicketFormStatuses(
        @PathVariable("ticket_form_id") @NotNull Integer ticketFormId,
        @Body @Nullable @Valid DeleteTicketFormStatusesRequest deleteTicketFormStatusesRequest
    );

    /**
     * {@summary List Ticket Form Statuses}
     * <p>Fetches all of the ticket form statuses for the account.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_form_statuses")
    Mono<@Valid TicketFormStatusesResponse> listTicketFormStatuses();

    /**
     * {@summary Show Many Ticket Form Statuses}
     * <p>Fetches all of the ticket form statuses specified by a comma separated list of ids.</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @param ids <p>Ticket form status ids to retrieve records for</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_form_statuses/show_many")
    Mono<@Valid TicketFormStatusesResponse> showManyTicketFormStatuses(
        @QueryValue("ids") @NotNull String ids
    );
}