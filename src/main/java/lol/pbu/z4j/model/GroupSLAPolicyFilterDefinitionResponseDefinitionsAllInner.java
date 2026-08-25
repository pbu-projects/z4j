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
import lol.pbu.z4j.model.GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInnerOperatorsInner;
import lol.pbu.z4j.model.GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInnerValues;
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
 * GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInner
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_GROUP,
    GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_OPERATORS,
    GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_TITLE,
    GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_VALUE,
    GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInner.JSON_PROPERTY_VALUES,
})
@Serdeable
public class GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInner {

    public static final String JSON_PROPERTY_GROUP = "group";
    public static final String JSON_PROPERTY_OPERATORS = "operators";
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
    private GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInnerValues values;

    /**
     * Add an item to the operators property in a chainable fashion.
     *
     * @return The same instance of GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInner for chaining.
     */
    public GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInner addOperatorsItem(GroupSLAPolicyFilterDefinitionResponseDefinitionsAllInnerOperatorsInner operatorsItem) {
        if (operators == null) {
            operators = new ArrayList<>();
        }
        operators.add(operatorsItem);
        return this;
    }

}