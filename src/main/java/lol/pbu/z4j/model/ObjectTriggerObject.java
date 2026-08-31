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
import lol.pbu.z4j.model.TriggerActionObject;
import lol.pbu.z4j.model.TriggerConditionsObject;
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
 * ObjectTriggerObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    ObjectTriggerObject.JSON_PROPERTY_ACTIONS,
    ObjectTriggerObject.JSON_PROPERTY_CONDITIONS,
    ObjectTriggerObject.JSON_PROPERTY_TITLE,
    ObjectTriggerObject.JSON_PROPERTY_ACTIVE,
    ObjectTriggerObject.JSON_PROPERTY_CREATED_AT,
    ObjectTriggerObject.JSON_PROPERTY_DEFAULT,
    ObjectTriggerObject.JSON_PROPERTY_DESCRIPTION,
    ObjectTriggerObject.JSON_PROPERTY_ID,
    ObjectTriggerObject.JSON_PROPERTY_POSITION,
    ObjectTriggerObject.JSON_PROPERTY_RAW_TITLE,
    ObjectTriggerObject.JSON_PROPERTY_UPDATED_AT,
    ObjectTriggerObject.JSON_PROPERTY_URL,
})
@Serdeable
public class ObjectTriggerObject {

    public static final String JSON_PROPERTY_ACTIONS = "actions";
    public static final String JSON_PROPERTY_CONDITIONS = "conditions";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_RAW_TITLE = "raw_title";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>An array of actions the trigger does when its conditions are met. See <a href=\"/documentation/ticketing/reference-guides/actions-reference\">Actions reference</a></p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_ACTIONS)
    private List<@Valid TriggerActionObject> actions = new ArrayList<>();

    @NotNull
    @Valid
    @JsonProperty(JSON_PROPERTY_CONDITIONS)
    private TriggerConditionsObject conditions;

    /**
     * <p>The title of the trigger</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * <p>Whether the trigger is active</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean active;

    /**
     * <p>The time the trigger was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * <p>Always false for object triggers</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _default;

    /**
     * <p>The description of the trigger</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Automatically assigned when created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>Position of the trigger, determines the order they will execute in</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    /**
     * <p>The raw format of the title of the trigger</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitle;

    /**
     * <p>The time of the last update of the trigger</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * <p>The url of the trigger</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    public ObjectTriggerObject(List<@Valid TriggerActionObject> actions, TriggerConditionsObject conditions, String title) {
        this.actions = actions;
        this.conditions = conditions;
        this.title = title;
    }

    /**
     * Add an item to the actions property in a chainable fashion.
     *
     * @return The same instance of ObjectTriggerObject for chaining.
     */
    public ObjectTriggerObject addActionsItem(TriggerActionObject actionsItem) {
        actions.add(actionsItem);
        return this;
    }

}