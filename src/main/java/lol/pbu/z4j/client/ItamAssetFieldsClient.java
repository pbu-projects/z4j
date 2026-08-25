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
import lol.pbu.z4j.model.ItamAssetFieldCreateRequest;
import lol.pbu.z4j.model.ItamAssetFieldResponse;
import lol.pbu.z4j.model.ItamAssetTypeFieldsResponse;
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
public interface ItamAssetFieldsClient {

    /**
     * {@summary Create Asset Field}
     * <p>Creates an asset field for an individual asset type.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param assetTypeId <p>The id of the asset type</p> (required)
     * @param itamAssetFieldCreateRequest (optional)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/it_asset_management/asset_types/{asset_type_id}/fields")
    Mono<@Valid ItamAssetFieldResponse> createItamAssetTypeField(
        @PathVariable("asset_type_id") @NotNull String assetTypeId,
        @Body @Nullable @Valid ItamAssetFieldCreateRequest itamAssetFieldCreateRequest
    );

    /**
     * {@summary Delete Asset Field}
     * <p>Deletes an asset field with the specified id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param assetTypeId <p>The id of the asset type</p> (required)
     * @param assetTypeFieldId <p>The id of the asset field</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/it_asset_management/asset_types/{asset_type_id}/fields/{asset_type_field_id}")
    Mono<Void> deleteItamAssetTypeField(
        @PathVariable("asset_type_id") @NotNull String assetTypeId,
        @PathVariable("asset_type_field_id") @NotNull String assetTypeFieldId
    );

    /**
     * {@summary List Asset Fields}
     * <p>Lists all standard and custom fields for an asset type.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param assetTypeId <p>The id of the asset type</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/it_asset_management/asset_types/{asset_type_id}/fields")
    Mono<@Valid ItamAssetTypeFieldsResponse> listItamAssetTypeFields(
        @PathVariable("asset_type_id") @NotNull String assetTypeId
    );

    /**
     * {@summary Show Asset Field}
     * <p>Returns an asset field with the specified id.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param assetTypeId <p>The id of the asset type</p> (required)
     * @param assetTypeFieldId <p>The id of the asset field</p> (required)
     *
     * @return <p>Asset field</p> (status code 200)
     */
    @Get("/api/v2/it_asset_management/asset_types/{asset_type_id}/fields/{asset_type_field_id}")
    Mono<@Valid ItamAssetFieldResponse> showItamAssetTypeField(
        @PathVariable("asset_type_id") @NotNull String assetTypeId,
        @PathVariable("asset_type_field_id") @NotNull String assetTypeFieldId
    );

    /**
     * {@summary Update Asset Field}
     * <p>Updates an existing asset field with the specified id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param assetTypeId <p>The id of the asset type</p> (required)
     * @param assetTypeFieldId <p>The id of the asset field</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Patch("/api/v2/it_asset_management/asset_types/{asset_type_id}/fields/{asset_type_field_id}")
    Mono<@Valid ItamAssetFieldResponse> updateItamAssetTypeField(
        @PathVariable("asset_type_id") @NotNull String assetTypeId,
        @PathVariable("asset_type_field_id") @NotNull String assetTypeFieldId
    );
}