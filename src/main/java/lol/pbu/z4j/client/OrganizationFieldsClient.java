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
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.OrganizationFieldResponse;
import lol.pbu.z4j.model.OrganizationFieldsResponse;
import lol.pbu.z4j.model.ShowOrganizationFieldOrganizationFieldIdParameter;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface OrganizationFieldsClient {

    /**
     * {@summary Create Organization Field}
     * <p>Creates any of the following custom field types:</p> <ul> <li>text (default when no \"type\" is specified)</li> <li>textarea</li> <li>checkbox</li> <li>date</li> <li>integer</li> <li>decimal</li> <li>regexp</li> <li>dropdown</li> <li>lookup</li> <li>multiselect</li> </ul> <p>See <a href=\"https://support.zendesk.com/hc/en-us/articles/203661866\">About custom field types</a> in Zendesk help.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/organization_fields")
    Mono<@Valid OrganizationFieldResponse> createOrganizationField();

    /**
     * {@summary Delete Organization Field}
     * <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param organizationFieldId <p>The ID or key of the organization field</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/organization_fields/{organization_field_id}")
    Mono<Void> deleteOrganizationField(
        @PathVariable("organization_field_id") @NotNull Long organizationFieldId
    );

    /**
     * {@summary List Organization Fields}
     * <p>Returns a list of custom organization fields in your account. Fields are returned in the order that you specify in your organization fields configuration in Zendesk Support. Clients should cache this resource for the duration of their API usage and map the key for each organization field to the values returned under the <code>organization_fields</code> attribute on the <a href=\"/api-reference/ticketing/organizations/organizations/\">organization</a> resource.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organization_fields")
    Mono<@Valid OrganizationFieldsResponse> listOrganizationFields();

    /**
     * {@summary Reorder Organization Field}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/organization_fields/reorder")
    Mono<@NotNull String> reorderOrganizationField();

    /**
     * {@summary Show Organization Field}
     * <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param organizationFieldId <p>The ID or key of the organization field</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/organization_fields/{organization_field_id}")
    Mono<@Valid OrganizationFieldResponse> showOrganizationField(
        @PathVariable("organization_field_id") @NotNull Long organizationFieldId
    );

    /**
     * {@summary Update Organization Field}
     * <h4>Updating a Dropdown (Tagger) or Multiselect Field</h4> <p>Dropdown and multiselect fields return an array of <code>custom_field_options</code> which specify the name, value, and order of dropdown or multiselect options. When updating a dropdown or multiselect field, note the following information:</p> <ul> <li>All options must be passed on update. Options that are not passed will be removed. As a result, these values will be removed from any organizations</li> <li>To create a new option, pass a null <code>id</code> along with the <code>name</code> and <code>value</code></li> <li>To update an existing option, pass its <code>id</code> along with the <code>name</code> and <code>value</code></li> <li>To reorder an option, reposition it in the <code>custom_field_options</code> array relative to the other options</li> <li>To remove an option, omit it from the list of options upon update</li> </ul> <h4>Example Request</h4> <p><code>bash curl https://{subdomain}.zendesk.com/api/v2/organization_fields/{organization_field_id}.json \\   -H \"Content-Type: application/json\" -X PUT \\   -d '{\"organization_field\": {\"custom_field_options\": [{\"id\": 124, \"name\": \"Option 2\", \"value\": \"option_2\"}, {\"id\": 123, \"name\": \"Option 1\", \"value\": \"option_1\"}, {\"id\": 125, \"name\": \"Option 3\", \"value\": \"option_3\"}]}}' \\   -v -u {email_address}/token:{api_token}</code></p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param organizationFieldId <p>The ID or key of the organization field</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/organization_fields/{organization_field_id}")
    Mono<@Valid OrganizationFieldResponse> updateOrganizationField(
        @PathVariable("organization_field_id") @NotNull Long organizationFieldId
    );
}