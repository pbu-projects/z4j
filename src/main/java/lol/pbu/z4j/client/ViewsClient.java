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
import lol.pbu.z4j.model.Errors;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.TicketsResponse;
import lol.pbu.z4j.model.ViewCountResponse;
import lol.pbu.z4j.model.ViewCountsResponse;
import lol.pbu.z4j.model.ViewExportResponse;
import lol.pbu.z4j.model.ViewResponse;
import lol.pbu.z4j.model.ViewsCountResponse;
import lol.pbu.z4j.model.ViewsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface ViewsClient {

    /**
     * {@summary Bulk Delete Views}
     * <p>Deletes the views corresponding to the provided list of IDs.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ids <p>The IDs of the views to delete</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/views/destroy_many")
    Mono<Void> bulkDeleteViews(
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary Count Views}
     * <p>Returns an approximate count of shared and personal views available to the current user. If the count exceeds 100,000, the count will return a cached result.  This cached result will update every 24 hours.</p> <p>The <code>count[refreshed_at]</code> property is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>count[refreshed_at]</code> may occasionally be null. This indicates that the count is being updated in the background, and <code>count[value]</code> is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Count of views</p> (status code 200)
     */
    @Get("/api/v2/views/count")
    Mono<@Valid ViewsCountResponse> countViews();

    /**
     * {@summary Create View}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>JSON Format</h4> <p>The JSON format consists of one property, a <code>view</code> object that lists the values to set when the view is created.</p> <p><strong>Note</strong>: The request must include at least one condition in the <code>all</code> array that checks one of the following fields: <code>status</code>, <code>type</code>, <code>group_id</code>, <code>assignee_id</code>, or <code>requester_id</code>.</p> <p>| Name        | Description | ----------- | ----------- | title       | Required. The title of the view | all         | Required. An array of one or more conditions. A ticket must meet all of them to be included in the view. See <a href=\"/documentation/ticketing/reference-guides/conditions-reference\">Conditions reference</a> | any         | An array of one or more conditions. A ticket must meet any of them to be included in the view. See <a href=\"/documentation/ticketing/reference-guides/conditions-reference\">Conditions reference</a> | description | The description of the view | active      | Allowed values are true or false. Determines if the view is displayed or not | output      | An object that specifies the columns to display. Example: <code>\"output\": {\"columns\": [\"status\", \"description\", \"priority\"]}</code>. See <a href=\"#view-columns\">View columns</a> | restriction | An object that describes who can access the view. To give all agents access to the view, omit this property</p> <p>The <code>restriction</code> object has the following properties.</p> <p>| Name | Comment | ---- | ------- | type | Allowed values are \"Group\" or \"User\" | id   | The numeric ID of a single group or user | ids  | The numeric IDs of a single or more groups. Recommended for \"Group\" <code>type</code></p> <p>If <code>type</code> is \"Group\", the <code>ids</code> property is the preferred method of specifying the group id or ids.</p> <h4>Example Request Body</h4> <p><code>js {   \"view\": {     \"title\": \"Kelly's tickets\",     \"raw_title\": \"{{dc.tickets_assigned_to_kelly}}\",     \"description\": \"Tickets that are assigned to Kelly\",     \"active\": true,     \"restriction\": {       \"type\": \"User\",       \"id\": \"213977756\"     },     \"all\": [       {         \"field\": \"status\",         \"operator\": \"less_than\",         \"value\": \"solved\"       },       {         \"field\": \"group_id\",         \"operator\": \"is\",         \"value\": \"24000932\"       },       {         \"field\": \"custom_fields_360011872073\",         \"operator\": \"is\",         \"value\": \"Canada\"       },       ...     ],     \"output\": {       \"columns\": [\"status\", \"requester\", \"assignee\"],       \"group_by\": \"assignee\",       \"group_order\": \"desc\",       \"sort_by\": \"status\",       \"sort_order\": \"desc\"     }   } }</code></p> <h4>View columns</h4> <p>The <code>output</code> request parameter lets you specify what columns to include in the view in the agent interface. Example: <code>\"output\": {\"columns\": [\"status\", \"description\", \"priority\"]}</code>. The following table lists possible columns for views in the agent UI and the corresponding values in the <code>columns</code> array.</p> <p>For custom fields, specify the id of the custom field in the <code>columns</code> array.</p> <p>You can specify a total of 10 columns to a view.</p> <p>| View column title in UI     | Value                | |---------------------------- | -------------------- | | Assigned                    | <code>assigned</code>           | | Assignee                    | <code>assignee</code>           | | Due Date                    | <code>due_date</code>           | | Group                       | <code>group</code>              | | ID                          | <code>nice_id</code>            | | Updated                     | <code>updated</code>            | | Assignee updated            | <code>updated_assignee</code>   | | Requester updated           | <code>updated_requester</code>  | | Updater                     | <code>updated_by_type</code>    | | Organization                | <code>organization</code>       | | Priority                    | <code>priority</code>           | | Requested                   | <code>created</code>            | | Requester                   | <code>requester</code>          | | Requester language          | <code>locale_id</code>          | | Satisfaction                | <code>satisfaction_score</code> | | Solved                      | <code>solved</code>             | | Status category             | <code>status</code>             | | Subject                     | <code>description</code>        | | Submitter                   | <code>submitter</code>          | | Ticket form                 | <code>ticket_form</code>        | | Type                        | <code>type</code>               | | Brand                       | <code>brand</code>              | | Ticket status               | <code>custom_status_id</code>   |</p> <h4>View sorting</h4> <p>You can group and sort items in the view by adding items to the <code>output</code> parameter:</p> <p>| Attribute                   | Description |-----------------------------| ----------- | <code>group_by</code>, <code>sort_by</code>       | Sort or group the tickets by a column in the <a href=\"#view-columns\">View columns</a> table. The <code>description</code>, <code>submitter</code> and <code>custom_status_id</code> columns are not supported | <code>group_order</code>, <code>sort_order</code> | Either \"asc\" or \"desc\"</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/views")
    Mono<@Valid ViewResponse> createView();

    /**
     * {@summary Delete View}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param viewId <p>The ID of the view</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/views/{view_id}")
    Mono<Void> deleteView(
        @PathVariable("view_id") @NotNull Integer viewId
    );

    /**
     * {@summary Execute View}
     * <p>Returns the column titles and the rows of the specified view.</p> <p>The <code>columns</code> array lists the view's column titles and includes only views parameters.</p> <p>The <code>rows</code> array lists the values of each column for each ticket and includes parameters from both views and tickets. Though not displayed in the view, a partial ticket object is included with each row object.</p> <p><strong>Note</strong>: To get the full ticket objects for a specified view, use <a href=\"#list-tickets-from-a-view\">List Tickets from a View</a>.</p> <p>This endpoint is rate limited to 5 requests per minute, per view, per agent. This rate limit includes activity in Zendesk Support. An API script is more likely to encounter rate limit errors if the authenticating agent or admin is concurrently active in Zendesk Support.</p> <p>The view execution system is designed for periodic rather than high-frequency API usage. In particular, views called very frequently may be cached by Zendesk. This means that the API client will still receive a result, but that result may have been computed at any time within the last 10 minutes.</p> <p>Zendesk recommends using the Incremental Ticket Export endpoint to get the latest changes. You can call it more often, and it returns all the tickets that changed since the last poll. For details and rate limits, see <a href=\"/api-reference/ticketing/ticket-management/incremental_exports/\">Incremental Exports</a>.</p> <p>View output sorting can be controlled by passing the <code>sort_by</code> and <code>sort_order</code> parameters in the format described in the table in <a href=\"#preview-views\">Preview Views</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param viewId <p>The ID of the view</p> (required)
     * @param sortBy <p>The ticket field used for sorting. This will either be a title or a custom field id.</p> (optional)
     * @param sortOrder <p>The direction the tickets are sorted. May be one of 'asc' or 'desc'</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/views/{view_id}/execute")
    Mono<@Valid ViewResponse> executeView(
        @PathVariable("view_id") @NotNull Integer viewId,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary Export View}
     * <p>Returns the csv attachment of the specified view if possible. Enqueues a job to produce the csv if necessary.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param viewId <p>The ID of the view</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/views/{view_id}/export")
    Mono<@Valid ViewExportResponse> exportView(
        @PathVariable("view_id") @NotNull Integer viewId
    );

    /**
     * {@summary Count Tickets in View}
     * <p>Returns the ticket count for a single view.</p> <p>This endpoint is rate limited to 5 requests per minute, per view, per agent.</p> <h4>View Counts</h4> <p>The view count endpoints, Count Tickets in View (this endpoint) and <a href=\"#count-tickets-in-views\">Count Tickets in Views</a>, let you estimate how many tickets remain in a view without having to retrieve the entire view. They're designed to help estimate view size. From a business perspective, accuracy becomes less relevant as view size increases.</p> <p>To ensure quality of service, these counts are cached more heavily as the number of tickets in a view grows. For a view with thousands of tickets, you can expect the count to be cached for 60-90 minutes. As a result, the count may not reflect the actual number of tickets in your view.</p> <p>View counts are represented as JSON objects with the following attributes:</p> <p>| Name            | Type        | Comment | --------------- | ------------| ------- | view_id         | integer     | The id of the view | url             | string      | The API url of the count | value           | integer     | The cached number of tickets in the view. Can also be null if the system is loading and caching new data. Not to be confused with 0 tickets | pretty          | string      | A pretty-printed text approximation of the view count | fresh           | boolean     | false if the cached data is stale and the system is still loading and caching new data | active          | boolean     | Only active views if true, inactive views if false, all views if null.</p> <h4>Example</h4> <p><code>js {   \"view_count\": {     \"view_id\": 25,     \"url\":     \"https://company.zendesk.com/api/v2/views/25/count.json\",     \"value\":   719,     \"pretty\":  \"~700\",     \"fresh\":   true   } }</code></p>
     *
     * @param viewId <p>The ID of the view</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/views/{view_id}/count")
    Mono<@Valid ViewCountResponse> getViewCount(
        @PathVariable("view_id") @NotNull Integer viewId
    );

    /**
     * {@summary Count Tickets in Views}
     * <p>Returns the ticket count of each view in a list of views. Accepts up to 20 view ids per request. For the ticket count of a single view, see <a href=\"#count-tickets-in-view\">Count Tickets in View</a>.</p> <p>Only returns values for personal and shared views accessible to the user performing the request.</p> <p><strong><em>Note:</em></strong> Due to the asynchronous operation of computing the counts for the requested views, some of the views' counts could be null. This means that the system is still computing the count for that view. Periodically issue another request until all of the views' counts in the response are integers greater than zero. </p> <h4>Rate limiting</h4> <p>This endpoint is rate limited to 6 requests every 1 minute.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ids <p>List of view's ids separated by commas.</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Too Many Requests</p> (status code 429)
     */
    @Get("/api/v2/views/count_many")
    Mono<@Valid ViewCountsResponse> getViewCounts(
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary List Active Views}
     * <p>Lists active shared and personal views available to the current user.</p> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name             | Will sideload | ---------------- | ------------- | app_installation | The app installation that requires each view, if present | permissions      | The permissions for each view</p> <h4>Pagination</h4> <ul> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param access <p>Only views with given access. May be \"personal\", \"shared\", or \"account\"</p> (optional)
     * @param groupId <p>Only views belonging to given group</p> (optional)
     * @param sortBy <p>Possible values are \"alphabetical\", \"created_at\", or \"updated_at\". Defaults to \"position\"</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/views/active")
    Mono<@Valid ViewsResponse> listActiveViews(
        @QueryValue("access") @Nullable String access,
        @QueryValue("group_id") @Nullable Integer groupId,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List Views - Compact}
     * <p>A compacted list of shared and personal views available to the current user. This endpoint never returns more than 32 records and does not respect the \"per_page\" option.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/views/compact")
    Mono<@Valid ViewsResponse> listCompactViews();

    /**
     * {@summary List Tickets From a View}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param viewId <p>The ID of the view</p> (required)
     * @param sortBy <p>Sort or group the tickets by a column in the <a href=\"#view-columns\">View columns</a> table. The <code>subject</code> and <code>submitter</code> columns are not supported</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/views/{view_id}/tickets")
    Mono<@Valid TicketsResponse> listTicketsFromView(
        @PathVariable("view_id") @NotNull Integer viewId,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List Views}
     * <p>Lists shared and personal views available to the current user.</p> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name             | Will sideload | ---------------- | ------------- | app_installation | The app installation that requires each view, if present | permissions      | The permissions for each view</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param access <p>Only views with given access. May be \"personal\", \"shared\", or \"account\"</p> (optional)
     * @param active <p>Only active views if true, inactive views if false</p> (optional)
     * @param groupId <p>Only views belonging to given group</p> (optional)
     * @param sort <p>The sort parameter used with cursor pagination. Defaults to \"created_at\". Prefix with '-' for descending order</p> (optional)
     * @param sortBy <p>The sort_by parameter used with offset pagination. Possible values are \"alphabetical\", \"created_at\", or \"updated_at\". Defaults to \"position\"</p> (optional)
     * @param sortOrder <p>The sort_order parameter used with offset pagination. One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/views")
    Mono<@Valid ViewsResponse> listViews(
        @QueryValue("access") @Nullable String access,
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("group_id") @Nullable Integer groupId,
        @QueryValue("sort") @Nullable String sort,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List Views By ID}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name             | Will sideload | ---------------- | ------------- | app_installation | The app installation that requires each view, if present | permissions      | The permissions for each view</p>
     *
     * @param ids <p>List of view's ids separated by commas.</p> (required)
     * @param active <p>Only active views if true, inactive views if false</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/views/show_many")
    Mono<@Valid ViewsResponse> listViewsById(
        @QueryValue("ids") @NotNull String ids,
        @QueryValue("active") @Nullable Boolean active
    );

    /**
     * {@summary Preview Ticket Count}
     * <p>Returns the ticket count for a single preview.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/views/preview/count")
    Mono<@Valid ViewCountResponse> previewCount();

    /**
     * {@summary Preview Views}
     * <p>You can preview views by constructing the conditions in the proper format and nesting them under the <code>view</code> property. See <a href=\"/documentation/ticketing/reference-guides/conditions-reference/\">Conditions reference</a>. The output can also be controlled by passing in any of the following parameters and nesting them under the <code>output</code> property.</p> <p>| Name            | Type    | Comment | --------------- | ------- | ------- | columns         | Array   | The ticket fields to display. System fields are looked up by name, custom fields by title or id. See the <a href=\"#view-columns\">View columns</a> table | group_by        | String  | When present, the field by which the tickets are grouped | group_order     | String  | The direction the tickets are grouped. May be one of \"asc\" or \"desc\" | sort_order      | String  | The direction the tickets are sorted. May be one of \"asc\" or \"desc\" | sort_by         | String  | The ticket field used for sorting. This will either be a title or a custom field id.</p> <p>This endpoint is rate limited to 5 requests per minute, per view, per agent.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/views/preview")
    Mono<@Valid ViewResponse> previewViews();

    /**
     * {@summary Search Views}
     * <h4>Pagination</h4> <ul> <li>Offset pagination only</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/#using-offset-pagination\">Using Offset Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Sideloads</h4> <p>The following sideloads are supported. For more information, see <a href=\"/documentation/ticketing/using-the-zendesk-api/side_loading/\">Side-loading</a>.</p> <p>| Name             | Will sideload | ---------------- | ------------- | app_installation | The app installation that requires each view, if present | permissions      | The permissions for each view</p>
     *
     * @param query <p>Query string used to find all views with matching title</p> (required)
     * @param access <p>Filter views by access. May be \"personal\", \"shared\", or \"account\"</p> (optional)
     * @param active <p>Filter by active views if true or inactive views if false</p> (optional)
     * @param groupId <p>Filter views by group</p> (optional)
     * @param sortBy <p>Possible values are \"alphabetical\", \"created_at\", \"updated_at\", and \"position\". If unspecified, the views are sorted by relevance</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     * @param include <p>A sideload to include in the response. See <a href=\"#sideloads-3\">Sideloads</a></p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/views/search")
    Mono<@Valid ViewsResponse> searchViews(
        @QueryValue("query") @NotNull String query,
        @QueryValue("access") @Nullable String access,
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("group_id") @Nullable Integer groupId,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder,
        @QueryValue("include") @Nullable String include
    );

    /**
     * {@summary Show View}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param viewId <p>The ID of the view</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/views/{view_id}")
    Mono<@Valid ViewResponse> showView(
        @PathVariable("view_id") @NotNull Integer viewId
    );

    /**
     * {@summary Update Many Views}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Request Parameters</h4> <p>The PUT request expects a <code>views</code> object that lists the views to update.</p> <p>Each view may have the following properties:</p> <p>| Name     | Mandatory | Description | -------- | --------- | ----------- | id       | yes       | The ID of the view to update | position | no        | The new position of the view | active   | no        | The active status of the view (true or false)</p> <h4>Example Request Body</h4> <p><code>js {   \"views\": [     {\"id\": 25, \"position\": 3},     {\"id\": 23, \"position\": 5},     {\"id\": 27, \"position\": 9},     {\"id\": 22, \"position\": 7}   ] }</code></p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/views/update_many")
    Mono<@Valid ViewsResponse> updateManyViews();

    /**
     * {@summary Update View}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>JSON Format</h4> <p>The PUT request takes one property, a <code>view</code> object that lists the values to update. All properties are optional.</p> <p><strong>Note</strong>: Updating a condition updates the containing array, clearing the other conditions. Include all your conditions when updating any condition.</p> <p>| Name        | Description | ----------- | ----------- | title       | The title of the view | all         | An array of one or more conditions. A ticket must meet all the conditions to be included in the view. The PUT request replaces all existing conditions. See <a href=\"/documentation/ticketing/reference-guides/conditions-reference\">Conditions reference</a> | any         | An array of one or more conditions. A ticket must meet any of them to be included in the view. At least one <code>all</code> condition must be defined with the <code>any</code> conditions. The PUT request replaces all existing <code>any</code> conditions. See <a href=\"/documentation/ticketing/reference-guides/conditions-reference\">Conditions reference</a> | active      | Allowed values are true or false. Determines if the view is displayed or not | output      | An object that specifies the columns to display. Example: <code>\"output\": {\"columns\": [\"status\", \"description,\" \"priority\"]}</code>. See <a href=\"#view-columns\">View columns</a> | restriction | An object that describes who can access the view. To give all agents access to the view, omit this property</p> <p>The <code>restriction</code> object has the following properties.</p> <p>| Name | Comment | ---- | ------- | type | Allowed values are \"Group\" or \"User\" | id   | The numeric ID of a single group or user | ids  | The numeric IDs of a single or more groups. Recommended for \"Group\" <code>type</code></p> <p>If <code>type</code> is \"Group\", the <code>ids</code> property is the preferred method of specifying the group id or ids.</p> <p>You can also update how items are sorted and grouped. See <a href=\"#view-sorting\">View sorting</a> in Create View.</p> <h4>Example Request Body</h4> <p><code>js {   \"view\": {     \"title\": \"Code red tickets\",     \"restriction\": {       \"type\": \"Group\",       \"ids\": [10052, 10057, 10062, 10002]     },     \"all\": [       {         \"field\": \"priority\",         \"operator\": \"is\",         \"value\": \"urgent\"       }     ],     \"output\": {       \"columns\": [\"status\", \"requester\", \"assignee\", \"updated\"]     }   } }</code></p>
     *
     * @param viewId <p>The ID of the view</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/views/{view_id}")
    Mono<@Valid ViewResponse> updateView(
        @PathVariable("view_id") @NotNull Integer viewId
    );
}