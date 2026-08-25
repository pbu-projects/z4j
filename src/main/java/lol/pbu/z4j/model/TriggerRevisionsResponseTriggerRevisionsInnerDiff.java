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
import lol.pbu.z4j.model.TriggerActionDiffObject;
import lol.pbu.z4j.model.TriggerChangeObject;
import lol.pbu.z4j.model.TriggerConditionDiffObject;
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
 * TriggerRevisionsResponseTriggerRevisionsInnerDiff
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TriggerRevisionsResponseTriggerRevisionsInnerDiff.JSON_PROPERTY_ACTIONS,
    TriggerRevisionsResponseTriggerRevisionsInnerDiff.JSON_PROPERTY_ACTIVE,
    TriggerRevisionsResponseTriggerRevisionsInnerDiff.JSON_PROPERTY_CONDITIONS,
    TriggerRevisionsResponseTriggerRevisionsInnerDiff.JSON_PROPERTY_DESCRIPTION,
    TriggerRevisionsResponseTriggerRevisionsInnerDiff.JSON_PROPERTY_SOURCE_ID,
    TriggerRevisionsResponseTriggerRevisionsInnerDiff.JSON_PROPERTY_TARGET_ID,
    TriggerRevisionsResponseTriggerRevisionsInnerDiff.JSON_PROPERTY_TITLE,
})
@Serdeable
public class TriggerRevisionsResponseTriggerRevisionsInnerDiff {

    public static final String JSON_PROPERTY_ACTIONS = "actions";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CONDITIONS = "conditions";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_SOURCE_ID = "source_id";
    public static final String JSON_PROPERTY_TARGET_ID = "target_id";
    public static final String JSON_PROPERTY_TITLE = "title";

    /**
     * <p>An array that contain <a href=\"#Action Diffs\">action diff objects</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerActionDiffObject> actions;

    /**
     * <p>An array of <a href=\"#change\">change</a> objects</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerChangeObject> active;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CONDITIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TriggerConditionDiffObject conditions;

    /**
     * <p>An array of <a href=\"#change\">change</a> objects</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerChangeObject> description;

    /**
     * <p>ID of the source revision</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer sourceId;

    /**
     * <p>ID of the target revision</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TARGET_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer targetId;

    /**
     * <p>An array of <a href=\"#change\">change</a> objects</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TriggerChangeObject> title;

    /**
     * Add an item to the actions property in a chainable fashion.
     *
     * @return The same instance of TriggerRevisionsResponseTriggerRevisionsInnerDiff for chaining.
     */
    public TriggerRevisionsResponseTriggerRevisionsInnerDiff addActionsItem(TriggerActionDiffObject actionsItem) {
        if (actions == null) {
            actions = new ArrayList<>();
        }
        actions.add(actionsItem);
        return this;
    }

    /**
     * Add an item to the active property in a chainable fashion.
     *
     * @return The same instance of TriggerRevisionsResponseTriggerRevisionsInnerDiff for chaining.
     */
    public TriggerRevisionsResponseTriggerRevisionsInnerDiff addActiveItem(TriggerChangeObject activeItem) {
        if (active == null) {
            active = new ArrayList<>();
        }
        active.add(activeItem);
        return this;
    }

    /**
     * Add an item to the description property in a chainable fashion.
     *
     * @return The same instance of TriggerRevisionsResponseTriggerRevisionsInnerDiff for chaining.
     */
    public TriggerRevisionsResponseTriggerRevisionsInnerDiff addDescriptionItem(TriggerChangeObject descriptionItem) {
        if (description == null) {
            description = new ArrayList<>();
        }
        description.add(descriptionItem);
        return this;
    }

    /**
     * Add an item to the title property in a chainable fashion.
     *
     * @return The same instance of TriggerRevisionsResponseTriggerRevisionsInnerDiff for chaining.
     */
    public TriggerRevisionsResponseTriggerRevisionsInnerDiff addTitleItem(TriggerChangeObject titleItem) {
        if (title == null) {
            title = new ArrayList<>();
        }
        title.add(titleItem);
        return this;
    }

}