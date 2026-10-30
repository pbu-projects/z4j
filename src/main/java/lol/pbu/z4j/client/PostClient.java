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
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.*;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Community Posts and Comments in Zendesk.</h1>
 * <ul>
 *     <li>List Posts {@link #listPosts}</li>
 *     <li>List Posts by Topic {@link #listPostsByTopic}</li>
 *     <li>Show Post {@link #showPost}</li>
 *     <li>Create Post {@link #createPost}</li>
 *     <li>Update Post {@link #updatePost}</li>
 *     <li>Delete Post {@link #deletePost}</li>
 *     <li>Search Posts {@link #searchPosts}</li>
 *     <li>List Post Comments {@link #listPostComments}</li>
 *     <li>Show Post Comment {@link #showPostComment}</li>
 *     <li>Create Post Comment {@link #createPostComment}</li>
 *     <li>Update Post Comment {@link #updatePostComment}</li>
 *     <li>Delete Post Comment {@link #deletePostComment}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.2.4
 */
@Client("zendesk")
public interface PostClient {

    /**
     * <h1>{@summary List Posts}</h1>
     * Returns all community posts.
     * <h4>Allowed for: Agents</h4>
     *
     * @return OK (status code 200)
     */
    @Get("/api/v2/community/posts")
    Mono<@Valid PostsResponse> listPosts();

    /**
     * <h1>{@summary List Posts by Topic}</h1>
     * Returns all posts for a specific topic.
     * <h4>Allowed for: Agents</h4>
     *
     * @param topicId The unique ID of the topic (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/community/topics/{topic_id}/posts")
    Mono<@Valid PostsResponse> listPostsByTopic(@PathVariable("topic_id") @NotNull Long topicId);

    /**
     * <h1>{@summary Show Post}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param postId The unique ID of the post (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/community/posts/{post_id}")
    Mono<@Valid PostResponse> showPost(@PathVariable("post_id") @NotNull Long postId);

    /**
     * <h1>{@summary Create Post}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param body {@link PostCreateRequest} (required)
     * @return Created (status code 201)
     */
    @Post("/api/v2/community/posts")
    Mono<@Valid PostResponse> createPost(@Body @NotNull @Valid PostCreateRequest body);

    /**
     * <h1>{@summary Update Post}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param postId The unique ID of the post (required)
     * @param body   {@link PostUpdateRequest} (required)
     * @return OK (status code 200)
     */
    @Put("/api/v2/community/posts/{post_id}")
    Mono<@Valid PostResponse> updatePost(
            @PathVariable("post_id") @NotNull Long postId,
            @Body @NotNull @Valid PostUpdateRequest body
    );

    /**
     * <h1>{@summary Delete Post}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param postId The unique ID of the post (required)
     * @return No content (status code 204)
     */
    @Delete("/api/v2/community/posts/{post_id}")
    Mono<Void> deletePost(@PathVariable("post_id") @NotNull Long postId);

    /**
     * <h1>{@summary Search Posts}</h1>
     * Searches for community posts matching the query.
     * <h4>Allowed for: Agents</h4>
     *
     * @param query The search query string (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/community/posts/search")
    Mono<@Valid CommunityPostSearchResponse> searchPosts(@QueryValue("query") @NotNull String query);

    /**
     * <h1>{@summary List Post Comments}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param postId The unique ID of the post (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/community/posts/{post_id}/comments")
    Mono<@Valid PostCommentsResponse> listPostComments(@PathVariable("post_id") @NotNull Long postId);

    /**
     * <h1>{@summary Show Post Comment}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param postId    The unique ID of the post (required)
     * @param commentId The unique ID of the comment (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/community/posts/{post_id}/comments/{comment_id}")
    Mono<@Valid PostCommentResponse> showPostComment(
            @PathVariable("post_id") @NotNull Long postId,
            @PathVariable("comment_id") @NotNull Long commentId
    );

    /**
     * <h1>{@summary Create Post Comment}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param postId The unique ID of the post (required)
     * @param body   {@link PostCommentCreateRequest} (required)
     * @return Created (status code 201)
     */
    @Post("/api/v2/community/posts/{post_id}/comments")
    Mono<@Valid PostCommentResponse> createPostComment(
            @PathVariable("post_id") @NotNull Long postId,
            @Body @NotNull @Valid PostCommentCreateRequest body
    );

    /**
     * <h1>{@summary Update Post Comment}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param postId    The unique ID of the post (required)
     * @param commentId The unique ID of the comment (required)
     * @param body      {@link PostCommentUpdateRequest} (required)
     * @return OK (status code 200)
     */
    @Put("/api/v2/community/posts/{post_id}/comments/{comment_id}")
    Mono<@Valid PostCommentResponse> updatePostComment(
            @PathVariable("post_id") @NotNull Long postId,
            @PathVariable("comment_id") @NotNull Long commentId,
            @Body @NotNull @Valid PostCommentUpdateRequest body
    );

    /**
     * <h1>{@summary Delete Post Comment}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param postId    The unique ID of the post (required)
     * @param commentId The unique ID of the comment (required)
     * @return No content (status code 204)
     */
    @Delete("/api/v2/community/posts/{post_id}/comments/{comment_id}")
    Mono<Void> deletePostComment(
            @PathVariable("post_id") @NotNull Long postId,
            @PathVariable("comment_id") @NotNull Long commentId
    );
}
