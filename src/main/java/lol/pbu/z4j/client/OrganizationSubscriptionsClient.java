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
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.OrganizationSubscriptionCreateRequest;
import lol.pbu.z4j.model.OrganizationSubscriptionResponse;
import lol.pbu.z4j.model.OrganizationSubscriptionsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface OrganizationSubscriptionsClient {

    /**
     * {@summary Create Organization Subscription}
     * <h4>Allowed For:</h4> <ul> <li>Agents</li> <li>End users</li> </ul> <p>End users can only subscribe to shared organizations in which they're members.</p>
     *
     * @param organizationSubscriptionCreateRequest (optional)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Post("/api/v2/organization_subscriptions")
    Mono<@Valid OrganizationSubscriptionResponse> createOrganizationSubscription(
        @Body @Nullable @Valid OrganizationSubscriptionCreateRequest organizationSubscriptionCreateRequest
    );

    /**
     * {@summary Delete Organization Subscription}
     * <h4>Allowed For:</h4> <ul> <li>Agents</li> <li>End users</li> </ul>
     *
     * @param organizationSubscriptionId <p>The ID of the organization subscription</p> (required)
     *
     * @return <p>No content</p> (status code 204)
     */
    @Delete("/api/v2/organization_subscriptions/{organization_subscription_id}")
    Mono<Void> deleteOrganizationSubscription(
        @PathVariable("organization_subscription_id") @NotNull Integer organizationSubscriptionId
    );

    /**
     * {@summary List Organization Subscriptions}
     * <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> <li>End users</li> </ul> <p>For end users, the response will only list the subscriptions created by the requesting end user.</p>
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/organization_subscriptions")
    Mono<@Valid OrganizationSubscriptionsResponse> listOrganizationSubscriptions();

    /**
     * {@summary List Subscriptions By Organization}
     * <p>Returns a list of organization subscriptions for a specific organization.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> <li>End users</li> </ul> <p>For end users, the response will only list the subscriptions created by the requesting end user.</p>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}/subscriptions")
    Mono<@Valid OrganizationSubscriptionsResponse> listOrganizationSubscriptionsByOrganization(
        @PathVariable("organization_id") @NotNull Integer organizationId
    );

    /**
     * {@summary List User's Organization Subscriptions}
     * <p>Returns a list of organization subscriptions for a specific user.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For:</h4> <ul> <li>Agents</li> <li>End users</li> </ul> <p>For end users, the response will only list the subscriptions created by the requesting end user.</p>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/organization_subscriptions")
    Mono<@Valid OrganizationSubscriptionsResponse> listUserOrganizationSubscriptions(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Show Organization Subscription}
     * <h4>Allowed For:</h4> <ul> <li>Agents</li> <li>End users</li> </ul> <p>For end users, the response will only list the subscriptions created by the requesting end user.</p>
     *
     * @param organizationSubscriptionId <p>The ID of the organization subscription</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/organization_subscriptions/{organization_subscription_id}")
    Mono<@Valid OrganizationSubscriptionResponse> showOrganizationSubscription(
        @PathVariable("organization_subscription_id") @NotNull Integer organizationSubscriptionId
    );
}