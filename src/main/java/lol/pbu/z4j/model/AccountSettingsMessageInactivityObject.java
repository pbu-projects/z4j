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
import lol.pbu.z4j.model.AccountSettingsMessageInactivityObjectDefaultLocalizedMessages;
import lol.pbu.z4j.model.AccountSettingsMessageInactivityObjectRemindersInner;
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
 * <p>Auto-release capacity settings. See <a href=\"#messaging-inactivity\">Messaging inactivity</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsMessageInactivityObject.JSON_PROPERTY_DEFAULT_LOCALIZED_MESSAGES,
    AccountSettingsMessageInactivityObject.JSON_PROPERTY_ENABLED,
    AccountSettingsMessageInactivityObject.JSON_PROPERTY_END_SESSION,
    AccountSettingsMessageInactivityObject.JSON_PROPERTY_REMINDERS,
    AccountSettingsMessageInactivityObject.JSON_PROPERTY_TICKET_STATUS_ID,
    AccountSettingsMessageInactivityObject.JSON_PROPERTY_TIMEOUT,
})
@Serdeable
public class AccountSettingsMessageInactivityObject {

    public static final String JSON_PROPERTY_DEFAULT_LOCALIZED_MESSAGES = "default_localized_messages";
    public static final String JSON_PROPERTY_ENABLED = "enabled";
    public static final String JSON_PROPERTY_END_SESSION = "end_session";
    public static final String JSON_PROPERTY_REMINDERS = "reminders";
    public static final String JSON_PROPERTY_TICKET_STATUS_ID = "ticket_status_id";
    public static final String JSON_PROPERTY_TIMEOUT = "timeout";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_DEFAULT_LOCALIZED_MESSAGES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AccountSettingsMessageInactivityObjectDefaultLocalizedMessages defaultLocalizedMessages;

    /**
     * <p>Whether the messaging inactivity feature is enabled</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean enabled;

    /**
     * <p>Whether messaging session should end with the final reminder</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_END_SESSION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean endSession;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REMINDERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid AccountSettingsMessageInactivityObjectRemindersInner> reminders;

    /**
     * <p>Ticket status id to apply on the ticket when it is marked as inactive</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_STATUS_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketStatusId;

    /**
     * <p>Timeout in minutes after which the ticket will be marked as inactive</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TIMEOUT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long timeout;

    /**
     * Add an item to the reminders property in a chainable fashion.
     *
     * @return The same instance of AccountSettingsMessageInactivityObject for chaining.
     */
    public AccountSettingsMessageInactivityObject addRemindersItem(AccountSettingsMessageInactivityObjectRemindersInner remindersItem) {
        if (reminders == null) {
            reminders = new ArrayList<>();
        }
        reminders.add(remindersItem);
        return this;
    }

}