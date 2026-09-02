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
 * <p>See <a href=\"/api-reference/ticketing/tickets/tickets/\">Tickets</a> for a detailed example.</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    CursorBasedExportIncrementalTicketsResponse.JSON_PROPERTY_AFTER_CURSOR,
    CursorBasedExportIncrementalTicketsResponse.JSON_PROPERTY_AFTER_URL,
    CursorBasedExportIncrementalTicketsResponse.JSON_PROPERTY_BEFORE_CURSOR,
    CursorBasedExportIncrementalTicketsResponse.JSON_PROPERTY_BEFORE_URL,
    CursorBasedExportIncrementalTicketsResponse.JSON_PROPERTY_END_OF_STREAM,
    CursorBasedExportIncrementalTicketsResponse.JSON_PROPERTY_TICKETS,
})
@Serdeable
public class CursorBasedExportIncrementalTicketsResponse {

    public static final String JSON_PROPERTY_AFTER_CURSOR = "after_cursor";
    public static final String JSON_PROPERTY_AFTER_URL = "after_url";
    public static final String JSON_PROPERTY_BEFORE_CURSOR = "before_cursor";
    public static final String JSON_PROPERTY_BEFORE_URL = "before_url";
    public static final String JSON_PROPERTY_END_OF_STREAM = "end_of_stream";
    public static final String JSON_PROPERTY_TICKETS = "tickets";

    @Nullable
    @JsonProperty(JSON_PROPERTY_AFTER_CURSOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String afterCursor;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AFTER_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String afterUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BEFORE_CURSOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String beforeCursor;

    @Nullable
    @JsonProperty(JSON_PROPERTY_BEFORE_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String beforeUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_END_OF_STREAM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean endOfStream;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TicketObject> tickets;

    /**
     * Add an item to the tickets property in a chainable fashion.
     *
     * @return The same instance of CursorBasedExportIncrementalTicketsResponse for chaining.
     */
    public CursorBasedExportIncrementalTicketsResponse addTicketsItem(TicketObject ticketsItem) {
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        tickets.add(ticketsItem);
        return this;
    }

}