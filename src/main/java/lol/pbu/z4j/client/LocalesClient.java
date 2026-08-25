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
import lol.pbu.z4j.model.LocaleResponse;
import lol.pbu.z4j.model.LocalesResponse;
import reactor.core.publisher.Mono;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface LocalesClient {

    /**
     * {@summary Detect Best Language for User}
     * <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/locales/detect_best_locale")
    Mono<@Valid LocaleResponse> detectBestLocale();

    /**
     * {@summary List Available Public Locales}
     * <p>Lists the translation locales that are available to all accounts.</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/locales/public")
    Mono<@Valid LocalesResponse> listAvailablePublicLocales();

    /**
     * {@summary List Locales}
     * <p>Lists the translation locales available for the account.</p> <p><strong>Note</strong>: You can alter the list by passing an updated <code>locale_ids</code> array to the <a href=\"/api-reference/ticketing/account-configuration/account_settings/#update-account-settings\">Update Account Settings</a> endpoint.</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/locales")
    Mono<@Valid LocalesResponse> listLocales();

    /**
     * {@summary List Locales for Agent}
     * <p>Lists the translation locales that have been localized for agents on a specific account.</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/locales/agent")
    Mono<@Valid LocalesResponse> listLocalesForAgent();

    /**
     * {@summary Show Current Locale}
     * <p>This works like <a href=\"#show-locale\">Show Locale</a>, but instead of taking a locale id as an argument, it renders the locale of the user performing the request.</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/locales/current")
    Mono<@Valid LocaleResponse> showCurrentLocale();

    /**
     * {@summary Show Locale}
     * <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @param localeId <p>The ID or the <a href=\"https://en.wikipedia.org/wiki/IETF_language_tag\">BCP-47 code</a> of the locale. Examples: es-419, en-us, pr-br</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/locales/{locale_id}")
    Mono<@Valid LocaleResponse> showLocaleById(
        @PathVariable("locale_id") @NotNull String localeId
    );
}