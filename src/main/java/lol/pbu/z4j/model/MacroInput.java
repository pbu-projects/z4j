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
import lol.pbu.z4j.model.ActionObject;
import lol.pbu.z4j.model.MacroInputRestriction;
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
 * MacroInput
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    MacroInput.JSON_PROPERTY_ACTIONS,
    MacroInput.JSON_PROPERTY_TITLE,
    MacroInput.JSON_PROPERTY_ACTIVE,
    MacroInput.JSON_PROPERTY_DESCRIPTION,
    MacroInput.JSON_PROPERTY_RESTRICTION,
})
@Serdeable
public class MacroInput {

    public static final String JSON_PROPERTY_ACTIONS = "actions";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_RESTRICTION = "restriction";

    /**
     * <p>Each action describes what the macro will do</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_ACTIONS)
    private List<@Valid ActionObject> actions = new ArrayList<>();

    /**
     * <p>The title of the macro</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * <p>Useful for determining if the macro should be displayed</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>The description of the macro</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RESTRICTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private MacroInputRestriction restriction;

    public MacroInput(List<@Valid ActionObject> actions, String title) {
        this.actions = actions;
        this.title = title;
    }

    /**
     * Add an item to the actions property in a chainable fashion.
     *
     * @return The same instance of MacroInput for chaining.
     */
    public MacroInput addActionsItem(ActionObject actionsItem) {
        actions.add(actionsItem);
        return this;
    }

}