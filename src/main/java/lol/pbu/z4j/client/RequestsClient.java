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
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.RequestResponse;
import lol.pbu.z4j.model.RequestsResponse;
import lol.pbu.z4j.model.TicketCommentResponse;
import lol.pbu.z4j.model.TicketCommentsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface RequestsClient {

    /**
     * {@summary Create Request}
     * <p>Accepts a <code>request</code> object that sets one or more properties.</p> <h4>Allowed for</h4> <ul> <li>End users</li> <li>Anonymous users (rate limit of 5 requests per hour for <a href=\"/documentation/developer-tools/getting-started/getting-a-trial-or-sponsored-account-for-development/\">trial accounts</a>)</li> </ul> <h4>Additional properties</h4> <p>In addition to the writable request properties in the <a href=\"#json-format\">JSON Format table</a> above, you can set the following properties when creating a request.</p> <p>| Name                | Type   | Mandatory | Comment | ----------------    | -------| --------- | ------- | comment             | object | yes       | Describes the problem, incident, question, or task. See <a href=\"#request-comments\">Request comments</a> | collaborators       | array  | no        | Adds collaborators (cc's) to the request. An email notification is sent to them when the ticket is created. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-managing-requests#setting-collaborators\">Setting collaborators</a> | requester           | object | yes*      | *Required for anonymous requests. Specifies the requester of the anonymous request. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-managing-requests#creating-anonymous-requests\">Creating anonymous requests</a></p> <h4>Creating follow-up requests</h4> <p>Once a ticket is closed (as distinct from solved), it can't be reopened. However, you can create a new request that references the closed ticket. To create the follow-up request, include a <code>via_followup_source_id</code> property in the <code>request</code> object that specifies the closed ticket. The parameter only works with closed tickets. It has no effect with other tickets.</p>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/requests")
    Mono<@Valid RequestResponse> createRequest();

    /**
     * {@summary List CCD Requests}
     * <p>Lists requests where the authenticated end user is CC'd.</p> <h4>Allowed for</h4> <ul> <li>End Users</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param sortBy <p>Possible values are \"updated_at\", \"created_at\"</p> (optional)
     * @param sortOrder <p>One of \"asc\", \"desc\". Defaults to \"asc\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/requests/ccd")
    Mono<@Valid RequestsResponse> listCCDRequests(
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary Listing Comments}
     * <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <h4>Sorting</h4> <p>By default, comments are sorted by creation date in ascending order.</p> <p>When using cursor pagination, use the following parameter to change the sort order:</p> <p>| Name   | Type   | Required | Comments | ------ | ------ | -------- | -------- | <code>sort</code> | string | no       | Possible values are \"created_at\" (ascending order) or \"-created_at\" (descending order)</p> <p>When using offset pagination, use the following parameters to change the sort order:</p> <p>| Name         | Type   | Required | Comments | ------------ | ------ | -------- | -------- | <code>sort_by</code>    | string | no       | One of <code>created_at</code>, <code>updated_at</code> | <code>sort_order</code> | string | no       | One of <code>asc</code>, <code>desc</code></p> <h4>Allowed For</h4> <ul> <li>End Users</li> </ul>
     *
     * @param requestId <p>The ID of the request</p> (required)
     * @param since <p>Filters the comments from the given datetime</p> (optional)
     * @param role <p>One of \"agent\", \"end_user\". If not specified it does not filter</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/requests/{request_id}/comments")
    Mono<@Valid TicketCommentsResponse> listComments(
        @PathVariable("request_id") @NotNull Integer requestId,
        @QueryValue("since") @Nullable String since,
        @QueryValue("role") @Nullable String role
    );

    /**
     * {@summary List Open Requests}
     * <p>Lists requests with the \"open\" status for the authenticated end user.</p> <h4>Allowed for</h4> <ul> <li>End Users</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param sortBy <p>Possible values are \"updated_at\", \"created_at\"</p> (optional)
     * @param sortOrder <p>One of \"asc\", \"desc\". Defaults to \"asc\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/requests/open")
    Mono<@Valid RequestsResponse> listOpenRequests(
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List Organization Requests}
     * <p>Returns a list of requests for a specific organization.</p> <h4>Allowed for</h4> <ul> <li>End Users</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     * @param sortBy <p>Possible values are \"updated_at\", \"created_at\"</p> (optional)
     * @param sortOrder <p>One of \"asc\", \"desc\". Defaults to \"asc\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}/requests")
    Mono<@Valid RequestsResponse> listOrganizationRequests(
        @PathVariable("organization_id") @NotNull Integer organizationId,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List Requests}
     * <h4>Allowed for</h4> <ul> <li>End Users</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param sortBy <p>Possible values are \"updated_at\", \"created_at\"</p> (optional)
     * @param sortOrder <p>One of \"asc\", \"desc\". Defaults to \"asc\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/requests")
    Mono<@Valid RequestsResponse> listRequests(
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List Solved Requests}
     * <p>Lists requests with the \"solved\" status for the authenticated end user.</p> <h4>Allowed for</h4> <ul> <li>End Users</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param sortBy <p>Possible values are \"updated_at\", \"created_at\"</p> (optional)
     * @param sortOrder <p>One of \"asc\", \"desc\". Defaults to \"asc\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/requests/solved")
    Mono<@Valid RequestsResponse> listSolvedRequests(
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List User Requests}
     * <p>Lists requests for the specified user.</p> <h4>Allowed for</h4> <ul> <li>End Users</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param sortBy <p>Possible values are \"updated_at\", \"created_at\"</p> (optional)
     * @param sortOrder <p>One of \"asc\", \"desc\". Defaults to \"asc\"</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/requests")
    Mono<@Valid RequestsResponse> listUserRequests(
        @PathVariable("user_id") @NotNull Integer userId,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary Search Requests}
     * <p>Examples:</p> <ul> <li><code>GET /api/v2/requests/search.json?query=printer</code></li> <li><code>GET /api/v2/requests/search.json?query=printer&amp;organization_id=1</code></li> <li><code>GET /api/v2/requests/search.json?query=printer&amp;cc_id=true</code></li> <li><code>GET /api/v2/requests/search.json?query=printer&amp;status=hold,open</code></li> </ul> <h4>Pagination</h4> <ul> <li>Offset pagination only</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/#using-offset-pagination\">Using Offset Pagination</a>.</p> <h4>Results limit</h4> <p>The Search Requests endpoint returns up to 1,000 results per query, with a maximum of 100 results per page. See <a href=\"/api-reference/ticketing/introduction/#pagination\">Pagination</a>. If you request a page past the limit (<code>page=11</code> at 100 results per page), a 422 Insufficient Resource Error is returned.</p> <h4>Allowed For</h4> <ul> <li>End Users</li> </ul>
     *
     * @param query <p>The syntax and matching logic for the string is detailed in the <a href=\"https://support.zendesk.com/hc/en-us/articles/203663226\">Zendesk Support search reference</a>. See also <a href=\"/api-reference/ticketing/ticket-management/search/#query-basics\">Query basics</a> in the Tickets API doc.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/requests/search")
    Mono<@Valid RequestsResponse> searchRequests(
        @QueryValue("query") @Nullable String query
    );

    /**
     * {@summary Getting Comments}
     * <h4>Allowed For</h4> <ul> <li>End Users</li> </ul>
     *
     * @param requestId <p>The ID of the request</p> (required)
     * @param ticketCommentId <p>The ID of the ticket comment</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/requests/{request_id}/comments/{ticket_comment_id}")
    Mono<@Valid TicketCommentResponse> showComment(
        @PathVariable("request_id") @NotNull Integer requestId,
        @PathVariable("ticket_comment_id") @NotNull Integer ticketCommentId
    );

    /**
     * {@summary Show Request}
     * <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name             | Will sideload | ---------------- | ------------- | users            | The email ccs for a request by side-loading users</p> <h4>Allowed For</h4> <ul> <li>End Users</li> </ul>
     *
     * @param requestId <p>The ID of the request</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/requests/{request_id}")
    Mono<@Valid RequestResponse> showRequest(
        @PathVariable("request_id") @NotNull Integer requestId
    );

    /**
     * {@summary Update Request}
     * <p>Updates a request with a comment or collaborators (cc's). The end user who created the request can also use it to mark the request as solved. The endpoint can't be used to update other request attributes.</p> <h4>Writable properties</h4> <p>This endpoint can only update the following properties in the request.</p> <p>| Name                     | Type    | Required | Description                                          | | ------------------------ | ------- | -------- | ---------------------------------------------------- | | comment                  | object  | no       | Adds a comment to the request. See <a href=\"#request-comments\">Request comments</a> | | solved                   | boolean | no       | Marks the request as solved. Example: <code>{\"request\": {\"solved\": \"true\"}}</code>. End users can mark requests as solved only if the request's <code>can_be_solved_by_me</code> property is true. The property is true only when the ticket is assigned to an agent and the ticket type is not a problem but a question, task, or incident | | additional_collaborators | array   | no       | Adds collaborators to the request. An email notification is sent to them when the ticket is updated. See <a href=\"/documentation/ticketing/managing-tickets/creating-and-managing-requests#adding-collaborators\">Adding collaborators</a> |</p> <h4>Allowed For</h4> <ul> <li>End users</li> </ul>
     *
     * @param requestId <p>The ID of the request</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/requests/{request_id}")
    Mono<@Valid RequestResponse> updateRequest(
        @PathVariable("request_id") @NotNull Integer requestId
    );
}