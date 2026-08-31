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
import lol.pbu.z4j.model.EmailNotificationResponse;
import lol.pbu.z4j.model.EmailNotificationsResponse;
import lol.pbu.z4j.model.ListEmailNotificationsFilterParameter;
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
public interface EmailNotificationsClient {

    /**
     * {@summary List Email Notifications}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Request parameters</h4> <h5>Filters</h5> <p><strong>Important</strong>: You must specify a <code>filter</code> query parameter to narrow the scope of the search for this endpoint.</p> <ul> <li>By notification: <code>api/v2/email_notifications.json?filter[notification_id]=7824075373693</code></li> <li>By comment: <code>api/v2/email_notifications.json?filter[comment_id]=782407</code></li> <li>By ticket: <code>api/v2/email_notifications.json?filter[ticket_id]=623</code></li> </ul> <h5>Pagination</h5> <p>By default, a maximum of 100 email notifications are included per page. Use cursor-based pagination parameters (<code>page[after]</code> and <code>page[before]</code>) to navigate the records (can't be used together in the same request). See <a href=\"/api-reference/introduction/pagination/\">Pagination</a> for more details.</p> <h5>Sorting</h5> <p>By default, email notifications are sorted by creation time (newest first). The query parameter is not supported for this endpoint.</p>
     *
     * @param filter <p>Filters the email notifications by ticket, comment, or notification id.</p> (required)
     * @param perPage <p>The number of records to return per page</p> (optional)
     * @param sort <p>The field to sort the list.  Possible values are \"created_at\", \"updated_at\" (ascending order) or \"-created_at\", \"-updated_at\" (descending order)</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/email_notifications")
    Mono<@Valid EmailNotificationsResponse> listEmailNotifications(
        @QueryValue("filter") @NotNull @Valid ListEmailNotificationsFilterParameter filter,
        @QueryValue("per_page") @Nullable Long perPage,
        @QueryValue("sort") @Nullable String sort
    );

    /**
     * {@summary Show Email Notification}
     * <p>Shows details on an email notification. You can get the value of the <code>notification_id</code> parameter by listing the ticket's outbound emails.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param notificationId <p>The id of the email notification</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/email_notifications/{notification_id}")
    Mono<@Valid EmailNotificationResponse> showEmailNotification(
        @PathVariable("notification_id") @NotNull Long notificationId
    );

    /**
     * {@summary Show Many Email Notifications}
     * <p>Shows details of many email notifications. Allows you to query by providing a list of notifications, comments, or tickets IDs.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Filters</h4> <ul> <li>By notification: <code>?ids=8433702508541,8433348111869</code></li> <li>By comment: <code>?comment_ids=8433348111741,8433544226045,8433702508413</code></li> <li>By ticket: <code>?ticket_ids=730,723</code></li> </ul>
     *
     * @param ids <p>Comma-separated list of notification ids</p> (required)
     * @param commentIds <p>Comma-separated list of comment ids</p> (required)
     * @param ticketIds <p>Comma-separated list of ticket ids</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/email_notifications/show_many")
    Mono<@Valid EmailNotificationResponse> showManyEmailNotifications(
        @QueryValue("ids") @NotNull String ids,
        @QueryValue("comment_ids") @NotNull String commentIds,
        @QueryValue("ticket_ids") @NotNull String ticketIds
    );
}