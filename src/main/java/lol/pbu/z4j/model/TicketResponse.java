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

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import java.util.List;

@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketResponse.JSON_PROPERTY_TICKET,
        TicketResponse.JSON_PROPERTY_USERS,
        TicketResponse.JSON_PROPERTY_GROUPS,
        TicketResponse.JSON_PROPERTY_ORGANIZATIONS,
        TicketResponse.JSON_PROPERTY_METRIC_SETS,
        TicketResponse.JSON_PROPERTY_AUDITS
})
public record TicketResponse(
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_TICKET) Ticket ticket,
        @Nullable @JsonProperty(JSON_PROPERTY_USERS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid User> users,
        @Nullable @JsonProperty(JSON_PROPERTY_GROUPS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Group> groups,
        @Nullable @JsonProperty(JSON_PROPERTY_ORGANIZATIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Organization> organizations,
        @Nullable @JsonProperty(JSON_PROPERTY_METRIC_SETS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid TicketMetric> metricSets,
        @Nullable @JsonProperty(JSON_PROPERTY_AUDITS) @JsonAlias("last_audits") @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Audit> audits
) implements SideloadableResponse, TicketResponseBuilder.With {

    public static final String JSON_PROPERTY_TICKET = "ticket";
    public static final String JSON_PROPERTY_USERS = "users";
    public static final String JSON_PROPERTY_GROUPS = "groups";
    public static final String JSON_PROPERTY_ORGANIZATIONS = "organizations";
    public static final String JSON_PROPERTY_METRIC_SETS = "metric_sets";
    public static final String JSON_PROPERTY_AUDITS = "audits";

    public TicketResponse(Ticket ticket) {
        this(ticket, null, null, null, null, null);
    }

    public TicketResponse() {
        this(null);
    }
}
