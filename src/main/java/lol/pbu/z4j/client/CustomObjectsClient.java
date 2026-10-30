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
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.CustomObjectLimitsResponse;
import lol.pbu.z4j.model.CustomObjectResponse;
import lol.pbu.z4j.model.CustomObjectsCreateRequest;
import lol.pbu.z4j.model.CustomObjectsResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Custom Objects in Zendesk.</h1>
 *
 * @since 0.2.2
 */
@Client("zendesk")
public interface CustomObjectsClient {

    /**
     * <h1>{@summary Create Custom Object}</h1>
     *
     * @param customObjectsCreateRequest Request payload (required)
     * @return Created response (status code 201)
     */
    @Post("/api/v2/custom_objects")
    Mono<@Valid CustomObjectResponse> createCustomObject(
            @Body @NotNull @Valid CustomObjectsCreateRequest customObjectsCreateRequest
    );

    /**
     * <h1>{@summary Custom Objects Limit}</h1>
     *
     * @return Limits response (status code 200)
     */
    @Get("/api/v2/custom_objects/limits/object_limit")
    Mono<@Valid CustomObjectLimitsResponse> customObjectsLimit();

    /**
     * <h1>{@summary Delete Custom Object}</h1>
     *
     * @param customObjectKey The key of the custom object (required)
     * @return No content response (status code 204)
     */
    @Delete("/api/v2/custom_objects/{custom_object_key}")
    Mono<Void> deleteCustomObject(
            @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * <h1>{@summary List Custom Objects}</h1>
     *
     * @return Success response (status code 200)
     */
    @Get("/api/v2/custom_objects")
    Mono<@Valid CustomObjectsResponse> listCustomObjects();

    /**
     * <h1>{@summary Show Custom Object}</h1>
     *
     * @param customObjectKey The key of the custom object (required)
     * @return Custom Object response (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}")
    Mono<@Valid CustomObjectResponse> showCustomObject(
            @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * <h1>{@summary Update Custom Object}</h1>
     *
     * @param customObjectKey The key of the custom object (required)
     * @param customObjectsCreateRequest Request payload (required)
     * @return Success response (status code 200)
     */
    @Patch("/api/v2/custom_objects/{custom_object_key}")
    Mono<@Valid CustomObjectResponse> updateCustomObject(
            @PathVariable("custom_object_key") @NotNull String customObjectKey,
            @Body @NotNull @Valid CustomObjectsCreateRequest customObjectsCreateRequest
    );
}
