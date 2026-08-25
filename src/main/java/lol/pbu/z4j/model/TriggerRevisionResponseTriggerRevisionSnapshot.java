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
import lol.pbu.z4j.model.TriggerActionObject;
import lol.pbu.z4j.model.TriggerConditionsObject;
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
 * TriggerRevisionResponseTriggerRevisionSnapshot
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TriggerRevisionResponseTriggerRevisionSnapshot.JSON_PROPERTY_ACTIONS,
    TriggerRevisionResponseTriggerRevisionSnapshot.JSON_PROPERTY_ACTIVE,
    TriggerRevisionResponseTriggerRevisionSnapshot.JSON_PROPERTY_CONDITIONS,
    TriggerRevisionResponseTriggerRevisionSnapshot.JSON_PROPERTY_DESCRIPTION,
    TriggerRevisionResponseTriggerRevisionSnapshot.JSON_PROPERTY_TITLE,
})
@Serdeable
public class TriggerRevisionResponseTriggerRevisionSnapshot {

    public static final String JSON_PROPERTY_ACTIONS = "actions";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CONDITIONS = "conditions";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_TITLE = "title";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerActionObject> actions;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CONDITIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TriggerConditionsObject conditions;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    /**
     * Add an item to the actions property in a chainable fashion.
     *
     * @return The same instance of TriggerRevisionResponseTriggerRevisionSnapshot for chaining.
     */
    public TriggerRevisionResponseTriggerRevisionSnapshot addActionsItem(TriggerActionObject actionsItem) {
        if (actions == null) {
            actions = new ArrayList<>();
        }
        actions.add(actionsItem);
        return this;
    }

}