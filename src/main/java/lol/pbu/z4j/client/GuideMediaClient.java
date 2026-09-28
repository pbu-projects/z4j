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

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.GuideMediaCreateRequest;
import lol.pbu.z4j.model.GuideMediaResponse;
import lol.pbu.z4j.model.GuideMediaUploadUrlRequest;
import lol.pbu.z4j.model.GuideMediaUploadUrlResponse;
import lol.pbu.z4j.model.GuideMediasResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Guide Media objects in Zendesk.</h1>
 * <ul>
 *     <li>Search Guide Media {@link #searchGuideMedia}</li>
 *     <li>Create Upload URL {@link #createUploadUrl}</li>
 *     <li>Create Guide Media {@link #createGuideMedia}</li>
 *     <li>Show Guide Media {@link #showGuideMedia}</li>
 *     <li>Replace Guide Media {@link #replaceGuideMedia}</li>
 *     <li>Rename Guide Media {@link #renameGuideMedia}</li>
 *     <li>Delete Guide Media {@link #deleteGuideMedia}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Retryable
@Client("zendesk")
public interface GuideMediaClient {

    /**
     * <h1>{@summary Search Guide Media}</h1>
     *
     * @param namePrefix Filter media by name prefix (optional)
     * @param sort       Sort order for media items (optional, e.g. "created", "-created", "name", "-name")
     * @return Guide media list response (status code 200)
     */
    @Get("/api/v2/guide/medias")
    Mono<@Valid GuideMediasResponse> searchGuideMedia(
            @QueryValue("filter[name_prefix]") @Nullable String namePrefix,
            @QueryValue("sort") @Nullable String sort
    );

    /**
     * <h1>{@summary Create Upload URL for a Guide Media object}</h1>
     * Provisions an upload URL for a guide-media object.
     *
     * @param body The content type and file size specifications (required)
     * @return Upload URL response containing upload_url, asset_upload_id, and headers (status code 200)
     */
    @Post("/api/v2/guide/medias/upload_url")
    Mono<@Valid GuideMediaUploadUrlResponse> createUploadUrl(
            @Body @NotNull @Valid GuideMediaUploadUrlRequest body
    );

    /**
     * <h1>{@summary Create Upload URL for a Guide Media object (default parameters)}</h1>
     *
     * @return Upload URL response containing upload_url and asset_upload_id (status code 200)
     */
    default Mono<@Valid GuideMediaUploadUrlResponse> createUploadUrl() {
        return createUploadUrl(new GuideMediaUploadUrlRequest("text/plain", 100L));
    }

    /**
     * <h1>{@summary Create Guide Media object}</h1>
     * Creates a new guide media object from an uploaded asset.
     *
     * @param body The creation request body (required)
     * @return Created Guide Media response (status code 201)
     */
    @Post("/api/v2/guide/medias")
    Mono<@Valid GuideMediaResponse> createGuideMedia(
            @Body @NotNull @Valid GuideMediaCreateRequest body
    );

    /**
     * <h1>{@summary Show Guide Media object}</h1>
     *
     * @param id The unique ULID identifier of the media object (required)
     * @return Guide Media response (status code 200)
     */
    @Get("/api/v2/guide/medias/{id}")
    Mono<@Valid GuideMediaResponse> showGuideMedia(
            @PathVariable("id") @NotNull String id
    );

    /**
     * <h1>{@summary Replace Guide Media object}</h1>
     *
     * @param id   The unique ULID identifier of the media object (required)
     * @param body The replacement request body containing asset_upload_id and filename (required)
     * @return Replaced Guide Media response (status code 200)
     */
    @Put("/api/v2/guide/medias/{id}")
    Mono<@Valid GuideMediaResponse> replaceGuideMedia(
            @PathVariable("id") @NotNull String id,
            @Body @NotNull @Valid GuideMediaCreateRequest body
    );

    /**
     * <h1>{@summary Rename Guide Media object}</h1>
     *
     * @param id   The unique ULID identifier of the media object (required)
     * @param body The rename request body containing filename (required)
     * @return Renamed Guide Media response (status code 200)
     */
    @Patch("/api/v2/guide/medias/{id}")
    Mono<@Valid GuideMediaResponse> renameGuideMedia(
            @PathVariable("id") @NotNull String id,
            @Body @NotNull @Valid GuideMediaCreateRequest body
    );

    /**
     * <h1>{@summary Delete Guide Media object}</h1>
     *
     * @param id The unique ULID identifier of the media object (required)
     * @return Void response (status code 204)
     */
    @Delete("/api/v2/guide/medias/{id}")
    Mono<Void> deleteGuideMedia(
            @PathVariable("id") @NotNull String id
    );
}
