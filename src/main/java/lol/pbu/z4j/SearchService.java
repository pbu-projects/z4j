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
package lol.pbu.z4j;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lol.pbu.z4j.client.SearchClient;
import lol.pbu.z4j.model.ExportResponse;
import lol.pbu.z4j.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for searching and exporting tickets, orgs, users, and groups.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.3
 */
@Slf4j
@Singleton
public class SearchService {
    @Inject
    SearchClient searchClient;

    /**
     * Searches for tickets using the provided query string with a default page size of 100.
     *
     * @param query The search query string. Note: 'type:ticket' is automatically prepended.
     * @return A {@link Flux} emitting the {@link Ticket} results found.
     * @see #getTickets(String, int)
     */
    public Flux<Ticket> getTickets(String query) {
        return getTickets(query, 100);
    }


    /**
     * Searches for tickets using the provided query string and page size.
     *
     * @param query    The search query string. Note: 'type:ticket' is automatically prepended.
     * @param pageSize The number of results to return per page.
     * @return A {@link Flux} emitting the {@link Ticket} results found.
     */
    public Flux<Ticket> getTickets(String query, int pageSize) {
        String ticketPreface = "type:ticket";
        query = query.replace(ticketPreface + " ", "").replace(ticketPreface, "");
        if (query.contains("type:")) {
            if (query.contains("_type:")) {
                log.debug("verified that the query object does not already contain 'type:ticket': {}", query);
            } else {
                throw new IllegalArgumentException("Do not include 'type:<obj>' in your query. 'type:ticket' will automatically be added to your query.");
            }
        }
        final String finalQuery = ticketPreface + " " + query;

        return searchClient.exportTicket(finalQuery, pageSize, null).expand(page -> {
            if (null != page.getMeta() && page.getMeta().getHasMore() && null != page.getMeta().getAfterCursor()) {
                log.debug("sending query for {} with cursor {}", finalQuery, page.getMeta().getAfterCursor().substring(0, Math.min(page.getMeta().getAfterCursor().length(), 6)));
                return searchClient.exportTicket(finalQuery, pageSize, page.getMeta().getAfterCursor());
            }
            return Mono.empty();
        }).flatMapIterable(ExportResponse::getResults).cast(Ticket.class);
    }
}
