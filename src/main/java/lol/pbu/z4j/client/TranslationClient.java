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

    @Get("/api/v2/help_center/{resource_type}/{resource_id}/translations")
    Mono<@Valid TranslationsResponse> listTranslations(
            @PathVariable("resource_type") @NotNull String resourceType,
            @PathVariable("resource_id") @NotNull Long resourceId
    );

    @Get("/api/v2/help_center/{resource_type}/{resource_id}/translations/{locale}")
    Mono<@Valid TranslationResponse> showTranslation(
            @PathVariable("resource_type") @NotNull String resourceType,
            @PathVariable("resource_id") @NotNull Long resourceId,
            @PathVariable("locale") @NotNull lol.pbu.z4j.model.LocaleAbbreviation locale
    );

    @Post("/api/v2/help_center/{resource_type}/{resource_id}/translations")
    Mono<@Valid TranslationResponse> createTranslation(
            @PathVariable("resource_type") @NotNull String resourceType,
            @PathVariable("resource_id") @NotNull Long resourceId,
            @Body @NotNull @Valid TranslationCreateRequest body
    );

    @Put("/api/v2/help_center/{resource_type}/{resource_id}/translations/{locale}")
    Mono<@Valid TranslationResponse> updateTranslation(
            @PathVariable("resource_type") @NotNull String resourceType,
            @PathVariable("resource_id") @NotNull Long resourceId,
            @PathVariable("locale") @NotNull lol.pbu.z4j.model.LocaleAbbreviation locale,
            @Body @NotNull @Valid TranslationUpdateRequest body
    );

    @Delete("/api/v2/help_center/translations/{translation_id}")
    Mono<Void> deleteTranslation(
            @PathVariable("translation_id") @NotNull Long translationId
    );
}
