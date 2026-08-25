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
import lol.pbu.z4j.model.JobStatusResponse;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.TicketBulkImportRequest;
import lol.pbu.z4j.model.TicketImportRequest;
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
public interface TicketImportClient {

    /**
     * {@summary Ticket Bulk Import}
     * <p>Accepts an array of up to 100 ticket objects.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param archiveImmediately <p>If <code>true</code>, any ticket created with a <code>closed</code> status bypasses the normal ticket lifecycle and will be created directly in your ticket archive</p> (optional)
     * @param ticketBulkImportRequest (optional)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Post("/api/v2/imports/tickets/create_many")
    Mono<@Valid JobStatusResponse> ticketBulkImport(
        @QueryValue("archive_immediately") @Nullable Boolean archiveImmediately,
        @Body @Nullable @Valid TicketBulkImportRequest ticketBulkImportRequest
    );

    /**
     * {@summary Ticket Import}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param archiveImmediately <p>If <code>true</code>, any ticket created with a <code>closed</code> status bypasses the normal ticket lifecycle and will be created directly in your ticket archive</p> (optional)
     * @param ticketImportRequest (optional)
     *
     * @return <p>Successfully created</p> (status code 201)
     */
    @Post("/api/v2/imports/tickets")
    Mono<@Valid TicketResponse> ticketImport(
        @QueryValue("archive_immediately") @Nullable Boolean archiveImmediately,
        @Body @Nullable @Valid TicketImportRequest ticketImportRequest
    );
}