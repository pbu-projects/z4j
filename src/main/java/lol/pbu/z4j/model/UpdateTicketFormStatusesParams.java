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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * UpdateTicketFormStatusesParams
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder(UpdateTicketFormStatusesParams.JSON_PROPERTY_TICKET_FORM_STATUS)
@Serdeable
public class UpdateTicketFormStatusesParams {

    public static final String JSON_PROPERTY_TICKET_FORM_STATUS = "ticket_form_status";

    @NotNull
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_STATUS)
    private List<@Valid TicketFormStatusesUpdateParams> ticketFormStatus = new ArrayList<>();

    public UpdateTicketFormStatusesParams(List<@Valid TicketFormStatusesUpdateParams> ticketFormStatus) {
        this.ticketFormStatus = ticketFormStatus;
    }

    /**
     * Add an item to the ticketFormStatus property in a chainable fashion.
     *
     * @return The same instance of UpdateTicketFormStatusesParams for chaining.
     */
    public UpdateTicketFormStatusesParams addTicketFormStatusItem(TicketFormStatusesUpdateParams ticketFormStatusItem) {
        ticketFormStatus.add(ticketFormStatusItem);
        return this;
    }

}