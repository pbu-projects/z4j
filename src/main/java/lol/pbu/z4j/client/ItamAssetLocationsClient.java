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
import lol.pbu.z4j.model.ItamAssetLocationCreateRequest;
import lol.pbu.z4j.model.ItamAssetLocationResponse;
import lol.pbu.z4j.model.ItamAssetLocationsResponse;
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
public interface ItamAssetLocationsClient {

    /**
     * {@summary Create Asset Location}
     * <p>Creates a location.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param itamAssetLocationCreateRequest (optional)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/it_asset_management/locations")
    Mono<@Valid ItamAssetLocationResponse> createItamLocation(
        @Body @Nullable @Valid ItamAssetLocationCreateRequest itamAssetLocationCreateRequest
    );

    /**
     * {@summary Delete Asset Location}
     * <p>Deletes a location with the specified id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param locationId <p>The id of the location</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/it_asset_management/locations/{location_id}")
    Mono<Void> deleteItamLocation(
        @PathVariable("location_id") @NotNull String locationId
    );

    /**
     * {@summary List Asset Locations}
     * <p>Lists all locations.</p> <h4>Pagination</h4> <ul> <li><a href=\"/api-reference/introduction/pagination/#cursor-pagination\">Cursor pagination</a> only.</li> </ul> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/it_asset_management/locations")
    Mono<@Valid ItamAssetLocationsResponse> listItamLocations();

    /**
     * {@summary Show Asset Location}
     * <p>Returns the location with the specified id.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param locationId <p>The id of the location</p> (required)
     *
     * @return <p>Location</p> (status code 200)
     */
    @Get("/api/v2/it_asset_management/locations/{location_id}")
    Mono<@Valid ItamAssetLocationResponse> showItamLocation(
        @PathVariable("location_id") @NotNull String locationId
    );

    /**
     * {@summary Update Asset Location}
     * <p>Updates an existing location.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param locationId <p>The id of the location</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Patch("/api/v2/it_asset_management/locations/{location_id}")
    Mono<@Valid ItamAssetLocationResponse> updateItamLocation(
        @PathVariable("location_id") @NotNull String locationId
    );
}