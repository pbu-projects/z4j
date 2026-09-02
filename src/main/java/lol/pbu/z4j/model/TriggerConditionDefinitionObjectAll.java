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
 * TriggerConditionDefinitionObjectAll
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TriggerConditionDefinitionObjectAll.JSON_PROPERTY_GROUP,
    TriggerConditionDefinitionObjectAll.JSON_PROPERTY_NULLABLE,
    TriggerConditionDefinitionObjectAll.JSON_PROPERTY_OPERATORS,
    TriggerConditionDefinitionObjectAll.JSON_PROPERTY_REPEATABLE,
    TriggerConditionDefinitionObjectAll.JSON_PROPERTY_SUBJECT,
    TriggerConditionDefinitionObjectAll.JSON_PROPERTY_TITLE,
    TriggerConditionDefinitionObjectAll.JSON_PROPERTY_TYPE,
    TriggerConditionDefinitionObjectAll.JSON_PROPERTY_VALUES,
})
@Serdeable
public class TriggerConditionDefinitionObjectAll {

    public static final String JSON_PROPERTY_GROUP = "group";
    public static final String JSON_PROPERTY_NULLABLE = "nullable";
    public static final String JSON_PROPERTY_OPERATORS = "operators";
    public static final String JSON_PROPERTY_REPEATABLE = "repeatable";
    public static final String JSON_PROPERTY_SUBJECT = "subject";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_VALUES = "values";

    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String group;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NULLABLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean nullable;

    @Nullable
    @JsonProperty(JSON_PROPERTY_OPERATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid DefinitionsResponseDefinitionsConditionsAllInnerOperatorsInner> operators;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REPEATABLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean repeatable;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

    @Nullable
    @JsonProperty(JSON_PROPERTY_VALUES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid DefinitionsResponseDefinitionsConditionsAllInnerValuesInner> values;

    /**
     * Add an item to the operators property in a chainable fashion.
     *
     * @return The same instance of TriggerConditionDefinitionObjectAll for chaining.
     */
    public TriggerConditionDefinitionObjectAll addOperatorsItem(DefinitionsResponseDefinitionsConditionsAllInnerOperatorsInner operatorsItem) {
        if (operators == null) {
            operators = new ArrayList<>();
        }
        operators.add(operatorsItem);
        return this;
    }

    /**
     * Add an item to the values property in a chainable fashion.
     *
     * @return The same instance of TriggerConditionDefinitionObjectAll for chaining.
     */
    public TriggerConditionDefinitionObjectAll addValuesItem(DefinitionsResponseDefinitionsConditionsAllInnerValuesInner valuesItem) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(valuesItem);
        return this;
    }

}