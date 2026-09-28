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

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.RedirectRuleCreateRequest;
import lol.pbu.z4j.model.RedirectRuleResponse;
import lol.pbu.z4j.model.RedirectRulesResponse;
import reactor.core.publisher.Mono;

/**
 * <h1>Work with Redirect Rules in Zendesk Guide.</h1>
 * <ul>
 *     <li>Search Redirect Rules {@link #searchRedirectRules}</li>
 *     <li>Show Redirect Rule {@link #showRedirectRule}</li>
 *     <li>Create Redirect Rule {@link #createRedirectRule}</li>
 *     <li>Delete Redirect Rule {@link #deleteRedirectRule}</li>
 * </ul>
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Retryable
@Client("zendesk")
public interface RedirectRuleClient {

    /**
     * <h1>{@summary Search Redirect Rules}</h1>
     *
     * @return Redirect rules response (status code 200)
     */
    @Get("/api/v2/guide/redirect_rules")
    Mono<@Valid RedirectRulesResponse> searchRedirectRules();

    /**
     * <h1>{@summary List Redirect Rules}</h1>
     * Convenience alias for {@link #searchRedirectRules}.
     *
     * @return Redirect rules response (status code 200)
     */
    default Mono<@Valid RedirectRulesResponse> listRedirectRules() {
        return searchRedirectRules();
    }

    /**
     * <h1>{@summary Show Redirect Rule}</h1>
     *
     * @param id The unique identifier of the redirect rule (required)
     * @return Redirect rule response (status code 200)
     */
    @Get("/api/v2/guide/redirect_rules/{id}")
    Mono<@Valid RedirectRuleResponse> showRedirectRule(
            @PathVariable("id") @NotNull String id
    );

    /**
     * <h1>{@summary Create Redirect Rule}</h1>
     *
     * @param body The creation request body (required)
     * @return Void response (status code 204)
     */
    @Post("/api/v2/guide/redirect_rules")
    Mono<Void> createRedirectRule(
            @Body @NotNull @Valid RedirectRuleCreateRequest body
    );

    /**
     * <h1>{@summary Set Redirect Rule}</h1>
     * Convenience alias for {@link #createRedirectRule}.
     *
     * @param body The creation request body (required)
     * @return Void response (status code 204)
     */
    default Mono<Void> setRedirectRule(
            RedirectRuleCreateRequest body
    ) {
        return createRedirectRule(body);
    }

    /**
     * <h1>{@summary Delete Redirect Rule}</h1>
     *
     * @param id The unique identifier of the redirect rule (required)
     * @return Void response (status code 204)
     */
    @Delete("/api/v2/guide/redirect_rules/{id}")
    Mono<Void> deleteRedirectRule(
            @PathVariable("id") @NotNull String id
    );
}
