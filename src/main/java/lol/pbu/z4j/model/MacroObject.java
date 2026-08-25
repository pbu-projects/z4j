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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lol.pbu.z4j.model.ActionObject;
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
 * MacroObject
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    MacroObject.JSON_PROPERTY_ACTIONS,
    MacroObject.JSON_PROPERTY_TITLE,
    MacroObject.JSON_PROPERTY_ACTIVE,
    MacroObject.JSON_PROPERTY_CREATED_AT,
    MacroObject.JSON_PROPERTY_DEFAULT,
    MacroObject.JSON_PROPERTY_DESCRIPTION,
    MacroObject.JSON_PROPERTY_ID,
    MacroObject.JSON_PROPERTY_POSITION,
    MacroObject.JSON_PROPERTY_RAW_TITLE,
    MacroObject.JSON_PROPERTY_RESTRICTION,
    MacroObject.JSON_PROPERTY_UPDATED_AT,
    MacroObject.JSON_PROPERTY_URL,
    MacroObject.JSON_PROPERTY_APP_INSTALLATION,
    MacroObject.JSON_PROPERTY_CATEGORIES,
    MacroObject.JSON_PROPERTY_PERMISSIONS,
    MacroObject.JSON_PROPERTY_USAGE1H,
    MacroObject.JSON_PROPERTY_USAGE7D,
    MacroObject.JSON_PROPERTY_USAGE24H,
    MacroObject.JSON_PROPERTY_USAGE30D,
})
@Serdeable
public class MacroObject {

    public static final String JSON_PROPERTY_ACTIONS = "actions";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_ACTIVE = "active";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DEFAULT = "default";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_RAW_TITLE = "raw_title";
    public static final String JSON_PROPERTY_RESTRICTION = "restriction";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_APP_INSTALLATION = "app_installation";
    public static final String JSON_PROPERTY_CATEGORIES = "categories";
    public static final String JSON_PROPERTY_PERMISSIONS = "permissions";
    public static final String JSON_PROPERTY_USAGE1H = "usage_1h";
    public static final String JSON_PROPERTY_USAGE7D = "usage_7d";
    public static final String JSON_PROPERTY_USAGE24H = "usage_24h";
    public static final String JSON_PROPERTY_USAGE30D = "usage_30d";

    /**
     * <p>Each action describes what the macro will do. See <a href=\"/documentation/ticketing/reference-guides/actions-reference\">Actions reference</a></p>
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
     * <p>The time the macro was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>If true, the macro is a default macro</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _default;

    /**
     * <p>The description of the macro</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * <p>The id automatically assigned when a macro is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>The position of the macro</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer position;

    /**
     * <p>The raw format of the title of the macro</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RAW_TITLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String rawTitle;

    /**
     * <p>Access to this macro. A null value allows unrestricted access for all users in the account</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RESTRICTION)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> restriction;

    /**
     * <p>The time of the last update of the macro</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>A URL to access the macro's details</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * <p>The app installation that requires each macro, if present</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_APP_INSTALLATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String appInstallation;

    /**
     * <p>The macro categories</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CATEGORIES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String categories;

    /**
     * <p>Permissions for each macro</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PERMISSIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String permissions;

    /**
     * <p>The number of times each macro has been used in the past hour</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USAGE1H)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer usage1h;

    /**
     * <p>The number of times each macro has been used in the past week</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USAGE7D)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer usage7d;

    /**
     * <p>The number of times each macro has been used in the past day</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USAGE24H)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer usage24h;

    /**
     * <p>The number of times each macro has been used in the past thirty days</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USAGE30D)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer usage30d;

    public MacroObject(List<@Valid ActionObject> actions, String title) {
        this.actions = actions;
        this.title = title;
    }

    /**
     * Add an item to the actions property in a chainable fashion.
     *
     * @return The same instance of MacroObject for chaining.
     */
    public MacroObject addActionsItem(ActionObject actionsItem) {
        actions.add(actionsItem);
        return this;
    }

    /**
     * Set the value for the key for the restriction map property in a chainable fashion.
     *
     * @return The same instance of MacroObject for chaining.
     */
    public MacroObject putRestrictionItem(String key, Object restrictionItem) {
        if (restriction == null) {
            restriction = new HashMap<>();
        }
        restriction.put(key, restrictionItem);
        return this;
    }

}