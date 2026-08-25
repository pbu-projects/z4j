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
import lol.pbu.z4j.model.TriggerConditionDiffObject;
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
 * TriggerConditionsDiffObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TriggerConditionsDiffObject.JSON_PROPERTY_ALL,
    TriggerConditionsDiffObject.JSON_PROPERTY_ANY,
})
@Serdeable
public class TriggerConditionsDiffObject {

    public static final String JSON_PROPERTY_ALL = "all";
    public static final String JSON_PROPERTY_ANY = "any";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ALL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerConditionDiffObject> all;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ANY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerConditionDiffObject> any;

    /**
     * Add an item to the all property in a chainable fashion.
     *
     * @return The same instance of TriggerConditionsDiffObject for chaining.
     */
    public TriggerConditionsDiffObject addAllItem(TriggerConditionDiffObject allItem) {
        if (all == null) {
            all = new ArrayList<>();
        }
        all.add(allItem);
        return this;
    }

    /**
     * Add an item to the any property in a chainable fashion.
     *
     * @return The same instance of TriggerConditionsDiffObject for chaining.
     */
    public TriggerConditionsDiffObject addAnyItem(TriggerConditionDiffObject anyItem) {
        if (any == null) {
            any = new ArrayList<>();
        }
        any.add(anyItem);
        return this;
    }

}