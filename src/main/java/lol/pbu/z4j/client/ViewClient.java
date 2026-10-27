package lol.pbu.z4j.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.TicketsResponse;
import lol.pbu.z4j.model.ViewCountResponse;
import lol.pbu.z4j.model.ViewExecuteResponse;
import lol.pbu.z4j.model.ViewResponse;
import lol.pbu.z4j.model.ViewsResponse;
import reactor.core.publisher.Mono;

@Retryable
@Client("zendesk")
public interface ViewClient {

    @Get("/api/v2/views")
    Mono<@Valid ViewsResponse> listViews();

    @Get("/api/v2/views/active")
    Mono<@Valid ViewsResponse> listActiveViews();

    @Get("/api/v2/views/{id}")
    Mono<@Valid ViewResponse> showView(@PathVariable("id") @NotNull Long id);

    @Get("/api/v2/views/{id}/execute")
    Mono<@Valid ViewExecuteResponse> executeView(@PathVariable("id") @NotNull Long id);

    @Get("/api/v2/views/{id}/count")
    Mono<@Valid ViewCountResponse> countView(@PathVariable("id") @NotNull Long id);

    default Mono<@Valid TicketsResponse> listTicketsForView(@PathVariable("id") @NotNull Long id) {
        return listTicketsForView(id, null, null);
    }

    @Get("/api/v2/views/{id}/tickets.json")
    Mono<@Valid TicketsResponse> listTicketsForView(
        @PathVariable("id") @NotNull Long id,
        @QueryValue("page[after]") @Nullable String pageAfter,
        @QueryValue("page[size]") @Nullable @Max(100) Integer pageSize
    );

}
