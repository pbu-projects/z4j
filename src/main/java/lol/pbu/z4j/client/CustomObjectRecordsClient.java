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
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.CustomObjectRecordResponse;
import lol.pbu.z4j.model.CustomObjectRecordsCreateRequest;
import lol.pbu.z4j.model.CustomObjectRecordsResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Custom Object Records in Zendesk.</h1>
 *
 * @since 0.2.2
 */
@Client("zendesk")
public interface CustomObjectRecordsClient {

    /**
     * <h1>{@summary Create Custom Object Record}</h1>
     *
     * @param customObjectKey Key of custom object (required)
     * @param customObjectRecordsCreateRequest Request payload (required)
     * @return Created response (status code 201)
     */
    @Post("/api/v2/custom_objects/{custom_object_key}/records")
    Mono<@Valid CustomObjectRecordResponse> createCustomObjectRecord(
            @PathVariable("custom_object_key") @NotNull String customObjectKey,
            @Body @NotNull @Valid CustomObjectRecordsCreateRequest customObjectRecordsCreateRequest
    );

    /**
     * <h1>{@summary Delete Custom Object Record}</h1>
     *
     * @param customObjectKey Key of custom object (required)
     * @param customObjectRecordId ID of record (required)
     * @return No content response (status code 204)
     */
    @Delete("/api/v2/custom_objects/{custom_object_key}/records/{custom_object_record_id}")
    Mono<Void> deleteCustomObjectRecord(
            @PathVariable("custom_object_key") @NotNull String customObjectKey,
            @PathVariable("custom_object_record_id") @NotNull String customObjectRecordId
    );

    /**
     * <h1>{@summary List Custom Object Records}</h1>
     *
     * @param customObjectKey Key of custom object (required)
     * @param filterIds Filter by IDs (optional)
     * @param filterExternalIds Filter by external IDs (optional)
     * @param sort Sort order (optional)
     * @param pageBefore Page cursor before (optional)
     * @param pageAfter Page cursor after (optional)
     * @param pageSize Page size (optional)
     * @return Success response (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/records")
    Mono<@Valid CustomObjectRecordsResponse> listCustomObjectRecords(
            @PathVariable("custom_object_key") @NotNull String customObjectKey,
            @QueryValue("filter[ids]") @Nullable String filterIds,
            @QueryValue("filter[external_ids]") @Nullable String filterExternalIds,
            @QueryValue("sort") @Nullable String sort,
            @QueryValue("page[before]") @Nullable String pageBefore,
            @QueryValue("page[after]") @Nullable String pageAfter,
            @QueryValue("page[size]") @Nullable Long pageSize
    );

    default Mono<@Valid CustomObjectRecordsResponse> listCustomObjectRecords(String customObjectKey) {
        return listCustomObjectRecords(customObjectKey, null, null, null, null, null, null);
    }

    /**
     * <h1>{@summary List All Custom Object Records (Cursor Pagination)}</h1>
     * Iteratively fetches all pages of custom object records using cursor pagination.
     *
     * @param customObjectKey Key of custom object (required)
     * @return Flux of all custom object records
     */
    default reactor.core.publisher.Flux<lol.pbu.z4j.model.CustomObjectRecord> listAllCustomObjectRecords(String customObjectKey) {
        return listAllCustomObjectRecords(customObjectKey, null);
    }

    default reactor.core.publisher.Flux<lol.pbu.z4j.model.CustomObjectRecord> listAllCustomObjectRecords(String customObjectKey, Long pageSize) {
        return listCustomObjectRecords(customObjectKey, null, null, null, null, null, pageSize)
                .expand(response -> {
                    if (response.getMeta() != null && Boolean.TRUE.equals(response.getMeta().get("has_more"))) {
                        String afterCursor = (String) response.getMeta().get("after_cursor");
                        if (afterCursor != null) {
                            return listCustomObjectRecords(customObjectKey, null, null, null, null, afterCursor, pageSize);
                        }
                    }
                    return Mono.empty();
                })
                .flatMapIterable(CustomObjectRecordsResponse::getCustomObjectRecords);
    }

    /**
     * <h1>{@summary Search Custom Object Records}</h1>
     *
     * @param customObjectKey Key of custom object (required)
     * @param query Search query text (optional)
     * @param sort Sort order (optional)
     * @param pageBefore Page cursor before (optional)
     * @param pageAfter Page cursor after (optional)
     * @param pageSize Page size (optional)
     * @return Success response (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/records/search")
    Mono<@Valid CustomObjectRecordsResponse> searchCustomObjectRecords(
            @PathVariable("custom_object_key") @NotNull String customObjectKey,
            @QueryValue("query") @Nullable String query,
            @QueryValue("sort") @Nullable String sort,
            @QueryValue("page[before]") @Nullable String pageBefore,
            @QueryValue("page[after]") @Nullable String pageAfter,
            @QueryValue("page[size]") @Nullable Long pageSize
    );

    default Mono<@Valid CustomObjectRecordsResponse> searchCustomObjectRecords(String customObjectKey, String query) {
        return searchCustomObjectRecords(customObjectKey, query, null, null, null, null);
    }

    /**
     * <h1>{@summary Show Custom Object Record}</h1>
     *
     * @param customObjectKey Key of custom object (required)
     * @param customObjectRecordId ID of custom object record (required)
     * @return Custom Object Record response (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/records/{custom_object_record_id}")
    Mono<@Valid CustomObjectRecordResponse> showCustomObjectRecord(
            @PathVariable("custom_object_key") @NotNull String customObjectKey,
            @PathVariable("custom_object_record_id") @NotNull String customObjectRecordId
    );

    /**
     * <h1>{@summary Update Custom Object Record}</h1>
     *
     * @param customObjectKey Key of custom object (required)
     * @param customObjectRecordId ID of custom object record (required)
     * @param customObjectRecordsCreateRequest Request payload (required)
     * @return Success response (status code 200)
     */
    @Patch("/api/v2/custom_objects/{custom_object_key}/records/{custom_object_record_id}")
    Mono<@Valid CustomObjectRecordResponse> updateCustomObjectRecord(
            @PathVariable("custom_object_key") @NotNull String customObjectKey,
            @PathVariable("custom_object_record_id") @NotNull String customObjectRecordId,
            @Body @NotNull @Valid CustomObjectRecordsCreateRequest customObjectRecordsCreateRequest
    );
}
