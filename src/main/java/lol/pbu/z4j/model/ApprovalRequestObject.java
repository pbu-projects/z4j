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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lol.pbu.z4j.model.ApprovalRequestAssigneeUser;
import lol.pbu.z4j.model.ApprovalRequestDecision;
import lol.pbu.z4j.model.ApprovalRequestUser;
import lol.pbu.z4j.model.ApprovalTicketDetails;
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
 * ApprovalRequestObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ApprovalRequestObject.JSON_PROPERTY_ID,
    ApprovalRequestObject.JSON_PROPERTY_WORKFLOW_INSTANCE_ID,
    ApprovalRequestObject.JSON_PROPERTY_ASSIGNEE_USER,
    ApprovalRequestObject.JSON_PROPERTY_CREATED_AT,
    ApprovalRequestObject.JSON_PROPERTY_CREATED_BY_USER,
    ApprovalRequestObject.JSON_PROPERTY_DECIDED_AT,
    ApprovalRequestObject.JSON_PROPERTY_DECISIONS,
    ApprovalRequestObject.JSON_PROPERTY_MESSAGE,
    ApprovalRequestObject.JSON_PROPERTY_STATUS,
    ApprovalRequestObject.JSON_PROPERTY_SUBJECT,
    ApprovalRequestObject.JSON_PROPERTY_TICKET_DETAILS,
    ApprovalRequestObject.JSON_PROPERTY_TICKET_ID,
    ApprovalRequestObject.JSON_PROPERTY_WITHDRAWN_REASON,
})
@Serdeable
public class ApprovalRequestObject {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_WORKFLOW_INSTANCE_ID = "workflow_instance_id";
    public static final String JSON_PROPERTY_ASSIGNEE_USER = "assignee_user";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CREATED_BY_USER = "created_by_user";
    public static final String JSON_PROPERTY_DECIDED_AT = "decided_at";
    public static final String JSON_PROPERTY_DECISIONS = "decisions";
    public static final String JSON_PROPERTY_MESSAGE = "message";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_SUBJECT = "subject";
    public static final String JSON_PROPERTY_TICKET_DETAILS = "ticket_details";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_WITHDRAWN_REASON = "withdrawn_reason";

    /**
     * <p>Automatically assigned when the approval request is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>Unique identifier for the approval workflow instance</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_WORKFLOW_INSTANCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String workflowInstanceId;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_ASSIGNEE_USER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ApprovalRequestAssigneeUser assigneeUser;

    /**
     * <p>The time the approval request was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_CREATED_BY_USER)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ApprovalRequestUser createdByUser;

    /**
     * <p>The time at which the approver submitted a decision about the request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DECIDED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime decidedAt;

    /**
     * <p>List of decisions for the approval request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DECISIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ApprovalRequestDecision> decisions;

    /**
     * <p>Details for the approval request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MESSAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String message;

    /**
     * <p>Current status of the approval request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String status;

    /**
     * <p>Subject for the approval request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_TICKET_DETAILS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ApprovalTicketDetails ticketDetails;

    /**
     * <p>The id of the ticket associated with this approval request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer ticketId;

    /**
     * <p>Reason for withdrawing the approval request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_WITHDRAWN_REASON)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String withdrawnReason;

    /**
     * Add an item to the decisions property in a chainable fashion.
     *
     * @return The same instance of ApprovalRequestObject for chaining.
     */
    public ApprovalRequestObject addDecisionsItem(ApprovalRequestDecision decisionsItem) {
        if (decisions == null) {
            decisions = new ArrayList<>();
        }
        decisions.add(decisionsItem);
        return this;
    }

}