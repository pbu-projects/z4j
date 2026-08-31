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
import lol.pbu.z4j.model.BrandCreateRequest;
import lol.pbu.z4j.model.BrandResponse;
import lol.pbu.z4j.model.BrandUpdateRequest;
import lol.pbu.z4j.model.BrandsResponse;
import lol.pbu.z4j.model.HostMappingObject;
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
public interface BrandsClient {

    /**
     * {@summary Check Host Mapping Validity}
     * <p>Returns a JSON object determining whether a host mapping is valid for a given subdomain.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param hostMapping <p>The hostmapping to a brand, if any (only admins view this key)</p> (required)
     * @param subdomain <p>Subdomain for a given Zendesk account address</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/brands/check_host_mapping")
    Mono<@Valid HostMappingObject> checkHostMappingValidity(
        @QueryValue("host_mapping") @NotNull String hostMapping,
        @QueryValue("subdomain") @NotNull String subdomain
    );

    /**
     * {@summary Check Host Mapping Validity for an Existing Brand}
     * <p>Returns a JSON object determining whether a host mapping is valid for the given brand.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param brandId <p>The ID of the brand</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/brands/{brand_id}/check_host_mapping")
    Mono<@Valid HostMappingObject> checkHostMappingValidityForExistingBrand(
        @PathVariable("brand_id") @NotNull Long brandId
    );

    /**
     * {@summary Create Brand}
     * <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param brandCreateRequest (optional)
     *
     * @return <p>Successful response</p> (status code 201)
     */
    @Post("/api/v2/brands")
    Mono<@Valid BrandResponse> createBrand(
        @Body @Nullable @Valid BrandCreateRequest brandCreateRequest
    );

    /**
     * {@summary Delete a Brand}
     * <p>Deletes a brand.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param brandId <p>The ID of the brand</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/brands/{brand_id}")
    Mono<Void> deleteBrand(
        @PathVariable("brand_id") @NotNull Long brandId
    );

    /**
     * {@summary List Brands}
     * <p>Returns a list of all brands for your account sorted by name.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> <li>Agents with the <code>assign_tickets_to_any_brand</code> permission can list all brands for the account</li> <li>Agents without the <code>assign_tickets_to_any_brand</code> permission can only list brands they are members of</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/brands")
    Mono<@Valid BrandsResponse> listBrands();

    /**
     * {@summary Show a Brand}
     * <p>Returns a brand for your account.</p> <h4>Allowed for</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param brandId <p>The ID of the brand</p> (required)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Get("/api/v2/brands/{brand_id}")
    Mono<@Valid BrandResponse> showBrand(
        @PathVariable("brand_id") @NotNull Long brandId
    );

    /**
     * {@summary Update a Brand}
     * <p>Returns an updated brand.</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul> <h4>Updating a Brand's Image</h4> <p>A brand image can be updated by uploading a local file using the update brand endpoint. See the <strong>Using curl</strong> sections below for more information.</p>
     *
     * @param brandId <p>The ID of the brand</p> (required)
     * @param brandUpdateRequest (optional)
     *
     * @return <p>Successful response</p> (status code 200)
     */
    @Put("/api/v2/brands/{brand_id}")
    @Consumes({"application/json", "image/jpg", "image/png"})
    Mono<@Valid BrandResponse> updateBrand(
        @PathVariable("brand_id") @NotNull Long brandId,
        @Body @Nullable @Valid BrandUpdateRequest brandUpdateRequest
    );
}