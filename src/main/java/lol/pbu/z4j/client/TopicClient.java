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
 * <h1>Work with Community Topics in Zendesk.</h1>
 * <ul>
 *     <li>List Topics {@link #listTopics}</li>
 *     <li>Show Topic {@link #showTopic}</li>
 *     <li>Create Topic {@link #createTopic}</li>
 *     <li>Update Topic {@link #updateTopic}</li>
 *     <li>Delete Topic {@link #deleteTopic}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.2.4
 */
@Client("zendesk")
public interface TopicClient {

    /**
     * <h1>{@summary List Topics}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @return OK (status code 200)
     */
    @Get("/api/v2/community/topics")
    Mono<@Valid TopicsResponse> listTopics();

    /**
     * <h1>{@summary Show Topic}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param topicId The unique ID of the topic (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/community/topics/{topic_id}")
    Mono<@Valid TopicResponse> showTopic(@PathVariable("topic_id") @NotNull Long topicId);

    /**
     * <h1>{@summary Create Topic}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param body {@link TopicCreateRequest} (required)
     * @return Created (status code 201)
     */
    @Post("/api/v2/community/topics")
    Mono<@Valid TopicResponse> createTopic(@Body @NotNull @Valid TopicCreateRequest body);

    /**
     * <h1>{@summary Update Topic}</h1>
     * <h4>Allowed for: Agents</h4>
     *
     * @param topicId The unique ID of the topic (required)
     * @param body    {@link TopicUpdateRequest} (required)
     * @return OK (status code 200)
     */
    @Put("/api/v2/community/topics/{topic_id}")
    Mono<@Valid TopicResponse> updateTopic(
            @PathVariable("topic_id") @NotNull Long topicId,
            @Body @NotNull @Valid TopicUpdateRequest body
    );

    /**
     * <h1>{@summary Delete Topic}</h1>
     * <p><strong>WARNING: All posts in the topic will also be deleted.</strong></p>
     * <h4>Allowed for: Agents</h4>
     *
     * @param topicId The unique ID of the topic (required)
     * @return No content (status code 204)
     */
    @Delete("/api/v2/community/topics/{topic_id}")
    Mono<Void> deleteTopic(@PathVariable("topic_id") @NotNull Long topicId);
}
