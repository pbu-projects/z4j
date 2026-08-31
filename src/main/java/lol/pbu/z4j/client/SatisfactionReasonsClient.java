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
import lol.pbu.z4j.model.SatisfactionReasonResponse;
import lol.pbu.z4j.model.SatisfactionReasonsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface SatisfactionReasonsClient {

    /**
     * {@summary List Reasons for Satisfaction Rating}
     * <p>List all reasons for an account</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/satisfaction_reasons")
    Mono<@Valid SatisfactionReasonsResponse> listSatisfactionRatingReasons();

    /**
     * {@summary Show Reason for Satisfaction Rating}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param satisfactionReasonId <p>The id of the satisfaction rating reason</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/satisfaction_reasons/{satisfaction_reason_id}")
    Mono<@Valid SatisfactionReasonResponse> showSatisfactionRatings(
        @PathVariable("satisfaction_reason_id") @NotNull Long satisfactionReasonId
    );
}