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

@Retryable
@Client("zendesk")
public interface CategoryClient {

    /**
     * {@summary Create Category by Locale}
     * <p>You must specify a category name and locale. The locale can be omitted if it's specified in the URL. Optionally, you can specify multiple <a href='/api-reference/help_center/help-center-api/translations'>translations</a> for the category. The specified locales must be enabled for the current Help Center.</p> <h4>Allowed for</h4> <ul>     <li>Help Center managers</li> </ul>
     *
     * @param locale                The locale the item is displayed in. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param createCategoryRequest (optional)
     * @return Created response (status code 201)
     */
    @Post("/api/v2/help_center/{locale}/categories")
    Mono<@Valid CategoryResponse> createCategory(
            @PathVariable("locale") @NotNull String locale,
            @Body @Nullable @Valid CreateCategoryRequest createCategoryRequest
    );

    /**
     * {@summary Create Category}
     * <p>You must specify a category name and locale. The locale can be omitted if it's specified in the URL. Optionally, you can specify multiple <a href='/api-reference/help_center/help-center-api/translations'>translations</a> for the category. The specified locales must be enabled for the current Help Center.</p> <h4>Allowed for</h4> <ul>     <li>Help Center managers</li> </ul>
     *
     * @param createCategoryRequest (optional)
     * @return OK Response (status code 201)
     */
    @Post("/api/v2/help_center/categories")
    Mono<@Valid CategoryResponse> createCategoryNoLocale(
            @Body @Nullable @Valid CreateCategoryRequest createCategoryRequest
    );

    /**
     * {@summary Delete Category by Locale}
     * <p><strong>WARNING: Every section and all articles in the category will also be deleted.</strong></p> <h4>Allowed for</h4> <ul>     <li>Help Center managers</li> </ul>
     *
     * @param locale     The locale the item is displayed in. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param categoryId The unique ID of the category (required)
     * @return No content (status code 204)
     */
    @Delete("/api/v2/help_center/{locale}/categories/{category_id}")
    Mono<Void> deleteCategory(
            @PathVariable("locale") @NotNull String locale,
            @PathVariable("category_id") @NotNull Long categoryId
    );

    /**
     * {@summary Delete Category}
     * <p><strong>WARNING: Every section and all articles in the category will also be deleted.</strong></p> <h4>Allowed for</h4> <ul>     <li>Help Center managers</li> </ul>
     *
     * @param categoryId The unique ID of the category (required)
     * @return <p>No content</p> (status code 204)
     */
    @Delete("/api/v2/help_center/categories/{category_id}")
    Mono<Void> deleteCategoryNoLocale(
            @PathVariable("category_id") @NotNull Long categoryId
    );

    /**
     * {@summary List Categories by Locale}
     * <h4>Allowed for</h4> <ul>     <li>Anonymous users</li> </ul> <p>The response will list only the categories that the agent, end user, or anonymous user can view in the help center.</p> <p>Translations are embedded within the category because they're not shared between resources.</p>
     *
     * @param locale    The locale the item is displayed in. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param sortBy    <p>The <code>sort_by</code> parameter can have one of the following values:</p> <table>     <thead>     <tr>         <th>value</th>         <th>description</th>     </tr>     </thead>     <tbody>     <tr>         <td><code>position</code></td>         <td>order set manually using the Arrange Content page. Default order</td>     </tr>     <tr>         <td><code>created_at</code></td>         <td>order by creation time</td>     </tr>     <tr>         <td><code>updated_at</code></td>         <td>order by update time</td>     </tr>     </tbody> </table>  (optional)
     * @param sortOrder <table>     <thead>     <tr>         <th>value</th>         <th>description</th>     </tr>     </thead>     <tbody>     <tr>         <td><code>asc</code></td>         <td>ascending order</td>     </tr>     <tr>         <td><code>desc</code></td>         <td>descending order</td>     </tr>     </tbody> </table>  (optional)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/categories")
    Mono<@Valid CategoriesResponse> listCategories(
            @PathVariable("locale") @NotNull String locale,
            @QueryValue("sort_by") @Nullable ListCategoriesSortByParameter sortBy,
            @QueryValue("sort_order") @Nullable ListArticlesSortOrderParameter sortOrder
    );

    /**
     * {@summary List Categories}
     * <h4>Allowed for</h4> <ul>     <li>Agents</li> </ul> <p>The response will list only the categories that the agent can view in the help center.</p>
     *
     * @param sortBy    <p>Sorts the results by one of the accepted values</p> (optional)
     * @param sortOrder <p>Selects the order of the results.</p> (optional)
     * @return <p>description</p> (status code 200)
     */
    @Get("/api/v2/help_center/categories")
    Mono<@Valid CategoriesResponse> listCategoriesNoLocale(
            @QueryValue("sort_by") @Nullable ListCategoriesSortByParameter sortBy,
            @QueryValue("sort_order") @Nullable ListArticlesSortOrderParameter sortOrder
    );

    /**
     * {@summary Show Category by Locale}
     * <p><strong>Note</strong>: <code>{/locale}</code> is an optional parameter for admins and agents. End users and anonymous users must provide the parameter.</p> <h4>Allowed for</h4> <ul>     <li>Anonymous users</li> </ul> <p>Translations are embedded within the category because they're not shared between resources.</p>
     *
     * @param locale     The locale the item is displayed in. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param categoryId The unique ID of the category (required)
     * @return <p>description</p> (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/categories/{category_id}")
    Mono<@Valid CategoryResponse> showCategory(
            @PathVariable("locale") @NotNull String locale,
            @PathVariable("category_id") @NotNull Long categoryId
    );

    /**
     * {@summary Show Category}
     * <h4>Allowed for</h4> <ul>     <li>Agents</li> </ul>
     *
     * @param categoryId The unique ID of the category (required)
     * @return <p>description</p> (status code 200)
     */
    @Get("/api/v2/help_center/categories/{category_id}")
    Mono<@Valid CategoryResponse> showCategoryNoLocale(
            @PathVariable("category_id") @NotNull Long categoryId
    );

    /**
     * {@summary Update Category by Locale}
     * <p>These endpoints only update category-level metadata such as the sorting position. They don't update category translations.</p> <h4>Allowed for</h4> <ul>     <li>Help Center managers</li> </ul>
     *
     * @param locale                The locale the item is displayed in. (must be lowercase, even if returned from zendesk as mixed case) (required)
     * @param categoryId            The unique ID of the category (required)
     * @param createCategoryRequest (optional)
     * @return OK Response (status code 200)
     */
    @Put("/api/v2/help_center/{locale}/categories/{category_id}")
    Mono<@Valid CategoryResponse> updateCategory(
            @PathVariable("locale") @NotNull String locale,
            @PathVariable("category_id") @NotNull Long categoryId,
            @Body @Nullable @Valid CreateCategoryRequest createCategoryRequest
    );

    /**
     * {@summary Update Category}
     * <p>These endpoints only update category-level metadata such as the sorting position. They don't update category translations.</p> <h4>Allowed for</h4> <ul>     <li>Help Center managers</li> </ul>
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
     * {@summary Update Category Source Locale by Locale}
     * <p>The endpoint updates the category <code>source_locale</code> property</p> <h4>Allowed for</h4> <ul>     <li>Agents</li> </ul>
     *
     * @param categoryId The unique ID of the category (required)
     * @return <p>OK Response</p> (status code 200)
     */
    @Put("/api/v2/help_center/categories/{category_id}/source_locale")
    Mono<@Valid CategoryResponse> updateCategorySourceLocale(
            @PathVariable("category_id") @NotNull Long categoryId
    );
}