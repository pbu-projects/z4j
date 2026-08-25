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
import io.micronaut.core.io.buffer.ByteBuffer;
import lol.pbu.z4j.model.CustomObjectRecordAttachmentResponse;
import lol.pbu.z4j.model.CustomObjectRecordAttachmentUpdateRequest;
import lol.pbu.z4j.model.CustomObjectRecordAttachmentsResponse;
import lol.pbu.z4j.model.ListCustomObjectRecordAttachments400Response;
import reactor.core.publisher.Mono;
import io.micronaut.retry.annotation.Retryable;
import io.micronaut.http.client.multipart.MultipartBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Retryable
@Client("zendesk")
public interface CustomObjectRecordAttachmentsClient {

    /**
     * {@summary Create Custom Object Record Attachment}
     * <p>Creates a new attachment associated with a custom object record. The custom object must have the \"allows_attachments\" setting enabled.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param recordId <p>The id of a custom object record</p> (required)
     *
     * @return <p>Success</p> (status code 200)
     *         or <p>Bad Request - Attachments not allowed for custom object or invalid request</p> (status code 400)
     */
    @Post("/api/v2/custom_objects/{custom_object_key}/records/{record_id}/attachments")
    @Produces("multipart/form-data")
    Mono<@Valid CustomObjectRecordAttachmentResponse> createCustomObjectRecordAttachment(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("record_id") @NotNull String recordId,
        @Body @Nullable MultipartBody multipartBody
    );

    /**
     * {@summary Delete Custom Object Record Attachment}
     * <p>Deletes the specified attachment associated with a custom object record.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param recordId <p>The id of a custom object record</p> (required)
     * @param id <p>The id of a custom object record attachment</p> (required)
     *
     * @return <p>No Content - attachment successfully deleted</p> (status code 204)
     *         or <p>Bad Request - Attachments not allowed for custom object</p> (status code 400)
     */
    @Delete("/api/v2/custom_objects/{custom_object_key}/records/{record_id}/attachments/{id}")
    Mono<Void> deleteCustomObjectRecordAttachment(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("record_id") @NotNull String recordId,
        @PathVariable("id") @NotNull String id
    );

    /**
     * {@summary Download Custom Object Record Attachment}
     * <p>Downloads the specified attachment content. Returns a redirect to the attachment's content URL. Access to malicious attachments is controlled by the <code>malware_access_override</code> setting.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param recordId <p>The id of a custom object record</p> (required)
     * @param id <p>The id of a custom object record attachment</p> (required)
     * @param inline <p>If true, the attachment content is displayed inline in the browser. If false or omitted, the attachment is downloaded as a file.</p> (optional, default to false)
     *
     * @return <p>Successful download of attachment content</p> (status code 200)
     *         or <p>Bad Request - Attachments not allowed for custom object</p> (status code 400)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/records/{record_id}/attachments/{id}/download")
    @Consumes({"application/octet-stream", "application/json"})
    Mono<@NotNull ByteBuffer<?>> downloadCustomObjectRecordAttachment(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("record_id") @NotNull String recordId,
        @PathVariable("id") @NotNull String id,
        @QueryValue(value = "inline", defaultValue = "false") @Nullable Boolean inline
    );

    /**
     * {@summary List Custom Object Record Attachments}
     * <p>Lists all attachments associated with a custom object record.</p> <h4>Allowed For</h4> <ul> <li>Agents</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param recordId <p>The id of a custom object record</p> (required)
     *
     * @return <p>Success response</p> (status code 200)
     *         or <p>Bad Request - Attachments not allowed for custom object</p> (status code 400)
     */
    @Get("/api/v2/custom_objects/{custom_object_key}/records/{record_id}/attachments")
    Mono<@Valid CustomObjectRecordAttachmentsResponse> listCustomObjectRecordAttachments(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("record_id") @NotNull String recordId
    );

    /**
     * {@summary Update Custom Object Record Attachment for Malware}
     * <p>Updates malware access settings for the specified attachment. This is typically used to allow access to attachments that were flagged as containing malware.</p> <h4>Allowed For</h4> <ul> <li>Admins</li> </ul>
     *
     * @param customObjectKey <p>The key of a custom object</p> (required)
     * @param recordId <p>The id of a custom object record</p> (required)
     * @param id <p>The id of a custom object record attachment</p> (required)
     * @param customObjectRecordAttachmentUpdateRequest (optional)
     *
     * @return <p>Success</p> (status code 200)
     *         or <p>Bad Request - Attachments not allowed for custom object</p> (status code 400)
     */
    @Put("/api/v2/custom_objects/{custom_object_key}/records/{record_id}/attachments/{id}")
    Mono<@Valid CustomObjectRecordAttachmentResponse> updateCustomObjectRecordAttachment(
        @PathVariable("custom_object_key") @NotNull String customObjectKey,
        @PathVariable("record_id") @NotNull String recordId,
        @PathVariable("id") @NotNull String id,
        @Body @Nullable @Valid CustomObjectRecordAttachmentUpdateRequest customObjectRecordAttachmentUpdateRequest
    );
}