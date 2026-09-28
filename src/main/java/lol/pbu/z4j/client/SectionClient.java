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
import lol.pbu.z4j.model.CreateSectionRequest;
import lol.pbu.z4j.model.LocaleAbbreviation;
import lol.pbu.z4j.model.SectionResponse;
import lol.pbu.z4j.model.SectionSourceLocaleRequest;
import lol.pbu.z4j.model.SectionsResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Sections in Zendesk.</h1>
 * <ul>
 *     <li>List Sections by Locale {@link #listSections}</li>
 *     <li>List Sections {@link #listSectionsNoLocale}</li>
 *     <li>List Sections in Category by Locale {@link #listSectionsInCategory}</li>
 *     <li>List Sections in Category {@link #listSectionsInCategoryNoLocale}</li>
 *     <li>Show Section by Locale {@link #showSection}</li>
 *     <li>Show Section {@link #showSectionNoLocale}</li>
 *     <li>Create Section by Locale {@link #createSection}</li>
 *     <li>Create Section {@link #createSectionNoLocale}</li>
 *     <li>Update Section by Locale {@link #updateSection}</li>
 *     <li>Update Section {@link #updateSectionNoLocale}</li>
 *     <li>Update Section Source Locale {@link #updateSectionSourceLocale}</li>
 *     <li>Delete Section by Locale {@link #deleteSection}</li>
 *     <li>Delete Section {@link #deleteSectionNoLocale}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Retryable
@Client("zendesk")
public interface SectionClient {

    /**
     * <h1>List Sections by Locale</h1>
     *
     * @param localeAbbreviation The locale in which the section is displayed (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/sections")
    Mono<@Valid SectionsResponse> listSections(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation
    );

    /**
     * <h1>List Sections</h1>
     *
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/sections")
    Mono<@Valid SectionsResponse> listSectionsNoLocale();

    /**
     * <h1>List Sections in Category by Locale</h1>
     *
     * @param localeAbbreviation The locale in which the section is displayed (required)
     * @param categoryId         The unique ID of the category (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/categories/{category_id}/sections")
    Mono<@Valid SectionsResponse> listSectionsInCategory(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("category_id") @NotNull Long categoryId
    );

    /**
     * <h1>List Sections in Category</h1>
     *
     * @param categoryId The unique ID of the category (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/categories/{category_id}/sections")
    Mono<@Valid SectionsResponse> listSectionsInCategoryNoLocale(
            @PathVariable("category_id") @NotNull Long categoryId
    );

    /**
     * <h1>Show Section by Locale</h1>
     *
     * @param localeAbbreviation The locale in which the section is displayed (required)
     * @param sectionId          The unique ID of the section (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/{locale}/sections/{section_id}")
    Mono<@Valid SectionResponse> showSection(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("section_id") @NotNull Long sectionId
    );

    /**
     * <h1>Show Section</h1>
     *
     * @param sectionId The unique ID of the section (required)
     * @return OK (status code 200)
     */
    @Get("/api/v2/help_center/sections/{section_id}")
    Mono<@Valid SectionResponse> showSectionNoLocale(
            @PathVariable("section_id") @NotNull Long sectionId
    );

    /**
     * <h1>Create Section by Locale</h1>
     *
     * @param localeAbbreviation The locale in which the section is displayed (required)
     * @param categoryId         The unique ID of the category (required)
     * @param body               The section creation request (required)
     * @return Created (status code 201)
     */
    @Post("/api/v2/help_center/{locale}/categories/{category_id}/sections")
    Mono<@Valid SectionResponse> createSection(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("category_id") @NotNull Long categoryId,
            @Body @NotNull @Valid CreateSectionRequest body
    );

    /**
     * <h1>Create Section</h1>
     *
     * @param categoryId The unique ID of the category (required)
     * @param body       The section creation request (required)
     * @return Created (status code 201)
     */
    @Post("/api/v2/help_center/categories/{category_id}/sections")
    Mono<@Valid SectionResponse> createSectionNoLocale(
            @PathVariable("category_id") @NotNull Long categoryId,
            @Body @NotNull @Valid CreateSectionRequest body
    );

    /**
     * <h1>Update Section by Locale</h1>
     *
     * @param localeAbbreviation The locale in which the section is displayed (required)
     * @param sectionId          The unique ID of the section (required)
     * @param body               The section update request (required)
     * @return OK (status code 200)
     */
    @Put("/api/v2/help_center/{locale}/sections/{section_id}")
    Mono<@Valid SectionResponse> updateSection(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("section_id") @NotNull Long sectionId,
            @Body @NotNull @Valid CreateSectionRequest body
    );

    /**
     * <h1>Update Section</h1>
     *
     * @param sectionId The unique ID of the section (required)
     * @param body      The section update request (required)
     * @return OK (status code 200)
     */
    @Put("/api/v2/help_center/sections/{section_id}")
    Mono<@Valid SectionResponse> updateSectionNoLocale(
            @PathVariable("section_id") @NotNull Long sectionId,
            @Body @NotNull @Valid CreateSectionRequest body
    );

    /**
     * <h1>Update Section Source Locale</h1>
     *
     * @param localeAbbreviation The locale to set as the source locale (required)
     * @param sectionId          The unique ID of the section (required)
     * @param body               The {@link SectionSourceLocaleRequest} specifying the new source locale (required)
     * @return No content (status code 200)
     */
    @Put("/api/v2/help_center/{locale}/sections/{section_id}/source_locale")
    Mono<Void> updateSectionSourceLocale(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("section_id") @NotNull Long sectionId,
            @Body @NotNull @Valid SectionSourceLocaleRequest body
    );

    default Mono<Void> updateSectionSourceLocale(
            @NotNull LocaleAbbreviation localeAbbreviation,
            @NotNull Long sectionId
    ) {
        return updateSectionSourceLocale(localeAbbreviation, sectionId, new SectionSourceLocaleRequest(localeAbbreviation));
    }

    /**
     * <h1>Update Section Source Locale</h1>
     *
     * @param sectionId The unique ID of the section (required)
     * @param body      The {@link SectionSourceLocaleRequest} specifying the new source locale (required)
     * @return No content (status code 200)
     */
    @Put("/api/v2/help_center/sections/{section_id}/source_locale")
    Mono<Void> updateSectionSourceLocaleNoLocale(
            @PathVariable("section_id") @NotNull Long sectionId,
            @Body @NotNull @Valid SectionSourceLocaleRequest body
    );

    default Mono<Void> updateSectionSourceLocaleNoLocale(
            @NotNull LocaleAbbreviation localeAbbreviation,
            @NotNull Long sectionId
    ) {
        return updateSectionSourceLocaleNoLocale(sectionId, new SectionSourceLocaleRequest(localeAbbreviation));
    }

    /**
     * <h1>Delete Section by Locale</h1>
     *
     * @param localeAbbreviation The locale the section is displayed in (required)
     * @param sectionId          The unique ID of the section (required)
     * @return No content (status code 204)
     */
    @Delete("/api/v2/help_center/{locale}/sections/{section_id}")
    Mono<Void> deleteSection(
            @PathVariable("locale") @NotNull LocaleAbbreviation localeAbbreviation,
            @PathVariable("section_id") @NotNull Long sectionId
    );

    /**
     * <h1>Delete Section</h1>
     *
     * @param sectionId The unique ID of the section (required)
     * @return No content (status code 204)
     */
    @Delete("/api/v2/help_center/sections/{section_id}")
    Mono<Void> deleteSectionNoLocale(
            @PathVariable("section_id") @NotNull Long sectionId
    );
}
