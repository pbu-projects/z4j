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
import io.micronaut.core.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.core.convert.format.Format;
import lol.pbu.z4j.model.CustomObjectLimitsResponse;
import lol.pbu.z4j.model.CustomObjectResponse;
import lol.pbu.z4j.model.CustomObjectsCreateRequest;
import lol.pbu.z4j.model.CustomObjectsResponse;
import reactor.core.publisher.Mono;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface CustomObjectsClient {

    /**
     * {@summary Create Custom Object}
     * <p>Creates an object describing all the properties required to create a custom object record</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectsCreateRequest (optional)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/custom_objects")
    Mono<@Valid CustomObjectResponse> createCustomObject(
        @Body @Nullable @Valid CustomObjectsCreateRequest customObjectsCreateRequest
    );

    /**
     * {@summary Custom Objects Limit}
     * <p>List the current count and the limit for custom objects</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/limits/object_limit")
    Mono<@Valid CustomObjectLimitsResponse> customObjectsLimit();

    /**
     * {@summary Delete Custom Object}
     * <p>Permanently deletes the custom object with the specified key</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/custom_objects/{custom_object_key}")
    Mono<Void> deleteCustomObject(
        @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * {@summary List Custom Objects}
     * <p>Lists all undeleted custom objects for the account</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects")
    Mono<@Valid CustomObjectsResponse> listCustomObjects();

    /**
     * {@summary Show Custom Object}
     * <p>Returns an object with the specified key</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     *
     * @return <p>Custom Object</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}")
    Mono<@Valid CustomObjectResponse> showCustomObject(
        @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * {@summary Update Custom Object}
     * <p>Updates an individual custom object. The updating rules are as follows: * Takes a <code>custom_object</code> object that specifies the properties to update * The <code>key</code> property cannot be updated</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Patch("/api/v2/custom_objects/{custom_object_key}")
    Mono<@Valid CustomObjectResponse> updateCustomObject(
        @PathVariable("custom_object_key") @NotNull String customObjectKey
    );
}