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
import lol.pbu.z4j.model.DynamicContentResponse;
import lol.pbu.z4j.model.DynamicContentsResponse;
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
public interface DynamicContentClient {

    /**
     * {@summary Create Item}
     * <p>Create a new content item, with one or more variants in the item's <code>variants</code> array. See <a href=\"#specifying-item-variants\">Specifying item variants</a>.</p> <p>The <code>default_locale_id</code> and variant <code>locale_id</code> values must be one of the locales the account has active. You can get the list with the <a href=\"/api-reference/ticketing/account-configuration/locales/#list-locales\">List Locales</a> endpoint.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/dynamic_content/items")
    Mono<@Valid DynamicContentResponse> createDynamicContent();

    /**
     * {@summary Delete Item}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param dynamicContentItemId <p>The ID of the dynamic content item</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/dynamic_content/items/{dynamic_content_item_id}")
    Mono<Void> deleteDynamicContentItem(
        @PathVariable("dynamic_content_item_id") @NotNull Long dynamicContentItemId
    );

    /**
     * {@summary List Items}
     * <p>Returns a list of all dynamic content items for your account if accessed as an admin or agents who have permission to manage dynamic content.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/dynamic_content/items")
    Mono<@Valid DynamicContentsResponse> listDynamicContents();

    /**
     * {@summary Show Item}
     * <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param dynamicContentItemId <p>The ID of the dynamic content item</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/dynamic_content/items/{dynamic_content_item_id}")
    Mono<@Valid DynamicContentResponse> showDynamicContentItem(
        @PathVariable("dynamic_content_item_id") @NotNull Long dynamicContentItemId
    );

    /**
     * {@summary Show Many Items}
     * <h4>Stability</h4> <ul> <li>Development</li> </ul> <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param identifiers <p>Identifiers for the dynamic contents</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/dynamic_content/items/show_many")
    Mono<@Valid DynamicContentsResponse> showManyDynamicContents(
        @QueryValue("identifiers") @Nullable String identifiers
    );

    /**
     * {@summary Update Item}
     * <p>The only attribute you can change is the name.</p> <p>To add a variant to the item, or to update or delete the variants of the item, use the <a href=\"/api-reference/ticketing/ticket-management/dynamic_content_item_variants/#update-many-variants\">Item Variants API</a>.</p> <h4>Allowed For</h4> <ul> <li>Admins, Agents</li> </ul>
     *
     * @param dynamicContentItemId <p>The ID of the dynamic content item</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/dynamic_content/items/{dynamic_content_item_id}")
    Mono<@Valid DynamicContentResponse> updateDynamicContentItem(
        @PathVariable("dynamic_content_item_id") @NotNull Long dynamicContentItemId
    );
}