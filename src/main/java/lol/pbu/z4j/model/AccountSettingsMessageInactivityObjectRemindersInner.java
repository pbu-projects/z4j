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
 * AccountSettingsMessageInactivityObjectRemindersInner
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    AccountSettingsMessageInactivityObjectRemindersInner.JSON_PROPERTY_MESSAGE,
    AccountSettingsMessageInactivityObjectRemindersInner.JSON_PROPERTY_TAGS,
    AccountSettingsMessageInactivityObjectRemindersInner.JSON_PROPERTY_TICKET_STATUS_ID,
    AccountSettingsMessageInactivityObjectRemindersInner.JSON_PROPERTY_TIMEOUT,
})
@Serdeable
public class AccountSettingsMessageInactivityObjectRemindersInner {

    public static final String JSON_PROPERTY_MESSAGE = "message";
    public static final String JSON_PROPERTY_TAGS = "tags";
    public static final String JSON_PROPERTY_TICKET_STATUS_ID = "ticket_status_id";
    public static final String JSON_PROPERTY_TIMEOUT = "timeout";

    /**
     * <p>Mandatory message for the reminder</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_MESSAGE)
    private String message;

    /**
     * <p>Array of strings, tags added to ticket with each reminder. Can be empty</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> tags;

    /**
     * <p>Ticket status id, available and mandatory for the 2nd and 3rd reminders, if the reminder is configured. The field can be null to indicate no status change</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_STATUS_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketStatusId;

    /**
     * <p>Reminder timeout in seconds, available and mandatory for the 2nd and 3rd reminders, if the reminder is configured</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TIMEOUT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long timeout;

    public AccountSettingsMessageInactivityObjectRemindersInner(String message) {
        this.message = message;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of AccountSettingsMessageInactivityObjectRemindersInner for chaining.
     */
    public AccountSettingsMessageInactivityObjectRemindersInner addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }

}