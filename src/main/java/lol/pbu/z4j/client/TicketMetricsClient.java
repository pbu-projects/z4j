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
import lol.pbu.z4j.model.TicketMetricsByTicketMetricIdResponse;
import lol.pbu.z4j.model.TicketMetricsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TicketMetricsClient {

    /**
     * {@summary List Ticket Metrics}
     * <p>Returns a list of tickets with their metrics.</p> <p>Tickets are ordered chronologically by created date, from newest to oldest. The last ticket listed may not be the absolute oldest ticket in your account due to ticket archiving.</p> <p>Archived tickets are not included in the response. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203657756\">About archived tickets</a> in Zendesk help.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_metrics")
    Mono<@Valid TicketMetricsResponse> listTicketMetrics();

    /**
     * {@summary Show Ticket Metrics}
     * <p>Returns a specific metric, or the metrics of a specific ticket.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketMetricId <p>The id of the ticket metric to retrieve</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_metrics/{ticket_metric_id}")
    Mono<@Valid TicketMetricsByTicketMetricIdResponse> showTicketMetrics(
        @PathVariable("ticket_metric_id") @NotNull String ticketMetricId
    );

    /**
     * {@summary Show Ticket Metrics By Ticket}
     * <p>Returns the metrics for a specific ticket.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/metrics")
    Mono<@Valid TicketMetricsByTicketMetricIdResponse> showTicketMetricsByTicket(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );
}