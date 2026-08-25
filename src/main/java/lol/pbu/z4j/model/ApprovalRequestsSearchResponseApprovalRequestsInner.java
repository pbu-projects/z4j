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
 * ApprovalRequestsSearchResponseApprovalRequestsInner
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    ApprovalRequestsSearchResponseApprovalRequestsInner.JSON_PROPERTY_APPROVAL_WORKFLOW_INSTANCE_ID,
    ApprovalRequestsSearchResponseApprovalRequestsInner.JSON_PROPERTY_CREATED_AT,
    ApprovalRequestsSearchResponseApprovalRequestsInner.JSON_PROPERTY_CREATED_BY_NAME,
    ApprovalRequestsSearchResponseApprovalRequestsInner.JSON_PROPERTY_ID,
    ApprovalRequestsSearchResponseApprovalRequestsInner.JSON_PROPERTY_MESSAGE,
    ApprovalRequestsSearchResponseApprovalRequestsInner.JSON_PROPERTY_REQUESTER_NAME,
    ApprovalRequestsSearchResponseApprovalRequestsInner.JSON_PROPERTY_STATUS,
    ApprovalRequestsSearchResponseApprovalRequestsInner.JSON_PROPERTY_SUBJECT,
})
@Serdeable
public class ApprovalRequestsSearchResponseApprovalRequestsInner {

    public static final String JSON_PROPERTY_APPROVAL_WORKFLOW_INSTANCE_ID = "approval_workflow_instance_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CREATED_BY_NAME = "created_by_name";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_MESSAGE = "message";
    public static final String JSON_PROPERTY_REQUESTER_NAME = "requester_name";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_SUBJECT = "subject";

    /**
     * <p>Unique identifier for the approval workflow instance</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_APPROVAL_WORKFLOW_INSTANCE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String approvalWorkflowInstanceId;

    /**
     * <p>The time the approval request was created was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Name of the user that created the approval request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_BY_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdByName;

    /**
     * <p>Unique identifier for the approval request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>Details for the approval request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MESSAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String message;

    /**
     * <p>Name of the user that can make a decision on the approval request</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REQUESTER_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String requesterName;

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

}