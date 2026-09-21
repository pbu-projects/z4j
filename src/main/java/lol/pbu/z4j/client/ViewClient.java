package lol.pbu.z4j.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.*;
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

    @Get("/api/v2/views/{id}/tickets.json")
    Mono<@Valid TicketsResponse> listTicketsForView(@PathVariable("id") @NotNull Long id);

}
