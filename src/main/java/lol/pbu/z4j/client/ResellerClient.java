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
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.TrialAccountResponse;
import lol.pbu.z4j.model.VerifySubdomainAvailability200Response;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface ResellerClient {

    /**
     * {@summary Create Trial Account}
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/accounts")
    Mono<@Valid TrialAccountResponse> createTrialAccount();

    /**
     * {@summary Verify Subdomain Availability}
     * <p>Zendesk Support credentials are not required to access this endpoint. You can use any Zendesk Support subdomain.</p> <p>Returns \"true\" if the subdomain is available.</p>
     *
     * @param subdomain <p>Specify the name of the subdomain you want to verify. The name can't contain underscores, hyphens, or spaces.</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/accounts/available")
    Mono<@Valid VerifySubdomainAvailability200Response> verifySubdomainAvailability(
        @QueryValue("subdomain") @NotNull String subdomain
    );
}