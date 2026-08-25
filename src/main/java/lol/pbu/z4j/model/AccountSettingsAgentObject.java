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
 * <p>Configuration for the agent workspace. See <a href=\"#agents\">Agents</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsAgentObject.JSON_PROPERTY_AGENT_HOME,
    AccountSettingsAgentObject.JSON_PROPERTY_AGENT_WORKSPACE,
    AccountSettingsAgentObject.JSON_PROPERTY_AW_SELF_SERVE_MIGRATION_ENABLED,
    AccountSettingsAgentObject.JSON_PROPERTY_FOCUS_MODE,
    AccountSettingsAgentObject.JSON_PROPERTY_IDLE_TIMEOUT_ENABLED,
    AccountSettingsAgentObject.JSON_PROPERTY_UNIFIED_AGENT_STATUSES,
})
@Serdeable
public class AccountSettingsAgentObject {

    public static final String JSON_PROPERTY_AGENT_HOME = "agent_home";
    public static final String JSON_PROPERTY_AGENT_WORKSPACE = "agent_workspace";
    public static final String JSON_PROPERTY_AW_SELF_SERVE_MIGRATION_ENABLED = "aw_self_serve_migration_enabled";
    public static final String JSON_PROPERTY_FOCUS_MODE = "focus_mode";
    public static final String JSON_PROPERTY_IDLE_TIMEOUT_ENABLED = "idle_timeout_enabled";
    public static final String JSON_PROPERTY_UNIFIED_AGENT_STATUSES = "unified_agent_statuses";

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_HOME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentHome;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_WORKSPACE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentWorkspace;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AW_SELF_SERVE_MIGRATION_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean awSelfServeMigrationEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FOCUS_MODE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean focusMode;

    @Nullable
    @JsonProperty(JSON_PROPERTY_IDLE_TIMEOUT_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean idleTimeoutEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_UNIFIED_AGENT_STATUSES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean unifiedAgentStatuses;

}