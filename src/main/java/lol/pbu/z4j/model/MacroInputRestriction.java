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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * <p>Who may access this macro. Will be null when everyone in the account can access it</p>
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    MacroInputRestriction.JSON_PROPERTY_ID,
    MacroInputRestriction.JSON_PROPERTY_IDS,
    MacroInputRestriction.JSON_PROPERTY_TYPE,
})
@Serdeable
public class MacroInputRestriction extends HashMap<String, Object> {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_IDS = "ids";
    public static final String JSON_PROPERTY_TYPE = "type";

    /**
     * <p>The numeric ID of the group or user</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The numeric IDs of the groups</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> ids;

    /**
     * <p>Allowed values are Group or User</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

    /**
     * Add an item to the ids property in a chainable fashion.
     *
     * @return The same instance of MacroInputRestriction for chaining.
     */
    public MacroInputRestriction addIdsItem(Integer idsItem) {
        if (ids == null) {
            ids = new ArrayList<>();
        }
        ids.add(idsItem);
        return this;
    }

}