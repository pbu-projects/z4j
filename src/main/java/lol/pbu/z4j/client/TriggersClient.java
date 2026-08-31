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
import lol.pbu.z4j.model.SearchObjectTriggersFilterParameter;
import lol.pbu.z4j.model.TriggerBulkUpdateRequest;
import lol.pbu.z4j.model.TriggerDefinitionResponse;
import lol.pbu.z4j.model.TriggerResponse;
import lol.pbu.z4j.model.TriggerRevisionResponse;
import lol.pbu.z4j.model.TriggerRevisionsResponse;
import lol.pbu.z4j.model.TriggerWithCategoryRequest;
import lol.pbu.z4j.model.TriggersResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TriggersClient {

    /**
     * {@summary Create Trigger}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param triggerWithCategoryRequest (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/triggers")
    Mono<@Valid TriggerResponse> createTrigger(
        @Body @NotNull @Valid TriggerWithCategoryRequest triggerWithCategoryRequest
    );

    /**
     * {@summary Bulk Delete Ticket Triggers}
     * <p>Deletes the ticket triggers corresponding to the provided comma-separated list of IDs.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Request Parameters</h4> <p>The DELETE request takes one parameter, an <code>ids</code> object that lists the ticket triggers to delete.</p> <p>| Name | Description | ---- | ----------- | ids  | The IDs of the triggers to delete</p> <h4>Example request</h4> <p><code>js {   \"ids\": \"25,23,27,22\" }</code></p>
     *
     * @param ids <p>A comma separated list of trigger IDs</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/triggers/destroy_many")
    Mono<Void> deleteManyTriggers(
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary Delete Ticket Trigger}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param triggerId <p>The ID of the trigger</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/triggers/{trigger_id}")
    Mono<Void> deleteTrigger(
        @PathVariable("trigger_id") @NotNull Long triggerId
    );

    /**
     * {@summary Show Ticket Trigger}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <p>The Via Type value is a number instead of a text string. See <a href=\"/documentation/ticketing/reference-guides/via-types/\">Via types reference</a> for the keys.</p>
     *
     * @param triggerId <p>The ID of the trigger</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/triggers/{trigger_id}")
    Mono<@Valid TriggerResponse> getTrigger(
        @PathVariable("trigger_id") @NotNull Long triggerId
    );

    /**
     * {@summary List Active Ticket Triggers}
     * <p>Lists all active ticket triggers.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name             | Will sideload | ---------------- | ------------- | app_installation | The app installation that requires each ticket trigger, if present | permissions      | The permissions for each trigger | usage_1h         | The number of times each ticket trigger has been used in the past hour | usage_24h        | The number of times each ticket trigger has been used in the past day | usage_7d         | The number of times each ticket trigger has been used in the past week | usage_30d        | The number of times each ticket trigger has been used in the past thirty days</p>
     *
     * @param sort <p>Cursor-based pagination only. Possible values are \"alphabetical\", \"created_at\", \"updated_at\", or \"position\".</p> (optional)
     * @param sortBy <p>Offset pagination only. Possible values are \"alphabetical\", \"created_at\", \"updated_at\", \"usage_1h\", \"usage_24h\", or \"usage_7d\". Defaults to \"position\"</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     * @param categoryId <p>Filter triggers by category ID</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/triggers/active")
    Mono<@Valid TriggersResponse> listActiveTriggers(
        @QueryValue("sort") @Nullable String sort,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder,
        @QueryValue("category_id") @Nullable String categoryId
    );

    /**
     * {@summary List Ticket Trigger Action and Condition Definitions}
     * <p>Returns the definitions of the actions a ticket trigger can perform and the definitions of the conditions under which a ticket trigger can execute. The definition of the action includes a title (\"Status\"), a type (\"list\"), and possible values. The definition of the condition includes the same fields as well as the possible operators.</p> <p>For a list of supported actions, see the <a href=\"/documentation/ticketing/reference-guides/actions-reference\">Actions reference</a> For a list of supported conditions, see the <a href=\"/documentation/ticketing/reference-guides/conditions-reference\">Conditions reference</a></p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/triggers/definitions")
    Mono<@Valid TriggerDefinitionResponse> listTriggerActionConditionDefinitions();

    /**
     * {@summary List Ticket Trigger Revisions}
     * <p>List the revisions associated with a ticket trigger. Ticket trigger revision history is only available on Enterprise plans.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name  | Will sideload | ----- | ------------- | users | The user that authored each revision</p> <h4>Pagination</h4> <p>This endpoint uses cursor-based pagination. The records are ordered in descending order by the <code>created_at</code> timestamp, then by <code>id</code> on duplicate <code>created_at</code> values.</p> <p>The <code>cursor</code> parameter is a non-human-readable argument you can use to move forward or backward in time.</p> <p>Each JSON response will contain the following attributes to help you get more results:</p> <ul> <li><code>after_url</code> requests more recent results</li> <li><code>before_url</code> requests older results</li> <li><code>after_cursor</code> is the cursor to build the request yourself</li> <li><code>before_cursor</code> is the cursor to build the request yourself</li> </ul> <p>The properties are null if no more records are available.</p> <p>You can request a maximum of 1000 records using the <code>limit</code> parameter. If no <code>limit</code> parameter is supplied, it will default to 1,000.</p>
     *
     * @param triggerId <p>The ID of the trigger</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/triggers/{trigger_id}/revisions")
    Mono<@Valid TriggerRevisionsResponse> listTriggerRevisions(
        @PathVariable("trigger_id") @NotNull Long triggerId
    );

    /**
     * {@summary List Ticket Triggers}
     * <p>Lists all ticket triggers for the current account.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Sideloads</h4> <p>The following sideloads are supported. The usage sideloads are only supported on the Support Professional or Suite Growth plan or above.</p> <p>| Name             | Will sideload | ---------------- | ------------- | app_installation | The app installation that requires each trigger, if present | permissions      | The permissions for each trigger | usage_1h         | The number of times each trigger has been used in the past hour | usage_24h        | The number of times each trigger has been used in the past day | usage_7d         | The number of times each trigger has been used in the past week | usage_30d        | The number of times each trigger has been used in the past thirty days</p>
     *
     * @param active <p>Filter by active triggers if true or inactive triggers if false</p> (optional)
     * @param sort <p>Cursor-based pagination only. Possible values are \"alphabetical\", \"created_at\", \"updated_at\", or \"position\".</p> (optional)
     * @param sortBy <p>Offset pagination only. Possible values are \"alphabetical\", \"created_at\", \"updated_at\", \"usage_1h\", \"usage_24h\", or \"usage_7d\". Defaults to \"position\"</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     * @param categoryId <p>Filter triggers by category ID</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/triggers")
    Mono<@Valid TriggersResponse> listTriggers(
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("sort") @Nullable String sort,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder,
        @QueryValue("category_id") @Nullable String categoryId
    );

    /**
     * {@summary Reorder Ticket Triggers}
     * <p>Alters the firing order of ticket triggers in the account. See <a href=\"https://support.zendesk.com/hc/en-us/articles/115015696088\">Reordering and sorting triggers</a> in the Zendesk Help Center. The firing order is set in a <code>trigger_ids</code> array in the request body.</p> <p>You must include every ticket trigger id in your account to reorder the ticket triggers. If not, the endpoint will return 404 Forbidden.</p> <p>Reordering ticket triggers via the API is not permitted if you have more than one ticket trigger category. If there is more than one ticket trigger category, the endpoint will return a <code>LimitOneCategory</code> error.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/triggers/reorder")
    Mono<@Valid TriggerResponse> reorderTriggers();

    /**
     * {@summary Search Ticket Triggers}
     * <h4>Pagination</h4> <ul> <li>Offset pagination only</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/#using-offset-pagination\">Using Offset Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Sideloads</h4> <p>The following sideloads are supported. For more information, see <a href=\"/documentation/ticketing/using-the-zendesk-api/side_loading/\">Side-loading</a>.</p> <p>| Name             | Will sideload | ---------------- | ------------- | app_installation | The app installation that requires each ticket trigger, if present | permissions      | The permissions for each ticket trigger | usage_1h         | The number of times each ticket trigger has been used in the past hour | usage_24h        | The number of times each ticket trigger has been used in the past day | usage_7d         | The number of times each ticket trigger has been used in the past week | usage_30d        | The number of times each ticket trigger has been used in the past thirty days</p> <h4>Filter</h4> <p>Use the <code>filter</code> query parameter to filter a ticket trigger search by one or more attributes. For example, the following <code>filter</code> argument filters ticket triggers by the <code>description</code> attribute:</p> <p><code>json {   \"json\": {     \"description\": \"Close a ticket\"   } }</code></p>
     *
     * @param query <p>Query string used to find all triggers with matching title</p> (required)
     * @param filter <p>Trigger attribute filters for the search. See <a href=\"#filter\">Filter</a></p> (optional)
     * @param active <p>Filter by active triggers if true or inactive triggers if false</p> (optional)
     * @param sort <p>Cursor-based pagination only. Possible values are \"alphabetical\", \"created_at\", \"updated_at\", or \"position\".</p> (optional)
     * @param sortBy <p>Offset pagination only. Possible values are \"alphabetical\", \"created_at\", \"updated_at\", \"usage_1h\", \"usage_24h\", or \"usage_7d\". Defaults to \"position\"</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     * @param include <p>A sideload to include in the response. See <a href=\"#sideloads-2\">Sideloads</a></p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/triggers/search")
    Mono<@Valid TriggersResponse> searchTriggers(
        @QueryValue("query") @NotNull String query,
        @QueryValue("filter") @Nullable @Valid SearchObjectTriggersFilterParameter filter,
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("sort") @Nullable String sort,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder,
        @QueryValue("include") @Nullable String include
    );

    /**
     * {@summary Show Ticket Trigger Revision}
     * <p>Fetches a revision associated with a ticket trigger. Ticket trigger revision history is only available on Enterprise plans.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name  | Will sideload | ----- | ------------- | users | The user that authored each revision</p>
     *
     * @param triggerId <p>The ID of the trigger</p> (required)
     * @param triggerRevisionId <p>The ID of the revision for a particular trigger</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/triggers/{trigger_id}/revisions/{trigger_revision_id}")
    Mono<@Valid TriggerRevisionResponse> triggerRevision(
        @PathVariable("trigger_id") @NotNull Long triggerId,
        @PathVariable("trigger_revision_id") @NotNull Long triggerRevisionId
    );

    /**
     * {@summary Update Many Ticket Triggers}
     * <p>Updates the position or the active status of multiple ticket triggers. Any additional properties are ignored.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Request Parameters</h4> <p>The PUT request expects a <code>triggers</code> object that lists the ticket triggers to update.</p> <p>Each ticket trigger may have the following properties:</p> <p>| Name        | Mandatory | Description | --------    | --------- | ----------- | id          | yes       | The ID of the ticket trigger to update | position    | no        | The new position of the ticket trigger | active      | no        | The active status of the ticket trigger (true or false) | category_id | no        | The ID of the new category the ticket trigger is to be moved to</p> <h4>Example Request</h4> <p><code>js {   \"triggers\": [     {\"id\": 25, \"position\": 3},     {\"id\": 23, \"position\": 5},     {\"id\": 27, \"position\": 9},     {\"id\": 22, \"position\": 7}   ] }</code></p>
     *
     * @param triggerBulkUpdateRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/triggers/update_many")
    Mono<@Valid TriggersResponse> updateManyTriggers(
        @Body @NotNull @Valid TriggerBulkUpdateRequest triggerBulkUpdateRequest
    );

    /**
     * {@summary Update Ticket Trigger}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Note</h4> <p>Updating a condition or action updates both the conditions and actions arrays, clearing all existing values of both arrays. Include all your conditions and actions when updating any condition or action.</p>
     *
     * @param triggerId <p>The ID of the trigger</p> (required)
     * @param triggerWithCategoryRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/triggers/{trigger_id}")
    Mono<@Valid TriggerResponse> updateTrigger(
        @PathVariable("trigger_id") @NotNull Long triggerId,
        @Body @NotNull @Valid TriggerWithCategoryRequest triggerWithCategoryRequest
    );
}