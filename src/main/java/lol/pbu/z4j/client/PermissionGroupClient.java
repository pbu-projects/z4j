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
import lol.pbu.z4j.model.CreatePermissionGroupRequest;
import lol.pbu.z4j.model.PermissionGroupResponse;
import lol.pbu.z4j.model.PermissionGroupsResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Management Permission Groups in Zendesk Guide.</h1>
 * <ul>
 *     <li>List Permission Groups {@link #listPermissionGroups}</li>
 *     <li>Show Permission Group {@link #showPermissionGroup}</li>
 *     <li>Create Permission Group {@link #createPermissionGroup}</li>
 *     <li>Update Permission Group {@link #updatePermissionGroup}</li>
 *     <li>Delete Permission Group {@link #deletePermissionGroup}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Retryable
@Client("zendesk")
public interface PermissionGroupClient {

    /**
     * <h1>{@summary List Permission Groups}</h1>
     * <p>Lists management permission groups.</p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @return OK response (status code 200)
     */
    @Get("/api/v2/guide/permission_groups")
    Mono<@Valid PermissionGroupsResponse> listPermissionGroups();

    /**
     * <h1>{@summary Show Permission Group}</h1>
     * <p>Shows a single management permission group specified by ID.</p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param id The unique ID of the permission group (required)
     * @return OK response (status code 200)
     */
    @Get("/api/v2/guide/permission_groups/{id}")
    Mono<@Valid PermissionGroupResponse> showPermissionGroup(
            @PathVariable("id") @NotNull Long id
    );

    /**
     * <h1>{@summary Create Permission Group}</h1>
     * <p>Creates a new management permission group.</p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param body The permission group creation request (required)
     * @return Created response (status code 201)
     */
    @Post("/api/v2/guide/permission_groups")
    Mono<@Valid PermissionGroupResponse> createPermissionGroup(
            @Body @NotNull @Valid CreatePermissionGroupRequest body
    );

    /**
     * <h1>{@summary Update Permission Group}</h1>
     * <p>Updates an existing management permission group.</p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param id The unique ID of the permission group to update (required)
     * @param body The permission group update request (required)
     * @return OK response (status code 200)
     */
    @Put("/api/v2/guide/permission_groups/{id}")
    Mono<@Valid PermissionGroupResponse> updatePermissionGroup(
            @PathVariable("id") @NotNull Long id,
            @Body @NotNull @Valid CreatePermissionGroupRequest body
    );

    /**
     * <h1>{@summary Delete Permission Group}</h1>
     * <p>Deletes an existing management permission group.</p>
     * <h4>Allowed for Help Center managers</h4>
     *
     * @param id The unique ID of the permission group to delete (required)
     * @return Response when deleted (status code 204)
     */
    @Delete("/api/v2/guide/permission_groups/{id}")
    Mono<Void> deletePermissionGroup(
            @PathVariable("id") @NotNull Long id
    );

}
