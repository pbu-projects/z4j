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

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.TicketMetricEventBaseObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import io.micronaut.core.annotation.Nullable;

/**
 * ExportIncrementalTicketEventsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ExportIncrementalTicketEventsResponse.JSON_PROPERTY_COUNT,
    ExportIncrementalTicketEventsResponse.JSON_PROPERTY_END_OF_STREAM,
    ExportIncrementalTicketEventsResponse.JSON_PROPERTY_END_TIME,
    ExportIncrementalTicketEventsResponse.JSON_PROPERTY_NEXT_PAGE,
    ExportIncrementalTicketEventsResponse.JSON_PROPERTY_TICKET_EVENTS,
})
@Serdeable
public class ExportIncrementalTicketEventsResponse {

    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_END_OF_STREAM = "end_of_stream";
    public static final String JSON_PROPERTY_END_TIME = "end_time";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_TICKET_EVENTS = "ticket_events";

    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long count;

    @Nullable
    @JsonProperty(JSON_PROPERTY_END_OF_STREAM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean endOfStream;

    @Nullable
    @JsonProperty(JSON_PROPERTY_END_TIME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long endTime;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_EVENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TicketMetricEventBaseObject> ticketEvents;

    /**
     * Add an item to the ticketEvents property in a chainable fashion.
     *
     * @return The same instance of ExportIncrementalTicketEventsResponse for chaining.
     */
    public ExportIncrementalTicketEventsResponse addTicketEventsItem(TicketMetricEventBaseObject ticketEventsItem) {
        if (ticketEvents == null) {
            ticketEvents = new ArrayList<>();
        }
        ticketEvents.add(ticketEventsItem);
        return this;
    }

}