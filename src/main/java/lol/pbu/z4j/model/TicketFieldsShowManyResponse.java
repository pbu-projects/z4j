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
import lol.pbu.z4j.model.TicketFieldObject;
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
 * TicketFieldsShowManyResponse
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    TicketFieldsShowManyResponse.JSON_PROPERTY_TICKET_FIELDS,
    TicketFieldsShowManyResponse.JSON_PROPERTY_COUNT,
    TicketFieldsShowManyResponse.JSON_PROPERTY_NEXT_PAGE,
    TicketFieldsShowManyResponse.JSON_PROPERTY_PREVIOUS_PAGE,
})
@Serdeable
public class TicketFieldsShowManyResponse {

    public static final String JSON_PROPERTY_TICKET_FIELDS = "ticket_fields";
    public static final String JSON_PROPERTY_COUNT = "count";
    public static final String JSON_PROPERTY_NEXT_PAGE = "next_page";
    public static final String JSON_PROPERTY_PREVIOUS_PAGE = "previous_page";

    @NotNull
    @JsonProperty(JSON_PROPERTY_TICKET_FIELDS)
    private List<@Valid TicketFieldObject> ticketFields = new ArrayList<>();

    /**
     * <p>Total count when not using cursor pagination</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long count;

    /**
     * <p>URL or cursor to the next page when paginated; null otherwise</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NEXT_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String nextPage;

    /**
     * <p>URL or cursor to the previous page when paginated; null otherwise</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PREVIOUS_PAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String previousPage;

    public TicketFieldsShowManyResponse(List<@Valid TicketFieldObject> ticketFields) {
        this.ticketFields = ticketFields;
    }

    /**
     * Add an item to the ticketFields property in a chainable fashion.
     *
     * @return The same instance of TicketFieldsShowManyResponse for chaining.
     */
    public TicketFieldsShowManyResponse addTicketFieldsItem(TicketFieldObject ticketFieldsItem) {
        ticketFields.add(ticketFieldsItem);
        return this;
    }

}