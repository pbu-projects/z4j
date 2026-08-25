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
import lol.pbu.z4j.model.DefinitionsResponse;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.QueueResponse;
import lol.pbu.z4j.model.QueuesResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface OmnichannelRoutingQueuesClient {

    /**
     * {@summary Create Queue}
     * <p>Creates a queue. Accepts a JSON queue definition as the request body.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/queues")
    Mono<@Valid QueueResponse> createQueue();

    /**
     * {@summary Delete Queue}
     * <p>Deletes the queue and related records.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param queueId <p>The id of the omnichannel routing queue</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/queues/{queue_id}")
    Mono<Void> deleteQueue(
        @PathVariable("queue_id") @NotNull String queueId
    );

    /**
     * {@summary List Queue Definitions}
     * <p>Returns the definitions of the queues and the definitions of the conditions under which a queue can execute. The definition of the action includes a title (\"Status\"), a type (\"list\"), and possible values. The definition of the condition includes the same fields as well as the possible operators.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/queues/definitions")
    Mono<@Valid DefinitionsResponse> listQueueDefinitions();

    /**
     * {@summary List queues}
     * <p>Returns all active queues for an account.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/queues")
    Mono<@Valid QueuesResponse> listQueues();

    /**
     * {@summary Reorder Queues}
     * <p>Alters the evaluation order of OCR queues in the account. The evaluation order is set in a <code>queue_ids</code> array in the request body.</p> <p>You must include every queue id in your account to reorder the OCR queues. If not, the endpoint will return 400 Bad Request.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>No Content</p> (status code 204)
     */
    @Patch("/api/v2/queues/order")
    Mono<Void> reorderQueues();

    /**
     * {@summary Show Queue}
     * <p>Returns a queue for the given queue id.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param queueId <p>The id of the omnichannel routing queue</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/queues/{queue_id}")
    Mono<@Valid QueueResponse> showQueueById(
        @PathVariable("queue_id") @NotNull String queueId
    );

    /**
     * {@summary Update Queue}
     * <p>Updates the queue definition for a given queue id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param queueId <p>The id of the omnichannel routing queue</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/queues/{queue_id}")
    Mono<@Valid QueueResponse> updateQueue(
        @PathVariable("queue_id") @NotNull String queueId
    );
}