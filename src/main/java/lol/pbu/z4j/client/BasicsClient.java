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
import lol.pbu.z4j.model.TicketCreateVoicemailTicketRequest;
import lol.pbu.z4j.model.TicketResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface BasicsClient {

    /**
     * {@summary Create Ticket or Voicemail Ticket}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h3>Creating tickets</h3> <h4>Introduction</h4> <p>Creating tickets using Talk Partner Edition follows the same conventions as the Create Ticket endpoint. See <a href=\"/api-reference/ticketing/tickets/tickets/#create-ticket\">Create Ticket</a>.</p> <h4>Request parameters</h4> <p>The POST request takes a mandatory <code>ticket</code> object that lists the values to set when the ticket is created. You may also include an optional <code>display_to_agent</code> value such as the ID of the agent that will see the newly created ticket. The <code>display_to_agent</code> is validated before creating the ticket, returning a 422 error if it is invalid.</p> <p>Tickets created using this endpoint must have a <code>via_id</code> parameter. See the following section for possible values.</p> <h4>Zendesk Talk Integration Via IDs</h4> <p>Tickets created using this endpoint must have one of the following <code>via_id</code> parameters:</p> <p>| ID       | Description | ---------| ------------- | 44       | Voicemail | 45       | Phone call (inbound) | 46       | Phone call (outbound)</p> <h3>Creating voicemail tickets</h3> <h4>Request parameters</h4> <p>The POST request takes a mandatory <code>ticket</code> object that lists the values to set when the ticket is created. The ticket must have a <code>voice_comment</code> with the following values:</p> <p>| Name               | Type                  | Comment | ------------------ | ----------------------| ------- | from               | string                | Incoming phone number | to                 | string                | Dialed phone number | recording_url      | string                | URL of the recording | started_at         | date                  | <a href=\"http://en.wikipedia.org/wiki/ISO_8601\">ISO 8601</a> timestamp of the call starting time | call_duration      | integer               | Duration in seconds of the call | answered_by_id     | integer               | The agent who answered the call | transcription_text | string                | Transcription of the call (optional) | location           | string                | Location of the caller (optional)</p>
     *
     * @param agentId <p>ID of an agent</p> (required)
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param ticketCreateVoicemailTicketRequest (optional)
     *
     * @return <p>Successful response</p> (status code 201)
     *         or <p>When the <code>ticket_id</code> is invalid</p> (status code 404)
     *         or <p>When the <code>agent_id</code> is invalid</p> (status code 422)
     */
    @Post("/api/v2/channels/voice/tickets")
    Mono<@Valid TicketResponse> createTicketOrVoicemailTicket(
        @QueryValue("agent_id") @Nullable Integer agentId,
        @QueryValue("ticket_id") @Nullable Integer ticketId,
        @Body @Nullable @Valid TicketCreateVoicemailTicketRequest ticketCreateVoicemailTicketRequest
    );

    /**
     * {@summary Open Ticket in Agent's Browser}
     * <p>Allows you to instruct an agent's browser to open a ticket.</p> <p>When the message is successfully delivered to an agent's browser:</p> <p><code>http Status: 200 OK</code></p> <p>When <code>agent_id</code> or <code>ticket_id</code> is invalid:</p> <p><code>http Status: 404 Not Found</code></p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param agentId <p>ID of an agent</p> (required)
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     *         or <p>When the <code>agent_id</code> or <code>ticket_id</code> is invalid</p> (status code 404)
     */
    @Post("/api/v2/channels/voice/agents/{agent_id}/tickets/{ticket_id}/display")
    Mono<@NotNull String> openTicketInAgentBrowser(
        @PathVariable("agent_id") @NotNull Integer agentId,
        @PathVariable("ticket_id") @NotNull Integer ticketId
    );

    /**
     * {@summary Open a User's Profile in an Agent's Browser}
     * <p>Allows you to instruct an agent's browser to open a user's profile.</p> <p>When the message is successfully delivered to an agent's browser:</p> <p><code>http Status: 200 OK</code></p> <p>When <code>agent_id</code> or <code>user_id</code> is invalid:</p> <p><code>http Status: 404 Not Found</code></p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param agentId <p>ID of an agent</p> (required)
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     *         or <p>When the <code>agent_id</code> or <code>user_id</code> is invalid</p> (status code 404)
     */
    @Post("/api/v2/channels/voice/agents/{agent_id}/users/{user_id}/display")
    Mono<@NotNull String> openUsersProfileInAgentBrowser(
        @PathVariable("agent_id") @NotNull Integer agentId,
        @PathVariable("user_id") @NotNull Integer userId
    );
}