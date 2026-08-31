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
import lol.pbu.z4j.model.TagCountResponse;
import lol.pbu.z4j.model.TagsByObjectIdResponse;
import lol.pbu.z4j.model.TagsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TagsClient {

    /**
     * {@summary Add Organization Tags}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/organizations/{organization_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> addOrganizationTags(
        @PathVariable("organization_id") @NotNull Long organizationId
    );

    /**
     * {@summary Search Tags}
     * <p>Returns an array of registered and recent tag names that start with the characters specified in the <code>name</code> query parameter. You must specify at least 2 characters.</p> <h4>Pagination</h4> <ul> <li>Offset pagination only</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/#using-offset-pagination\">Using Offset Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param name <p>A substring of a tag to search for</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/autocomplete/tags")
    Mono<@Valid TagsByObjectIdResponse> autocompleteTags(
        @QueryValue("name") @Nullable String name
    );

    /**
     * {@summary Count Tags}
     * <p>Returns an approximate count of tags. If the count exceeds 100,000, it is updated every 24 hours.</p> <p>The <code>refreshed_at</code> property of the <code>count</code> object is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, the <code>refreshed_at</code> property in the <code>count</code> object may occasionally be null. This indicates that the count is being updated in the background and the <code>value</code> property in the <code>count</code> object is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/tags/count")
    Mono<@Valid TagCountResponse> countTags();

    /**
     * {@summary Remove Tags}
     * <p>You can also delete tags from multiple tickets with the <a href=\"/api-reference/ticketing/tickets/tickets/#update-many-tickets\">Update Many Tickets</a> endpoint.</p> <p>This endpoint supports safe updates. See <a href=\"/api-reference/ticketing/ticket-management/tags/#safe-update\">Safe Update</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Delete("/api/v2/tickets/{ticket_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> deleteTagsTicket(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Remove User Tags}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Delete("/api/v2/users/{user_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> deleteUserTags(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary List Organization Tags}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organizations/{organization_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> listOrganizationTags(
        @PathVariable("organization_id") @NotNull Long organizationId
    );

    /**
     * {@summary List Resource Tags}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> listResourceTags(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary List Tags}
     * <p>Lists up to the 20,000 most popular tags in the last 60 days, in decreasing popularity.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/tags")
    Mono<@Valid TagsResponse> listTags();

    /**
     * {@summary List User Tags}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/users/{user_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> listUserTags(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Add Tags}
     * <p>You can also add tags to multiple tickets with the <a href=\"/api-reference/ticketing/tickets/tickets/#update-many-tickets\">Update Many Tickets</a> endpoint.</p> <h4>Safe Update</h4> <p>If the same ticket is updated by multiple API requests at the same time, some tags could be lost because of ticket update collisions. Include <code>updated_stamp</code> and <code>safe_update</code> properties in the request body to make a safe update.</p> <p>For <code>updated_stamp</code>, retrieve and specify the ticket's latest <code>updated_at</code> timestamp. The tag update only occurs if the <code>updated_stamp</code> timestamp matches the ticket's actual <code>updated_at</code> timestamp at the time of the request. If the timestamps don't match (in other words, if the ticket was updated since you retrieved the ticket's last <code>updated_at</code> timestamp), the request returns a 409 Conflict error.</p> <h4>Example</h4> <p><code>js {   \"tags\": [\"customer\"],   \"updated_stamp\":\"2019-09-12T21:45:16Z\",   \"safe_update\":\"true\" }</code></p> <p>For details, see <a href=\"/api-reference/ticketing/tickets/tickets/#protecting-against-ticket-update-collisions\">Protecting against ticket update collisions</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/tickets/{ticket_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> putTagsTicket(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Add User Tags}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/users/{user_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> putUserTags(
        @PathVariable("user_id") @NotNull Long userId
    );

    /**
     * {@summary Remove Organization Tags}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Delete("/api/v2/organizations/{organization_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> removeOrganizationTags(
        @PathVariable("organization_id") @NotNull Long organizationId
    );

    /**
     * {@summary Set Organization Tags}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param organizationId <p>The ID of an organization</p> (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/organizations/{organization_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> setOrganizationTags(
        @PathVariable("organization_id") @NotNull Long organizationId
    );

    /**
     * {@summary Set Tags}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/tickets/{ticket_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> setTagsTicket(
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Set User Tags}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/users/{user_id}/tags")
    Mono<@Valid TagsByObjectIdResponse> setUserTags(
        @PathVariable("user_id") @NotNull Long userId
    );
}