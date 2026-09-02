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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * TicketFormStatusesResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(TicketFormStatusesResponse.JSON_PROPERTY_TICKET_FORM_STATUSES)
@Serdeable
public class TicketFormStatusesResponse {

    public static final String JSON_PROPERTY_TICKET_FORM_STATUSES = "ticket_form_statuses";

    @Nullable
    @Size(max = 1)
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_STATUSES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TicketFormStatusObject> ticketFormStatuses;

    /**
     * Add an item to the ticketFormStatuses property in a chainable fashion.
     *
     * @return The same instance of TicketFormStatusesResponse for chaining.
     */
    public TicketFormStatusesResponse addTicketFormStatusesItem(TicketFormStatusObject ticketFormStatusesItem) {
        if (ticketFormStatuses == null) {
            ticketFormStatuses = new ArrayList<>();
        }
        ticketFormStatuses.add(ticketFormStatusesItem);
        return this;
    }

}