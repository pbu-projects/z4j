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
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.Vote;
import lol.pbu.z4j.model.VoteCreateRequest;
import lol.pbu.z4j.model.VoteResponse;
import lol.pbu.z4j.model.VotesResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Votes on Articles in Zendesk Help Center.</h1>
 * <ul>
 *     <li>List Votes on Article {@link #listVotesOnArticle}</li>
 *     <li>List Votes by User {@link #listVotesByUser}</li>
 *     <li>Show Vote {@link #showVote}</li>
 *     <li>Upvote Article {@link #upvoteArticle}</li>
 *     <li>Downvote Article {@link #downvoteArticle}</li>
 *     <li>Create Vote on Article {@link #createVoteOnArticle}</li>
 *     <li>Delete Vote {@link #deleteVote}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Retryable
@Client("zendesk")
public interface VoteClient {

    /**
     * <h1>{@summary List Votes on Article}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return Votes (status code 200)
     */
    @Get("/api/v2/help_center/articles/{article_id}/votes")
    Mono<@Valid VotesResponse> listVotesOnArticle(@PathVariable("article_id") @NotNull Long articleId);

    /**
     * <h1>{@summary List Votes by User}</h1>
     *
     * @param userId The unique ID of the user (required)
     * @return Votes (status code 200)
     */
    @Get("/api/v2/help_center/users/{user_id}/votes")
    Mono<@Valid VotesResponse> listVotesByUser(@PathVariable("user_id") @NotNull Long userId);

    /**
     * <h1>{@summary Show Vote}</h1>
     *
     * @param voteId The unique ID of the vote (required)
     * @return Vote (status code 200)
     */
    @Get("/api/v2/help_center/votes/{vote_id}")
    Mono<@Valid VoteResponse> showVote(@PathVariable("vote_id") @NotNull Long voteId);

    /**
     * <h1>{@summary Upvote Article}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return Vote (status code 200)
     */
    @Post("/api/v2/help_center/articles/{article_id}/up")
    Mono<@Valid VoteResponse> upvoteArticle(@PathVariable("article_id") @NotNull Long articleId);

    /**
     * <h1>{@summary Downvote Article}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return Vote (status code 200)
     */
    @Post("/api/v2/help_center/articles/{article_id}/down")
    Mono<@Valid VoteResponse> downvoteArticle(@PathVariable("article_id") @NotNull Long articleId);

    /**
     * <h1>{@summary Create Vote on Article}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param body      {@link VoteCreateRequest} (required)
     * @return Created Vote (status code 200)
     */
    default Mono<@Valid VoteResponse> createVoteOnArticle(
            @NotNull Long articleId,
            @NotNull @Valid VoteCreateRequest body
    ) {
        if (body.getVote() != null && body.getVote().getValue() != null && body.getVote().getValue() < 0) {
            return downvoteArticle(articleId);
        }
        return upvoteArticle(articleId);
    }

    /**
     * <h1>{@summary Delete Vote}</h1>
     *
     * @param voteId The unique ID of the vote (required)
     * @return Void (status code 204)
     */
    @Delete("/api/v2/help_center/votes/{vote_id}")
    Mono<Void> deleteVote(@PathVariable("vote_id") @NotNull Long voteId);

    /**
     * <h1>{@summary Create Vote on Article convenience method}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param value     The vote value (+1 or -1) (required)
     * @return Created Vote (status code 200)
     */
    default Mono<@Valid VoteResponse> createVoteOnArticle(Long articleId, Integer value) {
        return createVoteOnArticle(articleId, new VoteCreateRequest(value));
    }

    /**
     * <h1>{@summary Create Vote on Article convenience method}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param vote      {@link Vote} (required)
     * @return Created Vote (status code 200)
     */
    default Mono<@Valid VoteResponse> createVoteOnArticle(Long articleId, Vote vote) {
        return createVoteOnArticle(articleId, new VoteCreateRequest(vote));
    }

}
