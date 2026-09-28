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

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.Comment;
import lol.pbu.z4j.model.CommentCreateRequest;
import lol.pbu.z4j.model.CommentResponse;
import lol.pbu.z4j.model.CommentUpdateRequest;
import lol.pbu.z4j.model.CommentsResponse;
import lol.pbu.z4j.model.LocaleAbbreviation;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Article Comments in Zendesk Help Center.</h1>
 * <ul>
 *     <li>List Comments on Article {@link #listCommentsOnArticle}</li>
 *     <li>List Comments on Article (Default Locale) {@link #listCommentsOnArticleNoLocale}</li>
 *     <li>List Comments by User {@link #listCommentsByUser}</li>
 *     <li>Show Comment {@link #showComment}</li>
 *     <li>Show Comment (Default Locale) {@link #showCommentNoLocale}</li>
 *     <li>Create Comment {@link #createComment}</li>
 *     <li>Create Comment (Default Locale) {@link #createCommentNoLocale}</li>
 *     <li>Update Comment {@link #updateComment}</li>
 *     <li>Update Comment (Default Locale) {@link #updateCommentNoLocale}</li>
 *     <li>Delete Comment {@link #deleteComment}</li>
 *     <li>Delete Comment (Default Locale) {@link #deleteCommentNoLocale}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Retryable
@Client("zendesk")
public interface ArticleCommentClient {

    /**
     * <h1>{@summary List Comments on Article}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @return Comments (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/articles/{article_id}/comments")
    Mono<@Valid CommentsResponse> listCommentsOnArticle(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary List Comments on Article (Default Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return Comments (status code 200)
     */
    @Get("/api/v2/help_center/articles/{article_id}/comments")
    Mono<@Valid CommentsResponse> listCommentsOnArticleNoLocale(
            @PathVariable("article_id") @NotNull Long articleId
    );

    /**
     * <h1>{@summary List Comments by User}</h1>
     *
     * @param userId The unique ID of the user (required)
     * @return Comments (status code 200)
     */
    @Get("/api/v2/help_center/users/{user_id}/comments")
    Mono<@Valid CommentsResponse> listCommentsByUser(
            @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * <h1>{@summary Show Comment}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param commentId          The unique ID of the comment (required)
     * @return Comment (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/articles/{article_id}/comments/{comment_id}")
    Mono<@Valid CommentResponse> showComment(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId,
            @PathVariable("comment_id") @NotNull Long commentId
    );

    /**
     * <h1>{@summary Show Comment (Default Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param commentId The unique ID of the comment (required)
     * @return Comment (status code 200)
     */
    @Get("/api/v2/help_center/articles/{article_id}/comments/{comment_id}")
    Mono<@Valid CommentResponse> showCommentNoLocale(
            @PathVariable("article_id") @NotNull Long articleId,
            @PathVariable("comment_id") @NotNull Long commentId
    );

    /**
     * <h1>{@summary Create Comment}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param body               {@link CommentCreateRequest} (required)
     * @return Created Comment (status code 201)
     */
    @Post("/api/v2/help_center/{locale}/articles/{article_id}/comments")
    Mono<@Valid CommentResponse> createComment(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId,
            @Body @NotNull @Valid CommentCreateRequest body
    );

    /**
     * <h1>{@summary Create Comment (Default Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param body      {@link CommentCreateRequest} (required)
     * @return Created Comment (status code 201)
     */
    @Post("/api/v2/help_center/articles/{article_id}/comments")
    Mono<@Valid CommentResponse> createCommentNoLocale(
            @PathVariable("article_id") @NotNull Long articleId,
            @Body @NotNull @Valid CommentCreateRequest body
    );

    /**
     * <h1>{@summary Update Comment}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param commentId          The unique ID of the comment (required)
     * @param body               {@link CommentUpdateRequest} (required)
     * @return Updated Comment (status code 200)
     */
    @Put("/api/v2/help_center/{locale}/articles/{article_id}/comments/{comment_id}")
    Mono<@Valid CommentResponse> updateComment(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId,
            @PathVariable("comment_id") @NotNull Long commentId,
            @Body @NotNull @Valid CommentUpdateRequest body
    );

    /**
     * <h1>{@summary Update Comment (Default Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param commentId The unique ID of the comment (required)
     * @param body      {@link CommentUpdateRequest} (required)
     * @return Updated Comment (status code 200)
     */
    @Put("/api/v2/help_center/articles/{article_id}/comments/{comment_id}")
    Mono<@Valid CommentResponse> updateCommentNoLocale(
            @PathVariable("article_id") @NotNull Long articleId,
            @PathVariable("comment_id") @NotNull Long commentId,
            @Body @NotNull @Valid CommentUpdateRequest body
    );

    /**
     * <h1>{@summary Delete Comment}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param commentId          The unique ID of the comment (required)
     * @return Void (status code 204)
     */
    @Delete("/api/v2/help_center/{locale}/articles/{article_id}/comments/{comment_id}")
    Mono<Void> deleteComment(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("article_id") @NotNull Long articleId,
            @PathVariable("comment_id") @NotNull Long commentId
    );

    /**
     * <h1>{@summary Delete Comment (Default Locale)}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param commentId The unique ID of the comment (required)
     * @return Void (status code 204)
     */
    @Delete("/api/v2/help_center/articles/{article_id}/comments/{comment_id}")
    Mono<Void> deleteCommentNoLocale(
            @PathVariable("article_id") @NotNull Long articleId,
            @PathVariable("comment_id") @NotNull Long commentId
    );

    /**
     * <h1>{@summary Create Comment convenience method}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param comment            {@link Comment} (required)
     * @return Created Comment (status code 201)
     */
    default Mono<@Valid CommentResponse> createComment(
            LocaleAbbreviation localeAbbreviation,
            Long articleId,
            Comment comment
    ) {
        return createComment(localeAbbreviation, articleId, new CommentCreateRequest(comment));
    }

    /**
     * <h1>{@summary Create Comment (Default Locale) convenience method}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param comment   {@link Comment} (required)
     * @return Created Comment (status code 201)
     */
    default Mono<@Valid CommentResponse> createCommentNoLocale(
            Long articleId,
            Comment comment
    ) {
        return createCommentNoLocale(articleId, new CommentCreateRequest(comment));
    }

    /**
     * <h1>{@summary Update Comment convenience method}</h1>
     *
     * @param localeAbbreviation The locale in which the article is displayed (required)
     * @param articleId          The unique ID of the article (required)
     * @param commentId          The unique ID of the comment (required)
     * @param comment            {@link Comment} (required)
     * @return Updated Comment (status code 200)
     */
    default Mono<@Valid CommentResponse> updateComment(
            LocaleAbbreviation localeAbbreviation,
            Long articleId,
            Long commentId,
            Comment comment
    ) {
        return updateComment(localeAbbreviation, articleId, commentId, new CommentUpdateRequest(comment));
    }

    /**
     * <h1>{@summary Update Comment (Default Locale) convenience method}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param commentId The unique ID of the comment (required)
     * @param comment   {@link Comment} (required)
     * @return Updated Comment (status code 200)
     */
    default Mono<@Valid CommentResponse> updateCommentNoLocale(
            Long articleId,
            Long commentId,
            Comment comment
    ) {
        return updateCommentNoLocale(articleId, commentId, new CommentUpdateRequest(comment));
    }

}
