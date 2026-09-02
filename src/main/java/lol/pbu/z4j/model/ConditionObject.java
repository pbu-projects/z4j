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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * ConditionObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ConditionObject.JSON_PROPERTY_FIELD,
    ConditionObject.JSON_PROPERTY_OPERATOR,
    ConditionObject.JSON_PROPERTY_VALUE,
})
@Serdeable
public class ConditionObject {

    public static final String JSON_PROPERTY_FIELD = "field";
    public static final String JSON_PROPERTY_OPERATOR = "operator";
    public static final String JSON_PROPERTY_VALUE = "value";

    /**
     * <p>The name of a ticket field</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FIELD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String field;

    /**
     * <p>A comparison operator</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OPERATOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String operator;

    /**
     * <p>The value of a ticket field</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String value;

}