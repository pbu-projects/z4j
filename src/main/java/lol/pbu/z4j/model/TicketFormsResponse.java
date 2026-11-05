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
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Serdeable
@RecordBuilder
@JsonPropertyOrder(TicketFormsResponse.JSON_PROPERTY_TICKET_FORMS)
public record TicketFormsResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_TICKET_FORMS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid TicketForm> ticketForms
) implements TicketFormsResponseBuilder.With {

    public static final String JSON_PROPERTY_TICKET_FORMS = "ticket_forms";

    public TicketFormsResponse addTicketFormsItem(TicketForm ticketFormsItem) {
        List<TicketForm> list = new ArrayList<>(ticketForms != null ? ticketForms : List.of());
        list.add(ticketFormsItem);
        return new TicketFormsResponse(list);
    }
}
