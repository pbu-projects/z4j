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
 * <p>User settings. See <a href=\"#users\">Users</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsUserObject.JSON_PROPERTY_AGENT_CREATED_WELCOME_EMAILS,
    AccountSettingsUserObject.JSON_PROPERTY_END_USER_PHONE_NUMBER_VALIDATION,
    AccountSettingsUserObject.JSON_PROPERTY_HAVE_GRAVATARS_ENABLED,
    AccountSettingsUserObject.JSON_PROPERTY_LANGUAGE_SELECTION,
    AccountSettingsUserObject.JSON_PROPERTY_MULTIPLE_ORGANIZATIONS,
    AccountSettingsUserObject.JSON_PROPERTY_TAGGING,
    AccountSettingsUserObject.JSON_PROPERTY_TIME_ZONE_SELECTION,
})
@Serdeable
public class AccountSettingsUserObject {

    public static final String JSON_PROPERTY_AGENT_CREATED_WELCOME_EMAILS = "agent_created_welcome_emails";
    public static final String JSON_PROPERTY_END_USER_PHONE_NUMBER_VALIDATION = "end_user_phone_number_validation";
    public static final String JSON_PROPERTY_HAVE_GRAVATARS_ENABLED = "have_gravatars_enabled";
    public static final String JSON_PROPERTY_LANGUAGE_SELECTION = "language_selection";
    public static final String JSON_PROPERTY_MULTIPLE_ORGANIZATIONS = "multiple_organizations";
    public static final String JSON_PROPERTY_TAGGING = "tagging";
    public static final String JSON_PROPERTY_TIME_ZONE_SELECTION = "time_zone_selection";

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_CREATED_WELCOME_EMAILS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentCreatedWelcomeEmails;

    @Nullable
    @JsonProperty(JSON_PROPERTY_END_USER_PHONE_NUMBER_VALIDATION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean endUserPhoneNumberValidation;

    @Nullable
    @JsonProperty(JSON_PROPERTY_HAVE_GRAVATARS_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean haveGravatarsEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LANGUAGE_SELECTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean languageSelection;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MULTIPLE_ORGANIZATIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean multipleOrganizations;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGGING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean tagging;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TIME_ZONE_SELECTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean timeZoneSelection;

}