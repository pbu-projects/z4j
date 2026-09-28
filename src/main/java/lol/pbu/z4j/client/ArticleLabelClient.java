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
import lol.pbu.z4j.model.Label;
import lol.pbu.z4j.model.LabelCreateRequest;
import lol.pbu.z4j.model.LabelResponse;
import lol.pbu.z4j.model.LabelsResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Article Labels in Zendesk Help Center.</h1>
 * <ul>
 *     <li>List All Labels {@link #listLabels}</li>
 *     <li>List Article Labels {@link #listLabelsOnArticle}</li>
 *     <li>Show Label {@link #showLabel}</li>
 *     <li>Create Label {@link #createLabel}</li>
 *     <li>Delete Label {@link #deleteLabel}</li>
 *     <li>Delete Label from Article {@link #deleteLabelFromArticle}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Retryable
@Client("zendesk")
public interface ArticleLabelClient {

    /**
     * <h1>{@summary List All Labels}</h1>
     *
     * @return Labels (status code 200)
     */
    @Get("/api/v2/help_center/articles/labels")
    Mono<@Valid LabelsResponse> listLabels();

    /**
     * <h1>{@summary List Article Labels}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @return Labels (status code 200)
     */
    @Get("/api/v2/help_center/articles/{article_id}/labels")
    Mono<@Valid LabelsResponse> listLabelsOnArticle(@PathVariable("article_id") @NotNull Long articleId);

    /**
     * <h1>{@summary Show Label}</h1>
     *
     * @param labelId The unique ID of the label (required)
     * @return Label (status code 200)
     */
    @Get("/api/v2/help_center/articles/labels/{label_id}")
    Mono<@Valid LabelResponse> showLabel(@PathVariable("label_id") @NotNull Long labelId);

    /**
     * <h1>{@summary Create Label}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param body      {@link LabelCreateRequest} (required)
     * @return Created Label (status code 201)
     */
    @Post("/api/v2/help_center/articles/{article_id}/labels")
    Mono<@Valid LabelResponse> createLabel(
            @PathVariable("article_id") @NotNull Long articleId,
            @Body @NotNull @Valid LabelCreateRequest body
    );

    /**
     * <h1>{@summary Delete Label}</h1>
     *
     * @param labelId The unique ID of the label (required)
     * @return Void (status code 204)
     */
    @Delete("/api/v2/help_center/articles/labels/{label_id}")
    Mono<Void> deleteLabel(@PathVariable("label_id") @NotNull Long labelId);

    /**
     * <h1>{@summary Delete Label from Article}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param labelId   The unique ID of the label (required)
     * @return Void (status code 204)
     */
    @Delete("/api/v2/help_center/articles/{article_id}/labels/{label_id}")
    Mono<Void> deleteLabelFromArticle(
            @PathVariable("article_id") @NotNull Long articleId,
            @PathVariable("label_id") @NotNull Long labelId
    );

    /**
     * <h1>{@summary Create Label convenience method}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param name      The name of the label (required)
     * @return Created Label (status code 201)
     */
    default Mono<@Valid LabelResponse> createLabel(Long articleId, String name) {
        return createLabel(articleId, new LabelCreateRequest(name));
    }

    /**
     * <h1>{@summary Create Label convenience method}</h1>
     *
     * @param articleId The unique ID of the article (required)
     * @param label     {@link Label} (required)
     * @return Created Label (status code 201)
     */
    default Mono<@Valid LabelResponse> createLabel(Long articleId, Label label) {
        return createLabel(articleId, new LabelCreateRequest(label));
    }

}
