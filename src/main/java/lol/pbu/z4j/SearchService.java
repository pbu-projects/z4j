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
import java.util.Collections;
import lol.pbu.z4j.client.SearchClient;
import lol.pbu.z4j.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for searching and exporting tickets, orgs, users, and groups.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.3
 */
@Singleton
public class SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchService.class);

    @Inject
    SearchClient searchClient;

    /**
     * Searches for tickets using the provided query string with a default page size of 100.
     *
     * @param query The search query string.
     * @return A {@link Flux} emitting the {@link Ticket} results found.
     * @see #getTickets(String, int)
     */
    public Flux<Ticket> getTickets(String query) {
        return getTickets(query, 100);
    }

    /**
     * Searches for tickets using the provided query string and page size.
     *
     * @param query    The search query string.
     * @param pageSize The number of results to return per page.
     * @return A {@link Flux} emitting the {@link Ticket} results found.
     */
    public Flux<Ticket> getTickets(String query, int pageSize) {
        if (query == null) {
            throw new IllegalArgumentException("Search query must not be null.");
        }
        String ticketPreface = "type:ticket";
        String sanitizedQuery = query.replace(ticketPreface + " ", "").replace(ticketPreface, "").trim();
        if (sanitizedQuery.contains("type:")) {
            if (sanitizedQuery.contains("_type:")) {
                log.debug("verified that the query object does not already contain 'type:ticket': {}", sanitizedQuery);
            } else {
                throw new IllegalArgumentException("Do not include 'type:<obj>' in your query. 'filter[type]=ticket' is sent automatically.");
            }
        }
        final String finalQuery = sanitizedQuery.isEmpty() ? "*" : sanitizedQuery;

        return searchClient.exportTicket(finalQuery, pageSize, null).expand(page -> {
            if (null != page.meta() && Boolean.TRUE.equals(page.meta().hasMore()) && null != page.meta().afterCursor()) {
                log.debug("sending query for {} with cursor {}", finalQuery, page.meta().afterCursor().substring(0, Math.min(page.meta().afterCursor().length(), 6)));
                return searchClient.exportTicket(finalQuery, pageSize, page.meta().afterCursor());
            }
            return Mono.empty();
        }).flatMapIterable(page -> page.results() != null ? page.results() : Collections.emptyList()).cast(Ticket.class);
    }
}
