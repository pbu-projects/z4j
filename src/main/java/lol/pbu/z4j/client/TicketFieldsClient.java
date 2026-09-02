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
import lol.pbu.z4j.model.TicketFieldCountResponse;
import lol.pbu.z4j.model.TicketFieldResponse;
import lol.pbu.z4j.model.TicketFieldsResponse;
import lol.pbu.z4j.model.TicketFieldsShowManyResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TicketFieldsClient {

    /**
     * {@summary Count Ticket Fields}
     * <p>Returns an approximate count of system and custom ticket fields in the account. If the count exceeds 100,000, the count will return a cached result.  This cached result will update every 24 hours.</p> <p>The <code>count[refreshed_at]</code> property is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>count[refreshed_at]</code> may occasionally be null. This indicates that the count is being updated in the background, and <code>count[value]</code> is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Count of ticket fields</p> (status code 200)
     */
    @Get("/api/v2/ticket_fields/count")
    Mono<@Valid TicketFieldCountResponse> countTicketFields();

    /**
     * {@summary Create or Update Ticket Field Option}
     * <p>Creates or updates an option for the given drop-down ticket field.</p> <p>To update an option, include the id of the option in the <code>custom_field_option</code> object. Example:</p> <p><code>{\"custom_field_option\": {\"id\": 10002, \"name\": \"Pineapples\", ... }</code></p> <p>If an option exists for the given ID, the option will be updated. Otherwise, a new option will be created.</p> <h4>Response</h4> <p>Returns one of the following status codes:</p> <ul> <li>200 with <code>Location: /api/v2/ticket_fields/{ticket_field_id}/options.json</code> if the ticket field option already exists in the database</li> <li>201 with <code>Location: /api/v2/ticket_fields/{ticket_field_id}/options.json</code> if the ticket field option is new</li> </ul> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Rate Limit</h4> <p>You can make 100 requests every 1 minute using this endpoint. The rate limiting mechanism behaves as described in <a href=\"/api-reference/ticketing/account-configuration/usage_limits/#monitoring-your-request-activity\">Monitoring your request activity</a> in the API introduction.</p> <h4>Field Option Limits</h4> <ul> <li>2000 options per ticket field</li> </ul>
     *
     * @param ticketFieldId <p>The ID of the ticket field</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/ticket_fields/{ticket_field_id}/options")
    Mono<@Valid CustomFieldOptionResponse> createOrUpdateTicketFieldOption(
        @PathVariable("ticket_field_id") @NotNull Long ticketFieldId
    );

    /**
     * {@summary Create Ticket Field}
     * <p>Creates any of the following custom field types:</p> <p>| Custom field type | Description                                                                                                                                                     | |-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------| | text              | Default custom field type when <code>type</code> is not specified                                                                                                          | | textarea          | For multi-line text                                                                                                                                             | | checkbox          | To capture a boolean value. Allowed values are true or false. Optionally, you can specify a tag to be added to the ticket when the value is true.               | | date              | Example: 2021-04-16                                                                                                                                             | | integer           | String composed of numbers. May contain an optional decimal point                                                                                               | | decimal           | For numbers containing decimals                                                                                                                                 | | regexp            | Matches the Regex pattern found in the custom field settings                                                                                                    | | partialcreditcard | A credit card number. Only the last 4 digits are retained                                                                                                       | | multiselect       | Enables users to choose multiple options from a dropdown menu. It contains one or more tag values belonging to the field's options.                             | | tagger            | Single-select dropdown menu. It contains one or more tag values belonging to the field's options. Example: ( {\"id\": 21938362, \"value\": [\"hd_3000\", \"hd_5555\"]}) | | lookup            | A field to create a relationship (see <a href=\"/api-reference/ticketing/lookup_relationships/lookup_relationships/\">lookup relationships</a>) to another object such as a user, ticket, or organization |</p> <p><strong>Note</strong>: Tags can't be re-used across custom ticket fields. For example, if you configure a tag for a checkbox field, you can't use that tag value for a dropdown (tagger) field option. The use of tags isn't validated and can prevent editing in the future.</p> <p>See <a href=\"https://support.zendesk.com/hc/en-us/articles/203661866\">About custom field types</a> in the Zendesk Help Center.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Field limits</h4> <p>We recommend the following best practices for ticket fields limits. Creating more than these amounts can affect performance.</p> <ul> <li>400 ticket fields per account if your account doesn't have ticket forms</li> <li>400 ticket fields per ticket form if your account has ticket forms</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/ticket_fields")
    Mono<TicketFieldResponse> createTicketField();

    /**
     * {@summary Delete Ticket Field}
     * <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketFieldId <p>The ID of the ticket field</p> (required)
     * @param creator <p>If true, displays the <code>creator_user_id</code> and <code>creator_app_name</code> properties. If the ticket field is created  by an app, <code>creator_app_name</code> is the name of the app and <code>creator_user_id</code> is <code>-1</code>. If the ticket field  is not created by an app, then <code>creator_app_name</code> is null</p> (optional)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/ticket_fields/{ticket_field_id}")
    Mono<Void> deleteTicketField(
        @PathVariable("ticket_field_id") @NotNull Long ticketFieldId,
        @QueryValue("creator") @Nullable Boolean creator
    );

    /**
     * {@summary Delete Ticket Field Option}
     * <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketFieldId <p>The ID of the ticket field</p> (required)
     * @param ticketFieldOptionId <p>The ID of the ticket field option</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/ticket_fields/{ticket_field_id}/options/{ticket_field_option_id}")
    Mono<Void> deleteTicketFieldOption(
        @PathVariable("ticket_field_id") @NotNull Long ticketFieldId,
        @PathVariable("ticket_field_option_id") @NotNull Long ticketFieldOptionId
    );

    /**
     * {@summary List Ticket Field Options}
     * <p>Returns a list of custom ticket field options for the given drop-down ticket field.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p>
     *
     * @param ticketFieldId <p>The ID of the ticket field</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_fields/{ticket_field_id}/options")
    Mono<@Valid CustomFieldOptionsResponse> listTicketFieldOptions(
        @PathVariable("ticket_field_id") @NotNull Long ticketFieldId
    );

    /**
     * {@summary List Ticket Fields}
     * <p>Returns a list of all system and custom ticket fields in your account.</p> <p>For end users, only the ticket fields with visible_in_portal set to true are returned.</p> <p>Cursor pagination returns a maximum of 100 records per page and fields are returned in the order specified by their id.</p> <p>If the results are not paginated, every field is returned in the response and fields are returned in the order specified by the position.</p> <p>You can adjust the position of ticket fields by:</p> <ul> <li>Using the <a href=\"/api-reference/ticketing/tickets/ticket_fields/#update-ticket-field\">Update Ticket Field</a> endpoint</li> <li>Using the <a href=\"/api-reference/ticketing/tickets/ticket_fields/#reorder-ticket-fields\">Reorder Ticket Fields</a> endpoint</li> <li>Ticket Fields page in the Admin Center (<strong>Admin Center</strong> &gt; <strong>Manage</strong> &gt; <strong>Ticket</strong> &gt; <strong>Fields</strong> &gt; <strong>Actions</strong> &gt; <strong>Edit order</strong>)</li> </ul> <p>These adjustments determine the order in which fields are displayed in various locations. For accounts without access to multiple ticket forms, the order will also be used to display field values within tickets. However, for accounts with access to multiple ticket forms, the field order on the ticket page is defined within each form.</p> <p>Consider caching this resource to use with the <a href=\"/api-reference/ticketing/tickets/tickets/#json-format\">Tickets</a> API.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>No pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name             | Will sideload | ---------------- | ------------- | users            | The user or users that created the ticket field</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @param locale <p>Forces the <code>title_in_portal</code> property to return a dynamic content variant for the specified locale.  Only accepts <a href=\"/api-reference/ticketing/account-configuration/locales/#list-locales\">active locale ids</a>. Example: <code>locale=\"de\"</code>.</p> (optional)
     * @param creator <p>Displays the <code>creator_user_id</code> and <code>creator_app_name</code> properties. If the ticket field is created  by an app, <code>creator_app_name</code> is the name of the app and <code>creator_user_id</code> is <code>-1</code>. If the ticket field  is not created by an app, <code>creator_app_name</code> is null</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_fields")
    Mono<@Valid TicketFieldsResponse> listTicketFields(
        @QueryValue("locale") @Nullable String locale,
        @QueryValue("creator") @Nullable Boolean creator
    );

    /**
     * {@summary Reorder Ticket Fields}
     * <h4>Allowed For</h4> <ul> <li>Admins</li> </ul> <h4>Request Parameters</h4> <p>You can pass in the following parameter in the payload:</p> <p>| Name                | Type   | Comment | ------------------- | ------ | -------- | ticket_field_ids    | array  | An array of ticket field ids. Example: \"[2, 23, 46, 50]\". Not all ticket_field_ids are necessary in the payload; only those provided will be assigned to the first positions. Missing IDs will be assigned incremental positions automatically.</p>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/ticket_fields/reorder")
    Mono<@NotNull String> reorderTicketFields();

    /**
     * {@summary Show Many Ticket Fields}
     * <p>Returns multiple ticket fields in a single request.</p> <p>Provide either: - <code>ids</code> — a comma-separated list of ticket field IDs, or - <code>keys</code> — a comma-separated list of ticket field keys</p> <p>Up to 100 values are accepted.</p> <p>The response payload matches the List Ticket Fields <a href=\"/api-reference/ticketing/tickets/ticket_fields/#example-responses\">response format</a>.</p> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name  | Will sideload                         | |-------|---------------------------------------| | users | The user or users that created fields |</p> <h4>Allowed For</h4> <ul> <li>Anyone</li> </ul>
     *
     * @param ticketFieldIds <p>Comma-separated list of ticket field IDs. Required if <code>keys</code> is not provided.</p> (optional)
     * @param ticketFieldKeys <p>Comma-separated list of ticket field keys. Required if <code>ids</code> is not provided.</p> (optional)
     * @param creator <p>If true, displays the <code>creator_user_id</code> and <code>creator_app_name</code> properties. If the ticket field is created  by an app, <code>creator_app_name</code> is the name of the app and <code>creator_user_id</code> is <code>-1</code>. If the ticket field  is not created by an app, then <code>creator_app_name</code> is null</p> (optional)
     * @param excludeSubSelectionOptions <p>Excludes <code>sub_selection_options</code> from option payloads to reduce response size.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_fields/show_many")
    Mono<@Valid TicketFieldsShowManyResponse> showManyTicketFields(
        @QueryValue("ticket_field_ids") @Nullable String ticketFieldIds,
        @QueryValue("ticket_field_keys") @Nullable String ticketFieldKeys,
        @QueryValue("creator") @Nullable Boolean creator,
        @QueryValue("exclude_sub_selection_options") @Nullable Boolean excludeSubSelectionOptions
    );

    /**
     * {@summary Show Ticket Field Option}
     * <h4>Allowed for</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketFieldId <p>The ID of the ticket field</p> (required)
     * @param ticketFieldOptionId <p>The ID of the ticket field option</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_fields/{ticket_field_id}/options/{ticket_field_option_id}")
    Mono<@Valid CustomFieldOptionResponse> showTicketFieldOption(
        @PathVariable("ticket_field_id") @NotNull Long ticketFieldId,
        @PathVariable("ticket_field_option_id") @NotNull Long ticketFieldOptionId
    );

    /**
     * {@summary Show Ticket Field}
     * <h4>Allowed for</h4> <ul> <li>Agents</li> </ul> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name             | Will sideload | ---------------- | ------------- | users            | The user or users that created the ticket field</p>
     *
     * @param ticketFieldId <p>The ID of the ticket field</p> (required)
     * @param creator <p>If true, displays the <code>creator_user_id</code> and <code>creator_app_name</code> properties. If the ticket field is created  by an app, <code>creator_app_name</code> is the name of the app and <code>creator_user_id</code> is <code>-1</code>. If the ticket field  is not created by an app, then <code>creator_app_name</code> is null</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/ticket_fields/{ticket_field_id}")
    Mono<TicketFieldResponse> showTicketfield(
        @PathVariable("ticket_field_id") @NotNull Long ticketFieldId,
        @QueryValue("creator") @Nullable Boolean creator
    );

    /**
     * {@summary Update Ticket Field}
     * <h4>Updating drop-down field options</h4> <p>You can also use the update endpoint to add, update, or remove options in a drop-down custom field. Updating field options for multi-select fields works exactly the same as drop-down field options.</p> <p><strong>Important</strong>: Unless you want to remove some options, you must specify all existing options in any update request. Omitting an option removes it from the drop-down field, which removes its values from any tickets or macros.</p> <p>Use the <code>custom_field_options</code> attribute to update the options. The attribute consists of an array of option objects, with each object consisting of a <code>name</code> and <code>value</code> property. The properties correspond to the \"Title\" and \"Tag\" text boxes in the admin interface. Example request body:</p> <p><code>json {\"ticket_field\": {     \"custom_field_options\": [       {\"name\": \"Apple Pie\", \"value\": \"apple\"},       {\"name\": \"Pecan Pie\", \"value\": \"pecan\"}     ]   } }</code></p> <h4>Example Request</h4> <p><code>bash curl https://{subdomain}.zendesk.com/api/v2/ticket_fields/{id}.json \\   -d '{\"ticket_field\": {\"custom_field_options\": [{\"name\": \"Apple Pie\", \"value\": \"apple\"}, {\"name\": \"Pecan Pie\", \"value\": \"pecan\"}]}}' \\   -H \"Content-Type: application/json\" -X PUT \\   -v -u {email_address}/token:{api_token}</code></p> <h4>Example Response</h4> <p>```http Status: 200 OK</p> <p>{   \"ticket_field\": {     \"id\":21938362,     \"type\":\"tagger\",     \"title\":\"Pies\",     ...     \"custom_field_options\": [       {         \"id\":21029772,         \"name\":\"Apple Pie\",         \"raw_name\":\"Apple Pie\",         \"value\":\"apple\",         \"default\":false       },       ...     ]   } } ```</p> <h4>Allowed for</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketFieldId <p>The ID of the ticket field</p> (required)
     * @param creator <p>If true, displays the <code>creator_user_id</code> and <code>creator_app_name</code> properties. If the ticket field is created  by an app, <code>creator_app_name</code> is the name of the app and <code>creator_user_id</code> is <code>-1</code>. If the ticket field  is not created by an app, then <code>creator_app_name</code> is null</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/ticket_fields/{ticket_field_id}")
    Mono<TicketFieldResponse> updateTicketField(
        @PathVariable("ticket_field_id") @NotNull Long ticketFieldId,
        @QueryValue("creator") @Nullable Boolean creator
    );
}