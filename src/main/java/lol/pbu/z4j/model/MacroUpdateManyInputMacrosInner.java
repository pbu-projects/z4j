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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * MacroUpdateManyInputMacrosInner
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    MacroUpdateManyInputMacrosInner.JSON_PROPERTY_ID,
    MacroUpdateManyInputMacrosInner.JSON_PROPERTY_ACTIVE,
    MacroUpdateManyInputMacrosInner.JSON_PROPERTY_POSITION,
})
@Serdeable
public class MacroUpdateManyInputMacrosInner {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_POSITION = "position";

    /**
     * <p>The ID of the macro to update</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_ID)
    private Long id;

    /**
     * <p>The active status of the macro (true or false)</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>The new position of the macro</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    public MacroUpdateManyInputMacrosInner(Long id) {
        this.id = id;
    }

}