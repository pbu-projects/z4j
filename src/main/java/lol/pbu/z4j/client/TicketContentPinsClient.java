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
import lol.pbu.z4j.model.CreateTicketContentPin400Response;
import lol.pbu.z4j.model.CreateTicketContentPin409Response;
import lol.pbu.z4j.model.CreateTicketContentPin422Response;
import lol.pbu.z4j.model.CreateTicketContentPinRequest;
import lol.pbu.z4j.model.DeleteTicketContentPin404Response;
import lol.pbu.z4j.model.ListTicketContentPins200Response;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.TicketContentPin;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TicketContentPinsClient {

    /**
     * {@summary Create Ticket Content Pin}
     * <p>Creates a new content pin for a specific ticket. Content pins allow you to link to articles, community posts, or external content for easy reference.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents</li> </ul>
     *
     * @param createTicketContentPinRequest <p>Creates a new content pin for a specific ticket. The content pin can link to articles, topics, or other relevant content.</p> (required)
     *
     * @return <p>Content pin created successfully</p> (status code 201)
     *         or <p>Bad Request</p> (status code 400)
     *         or <p>Conflict</p> (status code 409)
     *         or <p>Unprocessable Entity</p> (status code 422)
     */
    @Post("/api/v2/ticket_content_pins")
    Mono<@Valid TicketContentPin> createTicketContentPin(
        @Body @NotNull @Valid CreateTicketContentPinRequest createTicketContentPinRequest
    );

    /**
     * {@summary Delete Content Pin from Ticket}
     * <p>Deletes a specific content pin from a ticket.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param contentPinId <p>The id of the content pin to delete</p> (required)
     *
     * @return <p>Content pin deleted successfully</p> (status code 200)
     *         or <p>Content pin not found</p> (status code 404)
     */
    @Delete("/api/v2/ticket_content_pins/{content_pin_id}")
    Mono<@Valid TicketContentPin> deleteTicketContentPin(
        @PathVariable("content_pin_id") @NotNull String contentPinId
    );

    /**
     * {@summary List Ticket Content Pins}
     * <p>Lists the content pins for a specific ticket. Content pins are used to pin related content such as articles to a ticket for quick access. This endpoint returns the content pins associated with the specified ticket id.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The id of the ticket for which to list content pins</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_content_pins")
    Mono<@Valid ListTicketContentPins200Response> listTicketContentPins(
        @QueryValue("ticket_id") @Nullable String ticketId
    );
}