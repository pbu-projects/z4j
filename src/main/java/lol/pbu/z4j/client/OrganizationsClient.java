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
import lol.pbu.z4j.model.CountOrganizationResponse;
import lol.pbu.z4j.model.CreateOrganizationRequest;
import lol.pbu.z4j.model.Errors;
import lol.pbu.z4j.model.JobStatusResponse;
import lol.pbu.z4j.model.ListCustomObjectRecordAttachments400Response;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.OrganizationMergeListResponse;
import lol.pbu.z4j.model.OrganizationMergeRequest;
import lol.pbu.z4j.model.OrganizationMergeResponse;
import lol.pbu.z4j.model.OrganizationResponse;
import lol.pbu.z4j.model.OrganizationsRelatedResponse;
import lol.pbu.z4j.model.OrganizationsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface OrganizationsClient {

    /**
     * {@summary Autocomplete Organizations}
     * <p>Returns an array of organizations whose name starts with the value specified in the <code>name</code> parameter.</p> <h4>Pagination</h4> <ul> <li>Offset pagination only</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/#using-offset-pagination\">Using Offset Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param name <p>A substring of an organization to search for</p> (required)
     * @param fieldId <p>The id of a lookup relationship field.  The type of field is determined by the <code>source</code> param</p> (optional)
     * @param source <p>If a <code>field_id</code> is provided, this specifies the type of the field. For example, if the field is on a \"zen:user\", it references a field on a user</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Bad request</p> (status code 400)
     *         or <p>Too Many Requests</p> (status code 429)
     *         or <p>Internal Server Error</p> (status code 500)
     */
    @Get("/api/v2/organizations/autocomplete")
    Mono<@Valid OrganizationsResponse> autocompleteOrganizations(
        @QueryValue("name") @NotNull String name,
        @QueryValue("field_id") @Nullable String fieldId,
        @QueryValue("source") @Nullable String source
    );

    /**
     * {@summary Count Organizations}
     * <p>Returns an approximate count of organizations. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The <code>refreshed_at</code> property of the <code>count</code> object is a timestamp that indicates when the count was last updated.</p> <p>When the count exceeds 100,000, the <code>refreshed_at</code> property may occasionally be null. This indicates that the count is being updated in the background and the <code>value</code> property of the <code>count</code> object is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/count")
    Mono<@Valid CountOrganizationResponse> countOrganizations();

    /**
     * {@summary Count User's Organizations}
     * <p>Returns an approximate count of organizations for a specific user. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The <code>refreshed_at</code> property of the <code>count</code> object is a timestamp that indicates when the count was last updated.</p> <p>When the count exceeds 100,000, the <code>refreshed_at</code> property may occasionally be null. This indicates that the count is being updated in the background and the <code>value</code> property of the <code>count</code> object is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/organizations/count")
    Mono<@Valid CountOrganizationResponse> countUserOrganizations(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Create Many Organizations}
     * <p>Accepts an array of up to 100 organization objects.</p> <h4>Response</h4> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Agents, with restrictions applying on certain actions</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/organizations/create_many")
    Mono<@Valid JobStatusResponse> createManyOrganizations();

    /**
     * {@summary Create Or Update Organization}
     * <p>Creates an organization if it doesn't already exist, or updates an existing organization. Using this method means one less call to check if an organization exists before creating it. You need to specify the id or external id when updating an organization to avoid a duplicate error response. Name is not available as a matching criteria.</p> <h4>Allowed For</h4> <ul> <li>Agents, with restrictions on certain actions</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/organizations/create_or_update")
    Mono<@Valid OrganizationResponse> createOrUpdateOrganization();

    /**
     * {@summary Create Organization}
     * <p>You must provide a unique <code>name</code> for each organization. Normally the system doesn't allow records to be created with identical names. However, a race condition can occur if you make two or more identical POSTs very close to each other, causing the records to have identical organization names.</p> <p><strong>Note</strong>: Leading and trailing whitespace in <code>name</code> is automatically trimmed before validation. This means that names differing only by whitespace are treated as duplicates. For example, \"API Company\" and \"API Company \" are considered the same name.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage organizations (Enterprise only)</li> </ul>
     *
     * @param createOrganizationRequest (required)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/organizations")
    Mono<@Valid OrganizationResponse> createOrganization(
        @Body @NotNull @Valid CreateOrganizationRequest createOrganizationRequest
    );

    /**
     * {@summary Merge Organization With Another Organization}
     * <p>Merges two organizations by moving all users, tickets, and domain names from the organization specified by <code>{organization_id}</code> to the organization specified by <code>winner_id</code>. After the merge:</p> <ul> <li>The \"losing\" organization will be deleted.</li> <li>Other organization fields and their values will not be carried over to the \"winning\" organization.</li> <li>The merge operation creates an <code>Organization Merge</code> record which contains a status indicating the progress of the merge.</li> </ul> <p><strong>Note</strong>: This operation is irreversible.</p> <h4>Merge Statuses</h4> <p>| Status | Description | |--------|-------------| | new | A job has been queued to merge the two organizations. | | in progress | The job to merge the two organizations has started. | | error | An error occurred during the merge job. The merge can be retried by repeating the API call. |  | complete | The merge has been completed successfully. |</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     * @param organizationMergeRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/organizations/{organization_id}/merge")
    Mono<@Valid OrganizationMergeResponse> createOrganizationMerge(
        @PathVariable("organization_id") @NotNull Integer organizationId,
        @Body @NotNull @Valid OrganizationMergeRequest organizationMergeRequest
    );

    /**
     * {@summary Bulk Delete Organizations}
     * <p>Accepts a comma-separated list of up to 100 organization ids or external ids.</p> <h4>Response</h4> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage organizations (Enterprise only)</li> </ul>
     *
     * @param ids <p>A list of organization ids</p> (optional)
     * @param externalIds <p>A list of external ids</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Delete("/api/v2/organizations/destroy_many")
    Mono<@Valid JobStatusResponse> deleteManyOrganizations(
        @QueryValue("ids") @Nullable String ids,
        @QueryValue("external_ids") @Nullable String externalIds
    );

    /**
     * {@summary Delete Organization}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage organizations (Enterprise only)</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>No Content Response</p> (status code 204)
     */
    @Delete("/api/v2/organizations/{organization_id}")
    Mono<Void> deleteOrganization(
        @PathVariable("organization_id") @NotNull Integer organizationId
    );

    /**
     * {@summary List Organization Merges}
     * <p>Retrieves a list of all organization merge operations associated with a given organization. This endpoint allows you to track the history of merge actions for an organization, including ongoing and completed merges.</p> <p>Each entry in the list contains details such as the ID of the merge, the winning and losing organization IDs, the current status of the merge, and a URL to access the <code>Organization Merge</code> record.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination is used for this endpoint.</li> <li>A maximum of 100 records can be returned per page.</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a> for more details.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}/merges")
    Mono<@Valid OrganizationMergeListResponse> listOrganizationMerges(
        @PathVariable("organization_id") @NotNull Integer organizationId
    );

    /**
     * {@summary List Organizations}
     * <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents, with certain restrictions</li> </ul> <p>If the agent has a custom agent role that restricts their access to only users in their own organization, a 403 Forbidden error is returned. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203662026-Creating-custom-roles-and-assigning-agents#topic_cxn_hig_bd\">Creating custom agent roles</a> in Zendesk help.</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations")
    Mono<@Valid OrganizationsResponse> listOrganizations();

    /**
     * {@summary List User Organizations}
     * <p>Returns a list of organizations associated with the specified user.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents, with certain restrictions</li> </ul> <p>If the agent has a custom agent role that restricts their access to only users in their own organization, a 403 Forbidden error is returned. See <a href=\"https://support.zendesk.com/hc/en-us/articles/203662026-Creating-custom-roles-and-assigning-agents#topic_cxn_hig_bd\">Creating custom agent roles</a> in Zendesk help.</p>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Forbidden - Agent has restricted access</p> (status code 403)
     *         or <p>User not found</p> (status code 404)
     */
    @Get("/api/v2/users/{user_id}/organizations")
    Mono<@Valid OrganizationsResponse> listUserOrganizations(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Show Organization's Related Information}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}/related")
    Mono<@Valid OrganizationsRelatedResponse> organizationRelated(
        @PathVariable("organization_id") @NotNull Integer organizationId
    );

    /**
     * {@summary Search Organizations}
     * <p>Returns an array of organizations matching the criteria. You may search by an organization's <code>external_id</code> or <code>name</code>, but not both:</p> <h4>Searching by <code>external_id</code></h4> <p>If you set the <code>external_id</code> value of an organization to associate it to an external record, you can use it to search for the organization.</p> <p>For an organization to be returned, its <code>external_id</code> must exactly match the value provided (case insensitive).</p> <h4>Searching by <code>name</code></h4> <p>For an organization to be returned, its <code>name</code> must exactly match the value provided (case insensitive).</p> <h4>Allowed For:</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to add or modify organizations (Enterprise only)</li> </ul> <p>See <a href=\"https://support.zendesk.com/hc/en-us/articles/203662026#topic_cxn_hig_bd\">Creating custom agent roles</a> in the Support Help Center.</p>
     *
     * @param externalId <p>The external id of an organization</p> (optional)
     * @param name <p>The name of an organization</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/search")
    Mono<@Valid OrganizationsResponse> searchOrganizations(
        @QueryValue("external_id") @Nullable Integer externalId,
        @QueryValue("name") @Nullable String name
    );

    /**
     * {@summary Show Many Organizations}
     * <p>Accepts a comma-separated list of up to 100 organization ids or external ids.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @param ids <p>A list of organization ids</p> (optional)
     * @param externalIds <p>A list of external ids</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/show_many")
    Mono<@Valid OrganizationsResponse> showManyOrganizations(
        @QueryValue("ids") @Nullable String ids,
        @QueryValue("external_ids") @Nullable String externalIds
    );

    /**
     * {@summary Show Organization}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}")
    Mono<@Valid OrganizationResponse> showOrganization(
        @PathVariable("organization_id") @NotNull Integer organizationId
    );

    /**
     * {@summary Show Organization Merge}
     * <p>Retrieves the details of a specific organization merge operation. This endpoint is useful for obtaining the status and outcome of a merge that was previously initiated. It provides information such as the winning and losing organization IDs, the status of the merge, and the associated URLs.</p> <p>This endpoint can be used to determine if a merge is still in progress, has completed successfully, or has encountered an error.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param organizationMergeId <p>The ID of the organization merge</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organization_merges/{organization_merge_id}")
    Mono<@Valid OrganizationMergeResponse> showOrganizationMerge(
        @PathVariable("organization_merge_id") @NotNull String organizationMergeId
    );

    /**
     * {@summary Update Many Organizations}
     * <p>Bulk or batch updates up to 100 organizations.</p> <h4>Bulk update</h4> <p>To make the same change to multiple organizations, use the following endpoint and data format:</p> <p><code>https://{subdomain}.zendesk.com/api/v2/organizations/update_many.json?ids=1,2,3</code></p> <p><code>js {   \"organization\": {     \"notes\": \"Priority\"   } }</code></p> <h4>Batch update</h4> <p>To make different changes to multiple organizations, use the following endpoint and data format:</p> <p><code>https://{subdomain}.zendesk.com/api/v2/organizations/update_many.json</code></p> <p><code>js {   \"organizations\": [     { \"id\": 1, \"notes\": \"Priority\" },     { \"id\": 2, \"notes\": \"Normal\" }   ] }</code></p> <h4>Response</h4> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul> <p>Agents with no permissions restrictions can only update \"notes\" on organizations.</p>
     *
     * @param ids <p>A list of organization ids</p> (optional)
     * @param externalIds <p>A list of external ids</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/organizations/update_many")
    Mono<@Valid JobStatusResponse> updateManyOrganizations(
        @QueryValue("ids") @Nullable String ids,
        @QueryValue("external_ids") @Nullable String externalIds
    );

    /**
     * {@summary Update Organization}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul> <p>Agents with no permissions restrictions can only update \"notes\" on organizations.</p> <p><strong>Note:</strong> Updating an organization's <code>domain_names</code> property overwrites all existing <code>domain_names</code> values. To prevent this, submit a complete list of <code>domain_names</code> for the organization in your request.</p> <h4>Example Request</h4> <p><code>js {   \"organization\": {     \"notes\": \"Something interesting\"   } }</code></p>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Too Many Requests</p> (status code 429)
     */
    @Put("/api/v2/organizations/{organization_id}")
    Mono<@Valid OrganizationResponse> updateOrganization(
        @PathVariable("organization_id") @NotNull Integer organizationId,
        @Body @NotNull @Valid CreateOrganizationRequest updateOrganizationRequest
    );
}