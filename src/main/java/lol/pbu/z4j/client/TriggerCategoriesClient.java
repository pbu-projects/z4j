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
import lol.pbu.z4j.model.BatchJobRequest;
import lol.pbu.z4j.model.BatchJobResponse;
import lol.pbu.z4j.model.CreateTriggerCategoryRequest;
import lol.pbu.z4j.model.Errors;
import lol.pbu.z4j.model.ListTriggerCategories200Response;
import lol.pbu.z4j.model.ListTriggerCategoriesIncludeParameter;
import lol.pbu.z4j.model.ListTriggerCategoriesPageParameter;
import lol.pbu.z4j.model.ListTriggerCategoriesSortParameter;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.TriggerCategoryResponse;
import lol.pbu.z4j.model.UpdateTriggerCategoryRequest;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TriggerCategoriesClient {

    /**
     * {@summary Create Batch Job for Ticket Trigger Categories}
     * <p>Creates a job that performs a batch operation for the given ticket trigger categories.</p>
     *
     * @param batchJobRequest (required)
     *
     * @return <p>The response to the batch job</p> (status code 200)
     *         or <p>Error</p> (status code 400)
     */
    @Post("/api/v2/trigger_categories/jobs")
    Mono<@Valid BatchJobResponse> batchOperateTriggerCategories(
        @Body @NotNull @Valid BatchJobRequest batchJobRequest
    );

    /**
     * {@summary Create Ticket Trigger Category}
     * <p>Creates a ticket trigger category.</p>
     *
     * @param createTriggerCategoryRequest (required)
     *
     * @return <p>The created ticket trigger category</p> (status code 200)
     *         or <p>Error</p> (status code 400)
     *         or <p>Error</p> (status code 403)
     */
    @Post("/api/v2/trigger_categories")
    Mono<@Valid TriggerCategoryResponse> createTriggerCategory(
        @Body @NotNull @Valid CreateTriggerCategoryRequest createTriggerCategoryRequest
    );

    /**
     * {@summary Delete Ticket Trigger Category}
     * <p>Deletes the ticket trigger category with the specified ID.</p>
     *
     * @param triggerCategoryId <p>The id of the ticket trigger category to delete</p> (required)
     *
     * @return <p>No content</p> (status code 204)
     *         or <p>Error</p> (status code 400)
     *         or <p>Error</p> (status code 404)
     */
    @Delete("/api/v2/trigger_categories/{trigger_category_id}")
    Mono<Void> deleteTriggerCategory(
        @PathVariable("trigger_category_id") @NotNull String triggerCategoryId
    );

    /**
     * {@summary List Ticket Trigger Categories}
     * <p>Returns all the ticket trigger categories in the account.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param page <p>Pagination parameters</p> (optional)
     * @param sort <p>Sort parameters</p> (optional)
     * @param include <p>Allowed sideloads</p> (optional)
     *
     * @return <p>A paged array of ticket trigger categories</p> (status code 200)
     *         or <p>Error</p> (status code 400)
     *         or <p>Error</p> (status code 403)
     */
    @Get("/api/v2/trigger_categories")
    Mono<@Valid ListTriggerCategories200Response> listTriggerCategories(
        @QueryValue("page") @Nullable @Valid ListTriggerCategoriesPageParameter page,
        @QueryValue("sort") @Nullable ListTriggerCategoriesSortParameter sort,
        @QueryValue("include") @Nullable ListTriggerCategoriesIncludeParameter include
    );

    /**
     * {@summary Show Ticket Trigger Category}
     * <p>Returns the ticket trigger category with the specified ID.</p>
     *
     * @param triggerCategoryId <p>The id of the ticket trigger category to retrieve</p> (required)
     *
     * @return <p>The requested ticket trigger category</p> (status code 200)
     *         or <p>Error</p> (status code 404)
     */
    @Get("/api/v2/trigger_categories/{trigger_category_id}")
    Mono<@Valid TriggerCategoryResponse> showTriggerCategoryById(
        @PathVariable("trigger_category_id") @NotNull String triggerCategoryId
    );

    /**
     * {@summary Update Ticket Trigger Category}
     * <p>Updates the ticket trigger category with the specified ID.</p>
     *
     * @param triggerCategoryId <p>The id of the ticket trigger category to update</p> (required)
     * @param updateTriggerCategoryRequest (required)
     *
     * @return <p>The updated trigger category</p> (status code 200)
     *         or <p>Error</p> (status code 400)
     *         or <p>Error</p> (status code 404)
     */
    @Patch("/api/v2/trigger_categories/{trigger_category_id}")
    Mono<@Valid TriggerCategoryResponse> updateTriggerCategory(
        @PathVariable("trigger_category_id") @NotNull String triggerCategoryId,
        @Body @NotNull @Valid UpdateTriggerCategoryRequest updateTriggerCategoryRequest
    );
}