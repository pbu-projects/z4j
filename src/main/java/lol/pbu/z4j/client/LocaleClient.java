package lol.pbu.z4j.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import lol.pbu.z4j.model.LocalesResponse;
import reactor.core.publisher.Mono;

@Retryable
@Client("zendesk")
public interface LocaleClient {

    /**
     * {@summary List Locales}
     * Lists the translation locales available for the account.<br><br>  Allowed for anyone
     *
     * @return Success response (status code 200)
     */
    @Get("/api/v2/locales")
    Mono<@Valid LocalesResponse> listLocales();
}