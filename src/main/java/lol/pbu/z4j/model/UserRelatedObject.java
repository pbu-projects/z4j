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
 * UserRelatedObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    UserRelatedObject.JSON_PROPERTY_ASSIGNED_TICKETS,
    UserRelatedObject.JSON_PROPERTY_CCD_TICKETS,
    UserRelatedObject.JSON_PROPERTY_ORGANIZATION_SUBSCRIPTIONS,
    UserRelatedObject.JSON_PROPERTY_REQUESTED_TICKETS,
})
@Serdeable
public class UserRelatedObject {

    public static final String JSON_PROPERTY_ASSIGNED_TICKETS = "assigned_tickets";
    public static final String JSON_PROPERTY_CCD_TICKETS = "ccd_tickets";
    public static final String JSON_PROPERTY_ORGANIZATION_SUBSCRIPTIONS = "organization_subscriptions";
    public static final String JSON_PROPERTY_REQUESTED_TICKETS = "requested_tickets";

    /**
     * <p>Count of assigned tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNED_TICKETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long assignedTickets;

    /**
     * <p>Count of collaborated tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CCD_TICKETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ccdTickets;

    /**
     * <p>Count of organization subscriptions</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_SUBSCRIPTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long organizationSubscriptions;

    /**
     * <p>Count of requested tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTED_TICKETS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long requestedTickets;

}