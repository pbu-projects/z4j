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

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.IncrementalOrganizationTimeResponse;
import lol.pbu.z4j.model.IncrementalTicketCursorResponse;
import lol.pbu.z4j.model.IncrementalTicketEventTimeResponse;
import lol.pbu.z4j.model.IncrementalUserCursorResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Incremental Export Client in Zendesk.</h1>
 *
 * @since 0.2.7
 */
@Retryable
@Client("zendesk")
public interface IncrementalClient {

    @Get("/api/v2/incremental/tickets/cursor.json")
    Mono<IncrementalTicketCursorResponse> exportTickets(
        @QueryValue("start_time") @Nullable Long startTime,
        @QueryValue("cursor") @Nullable String cursor,
        @QueryValue("per_page") @Nullable @Max(1000) Integer perPage
    );

    @Get("/api/v2/incremental/users/cursor.json")
    Mono<IncrementalUserCursorResponse> exportUsers(
        @QueryValue("start_time") @Nullable Long startTime,
        @QueryValue("cursor") @Nullable String cursor,
        @QueryValue("per_page") @Nullable @Max(1000) Integer perPage
    );

    @Get("/api/v2/incremental/organizations.json")
    Mono<IncrementalOrganizationTimeResponse> exportOrganizations(
        @QueryValue("start_time") @NotNull Long startTime,
        @QueryValue("per_page") @Nullable @Max(1000) Integer perPage
    );

    @Get("/api/v2/incremental/ticket_events.json")
    Mono<IncrementalTicketEventTimeResponse> exportTicketEvents(
        @QueryValue("start_time") @NotNull Long startTime
    );
}
