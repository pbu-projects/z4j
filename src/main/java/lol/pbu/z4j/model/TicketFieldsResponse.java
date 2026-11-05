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
@JsonPropertyOrder({
        TicketFieldsResponse.JSON_PROPERTY_TICKET_FIELDS,
        TicketFieldsResponse.JSON_PROPERTY_META,
        TicketFieldsResponse.JSON_PROPERTY_LINKS
})
public record TicketFieldsResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_TICKET_FIELDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid TicketField> ticketFields,
        @Nullable @JsonProperty(JSON_PROPERTY_META) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Meta meta,
        @Nullable @JsonProperty(JSON_PROPERTY_LINKS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Links links
) implements TicketFieldsResponseBuilder.With {

    public static final String JSON_PROPERTY_TICKET_FIELDS = "ticket_fields";
    public static final String JSON_PROPERTY_META = "meta";
    public static final String JSON_PROPERTY_LINKS = "links";

    public TicketFieldsResponse addTicketFieldsItem(TicketField ticketFieldsItem) {
        List<TicketField> list = new ArrayList<>(ticketFields != null ? ticketFields : List.of());
        list.add(ticketFieldsItem);
        return new TicketFieldsResponse(list, meta, links);
    }
}
