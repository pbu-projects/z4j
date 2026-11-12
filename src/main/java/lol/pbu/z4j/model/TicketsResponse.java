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
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketsResponse.JSON_PROPERTY_TICKETS,
        TicketsResponse.JSON_PROPERTY_USERS,
        TicketsResponse.JSON_PROPERTY_GROUPS,
        TicketsResponse.JSON_PROPERTY_ORGANIZATIONS,
        TicketsResponse.JSON_PROPERTY_METRIC_SETS,
        TicketsResponse.JSON_PROPERTY_AUDITS,
        TicketsResponse.JSON_PROPERTY_META,
        TicketsResponse.JSON_PROPERTY_LINKS,
        TicketsResponse.JSON_PROPERTY_COUNT
})
public record TicketsResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_TICKETS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Ticket> tickets,
        @Nullable @JsonProperty(JSON_PROPERTY_USERS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid User> users,
        @Nullable @JsonProperty(JSON_PROPERTY_GROUPS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Group> groups,
        @Nullable @JsonProperty(JSON_PROPERTY_ORGANIZATIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Organization> organizations,
        @Nullable @JsonProperty(JSON_PROPERTY_METRIC_SETS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid TicketMetric> metricSets,
        @Nullable @JsonProperty(JSON_PROPERTY_AUDITS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Audit> audits,
        @Nullable @JsonProperty(JSON_PROPERTY_META) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Meta meta,
        @Nullable @JsonProperty(JSON_PROPERTY_LINKS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Links links,
        @Nullable @JsonProperty(JSON_PROPERTY_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer count
) implements SideloadableResponse, TicketsResponseBuilder.With {

    public static final String JSON_PROPERTY_TICKETS = "tickets";
    public static final String JSON_PROPERTY_USERS = "users";
    public static final String JSON_PROPERTY_GROUPS = "groups";
    public static final String JSON_PROPERTY_ORGANIZATIONS = "organizations";
    public static final String JSON_PROPERTY_METRIC_SETS = "metric_sets";
    public static final String JSON_PROPERTY_AUDITS = "audits";
    public static final String JSON_PROPERTY_META = "meta";
    public static final String JSON_PROPERTY_LINKS = "links";
    public static final String JSON_PROPERTY_COUNT = "count";

    public TicketsResponse(List<Ticket> tickets, Meta meta, Links links, Integer count) {
        this(tickets, null, null, null, null, null, meta, links, count);
    }

    public TicketsResponse() {
        this(null, null, null, null);
    }

    public TicketsResponse addTicketsItem(Ticket ticketsItem) {
        List<Ticket> list = new ArrayList<>(tickets != null ? tickets : List.of());
        list.add(ticketsItem);
        return new TicketsResponse(list, users, groups, organizations, metricSets, audits, meta, links, count);
    }
}
