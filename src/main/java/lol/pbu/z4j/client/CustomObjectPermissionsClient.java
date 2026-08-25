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
import lol.pbu.z4j.model.AccessRuleCreateRequest;
import lol.pbu.z4j.model.AccessRuleDefinitionsResponse;
import lol.pbu.z4j.model.AccessRuleResponse;
import lol.pbu.z4j.model.AccessRuleUpdateRequest;
import lol.pbu.z4j.model.AccessRulesResponse;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.PermissionPoliciesResponse;
import lol.pbu.z4j.model.PermissionPolicyResponse;
import lol.pbu.z4j.model.PermissionPolicyUpdateRequest;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface CustomObjectPermissionsClient {

    /**
     * {@summary Create Access Rule}
     * <p>Creates a new access rule for a custom object. Access rules define conditions that restrict which records a role can access based on field values or relationships.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param accessRuleCreateRequest (required)
     *
     * @return <p>Created Access Rule</p> (status code 201)
     */
    @Post("/api/v2/custom_objects/{custom_object_key}/access_rules")
    Mono<@Valid AccessRuleResponse> createAccessRule(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @Body @NotNull @Valid AccessRuleCreateRequest accessRuleCreateRequest
    );

    /**
     * {@summary Delete Access Rule}
     * <p>Permanently deletes an access rule for a custom object.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param id <p>The access rule ID</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/custom_objects/{custom_object_key}/access_rules/{id}")
    Mono<Void> deleteAccessRule(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("id") @NotNull String id
    );

    /**
     * {@summary List Access Rule Definitions}
     * <p>Returns the available field definitions and operators that can be used when creating access rules for a custom object. This endpoint helps you understand what fields are available for filtering and what operators can be applied to each field type.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     *
     * @return <p>Access Rule Definitions</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/access_rules/definitions")
    Mono<@Valid AccessRuleDefinitionsResponse> listAccessRuleDefinitions(
        @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * {@summary List Access Rules}
     * <p>Returns a list of access rules for a custom object. Access rules define conditions that restrict which custom object records a role can access.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     *
     * @return <p>Access Rules</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/access_rules")
    Mono<@Valid AccessRulesResponse> listAccessRules(
        @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * {@summary List Permission Policies}
     * <p>Returns a list of permission policies for a custom object. Permission policies define what actions (create, read, update, delete) different roles can perform on custom object records.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     *
     * @return <p>Permission Policies</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/permission_policies")
    Mono<@Valid PermissionPoliciesResponse> listPermissionPolicies(
        @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * {@summary Show Access Rule}
     * <p>Returns a specific access rule for a custom object.</p> <h4>Allowed For</h4> <ul> <li>Admins </li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param id <p>The access rule ID</p> (required)
     *
     * @return <p>Access Rule</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/access_rules/{id}")
    Mono<@Valid AccessRuleResponse> showAccessRule(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("id") @NotNull String id
    );

    /**
     * {@summary Show Permission Policy}
     * <p>Returns a permission policy for a specific role on a custom object. The policy ID can be: - <code>custom-role-{custom_role_id}</code> for custom roles - <code>end-user</code> for the end user system role</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param id <p>The permission policy ID. Use <code>custom-role-{custom_role_id}</code> for custom roles or <code>end-user</code> for the end user system role.</p> (required)
     *
     * @return <p>Permission Policy</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/permission_policies/{id}")
    Mono<@Valid PermissionPolicyResponse> showPermissionPolicy(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("id") @NotNull String id
    );

    /**
     * {@summary Update Access Rule}
     * <p>Updates an existing access rule for a custom object.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param id <p>The access rule ID</p> (required)
     * @param accessRuleUpdateRequest (required)
     *
     * @return <p>Updated Access Rule</p> (status code 200)
     */
    @Patch("/api/v2/custom_objects/{custom_object_key}/access_rules/{id}")
    Mono<@Valid AccessRuleResponse> updateAccessRule(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("id") @NotNull String id,
        @Body @NotNull @Valid AccessRuleUpdateRequest accessRuleUpdateRequest
    );

    /**
     * {@summary Update Permission Policy}
     * <p>Updates a permission policy for a specific role on a custom object. Define what actions (create, read, update, delete) the role can perform and optionally specify access rules.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param id <p>The permission policy ID. Use <code>custom-role-{custom_role_id}</code> for custom roles or <code>end-user</code> for the end user system role.</p> (required)
     * @param permissionPolicyUpdateRequest (required)
     *
     * @return <p>Updated Permission Policy</p> (status code 200)
     */
    @Patch("/api/v2/custom_objects/{custom_object_key}/permission_policies/{id}")
    Mono<@Valid PermissionPolicyResponse> updatePermissionPolicy(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("id") @NotNull String id,
        @Body @NotNull @Valid PermissionPolicyUpdateRequest permissionPolicyUpdateRequest
    );
}