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
 * <p>Side conversations settings</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsSideConversationsObject.JSON_PROPERTY_EMAIL_CHANNEL,
    AccountSettingsSideConversationsObject.JSON_PROPERTY_MSTEAMS_CHANNEL,
    AccountSettingsSideConversationsObject.JSON_PROPERTY_SHOW_IN_CONTEXT_PANEL,
    AccountSettingsSideConversationsObject.JSON_PROPERTY_SLACK_CHANNEL,
    AccountSettingsSideConversationsObject.JSON_PROPERTY_TICKETS_CHANNEL,
})
@Serdeable
public class AccountSettingsSideConversationsObject {

    public static final String JSON_PROPERTY_EMAIL_CHANNEL = "email_channel";
    public static final String JSON_PROPERTY_MSTEAMS_CHANNEL = "msteams_channel";
    public static final String JSON_PROPERTY_SHOW_IN_CONTEXT_PANEL = "show_in_context_panel";
    public static final String JSON_PROPERTY_SLACK_CHANNEL = "slack_channel";
    public static final String JSON_PROPERTY_TICKETS_CHANNEL = "tickets_channel";

    @Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_CHANNEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean emailChannel;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MSTEAMS_CHANNEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean msteamsChannel;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SHOW_IN_CONTEXT_PANEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean showInContextPanel;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SLACK_CHANNEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean slackChannel;

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKETS_CHANNEL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ticketsChannel;

}