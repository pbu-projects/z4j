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
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.DeletedUserResponse;
import lol.pbu.z4j.model.DeletedUsersResponse;
import lol.pbu.z4j.model.JobStatusResponse;
import lol.pbu.z4j.model.User;
import lol.pbu.z4j.model.UserCountResponse;
import lol.pbu.z4j.model.UserRelatedResponse;
import lol.pbu.z4j.model.UserRequest;
import lol.pbu.z4j.model.UserResponse;
import lol.pbu.z4j.model.UsersRequest;
import lol.pbu.z4j.model.UsersResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>Work with Users in Zendesk.</h1>
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Client("zendesk")
public interface UserClient {

    /**
     * List users.
     *
     * @return Users response
     */
    default Mono<@Valid UsersResponse> listUsers() {
        return listUsers(null, null, null);
    }

    /**
     * List users with optional role, permission set, and external ID filters.
     *
     * @param role Filters users by role (optional)
     * @param permissionSet Custom role ID filter (optional)
     * @param externalId External ID filter (optional)
     * @return Users response
     */
    @Get("/api/v2/users")
    Mono<@Valid UsersResponse> listUsers(
            @QueryValue("role") @Nullable String role,
            @QueryValue("permission_set") @Nullable Long permissionSet,
            @QueryValue("external_id") @Nullable String externalId);

    /**
     * Show user details.
     *
     * @param userId User ID (required)
     * @return User response
     */
    @Get("/api/v2/users/{user_id}")
    Mono<@Valid UserResponse> showUser(@PathVariable("user_id") @NotNull Long userId);

    /**
     * Create user.
     *
     * @param userRequest User creation request (required)
     * @return User response
     */
    @Post("/api/v2/users")
    Mono<@Valid UserResponse> createUser(@Body @NotNull @Valid UserRequest userRequest);

    /**
     * Update user.
     *
     * @param userId User ID (required)
     * @param userRequest User update request (required)
     * @return User response
     */
    @Put("/api/v2/users/{user_id}")
    Mono<@Valid UserResponse> updateUser(
            @PathVariable("user_id") @NotNull Long userId,
            @Body @NotNull @Valid UserRequest userRequest);

    /**
     * Delete user.
     *
     * @param userId User ID (required)
     * @return User response
     */
    @Delete("/api/v2/users/{user_id}")
    Mono<@Valid UserResponse> deleteUser(@PathVariable("user_id") @NotNull Long userId);

    /**
     * Show current authenticated user.
     *
     * @return User response
     */
    @Get("/api/v2/users/me")
    Mono<@Valid UserResponse> showCurrentUser();

    /**
     * Count users.
     *
     * @return User count response
     */
    default Mono<@Valid UserCountResponse> countUsers() {
        return countUsers(null, null);
    }

    /**
     * Count users with optional role and permission set filters.
     *
     * @param role Filters users by role (optional)
     * @param permissionSet Custom role ID filter (optional)
     * @return User count response
     */
    @Get("/api/v2/users/count")
    Mono<@Valid UserCountResponse> countUsers(
            @QueryValue("role") @Nullable String role,
            @QueryValue("permission_set") @Nullable Long permissionSet);

    /**
     * Search users by query and optional external ID.
     *
     * @param query Search query string (optional)
     * @param externalId External ID (optional)
     * @return Users response
     */
    @Get("/api/v2/users/search")
    Mono<@Valid UsersResponse> searchUsers(
            @QueryValue("query") @Nullable String query,
            @QueryValue("external_id") @Nullable String externalId);

    /**
     * Autocomplete users whose name starts with the given value.
     *
     * @param name Name prefix to search (required)
     * @return Users response
     */
    default Mono<@Valid UsersResponse> autocompleteUsers(@QueryValue("name") @NotNull String name) {
        return autocompleteUsers(name, null, null);
    }

    /**
     * Autocomplete users whose name starts with the given value, with optional field ID and source filters.
     *
     * @param name Name prefix to search (required)
     * @param fieldId Custom user field ID (optional)
     * @param source Source channel filter (optional)
     * @return Users response
     */
    @Get("/api/v2/users/autocomplete")
    Mono<@Valid UsersResponse> autocompleteUsers(
            @QueryValue("name") @NotNull String name,
            @QueryValue("field_id") @Nullable String fieldId,
            @QueryValue("source") @Nullable String source);

    /**
     * Show many users by IDs, external IDs, or include deleted flag.
     *
     * @param ids Comma-separated list of user IDs (optional)
     * @param externalIds Comma-separated list of external IDs (optional)
     * @param includeDeleted Whether to return deleted users (optional)
     * @return Users response
     */
    @Get("/api/v2/users/show_many")
    Mono<@Valid UsersResponse> showManyUsers(
            @QueryValue("ids") @Nullable String ids,
            @QueryValue("external_ids") @Nullable String externalIds,
            @QueryValue("include_deleted") @Nullable Boolean includeDeleted);

    /**
     * Convenience method to show many users by a list of user IDs.
     *
     * @param ids List of user IDs (required)
     * @return Users response
     */
    default Mono<@Valid UsersResponse> showManyUsers(@NotNull List<Long> ids) {
        return showManyUsers(ids.stream().map(String::valueOf).collect(Collectors.joining(",")), null, null);
    }

    /**
     * Create many users in bulk.
     *
     * @param usersRequest Users request (required)
     * @return Job status response
     */
    @Post("/api/v2/users/create_many")
    Mono<@Valid JobStatusResponse> createManyUsers(@Body @NotNull @Valid UsersRequest usersRequest);

    /**
     * Convenience method to create many users in bulk from a list of user objects.
     *
     * @param users List of users (required)
     * @return Job status response
     */
    default Mono<@Valid JobStatusResponse> createManyUsers(@NotNull List<User> users) {
        return createManyUsers(new UsersRequest(users));
    }

    /**
     * Create or update user.
     *
     * @param userRequest User request (required)
     * @return User response
     */
    @Post("/api/v2/users/create_or_update")
    Mono<@Valid UserResponse> createOrUpdateUser(@Body @NotNull @Valid UserRequest userRequest);

    /**
     * Create or update many users in bulk.
     *
     * @param usersRequest Users request (required)
     * @return Job status response
     */
    @Post("/api/v2/users/create_or_update_many")
    Mono<@Valid JobStatusResponse> createOrUpdateManyUsers(@Body @NotNull @Valid UsersRequest usersRequest);

    /**
     * Bulk destroy many users by IDs or external IDs.
     *
     * @param ids Comma-separated list of user IDs (optional)
     * @param externalIds Comma-separated list of external IDs (optional)
     * @return Job status response
     */
    @Delete("/api/v2/users/destroy_many")
    Mono<@Valid JobStatusResponse> destroyManyUsers(
            @QueryValue("ids") @Nullable String ids,
            @QueryValue("external_ids") @Nullable String externalIds);

    /**
     * Convenience method to destroy many users by a list of user IDs.
     *
     * @param ids List of user IDs (required)
     * @return Job status response
     */
    default Mono<@Valid JobStatusResponse> destroyManyUsers(@NotNull List<Long> ids) {
        return destroyManyUsers(ids.stream().map(String::valueOf).collect(Collectors.joining(",")), null);
    }

    /**
     * Update many users with the same attributes.
     *
     * @param ids Comma-separated list of user IDs (optional)
     * @param externalIds Comma-separated list of external IDs (optional)
     * @param userRequest User request to apply to matching users (required)
     * @return Job status response
     */
    @Put("/api/v2/users/update_many")
    Mono<@Valid JobStatusResponse> updateManyUsers(
            @QueryValue("ids") @Nullable String ids,
            @QueryValue("external_ids") @Nullable String externalIds,
            @Body @NotNull @Valid UserRequest userRequest);

    /**
     * Convenience method to update many users with the same attributes by a list of user IDs.
     *
     * @param ids List of user IDs (required)
     * @param userRequest User request to apply to matching users (required)
     * @return Job status response
     */
    default Mono<@Valid JobStatusResponse> updateManyUsers(
            @NotNull List<Long> ids,
            @NotNull @Valid UserRequest userRequest) {
        return updateManyUsers(ids.stream().map(String::valueOf).collect(Collectors.joining(",")), null, userRequest);
    }

    /**
     * Update many users with distinct attributes.
     *
     * @param usersRequest Users request containing multiple user updates (required)
     * @return Job status response
     */
    @Put("/api/v2/users/update_many")
    Mono<@Valid JobStatusResponse> updateManyUsers(@Body @NotNull @Valid UsersRequest usersRequest);

    /**
     * Convenience method to update many users with distinct attributes from a list of user objects.
     *
     * @param users List of users with updates (required)
     * @return Job status response
     */
    default Mono<@Valid JobStatusResponse> updateManyUsers(@NotNull List<User> users) {
        return updateManyUsers(new UsersRequest(users));
    }

    /**
     * Merge users.
     *
     * @param userId Source user ID in path parameter (required)
     * @param userRequest Target user in body (required)
     * @return User response
     */
    @Put("/api/v2/users/{user_id}/merge")
    Mono<@Valid UserResponse> mergeUsers(
            @PathVariable("user_id") @NotNull Long userId,
            @Body @NotNull @Valid UserRequest userRequest);

    /**
     * Show user related information.
     *
     * @param userId User ID (required)
     * @return User related response
     */
    @Get("/api/v2/users/{user_id}/related")
    Mono<@Valid UserRelatedResponse> showUserRelated(@PathVariable("user_id") @NotNull Long userId);

    /**
     * List soft-deleted users.
     *
     * @return Deleted users response
     */
    @Get("/api/v2/deleted_users")
    Mono<@Valid DeletedUsersResponse> listDeletedUsers();

    /**
     * Show soft-deleted user.
     *
     * @param deletedUserId Deleted user ID (required)
     * @return Deleted user response
     */
    @Get("/api/v2/deleted_users/{deleted_user_id}")
    Mono<@Valid DeletedUserResponse> showDeletedUser(@PathVariable("deleted_user_id") @NotNull Long deletedUserId);

    /**
     * Permanently delete user.
     *
     * @param deletedUserId Deleted user ID (required)
     * @return Deleted user response
     */
    @Delete("/api/v2/deleted_users/{deleted_user_id}")
    Mono<@Valid DeletedUserResponse> permanentlyDeleteUser(@PathVariable("deleted_user_id") @NotNull Long deletedUserId);

    /**
     * Count deleted users.
     *
     * @return User count response
     */
    @Get("/api/v2/deleted_users/count")
    Mono<@Valid UserCountResponse> countDeletedUsers();
}
