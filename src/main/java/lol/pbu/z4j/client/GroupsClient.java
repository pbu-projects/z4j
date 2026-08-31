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
import lol.pbu.z4j.model.GroupResponse;
import lol.pbu.z4j.model.GroupsCountObject;
import lol.pbu.z4j.model.GroupsResponse;
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
public interface GroupsClient {

    /**
     * {@summary Count Groups}
     * <p>Returns an approximate count of groups. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The <code>refreshed_at</code> property of the <code>count</code> object is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>refreshed_at</code> may occasionally be null. This indicates that the count is being updated in the background, and the <code>value</code> property of the <code>count</code> object is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/groups/count")
    Mono<@Valid GroupsCountObject> countGroups();

    /**
     * {@summary Count User Groups}
     * <p>Returns an approximate count of groups for the specified user. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The <code>refreshed_at</code> property of the <code>count</code> object is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>refreshed_at</code> may occasionally be null. This indicates that the count is being updated in the background, and the <code>value</code> property of the <code>count</code> object is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/groups/count")
    Mono<@Valid GroupsCountObject> countUserGroups(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Create Group}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage groups (Enterprise only)</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/groups")
    Mono<@Valid GroupResponse> createGroup(
        @Body @NotNull GroupResponse group
    );

    /**
     * {@summary Delete Group}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents assigned to a custom role with permissions to manage groups (Enterprise only)</li> </ul>
     *
     * @param groupId <p>The ID of the group</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/groups/{group_id}")
    Mono<Void> deleteGroup(
        @PathVariable("group_id") @NotNull Long groupId
    );

    /**
     * {@summary List Assignable Groups}
     * <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/groups/assignable")
    Mono<@Valid GroupsResponse> listAssignableGroups();

    /**
     * {@summary List Groups}
     * <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @param excludeDeleted <p>Whether to exclude deleted entities</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/groups")
    Mono<@Valid GroupsResponse> listGroups(
        @QueryValue("exclude_deleted") @Nullable Boolean excludeDeleted
    );

    /**
     * {@summary List User Groups}
     * <p>Returns a list of groups for the specified user.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param excludeDeleted <p>Whether to exclude deleted entities</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/groups")
    Mono<@Valid GroupsResponse> listUserGroups(
        @PathVariable("user_id") @NotNull Long userId,
        @QueryValue("exclude_deleted") @Nullable Boolean excludeDeleted
    );

    /**
     * {@summary Show Group}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @param groupId <p>The ID of the group</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/groups/{group_id}")
    Mono<@Valid GroupResponse> showGroupById(
        @PathVariable("group_id") @NotNull Long groupId
    );

    /**
     * {@summary Update Group}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param groupId <p>The ID of the group</p> (required)
     * @param group <p>The group update payload</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/groups/{group_id}")
    Mono<@Valid GroupResponse> updateGroup(
        @PathVariable("group_id") @NotNull Long groupId,
        @Body @NotNull GroupResponse group
    );
}