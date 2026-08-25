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
import lol.pbu.z4j.model.ItamAssetBulkJobRequest;
import lol.pbu.z4j.model.ItamAssetBulkJobResponse;
import lol.pbu.z4j.model.ItamAssetCreateRequest;
import lol.pbu.z4j.model.ItamAssetResponse;
import lol.pbu.z4j.model.ItamAssetsResponse;
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
public interface ItamAssetsClient {

    /**
     * {@summary Create Asset}
     * <p>Creates an asset.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param itamAssetCreateRequest (optional)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/it_asset_management/assets")
    Mono<@Valid ItamAssetResponse> createItamAsset(
        @Body @Nullable @Valid ItamAssetCreateRequest itamAssetCreateRequest
    );

    /**
     * {@summary Delete Asset}
     * <p>Deletes an asset with the specified id.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param assetId <p>The id of the asset</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/it_asset_management/assets/{asset_id}")
    Mono<Void> deleteItamAsset(
        @PathVariable("asset_id") @NotNull String assetId
    );

    /**
     * {@summary Asset Bulk Jobs}
     * <p>Queues a background job to perform bulk actions on up to 100 asset records per request. Takes a <code>job</code> object with two nested fields: * <code>action</code>, one of:     * <code>\"create\"</code>     * <code>\"update\"</code>     * <code>\"delete\"</code>     * <code>\"delete_by_external_id\"</code> * <code>items</code>     * For a <code>\"create\"</code> action, an array of JSON objects representing the assets being created     * For an <code>\"update\"</code> action, an array of JSON objects representing the assets being updated (must include <code>id</code> attribute)     * For a <code>\"delete\"</code> action, an array of strings representing Zendesk asset ids     * For a <code>\"delete_by_external_id\"</code> action, an array of strings representing external ids</p> <p>Note: For create and update actions, the <code>asset_type_id</code>, <code>status_id</code>, and <code>location_id</code> fields can be specified using either the ID or the name of the resource. For example, you can use <code>\"asset_type_id\": \"01K9BW852KHGF59W0TM02J2F6H\"</code> or <code>\"asset_type\": \"Laptop\"</code>.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Response</h4> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion. Only a certain number of jobs can be queued or running at the same time. See <a href=\"/api-reference/introduction/rate-limits/#job-limit\">Job limit</a> for more information.</p>
     *
     * @param itamAssetBulkJobRequest (optional)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/it_asset_management/assets/jobs")
    Mono<@Valid ItamAssetBulkJobResponse> itamAssetBulkJobs(
        @Body @Nullable @Valid ItamAssetBulkJobRequest itamAssetBulkJobRequest
    );

    /**
     * {@summary List Assets}
     * <p>Lists all assets for all asset types.</p> <h4>Pagination</h4> <ul> <li><a href=\"/api-reference/introduction/pagination/#cursor-pagination\">Cursor pagination</a> only.</li> </ul> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/it_asset_management/assets")
    Mono<@Valid ItamAssetsResponse> listItamAssets();

    /**
     * {@summary Show Asset}
     * <p>Returns the asset with the specified id.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param assetId <p>The id of the asset</p> (required)
     *
     * @return <p>Asset</p> (status code 200)
     */
    @Get("/api/v2/it_asset_management/assets/{asset_id}")
    Mono<@Valid ItamAssetResponse> showItamAsset(
        @PathVariable("asset_id") @NotNull String assetId
    );

    /**
     * {@summary Update Asset}
     * <p>Updates an individual asset. This request takes an <code>asset</code> object that specifies the properties to update, with custom field values nested within a <code>custom_field_values</code> object. Values are updated only for the properties specified in the request. Any asset properties that aren't specified in the request are unaffected, and their values are preserved for the asset.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param assetId <p>The id of the asset</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Patch("/api/v2/it_asset_management/assets/{asset_id}")
    Mono<@Valid ItamAssetResponse> updateItamAsset(
        @PathVariable("asset_id") @NotNull String assetId
    );
}