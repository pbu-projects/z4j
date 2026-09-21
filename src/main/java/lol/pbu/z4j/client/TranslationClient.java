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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.TranslationResponse;
import lol.pbu.z4j.model.TranslationsResponse;
import lol.pbu.z4j.model.TranslationCreateRequest;
import lol.pbu.z4j.model.TranslationUpdateRequest;
import reactor.core.publisher.Mono;

@Client("zendesk")
public interface TranslationClient {

    @Get("/api/v2/help_center/articles/{article_id}/translations")
    Mono<@Valid TranslationsResponse> listArticleTranslations(
            @PathVariable("article_id") @NotNull Long articleId
    );

    @Get("/api/v2/help_center/articles/{article_id}/translations/{locale}")
    Mono<@Valid TranslationResponse> showArticleTranslation(
            @PathVariable("article_id") @NotNull Long articleId,
            @PathVariable("locale") @NotNull lol.pbu.z4j.model.LocaleAbbreviation locale
    );

    @Post("/api/v2/help_center/articles/{article_id}/translations")
    Mono<@Valid TranslationResponse> createArticleTranslation(
            @PathVariable("article_id") @NotNull Long articleId,
            @Body @NotNull @Valid TranslationCreateRequest body
    );

    @Put("/api/v2/help_center/articles/{article_id}/translations/{locale}")
    Mono<@Valid TranslationResponse> updateArticleTranslation(
            @PathVariable("article_id") @NotNull Long articleId,
            @PathVariable("locale") @NotNull lol.pbu.z4j.model.LocaleAbbreviation locale,
            @Body @NotNull @Valid TranslationUpdateRequest body
    );

    @Delete("/api/v2/help_center/translations/{translation_id}")
    Mono<Void> deleteTranslation(
            @PathVariable("translation_id") @NotNull Long translationId
    );
}
