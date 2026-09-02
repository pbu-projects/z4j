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
 * <p>Configuration for routing. See <a href=\"#routing\">Routing</a></p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AccountSettingsRoutingObject.JSON_PROPERTY_AUTOROUTING_TAG,
    AccountSettingsRoutingObject.JSON_PROPERTY_ENABLED,
    AccountSettingsRoutingObject.JSON_PROPERTY_MAX_EMAIL_CAPACITY,
    AccountSettingsRoutingObject.JSON_PROPERTY_MAX_MESSAGING_CAPACITY,
    AccountSettingsRoutingObject.JSON_PROPERTY_REASSIGNMENT_MESSAGING_ENABLED,
    AccountSettingsRoutingObject.JSON_PROPERTY_REASSIGNMENT_MESSAGING_TIMEOUT,
    AccountSettingsRoutingObject.JSON_PROPERTY_REASSIGNMENT_TALK_TIMEOUT,
})
@Serdeable
public class AccountSettingsRoutingObject {

    public static final String JSON_PROPERTY_AUTOROUTING_TAG = "autorouting_tag";
    public static final String JSON_PROPERTY_ENABLED = "enabled";
    public static final String JSON_PROPERTY_MAX_EMAIL_CAPACITY = "max_email_capacity";
    public static final String JSON_PROPERTY_MAX_MESSAGING_CAPACITY = "max_messaging_capacity";
    public static final String JSON_PROPERTY_REASSIGNMENT_MESSAGING_ENABLED = "reassignment_messaging_enabled";
    public static final String JSON_PROPERTY_REASSIGNMENT_MESSAGING_TIMEOUT = "reassignment_messaging_timeout";
    public static final String JSON_PROPERTY_REASSIGNMENT_TALK_TIMEOUT = "reassignment_talk_timeout";

    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTOROUTING_TAG)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String autoroutingTag;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean enabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MAX_EMAIL_CAPACITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long maxEmailCapacity;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MAX_MESSAGING_CAPACITY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long maxMessagingCapacity;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REASSIGNMENT_MESSAGING_ENABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean reassignmentMessagingEnabled;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REASSIGNMENT_MESSAGING_TIMEOUT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long reassignmentMessagingTimeout;

    @Nullable
    @JsonProperty(JSON_PROPERTY_REASSIGNMENT_TALK_TIMEOUT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long reassignmentTalkTimeout;

}