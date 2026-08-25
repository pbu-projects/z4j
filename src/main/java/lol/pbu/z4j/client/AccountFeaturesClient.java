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
import lol.pbu.z4j.model.FeaturesResponse;
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
public interface AccountFeaturesClient {

    /**
     * {@summary List Account Features}
     * <p>Returns a list of features and whether they are enabled for the account. Checks if a feature (arturo, subscription, custom code block) is available. If a corresponding setting exists, also checks whether that setting is enabled.</p> <p>The optional query parameter <code>subscription</code> may be supplied. If it evaluates to a true value (example, subscription=true) then the list of features is limited to subscription-backed features only.</p> <p>The optional query parameter <code>ids</code> may be supplied with a comma-separated list of feature names to filter the response to only those features.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param subscription <p>Filter features to only subscription-backed features</p> (optional)
     * @param ids <p>Comma-separated list of feature names to filter the response</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/account/features")
    Mono<@Valid FeaturesResponse> listFeatures(
        @QueryValue("subscription") @Nullable Boolean subscription,
        @QueryValue("ids") @Nullable String ids
    );
}