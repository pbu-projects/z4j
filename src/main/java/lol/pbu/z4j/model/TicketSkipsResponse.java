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
import lol.pbu.z4j.model.TicketSkipObject;
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
 * TicketSkipsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(TicketSkipsResponse.JSON_PROPERTY_SKIPS)
@Serdeable
public class TicketSkipsResponse {

    public static final String JSON_PROPERTY_SKIPS = "skips";

    @Nullable
    @JsonProperty(JSON_PROPERTY_SKIPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TicketSkipObject> skips;

    /**
     * Add an item to the skips property in a chainable fashion.
     *
     * @return The same instance of TicketSkipsResponse for chaining.
     */
    public TicketSkipsResponse addSkipsItem(TicketSkipObject skipsItem) {
        if (skips == null) {
            skips = new ArrayList<>();
        }
        skips.add(skipsItem);
        return this;
    }

}