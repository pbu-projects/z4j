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
import java.time.ZonedDateTime;
import lol.pbu.z4j.model.TicketMetricObjectAgentWaitTimeInMinutes;
import lol.pbu.z4j.model.TicketMetricObjectFirstResolutionTimeInMinutes;
import lol.pbu.z4j.model.TicketMetricObjectFullResolutionTimeInMinutes;
import lol.pbu.z4j.model.TicketMetricObjectOnHoldTimeInMinutes;
import lol.pbu.z4j.model.TicketMetricObjectReplyTimeInMinutes;
import lol.pbu.z4j.model.TicketMetricObjectReplyTimeInSeconds;
import lol.pbu.z4j.model.TicketMetricObjectRequesterWaitTimeInMinutes;
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
 * TicketMetricObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketMetricObject.JSON_PROPERTY_AGENT_WAIT_TIME_IN_MINUTES,
    TicketMetricObject.JSON_PROPERTY_ASSIGNED_AT,
    TicketMetricObject.JSON_PROPERTY_ASSIGNEE_STATIONS,
    TicketMetricObject.JSON_PROPERTY_ASSIGNEE_UPDATED_AT,
    TicketMetricObject.JSON_PROPERTY_CREATED_AT,
    TicketMetricObject.JSON_PROPERTY_CUSTOM_STATUS_UPDATED_AT,
    TicketMetricObject.JSON_PROPERTY_FIRST_RESOLUTION_TIME_IN_MINUTES,
    TicketMetricObject.JSON_PROPERTY_FULL_RESOLUTION_TIME_IN_MINUTES,
    TicketMetricObject.JSON_PROPERTY_GROUP_STATIONS,
    TicketMetricObject.JSON_PROPERTY_ID,
    TicketMetricObject.JSON_PROPERTY_INITIALLY_ASSIGNED_AT,
    TicketMetricObject.JSON_PROPERTY_LATEST_COMMENT_ADDED_AT,
    TicketMetricObject.JSON_PROPERTY_ON_HOLD_TIME_IN_MINUTES,
    TicketMetricObject.JSON_PROPERTY_REOPENS,
    TicketMetricObject.JSON_PROPERTY_REPLIES,
    TicketMetricObject.JSON_PROPERTY_REPLY_TIME_IN_MINUTES,
    TicketMetricObject.JSON_PROPERTY_REPLY_TIME_IN_SECONDS,
    TicketMetricObject.JSON_PROPERTY_REQUESTER_UPDATED_AT,
    TicketMetricObject.JSON_PROPERTY_REQUESTER_WAIT_TIME_IN_MINUTES,
    TicketMetricObject.JSON_PROPERTY_SOLVED_AT,
    TicketMetricObject.JSON_PROPERTY_STATUS_UPDATED_AT,
    TicketMetricObject.JSON_PROPERTY_TICKET_ID,
    TicketMetricObject.JSON_PROPERTY_UPDATED_AT,
    TicketMetricObject.JSON_PROPERTY_URL,
})
@Serdeable
public class TicketMetricObject {

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

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_AGENT_WAIT_TIME_IN_MINUTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketMetricObjectAgentWaitTimeInMinutes agentWaitTimeInMinutes;

    /**
     * <p>When the ticket was assigned</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime assignedAt;

    /**
     * <p>Number of assignees the ticket had</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_STATIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long assigneeStations;

    /**
     * <p>When the assignee last updated the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime assigneeUpdatedAt;

    /**
     * <p>When the record was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The date and time the ticket's custom status was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_STATUS_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime customStatusUpdatedAt;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_FIRST_RESOLUTION_TIME_IN_MINUTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketMetricObjectFirstResolutionTimeInMinutes firstResolutionTimeInMinutes;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_FULL_RESOLUTION_TIME_IN_MINUTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketMetricObjectFullResolutionTimeInMinutes fullResolutionTimeInMinutes;

    /**
     * <p>Number of groups the ticket passed through</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_STATIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long groupStations;

    /**
     * <p>Automatically assigned when the client is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>When the ticket was initially assigned</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_INITIALLY_ASSIGNED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime initiallyAssignedAt;

    /**
     * <p>When the latest comment was added</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LATEST_COMMENT_ADDED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime latestCommentAddedAt;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_ON_HOLD_TIME_IN_MINUTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketMetricObjectOnHoldTimeInMinutes onHoldTimeInMinutes;

    /**
     * <p>Total number of times the ticket was reopened</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REOPENS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long reopens;

    /**
     * <p>The number of public replies added to a ticket by an agent</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REPLIES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long replies;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_REPLY_TIME_IN_MINUTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketMetricObjectReplyTimeInMinutes replyTimeInMinutes;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_REPLY_TIME_IN_SECONDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketMetricObjectReplyTimeInSeconds replyTimeInSeconds;

    /**
     * <p>When the requester last updated the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTER_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime requesterUpdatedAt;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_REQUESTER_WAIT_TIME_IN_MINUTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketMetricObjectRequesterWaitTimeInMinutes requesterWaitTimeInMinutes;

    /**
     * <p>When the ticket was solved</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOLVED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime solvedAt;

    /**
     * <p>When the status of the ticket was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime statusUpdatedAt;

    /**
     * <p>Id of the associated ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketId;

    /**
     * <p>When the record was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of the ticket metric</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}