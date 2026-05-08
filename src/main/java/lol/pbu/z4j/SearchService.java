package lol.pbu.z4j;

import jakarta.inject.Inject;
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
public class SearchService {
    @Inject
    SearchClient searchClient;

    public Flux<Ticket> getTickets(String query) {
        return getTickets(query, 100);
    }

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
