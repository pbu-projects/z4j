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
import lol.pbu.z4j.model.BrandAgentResponse;
import lol.pbu.z4j.model.BrandAgentsResponse;
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
public interface BrandAgentsClient {

    /**
     * {@summary List Brand Agent Memberships}
     * <p>Returns a list of all brand agent memberships for your account.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Admins</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param brandId <p>The ID of the brand</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/brand_agents")
    Mono<@Valid BrandAgentsResponse> listBrandAgents(
        @QueryValue("user_id") @Nullable Long userId,
        @QueryValue("brand_id") @Nullable Long brandId
    );

    /**
     * {@summary List Agents By Brand}
     * <p>Returns a list of all agents assigned to a specific brand.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Admins</li> </ul>
     *
     * @param brandId <p>The ID of the brand</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/brands/{brand_id}/agents")
    Mono<@Valid BrandAgentsResponse> listBrandAgentsByBrand(
        @PathVariable("brand_id") @NotNull Long brandId
    );

    /**
     * {@summary List Brand Agent Memberships By User}
     * <p>Returns a list of all brand agent memberships for a specific user.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Admins</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/brand_agents")
    Mono<@Valid BrandAgentsResponse> listUserBrandAgents(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Show Brand Agent Membership}
     * <p>Returns a brand agent membership for your account.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param brandAgentId <p>The id of the brand agent</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/brand_agents/{brand_agent_id}")
    Mono<@Valid BrandAgentResponse> showBrandAgentById(
        @PathVariable("brand_agent_id") @NotNull String brandAgentId
    );

    /**
     * {@summary Show Brand Agent Membership By User}
     * <p>Returns a specific brand agent membership for a user.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     * @param brandAgentId <p>The id of the brand agent</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/brand_agents/{brand_agent_id}")
    Mono<@Valid BrandAgentResponse> showUserBrandAgentById(
        @PathVariable("user_id") @NotNull Long userId,
        @PathVariable("brand_agent_id") @NotNull String brandAgentId
    );
}