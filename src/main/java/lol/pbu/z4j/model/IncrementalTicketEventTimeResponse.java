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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.List;

/**
 * Time-based incremental export response containing ticket events.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.7
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        IncrementalTicketEventTimeResponse.JSON_PROPERTY_TICKET_EVENTS,
        IncrementalTimePaginationResponse.JSON_PROPERTY_END_TIME,
        IncrementalTimePaginationResponse.JSON_PROPERTY_NEXT_PAGE,
        IncrementalTimePaginationResponse.JSON_PROPERTY_END_OF_STREAM,
        IncrementalTimePaginationResponse.JSON_PROPERTY_COUNT
})
public record IncrementalTicketEventTimeResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_TICKET_EVENTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<TicketEvent> ticketEvents,
        @Nullable @JsonProperty(JSON_PROPERTY_END_TIME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long endTime,
        @Nullable @JsonProperty(JSON_PROPERTY_NEXT_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String nextPage,
        @Nullable @JsonProperty(JSON_PROPERTY_END_OF_STREAM) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean endOfStream,
        @Nullable @JsonProperty(JSON_PROPERTY_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer count
) implements IncrementalTimePaginationResponse<TicketEvent>, IncrementalTicketEventTimeResponseBuilder.With {

    public static final String JSON_PROPERTY_TICKET_EVENTS = "ticket_events";

    public IncrementalTicketEventTimeResponse(@Nullable List<TicketEvent> ticketEvents) {
        this(ticketEvents, null, null, null, null);
    }

    @Override
    @JsonIgnore
    public List<TicketEvent> results() {
        return ticketEvents;
    }
}
