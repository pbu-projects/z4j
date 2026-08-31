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
import lol.pbu.z4j.model.JobStatusResponse;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.OrganizationMembershipResponse;
import lol.pbu.z4j.model.OrganizationMembershipsResponse;
import static io.micronaut.core.convert.converters.MultiValuesConverterFactory.*;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface OrganizationMembershipsClient {

    /**
     * {@summary Create Many Memberships}
     * <p>Accepts an array of up to 100 organization membership objects.</p> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/organization_memberships/create_many")
    Mono<@Valid JobStatusResponse> createManyOrganizationMemberships();

    /**
     * {@summary Create Membership}
     * <p>Assigns a user to a given organization. Returns an error with status 422 if the user is already assigned to the organization.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents when creating a new organization membership for an end user</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/organization_memberships")
    Mono<@Valid OrganizationMembershipResponse> createOrganizationMembership();

    /**
     * {@summary Create Organization Membership for User}
     * <p>Assigns a user to a given organization. Returns an error with status 422 if the user is already assigned to the organization.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents when creating a new organization membership for an end user</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/users/{user_id}/organization_memberships")
    Mono<@Valid OrganizationMembershipResponse> createUserOrganizationMembership(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Bulk Delete Memberships}
     * <p>Immediately removes a user from an organization and schedules a job to unassign all working tickets currently assigned to the user and organization combination. The <code>organization_id</code> of the unassigned tickets is set to null.</p> <h4>Response</h4> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ids <p>The IDs of the organization memberships to delete</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Delete("/api/v2/organization_memberships/destroy_many")
    Mono<@Valid JobStatusResponse> deleteManyOrganizationMemberships(
        @QueryValue("ids") @Nullable @Format(FORMAT_MULTI) List<@NotNull Long> ids
    );

    /**
     * {@summary Delete Membership}
     * <p>Immediately removes a user from an organization and schedules a job to unassign all working tickets currently assigned to the user and organization combination. The <code>organization_id</code> of the unassigned tickets is set to null.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> <li>Agents when deleting an organization membership for an end user</li> </ul>
     *
     * @param organizationMembershipId <p>The ID of the organization membership</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/organization_memberships/{organization_membership_id}")
    Mono<Void> deleteOrganizationMembership(
        @PathVariable("organization_membership_id") @NotNull Long organizationMembershipId
    );

    /**
     * {@summary Delete Organization Membership for User}
     * <p>Immediately removes a user from an organization and schedules a job to unassign all working tickets currently assigned to the user and organization combination. The <code>organization_id</code> of the unassigned tickets is set to null.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> <li>Agents when deleting an organization membership for an end user</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param organizationMembershipId <p>The ID of the organization membership</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/users/{user_id}/organization_memberships/{organization_membership_id}")
    Mono<Void> deleteUserOrganizationMembership(
        @PathVariable("user_id") @NotNull Long userId,
        @PathVariable("organization_membership_id") @NotNull Long organizationMembershipId
    );

    /**
     * {@summary List Memberships}
     * <p>Returns a list of organization memberships for the account, user or organization in question.</p> <p><strong>Note</strong>: When returning organization memberships for a user, organization memberships are sorted with the default organization first, and then by organization name.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> <li>End users</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organization_memberships")
    Mono<@Valid OrganizationMembershipsResponse> listOrganizationMemberships();

    /**
     * {@summary List Organization Memberships by Organization}
     * <p>Returns a list of organization memberships for the account, user or organization in question.</p> <p><strong>Note</strong>: When returning organization memberships for a user, organization memberships are sorted with the default organization first, and then by organization name.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> <li>End users</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}/organization_memberships")
    Mono<@Valid OrganizationMembershipsResponse> listOrganizationMembershipsByOrganization(
        @PathVariable("organization_id") @NotNull Long organizationId
    );

    /**
     * {@summary List Organization Memberships by User}
     * <p>Returns a list of organization memberships for the account, user or organization in question.</p> <p><strong>Note</strong>: When returning organization memberships for a user, organization memberships are sorted with the default organization first, and then by organization name.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> <li>End users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/organization_memberships")
    Mono<@Valid OrganizationMembershipsResponse> listUserOrganizationMemberships(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Set Organization as Default}
     * <p>Sets the default organization membership of a given user.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}/organizations/{organization_id}/make_default")
    Mono<@Valid OrganizationMembershipResponse> setOrganizationAsDefault(
        @PathVariable("user_id") @NotNull Long userId,
        @PathVariable("organization_id") @NotNull Long organizationId
    );

    /**
     * {@summary Set Membership as Default}
     * <p>Sets the default organization membership of a given user.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> <li>Agents when setting the default organization membership for an end user</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param organizationMembershipId <p>The ID of the organization membership</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}/organization_memberships/{organization_membership_id}/make_default")
    Mono<@Valid OrganizationMembershipsResponse> setOrganizationMembershipAsDefault(
        @PathVariable("user_id") @NotNull Long userId,
        @PathVariable("organization_membership_id") @NotNull Long organizationMembershipId
    );

    /**
     * {@summary Show Membership}
     * <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param organizationMembershipId <p>The ID of the organization membership</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organization_memberships/{organization_membership_id}")
    Mono<@Valid OrganizationMembershipResponse> showOrganizationMembershipById(
        @PathVariable("organization_membership_id") @NotNull Long organizationMembershipId
    );

    /**
     * {@summary Show Organization Membership by User}
     * <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param organizationMembershipId <p>The ID of the organization membership</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/organization_memberships/{organization_membership_id}")
    Mono<@Valid OrganizationMembershipResponse> showOrganizationMembershipByUserId(
        @PathVariable("user_id") @NotNull Long userId,
        @PathVariable("organization_membership_id") @NotNull Long organizationMembershipId
    );

    /**
     * {@summary Unassign Organization}
     * <p>Immediately removes a user from an organization and schedules a job to unassign all working tickets currently assigned to the user and organization combination. The <code>organization_id</code> of the unassigned tickets is set to null.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/users/{user_id}/organizations/{organization_id}")
    Mono<Void> unassignOrganization(
        @PathVariable("organization_id") @NotNull Long organizationId,
        @PathVariable("user_id") @NotNull Long userId
    );
}