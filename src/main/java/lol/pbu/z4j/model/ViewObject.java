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
import java.util.HashMap;
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
 * ViewObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ViewObject.JSON_PROPERTY_ACTIVE,
    ViewObject.JSON_PROPERTY_CONDITIONS,
    ViewObject.JSON_PROPERTY_CREATED_AT,
    ViewObject.JSON_PROPERTY_DEFAULT,
    ViewObject.JSON_PROPERTY_DESCRIPTION,
    ViewObject.JSON_PROPERTY_EXECUTION,
    ViewObject.JSON_PROPERTY_ID,
    ViewObject.JSON_PROPERTY_POSITION,
    ViewObject.JSON_PROPERTY_RESTRICTION,
    ViewObject.JSON_PROPERTY_TITLE,
    ViewObject.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
public class ViewObject {

    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CONDITIONS = "conditions";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_EXECUTION = "execution";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_RESTRICTION = "restriction";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    /**
     * <p>Whether the view is active</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>Describes how the view is constructed. See <a href=\"/documentation/ticketing/reference-guides/conditions-reference\">Conditions reference</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONDITIONS)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> conditions;

    /**
     * <p>The time the view was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>If true, the view is a default view</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _default;

    /**
     * <p>The description of the view</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Describes how the view should be executed. See <a href=\"#execution\">Execution</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EXECUTION)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> execution;

    /**
     * <p>Automatically assigned when created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The position of the view</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer position;

    /**
     * <p>Who may access this view. Is null when everyone in the account can access it</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RESTRICTION)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> restriction;

    /**
     * <p>The title of the view</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    /**
     * <p>The time the view was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * Set the value for the key for the conditions map property in a chainable fashion.
     *
     * @return The same instance of ViewObject for chaining.
     */
    public ViewObject putConditionsItem(String key, Object conditionsItem) {
        if (conditions == null) {
            conditions = new HashMap<>();
        }
        conditions.put(key, conditionsItem);
        return this;
    }

    /**
     * Set the value for the key for the execution map property in a chainable fashion.
     *
     * @return The same instance of ViewObject for chaining.
     */
    public ViewObject putExecutionItem(String key, Object executionItem) {
        if (execution == null) {
            execution = new HashMap<>();
        }
        execution.put(key, executionItem);
        return this;
    }

    /**
     * Set the value for the key for the restriction map property in a chainable fashion.
     *
     * @return The same instance of ViewObject for chaining.
     */
    public ViewObject putRestrictionItem(String key, Object restrictionItem) {
        if (restriction == null) {
            restriction = new HashMap<>();
        }
        restriction.put(key, restrictionItem);
        return this;
    }

}