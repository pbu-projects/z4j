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
import lol.pbu.z4j.model.GroupMembershipResponse;
import lol.pbu.z4j.model.GroupMembershipsResponse;
import lol.pbu.z4j.model.JobStatusResponse;
import reactor.core.publisher.Mono;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface GroupMembershipsClient {

    /**
     * {@summary Create Membership}
     * <p>Assigns an agent to a given group.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage group memberships (Enterprise only)</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/group_memberships")
    Mono<@Valid GroupMembershipResponse> createGroupMembership(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Create Group Membership for User}
     * <p>Assigns an agent to a given group.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage group memberships (Enterprise only)</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/users/{user_id}/group_memberships")
    Mono<@Valid GroupMembershipResponse> createUserGroupMembership(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Delete Membership}
     * <p>Immediately removes a user from a group and schedules a job to unassign all working tickets that are assigned to the given user and group combination.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage group memberships (Enterprise only)</li> </ul>
     *
     * @param groupMembershipId <p>The ID of the group membership</p> (required)
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/group_memberships/{group_membership_id}")
    Mono<Void> deleteGroupMembership(
        @PathVariable("group_membership_id") @NotNull Integer groupMembershipId,
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Delete User's Group Membership}
     * <p>Immediately removes a user from a group and schedules a job to unassign all working tickets that are assigned to the given user and group combination.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage group memberships (Enterprise only)</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param groupMembershipId <p>The ID of the group membership</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/users/{user_id}/group_memberships/{group_membership_id}")
    Mono<Void> deleteUserGroupMembership(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("group_membership_id") @NotNull Integer groupMembershipId
    );

    /**
     * {@summary Bulk Create Memberships}
     * <p>Assigns up to 100 agents to given groups.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage group memberships (Enterprise only)</li> </ul> <h4>Response</h4> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion.</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/group_memberships/create_many")
    Mono<@Valid JobStatusResponse> groupMembershipBulkCreate();

    /**
     * {@summary Bulk Delete Memberships}
     * <p>Immediately removes users from groups and schedules a job to unassign all working tickets that are assigned to the given user and group combinations.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage group memberships (Enterprise only)</li> </ul>
     *
     * @param ids <p>Id of the group memberships to delete. Comma separated</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Delete("/api/v2/group_memberships/destroy_many")
    Mono<@Valid JobStatusResponse> groupMembershipBulkDelete(
        @QueryValue("ids") @Nullable String ids
    );

    /**
     * {@summary Set Membership as Default}
     * <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param groupMembershipId <p>The ID of the group membership</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}/group_memberships/{group_membership_id}/make_default")
    Mono<@Valid GroupMembershipsResponse> groupMembershipSetDefault(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("group_membership_id") @NotNull Integer groupMembershipId
    );

    /**
     * {@summary List Assignable Memberships}
     * <p>Returns a maximum of 100 group memberships per page.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/group_memberships/assignable")
    Mono<@Valid GroupMembershipsResponse> listAssignableGroupMemberships();

    /**
     * {@summary List Assignable Memberships By Group}
     * <p>Returns a list of assignable group memberships for a specific group.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul>
     *
     * @param groupId <p>The ID of the group</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/groups/{group_id}/memberships/assignable")
    Mono<@Valid GroupMembershipsResponse> listAssignableGroupMembershipsByGroup(
        @PathVariable("group_id") @NotNull Integer groupId
    );

    /**
     * {@summary List Memberships}
     * <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param groupId <p>The ID of the group</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/group_memberships")
    Mono<@Valid GroupMembershipsResponse> listGroupMemberships(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("group_id") @NotNull Integer groupId
    );

    /**
     * {@summary List Memberships By Group}
     * <p>Returns a list of all group memberships for a specific group.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul>
     *
     * @param groupId <p>The ID of the group</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/groups/{group_id}/memberships")
    Mono<@Valid GroupMembershipsResponse> listGroupMembershipsByGroup(
        @PathVariable("group_id") @NotNull Integer groupId
    );

    /**
     * {@summary List Group Memberships by User}
     * <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/group_memberships")
    Mono<@Valid GroupMembershipsResponse> listUserGroupMemberships(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Show Membership}
     * <p>The 'id' is the group membership id, not a group id.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param groupMembershipId <p>The ID of the group membership</p> (required)
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/group_memberships/{group_membership_id}")
    Mono<@Valid GroupMembershipResponse> showGroupMembershipById(
        @PathVariable("group_membership_id") @NotNull Integer groupMembershipId,
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Show User's Group Membership}
     * <p>Returns a specific group membership for a user.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param groupMembershipId <p>The ID of the group membership</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/group_memberships/{group_membership_id}")
    Mono<@Valid GroupMembershipResponse> showUserGroupMembershipById(
        @PathVariable("user_id") @NotNull Integer userId,
        @PathVariable("group_membership_id") @NotNull Integer groupMembershipId
    );
}