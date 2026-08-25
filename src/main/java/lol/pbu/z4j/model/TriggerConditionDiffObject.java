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
import lol.pbu.z4j.model.TriggerChangeObject;
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
 * TriggerConditionDiffObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TriggerConditionDiffObject.JSON_PROPERTY_FIELD,
    TriggerConditionDiffObject.JSON_PROPERTY_OPERATOR,
    TriggerConditionDiffObject.JSON_PROPERTY_VALUE,
})
@Serdeable
public class TriggerConditionDiffObject {

    public static final String JSON_PROPERTY_FIELD = "field";
    public static final String JSON_PROPERTY_OPERATOR = "operator";
    public static final String JSON_PROPERTY_VALUE = "value";

    /**
     * <p>An array of <a href=\"#change\">change</a> objects</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FIELD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerChangeObject> field;

    /**
     * <p>An array of <a href=\"#change\">change</a> objects</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OPERATOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerChangeObject> operator;

    /**
     * <p>An array of <a href=\"#change\">change</a> objects</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerChangeObject> value;

    /**
     * Add an item to the field property in a chainable fashion.
     *
     * @return The same instance of TriggerConditionDiffObject for chaining.
     */
    public TriggerConditionDiffObject addFieldItem(TriggerChangeObject fieldItem) {
        if (field == null) {
            field = new ArrayList<>();
        }
        field.add(fieldItem);
        return this;
    }

    /**
     * Add an item to the operator property in a chainable fashion.
     *
     * @return The same instance of TriggerConditionDiffObject for chaining.
     */
    public TriggerConditionDiffObject addOperatorItem(TriggerChangeObject operatorItem) {
        if (operator == null) {
            operator = new ArrayList<>();
        }
        operator.add(operatorItem);
        return this;
    }

    /**
     * Add an item to the value property in a chainable fashion.
     *
     * @return The same instance of TriggerConditionDiffObject for chaining.
     */
    public TriggerConditionDiffObject addValueItem(TriggerChangeObject valueItem) {
        if (value == null) {
            value = new ArrayList<>();
        }
        value.add(valueItem);
        return this;
    }

}