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
 * SecuritySettingsObjectAuthenticationAgentAllOfPassword
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SecuritySettingsObjectAuthenticationAgentAllOfPassword.JSON_PROPERTY_DISALLOW_LOCAL_PART_FROM_EMAIL,
    SecuritySettingsObjectAuthenticationAgentAllOfPassword.JSON_PROPERTY_FAILED_ATTEMPTS_ALLOWED,
    SecuritySettingsObjectAuthenticationAgentAllOfPassword.JSON_PROPERTY_IS_AVAILABLE,
    SecuritySettingsObjectAuthenticationAgentAllOfPassword.JSON_PROPERTY_MAX_SEQUENCE,
    SecuritySettingsObjectAuthenticationAgentAllOfPassword.JSON_PROPERTY_PASSWORD_COMPLEXITY,
    SecuritySettingsObjectAuthenticationAgentAllOfPassword.JSON_PROPERTY_PASSWORD_DURATION,
    SecuritySettingsObjectAuthenticationAgentAllOfPassword.JSON_PROPERTY_PASSWORD_HISTORY_LENGTH,
    SecuritySettingsObjectAuthenticationAgentAllOfPassword.JSON_PROPERTY_PASSWORD_IN_MIXED_CASE,
    SecuritySettingsObjectAuthenticationAgentAllOfPassword.JSON_PROPERTY_PASSWORD_LENGTH,
})
@Serdeable
public class SecuritySettingsObjectAuthenticationAgentAllOfPassword {

    public static final String JSON_PROPERTY_DISALLOW_LOCAL_PART_FROM_EMAIL = "disallow_local_part_from_email";
    public static final String JSON_PROPERTY_FAILED_ATTEMPTS_ALLOWED = "failed_attempts_allowed";
    public static final String JSON_PROPERTY_IS_AVAILABLE = "is_available";
    public static final String JSON_PROPERTY_MAX_SEQUENCE = "max_sequence";
    public static final String JSON_PROPERTY_PASSWORD_COMPLEXITY = "password_complexity";
    public static final String JSON_PROPERTY_PASSWORD_DURATION = "password_duration";
    public static final String JSON_PROPERTY_PASSWORD_HISTORY_LENGTH = "password_history_length";
    public static final String JSON_PROPERTY_PASSWORD_IN_MIXED_CASE = "password_in_mixed_case";
    public static final String JSON_PROPERTY_PASSWORD_LENGTH = "password_length";

    @Nullable
    @JsonProperty(JSON_PROPERTY_DISALLOW_LOCAL_PART_FROM_EMAIL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean disallowLocalPartFromEmail;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FAILED_ATTEMPTS_ALLOWED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer failedAttemptsAllowed;

    @Nullable
    @JsonProperty(JSON_PROPERTY_IS_AVAILABLE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isAvailable = true;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MAX_SEQUENCE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer maxSequence;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PASSWORD_COMPLEXITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer passwordComplexity;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PASSWORD_DURATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer passwordDuration;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PASSWORD_HISTORY_LENGTH)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer passwordHistoryLength;

    /**
     * <p>If must include letters in mixed case</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PASSWORD_IN_MIXED_CASE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean passwordInMixedCase;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PASSWORD_LENGTH)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer passwordLength;

}