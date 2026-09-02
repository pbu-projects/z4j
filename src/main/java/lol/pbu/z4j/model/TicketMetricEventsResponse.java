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
package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * TicketMetricEventsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketMetricEventsResponse.JSON_PROPERTY_TICKET_METRIC_EVENTS,
    TicketMetricEventsResponse.JSON_PROPERTY_COUNT,
    TicketMetricEventsResponse.JSON_PROPERTY_END_TIME,
    TicketMetricEventsResponse.JSON_PROPERTY_NEXT_PAGE,
})
@Serdeable
public class TicketMetricEventsResponse {

    public static final String JSON_PROPERTY_TICKET_METRIC_EVENTS = "ticket_metric_events";
    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_END_TIME = "end_time";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_METRIC_EVENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TicketMetricEventBaseObject> ticketMetricEvents;

    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long count;

    @Nullable
    @JsonProperty(JSON_PROPERTY_END_TIME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long endTime;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    /**
     * Add an item to the ticketMetricEvents property in a chainable fashion.
     *
     * @return The same instance of TicketMetricEventsResponse for chaining.
     */
    public TicketMetricEventsResponse addTicketMetricEventsItem(TicketMetricEventBaseObject ticketMetricEventsItem) {
        if (ticketMetricEvents == null) {
            ticketMetricEvents = new ArrayList<>();
        }
        ticketMetricEvents.add(ticketMetricEventsItem);
        return this;
    }

}