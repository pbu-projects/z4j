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
package lol.pbu.z4j.client;

import io.micronaut.http.annotation.*;
import io.micronaut.core.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.core.convert.format.Format;
import lol.pbu.z4j.model.ApprovalRequestResponse;
import lol.pbu.z4j.model.ApprovalRequestsSearchResponse;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.UpdateDecisionApprovalRequestRequest;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface ApprovalRequestsClient {

    /**
     * {@summary Get Approvals by Approval Workflow Id}
     * <p>Returns a list of approvals associated with a specific workflow instance. Results can be filtered by approval request status.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param approvalWorkflowInstanceId <p>The id of the approval workflow instance</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/approval_workflow_instances/{approval_workflow_instance_id}/approval_requests/search")
    Mono<@Valid ApprovalRequestsSearchResponse> searchApprovals(
        @PathVariable("approval_workflow_instance_id") @NotNull String approvalWorkflowInstanceId
    );

    /**
     * {@summary Show Approval Request}
     * <p>Shows an approval request.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param approvalWorkflowInstanceId <p>The id of the approval workflow instance</p> (required)
     * @param approvalRequestId <p>The id of the approval request</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/approval_workflow_instances/{approval_workflow_instance_id}/approval_requests/{approval_request_id}")
    Mono<@Valid ApprovalRequestResponse> showApprovalRequest(
        @PathVariable("approval_workflow_instance_id") @NotNull String approvalWorkflowInstanceId,
        @PathVariable("approval_request_id") @NotNull String approvalRequestId
    );

    /**
     * {@summary Update Approval Request Status}
     * <p>Updates the  approver's decision about an approval request.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param approvalWorkflowInstanceId <p>The id of the approval workflow instance</p> (required)
     * @param approvalRequestId <p>The id of the approval request</p> (required)
     * @param updateDecisionApprovalRequestRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Patch("/api/v2/approval_workflow_instances/{approval_workflow_instance_id}/approval_requests/{approval_request_id}/decision")
    Mono<@Valid ApprovalRequestResponse> updateDecisionApprovalRequest(
        @PathVariable("approval_workflow_instance_id") @NotNull String approvalWorkflowInstanceId,
        @PathVariable("approval_request_id") @NotNull String approvalRequestId,
        @Body @NotNull @Valid UpdateDecisionApprovalRequestRequest updateDecisionApprovalRequestRequest
    );
}