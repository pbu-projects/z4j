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
import lol.pbu.z4j.model.TicketChatCommentRedactionResponse;
import lol.pbu.z4j.model.TicketCommentResponse;
import lol.pbu.z4j.model.TicketCommentsCountResponse;
import lol.pbu.z4j.model.TicketCommentsResponse;
import io.micronaut.retry.annotation.Retryable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface TicketCommentsClient {

    /**
     * {@summary Count Ticket Comments}
     * <p>Returns an approximate count of the comments added to the ticket. If the count exceeds 100,000, the count will return a cached result.  This cached result will update every 24 hours.</p> <p>The <code>count[refreshed_at]</code> property is a timestamp that indicates when the count was last updated.</p> <p><strong>Note</strong>: When the count exceeds 100,000, <code>count[refreshed_at]</code> may occasionally be null. This indicates that the count is being updated in the background, and <code>count[value]</code> is limited to 100,000 until the update is complete.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Count of ticket comments</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/comments/count")
    Mono<@Valid TicketCommentsCountResponse> countTicketComments(
        @PathVariable("ticket_id") @NotNull Integer ticketId
    );

    /**
     * {@summary List Comments}
     * <p>Returns the comments added to the ticket.</p> <p>Each comment may include a <code>content_url</code> for an attachment or a <code>recording_url</code> for a voice comment that points to a file that may be hosted externally. For security reasons, take care not to inadvertently send Zendesk authentication credentials to third parties when attempting to access these files. See <a href=\"/documentation/api-basics/best-practices/working-with-url-properties/\">Working with url properties</a>.</p> <h4 id=\"pagination\">Pagination</h4> <ul>     <li>Cursor pagination (recommended)</li>     <li>Offset pagination</li> </ul> <p>See <a href=\"/api-reference/introduction/pagination/\">Pagination</a>.</p> <p>Returns a maximum of 100 records per page.</p> <h4 id=\"sorting\">Sorting</h4> <p>By default, comments are sorted by creation date in ascending order.</p> <p>When using cursor pagination, use the following parameter to change the sort order:</p> <table>     <thead>     <tr>         <th>Name</th>         <th>Type</th>         <th>Required</th>         <th>Comments</th>     </tr>     </thead>     <tbody>     <tr>         <td><code>sort</code></td>         <td>string</td>         <td>no</td>         <td>Possible values are &quot;created_at&quot; (ascending order) or &quot;-created_at&quot; (descending order)</td>     </tr>     </tbody> </table> <p>When using offset pagination, use the following parameters to change the sort order:</p> <table>     <thead>     <tr>         <th>Name</th>         <th>Type</th>         <th>Required</th>         <th>Comments</th>     </tr>     </thead>     <tbody>     <tr>         <td><code>sort_order</code></td>         <td>string</td>         <td>no</td>         <td>One of <code>asc</code>, <code>desc</code>. Defaults to <code>asc</code></td>     </tr>     </tbody> </table> <h4 id=\"allowed-for\">Allowed For</h4> <ul>     <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param includeInlineImages <p>Default is false. When true, inline images are also listed as attachments in the response</p> (optional)
     * @param include <p>Accepts \"users\". Use this parameter to list email CCs by side-loading users. Example: <code>?include=users</code>. <strong>Note</strong>: If the comment source is email, a deleted user will be represented as the CCd email address. If the comment source is anything else, a deleted user will be represented as the user name.</p> (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}/comments")
    Mono<@Valid TicketCommentsResponse> listTicketComments(
        @PathVariable("ticket_id") @NotNull Integer ticketId,
        @QueryValue("include_inline_images") @Nullable Boolean includeInlineImages,
        @QueryValue("include") @Nullable String include
    );

    /**
     * {@summary Make Comment Private}
     * <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param ticketCommentId <p>The ID of the ticket comment</p> (required)
     *
     * @return <p>description</p> (status code 200)
     */
    @Put("/api/v2/tickets/{ticket_id}/comments/{ticket_comment_id}/make_private")
    Mono<@NotNull String> makeTicketCommentPrivate(
        @PathVariable("ticket_id") @NotNull Integer ticketId,
        @PathVariable("ticket_comment_id") @NotNull Integer ticketCommentId
    );

    /**
     * {@summary Redact Chat Comment}
     * <p>Permanently removes words or strings from a chat ticket's comment. </p> <p>Wrap <code>&lt;redact&gt;</code> tags around the content in the chat comment you want redacted. Example: </p> <p><code>json {   \"text\": \"My ID number is &lt;redact&gt;847564&lt;/redact&gt;!\" }</code></p> <p>The characters contained in the tag will be replaced by the ▇ symbol.</p> <p><strong>Note</strong>: This does not work on active chats. For chat tickets that predate March 2020, consider using <a href=\"#redact-ticket-comment-in-agent-workspace\">Redact Ticket Comment In Agent Workspace</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <p><a href=\"https://support.zendesk.com/hc/en-us/articles/360024218473\">Agent Workspace</a> must enabled for the account. Deleting tickets must be enabled for agents.</p> <h4>Request Body Properties</h4> <p>| Name                     | Type    | Required | Description                                                                                                                                                                                                                                       | | ------------------------ | ------- | -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | | chat_id                  | string  | true     | The <code>chat_id</code> in the <code>ChatStartedEvent</code> event in the ticket audit. See <a href=\"/api-reference/ticketing/tickets/ticket_audits\">Ticket Audits</a>                                                                                                            | | chat_index               | integer | false    | The <code>chat_index</code> in the <code>ChatMessage</code> event in the ticket audit. See <a href=\"/api-reference/ticketing/tickets/ticket_audits\">Ticket Audits</a>. Mandatory if <code>message_id</code> is not used                                                                       | | message_id               | string  | false    | The <code>message_id</code> of the <code>ChatMessage</code> event in the ticket audit that is part of a <code>ChatStartedEvent</code> history. Used when redacting a ChatMessage that is part of a conversation history. Mandatory if <code>chat_index</code> is not used                     | | text                     | string  | true     | The <code>message</code> in the <code>ChatMessage</code> event in the ticket audit. See <a href=\"/api-reference/ticketing/tickets/ticket_audits\">Ticket Audits</a>.  Wrap <code>message</code> with <code>&lt;redact&gt;</code> tags                                                                           |</p> <p>To get the required body properties, make a request to the <a href=\"/api-reference/ticketing/tickets/ticket_audits\">Ticket Audit</a> endpoint. Example response:</p> <p><code>http Status 200 OK {   \"audits\": [     \"events\": [       {         \"id\": 1932802680168,         \"type\": \"ChatStartedEvent\",         \"value\": {           \"visitor_id\": \"10502823-16EkM3T6VNq7KMd\",           \"chat_id\": \"2109.10502823.Sjuj2YrBpXwei\",           \"history\": [             {               \"chat_index\": 0,               \"type\": \"ChatMessage\",               \"message\": \"My ID number is 847564!\"             }           ]         }       }     ]   ] }</code></p>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/chat_redactions/{ticket_id}")
    Mono<@Valid TicketChatCommentRedactionResponse> redactChatComment(
        @PathVariable("ticket_id") @NotNull Integer ticketId
    );

    /**
     * {@summary Redact Chat Comment Attachment}
     * <p>Permanently removes one or more chat attachments from a chat ticket.</p> <p><strong>Note</strong>: This does not work on active chats. For chat tickets that predate March 2020, consider using <a href=\"#redact-ticket-comment-in-agent-workspace\">Redact Ticket Comment In Agent Workspace</a>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <p><a href=\"https://support.zendesk.com/hc/en-us/articles/360024218473\">Agent Workspace</a> must enabled for the account. Deleting tickets must be enabled for agents.</p> <h4>Request Body Properties</h4> <p>| Name         | Type    | Required | Description                                                                                                                                                                                                                                            | | ------------ | ------- | -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | | chat_id      | string  | true     | The <code>chat_id</code> in the <code>ChatStartedEvent</code> event in the ticket audit. See <a href=\"/api-reference/ticketing/tickets/ticket_audits\">Ticket Audits</a>                                                                                                                 | | chat_indexes | array   | false    | The array of <code>chat_index</code> in the <code>ChatFileAttachment</code> event in the ticket audit. See <a href=\"/api-reference/ticketing/tickets/ticket_audits\">Ticket Audits</a>. Mandatory if <code>message_ids</code> is not used                                                           | | message_ids  | array   | false    | The array of <code>message_id</code> in the <code>ChatFileAttachment</code> event in the ticket audit that is part of a <code>ChatStartedEvent</code> history. Used when redacting a ChatFileAttachment that is part of a conversation history. Mandatory if <code>chat_indexes</code> is not used |</p> <p>To get the required body properties, make a request to the <a href=\"/api-reference/ticketing/tickets/ticket_audits\">Ticket Audits</a> endpoint. Example response:</p> <p><code>http Status 200 OK {   \"audits\": [     \"events\": [       {         \"id\": 1932802680168,         \"type\": \"ChatStartedEvent\",         \"value\": {           \"visitor_id\": \"10502823-16EkM3T6VNq7KMd\",           \"chat_id\": \"2109.10502823.Sjuj2YrBpXwei\",           \"history\": [             {               \"chat_index\": 0,               \"type\": \"ChatFileAttachment\",               \"filename\": \"image1.jpg\"             },             {               \"chat_index\": 1,               \"type\": \"ChatFileAttachment\",               \"filename\": \"image2.jpg\"             }           ]         }       }     ]   ] }</code></p>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/chat_file_redactions/{ticket_id}")
    Mono<@Valid TicketChatCommentRedactionResponse> redactChatCommentAttachment(
        @PathVariable("ticket_id") @NotNull Integer ticketId
    );

    /**
     * {@summary Redact String in Comment}
     * <p>Permanently removes words or strings from a ticket comment. Specify the string to redact in an object with a <code>text</code> property. Example: <code>'{\"text\": \"987-65-4320\"}'</code>. The characters of the word or string are replaced by the ▇ symbol.</p> <p>If the comment was made by email, the endpoint also attempts to redact the string from the original email retained by Zendesk for audit purposes.</p> <p><strong>Note</strong>: If you use the rich text editor, support for redacting formatted text (bold, italics, hyperlinks) is limited.</p> <p>Redaction is permanent. You can't undo the redaction or see <em>what</em> was removed. Once a ticket is closed, you can no longer redact strings from its comments.</p> <p>To use this endpoint, the \"Agents can delete tickets\" option must be enabled in the Zendesk Support admin interface at <strong>Admin</strong> &gt; <strong>Settings</strong> &gt; <strong>Agents</strong>.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param ticketCommentId <p>The ID of the ticket comment</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/tickets/{ticket_id}/comments/{ticket_comment_id}/redact")
    Mono<@Valid TicketCommentResponse> redactStringInComment(
        @PathVariable("ticket_id") @NotNull Integer ticketId,
        @PathVariable("ticket_comment_id") @NotNull Integer ticketCommentId
    );

    /**
     * {@summary Redact Ticket Comment In Agent Workspace}
     * <p>Redaction allows you to permanently remove words, strings, or attachments from a ticket comment.</p> <p>In the <code>html_body</code> of the comment, wrap the content you want redacted in <code>&lt;redact&gt;</code> tags. Example:</p> <p><code>json {   \"html_body\": \"&lt;div class=\\\"zd-comment\\\" dir=\\\"auto\\\"&gt;My ID number is &lt;redact&gt;847564&lt;/redact&gt;!&lt;/div&gt;\",   \"ticket_id\":100 }</code></p> <p>The characters in the redact tag will be replaced by the ▇ symbol.</p> <p>To redact HTML elements such inline images, anchor tags, and links, add the <code>redact</code> tag attribute to the element as well as the <code>&lt;redact&gt;</code> tag to inner text, if any. Example: </p> <p><code>&lt;a href=\"http://example.com\" redact&gt;&lt;redact&gt;some link&lt;/redact&gt;&lt;/a&gt;</code></p> <p>The <code>redact</code> attribute only redacts the tag. Any inner text will be left behind if not enclosed in a <code>&lt;redact&gt;</code> tag.</p> <p>Redaction is permanent and can not be undone. Data is permanently deleted from Zendesk servers with no way to recover it.</p> <p>This endpoint provides all the same functionality that the <a href=\"/api-reference/ticketing/tickets/ticket_comments/#redact-string-in-comment\">Redact String in Comment</a> endpoint provides, plus:</p> <ul> <li> <p>Redaction of comments in closed tickets</p> </li> <li> <p>Redaction of comments in archived tickets</p> </li> <li> <p>Redaction of formatted text (bold, italics, hyperlinks)</p> </li> </ul> <p><strong>Limitations</strong>: When content is redacted from an email comment, the content is also redacted from the original email through a background job. It may take a while for the changes to be completed.</p> <p><strong>Note</strong>: We recommend using this endpoint instead of the <a href=\"/api-reference/ticketing/tickets/ticket_comments/#redact-string-in-comment\">Redact String in Comment</a> endpoint, which will eventually be deprecated.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul> <p><a href=\"https://support.zendesk.com/hc/en-us/articles/360024218473\">Agent Workspace</a> must be enabled on the account. For professional accounts, deleting tickets must be enabled for agents. On Enterprise accounts, you can assign agents to a custom role with permissions to redact ticket content.</p> <h4>Request Body Properties</h4> <p>| Name                     | Type    | Required | Description                                                                                                                                      | | -------------------------| ------- | -------- | ------------------------------------------------------------------------------------------------------------------------------------------------ | | ticket_id                | integer | true     | The ID of the ticket                                                                                                                             | | html_body                | string  | false    | The <code>html_body</code> of the comment containing <code>&lt;redact&gt;</code> tags or <code>redact</code> attributes                                           | | external_attachment_urls | array   | false    | Array of attachment URLs belonging to the comment to be redacted. See <a href=\"/api-reference/ticketing/tickets/ticket-attachments/\"><code>content_url</code> property of Attachment</a> |</p>
     *
     * @param ticketCommentId <p>The ID of the ticket comment</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/comment_redactions/{ticket_comment_id}")
    Mono<@Valid TicketCommentResponse> redactTicketCommentInAgentWorkspace(
        @PathVariable("ticket_comment_id") @NotNull Integer ticketCommentId
    );
}