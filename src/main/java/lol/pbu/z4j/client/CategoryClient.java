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

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.*;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Categories in Zendesk.</h1>
 * <ul>
 *     <li>Create Category by Locale {@link #createCategory}</li>
 *     <li>Create Category {@link #createCategoryNoLocale}</li>
 *     <li>Delete Category by Locale {@link #deleteCategory}</li>
 *     <li>Delete Category {@link #deleteCategoryNoLocale}</li>
 *     <li>List Categories by Locale {@link #listCategories}</li>
 *     <li>List Categories {@link #listCategoriesNoLocale}</li>
 *     <li>Show Category by Locale {@link #showCategory}</li>
 *     <li>Show Category {@link #showCategoryNoLocale}</li>
 *     <li>Update Category by Locale {@link #updateCategory}</li>
 *     <li>Update Category {@link #updateCategoryNoLocale}</li>
 *     <li>Update Category Source Locale by Locale {@link #updateCategorySourceLocale}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Retryable
@Client("zendesk")
public interface CategoryClient {

    /**
     * <h1>{@summary Create Category by Locale}</h1>
     * <p>You must specify a category name and locale.
     * Though the Zendesk Api states you can create multiple translations at this time, in practice this doesn't work.</p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * NOTE: pagination is not currently supported
     *
     * @param locale                The in which the item is displayed. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param createCategoryRequest The locale can be omitted from the createCategoryRequest. (required)
     * @return Created response (status code 201)
     */
    @Post("/api/v2/help_center/{locale}/categories")
    Mono<@Valid CategoryResponse> createCategory(
            @PathVariable("locale") @NotNull String locale,
            @Body @NotNull @Valid CreateCategoryRequest createCategoryRequest
    );

    /**
     * <h1>{@summary Create Category}</h1>
     * <p>You must specify a category name and locale.
     * Though the Zendesk Api states you can create multiple translations at this time, in practice this doesn't work.</p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * NOTE: pagination is not currently supported
     *
     * @param createCategoryRequest The locale cannot be omitted from this createCategoryRequest. (required)
     * @return OK Response (status code 201)
     */
    @Post("/api/v2/help_center/categories")
    Mono<@Valid CategoryResponse> createCategoryNoLocale(@Body @Valid CreateCategoryRequest createCategoryRequest);

    /**
     * <h1>{@summary Delete Category by Locale}</h1>
     * <p><strong>WARNING: Every section and all articles in the category will also be deleted.</strong></p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param locale     The locale the item is displayed in. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param categoryId The unique ID of the category (required)
     * @return No content (status code 204)
     */
    @Delete("/api/v2/help_center/{locale}/categories/{category_id}")
    Mono<Void> deleteCategory(@PathVariable("locale") @NotNull String locale, @PathVariable("category_id") @NotNull Long categoryId);

    /**
     * <h1>{@summary Delete Category}</h1>
     * <p><strong>WARNING: Every section and all articles in the category will also be deleted.</strong></p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param categoryId The unique ID of the category (required)
     * @return <p>No content</p> (status code 204)
     */
    @Delete("/api/v2/help_center/categories/{category_id}")
    Mono<Void> deleteCategoryNoLocale(@PathVariable("category_id") @NotNull Long categoryId);

    /**
     * <h1>{@summary List Categories by Locale}</h1>
     * <p>The response will list only the categories that the agent, end user, or anonymous user can view in the help center.</p>
     * <p>Translations are embedded within the category because they're not shared between resources.</p>
     * <h4>Allowed for Anonymous users</h4>
     *
     * @param locale    The locale the item is displayed in. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param sortBy    {@link SortCategoryBy}  (optional)
     * @param sortOrder {@link SortOrder}  (optional)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/categories")
    Mono<@Valid CategoriesResponse> listCategories(
            @PathVariable("locale") @NotNull String locale,
            @QueryValue("sort_by") @Nullable SortCategoryBy sortBy,
            @QueryValue("sort_order") @Nullable SortOrder sortOrder
    );

    /**
     * <h1>{@summary List Categories}</h1>
     * Allowed for Agents<p>The response will list only the categories that the agent can view in the help center.</p>
     *
     * @param sortBy    {@link SortCategoryBy}  (optional)
     * @param sortOrder {@link SortOrder}  (optional)
     * @return status code 200
     */
    @Get("/api/v2/help_center/categories")
    Mono<@Valid CategoriesResponse> listCategoriesNoLocale(
            @QueryValue("sort_by") @Nullable SortCategoryBy sortBy,
            @QueryValue("sort_order") @Nullable SortOrder sortOrder
    );

    /**
     * <h1>{@summary Show Category by Locale}</h1>
     * Note: Admins and agents can query the{@link CategoryClient#showCategoryNoLocale(Long)}.
     * End users and anonymous users can only use this command.</p>
     * <p>Translations are embedded within the category because they're not shared between resources.</p>
     *<h4>Allowed for Anonymous users</h4>
     * @param locale     The locale the item is displayed in. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param categoryId The unique ID of the category (required)
     * @return status code 200
     */
    @Get("/api/v2/help_center/{locale}/categories/{category_id}")
    Mono<@Valid CategoryResponse> showCategory(
            @PathVariable("locale") @NotNull String locale,
            @PathVariable("category_id") @NotNull Long categoryId
    );

    /**
     * <h1>{@summary Show Category}</h1>
     * Allowed for Agents
     *
     * @param categoryId The unique ID of the category (required)
     * @return status code 200
     */
    @Get("/api/v2/help_center/categories/{category_id}")
    Mono<@Valid CategoryResponse> showCategoryNoLocale(@PathVariable("category_id") @NotNull Long categoryId);

    /**
     * <h1>{@summary Update Category by Locale}</h1>
     * <p>This only updates category-level metadata such as the sorting position. This doesn't update category translations.</p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param locale                The locale the item is displayed in. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param categoryId            The unique ID of the category (required)
     * @param createCategoryRequest {@link CreateCategoryRequest} (required)
     * @return OK Response (status code 200)
     */
    @Put("/api/v2/help_center/{locale}/categories/{category_id}")
    Mono<@Valid CategoryResponse> updateCategory(
            @PathVariable("locale") @NotNull String locale,
            @PathVariable("category_id") @NotNull Long categoryId,
            @Body @NotNull @Valid CreateCategoryRequest createCategoryRequest
    );

    /**
     * <h1>{@summary Update Category}</h1>
     * <p>This only updates category-level metadata such as the sorting position. This doesn't update category translations.</p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param categoryId            The unique ID of the category (required)
     * @param createCategoryRequest (optional)
     * @return OK Response (status code 200)
     */
    @Put("/api/v2/help_center/categories/{category_id}")
    Mono<@Valid CategoryResponse> updateCategoryNoLocale(
            @PathVariable("category_id") @NotNull Long categoryId,
            @Body @Nullable @Valid CreateCategoryRequest createCategoryRequest
    );

    /**
     * <h1>{@summary Update Category Source Locale by Locale}</h1>
     * <p>The endpoint updates the category <code>source_locale</code> property</p>
     * <h4>Allowed for Agents</h4>
     *
     * @param categoryId The unique ID of the category (required)
     * @return <p>OK Response</p> (status code 200)
     */
    @Put("/api/v2/help_center/categories/{category_id}/source_locale")
    Mono<@Valid CategoryResponse> updateCategorySourceLocale(@PathVariable("category_id") @NotNull Long categoryId);
}
