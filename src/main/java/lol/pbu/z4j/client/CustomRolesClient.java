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
import lol.pbu.z4j.model.CustomRoleResponse;
import lol.pbu.z4j.model.CustomRolesResponse;
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
public interface CustomRolesClient {

    /**
     * {@summary Create Custom Role}
     * <h4>Availability</h4> <ul> <li>Accounts on the Enterprise plan or above</li> </ul> <h4>Allowed for</h4> <ul> <li>Administrators</li> <li>Agents with the <code>manage_roles</code> permission</li> </ul>
     *
     * @return <p>Created response</p> (status code 200)
     */
    @Post("/api/v2/custom_roles")
    Mono<@Valid CustomRoleResponse> createCustomRole();

    /**
     * {@summary Delete Custom Role}
     * <h4>Availability</h4> <ul> <li>Accounts on the Enterprise plan or above</li> </ul> <h4>Allowed for</h4> <ul> <li>Administrators</li> <li>Agents with the <code>manage_roles</code> permission</li> </ul>
     *
     * @param customRoleId <p>The ID of the custom agent role</p> (required)
     *
     * @return <p>No Contetnt response</p> (status code 204)
     */
    @Delete("/api/v2/custom_roles/{custom_role_id}")
    Mono<Void> deleteCustomRoleById(
        @PathVariable("custom_role_id") @NotNull Integer customRoleId
    );

    /**
     * {@summary List Custom Roles}
     * <h4>Availability</h4> <ul> <li>Accounts on the Enterprise plan or above</li> </ul> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_roles")
    Mono<@Valid CustomRolesResponse> listCustomRoles();

    /**
     * {@summary Show Custom Role}
     * <h4>Availability</h4> <ul> <li>Accounts on the Enterprise plan or above</li> </ul> <h4>Allowed for</h4> <ul> <li>Administrators</li> <li>Agents with the <code>manage_roles</code> permission</li> </ul>
     *
     * @param customRoleId <p>The ID of the custom agent role</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_roles/{custom_role_id}")
    Mono<@Valid CustomRoleResponse> showCustomRoleById(
        @PathVariable("custom_role_id") @NotNull Integer customRoleId
    );

    /**
     * {@summary Update Custom Role}
     * <h4>Availability</h4> <ul> <li>Accounts on the Enterprise plan or above</li> </ul> <h4>Allowed for</h4> <ul> <li>Administrators Agents with the <code>manage_roles</code> permission</li> </ul>
     *
     * @param customRoleId <p>The ID of the custom agent role</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/custom_roles/{custom_role_id}")
    Mono<@Valid CustomRoleResponse> updateCustomRoleById(
        @PathVariable("custom_role_id") @NotNull Integer customRoleId
    );
}