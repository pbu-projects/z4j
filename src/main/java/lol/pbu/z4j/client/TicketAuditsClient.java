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
import lol.pbu.z4j.model.TicketAuditResponse;
import lol.pbu.z4j.model.TicketAuditsCountResponse;
import lol.pbu.z4j.model.TicketAuditsResponse;
import lol.pbu.z4j.model.TicketAuditsResponseNoneCursor;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TicketAuditsClient {

    /**
     * {@summary Count Audits for a Ticket}
     * <p>Returns an approximate count of audits for a specified ticket. If the count exceeds 100,000, the count will return a cached result.  This cached result will update every 24 hours.</p> <p>The <code>count[refreshed_at]</code> property is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: If the total number of audits for a ticket exceeds 100,000, this endpoint returns a count of 100,000 with a <code>count[refreshed_at]</code> value of null. This value is cached for 24 hours, during which any requests returns the same count and timestamp. After 24 hours, the endpoint temporarily shows the same count again before providing an updated total.</p> <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Count of audits on a ticket</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/audits/count")
    Mono<@Valid TicketAuditsCountResponse> countAuditsForTicket(
        @PathVariable("ticket_id") @NotNull Integer ticketId
    );

    /**
     * {@summary List Audits for a Ticket}
     * <p>Lists the audits for a specified ticket.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <p><strong>Note</strong>: Audits for <a href=\"https://support.zendesk.com/hc/en-us/articles/4408887617050\">Archived Tickets</a> do not support pagination for this endpoint.</p> <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>OK response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/audits")
    Mono<@Valid TicketAuditsResponseNoneCursor> listAuditsForTicket(
        @PathVariable("ticket_id") @NotNull Integer ticketId
    );

    /**
     * {@summary List All Ticket Audits}
     * <p>Returns ticket audits. Archived tickets are not included in the response. Use the <a href=\"#list-audits-for-a-ticket\">List Audits for a Ticket</a> endpoint to retrieve audit records for an archived ticket. To learn more about archived tickets, see <a href=\"https://support.zendesk.com/hc/en-us/articles/203657756\">About archived tickets</a>.</p> <p>This endpoint should not be used for capturing change data. When continually chasing the tail of a cursor, some records will be skipped. For this use case, use the <a href=\"/api-reference/ticketing/ticket-management/incremental_exports/#incremental-ticket-event-export\">Incremental Ticket Event Export API</a>.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param pageBefore <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.before_cursor</code> value from a previous request. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</p> (optional)
     * @param pageAfter <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.after_cursor</code> value from a previous request. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</p> (optional)
     * @param pageSize <p>Specifies how many records to be returned in the response. You can specify up to 100 records per page.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_audits")
    Mono<@Valid TicketAuditsResponse> listTicketAudits(
        @QueryValue("page[before]") @Nullable String pageBefore,
        @QueryValue("page[after]") @Nullable String pageAfter,
        @QueryValue("page[size]") @Nullable Integer pageSize
    );

    /**
     * {@summary Change a Comment From Public To Private}
     * <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param ticketAuditId <p>The ID of the ticket audit</p> (required)
     *
     * @return <p>description</p> (status code 200)
     */
    @Put("/api/v2/tickets/{ticket_id}/audits/{ticket_audit_id}/make_private")
    Mono<@NotNull String> makeTicketCommentPrivateFromAudits(
        @PathVariable("ticket_id") @NotNull Integer ticketId,
        @PathVariable("ticket_audit_id") @NotNull Integer ticketAuditId
    );

    /**
     * {@summary Show Audit}
     * <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param ticketAuditId <p>The ID of the ticket audit</p> (required)
     *
     * @return <p>OK response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/audits/{ticket_audit_id}")
    Mono<@Valid TicketAuditResponse> showTicketAudit(
        @PathVariable("ticket_id") @NotNull Integer ticketId,
        @PathVariable("ticket_audit_id") @NotNull Integer ticketAuditId
    );
}