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
import lol.pbu.z4j.model.DefinitionsResponseDefinitionsConditionsAllInnerOperatorsInner;
import lol.pbu.z4j.model.DefinitionsResponseDefinitionsConditionsAllInnerValuesInner;
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
 * DefinitionsResponseDefinitionsConditionsAllInner
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    DefinitionsResponseDefinitionsConditionsAllInner.JSON_PROPERTY_GROUP,
    DefinitionsResponseDefinitionsConditionsAllInner.JSON_PROPERTY_NULLABLE,
    DefinitionsResponseDefinitionsConditionsAllInner.JSON_PROPERTY_OPERATORS,
    DefinitionsResponseDefinitionsConditionsAllInner.JSON_PROPERTY_REPEATABLE,
    DefinitionsResponseDefinitionsConditionsAllInner.JSON_PROPERTY_SUBJECT,
    DefinitionsResponseDefinitionsConditionsAllInner.JSON_PROPERTY_TITLE,
    DefinitionsResponseDefinitionsConditionsAllInner.JSON_PROPERTY_TYPE,
    DefinitionsResponseDefinitionsConditionsAllInner.JSON_PROPERTY_VALUES,
})
@Serdeable
public class DefinitionsResponseDefinitionsConditionsAllInner {

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
     * @return The same instance of DefinitionsResponseDefinitionsConditionsAllInner for chaining.
     */
    public DefinitionsResponseDefinitionsConditionsAllInner addOperatorsItem(DefinitionsResponseDefinitionsConditionsAllInnerOperatorsInner operatorsItem) {
        if (operators == null) {
            operators = new ArrayList<>();
        }
        operators.add(operatorsItem);
        return this;
    }

    /**
     * Add an item to the values property in a chainable fashion.
     *
     * @return The same instance of DefinitionsResponseDefinitionsConditionsAllInner for chaining.
     */
    public DefinitionsResponseDefinitionsConditionsAllInner addValuesItem(DefinitionsResponseDefinitionsConditionsAllInnerValuesInner valuesItem) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(valuesItem);
        return this;
    }

}