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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>Zendesk Talk settings. See <a href=\"#voice\">Voice</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsVoiceObject.JSON_PROPERTY_AGENT_CONFIRMATION_WHEN_FORWARDING,
    AccountSettingsVoiceObject.JSON_PROPERTY_AGENT_WRAP_UP_AFTER_CALLS,
    AccountSettingsVoiceObject.JSON_PROPERTY_ENABLED,
    AccountSettingsVoiceObject.JSON_PROPERTY_LOGGING,
    AccountSettingsVoiceObject.JSON_PROPERTY_MAXIMUM_QUEUE_SIZE,
    AccountSettingsVoiceObject.JSON_PROPERTY_MAXIMUM_QUEUE_WAIT_TIME,
    AccountSettingsVoiceObject.JSON_PROPERTY_ONLY_DURING_BUSINESS_HOURS,
    AccountSettingsVoiceObject.JSON_PROPERTY_OUTBOUND_ENABLED,
    AccountSettingsVoiceObject.JSON_PROPERTY_RECORDINGS_PUBLIC,
    AccountSettingsVoiceObject.JSON_PROPERTY_UK_MOBILE_FORWARDING,
})
@Serdeable
public class AccountSettingsVoiceObject {

    public static final String JSON_PROPERTY_AGENT_CONFIRMATION_WHEN_FORWARDING = "agent_confirmation_when_forwarding";
    public static final String JSON_PROPERTY_AGENT_WRAP_UP_AFTER_CALLS = "agent_wrap_up_after_calls";
    public static final String JSON_PROPERTY_ENABLED = "enabled";
    public static final String JSON_PROPERTY_LOGGING = "logging";
    public static final String JSON_PROPERTY_MAXIMUM_QUEUE_SIZE = "maximum_queue_size";
    public static final String JSON_PROPERTY_MAXIMUM_QUEUE_WAIT_TIME = "maximum_queue_wait_time";
    public static final String JSON_PROPERTY_ONLY_DURING_BUSINESS_HOURS = "only_during_business_hours";
    public static final String JSON_PROPERTY_OUTBOUND_ENABLED = "outbound_enabled";
    public static final String JSON_PROPERTY_RECORDINGS_PUBLIC = "recordings_public";
    public static final String JSON_PROPERTY_UK_MOBILE_FORWARDING = "uk_mobile_forwarding";

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_CONFIRMATION_WHEN_FORWARDING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentConfirmationWhenForwarding;

    @Nullable
    @JsonProperty(JSON_PROPERTY_AGENT_WRAP_UP_AFTER_CALLS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean agentWrapUpAfterCalls;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean enabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LOGGING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean logging;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MAXIMUM_QUEUE_SIZE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long maximumQueueSize;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MAXIMUM_QUEUE_WAIT_TIME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long maximumQueueWaitTime;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ONLY_DURING_BUSINESS_HOURS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean onlyDuringBusinessHours;

    @Nullable
    @JsonProperty(JSON_PROPERTY_OUTBOUND_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean outboundEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_RECORDINGS_PUBLIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean recordingsPublic;

    @Nullable
    @JsonProperty(JSON_PROPERTY_UK_MOBILE_FORWARDING)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean ukMobileForwarding;

}