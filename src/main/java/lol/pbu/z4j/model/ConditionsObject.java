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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>An object that describes the conditions under which the automation will execute. See <a href=\"/documentation/ticketing/reference-guides/conditions-reference\">Conditions reference</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ConditionsObject.JSON_PROPERTY_ALL,
    ConditionsObject.JSON_PROPERTY_ANY,
})
@Serdeable
public class ConditionsObject {

    public static final String JSON_PROPERTY_ALL = "all";
    public static final String JSON_PROPERTY_ANY = "any";

    /**
     * <p>Logical AND. Tickets must fulfill all of the conditions to be considered matching</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ALL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ConditionObject> all;

    /**
     * <p>Logical OR. Tickets may satisfy any of the conditions to be considered matching</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ANY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ConditionObject> any;

    /**
     * Add an item to the all property in a chainable fashion.
     *
     * @return The same instance of ConditionsObject for chaining.
     */
    public ConditionsObject addAllItem(ConditionObject allItem) {
        if (all == null) {
            all = new ArrayList<>();
        }
        all.add(allItem);
        return this;
    }

    /**
     * Add an item to the any property in a chainable fashion.
     *
     * @return The same instance of ConditionsObject for chaining.
     */
    public ConditionsObject addAnyItem(ConditionObject anyItem) {
        if (any == null) {
            any = new ArrayList<>();
        }
        any.add(anyItem);
        return this;
    }

}