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
import lol.pbu.z4j.model.CreateViewCategoryRequest;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.OrderViewCategoriesRequest;
import lol.pbu.z4j.model.UpdateViewCategoryRequest;
import lol.pbu.z4j.model.ViewCategoriesResponse;
import lol.pbu.z4j.model.ViewCategoryErrors;
import lol.pbu.z4j.model.ViewCategoryResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface ViewCategoriesClient {

    /**
     * {@summary Create View Category}
     * <p>Creates a view category.</p>
     *
     * @param createViewCategoryRequest (required)
     *
     * @return <p>The created view category</p> (status code 200)
     *         or <p>Bad Request - Invalid Input</p> (status code 400)
     *         or <p>Forbidden - Access Denied</p> (status code 403)
     */
    @Post("/api/v2/view_categories")
    Mono<@Valid ViewCategoryResponse> createViewCategory(
        @Body @NotNull @Valid CreateViewCategoryRequest createViewCategoryRequest
    );

    /**
     * {@summary Delete View Category}
     * <p>Deletes the view category with the specified id.</p>
     *
     * @param viewCategoryId <p>The id of the view category to delete</p> (required)
     *
     * @return <p>No content</p> (status code 204)
     *         or <p>Returned when the provided id is invalid or does not correspond to any existing view category.</p> (status code 404)
     */
    @Delete("/api/v2/view_categories/{view_category_id}")
    Mono<Void> deleteViewCategory(
        @PathVariable("view_category_id") @NotNull String viewCategoryId
    );

    /**
     * {@summary List View Categories}
     * <p>Returns all view categories in the account. If the optional parameter <code>parent_id</code> is provided, only the categories associated with the specified parent id will be returned.</p>
     *
     * @param parentId <p>The id of the parent category</p> (optional)
     *
     * @return <p>An array of view categories</p> (status code 200)
     *         or <p>Forbidden - Access Denied</p> (status code 403)
     */
    @Get("/api/v2/view_categories")
    Mono<@Valid ViewCategoriesResponse> listViewCategories(
        @QueryValue("parent_id") @Nullable String parentId
    );

    /**
     * {@summary Order View Categories and Views}
     * <p>Sets the positions of specified view categories or views.</p>
     *
     * @param orderViewCategoriesRequest (required)
     *
     * @return <p>No content - the order was successful</p> (status code 204)
     *         or <p>Bad Request - Invalid parameters</p> (status code 400)
     *         or <p>Forbidden - Access Denied</p> (status code 403)
     */
    @Put("/api/v2/view_categories/order")
    Mono<Void> orderViewCategories(
        @Body @NotNull @Valid OrderViewCategoriesRequest orderViewCategoriesRequest
    );

    /**
     * {@summary Update View Category}
     * <p>Updates the view category with the specified id.</p>
     *
     * @param viewCategoryId <p>The id of the view category to update</p> (required)
     * @param updateViewCategoryRequest (required)
     *
     * @return <p>The updated view category</p> (status code 200)
     *         or <p>Bad Request - Invalid Input</p> (status code 400)
     *         or <p>Forbidden - Access Denied</p> (status code 403)
     *         or <p>Returned when the provided id is invalid or does not correspond to any existing view category.</p> (status code 404)
     */
    @Patch("/api/v2/view_categories/{view_category_id}")
    Mono<@Valid ViewCategoryResponse> updateViewCategory(
        @PathVariable("view_category_id") @NotNull String viewCategoryId,
        @Body @NotNull @Valid UpdateViewCategoryRequest updateViewCategoryRequest
    );
}