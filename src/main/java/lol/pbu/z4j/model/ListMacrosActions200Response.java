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
import java.util.Map;
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
 * ListMacrosActions200Response
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(ListMacrosActions200Response.JSON_PROPERTY_ACTIONS)
@Serdeable
public class ListMacrosActions200Response {

    public static final String JSON_PROPERTY_ACTIONS = "actions";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Map<String, Object>> actions;

    /**
     * Add an item to the actions property in a chainable fashion.
     *
     * @return The same instance of ListMacrosActions200Response for chaining.
     */
    public ListMacrosActions200Response addActionsItem(Map<String, Object> actionsItem) {
        if (actions == null) {
            actions = new ArrayList<>();
        }
        actions.add(actionsItem);
        return this;
    }

}