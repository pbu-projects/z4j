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
import lol.pbu.z4j.model.ItamAssetStatusResponse;
import lol.pbu.z4j.model.ItamAssetStatusesResponse;
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
public interface ItamAssetStatusesClient {

    /**
     * {@summary List Asset Statuses}
     * <p>Lists all statuses.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/it_asset_management/statuses")
    Mono<@Valid ItamAssetStatusesResponse> listItamStatuses();

    /**
     * {@summary Show Asset Status}
     * <p>Returns the status with the specified id.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param statusId <p>The id of the asset status</p> (required)
     *
     * @return <p>Asset Status</p> (status code 200)
     */
    @Get("/api/v2/it_asset_management/statuses/{status_id}")
    Mono<@Valid ItamAssetStatusResponse> showItamStatus(
        @PathVariable("status_id") @NotNull String statusId
    );
}