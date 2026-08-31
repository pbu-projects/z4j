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
import lol.pbu.z4j.model.AutomationResponse;
import lol.pbu.z4j.model.AutomationsResponse;
import reactor.core.publisher.Mono;
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
public interface AutomationsClient {

    /**
     * {@summary Bulk Delete Automations}
     * <p>Deletes the automations corresponding to the provided comma-separated list of IDs.</p> <p><strong>Note</strong>: You might be restricted from deleting some default automations. If included in a bulk deletion, the unrestricted automations will be deleted.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Request Parameters</h4> <p>The DELETE request takes one parameter, an <code>ids</code> object that lists the automations to delete.</p> <p>| Name | Description | ---- | ----------- | ids  | The IDs of the automations to delete</p> <h4>Example request</h4> <p><code>js {   \"ids\": \"25,23,27,22\" }</code></p>
     *
     * @param ids <p>The IDs of the automations to delete</p> (optional)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/automations/destroy_many")
    Mono<Void> bulkDeleteAutomations(
        @QueryValue("ids") @Nullable @Format(FORMAT_MULTI) List<@NotNull Long> ids
    );

    /**
     * {@summary Create Automation}
     * <p>Creates an automation.</p> <p>New automations must be unique and have at least one condition that is true only once or an action that nullifies at least one of the conditions. Active automations can have overlapping conditions but can't be identical.</p> <p>The request must include the following conditions in the <code>all</code> array:</p> <ul> <li>At least one time-based condition</li> <li>At least one condition that checks one of the following fields: <code>status</code>, <code>type</code>, <code>group_id</code>, <code>assignee_id</code>, or <code>requester_id</code>.</li> </ul> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/automations")
    Mono<@Valid AutomationResponse> createAutomation();

    /**
     * {@summary Delete Automation}
     * <p><strong>Note</strong>: You might be restricted from deleting some default automations.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param automationId <p>The ID of the automation</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/automations/{automation_id}")
    Mono<Void> deleteAutomation(
        @PathVariable("automation_id") @NotNull Long automationId
    );

    /**
     * {@summary List Active Automations}
     * <p>Lists all active automations.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Available Parameters</h4> <p>You can pass in any combination of the following optional filters:</p> <p>| Name       | Type   | Comment | ---------- | ------ | ------- | sort_by    | string | Possible values are \"alphabetical\", \"created_at\", \"updated_at\", \"usage_1h\", \"usage_24h\", or \"usage_7d\". Defaults to \"position\" | sort_order | string | One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name             | Will sideload | ---------------- | ------------- | app_installation | The app installation that requires each automation, if present | permissions      | The permissions for each automation | usage_1h         | The number of tickets processed by an automation in the past hour | usage_24h        | The number of tickets processed by an automation in the past day | usage_7d         | The number of tickets processed by an automation in the past week | usage_30d        | The number of tickets processed by an automation in the past thirty days</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/automations/active")
    Mono<@Valid AutomationsResponse> listActiveAutomations();

    /**
     * {@summary List Automations}
     * <p>Lists all automations for the current account.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Available Parameters</h4> <p>You can pass in any combination of the following optional filters:</p> <p>| Name       | Type    | Comment | ---------- | ------- | ------- | active     | boolean | Only active automations if true, inactive automations if false | sort_by    | string  | Possible values are \"alphabetical\", \"created_at\", \"updated_at\", \"usage_1h\", \"usage_24h\", or \"usage_7d\". Defaults to \"position\" | sort_order | string  | One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> <h4>Sideloads</h4> <p>The following sideloads are supported. The usage sideloads are only supported on the Support Professional or Suite Growth plan or above.</p> <p>| Name             | Will sideload | ---------------- | ------------- | app_installation | The app installation that requires each automation, if present | permissions      | The permissions for each automation | usage_1h         | The number of tickets processed by an automation in the past hour | usage_24h        | The number of tickets processed by an automation in the past day | usage_7d         | The number of tickets processed by an automation in the past week | usage_30d        | The number of tickets processed by an automation in the past thirty days</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/automations")
    Mono<@Valid AutomationsResponse> listAutomations();

    /**
     * {@summary Search Automations}
     * <h4>Pagination</h4> <ul> <li>Offset pagination only</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/#using-offset-pagination\">Using Offset Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Sideloads</h4> <p>The following sideloads are supported. For more information, see <a href=\"/documentation/ticketing/using-the-zendesk-api/side_loading/\">Side-loading</a>.</p> <p>| Name             | Will sideload | ---------------- | ------------- | app_installation | The app installation that requires each automation, if present | permissions      | The permissions for each automation | usage_1h         | The number of tickets processed by an automation in the past hour | usage_24h        | The number of tickets processed by an automation in the past day | usage_7d         | The number of tickets processed by an automation in the past week | usage_30d        | The number of tickets processed by an automation in the past thirty days</p>
     *
     * @param query <p>Query string used to find all automations with matching title</p> (required)
     * @param active <p>Filter by active automations if true or inactive automations if false</p> (optional)
     * @param sortBy <p>Possible values are \"alphabetical\", \"created_at\", \"updated_at\", and \"position\". If unspecified, the automations are sorted by relevance</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     * @param include <p>A sideload to include in the response. See <a href=\"#sideloads-2\">Sideloads</a></p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/automations/search")
    Mono<@Valid AutomationsResponse> searchAutomations(
        @QueryValue("query") @NotNull String query,
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder,
        @QueryValue("include") @Nullable String include
    );

    /**
     * {@summary Show Automation}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param automationId <p>The ID of the automation</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/automations/{automation_id}")
    Mono<@Valid AutomationResponse> showAutomation(
        @PathVariable("automation_id") @NotNull Long automationId
    );

    /**
     * {@summary Update Automation}
     * <p>Updates an automation.</p> <p>Updated automations must be unique and have at least one condition that is true only once or an action that nullifies at least one of the conditions. Active automations can have overlapping conditions but can't be identical.</p> <p>The request must include the following conditions in the <code>all</code> array: - At least one time-based condition - At least one condition that checks one of the following fields: 'status', 'type', 'group_id', 'assignee_id', or 'requester_id'</p> <p><strong>Note</strong>: Updating a condition or action updates both the <code>conditions</code> and <code>actions</code> arrays, clearing all existing values of both arrays. Include all your conditions and actions when updating any condition or action. <strong>Note</strong>: You might be restricted from updating some default automations.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param automationId <p>The ID of the automation</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/automations/{automation_id}")
    Mono<@Valid AutomationResponse> updateAutomation(
        @PathVariable("automation_id") @NotNull Long automationId
    );

    /**
     * {@summary Update Many Automations}
     * <p><strong>Note</strong>: You might be restricted from updating some default automations. If included in a bulk update, the unrestricted automations will be updated.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Request Parameters</h4> <p>The PUT request expects an <code>automations</code> object that lists the automations to update.</p> <p>Each automation may have the following properties:</p> <p>| Name     | Mandatory | Description | -------- | --------- | ----------- | id       | yes       | The ID of the automation to update | position | no        | The new position of the automation | active   | no        | The active status of the automation (true or false)</p> <h4>Example Request</h4> <p><code>js {   \"automations\": [     {\"id\": 25, \"position\": 3},     {\"id\": 23, \"position\": 5},     {\"id\": 27, \"position\": 9},     {\"id\": 22, \"position\": 7}   ] }</code></p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/automations/update_many")
    Mono<@Valid AutomationsResponse> updateManyAutomations();
}