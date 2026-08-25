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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.ActionObject;
import lol.pbu.z4j.model.ConditionsObject;
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
 * AutomationObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AutomationObject.JSON_PROPERTY_ACTIONS,
    AutomationObject.JSON_PROPERTY_ACTIVE,
    AutomationObject.JSON_PROPERTY_CONDITIONS,
    AutomationObject.JSON_PROPERTY_CREATED_AT,
    AutomationObject.JSON_PROPERTY_DEFAULT,
    AutomationObject.JSON_PROPERTY_ID,
    AutomationObject.JSON_PROPERTY_POSITION,
    AutomationObject.JSON_PROPERTY_RAW_TITLE,
    AutomationObject.JSON_PROPERTY_TITLE,
    AutomationObject.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
public class AutomationObject {

    public static final String JSON_PROPERTY_ACTIONS = "actions";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CONDITIONS = "conditions";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_RAW_TITLE = "raw_title";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    /**
     * <p>An object describing what the automation will do. See <a href=\"/documentation/ticketing/reference-guides/actions-reference\">Actions reference</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ActionObject> actions;

    /**
     * <p>Whether the automation is active</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CONDITIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ConditionsObject conditions;

    /**
     * <p>The time the automation was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>If true, the automation is a default automation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _default;

    /**
     * <p>Automatically assigned when created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The position of the automation which specifies the order it will be executed</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer position;

    /**
     * <p>The raw title of the automation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitle;

    /**
     * <p>The title of the automation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    /**
     * <p>The time of the last update of the automation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * Add an item to the actions property in a chainable fashion.
     *
     * @return The same instance of AutomationObject for chaining.
     */
    public AutomationObject addActionsItem(ActionObject actionsItem) {
        if (actions == null) {
            actions = new ArrayList<>();
        }
        actions.add(actionsItem);
        return this;
    }

}