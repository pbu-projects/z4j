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
import lol.pbu.z4j.model.ItamAssetTypeCreateRequest;
import lol.pbu.z4j.model.ItamAssetTypeResponse;
import lol.pbu.z4j.model.ItamAssetTypesResponse;
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
public interface ItamAssetTypesClient {

    /**
     * {@summary Create Asset Type}
     * <p>Creates an asset type.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param itamAssetTypeCreateRequest (optional)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/it_asset_management/asset_types")
    Mono<@Valid ItamAssetTypeResponse> createItamAssetType(
        @Body @Nullable @Valid ItamAssetTypeCreateRequest itamAssetTypeCreateRequest
    );

    /**
     * {@summary Delete Asset Type}
     * <p>Deletes an asset type with the specified id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param assetTypeId <p>The id of the asset type</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/it_asset_management/asset_types/{asset_type_id}")
    Mono<Void> deleteItamAssetType(
        @PathVariable("asset_type_id") @NotNull String assetTypeId
    );

    /**
     * {@summary List Asset Types}
     * <p>Lists all asset types.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/it_asset_management/asset_types")
    Mono<@Valid ItamAssetTypesResponse> listItamAssetTypes();

    /**
     * {@summary Show Asset Type}
     * <p>Returns an asset type with the specified id.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param assetTypeId <p>The id of the asset type</p> (required)
     *
     * @return <p>Asset type</p> (status code 200)
     */
    @Get("/api/v2/it_asset_management/asset_types/{asset_type_id}")
    Mono<@Valid ItamAssetTypeResponse> showItamAssetType(
        @PathVariable("asset_type_id") @NotNull String assetTypeId
    );

    /**
     * {@summary Update Asset Type}
     * <p>Updates an existing asset type.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param assetTypeId <p>The id of the asset type</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Patch("/api/v2/it_asset_management/asset_types/{asset_type_id}")
    Mono<@Valid ItamAssetTypeResponse> updateItamAssetType(
        @PathVariable("asset_type_id") @NotNull String assetTypeId
    );
}