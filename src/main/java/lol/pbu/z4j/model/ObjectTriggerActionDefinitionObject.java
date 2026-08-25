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
import lol.pbu.z4j.model.ObjectTriggerActionDefinitionObjectValuesInner;
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
 * ObjectTriggerActionDefinitionObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ObjectTriggerActionDefinitionObject.JSON_PROPERTY_GROUP,
    ObjectTriggerActionDefinitionObject.JSON_PROPERTY_NULLABLE,
    ObjectTriggerActionDefinitionObject.JSON_PROPERTY_REPEATABLE,
    ObjectTriggerActionDefinitionObject.JSON_PROPERTY_SUBJECT,
    ObjectTriggerActionDefinitionObject.JSON_PROPERTY_TITLE,
    ObjectTriggerActionDefinitionObject.JSON_PROPERTY_TYPE,
    ObjectTriggerActionDefinitionObject.JSON_PROPERTY_VALUES,
})
@Serdeable
public class ObjectTriggerActionDefinitionObject {

    public static final String JSON_PROPERTY_GROUP = "group";
    public static final String JSON_PROPERTY_NULLABLE = "nullable";
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
    private List<@Valid ObjectTriggerActionDefinitionObjectValuesInner> values;

    /**
     * Add an item to the values property in a chainable fashion.
     *
     * @return The same instance of ObjectTriggerActionDefinitionObject for chaining.
     */
    public ObjectTriggerActionDefinitionObject addValuesItem(ObjectTriggerActionDefinitionObjectValuesInner valuesItem) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(valuesItem);
        return this;
    }

}