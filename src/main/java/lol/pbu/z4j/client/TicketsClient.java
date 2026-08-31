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
import lol.pbu.z4j.model.AutocompleteProblemsRequest;
import lol.pbu.z4j.model.CountOrganizationTickets200Response;
import lol.pbu.z4j.model.JobStatusResponse;
import lol.pbu.z4j.model.ListDeletedTicketsResponse;
import lol.pbu.z4j.model.ListDeletedTicketsSortByParameter;
import lol.pbu.z4j.model.ListDeletedTicketsSortOrderParameter;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.TicketCreateRequest;
import lol.pbu.z4j.model.TicketMergeInput;
import lol.pbu.z4j.model.TicketRelatedInformation;
import lol.pbu.z4j.model.TicketResponse;
import lol.pbu.z4j.model.TicketUpdateRequest;
import lol.pbu.z4j.model.TicketUpdateResponse;
import lol.pbu.z4j.model.TicketsCreateRequest;
import lol.pbu.z4j.model.TicketsResponse;
import lol.pbu.z4j.model.TicketsUpdateRequest;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TicketsClient {

    /**
     * {@summary Autocomplete Problems}
     * <p>Returns tickets whose type is \"problem\" and whose subject contains the string specified in the <code>text</code> parameter.</p> <p>You can specify the <code>text</code> parameter in the request body rather than the query string. Example:</p> <p><code>{\"text\": \"fire\"}</code></p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param text <p>The text to search for</p> (optional)
     * @param autocompleteProblemsRequest (optional)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Post("/api/v2/problems/autocomplete")
    Mono<Map<String, Object>> autocompleteProblems(
        @QueryValue("text") @Nullable String text,
        @Body @Nullable @Valid AutocompleteProblemsRequest autocompleteProblemsRequest
    );

    /**
     * {@summary Bulk Delete Tickets}
     * <p>Accepts a comma-separated list of up to 100 ticket ids.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents with permission to delete tickets</li> </ul> <p>Agent delete permissions are set in Support. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203690936\">Deleting tickets</a> in the Support Help Center.</p> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p>
     *
     * @param ids <p>Comma-separated list of ticket ids</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Delete("/api/v2/tickets/destroy_many")
    Mono<@Valid JobStatusResponse> bulkDeleteTickets(
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary Delete Multiple Tickets Permanently}
     * <p>Permanently deletes up to 100 soft-deleted tickets. See <a href=\"https://support.zendesk.com/hc/en-us/articles/4408834005530#topic_zrm_wbj_1db\">Soft delete</a> in the Zendesk GDPR docs. To soft delete tickets, use the <a href=\"#bulk-delete-tickets\">Bulk Delete Tickets</a> endpoint.</p> <p>This endpoint accepts a comma-separated list of up to 100 ticket ids. It enqueues a ticket deletion job and returns a payload with the jobs status.</p> <p>If one ticket fails to be deleted, the endpoint still attempts to delete the others. If the job succeeds, the tickets that were successfully deleted are permanently deleted. This operation can't be undone.</p> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ids <p>Comma-separated list of ticket ids</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Delete("/api/v2/deleted_tickets/destroy_many")
    Mono<@Valid JobStatusResponse> bulkPermanentlyDeleteTickets(
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary Restore Previously Deleted Tickets in Bulk}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ids <p>Comma-separated list of ticket ids</p> (required)
     *
     * @return <p>Empty response</p> (status code 200)
     */
    @Put("/api/v2/deleted_tickets/restore_many")
    Mono<@NotNull String> bulkRestoreDeletedTickets(
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary Count Organization Tickets}
     * <p>Returns an approximate count of tickets for a specific organization. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The <code>count[refreshed_at]</code> property is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>count[refreshed_at]</code> may occasionally be null. This indicates that the count is being updated in the background, and <code>count[value]</code> is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Count of tickets</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}/tickets/count")
    Mono<@Valid CountOrganizationTickets200Response> countOrganizationTickets(
        @PathVariable("organization_id") @NotNull Long organizationId
    );

    /**
     * {@summary Count Tickets}
     * <p>Returns an approximate count of tickets in the account. If the count exceeds 100,000, it is updated every 24 hours.</p> <p><code>ccd</code> lists tickets that the specified user is cc'd on.</p> <p>The <code>count[refreshed_at]</code> property is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>count[refreshed_at]</code> may occasionally be null. This indicates that the count is being updated in the background, and <code>count[value]</code> is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Count of tickets</p> (status code 200)
     */
    @Get("/api/v2/tickets/count")
    Mono<@Valid CountOrganizationTickets200Response> countTickets();

    /**
     * {@summary Count User Assigned Tickets}
     * <p>Returns an approximate count of tickets assigned to the specified user. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The <code>count[refreshed_at]</code> property is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>count[refreshed_at]</code> may occasionally be null. This indicates that the count is being updated in the background, and <code>count[value]</code> is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Count of tickets</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/tickets/assigned/count")
    Mono<@Valid CountOrganizationTickets200Response> countUserAssignedTickets(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Count User CCD Tickets}
     * <p>Returns an approximate count of tickets where the specified user is CC'd. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The <code>count[refreshed_at]</code> property is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>count[refreshed_at]</code> may occasionally be null. This indicates that the count is being updated in the background, and <code>count[value]</code> is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Count of tickets</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/tickets/ccd/count")
    Mono<@Valid CountOrganizationTickets200Response> countUserCCDTickets(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Create Ticket}
     *
     * @param ticketCreateRequest (optional)
     *
     * @return <p>Create ticket</p> (status code 201)
     */
    @Post("/api/v2/tickets")
    Mono<@Valid TicketResponse> createTicket(
        @Body @Nullable @Valid TicketCreateRequest ticketCreateRequest
    );

    /**
     * {@summary Delete Ticket}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents with permission to delete tickets</li> </ul> <p>Agent delete permissions are set in Support. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203690936\">Deleting tickets</a> in the Support Help Center.</p> <h4>Ticket deletion rate limit</h4> <p>You can delete 400 tickets every 1 minute using this endpoint. The rate limiting mechanism behaves as described in <a href=\"/api-reference/introduction/rate-limits/\">Rate limits</a> in the API introduction. Zendesk recommends that you obey the Retry-After header values. To delete many tickets, you may use <a href=\"/api-reference/ticketing/tickets/tickets/#bulk-delete-tickets\">Bulk Delete Tickets</a>.</p>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>No content</p> (status code 204)
     */
    @Delete("/api/v2/tickets/{ticket_id}")
    Mono<Void> deleteTicket(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Delete Ticket Permanently}
     * <p>Permanently deletes a soft-deleted ticket. See <a href=\"https://support.zendesk.com/hc/en-us/articles/4408834005530#topic_zrm_wbj_1db\">Soft delete</a> in the Zendesk GDPR docs. To soft delete a ticket, use the <a href=\"#delete-ticket\">Delete Ticket</a> endpoint.</p> <p>This endpoint enqueues a ticket deletion job and returns a payload with the jobs status.</p> <p>If the job succeeds, the ticket is permanently deleted. This operation can't be undone.</p> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Delete("/api/v2/deleted_tickets/{ticket_id}")
    Mono<@Valid JobStatusResponse> deleteTicketPermanently(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary List Deleted Tickets}
     * <p>Returns a maximum of 100 deleted tickets per page. See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>The results includes all deleted (and not yet archived) tickets that have not yet been <a href=\"https://support.zendesk.com/hc/en-us/articles/4408845703194#topic_fv5_w51_sdb\">scrubbed</a> in the past 30 days. Archived tickets are not included in the results. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203657756\">About archived tickets</a> in the Support Help Center.</p> <p>The tickets are ordered chronologically by created date, from oldest to newest. The first ticket listed may not be the oldest ticket in your account due to <a href=\"https://support.zendesk.com/hc/en-us/articles/203657756\">ticket archiving</a>.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Rate Limit</h4> <p>You can make 10 requests every 1 minute using this endpoint. When making requests beyond page 100, you can make 5 requests every 1 minute. These rate limits apply to both API calls and actions performed in the Admin Center. The rate limiting mechanism behaves as described in <a href=\"/api-reference/ticketing/account-configuration/usage_limits/#monitoring-your-request-activity\">Monitoring your request activity</a> in the API introduction.</p>
     *
     * @param sortBy <p>Sort by</p> (optional)
     * @param sortOrder <p>Sort order. Defaults to \"asc\"</p> (optional)
     * @param supportTypeScope <p>Lists tickets by support type. Possible values are \"all\", \"agent\", or \"ai_agent\". Defaults to \"agent\"</p> (optional)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/deleted_tickets")
    Mono<@Valid ListDeletedTicketsResponse> listDeletedTickets(
        @QueryValue("sort_by") @Nullable ListDeletedTicketsSortByParameter sortBy,
        @QueryValue("sort_order") @Nullable ListDeletedTicketsSortOrderParameter sortOrder,
        @QueryValue("support_type_scope") @Nullable String supportTypeScope
    );

    /**
     * {@summary List Organization Tickets}
     * <p>Returns a list of tickets for a specific organization.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>List tickets</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}/tickets")
    Mono<@Valid TicketsResponse> listOrganizationTickets(
        @PathVariable("organization_id") @NotNull Long organizationId
    );

    /**
     * {@summary List Recent Tickets}
     * <p>Lists tickets that the requesting agent recently viewed in the agent interface, not recently created or updated tickets.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>List recently viewed tickets</p> (status code 200)
     */
    @Get("/api/v2/tickets/recent")
    Mono<@Valid TicketsResponse> listRecentTickets();

    /**
     * {@summary List Collaborators for a Ticket}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/collaborators")
    Mono<Map<String, Object>> listTicketCollaborators(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary List Email CCs for a Ticket}
     * <p>Returns any users cc'd on the ticket.</p> <h4>Availability</h4> <p>The <a href=\"https://support.zendesk.com/hc/en-us/articles/203690846\">CCs and Followers</a> feature must be enabled in Zendesk Support.</p> <p>If the feature is not enabled, the default CC functionality is used. In that case, use <a href=\"/api-reference/ticketing/tickets/tickets/#list-collaborators-for-a-ticket\">List Collaborators</a> to list the users cc'ed on the ticket.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/email_ccs")
    Mono<Map<String, Object>> listTicketEmailCCs(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary List Followers for a Ticket}
     * <p>Returns any users who follow the ticket.</p> <h4>Availability</h4> <p>The <a href=\"https://support.zendesk.com/hc/en-us/articles/203690846\">CCs and Followers</a> feature must be enabled in Zendesk Support.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/followers")
    Mono<Map<String, Object>> listTicketFollowers(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary List Ticket Incidents}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/incidents")
    Mono<Map<String, Object>> listTicketIncidents(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary List Ticket Problems}
     * <p>The response is always ordered by <code>updated_at</code> in descending order</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/problems")
    Mono<Map<String, Object>> listTicketProblems();

    /**
     * {@summary List Tickets}
     *
     * @param externalId <p>Lists tickets by external id. External ids don't have to be unique for each ticket. As a result, the request may return multiple tickets with the same external id.</p> (optional)
     * @param supportTypeScope <p>Lists tickets by support type. Possible values are \"all\", \"agent\", or \"ai_agent\". Defaults to \"agent\"</p> (optional)
     *
     * @return <p>List tickets</p> (status code 200)
     */
    @Get("/api/v2/tickets")
    Mono<@Valid TicketsResponse> listTickets(
        @QueryValue("external_id") @Nullable String externalId,
        @QueryValue("support_type_scope") @Nullable String supportTypeScope
    );

    /**
     * {@summary List User Assigned Tickets}
     * <p>Lists tickets assigned to the specified user.</p>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>List tickets assigned to the user</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/tickets/assigned")
    Mono<@Valid TicketsResponse> listUserAssignedTickets(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary List User CCD Tickets}
     * <p>Lists tickets where the specified user is CC'd.</p>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>List tickets where the user is CC'd</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/tickets/ccd")
    Mono<@Valid TicketsResponse> listUserCCDTickets(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary List User Followed Tickets}
     * <p>Lists tickets the specified user is following.</p>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>List tickets followed by the user</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/tickets/followed")
    Mono<@Valid TicketsResponse> listUserFollowedTickets(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary List User Requested Tickets}
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>List tickets requested by the user</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/tickets/requested")
    Mono<@Valid TicketsResponse> listUserRequestedTickets(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Bulk Mark Tickets as Spam}
     * <p>Accepts a comma-separated list of up to 100 ticket ids.</p> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ids <p>Comma-separated list of ticket ids</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Put("/api/v2/tickets/mark_many_as_spam")
    Mono<@Valid JobStatusResponse> markManyTicketsAsSpam(
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary Mark Ticket as Spam and Suspend Requester}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Put("/api/v2/tickets/{ticket_id}/mark_as_spam")
    Mono<@NotNull String> markTicketAsSpamAndSuspendRequester(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Merge Tickets into Target Ticket}
     * <p>Merges one or more tickets into the ticket with the specified id.</p> <p>See <a href=\"https://support.zendesk.com/hc/en-us/articles/203690916\">Merging tickets</a> in the Support Help Center for ticket merging rules.</p> <p>Any attachment to the source ticket is copied to the target ticket.</p> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <p>Agents in the Enterprise account must have merge permissions. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203662026\">Creating custom roles and assigning agents (Enterprise)</a> in the Support Help Center.</p> <h4>Available parameters</h4> <p>The request takes a data object with the following properties:</p> <p>| Name                     | Type    | Required | Comments                                                | | ------------------------ | ------- | -------- | ------------------------------------------------------- | | ids                      | array   | yes      | Ids of tickets to merge into the target ticket          | | target_comment           | string  | no       | Private comment to add to the target ticket. This comment is optional but strongly recommended | | source_comment           | string  | no       | Private comment to add to the source ticket. This comment is optional but strongly recommended | | target_comment_is_public | boolean | no       | Whether comments in the target ticket are public or private   | | source_comment_is_public | boolean | no       | Whether comments in the source tickets are public or private |</p> <p><code>target_comment</code> and <code>source_comment</code> can be used to provide a reason for the merge for recordkeeping purposes. If the source ticket has attachments, they are included in <code>target_comment</code>.</p> <p>Comments are private and can't be modified in the following cases:</p> <ul> <li>Any of the sources or target tickets are private</li> <li>Any of the sources or target tickets were created through X (formerly Twitter), Facebook or the Channel framework</li> </ul> <p>In any other case, comments default to private but can be modified with the comment privacy parameters.</p>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param ticketMergeInput (optional)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Post("/api/v2/tickets/{ticket_id}/merge")
    Mono<@Valid JobStatusResponse> mergeTicketsIntoTargetTicket(
        @PathVariable("ticket_id") @NotNull Long ticketId,
        @Body @Nullable @Valid TicketMergeInput ticketMergeInput
    );

    /**
     * {@summary Restore a Previously Deleted Ticket}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Empty response</p> (status code 200)
     */
    @Put("/api/v2/deleted_tickets/{ticket_id}/restore")
    Mono<@NotNull String> restoreDeletedTicket(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Show Ticket}
     * <p>Returns a number of ticket properties, but doesn't include the full comment thread. The initial comment is available in the ticket's <a href=\"/api-reference/ticketing/tickets/tickets/#json-format\">description property</a>. To retrieve all the ticket's comments, use <a href=\"/api-reference/ticketing/tickets/ticket_comments/#list-comments\">List Comments</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Ticket</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}")
    Mono<@Valid TicketResponse> showTicket(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Ticket Related Information}
     * <p>The request returns a data object with the following properties:</p> <p>| Name                | Type    | Comment | ------------------- | ------- | ------- | topic_id            | string  | Related topic in the Web portal (deprecated feature) | jira_issue_ids      | array   | Array of associated jira issues | followup_source_ids | array   | Sources to follow up | from_archive        | boolean | Is true if the current ticket is archived | incidents           | integer | A count of related incident occurrences</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/related")
    Mono<@Valid TicketRelatedInformation> ticketRelatedInformation(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Create Many Tickets}
     * <p>Accepts an array of up to 100 ticket objects. <strong>Note</strong>: Every ticket created with this endpoint may be affected by your business rules, which can include sending email notifications to your end users. If you are importing historical tickets or creating more than 1000 tickets, consider using the <a href=\"/api-reference/ticketing/tickets/ticket_import/#ticket-bulk-import\">Ticket Bulk Import</a> endpoint.</p> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketsCreateRequest (optional)
     *
     * @return <p>Create many tickets</p> (status code 200)
     */
    @Post("/api/v2/tickets/create_many")
    Mono<@Valid JobStatusResponse> ticketsCreateMany(
        @Body @Nullable @Valid TicketsCreateRequest ticketsCreateRequest
    );

    /**
     * {@summary Show Multiple Tickets}
     * <p>Accepts a comma-separated list of ticket ids to return.</p> <p>This endpoint will return up to 100 tickets records.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ids <p>Comma-separated list of ticket ids</p> (required)
     *
     * @return <p>List tickets</p> (status code 200)
     */
    @Get("/api/v2/tickets/show_many")
    Mono<@Valid TicketsResponse> ticketsShowMany(
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary Update Many Tickets}
     * <p>Accepts an array of up to 100 ticket objects, or a comma-separated list of up to 100 ticket ids.</p>
     *
     * @param ticketsUpdateRequest <p>Ticket object for bulk updates (with ids query parameter) or array of ticket objects for batch updates</p> (required)
     * @param ids <p>Comma-separated list of ticket ids</p> (optional)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Put("/api/v2/tickets/update_many")
    Mono<@Valid JobStatusResponse> ticketsUpdateMany(
        @Body @NotNull @Valid TicketsUpdateRequest ticketsUpdateRequest,
        @QueryValue("ids") @Nullable String ids
    );

    /**
     * {@summary Update Ticket}
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param ticketUpdateRequest (optional)
     *
     * @return <p>Successful request</p> (status code 200)
     */
    @Put("/api/v2/tickets/{ticket_id}")
    Mono<@Valid TicketUpdateResponse> updateTicket(
        @PathVariable("ticket_id") @NotNull Long ticketId,
        @Body @Nullable @Valid TicketUpdateRequest ticketUpdateRequest
    );
}