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
import lol.pbu.z4j.model.CustomObjectFieldResponse;
import lol.pbu.z4j.model.CustomObjectFieldsCreateRequest;
import lol.pbu.z4j.model.CustomObjectFieldsResponse;
import lol.pbu.z4j.model.CustomObjectLimitsResponse;
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
public interface CustomObjectFieldsClient {

    /**
     * {@summary Create Custom Object Field}
     * <p>Creates any of the following custom field types:</p> <ul> <li>text (default when no \"type\" is specified)</li> <li>textarea</li> <li>checkbox</li> <li>date</li> <li>integer</li> <li>decimal</li> <li>regexp</li> <li>dropdown</li> <li>lookup</li> <li>multiselect</li> </ul> <p>See <a href=\"https://support.zendesk.com/hc/en-us/articles/203661866\">About custom field types</a> in Zendesk help.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param customObjectFieldsCreateRequest (optional)
     *
     * @return <p>Created</p> (status code 201)
     */
    @Post("/api/v2/custom_objects/{custom_object_key}/fields")
    Mono<@Valid CustomObjectFieldResponse> createCustomObjectField(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @Body @Nullable @Valid CustomObjectFieldsCreateRequest customObjectFieldsCreateRequest
    );

    /**
     * {@summary Custom Object Fields Limit}
     * <p>List the current count and the limit for a custom object's fields</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/limits/field_limit")
    Mono<@Valid CustomObjectLimitsResponse> customObjectFieldsLimit(
        @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * {@summary Delete Custom Object Field}
     * <p>Deletes a field with the specified key. Note: You can't delete standard fields.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param customObjectFieldKeyOrId <p>The key or id of a custom object field</p> (required)
     *
     * @return <p>No content response</p> (status code 204)
     */
    @Delete("/api/v2/custom_objects/{custom_object_key}/fields/{custom_object_field_key_or_id}")
    Mono<Void> deleteCustomObjectField(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("custom_object_field_key_or_id") @NotNull String customObjectFieldKeyOrId
    );

    /**
     * {@summary List Custom Object Fields}
     * <p>Lists all undeleted custom fields for the specified object.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param includeStandardFields <p>Include standard fields if true. Exclude them if false</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/fields")
    Mono<@Valid CustomObjectFieldsResponse> listCustomObjectFields(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @QueryValue("include_standard_fields") @Nullable Boolean includeStandardFields
    );

    /**
     * {@summary Reorder Custom Fields of an Object}
     * <p>Sets a preferred order of custom fields for a specific object by providing field ids in the desired order.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     *
     * @return <p>Reordered</p> (status code 200)
     */
    @Put("/api/v2/custom_objects/{custom_object_key}/fields/reorder")
    Mono<@NotNull String> reorderCustomObjectFields(
        @PathVariable("custom_object_key") @NotNull String customObjectKey
    );

    /**
     * {@summary Show Custom Object Field}
     * <p>Returns a custom field for a specific object using a provided key or id of the field.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param customObjectFieldKeyOrId <p>The key or id of a custom object field</p> (required)
     *
     * @return <p>Custom Object Field</p> (status code 200)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/fields/{custom_object_field_key_or_id}")
    Mono<@Valid CustomObjectFieldResponse> showCustomObjectField(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("custom_object_field_key_or_id") @NotNull String customObjectFieldKeyOrId
    );

    /**
     * {@summary Update Custom Object Field}
     * <p>Updates individual custom object fields. The updating rules are as follows: * Takes a <code>custom_object_field</code> object that specifies the properties to update. * The <code>key</code> property cannot be updated. * If updating a standard field, only the <code>title</code>, <code>description</code>, and <code>properties</code> attributes can be updated. * Standard name field is always required. Therefore, the <code>required</code> property for standard name field isn't editable. * The <code>properties</code> parameter is comprised of four parts and can't be changed if any records exist for the object.     * <code>autoincrement_enabled</code>: A Boolean that enables and disables autonumbering. Must be false if is_unique is true.     * <code>autoincrement_prefix</code>: A string value that is used as a prefix to the autogenerated numbers. It can't exceed 30 characters.     * <code>autoincrement_padding</code>: An integer specifying the starting number of digits in the autogenerated numbers. This value may be between 0-9. However, if you create records in excess of of these digits, additional digits are added as necessary.     * <code>autoincrement_next_sequence</code>: An integer that will be used as the next number in the autonumbering sequence. It can't be negative or less than the current autonumbering value.     * <code>is_unique</code>: A Boolean that enforces uniqueness for manually entered record names. When true, custom object record names must be unique. Must be false if autoincrement_enabled is true.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param customObjectFieldKeyOrId <p>The key or id of a custom object field</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Patch("/api/v2/custom_objects/{custom_object_key}/fields/{custom_object_field_key_or_id}")
    Mono<@Valid CustomObjectFieldResponse> updateCustomObjectField(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("custom_object_field_key_or_id") @NotNull String customObjectFieldKeyOrId
    );
}