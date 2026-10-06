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
import lol.pbu.z4j.model.HelpCenterLocalesResponse;
import lol.pbu.z4j.model.LocaleAbbreviation;
import lol.pbu.z4j.model.MissingTranslationsResponse;
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
 * <ul>
 *     <li>Generic Translations: {@link #listTranslations}, {@link #showTranslation}, {@link #createTranslation}, {@link #updateTranslation}, {@link #deleteTranslation}</li>
 *     <li>Article Translations: {@link #listArticleTranslations}, {@link #showArticleTranslation}, {@link #createArticleTranslation}, {@link #updateArticleTranslation}</li>
 *     <li>Section Translations: {@link #listSectionTranslations}, {@link #showSectionTranslation}, {@link #createSectionTranslation}, {@link #updateSectionTranslation}</li>
 *     <li>Category Translations: {@link #listCategoryTranslations}, {@link #showCategoryTranslation}, {@link #createCategoryTranslation}, {@link #updateCategoryTranslation}</li>
 *     <li>Missing Translations: {@link #listMissingTranslations}, {@link #listMissingArticleTranslations}, {@link #listMissingSectionTranslations}, {@link #listMissingCategoryTranslations}</li>
 *     <li>Locales: {@link #listHelpCenterLocales}</li>
 * </ul>
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

    /**
     * <h1>{@summary List Missing Translations}</h1>
     *
     * @param resourceType The resource type (e.g. "articles", "sections", "categories") (required)
     * @param resourceId   The ID of the parent resource (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/{resource_type}/{resource_id}/translations/missing")
    Mono<@Valid MissingTranslationsResponse> listMissingTranslations(
            @PathVariable("resource_type") @NotNull String resourceType,
            @PathVariable("resource_id") @NotNull Long resourceId
    );

    /**
     * <h1>{@summary List Missing Article Translations}</h1>
     *
     * @param articleId The ID of the article (required)
     * @return Missing translations response (status code 200)
     */
    default Mono<@Valid MissingTranslationsResponse> listMissingArticleTranslations(@NotNull Long articleId) {
        return listMissingTranslations("articles", articleId);
    }

    /**
     * <h1>{@summary List Missing Section Translations}</h1>
     *
     * @param sectionId The ID of the section (required)
     * @return Missing translations response (status code 200)
     */
    default Mono<@Valid MissingTranslationsResponse> listMissingSectionTranslations(@NotNull Long sectionId) {
        return listMissingTranslations("sections", sectionId);
    }

    /**
     * <h1>{@summary List Missing Category Translations}</h1>
     *
     * @param categoryId The ID of the category (required)
     * @return Missing translations response (status code 200)
     */
    default Mono<@Valid MissingTranslationsResponse> listMissingCategoryTranslations(@NotNull Long categoryId) {
        return listMissingTranslations("categories", categoryId);
    }

    /**
     * <h1>{@summary List Article Translations}</h1>
     *
     * @param articleId The ID of the article (required)
     * @return Article translations response (status code 200)
     */
    default Mono<@Valid TranslationsResponse> listArticleTranslations(@NotNull Long articleId) {
        return listTranslations("articles", articleId);
    }

    /**
     * <h1>{@summary Show Article Translation}</h1>
     *
     * @param articleId The ID of the article (required)
     * @param locale    The locale of the translation (required)
     * @return Article translation response (status code 200)
     */
    default Mono<@Valid TranslationResponse> showArticleTranslation(@NotNull Long articleId, @NotNull LocaleAbbreviation locale) {
        return showTranslation("articles", articleId, locale);
    }

    /**
     * <h1>{@summary Create Article Translation}</h1>
     *
     * @param articleId The ID of the article (required)
     * @param body      {@link TranslationCreateRequest} (required)
     * @return Created translation response (status code 201)
     */
    default Mono<@Valid TranslationResponse> createArticleTranslation(@NotNull Long articleId, @NotNull @Valid TranslationCreateRequest body) {
        return createTranslation("articles", articleId, body);
    }

    /**
     * <h1>{@summary Update Article Translation}</h1>
     *
     * @param articleId The ID of the article (required)
     * @param locale    The locale of the translation to update (required)
     * @param body      {@link TranslationUpdateRequest} (required)
     * @return Updated translation response (status code 200)
     */
    default Mono<@Valid TranslationResponse> updateArticleTranslation(
            @NotNull Long articleId,
            @NotNull LocaleAbbreviation locale,
            @NotNull @Valid TranslationUpdateRequest body
    ) {
        return updateTranslation("articles", articleId, locale, body);
    }

    /**
     * <h1>{@summary List Section Translations}</h1>
     *
     * @param sectionId The ID of the section (required)
     * @return Section translations response (status code 200)
     */
    default Mono<@Valid TranslationsResponse> listSectionTranslations(@NotNull Long sectionId) {
        return listTranslations("sections", sectionId);
    }

    /**
     * <h1>{@summary Show Section Translation}</h1>
     *
     * @param sectionId The ID of the section (required)
     * @param locale    The locale of the translation (required)
     * @return Section translation response (status code 200)
     */
    default Mono<@Valid TranslationResponse> showSectionTranslation(@NotNull Long sectionId, @NotNull LocaleAbbreviation locale) {
        return showTranslation("sections", sectionId, locale);
    }

    /**
     * <h1>{@summary Create Section Translation}</h1>
     *
     * @param sectionId The ID of the section (required)
     * @param body      {@link TranslationCreateRequest} (required)
     * @return Created translation response (status code 201)
     */
    default Mono<@Valid TranslationResponse> createSectionTranslation(@NotNull Long sectionId, @NotNull @Valid TranslationCreateRequest body) {
        return createTranslation("sections", sectionId, body);
    }

    /**
     * <h1>{@summary Update Section Translation}</h1>
     *
     * @param sectionId The ID of the section (required)
     * @param locale    The locale of the translation to update (required)
     * @param body      {@link TranslationUpdateRequest} (required)
     * @return Updated translation response (status code 200)
     */
    default Mono<@Valid TranslationResponse> updateSectionTranslation(
            @NotNull Long sectionId,
            @NotNull LocaleAbbreviation locale,
            @NotNull @Valid TranslationUpdateRequest body
    ) {
        return updateTranslation("sections", sectionId, locale, body);
    }

    /**
     * <h1>{@summary List Category Translations}</h1>
     *
     * @param categoryId The ID of the category (required)
     * @return Category translations response (status code 200)
     */
    default Mono<@Valid TranslationsResponse> listCategoryTranslations(@NotNull Long categoryId) {
        return listTranslations("categories", categoryId);
    }

    /**
     * <h1>{@summary Show Category Translation}</h1>
     *
     * @param categoryId The ID of the category (required)
     * @param locale     The locale of the translation (required)
     * @return Category translation response (status code 200)
     */
    default Mono<@Valid TranslationResponse> showCategoryTranslation(@NotNull Long categoryId, @NotNull LocaleAbbreviation locale) {
        return showTranslation("categories", categoryId, locale);
    }

    /**
     * <h1>{@summary Create Category Translation}</h1>
     *
     * @param categoryId The ID of the category (required)
     * @param body       {@link TranslationCreateRequest} (required)
     * @return Created translation response (status code 201)
     */
    default Mono<@Valid TranslationResponse> createCategoryTranslation(@NotNull Long categoryId, @NotNull @Valid TranslationCreateRequest body) {
        return createTranslation("categories", categoryId, body);
    }

    /**
     * <h1>{@summary Update Category Translation}</h1>
     *
     * @param categoryId The ID of the category (required)
     * @param locale     The locale of the translation to update (required)
     * @param body       {@link TranslationUpdateRequest} (required)
     * @return Updated translation response (status code 200)
     */
    default Mono<@Valid TranslationResponse> updateCategoryTranslation(
            @NotNull Long categoryId,
            @NotNull LocaleAbbreviation locale,
            @NotNull @Valid TranslationUpdateRequest body
    ) {
        return updateTranslation("categories", categoryId, locale, body);
    }

    /**
     * <h1>{@summary List Help Center Locales}</h1>
     *
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/locales")
    Mono<@Valid HelpCenterLocalesResponse> listHelpCenterLocales();
}
