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
import lol.pbu.z4j.model.TargetResponse;
import lol.pbu.z4j.model.TargetsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TargetsClient {

    /**
     * {@summary Create Target}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/targets")
    Mono<@Valid TargetResponse> createTarget();

    /**
     * {@summary Delete Target}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param targetId <p>The ID of the target</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/targets/{target_id}")
    Mono<Void> deleteTarget(
        @PathVariable("target_id") @NotNull Integer targetId
    );

    /**
     * {@summary List Targets}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/targets")
    Mono<@Valid TargetsResponse> listTargets();

    /**
     * {@summary Show Target}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param targetId <p>The ID of the target</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/targets/{target_id}")
    Mono<@Valid TargetResponse> showTarget(
        @PathVariable("target_id") @NotNull Integer targetId
    );

    /**
     * {@summary Update Target}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param targetId <p>The ID of the target</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/targets/{target_id}")
    Mono<@Valid TargetResponse> updateTarget(
        @PathVariable("target_id") @NotNull Integer targetId
    );
}