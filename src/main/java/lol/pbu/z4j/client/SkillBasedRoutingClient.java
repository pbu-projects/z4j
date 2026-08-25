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
import lol.pbu.z4j.model.BulkSkillBasedRoutingAttributeValuesRequest;
import lol.pbu.z4j.model.JobStatusResponse;
import lol.pbu.z4j.model.ManySkillBasedRoutingAttributeValuesResponse;
import reactor.core.publisher.Mono;
import lol.pbu.z4j.model.SkillBasedRoutingAttributeDefinitions;
import lol.pbu.z4j.model.SkillBasedRoutingAttributeResponse;
import lol.pbu.z4j.model.SkillBasedRoutingAttributeValueResponse;
import lol.pbu.z4j.model.SkillBasedRoutingAttributeValuesError;
import lol.pbu.z4j.model.SkillBasedRoutingAttributeValuesResponse;
import lol.pbu.z4j.model.SkillBasedRoutingAttributeValuesWithoutPriorityResponse;
import lol.pbu.z4j.model.SkillBasedRoutingAttributesResponse;
import lol.pbu.z4j.model.SkillBasedRoutingTicketFulfilledResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface SkillBasedRoutingClient {

    /**
     * {@summary Bulk Set Agent Attribute Values Job}
     * <p>Adds, replaces or removes multiple attributes for up to 100 agents.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li><a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882-Creating-custom-roles-and-assigning-agents\">Agents in custom role with permission to manage skills</a></li> </ul> <h4>Available Parameters</h4> <p>The request takes a data object with the following properties: | Name       | Type   | Required | Description                                                                                                                                                                                                                                       | | ---------- | ------ | -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | | action     | string | true     | The action to perform on the attribute values. One of the following: \"upsert\", \"update\", \"delete\"                                                                                                                                                 | | attributes | object | true     | The attribute values to update. See <a href=\"#attribute-values\">Attribute Values</a>. <code>agent_skill_priority</code> is optional. If not provided, it keeps the current priority or defaults to <code>NORMAL</code> when adding new attribute values.                           | | items      | array  | true     | The list of agent ids                                                                                                                                                                                                                             |</p> <p>Action can be one of the following:   * upsert: Adds new attribute values to the agents   * update: Replaces all the current attribute values of the agents with the new values   * delete: Removes specified attribute values from the agents</p> <p>This endpoint returns a <code>job_status</code> <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#json-format\">JSON object</a> and queues a background job to do the work. Use the <a href=\"/api-reference/ticketing/ticket-management/job_statuses/#show-job-status\">Show Job Status</a> endpoint to check for the job's completion.</p>
     *
     * @param bulkSkillBasedRoutingAttributeValuesRequest (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Bad Request</p> (status code 400)
     */
    @Post("/api/v2/routing/agents/instance_values/job")
    Mono<@Valid JobStatusResponse> bulkSetAgentAttributeValuesJob(
        @Body @NotNull @Valid BulkSkillBasedRoutingAttributeValuesRequest bulkSkillBasedRoutingAttributeValuesRequest
    );

    /**
     * {@summary Create Attribute}
     * <p>Creates an attribute.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/routing/attributes")
    Mono<@Valid SkillBasedRoutingAttributeResponse> createAttribute();

    /**
     * {@summary Create Attribute Value}
     * <p>Creates an attribute value.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param attributeId <p>The ID of the skill-based routing attribute</p> (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/routing/attributes/{attribute_id}/values")
    Mono<@Valid SkillBasedRoutingAttributeValueResponse> createAttributeValue(
        @PathVariable("attribute_id") @NotNull String attributeId
    );

    /**
     * {@summary Delete Attribute}
     * <p>Deletes an attribute.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param attributeId <p>The ID of the skill-based routing attribute</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/routing/attributes/{attribute_id}")
    Mono<Void> deleteAttribute(
        @PathVariable("attribute_id") @NotNull String attributeId
    );

    /**
     * {@summary Delete Attribute Value}
     * <p>Deletes an attribute value.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param attributeId <p>The ID of the skill-based routing attribute</p> (required)
     * @param attributeValueId <p>The ID of the skill-based routing attribute value</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/routing/attributes/{attribute_id}/values/{attribute_value_id}")
    Mono<Void> deleteAttributeValue(
        @PathVariable("attribute_id") @NotNull String attributeId,
        @PathVariable("attribute_value_id") @NotNull String attributeValueId
    );

    /**
     * {@summary List Agent Attribute Values}
     * <p>Returns an attribute value.</p> <h4>Allowed For</h4> <ul> <li>Agents and admins</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/routing/agents/{user_id}/instance_values")
    Mono<@Valid SkillBasedRoutingAttributeValuesResponse> listAGentAttributeValues(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary List Account Attributes}
     * <p>Returns a list of attributes for the account.</p> <h4>Sideloads</h4> <p>The following sideloads are supported:</p> <p>| Name             | Will sideload | ---------------- | ------------- | attribute_values | The attribute values available on the account</p> <h4>Allowed For</h4> <ul> <li>Agents and admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/routing/attributes")
    Mono<@Valid SkillBasedRoutingAttributesResponse> listAccountAttributes();

    /**
     * {@summary List Attribute Values for an Attribute}
     * <p>Returns a list of attribute values for a provided attribute.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param attributeId <p>The ID of the skill-based routing attribute</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/routing/attributes/{attribute_id}/values")
    Mono<@Valid SkillBasedRoutingAttributeValuesWithoutPriorityResponse> listAttributeValues(
        @PathVariable("attribute_id") @NotNull String attributeId
    );

    /**
     * {@summary List Attribute Values for Many Agents}
     * <p>Accepts a comma-separated list of up to 100 agent ids and returns attribute values for each agent in the list.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li><a href=\"https://support.zendesk.com/hc/en-us/articles/4408882153882-Creating-custom-roles-and-assigning-agents\">Agents in custom role with permission to manage skills</a></li> </ul> <h4>Pagination</h4> <ul> <li><a href=\"/api-reference/introduction/pagination/#cursor-pagination\">Cursor pagination</a> only. Note: <code>page[before]</code> and <code>page[after]</code> can't be used together in the same request.</li> </ul>
     *
     * @param filterAgentIds <p>A comma-separated list of agent ids</p> (required)
     * @param pageBefore <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.before_cursor</code> value from a previous request.</p> (optional)
     * @param pageAfter <p>A <a href=\"/documentation/api-basics/pagination/paginating-through-lists-using-cursor-pagination\">pagination cursor</a> that tells the endpoint which page to start on. It should be a <code>meta.after_cursor</code> value from a previous request.</p> (optional)
     * @param pageSize <p>The number of items to return per page</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Bad Request</p> (status code 400)
     */
    @Get("/api/v2/routing/agents/instance_values")
    Mono<@Valid ManySkillBasedRoutingAttributeValuesResponse> listManyAgentsAttributeValues(
        @QueryValue("filter[agent_ids]") @NotNull String filterAgentIds,
        @QueryValue("page[before]") @Nullable String pageBefore,
        @QueryValue("page[after]") @Nullable String pageAfter,
        @QueryValue("page[size]") @Nullable @Min(1) @Max(100) Integer pageSize
    );

    /**
     * {@summary List Routing Attribute Definitions}
     * <p>Returns the condition definitions that can be configured to apply attributes to a ticket.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/routing/attributes/definitions")
    Mono<@Valid SkillBasedRoutingAttributeDefinitions> listRoutingAttributeDefinitions();

    /**
     * {@summary List Ticket Attribute Values}
     * <p>Returns a list of attributes values for the ticket.</p> <h4>Allowed For</h4> <ul> <li>Agents and admins</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/routing/tickets/{ticket_id}/instance_values")
    Mono<@Valid SkillBasedRoutingAttributeValuesResponse> listTicketAttributeValues(
        @PathVariable("ticket_id") @NotNull Integer ticketId
    );

    /**
     * {@summary List Tickets Fulfilled by a User}
     * <p>Returns a list of ticket ids that contain attributes matching the current user's attributes. Accepts a <code>ticket_ids</code> parameter for relevant tickets to check for matching attributes.</p> <h4>Allowed For</h4> <ul> <li>Agents and admins</li> </ul>
     *
     * @param ticketIds <p>The IDs of the relevant tickets to check for matching attributes</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/routing/requirements/fulfilled")
    Mono<@Valid SkillBasedRoutingTicketFulfilledResponse> listTicketsFullfilledByUser(
        @QueryValue("ticket_ids") @NotNull Integer ticketIds
    );

    /**
     * {@summary Set Agent Attribute Values}
     * <p>Adds the specified attributes if no attributes exists, or replaces all existing attributes with the specified attributes.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param userId <p>The id of the user</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/routing/agents/{user_id}/instance_values")
    Mono<@Valid SkillBasedRoutingAttributeValuesWithoutPriorityResponse> setAgentAttributeValues(
        @PathVariable("user_id") @NotNull Integer userId
    );

    /**
     * {@summary Set Ticket Attribute Values}
     * <p>Adds the specified attributes if no attributes exists, or replaces all existing attributes with the specified attributes.</p> <p>Invalid or deleted attributes are ignored.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Post("/api/v2/routing/tickets/{ticket_id}/instance_values")
    Mono<@Valid SkillBasedRoutingAttributeValuesResponse> setTicketAttributeValues(
        @PathVariable("ticket_id") @NotNull Integer ticketId
    );

    /**
     * {@summary Show Attribute}
     * <p>Returns an attribute.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param attributeId <p>The ID of the skill-based routing attribute</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/routing/attributes/{attribute_id}")
    Mono<@Valid SkillBasedRoutingAttributeResponse> showAttribute(
        @PathVariable("attribute_id") @NotNull String attributeId
    );

    /**
     * {@summary Show Attribute Value}
     * <p>Returns an attribute value.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param attributeId <p>The ID of the skill-based routing attribute</p> (required)
     * @param attributeValueId <p>The ID of the skill-based routing attribute value</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/routing/attributes/{attribute_id}/values/{attribute_value_id}")
    Mono<@Valid SkillBasedRoutingAttributeValueResponse> showAttributeValue(
        @PathVariable("attribute_id") @NotNull String attributeId,
        @PathVariable("attribute_value_id") @NotNull String attributeValueId
    );

    /**
     * {@summary Update Attribute}
     * <p>Updates an attribute.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param attributeId <p>The ID of the skill-based routing attribute</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/routing/attributes/{attribute_id}")
    Mono<@Valid SkillBasedRoutingAttributeResponse> updateAttribute(
        @PathVariable("attribute_id") @NotNull String attributeId
    );

    /**
     * {@summary Update Attribute Value}
     * <p>Updates the name and ticket conditions of a skill. When a ticket is created, the skill is applied to a ticket  if the ticket meets the specified condition or conditions. See the <a href=\"/documentation/ticketing/reference-guides/conditions-reference/\">Conditions reference</a> for more information.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param attributeId <p>The ID of the skill-based routing attribute</p> (required)
     * @param attributeValueId <p>The ID of the skill-based routing attribute value</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Patch("/api/v2/routing/attributes/{attribute_id}/values/{attribute_value_id}")
    Mono<@Valid SkillBasedRoutingAttributeValueResponse> updateAttributeValue(
        @PathVariable("attribute_id") @NotNull String attributeId,
        @PathVariable("attribute_value_id") @NotNull String attributeValueId
    );
}