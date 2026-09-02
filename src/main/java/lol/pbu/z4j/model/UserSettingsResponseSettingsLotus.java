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
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashMap;

/**
 * <p>Lotus UI settings for onboarding, tooltips, and feature preferences</p>
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    UserSettingsResponseSettingsLotus.JSON_PROPERTY_AGENT_WORKSPACE_THEME_PREFERENCE,
    UserSettingsResponseSettingsLotus.JSON_PROPERTY_KEYBOARD_SHORTCUTS_ENABLED,
    UserSettingsResponseSettingsLotus.JSON_PROPERTY_MACRO_SHORTCUTS_ENABLED,
    UserSettingsResponseSettingsLotus.JSON_PROPERTY_SHOW_ONBOARDING_TOOLTIPS,
    UserSettingsResponseSettingsLotus.JSON_PROPERTY_TWO_FACTOR_AUTHENTICATION,
})
@Serdeable
public class UserSettingsResponseSettingsLotus extends HashMap<String, Object> {

    public static final String JSON_PROPERTY_AGENT_WORKSPACE_THEME_PREFERENCE = "agent_workspace_theme_preference";
    public static final String JSON_PROPERTY_KEYBOARD_SHORTCUTS_ENABLED = "keyboard_shortcuts_enabled";
    public static final String JSON_PROPERTY_MACRO_SHORTCUTS_ENABLED = "macro_shortcuts_enabled";
    public static final String JSON_PROPERTY_SHOW_ONBOARDING_TOOLTIPS = "show_onboarding_tooltips";
    public static final String JSON_PROPERTY_TWO_FACTOR_AUTHENTICATION = "two_factor_authentication";

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_WORKSPACE_THEME_PREFERENCE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserSettingsResponseSettingsLotusAgentWorkspaceThemePreference agentWorkspaceThemePreference;

    @Nullable
    @JsonProperty(JSON_PROPERTY_KEYBOARD_SHORTCUTS_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean keyboardShortcutsEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MACRO_SHORTCUTS_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean macroShortcutsEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SHOW_ONBOARDING_TOOLTIPS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean showOnboardingTooltips;

    /**
     * <p>Whether user is eligible for 2FA</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TWO_FACTOR_AUTHENTICATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean twoFactorAuthentication;

}