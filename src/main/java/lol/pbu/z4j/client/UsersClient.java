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
import lol.pbu.z4j.model.AutocompleteUsersPostRequest;
import lol.pbu.z4j.model.ComplianceDeletionStatusesResponse;
import lol.pbu.z4j.model.CountResponse;
import lol.pbu.z4j.model.CurrentUserResponse;
import lol.pbu.z4j.model.DeleteTicketContentPin404Response;
import lol.pbu.z4j.model.DeletedUserResponse;
import lol.pbu.z4j.model.DeletedUsersResponse;
import lol.pbu.z4j.model.JobStatusResponse;
import lol.pbu.z4j.model.ListCustomObjectRecordAttachments400Response;
import lol.pbu.z4j.model.ListGroupUsersRoleParameter;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.UpdateManyUsersRequest;
import lol.pbu.z4j.model.UserEntitlementsFullResponse;
import lol.pbu.z4j.model.UserRelatedResponse;
import lol.pbu.z4j.model.UserRequest;
import lol.pbu.z4j.model.UserResponse;
import lol.pbu.z4j.model.UserSettingsResponse;
import lol.pbu.z4j.model.UserSettingsUpdateRequest;
import lol.pbu.z4j.model.UsersRequest;
import lol.pbu.z4j.model.UsersResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface UsersClient {

    /**
     * {@summary Autocomplete Users}
     * <p>Returns an array of users whose name starts with the value specified in the <code>name</code> parameter. It only returns users with no foreign identities.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param name <p>The name to search for the user.</p> (required)
     * @param fieldId <p>The id of a lookup relationship field.  The type of field is determined by the <code>source</code> param</p> (optional)
     * @param source <p>If a <code>field_id</code> is provided, this specifies the type of the field. For example, if the field is on a \"zen:user\", it references a field on a user</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/autocomplete")
    Mono<@Valid UsersResponse> autocompleteUsers(
        @QueryValue("name") @NotNull String name,
        @QueryValue("field_id") @Nullable String fieldId,
        @QueryValue("source") @Nullable String source
    );

    /**
     * {@summary Autocomplete Users by Request Body}
     * <p>Returns an array of users whose name starts with the value specified in the <code>name</code> property in the request body. It only returns users with no foreign identities.</p> <p>This endpoint accepts the same parameters as the <a href=\"#autocomplete-users-by-query-string\">GET method</a> but they are specified in the request body instead of the query string.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param autocompleteUsersPostRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Bad request - Invalid filter value or query error</p> (status code 400)
     *         or <p>Internal server error</p> (status code 500)
     */
    @Post("/api/v2/users/autocomplete")
    Mono<@Valid UsersResponse> autocompleteUsersPost(
        @Body @NotNull @Valid AutocompleteUsersPostRequest autocompleteUsersPostRequest
    );

    /**
     * {@summary Count Deleted Users}
     * <p>Returns an approximate count of deleted users, including permanently deleted users. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The response includes a <code>refreshed_at</code> property in a <code>count</code> object that contains a timestamp indicating when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>count[refreshed_at]</code> may occasionally be null. This indicates that the count is being updated in the background, and <code>count[value]</code> is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/deleted_users/count")
    Mono<@Valid CountResponse> countDeletedUsers();

    /**
     * {@summary Count Users By Group}
     * <p>Returns an approximate count of users in the specified group. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The response includes a <code>refreshed_at</code> property in a <code>count</code> object that contains a timestamp indicating when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, the <code>refreshed_at</code> property may occasionally be null. This indicates that the count is being updated in the background. The <code>count</code> object's <code>value</code> property is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents and Light Agents</li> </ul>
     *
     * @param groupId <p>The ID of the group</p> (required)
     * @param role <p>Filters the results by role. Possible values are \"end-user\", \"agent\", or \"admin\"</p> (optional)
     * @param role2 <p>Filters the results by more than one role using the format <code>role[]={role}&amp;role[]={role}</code></p> (optional)
     * @param permissionSet <p>For custom roles which is available on the Enterprise plan and above. You can only filter by one role ID per request</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/groups/{group_id}/users/count")
    Mono<@Valid CountResponse> countGroupUsers(
        @PathVariable("group_id") @NotNull Long groupId,
        @QueryValue("role") @Nullable ListGroupUsersRoleParameter role,
        @QueryValue("role[]") @Nullable String role2,
        @QueryValue("permission_set") @Nullable Long permissionSet
    );

    /**
     * {@summary Count Organization Users}
     * <p>Returns an approximate count of users for a specific organization. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The response includes a <code>refreshed_at</code> property in a <code>count</code> object that contains a timestamp indicating when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, the <code>refreshed_at</code> property may occasionally be null. This indicates that the count is being updated in the background. The <code>count</code> object's <code>value</code> property is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents and Light Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     * @param role <p>Filters the results by role. Possible values are \"end-user\", \"agent\", or \"admin\"</p> (optional)
     * @param role2 <p>Filters the results by more than one role using the format <code>role[]={role}&amp;role[]={role}</code></p> (optional)
     * @param permissionSet <p>For custom roles which is available on the Enterprise plan and above. You can only filter by one role ID per request</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}/users/count")
    Mono<@Valid CountResponse> countOrganizationUsers(
        @PathVariable("organization_id") @NotNull Long organizationId,
        @QueryValue("role") @Nullable ListGroupUsersRoleParameter role,
        @QueryValue("role[]") @Nullable String role2,
        @QueryValue("permission_set") @Nullable Long permissionSet
    );

    /**
     * {@summary Count Users}
     * <p>Returns an approximate count of users. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The response includes a <code>refreshed_at</code> property in a <code>count</code> object that contains a timestamp indicating when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, the <code>refreshed_at</code> property may occasionally be null. This indicates that the count is being updated in the background. The <code>count</code> object's <code>value</code> property is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents and Light Agents</li> </ul>
     *
     * @param role <p>Filters the results by role. Possible values are \"end-user\", \"agent\", or \"admin\"</p> (optional)
     * @param role2 <p>Filters the results by more than one role using the format <code>role[]={role}&amp;role[]={role}</code></p> (optional)
     * @param permissionSet <p>For custom roles which is available on the Enterprise plan and above. You can only filter by one role ID per request</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/count")
    Mono<@Valid CountResponse> countUsers(
        @QueryValue("role") @Nullable ListGroupUsersRoleParameter role,
        @QueryValue("role[]") @Nullable String role2,
        @QueryValue("permission_set") @Nullable Long permissionSet
    );

    /**
     * {@summary Create Many Users}
     * <p>Accepts an array of up to 100 user objects.</p> <p><strong>Note</strong>: To protect the data in your Zendesk account, bulk user imports are not enabled by default in Zendesk accounts. The account owner must contact <a href=\"https://support.zendesk.com/hc/en-us/articles/4408843597850\">Zendesk Customer Support</a> to enable the imports. A 403 Forbidden error is returned if data imports are not enabled.</p> <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage end users or team members</li> </ul> <h4>Specifying an organization</h4> <p>You can assign a user to an existing organization by setting an <code>organization_id</code> property in the user object.</p> <h4>Response</h4> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p>
     *
     * @param usersRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/users/create_many")
    Mono<@Valid JobStatusResponse> createManyUsers(
        @Body @NotNull @Valid UsersRequest usersRequest
    );

    /**
     * {@summary Create Or Update Many Users}
     * <p>Accepts an array of up to 100 user objects. For each user, the user is created if it does not already exist, or the existing user is updated.</p> <p><strong>Note</strong>: To protect the data in your Zendesk account, bulk user imports are not enabled by default in Zendesk accounts. The account owner must contact <a href=\"https://support.zendesk.com/hc/en-us/articles/4408843597850\">Zendesk Customer Support</a> to enable the imports. A 403 Forbidden error is returned if data imports are not enabled.</p> <p>Each individual user object can identify an existing user by <code>email</code> or by <code>external_id</code>.</p> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage end users or team members</li> </ul>
     *
     * @param usersRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/users/create_or_update_many")
    Mono<@Valid JobStatusResponse> createOrUpdateManyUsers(
        @Body @NotNull @Valid UsersRequest usersRequest
    );

    /**
     * {@summary Create Or Update User}
     * <p>Creates a user if the user does not already exist, or updates an existing user identified by e-mail address or external ID.</p> <p>If you don't specify a role parameter, the new user is assigned the role of end user.</p> <p>If you need to create users without sending out a verification email, include a <code>\"skip_verify_email\": true</code> property in the body.</p> <h4>External ID Case Sensitivity</h4> <p>When providing an external id to identify an existing user to update, the search for the user record is not case sensitive.</p> <p>However, if an existing user is found, the system will update the user's external id to match the case of the external id used to find the user.</p> <h4>Response Status Code</h4> <ul> <li>If the user already exists in Zendesk, a successful request returns a 200 OK status code.</li> <li>If the user does not exist in Zendesk and is created, the request returns a 201 Created status code.</li> <li>In both cases, the API responds with a JSON body containing the full user object, which includes the user's id and the fully-resolved URL to the user resource.</li> </ul> <p>Example response: <code>json {   \"user\": {     \"id\": 8929981612030,     \"url\": \"https://{subdomain}.zendesk.com/api/v2/users/8929981612030.json\",   ...   } }</code></p> <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage end users or team members</li> </ul>
     *
     * @param userRequest (required)
     *
     * @return <p>Successful response, when user exits</p> (status code 200)
     *         or <p>Created response, when user is new</p> (status code 201)
     */
    @Post("/api/v2/users/create_or_update")
    Mono<@Valid UserResponse> createOrUpdateUser(
        @Body @NotNull @Valid UserRequest userRequest
    );

    /**
     * {@summary Create User}
     *
     * @param userRequest (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/users")
    Mono<@Valid UserResponse> createUser(
        @Body @NotNull @Valid UserRequest userRequest
    );

    /**
     * {@summary Delete User}
     * <p>Deletes the user and associated records from the account.</p> <p><strong>Warning</strong>:</p> <ul> <li>Deleted users are not recoverable.</li> <li>Both agents and administrators can soft delete users in the agent interface in Zendesk Support. Agents with permission can delete end users, while administrators can delete all users except the account owner.</li> </ul> <p>To comply with GDPR, a further step is needed. See <a href=\"/api-reference/ticketing/users/users/#permanently-delete-user\">Permanently Delete User</a>.</p> <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage end users or team members</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Delete("/api/v2/users/{user_id}")
    Mono<@Valid UserResponse> deleteUser(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Bulk Delete Users}
     * <p>Accepts a comma-separated list of up to 100 user ids.</p> <p>The request takes an <code>ids</code> or an <code>external_ids</code> query parameter.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul> <h4>Response</h4> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p>
     *
     * @param ids <p>Id of the users to delete. Comma separated</p> (optional)
     * @param externalIds <p>External Id of the users to delete. Comma separated</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Delete("/api/v2/users/destroy_many")
    Mono<@Valid JobStatusResponse> destroyManyUsers(
        @QueryValue("ids") @Nullable String ids,
        @QueryValue("external_ids") @Nullable String externalIds
    );

    /**
     * {@summary Get Full User Entitlements}
     * <p>Returns the full entitlements for all Zendesk products (Explore, Voice, Knowledge, Live Chat) for the specified user. This includes the role name and active status for each product.</p> <p>An entitlement is only considered active if both of the following conditions apply: the user has access and the product is active on the account.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>OAuth Scopes</h4> <p>Requires one of the following OAuth scopes: <code>users:read</code> or <code>read</code></p>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>User not found</p> (status code 404)
     *         or <p>Service unavailable - Too many requests</p> (status code 503)
     */
    @Get("/api/v2/users/{user_id}/entitlements/full")
    Mono<@Valid UserEntitlementsFullResponse> getUserEntitlementsFull(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary List Deleted Users}
     * <p>Returns deleted users, including permanently deleted users.</p> <p>If the results contains permanently deleted users, the users' properties that normally contain personal data, such as <code>email</code> and <code>phone</code>, are null. The <code>name</code> property is \"Permanently Deleted User\".</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/deleted_users")
    Mono<@Valid DeletedUsersResponse> listDeletedUsers();

    /**
     * {@summary List Users By Group}
     * <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents and Light Agents</li> </ul>
     *
     * @param groupId <p>The ID of the group</p> (required)
     * @param role <p>Filters the results by role. Possible values are \"end-user\", \"agent\", or \"admin\"</p> (optional)
     * @param role2 <p>Filters the results by more than one role using the format <code>role[]={role}&amp;role[]={role}</code></p> (optional)
     * @param permissionSet <p>For custom roles which is available on the Enterprise plan and above. You can only filter by one role ID per request</p> (optional)
     * @param externalId <p>List users by external id. External id has to be unique for each user under the same account.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/groups/{group_id}/users")
    Mono<@Valid UsersResponse> listGroupUsers(
        @PathVariable("group_id") @NotNull Long groupId,
        @QueryValue("role") @Nullable ListGroupUsersRoleParameter role,
        @QueryValue("role[]") @Nullable String role2,
        @QueryValue("permission_set") @Nullable Long permissionSet,
        @QueryValue("external_id") @Nullable String externalId
    );

    /**
     * {@summary List Organization Users}
     * <p>Returns a list of users for a specific organization.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents and Light Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     * @param role <p>Filters the results by role. Possible values are \"end-user\", \"agent\", or \"admin\"</p> (optional)
     * @param role2 <p>Filters the results by more than one role using the format <code>role[]={role}&amp;role[]={role}</code></p> (optional)
     * @param permissionSet <p>For custom roles which is available on the Enterprise plan and above. You can only filter by one role ID per request</p> (optional)
     * @param externalId <p>List users by external id. External id has to be unique for each user under the same account.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}/users")
    Mono<@Valid UsersResponse> listOrganizationUsers(
        @PathVariable("organization_id") @NotNull Long organizationId,
        @QueryValue("role") @Nullable ListGroupUsersRoleParameter role,
        @QueryValue("role[]") @Nullable String role2,
        @QueryValue("permission_set") @Nullable Long permissionSet,
        @QueryValue("external_id") @Nullable String externalId
    );

    /**
     * {@summary List Users}
     * <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents and Light Agents</li> </ul>
     *
     * @param role <p>Filters the results by role. Possible values are \"end-user\", \"agent\", or \"admin\"</p> (optional)
     * @param role2 <p>Filters the results by more than one role using the format <code>role[]={role}&amp;role[]={role}</code></p> (optional)
     * @param permissionSet <p>For custom roles which is available on the Enterprise plan and above. You can only filter by one role ID per request</p> (optional)
     * @param externalId <p>List users by external id. External id has to be unique for each user under the same account.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users")
    Mono<@Valid UsersResponse> listUsers(
        @QueryValue("role") @Nullable ListGroupUsersRoleParameter role,
        @QueryValue("role[]") @Nullable String role2,
        @QueryValue("permission_set") @Nullable Long permissionSet,
        @QueryValue("external_id") @Nullable String externalId
    );

    /**
     * {@summary Logout many users}
     * <p>Accepts a comma-separated list of up to 100 user ids.</p> <h4>Allowed For:</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ids <p>Accepts a comma-separated list of up to 100 user ids.</p> (optional)
     *
     * @return <p>Accepted response</p> (status code 202)
     */
    @Post("/api/v2/users/logout_many")
    Mono<@NotNull String> logoutManyUsers(
        @QueryValue("ids") @Nullable String ids
    );

    /**
     * {@summary Merge End Users}
     * <p>Merges the end user specified in the path parameter into the existing end user specified in the request body.</p> <p>Any two end users can be merged with the exception of end users created by sharing agreements.</p> <p>To be eligible for merging, the user in the path parameter must be a requester on 10,000 or fewer tickets. Otherwise, the merge will be blocked.</p> <p>Agents, admins, and users with more than 10,000 requested tickets cannot be merged.</p> <p>For more information about how user data is merged, see <a href=\"https://support.zendesk.com/hc/en-us/articles/4408887695898\">Merging a user's duplicate account</a> in Zendesk help.</p> <h4>Allowed For</h4> <ul> <li>Admins or agents with permission to edit end users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}/merge")
    Mono<@Valid UserResponse> mergeEndUsers(
        @PathVariable("user_id") @NotNull Long userId,
        @Body @NotNull @Valid UserRequest userRequest
    );

    /**
     * {@summary Permanently Delete User}
     * <p>Before permanently deleting a user, you must delete the user first. See <a href=\"/api-reference/ticketing/users/users/#delete-user\">Delete User</a>.</p> <p>WARNING: Permanently deleting a user deletes all of their information. This information is not recoverable.</p> <h4>Permanent user deletion rate limit</h4> <p>You can permanently delete 700 users every 10 minutes. The rate limiting mechanism behaves as described in <a href=\"/api-reference/introduction/rate-limits/#monitoring-your-request-activity\">Rates Limits</a> in the API introduction. Zendesk recommends that you obey the Retry-After header values.</p> <h4>Allowed For</h4> <ul> <li>Admins and <a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882#topic_cxn_hig_bd\">agents in custom roles with permission</a> to manage end users or team members</li> </ul>
     *
     * @param deletedUserId <p>The ID of the deleted user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Delete("/api/v2/deleted_users/{deleted_user_id}")
    Mono<@Valid DeletedUserResponse> permanentlyDeleteUser(
        @PathVariable("deleted_user_id") @NotNull Long deletedUserId
    );

    /**
     * {@summary Request User Create}
     * <p>Sends the owner a reminder email to update their subscription so more agents can be created.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userRequest (required)
     *
     * @return <p>description</p> (status code 200)
     */
    @Post("/api/v2/users/request_create")
    Mono<@NotNull String> requestUserCreate(
        @Body @NotNull @Valid UserRequest userRequest
    );

    /**
     * {@summary Search Users}
     * <p>Returns an array of users who meet the search criteria.</p> <p>Returns up to 100 records per page to a maximum of 10,000 records per query. See <a href=\"/api-reference/introduction/pagination/#using-offset-pagination\">Using offset pagination</a>.</p> <h4>Pagination</h4> <ul> <li>Offset pagination only</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/#using-offset-pagination\">Using Offset Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param query <p>The <code>query</code> parameter supports the Zendesk search syntax for more advanced user searches. It can specify a partial or full value of any user property, including name, email address, notes, or phone. Example: <code>query=\"jdoe\"</code>. See the <a href=\"/api-reference/ticketing/ticket-management/search/\">Search API</a>.</p> (optional)
     * @param externalId <p>The <code>external_id</code> parameter does not support the search syntax. It only accepts ids.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/search")
    Mono<@Valid UsersResponse> searchUsers(
        @QueryValue("query") @Nullable String query,
        @QueryValue("external_id") @Nullable String externalId
    );

    /**
     * {@summary Show Self}
     * <p>The endpoint returns <a href=\"/api-reference/ticketing/users/users/\">user information</a> and an <code>authenticity_token</code>.</p> <h4>Allowed For</h4> <ul> <li>Anonymous users</li> </ul> <h4>Authenticity Token</h4> <p>Zendesk API calls made by end users from a Zendesk help center must include <code>authenticity_token</code> in the <code>X-CSRF-Token</code> HTTP header. This helps prevent <a href=\"https://en.wikipedia.org/wiki/Cross-site_request_forgery\">cross-site request forgery (CSRF)</a> attacks.</p> <p>For an example using an authenticity token, see the AJAX request in the <a href=\"https://developer.zendesk.com/documentation/help_center/help-center-templates/v1#jquery\">Upgrading from Templating API v1</a> documentation.</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/me")
    Mono<@Valid CurrentUserResponse> showCurrentUser();

    /**
     * {@summary Show Deleted User}
     * <p>Returns users that have been deleted but not permanently yet. See <a href=\"#permanently-delete-user\">Permanently Delete User</a>.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul>
     *
     * @param deletedUserId <p>The ID of the deleted user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/deleted_users/{deleted_user_id}")
    Mono<@Valid DeletedUserResponse> showDeletedUser(
        @PathVariable("deleted_user_id") @NotNull Long deletedUserId
    );

    /**
     * {@summary Show Many Users}
     * <p>Accepts a comma-separated list of up to 100 user ids or external ids.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ids <p>Accepts a comma-separated list of up to 100 user ids.</p> (optional)
     * @param externalIds <p>Accepts a comma-separated list of up to 100 external ids.</p> (optional)
     * @param includeDeleted <p>If true, returns inactive or deleted users.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/show_many")
    Mono<@Valid UsersResponse> showManyUsers(
        @QueryValue("ids") @Nullable String ids,
        @QueryValue("external_ids") @Nullable String externalIds,
        @QueryValue("include_deleted") @Nullable Boolean includeDeleted
    );

    /**
     * {@summary Show User}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}")
    Mono<@Valid UserResponse> showUser(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Show Compliance Deletion Statuses}
     * <p>Returns the GDPR status for each user per area of compliance. A Zendesk area of compliance is typically a product like \"support/explore\" but can be more fine-grained for areas within the product lines.</p> <p>If the user is not in the account, the request returns a 404 status.</p> <p><code>http Status: 404 {   \"error\":\"RecordNotFound\",   \"description\":\"Not found\" }</code></p> <h4>Allowed For</h4> <ul> <li>Agents, with restrictions</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param application <p>Area of compliance</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/compliance_deletion_statuses")
    Mono<@Valid ComplianceDeletionStatusesResponse> showUserComplianceDeletionStatuses(
        @PathVariable("user_id") @NotNull Long userId,
        @QueryValue("application") @Nullable String application
    );

    /**
     * {@summary Show User Related Information}
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/related")
    Mono<@Valid UserRelatedResponse> showUserRelated(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Update Current User Settings}
     * <p>Updates the settings for the currently authenticated user. This includes UI preferences for onboarding, tooltips, keyboard shortcuts, theme preferences, and other feature toggles.</p> <p>Settings are grouped into: - <strong>Support</strong>: Support UI preferences (onboarding, tooltips, shortcuts, theme) - <strong>admin_center</strong>: Admin Center UI preferences (navigation, onboarding) - <strong>shared_views_order</strong>: Optional array of view IDs for custom ordering</p> <p>Only the specified settings will be updated. Other settings will remain unchanged.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userSettingsUpdateRequest (required)
     *
     * @return <p>Success response - Returns updated settings</p> (status code 200)
     *         or <p>Bad request - No settings provided</p> (status code 400)
     *         or <p>Unauthorized - Not an agent</p> (status code 401)
     */
    @Put("/api/v2/users/me/settings")
    Mono<@Valid UserSettingsResponse> updateCurrentUserSettings(
        @Body @NotNull @Valid UserSettingsUpdateRequest userSettingsUpdateRequest
    );

    /**
     * {@summary Update Many Users}
     *
     * @param updateManyUsersRequest (required)
     * @param ids <p>Id of the users to update. Comma separated</p> (optional)
     * @param externalIds <p>External Id of the users to update. Comma separated</p> (optional)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Put("/api/v2/users/update_many")
    Mono<@Valid JobStatusResponse> updateManyUsers(
        @Body @NotNull @Valid UpdateManyUsersRequest updateManyUsersRequest,
        @QueryValue("ids") @Nullable String ids,
        @QueryValue("external_ids") @Nullable String externalIds
    );

    /**
     * {@summary Update User}
     *
     * @param userId <p>The id of the user</p> (required)
     * @param userRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}")
    Mono<@Valid UserResponse> updateUser(
        @PathVariable("user_id") @NotNull Long userId,
        @Body @NotNull @Valid UserRequest userRequest
    );
}