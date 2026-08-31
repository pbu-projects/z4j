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
import lol.pbu.z4j.model.CursorBasedExportIncrementalTicketsResponse;
import lol.pbu.z4j.model.CursorBasedExportIncrementalUsersResponse;
import lol.pbu.z4j.model.ExportIncrementalOrganizationsResponse;
import lol.pbu.z4j.model.ExportIncrementalTicketEventsResponse;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.TimeBasedExportIncrementalTicketsResponse;
import lol.pbu.z4j.model.TimeBasedExportIncrementalUsersResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface IncrementalExportClient {

    /**
     * {@summary Incremental Organization Export}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Sideloading</h4> <p>See <a href=\"/documentation/ticketing/using-the-zendesk-api/side_loading/#supported-endpoints\">Organizations sideloads</a>.</p>
     *
     * @param startTime <p>The time to start the incremental export from. Must be at least one minute in the past. Data isn't provided for the most recent minute</p> (required)
     * @param perPage <p>The number of records to return per page</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/incremental/organizations")
    Mono<@Valid ExportIncrementalOrganizationsResponse> incrementalOrganizationExport(
        @QueryValue("start_time") @NotNull Long startTime,
        @QueryValue("per_page") @Nullable Long perPage
    );

    /**
     * {@summary Incremental Sample Export}
     * <p>Use this endpoint to test the incremental export format. It's more strict in terms of rate limiting, at 10 requests per 20 minutes instead of 10 requests per minute. It also returns only up to 50 results per request. Otherwise, it's identical to the above APIs.</p> <p>Use the <code>incremental_resource</code> parameter to specify the resource. Possible values are \"tickets\", \"ticket_events\", \"users\", or \"organizations\".</p>
     *
     * @param startTime <p>The time to start the incremental export from. Must be at least one minute in the past. Data isn't provided for the most recent minute</p> (required)
     * @param incrementalResource <p>The resource requested for incremental sample export</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/incremental/{incremental_resource}/sample")
    Mono<@Valid TimeBasedExportIncrementalTicketsResponse> incrementalSampleExport(
        @QueryValue("start_time") @NotNull Long startTime,
        @PathVariable("incremental_resource") @NotNull String incrementalResource
    );

    /**
     * {@summary Incremental Ticket Event Export}
     * <p>Returns a stream of changes that occurred on tickets, excluding events occuring within one minute of the request. Each event is tied to an update on a ticket and contains all the fields that were updated in that change. For more information, see:</p> <ul> <li><a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api#exporting-ticket-events\">Exporting ticket events</a> in <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api\">Using the Incremental Exports API</a></li> <li><a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api#time-based-incremental-exports\">Time-based incremental exports</a> in <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api\">Using the Incremental Exports API</a></li> </ul> <p>You can include comments in the event stream by using the <code>comment_events</code> sideload. See Sideloading below. If you don't specify the sideload, any comment present in the ticket update is described only by Boolean <code>comment_present</code> and <code>comment_public</code> object properties in the event's <code>child_events</code> array. The comment itself is not included.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Sideloading</h4> <p>The endpoint supports the <code>comment_events</code> sideload. Any comment present in the ticket update is listed as an object in the event's <code>child_events</code> array. Example:</p> <p><code>js \"child_events\": [   {     \"id\": 91048994488,     \"via\": {       \"channel\": \"api\",       \"source\": {\"from\":{},\"to\":{},\"rel\":null}},     \"via_reference_id\":null,     \"type\": \"Comment\",     \"author_id\": 5031726587,     \"body\": \"This is a comment\",     \"html_body\": \"&amp;lt;div class=\"zd-comment\"&amp;gt;&amp;lt;p dir=\"auto\"&amp;gt;This is a comment&amp;lt;/p&amp;gt;\",     \"public\": true,     \"attachments\": [],     \"audit_id\": 91048994468,     \"created_at\": \"2009-06-25T10:15:18Z\",     \"event_type\": \"Comment\"   },   ... ], ...</code></p>
     *
     * @param startTime <p>The time to start the incremental export from. Must be at least one minute in the past. Data isn't provided for the most recent minute</p> (required)
     * @param supportTypeScope <p>Lists tickets by support type. Possible values are \"all\", \"agent\", or \"ai_agent\". Defaults to \"agent\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/incremental/ticket_events")
    Mono<@Valid ExportIncrementalTicketEventsResponse> incrementalTicketEvents(
        @QueryValue("start_time") @NotNull Long startTime,
        @QueryValue("support_type_scope") @Nullable String supportTypeScope
    );

    /**
     * {@summary Incremental Ticket Export, Cursor Based}
     * <p>Returns the tickets that changed since the start time. For more information, see <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api#exporting-tickets\">Exporting tickets</a> in <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api\">Using the Incremental Exports API</a>.</p> <p>This endpoint supports cursor-based incremental exports. Cursor-based exports are highly encouraged because they provide more consistent performance and response body sizes. For more information, see <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api#cursor-based-incremental-exports\">Cursor-based incremental exports</a> in <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api\">Using the Incremental Exports API</a>.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Sideloading</h4> <p>See <a href=\"/documentation/ticketing/using-the-zendesk-api/side_loading/#supported-endpoints\">Tickets sideloads</a>. For performance reasons, <code>last_audits</code> sideloads aren't supported.</p>
     *
     * @param startTime <p>The time to start the incremental export from. Must be at least one minute in the past. Data isn't provided for the most recent minute</p> (required)
     * @param cursor <p>The cursor pointer to work with for all subsequent exports after the initial request</p> (optional)
     * @param supportTypeScope <p>Lists tickets by support type. Possible values are \"all\", \"agent\", or \"ai_agent\". Defaults to \"agent\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/incremental/tickets/cursor")
    Mono<@Valid CursorBasedExportIncrementalTicketsResponse> incrementalTicketExportCursor(
        @QueryValue("start_time") @NotNull Long startTime,
        @QueryValue("cursor") @Nullable String cursor,
        @QueryValue("support_type_scope") @Nullable String supportTypeScope
    );

    /**
     * {@summary Incremental Ticket Export, Time Based}
     * <p>Returns the tickets that changed since the start time. For more information, see <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api#exporting-tickets\">Exporting tickets</a> in <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api\">Using the Incremental Exports API</a>.</p> <p>This endpoint supports time-based incremental exports. For more information, see <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api#time-based-incremental-exports\">Time-based incremental exports</a> in <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api\">Using the Incremental Exports API</a>. You can also return tickets using cursor-based pagination. See <a href=\"#incremental-ticket-export-cursor-based\">Incremental Ticket Export, Cursor Based</a>.</p> <p>The results include tickets that were updated by the system. See <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api#excluding-system-updated-tickets-time-based-exports\">Excluding system-updated tickets</a> in <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api\">Using the Incremental Exports API</a>.</p> <p>The endpoint can return tickets with an <code>updated_at</code> time that's earlier than the <code>start_time</code> time. The reason is that the API compares the <code>start_time</code> with the ticket's <code>generated_timestamp</code> value, not its <code>updated_at</code> value. The <code>updated_at</code> value is updated only if the update generates a <a href=\"#incremental-ticket-event-export\">ticket event</a>. The <code>generated_timestamp</code> value is updated for all ticket updates, including system updates. If a system update occurs after a ticket event, the unchanged <code>updated_at</code> time will become earlier relative to the updated <code>generated_timestamp</code> time.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Sideloading</h4> <p>See <a href=\"/documentation/ticketing/using-the-zendesk-api/side_loading/#supported-endpoints\">Tickets sideloads</a>. For performance reasons, <code>last_audits</code> sideloads aren't supported.</p>
     *
     * @param startTime <p>The time to start the incremental export from. Must be at least one minute in the past. Data isn't provided for the most recent minute</p> (required)
     * @param supportTypeScope <p>Lists tickets by support type. Possible values are \"all\", \"agent\", or \"ai_agent\". Defaults to \"agent\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/incremental/tickets")
    Mono<@Valid TimeBasedExportIncrementalTicketsResponse> incrementalTicketExportTime(
        @QueryValue("start_time") @NotNull Long startTime,
        @QueryValue("support_type_scope") @Nullable String supportTypeScope
    );

    /**
     * {@summary Incremental User Export, Cursor Based}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Sideloading</h4> <p>See <a href=\"/documentation/ticketing/using-the-zendesk-api/side_loading/#supported-endpoints\">Users sideloads</a>.</p>
     *
     * @param startTime <p>The time to start the incremental export from. Must be at least one minute in the past. Data isn't provided for the most recent minute</p> (required)
     * @param cursor <p>The cursor pointer to work with for all subsequent exports after the initial request</p> (optional)
     * @param perPage <p>The number of records to return per page</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/incremental/users/cursor")
    Mono<@Valid CursorBasedExportIncrementalUsersResponse> incrementalUserExportCursor(
        @QueryValue("start_time") @NotNull Long startTime,
        @QueryValue("cursor") @Nullable String cursor,
        @QueryValue("per_page") @Nullable Long perPage
    );

    /**
     * {@summary Incremental User Export, Time Based}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Sideloading</h4> <p>See <a href=\"/documentation/ticketing/using-the-zendesk-api/side_loading/#supported-endpoints\">Users sideloads</a>.</p>
     *
     * @param startTime <p>The time to start the incremental export from. Must be at least one minute in the past. Data isn't provided for the most recent minute</p> (required)
     * @param perPage <p>The number of records to return per page</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/incremental/users")
    Mono<@Valid TimeBasedExportIncrementalUsersResponse> incrementalUserExportTime(
        @QueryValue("start_time") @NotNull Long startTime,
        @QueryValue("per_page") @Nullable Long perPage
    );
}