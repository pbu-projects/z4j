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
import lol.pbu.z4j.model.TicketMetricEventsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TicketMetricEventsClient {

    /**
     * {@summary List Ticket Metric Events}
     * <p>Returns ticket metric events that occurred on or after the start time.</p> <p>Cursor pagination returns a maximum of 100 records per page. Events are listed in chronological order.</p> <p>If the results are not paginated, events will be returned as a time-based incremental export.</p> <p>See <a href=\"/documentation/ticketing/managing-tickets/using-the-incremental-export-api#time-based-incremental-exports\">Time-based incremental exports</a>.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param startTime <p>The Unix UTC epoch time of the oldest event you're interested in. Example: 1332034771.</p> (required)
     * @param includeChanges <p>This optional parameter enhances incremental data retrieval, delivering a consistent and accurate representation of data changes.</p> (optional)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/incremental/ticket_metric_events")
    Mono<@Valid TicketMetricEventsResponse> listTicketMetricEvents(
        @QueryValue("start_time") @NotNull Integer startTime,
        @QueryValue("include_changes") @Nullable Boolean includeChanges
    );
}