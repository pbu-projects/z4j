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
import lol.pbu.z4j.model.TwitterChannelResponse;
import lol.pbu.z4j.model.TwitterChannelTwicketStatusResponse;
import lol.pbu.z4j.model.TwitterChannelsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface XChannelClient {

    /**
     * {@summary Create Ticket from Tweet}
     * <p>Turns a tweet into a ticket. You must provide the tweet id as well as the id of a monitored X (formerly Twitter) handle configured for your account.</p> <p>The submitter of the ticket is set to be the user submitting the API request.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>description</p> (status code 201)
     */
    @Post("/api/v2/channels/twitter/tickets")
    Mono<@NotNull String> createTicketFromTweet();

    /**
     * {@summary List Ticket statuses}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param commentId <p>The ID of the comment</p> (required)
     * @param ids <p>Optional comment ids to retrieve tweet information for only particular comments</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/channels/twitter/tickets/{comment_id}/statuses")
    Mono<@Valid TwitterChannelTwicketStatusResponse> gettingTwicketStatus(
        @PathVariable("comment_id") @NotNull Integer commentId,
        @QueryValue("ids") @Nullable String ids
    );

    /**
     * {@summary List Monitored X Handles}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/channels/twitter/monitored_twitter_handles")
    Mono<@Valid TwitterChannelsResponse> listMonitoredTwitterHandles();

    /**
     * {@summary Show Monitored X Handle}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @param monitoredTwitterHandleId <p>The ID of the custom agent role</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/channels/twitter/monitored_twitter_handles/{monitored_twitter_handle_id}")
    Mono<@Valid TwitterChannelResponse> showMonitoredTwitterHandle(
        @PathVariable("monitored_twitter_handle_id") @NotNull Integer monitoredTwitterHandleId
    );
}