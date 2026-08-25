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
import lol.pbu.z4j.model.ConversationLogResponse;
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
public interface ConversationLogClient {

    /**
     * {@summary List Conversation log for Ticket}
     * <p>Lists the conversation log events for a specified ticket.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/conversation_log")
    Mono<@Valid ConversationLogResponse> listConversationLogForTicket(
        @PathVariable("ticket_id") @NotNull Integer ticketId
    );
}