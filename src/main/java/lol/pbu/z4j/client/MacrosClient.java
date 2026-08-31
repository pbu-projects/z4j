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
import lol.pbu.z4j.model.CreateMacro200Response;
import lol.pbu.z4j.model.CreateMacroRequest;
import lol.pbu.z4j.model.ListMacroActionDefinitions200Response;
import lol.pbu.z4j.model.ListMacrosActions200Response;
import lol.pbu.z4j.model.MacroApplyTicketResponse;
import lol.pbu.z4j.model.MacroAttachmentResponse;
import lol.pbu.z4j.model.MacroAttachmentsResponse;
import lol.pbu.z4j.model.MacroCategoriesResponse;
import lol.pbu.z4j.model.MacroResponse;
import lol.pbu.z4j.model.MacroUpdateManyInput;
import lol.pbu.z4j.model.MacrosResponse;
import reactor.core.publisher.Mono;
import static io.micronaut.core.convert.converters.MultiValuesConverterFactory.*;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface MacrosClient {

    /**
     * {@summary Create Macro Attachment}
     * <p>Allows an attachment to be uploaded and associated with a macro at the same time.</p> <p><strong>Note:</strong> A macro can be associated with up to five attachments.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param macroId <p>The ID of the macro</p> (required)
     *
     * @return <p>Success Response</p> (status code 201)
     */
    @Post("/api/v2/macros/{macro_id}/attachments")
    Mono<@Valid MacroAttachmentResponse> createAssociatedMacroAttachment(
        @PathVariable("macro_id") @NotNull Long macroId
    );

    /**
     * {@summary Create Macro}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param createMacroRequest (optional)
     *
     * @return <p>OK</p> (status code 200)
     */
    @Post("/api/v2/macros")
    Mono<@Valid CreateMacro200Response> createMacro(
        @Body @Nullable @Valid CreateMacroRequest createMacroRequest
    );

    /**
     * {@summary Create Unassociated Macro Attachment}
     * <p>Allows an attachment to be uploaded that can be associated with a macro at a later time.</p> <p><strong>Note:</strong> To ensure an uploaded attachment is not lost, associate it with a macro as soon as possible. From time to time, old attachments that are not not associated with any macro are purged.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Created Response</p> (status code 201)
     */
    @Post("/api/v2/macros/attachments")
    Mono<@Valid MacroAttachmentResponse> createMacroAttachment();

    /**
     * {@summary Delete Macro}
     * <h4>Allowed For</h4> <ul> <li>Agents, with restrictions applying on certain actions</li> </ul>
     *
     * @param macroId <p>The ID of the macro</p> (required)
     *
     * @return <p>No Content</p> (status code 204)
     */
    @Delete("/api/v2/macros/{macro_id}")
    Mono<Void> deleteMacro(
        @PathVariable("macro_id") @NotNull Long macroId
    );

    /**
     * {@summary Bulk Delete Macros}
     * <p>Deletes the macros corresponding to the provided comma-separated list of IDs.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ids <p>The IDs of the macros to delete</p> (required)
     *
     * @return <p>No Content</p> (status code 204)
     */
    @Delete("/api/v2/macros/destroy_many")
    Mono<Void> deleteManyMacros(
        @QueryValue("ids") @NotNull @Format(FORMAT_MULTI) List<@NotNull Long> ids
    );

    /**
     * {@summary List Active Macros}
     * <p>Lists all active shared and personal macros available to the current user.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param include <p>A sideload to include in the response. See <a href=\"#sideloads-2\">Sideloads</a></p> (optional)
     * @param access <p>Filter macros by access. Possible values are \"personal\", \"agents\", \"shared\", or \"account\". The \"agents\" value returns all personal macros for the account's agents and is only available to admins.</p> (optional)
     * @param category <p>Filter macros by category</p> (optional)
     * @param groupId <p>Filter macros by group</p> (optional)
     * @param sortBy <p>Possible values are \"alphabetical\", \"created_at\", \"updated_at\", \"usage_1h\", \"usage_24h\", \"usage_7d\", or \"usage_30d\". Defaults to alphabetical</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros/active")
    Mono<@Valid MacrosResponse> listActiveMacros(
        @QueryValue("include") @Nullable String include,
        @QueryValue("access") @Nullable String access,
        @QueryValue("category") @Nullable Long category,
        @QueryValue("group_id") @Nullable Long groupId,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List Macro Action Definitions}
     * <p>Returns the definitions of the actions a macro can perform. For example, one action can set the status of a ticket. The definition of the action includes a title (\"Status\"), a type (\"list\"), and possible values. For a list of support actions, see <a href=\"/documentation/ticketing/reference-guides/actions-reference\">Actions reference</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros/definitions")
    Mono<@Valid ListMacroActionDefinitions200Response> listMacroActionDefinitions();

    /**
     * {@summary List Macro Attachments}
     * <p>Lists the attachments associated with a macro.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param macroId <p>The ID of the macro</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros/{macro_id}/attachments")
    Mono<@Valid MacroAttachmentsResponse> listMacroAttachments(
        @PathVariable("macro_id") @NotNull Long macroId
    );

    /**
     * {@summary List Macro Categories}
     * <p>Lists all macro categories available to the current user.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros/categories")
    Mono<@Valid MacroCategoriesResponse> listMacroCategories();

    /**
     * {@summary List Macros}
     * <p>Lists all shared and personal macros available to the current user. For admins, the API returns all macros for the account, including the personal macros of agents and other admins.</p> <h4>Pagination</h4> <ul> <li>Cursor pagination (recommended)</li> <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param include <p>A sideload to include in the response. See <a href=\"#sideloads-2\">Sideloads</a></p> (optional)
     * @param access <p>Filter macros by access. Possible values are \"personal\", \"agents\", \"shared\", or \"account\". The \"agents\" value returns all personal macros for the account's agents and is only available to admins.</p> (optional)
     * @param active <p>Filter by active macros if true or inactive macros if false</p> (optional)
     * @param category <p>Filter macros by category</p> (optional)
     * @param groupId <p>Filter macros by group</p> (optional)
     * @param onlyViewable <p>If true, returns only macros that can be applied to tickets. If false, returns all macros the current user can manage. Default is false</p> (optional)
     * @param sortBy <p>Possible values are \"alphabetical\", \"created_at\", \"updated_at\", \"usage_1h\", \"usage_24h\", \"usage_7d\", or \"usage_30d\". Defaults to alphabetical</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros")
    Mono<@Valid MacrosResponse> listMacros(
        @QueryValue("include") @Nullable String include,
        @QueryValue("access") @Nullable String access,
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("category") @Nullable Long category,
        @QueryValue("group_id") @Nullable Long groupId,
        @QueryValue("only_viewable") @Nullable Boolean onlyViewable,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary List Supported Actions for Macros}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros/actions")
    Mono<@Valid ListMacrosActions200Response> listMacrosActions();

    /**
     * {@summary Search Macros}
     * <h4>Pagination</h4> <ul> <li>Offset pagination only</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/#using-offset-pagination\">Using Offset Pagination</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param query <p>Query string used to find macros with matching titles</p> (required)
     * @param include <p>A sideload to include in the response. See <a href=\"#sideloads-2\">Sideloads</a></p> (optional)
     * @param access <p>Filter macros by access. Possible values are \"personal\", \"agents\", \"shared\", or \"account\". The \"agents\" value returns all personal macros for the account's agents and is only available to admins.</p> (optional)
     * @param active <p>Filter by active macros if true or inactive macros if false</p> (optional)
     * @param category <p>Filter macros by category</p> (optional)
     * @param groupId <p>Filter macros by group</p> (optional)
     * @param onlyViewable <p>If true, returns only macros that can be applied to tickets. If false, returns all macros the current user can manage. Default is false</p> (optional)
     * @param sortBy <p>Possible values are \"alphabetical\", \"created_at\", \"updated_at\", or \"position\". Defaults to alphabetical</p> (optional)
     * @param sortOrder <p>One of \"asc\" or \"desc\". Defaults to \"asc\" for alphabetical and position sort, \"desc\" for all others</p> (optional)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros/search")
    Mono<@Valid MacrosResponse> searchMacro(
        @QueryValue("query") @NotNull String query,
        @QueryValue("include") @Nullable String include,
        @QueryValue("access") @Nullable String access,
        @QueryValue("active") @Nullable Boolean active,
        @QueryValue("category") @Nullable Long category,
        @QueryValue("group_id") @Nullable Long groupId,
        @QueryValue("only_viewable") @Nullable Boolean onlyViewable,
        @QueryValue("sort_by") @Nullable String sortBy,
        @QueryValue("sort_order") @Nullable String sortOrder
    );

    /**
     * {@summary Show Changes to Ticket}
     * <p>Returns the changes the macro would make to a ticket. It doesn't actually change a ticket. You can use the response data in a subsequent API call to the <a href=\"/api-reference/ticketing/tickets/tickets/\">Tickets</a> endpoint to update the ticket.</p> <p>The response includes only the ticket fields that would be changed by the macro. To get the full ticket object after the macro is applied, see <a href=\"#show-ticket-after-changes\">Show Ticket After Changes</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param macroId <p>The ID of the macro</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros/{macro_id}/apply")
    Mono<@Valid MacroApplyTicketResponse> showChangesToTicket(
        @PathVariable("macro_id") @NotNull Long macroId
    );

    /**
     * {@summary Show Macro Replica}
     * <p>Returns an unpersisted macro representation derived from a ticket or macro.</p> <p>The endpoint takes one of the following query parameters: <code>macro_id</code> or <code>ticket_id</code>. If you include both, <code>macro_id</code> is used.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param macroId <p>The ID of the macro to replicate</p> (required)
     * @param ticketId <p>The ID of the ticket from which to build a macro replica</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros/new")
    Mono<@Valid MacroResponse> showDerivedMacro(
        @QueryValue("macro_id") @NotNull Long macroId,
        @QueryValue("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Show Macro}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param macroId <p>The ID of the macro</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros/{macro_id}")
    Mono<@Valid MacroResponse> showMacro(
        @PathVariable("macro_id") @NotNull Long macroId
    );

    /**
     * {@summary Show Macro Attachment}
     * <p>Shows the properties of the specified macro attachment.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param attachmentId <p>The ID of the attachment</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/macros/attachments/{attachment_id}")
    Mono<@Valid MacroAttachmentResponse> showMacroAttachment(
        @PathVariable("attachment_id") @NotNull Long attachmentId
    );

    /**
     * {@summary Show Ticket After Changes}
     * <p>Returns the full ticket object as it would be after applying the macro to the ticket. It doesn't actually change the ticket.</p> <p>To get only the ticket fields that would be changed by the macro, see <a href=\"#show-changes-to-ticket\">Show Changes to Ticket</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param macroId <p>The ID of the macro</p> (required)
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/macros/{macro_id}/apply")
    Mono<@Valid MacroApplyTicketResponse> showTicketAfterChanges(
        @PathVariable("macro_id") @NotNull Long macroId,
        @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Update Macro}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param macroId <p>The ID of the macro</p> (required)
     * @param createMacroRequest (optional)
     *
     * @return <p>OK</p> (status code 200)
     */
    @Put("/api/v2/macros/{macro_id}")
    Mono<@Valid CreateMacro200Response> updateMacro(
        @PathVariable("macro_id") @NotNull Long macroId,
        @Body @Nullable @Valid CreateMacroRequest createMacroRequest
    );

    /**
     * {@summary Update Many Macros}
     * <p>Updates the provided macros with the specified changes.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param macroUpdateManyInput (optional)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Put("/api/v2/macros/update_many")
    Mono<@Valid MacrosResponse> updateManyMacros(
        @Body @Nullable @Valid MacroUpdateManyInput macroUpdateManyInput
    );
}