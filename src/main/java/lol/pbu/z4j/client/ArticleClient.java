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
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.ArticleCreateRequest;
import lol.pbu.z4j.model.ArticleResponse;
import lol.pbu.z4j.model.ArticleUpdateRequest;
import lol.pbu.z4j.model.ArticlesResponse;
import lol.pbu.z4j.model.BulkAttachmentsRequest;
import lol.pbu.z4j.model.LocaleAbbreviation;
import lol.pbu.z4j.model.SortArticleBy;
import lol.pbu.z4j.model.SortOrder;
import reactor.core.publisher.Mono;

/**
 * <h1>{@summary Work with Articles in Zendesk.}</h1>
 *
 * <ul>
 *     <li> List Articles by a given locale with {@link #listArticles}</li>
 *     <li> List Articles across all locales with {@link #listArticlesNoLocale}</li>
 *     <li> Show Article by a given locale or ID with {@link #showArticle}</li>
 *     <li> Create Article with {@link #createArticle}</li>
 *     <li> Update Article with {@link #updateArticle}</li>
 *     <li> Delete Article with {@link #deleteArticle}</li>
 *     <li> Associate Attachments In Bulk with {@link #associateAttachmentsInBulk}</li>
 *     <li> Archive Article with {@link #archiveArticle}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */

@Client("zendesk")
public interface ArticleClient {

    /**
     * <h1>{@summary List Articles}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed. (must be lowercase, even if zendesk returns a mixed case response) (required)
     * @return OK Response (status code 200)
     */
    default Mono<@Valid ArticlesResponse> listArticles(@NotNull LocaleAbbreviation localeAbbreviation) {
        return listArticles(localeAbbreviation, null, null, null, null);
    }

    /**
     * <h1>{@summary List Articles}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed. (must be lowercase, even if zendesk returns a mixed case response) (required)
     * @param sortBy             Sorts the articles by one of the accepted values (optional)
     * @param sortOrder          Selects the order of the results. (optional)
     * @param startTime          You can use the incremental article endpoint to list all the articles that were updated since a certain date and time.
     *                           This time is given as a <a href='https://www.epochconverter.com/'>Unix epoch timeStamp</a>  (optional)
     * @param labelNames         Only articles that have all the labels are returned. Multiple labels can be passed in a comma-delimited string (optional)
     * @return OK Response (status code 200)
     */
    default Mono<@Valid ArticlesResponse> listArticles(
            @NotNull LocaleAbbreviation localeAbbreviation,
            @Nullable SortArticleBy sortBy,
            @Nullable SortOrder sortOrder,
            @Nullable Long startTime,
            @Nullable String labelNames
    ) {
        return listArticles(localeAbbreviation, sortBy, sortOrder, startTime, labelNames, null, null, null, null, null);
    }

    /**
     * <h1>{@summary List Articles with Cursor Pagination}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed. (must be lowercase, even if zendesk returns a mixed case response) (required)
     * @param pageAfter          Cursor token used to fetch the next page when cursor pagination is active (optional)
     * @param pageSize           Number of results per page. Required to activate cursor pagination. Max 100. (optional)
     * @return OK Response (status code 200)
     */
    default Mono<@Valid ArticlesResponse> listArticles(
            @NotNull LocaleAbbreviation localeAbbreviation,
            @Nullable String pageAfter,
            @Nullable @Max(100) Integer pageSize
    ) {
        return listArticles(localeAbbreviation, null, null, null, null, null, pageAfter, pageSize, null, null);
    }

    /**
     * <h1>{@summary List Articles with Offset Pagination}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed. (must be lowercase, even if zendesk returns a mixed case response) (required)
     * @param page               Page number for offset pagination (optional)
     * @param perPage            Number of results per page for offset pagination. Max 100. (optional)
     * @return OK Response (status code 200)
     */
    default Mono<@Valid ArticlesResponse> listArticles(
            @NotNull LocaleAbbreviation localeAbbreviation,
            @Nullable Integer page,
            @Nullable @Max(100) Integer perPage
    ) {
        return listArticles(localeAbbreviation, null, null, null, null, null, null, null, page, perPage);
    }

    /**
     * <h1>{@summary List Articles with Cursor Pagination}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed. (must be lowercase, even if zendesk returns a mixed case response) (required)
     * @param sortBy             Sorts the articles by one of the accepted values (optional)
     * @param sortOrder          Selects the order of the results. (optional)
     * @param startTime          You can use the incremental article endpoint to list all the articles that were updated since a certain date and time.
     *                           This time is given as a <a href='https://www.epochconverter.com/'>Unix epoch timeStamp</a>  (optional)
     * @param labelNames         Only articles that have all the labels are returned. Multiple labels can be passed in a comma-delimited string (optional)
     * @param pageAfter          Cursor token used to fetch the next page when cursor pagination is active (optional)
     * @param pageSize           Number of results per page. Required to activate cursor pagination. Max 100. (optional)
     * @return OK Response (status code 200)
     */
    default Mono<@Valid ArticlesResponse> listArticles(
            @NotNull LocaleAbbreviation localeAbbreviation,
            @Nullable SortArticleBy sortBy,
            @Nullable SortOrder sortOrder,
            @Nullable Long startTime,
            @Nullable String labelNames,
            @Nullable String pageAfter,
            @Nullable @Max(100) Integer pageSize
    ) {
        return listArticles(localeAbbreviation, sortBy, sortOrder, startTime, labelNames, null, pageAfter, pageSize, null, null);
    }

    /**
     * <h1>{@summary List Articles}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed. (must be lowercase, even if zendesk returns a mixed case response) (required)
     * @param sortBy             Sorts the articles by one of the accepted values (optional)
     * @param sortOrder          Selects the order of the results. (optional)
     * @param startTime          You can use the incremental article endpoint to list all the articles that were updated since a certain date and time.
     *                           This time is given as a <a href='https://www.epochconverter.com/'>Unix epoch timeStamp</a>  (optional)
     * @param labelNames         Only articles that have all the labels are returned. Multiple labels can be passed in a comma-delimited string (optional)
     * @param pageBefore         Cursor token used to fetch the previous page when cursor pagination is active (optional)
     * @param pageAfter          Cursor token used to fetch the next page when cursor pagination is active (optional)
     * @param pageSize           Number of results per page. Required to activate cursor pagination. Max 100. (optional)
     * @param page               Page number for offset pagination (optional)
     * @param perPage            Number of results per page for offset pagination. Max 100. (optional)
     * @return OK Response (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/articles")
    Mono<@Valid ArticlesResponse> listArticles(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @QueryValue("sort_by") @Nullable SortArticleBy sortBy,
            @QueryValue("sort_order") @Nullable SortOrder sortOrder,
            @QueryValue("start_time") @Nullable Long startTime,
            @QueryValue("label_names") @Nullable String labelNames,
            @QueryValue("page[before]") @Nullable String pageBefore,
            @QueryValue("page[after]") @Nullable String pageAfter,
            @QueryValue("page[size]") @Nullable @Max(100) Integer pageSize,
            @QueryValue("page") @Nullable Integer page,
            @QueryValue("per_page") @Nullable @Max(100) Integer perPage
    );

    /**
     * <h1>{@summary List Articles (No Locale)}</h1>
     *
     * @return OK Response (status code 200)
     */
    default Mono<@Valid ArticlesResponse> listArticlesNoLocale() {
        return listArticlesNoLocale(null, null, null, null, null, null, null, null, null);
    }

    /**
     * <h1>{@summary List Articles (No Locale)}</h1>
     *
     * @param sortBy     Sorts the articles by one of the accepted values (optional)
     * @param sortOrder  Selects the order of the results. (optional)
     * @param startTime  Incremental start time timestamp (optional)
     * @param labelNames Comma-delimited list of label names (optional)
     * @return OK Response (status code 200)
     */
    default Mono<@Valid ArticlesResponse> listArticlesNoLocale(
            @Nullable SortArticleBy sortBy,
            @Nullable SortOrder sortOrder,
            @Nullable Long startTime,
            @Nullable String labelNames
    ) {
        return listArticlesNoLocale(sortBy, sortOrder, startTime, labelNames, null, null, null, null, null);
    }

    /**
     * <h1>{@summary List Articles (No Locale) with Cursor Pagination}</h1>
     *
     * @param pageAfter Cursor token used to fetch the next page when cursor pagination is active (optional)
     * @param pageSize  Number of results per page. Required to activate cursor pagination. Max 100. (optional)
     * @return OK Response (status code 200)
     */
    default Mono<@Valid ArticlesResponse> listArticlesNoLocale(
            @Nullable String pageAfter,
            @Nullable @Max(100) Integer pageSize
    ) {
        return listArticlesNoLocale(null, null, null, null, null, pageAfter, pageSize, null, null);
    }

    /**
     * <h1>{@summary List Articles (No Locale) with Offset Pagination}</h1>
     *
     * @param page    Page number for offset pagination (optional)
     * @param perPage Number of results per page for offset pagination. Max 100. (optional)
     * @return OK Response (status code 200)
     */
    default Mono<@Valid ArticlesResponse> listArticlesNoLocale(
            @Nullable Integer page,
            @Nullable @Max(100) Integer perPage
    ) {
        return listArticlesNoLocale(null, null, null, null, null, null, null, page, perPage);
    }

    /**
     * <h1>{@summary List Articles (No Locale) with Cursor Pagination}</h1>
     *
     * @param sortBy     Sorts the articles by one of the accepted values (optional)
     * @param sortOrder  Selects the order of the results. (optional)
     * @param startTime  Incremental start time timestamp (optional)
     * @param labelNames Comma-delimited list of label names (optional)
     * @param pageAfter  Cursor token used to fetch the next page when cursor pagination is active (optional)
     * @param pageSize   Number of results per page. Required to activate cursor pagination. Max 100. (optional)
     * @return OK Response (status code 200)
     */
    default Mono<@Valid ArticlesResponse> listArticlesNoLocale(
            @Nullable SortArticleBy sortBy,
            @Nullable SortOrder sortOrder,
            @Nullable Long startTime,
            @Nullable String labelNames,
            @Nullable String pageAfter,
            @Nullable @Max(100) Integer pageSize
    ) {
        return listArticlesNoLocale(sortBy, sortOrder, startTime, labelNames, null, pageAfter, pageSize, null, null);
    }

    /**
     * <h1>{@summary List Articles (No Locale)}</h1>
     *
     * @param sortBy     Sorts the articles by one of the accepted values (optional)
     * @param sortOrder  Selects the order of the results. (optional)
     * @param startTime  Incremental start time timestamp (optional)
     * @param labelNames Comma-delimited list of label names (optional)
     * @param pageBefore Cursor token used to fetch the previous page when cursor pagination is active (optional)
     * @param pageAfter  Cursor token used to fetch the next page when cursor pagination is active (optional)
     * @param pageSize   Number of results per page. Required to activate cursor pagination. Max 100. (optional)
     * @param page       Page number for offset pagination (optional)
     * @param perPage    Number of results per page for offset pagination. Max 100. (optional)
     * @return OK Response (status code 200)
     */
    @Get("/api/v2/help_center/articles")
    Mono<@Valid ArticlesResponse> listArticlesNoLocale(
            @QueryValue("sort_by") @Nullable SortArticleBy sortBy,
            @QueryValue("sort_order") @Nullable SortOrder sortOrder,
            @QueryValue("start_time") @Nullable Long startTime,
            @QueryValue("label_names") @Nullable String labelNames,
            @QueryValue("page[before]") @Nullable String pageBefore,
            @QueryValue("page[after]") @Nullable String pageAfter,
            @QueryValue("page[size]") @Nullable @Max(100) Integer pageSize,
            @QueryValue("page") @Nullable Integer page,
            @QueryValue("per_page") @Nullable @Max(100) Integer perPage
    );

    /**
     * <h1>{@summary Create Article}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param sectionId          The ID of the section to which the article belongs (required)
     * @param body               {@link ArticleCreateRequest} (required)
     * @return Created (status code 201)
     */
    @Post("/api/v2/help_center/{locale}/sections/{section_id}/articles")
    Mono<@Valid ArticleResponse> createArticle(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("section_id") @NotNull Long sectionId,
            @Body @NotNull @Valid ArticleCreateRequest body
    );

    /**
     * <h1>{@summary Update Article}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param body               {@link ArticleUpdateRequest} (required)
     * @return OK (status code 200)
     */
    @Put("/api/v2/help_center/{locale}/articles/{article_id}")
    Mono<@Valid ArticleResponse> updateArticle(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId,
            @Body @NotNull @Valid ArticleUpdateRequest body
    );

    /**
     * <h1>{@summary Update Article}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param body      {@link ArticleUpdateRequest} (required)
     * @return OK (status code 200)
     */
    @Put("/api/v2/help_center/articles/{article_id}")
    Mono<@Valid ArticleResponse> updateArticleNoLocale(
            @PathVariable("article_id") @NotNull Long articleId,
            @Body @NotNull @Valid ArticleUpdateRequest body
    );

    /**
     * <h1>{@summary Update Article}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param body      {@link ArticleUpdateRequest} (required)
     * @return OK (status code 200)
     */
    default Mono<@Valid ArticleResponse> updateArticle(
            @NotNull Long articleId,
            @NotNull @Valid ArticleUpdateRequest body
    ) {
        return updateArticleNoLocale(articleId, body);
    }

    /**
     * <h1>{@summary Show Article}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/articles/{article_id}")
    Mono<@Valid ArticleResponse> showArticle(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary Show Article}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/articles/{article_id}")
    Mono<@Valid ArticleResponse> showArticleNoLocale(
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary Show Article}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return OK (status code 200)
     */
    default Mono<@Valid ArticleResponse> showArticle(@NotNull Long articleId) {
        return showArticleNoLocale(articleId);
    }

    /**
     * <h1>{@summary Delete Article}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @return No Content (status code 204)
     */
    @Delete("/api/v2/help_center/{locale}/articles/{article_id}")
    Mono<Void> deleteArticle(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary Associate Attachments In Bulk}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param body               {@link BulkAttachmentsRequest} (required)
     * @return No Content (status code 204)
     */
    @Post("/api/v2/help_center/{locale}/articles/{article_id}/bulk_attachments")
    Mono<Void> associateAttachmentsInBulk(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId,
            @Body @NotNull @Valid BulkAttachmentsRequest body
    );

    /**
     * <h1>{@summary Associate Attachments In Bulk (No Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param body      {@link BulkAttachmentsRequest} (required)
     * @return No Content (status code 204)
     */
    @Post("/api/v2/help_center/articles/{article_id}/bulk_attachments")
    Mono<Void> associateAttachmentsInBulkNoLocale(
            @PathVariable("article_id") @NotNull Long articleId,
            @Body @NotNull @Valid BulkAttachmentsRequest body
    );

    /**
     * <h1>{@summary Associate Attachments In Bulk}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param body      {@link BulkAttachmentsRequest} (required)
     * @return No Content (status code 204)
     */
    default Mono<Void> associateAttachmentsInBulk(
            @NotNull Long articleId,
            @NotNull @Valid BulkAttachmentsRequest body
    ) {
        return associateAttachmentsInBulkNoLocale(articleId, body);
    }

    /**
     * <h1>{@summary Delete Article (No Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return No Content (status code 204)
     */
    @Delete("/api/v2/help_center/articles/{article_id}")
    Mono<Void> deleteArticleNoLocale(@PathVariable("article_id") @NotNull Long articleId);

    /**
     * <h1>{@summary Delete Article}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return No Content (status code 204)
     */
    default Mono<Void> deleteArticle(@NotNull Long articleId) {
        return deleteArticleNoLocale(articleId);
    }

    /**
     * <h1>{@summary Archive Article}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @return No Content (status code 204)
     */
    default Mono<Void> archiveArticle(@NotNull LocaleAbbreviation localeAbbreviation, @NotNull Long articleId) {
        return deleteArticle(localeAbbreviation, articleId);
    }

    /**
     * <h1>{@summary Archive Article (No Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return No Content (status code 204)
     */
    default Mono<Void> archiveArticle(@NotNull Long articleId) {
        return deleteArticleNoLocale(articleId);
    }
}
