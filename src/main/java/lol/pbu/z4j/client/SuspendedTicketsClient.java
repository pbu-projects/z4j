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
import java.math.BigDecimal;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.RecoverSuspendedTicketResponse;
import lol.pbu.z4j.model.RecoverSuspendedTicketUnprocessableContentResponse;
import lol.pbu.z4j.model.RecoverSuspendedTicketsResponse;
import lol.pbu.z4j.model.SuspendedTicketsAttachmentsResponse;
import lol.pbu.z4j.model.SuspendedTicketsExportResponse;
import lol.pbu.z4j.model.SuspendedTicketsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface SuspendedTicketsClient {

    /**
     * {@summary Delete Suspended Ticket}
     * <h4>Allowed For</h4> <ul> <li>Unrestricted agents</li> </ul>
     *
     * @param id <p>id of the suspended ticket</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/suspended_tickets/{id}")
    Mono<Void> deleteSuspendedTicket(
        @PathVariable("id") @NotNull BigDecimal id
    );

    /**
     * {@summary Delete Multiple Suspended Tickets}
     * <p>Accepts up to 100 ids (the auto-generated id, not the ticket id.)</p> <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage suspended tickets on Enterprise plans</li> <li>Unrestricted agents on all other plans</li> </ul>
     *
     * @param ids <p>A comma separated list of ids of suspended tickets to delete.</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/suspended_tickets/destroy_many")
    Mono<Void> deleteSuspendedTickets(
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary Export Suspended Tickets}
     * <p>Exports a list of suspended tickets for the Zendesk Support instance. To export the list, the endpoint enqueues a job to create a CSV file with the data. When done, Zendesk sends the requester an email containing a link to the CSV file. In the CSV, tickets are sorted by the update timestamp in ascending order.</p> <p>#### Allowed For</p> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage suspended tickets on Enterprise plans</li> <li>Unrestricted agents on all other plans</li> </ul> <p>#### Rate limits</p> <p>Limited to one request per minute and up to one million records in return. The rate-limiting mechanism behaves identically to the one described in <a href=\"/api-reference/ticketing/account-configuration/usage_limits/#monitoring-your-request-activity\">Usage limits</a>.  We recommend using the <code>Retry-After</code> header value as described in <a href=\"/documentation/ticketing/using-the-zendesk-api/best-practices-for-avoiding-rate-limiting#catch\">Catching errors caused by rate limiting</a>.</p>
     *
     * @return <p>Ok</p> (status code 200)
     */
    @Post("/api/v2/suspended_tickets/export")
    Mono<@Valid SuspendedTicketsExportResponse> exportSuspendedTickets();

    /**
     * {@summary List Suspended Tickets}
     * <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage suspended tickets on Enterprise plans</li> <li>Unrestricted agents on all other plans</li> </ul> <h4>Sorting</h4> <p>You can sort the tickets with the <code>sort_by</code> and <code>sort_order</code> query string parameters.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param sortBy <p>The field to sort the suspended tickets by. One of \"author_email\", \"cause\", \"created_at\", or \"subject\"</p> (optional)
     * @param sortOrder <p>The order in which to sort the suspended tickets.  This can take value <code>asc</code> or <code>desc</code>.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/suspended_tickets")
    Mono<@Valid SuspendedTicketsResponse> listSuspendedTickets(
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary Recover Suspended Ticket}
     * <p><strong>Note</strong>: During recovery, the API sets the requester to the authenticated agent who called the API, not the original requester. This prevents the ticket from being re-suspended after recovery. To preserve the original requester, use the <a href=\"#recover-multiple-suspended-tickets\">Recover Multiple Suspended Tickets</a> endpoint with the single ticket.</p> <p>This endpoint does not queue an asynchronous job that can be tracked from <a href=\"/api-reference/ticketing/ticket-management/job_statuses/\">Job Statuses</a>. Instead, it processes the request with a synchronous response.    - If all recoveries are successful, it returns a 200 with a <code>tickets</code> array in the response.    - If all recoveries fail, it returns a 422 with a <code>suspended_tickets</code> array in the response.    - If there is a mixture of successes and failures in a single call, it returns a 422 with a <code>suspended_tickets</code> array of the failures in the response.</p> <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage suspended tickets on Enterprise plans</li> <li>Unrestricted agents on all other plans</li> </ul>
     *
     * @param id <p>id of the suspended ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Recovery failed response</p> (status code 422)
     */
    @Put("/api/v2/suspended_tickets/{id}/recover")
    Mono<@Valid RecoverSuspendedTicketResponse> recoverSuspendedTicket(
        @PathVariable("id") @NotNull BigDecimal id
    );

    /**
     * {@summary Recover Multiple Suspended Tickets}
     * <p>Accepts up to 100 ids (the auto-generated id, not the ticket id.) Note that suspended tickets that fail to be recovered are still included in the response.</p> <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage suspended tickets on Enterprise plans</li> <li>Unrestricted agents on all other plans</li> </ul>
     *
     * @param ids <p>A comma separated list of ids of suspended tickets to recover.</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/suspended_tickets/recover_many")
    Mono<@Valid RecoverSuspendedTicketsResponse> recoverSuspendedTickets(
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary Show Suspended Ticket}
     * <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage suspended tickets on Enterprise plans</li> <li>Unrestricted agents on all other plans</li> </ul>
     *
     * @param id <p>id of the suspended ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/suspended_tickets/{id}")
    Mono<@Valid SuspendedTicketsResponse> showSuspendedTickets(
        @PathVariable("id") @NotNull BigDecimal id
    );

    /**
     * {@summary Suspended Ticket Attachments}
     * <p>Makes copies of any attachments on a suspended ticket and returns them as <a href=\"/api-reference/ticketing/tickets/ticket-attachments/\">attachment tokens</a>. If the  ticket is manually recovered, you can include the attachment tokens on the new ticket.</p> <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage suspended tickets on Enterprise plans</li> <li>Unrestricted agents on all other plans</li> </ul>
     *
     * @param id <p>id of the suspended ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/suspended_tickets/{id}/attachments")
    Mono<@Valid SuspendedTicketsAttachmentsResponse> suspendedTicketsAttachments(
        @PathVariable("id") @NotNull BigDecimal id
    );
}