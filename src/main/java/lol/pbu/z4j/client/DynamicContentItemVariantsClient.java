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
import lol.pbu.z4j.model.DynamicContentVariantResponse;
import lol.pbu.z4j.model.DynamicContentVariantsResponse;
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
public interface DynamicContentItemVariantsClient {

    /**
     * {@summary Create Variant}
     * <p>You can only create one variant for each locale id. If a locale variant already exists, the request is rejected.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param dynamicContentItemId <p>The ID of the dynamic content item</p> (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/dynamic_content/items/{dynamic_content_item_id}/variants")
    Mono<@Valid DynamicContentVariantResponse> createDynamicContentVariant(
        @PathVariable("dynamic_content_item_id") @NotNull Long dynamicContentItemId
    );

    /**
     * {@summary Create Many Variants}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param dynamicContentItemId <p>The ID of the dynamic content item</p> (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/dynamic_content/items/{dynamic_content_item_id}/variants/create_many")
    Mono<@Valid DynamicContentVariantsResponse> createManyDynamicContentVariants(
        @PathVariable("dynamic_content_item_id") @NotNull Long dynamicContentItemId
    );

    /**
     * {@summary Delete Variant}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param dynamicContentItemId <p>The ID of the dynamic content item</p> (required)
     * @param dynamicContentVariantId <p>The ID of the variant</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/dynamic_content/items/{dynamic_content_item_id}/variants/{dynamic_content_variant_id}")
    Mono<Void> deleteDynamicContentVariant(
        @PathVariable("dynamic_content_item_id") @NotNull Long dynamicContentItemId,
        @PathVariable("dynamic_content_variant_id") @NotNull Long dynamicContentVariantId
    );

    /**
     * {@summary List Variants}
     * <p>Returns all the variants of the specified dynamic content item.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents who have permission to manage dynamic content</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param dynamicContentItemId <p>The ID of the dynamic content item</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/dynamic_content/items/{dynamic_content_item_id}/variants")
    Mono<@Valid DynamicContentVariantsResponse> dynamicContentListVariants(
        @PathVariable("dynamic_content_item_id") @NotNull Long dynamicContentItemId
    );

    /**
     * {@summary Show Variant}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param dynamicContentItemId <p>The ID of the dynamic content item</p> (required)
     * @param dynamicContentVariantId <p>The ID of the variant</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/dynamic_content/items/{dynamic_content_item_id}/variants/{dynamic_content_variant_id}")
    Mono<@Valid DynamicContentVariantResponse> showDynamicContentVariant(
        @PathVariable("dynamic_content_item_id") @NotNull Long dynamicContentItemId,
        @PathVariable("dynamic_content_variant_id") @NotNull Long dynamicContentVariantId
    );

    /**
     * {@summary Update Variant}
     * <p>Updates the specified variant. You don't need to include all the properties. If you just want to update content, for example, then include just that.</p> <p>You can't switch the active state of the default variant of an item. Similarly, you can't switch the default to false if the variant is the default. You must make another variant default instead.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param dynamicContentItemId <p>The ID of the dynamic content item</p> (required)
     * @param dynamicContentVariantId <p>The ID of the variant</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/dynamic_content/items/{dynamic_content_item_id}/variants/{dynamic_content_variant_id}")
    Mono<@Valid DynamicContentVariantResponse> updateDynamicContentVariant(
        @PathVariable("dynamic_content_item_id") @NotNull Long dynamicContentItemId,
        @PathVariable("dynamic_content_variant_id") @NotNull Long dynamicContentVariantId
    );

    /**
     * {@summary Update Many Variants}
     * <p>Updates one or more variants. See <a href=\"/api-reference/ticketing/ticket-management/dynamic_content_item_variants/#update-variant\">Update Variant</a>.</p> <p>You must specify the variants by id in the body. To get the variant ids, see <a href=\"/api-reference/ticketing/ticket-management/dynamic_content_item_variants/#list-variants\">List Variants</a>.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param dynamicContentItemId <p>The ID of the dynamic content item</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/dynamic_content/items/{dynamic_content_item_id}/variants/update_many")
    Mono<@Valid DynamicContentVariantsResponse> updateManyDynamicContentVariants(
        @PathVariable("dynamic_content_item_id") @NotNull Long dynamicContentItemId
    );
}