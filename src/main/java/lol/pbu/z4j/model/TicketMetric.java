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
import java.time.ZonedDateTime;

/**
 * Ticket metric model representing metric set timestamps and duration measurements.
 *
 * @author Jonathan-Zollinger
 * @since 0.4.3
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketMetric.JSON_PROPERTY_AGENT_WAIT_TIME_IN_MINUTES,
        TicketMetric.JSON_PROPERTY_ASSIGNED_AT,
        TicketMetric.JSON_PROPERTY_ASSIGNEE_STATIONS,
        TicketMetric.JSON_PROPERTY_ASSIGNEE_UPDATED_AT,
        TicketMetric.JSON_PROPERTY_CREATED_AT,
        TicketMetric.JSON_PROPERTY_CUSTOM_STATUS_UPDATED_AT,
        TicketMetric.JSON_PROPERTY_FIRST_RESOLUTION_TIME_IN_MINUTES,
        TicketMetric.JSON_PROPERTY_FULL_RESOLUTION_TIME_IN_MINUTES,
        TicketMetric.JSON_PROPERTY_GROUP_STATIONS,
        TicketMetric.JSON_PROPERTY_ID,
        TicketMetric.JSON_PROPERTY_INITIALLY_ASSIGNED_AT,
        TicketMetric.JSON_PROPERTY_LATEST_COMMENT_ADDED_AT,
        TicketMetric.JSON_PROPERTY_ON_HOLD_TIME_IN_MINUTES,
        TicketMetric.JSON_PROPERTY_REOPENS,
        TicketMetric.JSON_PROPERTY_REPLIES,
        TicketMetric.JSON_PROPERTY_REPLY_TIME_IN_MINUTES,
        TicketMetric.JSON_PROPERTY_REPLY_TIME_IN_SECONDS,
        TicketMetric.JSON_PROPERTY_REQUESTER_UPDATED_AT,
        TicketMetric.JSON_PROPERTY_REQUESTER_WAIT_TIME_IN_MINUTES,
        TicketMetric.JSON_PROPERTY_SOLVED_AT,
        TicketMetric.JSON_PROPERTY_STATUS_UPDATED_AT,
        TicketMetric.JSON_PROPERTY_TICKET_ID,
        TicketMetric.JSON_PROPERTY_UPDATED_AT,
        TicketMetric.JSON_PROPERTY_URL
})
public record TicketMetric(
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_AGENT_WAIT_TIME_IN_MINUTES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketMetricTime agentWaitTimeInMinutes,
        @Nullable @JsonProperty(JSON_PROPERTY_ASSIGNED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime assignedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_ASSIGNEE_STATIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long assigneeStations,
        @Nullable @JsonProperty(JSON_PROPERTY_ASSIGNEE_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime assigneeUpdatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime customStatusUpdatedAt,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_FIRST_RESOLUTION_TIME_IN_MINUTES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketMetricTime firstResolutionTimeInMinutes,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_FULL_RESOLUTION_TIME_IN_MINUTES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketMetricTime fullResolutionTimeInMinutes,
        @Nullable @JsonProperty(JSON_PROPERTY_GROUP_STATIONS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long groupStations,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_INITIALLY_ASSIGNED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime initiallyAssignedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_LATEST_COMMENT_ADDED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime latestCommentAddedAt,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_ON_HOLD_TIME_IN_MINUTES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketMetricTime onHoldTimeInMinutes,
        @Nullable @JsonProperty(JSON_PROPERTY_REOPENS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long reopens,
        @Nullable @JsonProperty(JSON_PROPERTY_REPLIES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long replies,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_REPLY_TIME_IN_MINUTES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketMetricTime replyTimeInMinutes,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_REPLY_TIME_IN_SECONDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketMetricTime replyTimeInSeconds,
        @Nullable @JsonProperty(JSON_PROPERTY_REQUESTER_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime requesterUpdatedAt,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_REQUESTER_WAIT_TIME_IN_MINUTES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketMetricTime requesterWaitTimeInMinutes,
        @Nullable @JsonProperty(JSON_PROPERTY_SOLVED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime solvedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_STATUS_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime statusUpdatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_TICKET_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long ticketId,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url
) implements TicketMetricBuilder.With {

    public static final String JSON_PROPERTY_AGENT_WAIT_TIME_IN_MINUTES = "agent_wait_time_in_minutes";
    public static final String JSON_PROPERTY_ASSIGNED_AT = "assigned_at";
    public static final String JSON_PROPERTY_ASSIGNEE_STATIONS = "assignee_stations";
    public static final String JSON_PROPERTY_ASSIGNEE_UPDATED_AT = "assignee_updated_at";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CUSTOM_STATUS_UPDATED_AT = "custom_status_updated_at";
    public static final String JSON_PROPERTY_FIRST_RESOLUTION_TIME_IN_MINUTES = "first_resolution_time_in_minutes";
    public static final String JSON_PROPERTY_FULL_RESOLUTION_TIME_IN_MINUTES = "full_resolution_time_in_minutes";
    public static final String JSON_PROPERTY_GROUP_STATIONS = "group_stations";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_INITIALLY_ASSIGNED_AT = "initially_assigned_at";
    public static final String JSON_PROPERTY_LATEST_COMMENT_ADDED_AT = "latest_comment_added_at";
    public static final String JSON_PROPERTY_ON_HOLD_TIME_IN_MINUTES = "on_hold_time_in_minutes";
    public static final String JSON_PROPERTY_REOPENS = "reopens";
    public static final String JSON_PROPERTY_REPLIES = "replies";
    public static final String JSON_PROPERTY_REPLY_TIME_IN_MINUTES = "reply_time_in_minutes";
    public static final String JSON_PROPERTY_REPLY_TIME_IN_SECONDS = "reply_time_in_seconds";
    public static final String JSON_PROPERTY_REQUESTER_UPDATED_AT = "requester_updated_at";
    public static final String JSON_PROPERTY_REQUESTER_WAIT_TIME_IN_MINUTES = "requester_wait_time_in_minutes";
    public static final String JSON_PROPERTY_SOLVED_AT = "solved_at";
    public static final String JSON_PROPERTY_STATUS_UPDATED_AT = "status_updated_at";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    public TicketMetric() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }
}
