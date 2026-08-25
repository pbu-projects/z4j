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
import java.util.Map;
import lol.pbu.z4j.model.ConditionsObject;
import lol.pbu.z4j.model.MacroObject;
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
 * WorkspaceObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    WorkspaceObject.JSON_PROPERTY_ACTIVATED,
    WorkspaceObject.JSON_PROPERTY_APPS,
    WorkspaceObject.JSON_PROPERTY_CONDITIONS,
    WorkspaceObject.JSON_PROPERTY_CREATED_AT,
    WorkspaceObject.JSON_PROPERTY_DESCRIPTION,
    WorkspaceObject.JSON_PROPERTY_ID,
    WorkspaceObject.JSON_PROPERTY_MACRO_IDS,
    WorkspaceObject.JSON_PROPERTY_MACROS,
    WorkspaceObject.JSON_PROPERTY_POSITION,
    WorkspaceObject.JSON_PROPERTY_PREFER_WORKSPACE_APP_ORDER,
    WorkspaceObject.JSON_PROPERTY_SELECTED_MACROS,
    WorkspaceObject.JSON_PROPERTY_TICKET_FORM_ID,
    WorkspaceObject.JSON_PROPERTY_TITLE,
    WorkspaceObject.JSON_PROPERTY_UPDATED_AT,
    WorkspaceObject.JSON_PROPERTY_URL,
})
@Serdeable
public class WorkspaceObject {

    public static final String JSON_PROPERTY_ACTIVATED = "activated";
    public static final String JSON_PROPERTY_APPS = "apps";
    public static final String JSON_PROPERTY_CONDITIONS = "conditions";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_MACRO_IDS = "macro_ids";
    public static final String JSON_PROPERTY_MACROS = "macros";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_PREFER_WORKSPACE_APP_ORDER = "prefer_workspace_app_order";
    public static final String JSON_PROPERTY_SELECTED_MACROS = "selected_macros";
    public static final String JSON_PROPERTY_TICKET_FORM_ID = "ticket_form_id";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>If true, this workspace is available for use</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACTIVATED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean activated;

    /**
     * <p>The apps associated to this workspace</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_APPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Map<String, Object>> apps;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CONDITIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ConditionsObject conditions;

    /**
     * <p>The time the workspace was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>User-defined description of this workspace's purpose</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The ids of the macros associated to this workspace</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> macroIds;

    /**
     * <p>The ids of the macros associated to this workspace</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MACROS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Integer> macros;

    /**
     * <p>Ordering of the workspace relative to other workspaces</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer position;

    /**
     * <p>If true, the order of apps within the workspace will be preserved</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PREFER_WORKSPACE_APP_ORDER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean preferWorkspaceAppOrder;

    /**
     * <p>An array of the macro objects that will be used in this workspace. See <a href=\"/api-reference/ticketing/business-rules/macros/\">Macros</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SELECTED_MACROS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid MacroObject> selectedMacros;

    /**
     * <p>The id of the ticket web form associated to this workspace</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FORM_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer ticketFormId;

    /**
     * <p>The title of the workspace</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String title;

    /**
     * <p>The time of the last update of the workspace</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The URL for this resource</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * Add an item to the apps property in a chainable fashion.
     *
     * @return The same instance of WorkspaceObject for chaining.
     */
    public WorkspaceObject addAppsItem(Map<String, Object> appsItem) {
        if (apps == null) {
            apps = new ArrayList<>();
        }
        apps.add(appsItem);
        return this;
    }

    /**
     * Add an item to the macroIds property in a chainable fashion.
     *
     * @return The same instance of WorkspaceObject for chaining.
     */
    public WorkspaceObject addMacroIdsItem(Integer macroIdsItem) {
        if (macroIds == null) {
            macroIds = new ArrayList<>();
        }
        macroIds.add(macroIdsItem);
        return this;
    }

    /**
     * Add an item to the macros property in a chainable fashion.
     *
     * @return The same instance of WorkspaceObject for chaining.
     */
    public WorkspaceObject addMacrosItem(Integer macrosItem) {
        if (macros == null) {
            macros = new ArrayList<>();
        }
        macros.add(macrosItem);
        return this;
    }

    /**
     * Add an item to the selectedMacros property in a chainable fashion.
     *
     * @return The same instance of WorkspaceObject for chaining.
     */
    public WorkspaceObject addSelectedMacrosItem(MacroObject selectedMacrosItem) {
        if (selectedMacros == null) {
            selectedMacros = new ArrayList<>();
        }
        selectedMacros.add(selectedMacrosItem);
        return this;
    }

}