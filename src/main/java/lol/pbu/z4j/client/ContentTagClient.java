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
import lol.pbu.z4j.model.ContentTagCountResponse;
import lol.pbu.z4j.model.ContentTagCreateRequest;
import lol.pbu.z4j.model.ContentTagResponse;
import lol.pbu.z4j.model.ContentTagsResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Content Tags in Zendesk Guide.</h1>
 * <ul>
 *     <li>Search Content Tags {@link #searchContentTags}</li>
 *     <li>Count Content Tags {@link #countContentTags}</li>
 *     <li>Show Content Tag {@link #showContentTag}</li>
 *     <li>Create Content Tag {@link #createContentTag}</li>
 *     <li>Update Content Tag {@link #updateContentTag}</li>
 *     <li>Delete Content Tag {@link #deleteContentTag}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Retryable
@Client("zendesk")
public interface ContentTagClient {

    /**
     * <h1>{@summary Search Content Tags}</h1>
     *
     * @return Content tags response (status code 200)
     */
    @Get("/api/v2/guide/content_tags")
    Mono<@Valid ContentTagsResponse> searchContentTags();

    /**
     * <h1>{@summary Count Content Tags}</h1>
     *
     * @return Content tag count response (status code 200)
     */
    @Get("/api/v2/guide/content_tags/count")
    Mono<@Valid ContentTagCountResponse> countContentTags();

    /**
     * <h1>{@summary Show Content Tag}</h1>
     *
     * @param id The unique ULID identifier of the content tag (required)
     * @return Content tag response (status code 200)
     */
    @Get("/api/v2/guide/content_tags/{id}")
    Mono<@Valid ContentTagResponse> showContentTag(
            @PathVariable("id") @NotNull String id
    );

    /**
     * <h1>{@summary Create Content Tag}</h1>
     *
     * @param body The creation request body (required)
     * @return Created content tag response (status code 201)
     */
    @Post("/api/v2/guide/content_tags")
    Mono<@Valid ContentTagResponse> createContentTag(
            @Body @NotNull @Valid ContentTagCreateRequest body
    );

    /**
     * <h1>{@summary Update Content Tag}</h1>
     *
     * @param id   The unique ULID identifier of the content tag (required)
     * @param body The update request body (required)
     * @return Updated content tag response (status code 200)
     */
    @Put("/api/v2/guide/content_tags/{id}")
    Mono<@Valid ContentTagResponse> updateContentTag(
            @PathVariable("id") @NotNull String id,
            @Body @NotNull @Valid ContentTagCreateRequest body
    );

    /**
     * <h1>{@summary Delete Content Tag}</h1>
     *
     * @param id The unique ULID identifier of the content tag (required)
     * @return Void response (status code 204)
     */
    @Delete("/api/v2/guide/content_tags/{id}")
    Mono<Void> deleteContentTag(
            @PathVariable("id") @NotNull String id
    );
}
