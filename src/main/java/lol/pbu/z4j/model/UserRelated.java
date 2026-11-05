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
import io.soabase.recordbuilder.core.RecordBuilder;

/**
 * Counts of tickets and subscriptions related to a user.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        UserRelated.JSON_PROPERTY_ASSIGNED_TICKETS,
        UserRelated.JSON_PROPERTY_CCD_TICKETS,
        UserRelated.JSON_PROPERTY_ORGANIZATION_SUBSCRIPTIONS,
        UserRelated.JSON_PROPERTY_REQUESTED_TICKETS
})
public record UserRelated(
        @Nullable @JsonProperty(JSON_PROPERTY_ASSIGNED_TICKETS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer assignedTickets,
        @Nullable @JsonProperty(JSON_PROPERTY_CCD_TICKETS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer ccdTickets,
        @Nullable @JsonProperty(JSON_PROPERTY_ORGANIZATION_SUBSCRIPTIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer organizationSubscriptions,
        @Nullable @JsonProperty(JSON_PROPERTY_REQUESTED_TICKETS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer requestedTickets
) implements UserRelatedBuilder.With {

    public static final String JSON_PROPERTY_ASSIGNED_TICKETS = "assigned_tickets";
    public static final String JSON_PROPERTY_CCD_TICKETS = "ccd_tickets";
    public static final String JSON_PROPERTY_ORGANIZATION_SUBSCRIPTIONS = "organization_subscriptions";
    public static final String JSON_PROPERTY_REQUESTED_TICKETS = "requested_tickets";

    public UserRelated() {
        this(null, null, null, null);
    }
}
