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
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.ArticlesResponse;
import lol.pbu.z4j.model.ArticleResponse;
import lol.pbu.z4j.model.ArticleCreateRequest;
import lol.pbu.z4j.model.ArticleUpdateRequest;
import lol.pbu.z4j.model.LocaleAbbreviation;
import lol.pbu.z4j.model.SortArticleBy;
import lol.pbu.z4j.model.SortOrder;
import reactor.core.publisher.Mono;

/**
 * <h1>{@summary Work with Articles in Zendesk.}</h1>
 *
 * <ul><li> List Articles by a given locale with {@link #listArticles}</li></ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */

@Retryable
@Client("zendesk")
public interface ArticleClient {

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
    @Get("/api/v2/help_center/{locale}/articles")
    Mono<@Valid ArticlesResponse> listArticles(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @QueryValue("sort_by") @Nullable SortArticleBy sortBy,
            @QueryValue("sort_order") @Nullable SortOrder sortOrder,
            @QueryValue("start_time") @Nullable Long startTime,
            @QueryValue("label_names") @Nullable String labelNames
    );

    /**
     * <h1>{@summary Create Article}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param sectionId          The ID of the section to which the article belongs (required)
     * @param body               {@link ArticleCreateRequest} (required)
     * @return Created (status code 201)
     */
    @io.micronaut.http.annotation.Post("/api/v2/help_center/{locale}/sections/{section_id}/articles")
    Mono<@Valid ArticleResponse> createArticle(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("section_id") @NotNull Long sectionId,
            @io.micronaut.http.annotation.Body @NotNull @Valid ArticleCreateRequest body
    );

    /**
     * <h1>{@summary Update Article}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param body               {@link ArticleUpdateRequest} (required)
     * @return OK (status code 200)
     */
    @io.micronaut.http.annotation.Put("/api/v2/help_center/{locale}/articles/{article_id}")
    Mono<@Valid ArticleResponse> updateArticle(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId,
            @io.micronaut.http.annotation.Body @NotNull @Valid ArticleUpdateRequest body
    );

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
     * <h1>{@summary Delete Article}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @return No Content (status code 204)
     */
    @io.micronaut.http.annotation.Delete("/api/v2/help_center/{locale}/articles/{article_id}")
    Mono<Void> deleteArticle(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId
    );
}
