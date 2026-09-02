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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <h1>{@summary Zendesk Search API}</h1>
 * <p>Reactive client for performing searches in Zendesk. These methods provide low-level access to the search endpoints.</p>
 * <p>Search queries can be dynamically built using {@link SearchQueryBuilder} to ensure syntax correctness.</p>
 * <ul>
 *     <li>Show Search Result Counts with {@link #count(String)}</li>
 *     <li>Export Search Results with {@link #exportTicket(String, Integer, String)}</li>
 *     <li>List Search Results with {@link #list}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Retryable
@Client("zendesk")
public interface SearchClient {

    /**
     * <h1>{@summary Show Search Results Count}</h1>
     * <p>Returns the number of items matching the query rather than returning the items themselves.</p>
     * <p>See <a href='https://developer.zendesk.com/api-reference/ticketing/ticket-management/search/#show-results-count'>Zendesk API Docs</a>.</p>
     *
     * <h4>Allowed For:</h4>
     * <ul>
     *   <li>Agents</li>
     * </ul>
     *
     * @param query The search query string. See <a href='https://support.zendesk.com/hc/en-us/articles/203663226'>Zendesk Support search reference</a>. (required)
     * @return Success response (status code 200) containing the count.
     */
    @Get("/api/v2/search/count")
    Mono<@Valid SearchResponse> count(@QueryValue("query") @NotNull String query);

    /**
     * <h1>{@summary Export Search Results}</h1>
     * <p>Exports a set of Tickets using cursor-based pagination. Best for queries that return more than 1000 results.</p>
     * <p>See <a href='https://developer.zendesk.com/api-reference/ticketing/ticket-management/search/#export-search-results'>Zendesk API Docs</a>.</p>
     *
     * <h4>Pagination</h4>
     * <p>Uses Cursor Pagination. Returns a maximum of 1000 records per page. The result set is ordered only by the {@code created_at} attribute.</p>
     * 
     * <h4>Rate Limits</h4>
     * <p>Rate-limited to 100 requests per minute per account.</p>
     *
     * @param query      The search query string. (required)
     * @param pageSize   The number of results shown in a page, max 1000. (optional)
     * @param pageAfter  The cursor token for fetching the next page of results. (optional)
     * @return Success response (status code 200) containing a cursor-paginated list of tickets.
     */
    @Get("/api/v2/search/export?filter[type]=ticket")
    Mono<@Valid ExportResponse<Ticket>> exportTicket(
            @QueryValue("query") @NotNull String query,
            @QueryValue("page[size]") @Nullable @Max(1000) Integer pageSize,
            @QueryValue("page[after]") @Nullable String pageAfter
    );

    /**
     * <h1>{@summary List Search Results}</h1>
     * <p>Returns the search results matching the query.</p>
     * <p>See <a href='https://developer.zendesk.com/api-reference/ticketing/ticket-management/search/#list-search-results'>Zendesk API Docs</a>.</p>
     *
     * <h4>Sideloading</h4>
     * <p>You can sideload related records using the {@code include} parameter. For tickets, you might sideload {@code users} or {@code groups}.</p>
     *
     * <h4>Allowed For:</h4>
     * <ul>
     *   <li>Agents</li>
     * </ul>
     *
     * @param query     The search query string. (required)
     * @param include   A list of entities to sideload (e.g. "users", "groups"). Use {@link TicketSideload} or {@link UserSideload}. (optional)
     * @param sortBy    One of {@code updated_at}, {@code created_at}, {@code priority}, {@code status}, or {@code ticket_type}. (optional)
     * @param sortOrder Defaults to descending. (optional)
     * @param page      The offset page number to retrieve. (optional)
     * @param perPage   The count of results to include in each offset page (max 100). (optional)
     * @return Success response (status code 200) containing search results.
     */
    @Get("/api/v2/search")
    Mono<@Valid SearchResponse> list(
            @QueryValue("query") @NotNull String query,
            @QueryValue("include") @Nullable List<String> include,
            @QueryValue("sort_by") @Nullable SortBy sortBy,
            @QueryValue("sort_order") @Nullable SortOrder sortOrder,
            @QueryValue("page") @Nullable Integer page,
            @QueryValue("per_page") @Nullable @Max(100) Integer perPage
    );
}
