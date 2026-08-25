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
import lol.pbu.z4j.model.CustomFieldOptionResponse;
import lol.pbu.z4j.model.CustomFieldOptionsResponse;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.ShowOrganizationFieldOrganizationFieldIdParameter;
import lol.pbu.z4j.model.UserFieldResponse;
import lol.pbu.z4j.model.UserFieldsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface UserFieldsClient {

    /**
     * {@summary Create or Update a User Field Option}
     * <p>Creates a new option or updates an existing option for the given drop-down user field.</p> <p>To update an option, include the id of the option in the <code>custom_field_option</code> object. Example: <code>{\"custom_field_option\": {\"id\": 10002, \"name\": \"Pineapples\", ... }</code>. If an option exists for the given ID, the option will be updated. Otherwise, a new option will be created.</p> <h4>Response</h4> <p>Returns one of the following status codes:</p> <ul> <li>200 with <code>Location: /api/v2/user_fields/{user_field_id}/options.json</code> if the user field option already exists in the database</li> <li>201 with <code>Location: /api/v2/user_fields/{user_field_id}/options.json</code> if the user field option is new</li> </ul> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param userFieldId <p>The ID or key of the user field</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/user_fields/{user_field_id}/options")
    Mono<@Valid CustomFieldOptionResponse> createOrUpdateUserFieldOption(
        @PathVariable("user_field_id") @NotNull Integer userFieldId
    );

    /**
     * {@summary Create User Field}
     * <p>Creates any of the following custom field types:</p> <ul> <li>text (default when no \"type\" is specified)</li> <li>textarea</li> <li>checkbox</li> <li>date</li> <li>integer</li> <li>decimal</li> <li>regexp</li> <li>dropdown</li> <li>lookup</li> <li>multiselect</li> </ul> <p>See <a href=\"https://support.zendesk.com/hc/en-us/articles/203661866\">About custom field types</a> in Zendesk help.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/user_fields")
    Mono<@Valid UserFieldResponse> createUserField();

    /**
     * {@summary Delete User Field}
     * <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param userFieldId <p>The ID or key of the user field</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/user_fields/{user_field_id}")
    Mono<Void> deleteUserField(
        @PathVariable("user_field_id") @NotNull Integer userFieldId
    );

    /**
     * {@summary Delete User Field Option}
     * <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param userFieldId <p>The ID or key of the user field</p> (required)
     * @param userFieldOptionId <p>The ID of the user field option</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/user_fields/{user_field_id}/options/{user_field_option_id}")
    Mono<Void> deleteUserFieldOption(
        @PathVariable("user_field_id") @NotNull Integer userFieldId,
        @PathVariable("user_field_option_id") @NotNull Integer userFieldOptionId
    );

    /**
     * {@summary List User Field Options}
     * <p>Returns a list of custom user field options for the given dropdown user field.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userFieldId <p>The ID or key of the user field</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/user_fields/{user_field_id}/options")
    Mono<@Valid CustomFieldOptionsResponse> listUserFieldOptions(
        @PathVariable("user_field_id") @NotNull Integer userFieldId
    );

    /**
     * {@summary List User Fields}
     * <p>Returns a list of custom user fields in your account. Fields are returned in the order that you specify in your user fields configuration in Zendesk Support. Clients should cache this resource for the duration of their API usage and map the key for each User Field to the values returned under the <code>user_fields</code> attribute on the <a href=\"/api-reference/ticketing/users/users/\">User</a> resource.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/user_fields")
    Mono<@Valid UserFieldsResponse> listUserFields();

    /**
     * {@summary Reorder User Field}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/user_fields/reorder")
    Mono<@NotNull String> reorderUserField();

    /**
     * {@summary Show User Field}
     * <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userFieldId <p>The ID or key of the user field</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/user_fields/{user_field_id}")
    Mono<@Valid UserFieldResponse> showUserField(
        @PathVariable("user_field_id") @NotNull Integer userFieldId
    );

    /**
     * {@summary Show a User Field Option}
     * <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param userFieldId <p>The ID or key of the user field</p> (required)
     * @param userFieldOptionId <p>The ID of the user field option</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/user_fields/{user_field_id}/options/{user_field_option_id}")
    Mono<@Valid CustomFieldOptionResponse> showUserFieldOption(
        @PathVariable("user_field_id") @NotNull Integer userFieldId,
        @PathVariable("user_field_option_id") @NotNull Integer userFieldOptionId
    );

    /**
     * {@summary Update User Field}
     * <h4>Updating a Dropdown (Tagger) or Multiselect Field</h4> <p>Dropdown and multiselect fields return an array of <code>custom_field_options</code> which specify the name, value, and order of the list of dropdown or multiselect options. Understand the following behavior when updating a dropdown or multiselect field:</p> <ul> <li>All options must be passed on update. Options that are not passed will be removed. As a result, these values will be removed from any organizations.</li> <li>To create a new option, pass a null <code>id</code> along with <code>name</code> and <code>value</code>.</li> <li>To update an existing option, pass its <code>id</code> along with <code>name</code> and <code>value</code>.</li> <li>To re-order an option, reposition it in the <code>custom_field_options</code> array relative to the other options.</li> <li>To remove an option, omit it from the list of options upon update.</li> </ul> <h4>Example Request</h4> <p><code>bash curl https://{subdomain}.zendesk.com/api/v2/user_fields/{user_field_id}.json \\   -H \"Content-Type: application/json\" -X PUT \\   -d '{\"user_field\": {\"custom_field_options\": [{\"id\": 124, \"name\": \"Option 2\", \"value\": \"option_2\"}, {\"id\": 123, \"name\": \"Option 1\", \"value\": \"option_1\"}, {\"id\": 125, \"name\": \"Option 2\", \"value\": \"option_3\"}]}}' \\   -v -u {email_address}/token:{api_token}</code></p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param userFieldId <p>The ID or key of the user field</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/user_fields/{user_field_id}")
    Mono<@Valid UserFieldResponse> updateUserField(
        @PathVariable("user_field_id") @NotNull Integer userFieldId
    );
}