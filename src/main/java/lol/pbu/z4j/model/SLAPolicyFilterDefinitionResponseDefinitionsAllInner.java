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
 * SLAPolicyFilterDefinitionResponseDefinitionsAllInner
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_GROUP,
    SLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_OPERATORS,
    SLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_TARGET,
    SLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_TITLE,
    SLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_VALUE,
    SLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_VALUES,
})
@Serdeable
public class SLAPolicyFilterDefinitionResponseDefinitionsAllInner {

    public static final String JSON_PROPERTY_GROUP = "group";
    public static final String JSON_PROPERTY_OPERATORS = "operators";
    public static final String JSON_PROPERTY_TARGET = "target";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_VALUES = "values";

    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String group;

    @Nullable
    @JsonProperty(JSON_PROPERTY_OPERATORS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInnerOperatorsInner> operators;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TARGET)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String target;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    @Nullable
    @JsonProperty(JSON_PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String value;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VALUES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SLAPolicyFilterDefinitionResponseDefinitionsAllInnerValues values;

    /**
     * Add an item to the operators property in a chainable fashion.
     *
     * @return The same instance of SLAPolicyFilterDefinitionResponseDefinitionsAllInner for chaining.
     */
    public SLAPolicyFilterDefinitionResponseDefinitionsAllInner addOperatorsItem(GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInnerOperatorsInner operatorsItem) {
        if (operators == null) {
            operators = new ArrayList<>();
        }
        operators.add(operatorsItem);
        return this;
    }

}