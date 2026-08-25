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
import lol.pbu.z4j.model.AttachmentResponse;
import lol.pbu.z4j.model.AttachmentUpdateRequest;
import lol.pbu.z4j.model.AttachmentUploadResponse;
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
public interface AttachmentsClient {

    /**
     * {@summary Delete Upload}
     * <h4>Allowed for</h4> <ul> <li>End Users</li> </ul>
     *
     * @param token <p>The token of the uploaded attachment</p> (required)
     *
     * @return <p>No Content response</p> (status code 204)
     */
    @Delete("/api/v2/uploads/{token}")
    Mono<Void> deleteUpload(
        @PathVariable("token") @NotNull String token
    );

    /**
     * {@summary Redact Comment Attachment}
     * <p>Redaction allows you to permanently remove attachments from an existing comment on a ticket. Once removed from a comment, the attachment is replaced with an empty \"redacted.txt\" file.</p> <p>The redaction is permanent. It is not possible to undo redaction or see what was removed. Once a ticket is closed, redacting its attachments is no longer possible.</p> <p>Also, if you want to redact an inline attachment, you can use the <code>include_inline_images</code> parameter in the <a href=\"/api-reference/ticketing/tickets/ticket_comments/#list-comments\">List Comments</a> operation to obtain the inline attachment ID, and use it in the request URL.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> <li>Agents when <a href=\"https://support.zendesk.com/hc/en-us/articles/360002128107\">deleting tickets is enabled for agents on professional accounts</a></li> <li>Agents assigned to a custom role with permissions to redact ticket content (Enterprise only)</li> </ul>
     *
     * @param ticketId <p>The ID of the ticket</p> (required)
     * @param commentId <p>The ID of the comment</p> (required)
     * @param attachmentId <p>The ID of the attachment</p> (required)
     *
     * @return <p>OK response</p> (status code 200)
     */
    @Put("/api/v2/tickets/{ticket_id}/comments/{comment_id}/attachments/{attachment_id}/redact")
    Mono<@Valid AttachmentResponse> redactCommentAttachment(
        @PathVariable("ticket_id") @NotNull Integer ticketId,
        @PathVariable("comment_id") @NotNull Integer commentId,
        @PathVariable("attachment_id") @NotNull Integer attachmentId
    );

    /**
     * {@summary Show Attachment}
     * <p>Shows attachment details. You can get the value of the <code>attachment_id</code> parameter by listing the ticket's comments. See <a href=\"/api-reference/ticketing/tickets/ticket_comments/#list-comments\">List Comments</a>. Each comment in the list has an <code>attachments</code> list that specifies an <code>id</code> for each attachment.</p> <p>#### Allowed for</p> <ul> <li>Agents</li> </ul>
     *
     * @param attachmentId <p>The ID of the attachment</p> (required)
     *
     * @return <p>Success Response</p> (status code 200)
     */
    @Get("/api/v2/attachments/{attachment_id}")
    Mono<@Valid AttachmentResponse> showAttachment(
        @PathVariable("attachment_id") @NotNull Integer attachmentId
    );

    /**
     * {@summary Update Attachment for Malware}
     * <p>Toggles enabling or restricting agent access to attachments with detected malware.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param attachmentId <p>The ID of the attachment</p> (required)
     * @param attachmentUpdateRequest (optional)
     *
     * @return <p>Success response</p> (status code 200)
     */
    @Put("/api/v2/attachments/{attachment_id}")
    Mono<@Valid AttachmentResponse> updateAttachment(
        @PathVariable("attachment_id") @NotNull Integer attachmentId,
        @Body @Nullable @Valid AttachmentUpdateRequest attachmentUpdateRequest
    );

    /**
     * {@summary Upload Files}
     * <p>Uploads a file that can be attached to a ticket comment. It doesn't attach the file to the comment. For details and examples, see <a href=\"/documentation/ticketing/managing-tickets/adding-ticket-attachments-with-the-api/\">Attaching ticket comments with the API</a>.</p> <p>The endpoint has a required <code>filename</code> query parameter. The parameter specifies what the file will be named when attached to the ticket comment (to give the agent more context about the file). The parameter does not specify the file on the local system to be uploaded. While the two names can be different, their file extensions must be the same. If they don't match, the agent's browser or file reader could give an error when attempting to open the attachment.</p> <p>The <code>Content-Type</code> header must contain a recognized MIME type that correctly describes the type of the uploaded file. Failing to send a recognized, correct type may cause undesired behavior. For example, in-browser audio playback may be interrupted by the browser's security mechanisms for MP3s uploaded with an incorrect type.</p> <p>Adding multiple files to the same upload is handled by splitting requests and passing the API token received from the first request to each subsequent request. The token is valid for 60 minutes.</p> <p><strong>Note</strong>: Even if <a href=\"https://support.zendesk.com/hc/en-us/articles/204265396\">private attachments</a> are enabled in the Zendesk Support instance, uploaded files are visible to any authenticated user at the <code>content_URL</code> specified in the <a href=\"#json-format\">JSON response</a> until the upload token is consumed. Once a file is associated with a ticket or post, visibility is restricted to users with access to the ticket or post with the attachment.</p> <h4>Allowed For</h4> <ul> <li>End users</li> </ul>
     *
     * @param filename <p>The name to assign to the uploaded file</p> (required)
     *
     * @return <p>Created response</p> (status code 201)
     */
    @Post("/api/v2/uploads")
    Mono<@Valid AttachmentUploadResponse> uploadFiles(
        @QueryValue("filename") @NotNull String filename
    );
}