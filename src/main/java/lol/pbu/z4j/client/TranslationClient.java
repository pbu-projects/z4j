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
import lol.pbu.z4j.model.LocaleAbbreviation;
import lol.pbu.z4j.model.TranslationCreateRequest;
import lol.pbu.z4j.model.TranslationResponse;
import lol.pbu.z4j.model.TranslationUpdateRequest;
import lol.pbu.z4j.model.TranslationsResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>{@summary Work with Translations in Zendesk Help Center.}</h1>
 *
 * <p>Supports listing, showing, creating, updating, and deleting translations
 * for Help Center resources (such as articles, sections, and categories).</p>
 *
 * @author Jonathan-Zollinger
 * @since 0.2.5
 */
@Retryable
@Client("zendesk")
public interface TranslationClient {

    /**
     * <h1>{@summary List Translations}</h1>
     *
     * @param resourceType The resource type (e.g. "articles", "sections", "categories") (required)
     * @param resourceId   The ID of the parent resource (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/{resource_type}/{resource_id}/translations")
    Mono<@Valid TranslationsResponse> listTranslations(
            @PathVariable("resource_type") @NotNull String resourceType,
            @PathVariable("resource_id") @NotNull Long resourceId
    );

    /**
     * <h1>{@summary Show Translation}</h1>
     *
     * @param resourceType The resource type (e.g. "articles", "sections", "categories") (required)
     * @param resourceId   The ID of the parent resource (required)
     * @param locale       The locale of the translation (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/{resource_type}/{resource_id}/translations/{locale}")
    Mono<@Valid TranslationResponse> showTranslation(
            @PathVariable("resource_type") @NotNull String resourceType,
            @PathVariable("resource_id") @NotNull Long resourceId,
            @PathVariable("locale") @NotNull LocaleAbbreviation locale
    );

    /**
     * <h1>{@summary Create Translation}</h1>
     *
     * @param resourceType The resource type (e.g. "articles", "sections", "categories") (required)
     * @param resourceId   The ID of the parent resource (required)
     * @param body         {@link TranslationCreateRequest} (required)
     * @return Created (status code 201)
     */
    @Post("/api/v2/help_center/{resource_type}/{resource_id}/translations")
    Mono<@Valid TranslationResponse> createTranslation(
            @PathVariable("resource_type") @NotNull String resourceType,
            @PathVariable("resource_id") @NotNull Long resourceId,
            @Body @NotNull @Valid TranslationCreateRequest body
    );

    /**
     * <h1>{@summary Update Translation}</h1>
     *
     * @param resourceType The resource type (e.g. "articles", "sections", "categories") (required)
     * @param resourceId   The ID of the parent resource (required)
     * @param locale       The locale of the translation to update (required)
     * @param body         {@link TranslationUpdateRequest} (required)
     * @return OK (status code 200)
     */
    @Put("/api/v2/help_center/{resource_type}/{resource_id}/translations/{locale}")
    Mono<@Valid TranslationResponse> updateTranslation(
            @PathVariable("resource_type") @NotNull String resourceType,
            @PathVariable("resource_id") @NotNull Long resourceId,
            @PathVariable("locale") @NotNull LocaleAbbreviation locale,
            @Body @NotNull @Valid TranslationUpdateRequest body
    );

    /**
     * <h1>{@summary Delete Translation}</h1>
     *
     * @param translationId The unique ID of the translation (required)
     * @return No Content (status code 204)
     */
    @Delete("/api/v2/help_center/translations/{translation_id}")
    Mono<Void> deleteTranslation(
            @PathVariable("translation_id") @NotNull Long translationId
    );
}
