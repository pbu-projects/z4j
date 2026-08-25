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
import lol.pbu.z4j.model.TicketsUpdateRequestOneOf1TicketsInner;
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
 * TicketsUpdateRequestOneOf1
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder(TicketsUpdateRequestOneOf1.JSON_PROPERTY_TICKETS)
@Serdeable
public class TicketsUpdateRequestOneOf1 implements TicketsUpdateRequest {

    public static final String JSON_PROPERTY_TICKETS = "tickets";

    @NotNull
    @Size(max = 100)
    @JsonProperty(JSON_PROPERTY_TICKETS)
    private List<@Valid TicketsUpdateRequestOneOf1TicketsInner> tickets = new ArrayList<>();

    public TicketsUpdateRequestOneOf1(List<@Valid TicketsUpdateRequestOneOf1TicketsInner> tickets) {
        this.tickets = tickets;
    }

    /**
     * Add an item to the tickets property in a chainable fashion.
     *
     * @return The same instance of TicketsUpdateRequestOneOf1 for chaining.
     */
    public TicketsUpdateRequestOneOf1 addTicketsItem(TicketsUpdateRequestOneOf1TicketsInner ticketsItem) {
        tickets.add(ticketsItem);
        return this;
    }

}