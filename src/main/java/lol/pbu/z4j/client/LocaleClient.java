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

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import lol.pbu.z4j.model.LocalesResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Locales in Zendesk.</h1>
 * <ul>
 *     <li>List Locales {@link #listLocales}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Retryable
@Client("zendesk")
public interface LocaleClient {

    /**
     * <h1>{@summary List Locales}</h1>
     * Lists the translation locales available for the account.<br><br>  Allowed for anyone
     *
     * @return Success response (status code 200)
     */
    @Get("/api/v2/locales")
    Mono<@Valid LocalesResponse> listLocales();
}
