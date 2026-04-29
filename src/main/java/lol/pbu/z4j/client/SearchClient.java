package lol.pbu.z4j.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.SearchExportType;
import lol.pbu.z4j.model.SearchResponse;
import lol.pbu.z4j.model.SearchSortBy;
import lol.pbu.z4j.model.SearchSortOrder;
import reactor.core.publisher.Mono;

@Retryable
@Client("zendesk")
public interface SearchClient {

    /**
     * {@summary Show Search Results Count}
     * Returns the number of items matching the query rather than returning the items. The search string works the same as a regular search.  <h4 id='allowed-for'>Allowed For</h4> <ul>     <li>Agents</li> </ul>
     *
     * @param query Returns the search results. See <a href='https://developer.zendesk.com/api-reference/ticketing/ticket-management/search/#query-syntax'>Query syntax</a> for details on the {@code query} parameter. For details on the query syntax, see the <a href='https://support.zendesk.com/hc/en-us/articles/203663226'>Zendesk Support search reference</a>. (required)
     * @return Success response (status code 200)
     * or Error response (status code 400)
     */
    @Get("/api/v2/search/count")
    Mono<@Valid SearchResponse> count(
            @QueryValue("query") @NotNull String query
    );

    /**
     * {@summary Export Search Results}
     * <p>Exports a set of results. See <a href='https://developer.zendesk.com/api-reference/ticketing/ticket-management/search/#query-syntax'>Query syntax</a> for the syntax of the {@code query} parameter.</p> <p>Use this endpoint for search queries that will return more than 1000 results. The result set is ordered only by the {@code created_at} attribute.</p> <p>The search only returns results of a single object type. The following object types are supported: ticket, organization, user, or group.</p> <p>You must specify the type in the {@code filter[type]} parameter. Searches with type in the query string will result in an error.</p> <h4>Allowed For</h4> <ul>     <li>Agents</li> </ul> <h4>Pagination</h4> <ul>     <li>Cursor pagination</li> </ul> <p>See <a href='https://developer.zendesk.com/api-reference/introduction/pagination/'>Pagination</a>.</p> <p>Returns a maximum of 1000 records per page. The number of results shown in a page is determined by the {@code page[size]} parameter.</p> <p><strong>Note</strong>: You may experience a speed reduction or a timeout if you request 1000 results per page and you have many archived tickets in the results. Try reducing the number of results per page. We recommend 100 results per page.</p> <p>The cursor specified by the {@code after_cursor} property in a response expires after one hour.</p> <p>For more information on cursor-based pagination, see the following articles:</p> <ul>     <li><a href='https://developer.zendesk.com/documentation/developer-tools/pagination/comparing-cursor-pagination-and-offset-pagination'>Comparing cursor pagination and offset pagination</a></li>     <li><a href='https://developer.zendesk.com/documentation/developer-tools/pagination/paginating-through-lists-using-cursor-pagination'>Paginating through lists using cursor pagination</a></li> </ul> <h4>Export Search Results Limits</h4> <p>This API endpoint is rate-limited to 100 requests per minute per account. The limit also counts towards the global API rate limit.</p>
     *
     * @param query      Returns the search results. See <a href='https://developer.zendesk.com/api-reference/ticketing/ticket-management/search/#query-syntax'>Query syntax</a> for details on the {@code query} parameter. For details on the query syntax, see the <a href='https://support.zendesk.com/hc/en-us/articles/203663226'>Zendesk Support search reference</a>. (required)
     * @param pageSize   The number of results shown in a page. (required)
     * @param pageAfter  The cursor token for fetching the next page of results. (required)
     * @param filterType The object type returned by the export query. Can be `ticket`, `organization`, `user`, or `group`. (required)
     * @param include    Sideloads to include in the response. Accepts a comma-separated list of values. The available sideloads depend on the search result types.  (optional)
     * @return Success response (status code 200)
     * or Error response (status code 400)
     */
    @Get("/api/v2/search/export")
    Mono<@Valid SearchResponse> export(
            @QueryValue("query") @NotNull String query,
            @QueryValue("page[size]") @NotNull Integer pageSize,
            @QueryValue("page[after]") @NotNull String pageAfter,
            @QueryValue("filter[type]") @NotNull SearchExportType filterType,
            @QueryValue("include") @Nullable String include
    );

    /**
     * {@summary List Search Results}
     * <p>Returns the search results. See <a href='https://developer.zendesk.com/api-reference/ticketing/ticket-management/search/#query-syntax'>Query syntax</a> for details on the {@code query} parameter.</p> <p>Use the ampersand character (&amp;) to append the {@code sort_by} or {@code sort_order} parameters to the URL.</p> <p>For examples, see <a href='https://developer.zendesk.com/documentation/ticketing/using-the-zendesk-api/searching-with-the-zendesk-api'>Searching with Zendesk API</a>.</p> <p>This endpoint has its own rate limit. The rate limit counts towards the global API rate limit. See <a href='https://developer.zendesk.com/api-reference/ticketing/introduction/#limits'>Limits</a>.</p> <h4 id='allowed-for'>Allowed For</h4> <ul>     <li>Agents</li> </ul> <h4>Pagination</h4> <ul>     <li>Offset pagination only</li> </ul> <p>Offset pagination may result in duplicate results when paging. You can also use the <a href='https://developer.zendesk.com/api-reference/ticketing/ticket-management/search/#export-search-results'>Export Search Results</a> endpoint, which uses cursor-based pagination and doesn't return duplicate results. See <a href='https://developer.zendesk.com/api-reference/introduction/pagination/#using-cursor-pagination'>Using cursor pagination</a> for more information.</p>
     *
     * @param query     Returns the search results. See <a href='https://developer.zendesk.com/api-reference/ticketing/ticket-management/search/#query-syntax'>Query syntax</a> for details on the {@code query} parameter. For details on the query syntax, see the <a href='https://support.zendesk.com/hc/en-us/articles/203663226'>Zendesk Support search reference</a>. (required)
     * @param sortBy    One of {@code updated_at}, {@code created_at}, {@code priority}, {@code status}, or {@code ticket_type}. Defaults to sorting by relevance (optional)
     * @param sortOrder Defaults to descending (optional)
     * @param include   Sideloads to include in the response. Accepts a comma-separated list of values. The available sideloads depend on the search result types.  (optional)
     * @return Success response (status code 200)
     * or Error response (status code 400)
     */
    @Get("/api/v2/search")
    Mono<@Valid SearchResponse> list(
            @QueryValue("query") @NotNull String query,
            @QueryValue("sort_by") @Nullable SearchSortBy sortBy,
            @QueryValue("sort_order") @Nullable SearchSortOrder sortOrder,
            @QueryValue("include") @Nullable String include
    );
}