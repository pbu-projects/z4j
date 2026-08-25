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
import lol.pbu.z4j.model.AccessRuleFieldDefinition;
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
 * AccessRuleDefinitionsResponseDefinitions
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccessRuleDefinitionsResponseDefinitions.JSON_PROPERTY_CONDITIONS_ALL,
    AccessRuleDefinitionsResponseDefinitions.JSON_PROPERTY_CONDITIONS_ANY,
})
@Serdeable
public class AccessRuleDefinitionsResponseDefinitions {

    public static final String JSON_PROPERTY_CONDITIONS_ALL = "conditions_all";
    public static final String JSON_PROPERTY_CONDITIONS_ANY = "conditions_any";

    /**
     * <p>Available field definitions for 'all' conditions (AND logic)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONDITIONS_ALL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid AccessRuleFieldDefinition> conditionsAll;

    /**
     * <p>Available field definitions for 'any' conditions (OR logic)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONDITIONS_ANY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid AccessRuleFieldDefinition> conditionsAny;

    /**
     * Add an item to the conditionsAll property in a chainable fashion.
     *
     * @return The same instance of AccessRuleDefinitionsResponseDefinitions for chaining.
     */
    public AccessRuleDefinitionsResponseDefinitions addConditionsAllItem(AccessRuleFieldDefinition conditionsAllItem) {
        if (conditionsAll == null) {
            conditionsAll = new ArrayList<>();
        }
        conditionsAll.add(conditionsAllItem);
        return this;
    }

    /**
     * Add an item to the conditionsAny property in a chainable fashion.
     *
     * @return The same instance of AccessRuleDefinitionsResponseDefinitions for chaining.
     */
    public AccessRuleDefinitionsResponseDefinitions addConditionsAnyItem(AccessRuleFieldDefinition conditionsAnyItem) {
        if (conditionsAny == null) {
            conditionsAny = new ArrayList<>();
        }
        conditionsAny.add(conditionsAnyItem);
        return this;
    }

}