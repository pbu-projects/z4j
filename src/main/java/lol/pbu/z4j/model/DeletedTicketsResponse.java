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
        DeletedTicketsResponse.JSON_PROPERTY_DELETED_TICKETS,
        DeletedTicketsResponse.JSON_PROPERTY_COUNT,
        DeletedTicketsResponse.JSON_PROPERTY_NEXT_PAGE,
        DeletedTicketsResponse.JSON_PROPERTY_PREVIOUS_PAGE
})
public record DeletedTicketsResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_DELETED_TICKETS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid DeletedTicket> deletedTickets,
        @Nullable @JsonProperty(JSON_PROPERTY_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long count,
        @Nullable @JsonProperty(JSON_PROPERTY_NEXT_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String nextPage,
        @Nullable @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String previousPage
) implements DeletedTicketsResponseBuilder.With {

    public static final String JSON_PROPERTY_DELETED_TICKETS = "deleted_tickets";
    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";

    public DeletedTicketsResponse addDeletedTicketsItem(DeletedTicket item) {
        List<DeletedTicket> list = new ArrayList<>(deletedTickets != null ? deletedTickets : List.of());
        list.add(item);
        return new DeletedTicketsResponse(list, count, nextPage, previousPage);
    }
}
