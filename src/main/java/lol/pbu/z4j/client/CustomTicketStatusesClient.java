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
import lol.pbu.z4j.model.BulkUpdateDefaultCustomStatusRequest;
import lol.pbu.z4j.model.CreateTicketFormStatusesForCustomStatusRequest;
import lol.pbu.z4j.model.CustomStatusCreateRequest;
import lol.pbu.z4j.model.CustomStatusResponse;
import lol.pbu.z4j.model.CustomStatusUpdateRequest;
import lol.pbu.z4j.model.CustomStatusesResponse;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.TicketFormStatusesResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface CustomTicketStatusesClient {

    /**
     * {@summary Bulk Update Default Custom Ticket Status}
     * <p>Updates the default values for many custom ticket statuses at once.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param bulkUpdateDefaultCustomStatusRequest (optional)
     *
     * @return <p>Updated</p> (status code 200)
     */
    @Put("/api/v2/custom_status/default")
    Mono<Object> bulkUpdateDefaultCustomStatus(
        @Body @Nullable @Valid BulkUpdateDefaultCustomStatusRequest bulkUpdateDefaultCustomStatusRequest
    );

    /**
     * {@summary Create Custom Ticket Status}
     * <p>Takes a <code>custom_status</code> object that specifies the custom ticket status properties to create.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customStatusCreateRequest (optional)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/custom_statuses")
    Mono<@Valid CustomStatusResponse> createCustomStatus(
        @Body @Nullable @Valid CustomStatusCreateRequest customStatusCreateRequest
    );

    /**
     * {@summary Create Ticket Form Statuses for a Custom Status}
     * <p>Creates one or many tickets form status associations for a custom status.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customStatusId <p>The id of the custom status</p> (required)
     * @param createTicketFormStatusesForCustomStatusRequest (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/custom_statuses/{custom_status_id}/ticket_form_statuses")
    Mono<@Valid TicketFormStatusesResponse> createTicketFormStatusesForCustomStatus(
        @PathVariable("custom_status_id") @NotNull Long customStatusId,
        @Body @Nullable @Valid CreateTicketFormStatusesForCustomStatusRequest createTicketFormStatusesForCustomStatusRequest
    );

    /**
     * {@summary List Custom Ticket Statuses}
     * <p>Lists all undeleted custom ticket statuses for the account. No pagination is provided.</p> <h4>Allowed For</h4> <ul> <li>End Users</li> </ul>
     *
     * @param statusCategories <p>Filter the list of custom ticket statuses by a comma-separated list of status categories</p> (optional)
     * @param active <p>If true, show only active custom ticket statuses. If false, show only inactive custom ticket statuses. If the filter is not used, show all custom ticket statuses</p> (optional)
     * @param _default <p>If true, show only default custom ticket statuses. If false, show only non-default custom ticket statuses. If the filter is not used, show all custom ticket statuses</p> (optional)
     *
     * @return <p>List custom ticket statuses</p> (status code 200)
     */
    @Get("/api/v2/custom_statuses")
    Mono<@Valid CustomStatusesResponse> listCustomStatuses(
        @QueryValue("status_categories") @Nullable String statusCategories,
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("default") @Nullable Boolean _default
    );

    /**
     * {@summary Show Custom Ticket Status}
     * <p>Returns the custom ticket status object.</p> <h4>Allowed For</h4> <ul> <li>End Users</li> </ul>
     *
     * @param customStatusId <p>The id of the custom status</p> (required)
     *
     * @return <p>Custom Status</p> (status code 200)
     */
    @Get("/api/v2/custom_statuses/{custom_status_id}")
    Mono<@Valid CustomStatusResponse> showCustomStatus(
        @PathVariable("custom_status_id") @NotNull Long customStatusId
    );

    /**
     * {@summary Update Custom Ticket Status}
     * <p>Takes a <code>custom_status</code> object that specifies the properties to update.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customStatusId <p>The id of the custom status</p> (required)
     * @param customStatusUpdateRequest (optional)
     *
     * @return <p>Updated</p> (status code 200)
     */
    @Put("/api/v2/custom_statuses/{custom_status_id}")
    Mono<@Valid CustomStatusResponse> updateCustomStatus(
        @PathVariable("custom_status_id") @NotNull Long customStatusId,
        @Body @Nullable @Valid CustomStatusUpdateRequest customStatusUpdateRequest
    );
}