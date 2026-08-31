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
import lol.pbu.z4j.model.ObjectTriggerBulkUpdateRequest;
import lol.pbu.z4j.model.ObjectTriggerDefinitionResponse;
import lol.pbu.z4j.model.ObjectTriggerRequest;
import lol.pbu.z4j.model.ObjectTriggerResponse;
import lol.pbu.z4j.model.ObjectTriggersResponse;
import lol.pbu.z4j.model.SearchObjectTriggersFilterParameter;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface ObjectTriggersClient {

    /**
     * {@summary Create Object Trigger}
     * <p>Creates a new object trigger for a specified object.</p> <h4>Allowed For</h4> <ul> <li>Administrators</li> <li>Agents in custom roles with the <code>manage_triggers</code> permission (Enterprise only)</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param objectTriggerRequest (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/custom_objects/{custom_object_key}/triggers")
    Mono<@Valid ObjectTriggerResponse> createObjectTrigger(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @Body @NotNull @Valid ObjectTriggerRequest objectTriggerRequest
    );

    /**
     * {@summary Delete Many Object Triggers}
     * <p>Deletes the object triggers corresponding to the provided comma-separated list of ids. </p> <p><strong>Note</strong>: You can only bulk-delete triggers associated with one object at a time, specified by the <code>custom_object_key</code> in the request.</p> <h4>Allowed For</h4> <ul> <li>Administrators</li> <li>Agents in custom roles with the <code>manage_triggers</code> permission (Enterprise only)</li> </ul> <h4>Request Parameters</h4> <p>The DELETE request takes an <code>ids</code> object that lists the object triggers to delete. All of the specified object trigger <code>ids</code> must be associated with a single object.</p> <p>| Name | Description | ---- | ----------- | ids  | The ids of the triggers to delete</p> <h4>Example request</h4> <p><code>js {   \"ids\": \"25,23,27,22\" }</code></p>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param ids <p>A comma separated list of trigger IDs</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/custom_objects/{custom_object_key}/triggers/destroy_many")
    Mono<Void> deleteManyObjectTriggers(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("ids") @NotNull String ids
    );

    /**
     * {@summary Delete Object Trigger}
     * <p>Deletes a specified object trigger.</p> <h4>Allowed For</h4> <ul> <li>Administrators</li> <li>Agents in custom roles with the <code>manage_triggers</code> permission (Enterprise only)</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param triggerId <p>The ID of the trigger</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/custom_objects/{custom_object_key}/triggers/{trigger_id}")
    Mono<Void> deleteObjectTrigger(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("trigger_id") @NotNull Long triggerId
    );

    /**
     * {@summary Show Object Trigger}
     * <p>Returns details of a specific object trigger.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param triggerId <p>The ID of the trigger</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/triggers/{trigger_id}")
    Mono<@Valid ObjectTriggerResponse> getObjectTrigger(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("trigger_id") @NotNull Long triggerId
    );

    /**
     * {@summary List Active Object Triggers}
     * <p>Lists all active object triggers.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Administrators</li> <li>Agents in custom roles with the <code>manage_triggers</code> permission (Enterprise only)</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param sortBy <p>Offset pagination only. Possible values are \"alphabetical\", \"created_at\", \"updated_at\", \"usage_1h\", \"usage_24h\", or \"usage_7d\". Defaults to \"position\"</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/triggers/active")
    Mono<@Valid ObjectTriggersResponse> listActiveObjectTriggers(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List Object Triggers}
     * <p>Lists all triggers for the specified custom object.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param active <p>Filter by active triggers if true or inactive triggers if false</p> (optional)
     * @param sortBy <p>Offset pagination only. Possible values are \"alphabetical\", \"created_at\", \"updated_at\", \"usage_1h\", \"usage_24h\", or \"usage_7d\". Defaults to \"position\"</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/triggers")
    Mono<@Valid ObjectTriggersResponse> listObjectTriggers(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List Object Trigger Action and Condition Definitions}
     * <p>Lists the conditions and actions of all triggers for the specified custom object.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/triggers/definitions")
    Mono<@Valid ObjectTriggerDefinitionResponse> listObjectTriggersDefinitions(
        @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * {@summary Search Object Triggers}
     * <p>Returns a list of object triggers that meet your filter or search criteria.</p> <h4>Pagination</h4> <ul> <li>Offset pagination only</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/#using-offset-pagination\">Using Offset Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Filter</h4> <p>Use the <code>filter</code> query parameter to filter an object trigger search by one or more attributes. For example, the following <code>filter</code> argument filters object triggers by the <code>title</code> attribute:</p> <p><code>json {   \"json\": {     \"title\": \"test\"   } }</code></p>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
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
    @Get("/api/v2/custom_objects/{custom_object_key}/triggers/search")
    Mono<@Valid ObjectTriggersResponse> searchObjectTriggers(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("query") @NotNull String query,
        @QueryValue("filter") @Nullable @Valid SearchObjectTriggersFilterParameter filter,
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("sort") @Nullable String sort,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder,
        @QueryValue("include") @Nullable String include
    );

    /**
     * {@summary Update Many Object Triggers}
     * <p>Updates the position or the active status of multiple object triggers. Any additional properties are ignored.</p> <p><strong>Note</strong>: You can only bulk-update triggers associated with one object at a time, specified by the <code>custom_object_key</code> in the request.</p> <h4>Allowed For</h4> <ul> <li>Administrators</li> <li>Agents in custom roles with the <code>manage_triggers</code> permission (Enterprise only)</li> </ul> <h4>Request Parameters</h4> <p>The PUT request expects a <code>triggers</code> object that lists the object triggers to update. All of the specified object trigger <code>ids</code> must be associated with a single object.</p> <p>You can specify the following properties for each object trigger you're updating:</p> <p>| Name        | Mandatory | Description | --------    | --------- | ----------- | id          | yes       | The ID of the object trigger to update | position    | no        | The new position of the object trigger | active      | no        | The active status of the object trigger (true or false)</p> <h4>Example Request</h4> <p><code>js {   \"triggers\": [     {\"id\": 25, \"position\": 3},     {\"id\": 23, \"active\": true},     {\"id\": 27, \"position\": 9, \"active\": false},     {\"id\": 22, \"position\": 7}   ] }</code></p>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param objectTriggerBulkUpdateRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/custom_objects/{custom_object_key}/triggers/update_many")
    Mono<@Valid ObjectTriggersResponse> updateManyObjectTriggers(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @Body @NotNull @Valid ObjectTriggerBulkUpdateRequest objectTriggerBulkUpdateRequest
    );

    /**
     * {@summary Update Object Trigger}
     * <p>Updates a specified object trigger.</p> <p><strong>Note</strong>: Updating a condition or action updates both the conditions and actions arrays, clearing all existing values of both arrays. Include all your conditions and actions when updating any condition or action.</p> <h4>Allowed For</h4> <ul> <li>Administrators</li> <li>Agents in custom roles with the <code>manage_triggers</code> permission (Enterprise only)</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param triggerId <p>The ID of the trigger</p> (required)
     * @param objectTriggerRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/custom_objects/{custom_object_key}/triggers/{trigger_id}")
    Mono<@Valid ObjectTriggerResponse> updateObjectTrigger(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("trigger_id") @NotNull Long triggerId,
        @Body @NotNull @Valid ObjectTriggerRequest objectTriggerRequest
    );
}