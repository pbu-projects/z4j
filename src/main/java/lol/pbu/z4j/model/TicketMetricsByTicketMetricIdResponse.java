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

/**
 * TicketMetricsByTicketMetricIdResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(TicketMetricsByTicketMetricIdResponse.JSON_PROPERTY_TICKET_METRIC)
@Serdeable
public class TicketMetricsByTicketMetricIdResponse {

    public static final String JSON_PROPERTY_TICKET_METRIC = "ticket_metric";

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_METRIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private @Valid TicketMetricObject ticketMetric;

    /**
     * Add an item to the ticketMetric property in a chainable fashion.
     *
     * @return The same instance of TicketMetricsByTicketMetricIdResponse for chaining.
     */
    public TicketMetricsByTicketMetricIdResponse ticketMetric(TicketMetricObject ticketMetricItem) {
        this.ticketMetric = ticketMetricItem;
        return this;
    }

}