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
package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Upload details including token and attachment objects.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.3
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        AttachmentUploadResponseUpload.JSON_PROPERTY_TOKEN,
        AttachmentUploadResponseUpload.JSON_PROPERTY_ATTACHMENT,
        AttachmentUploadResponseUpload.JSON_PROPERTY_ATTACHMENTS
})
public record AttachmentUploadResponseUpload(
        @Nullable @JsonProperty(JSON_PROPERTY_TOKEN) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String token,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_ATTACHMENT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) AttachmentObject attachment,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_ATTACHMENTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<AttachmentObject> attachments
) implements AttachmentUploadResponseUploadBuilder.With {

    public static final String JSON_PROPERTY_TOKEN = "token";
    public static final String JSON_PROPERTY_ATTACHMENT = "attachment";
    public static final String JSON_PROPERTY_ATTACHMENTS = "attachments";

    public AttachmentUploadResponseUpload() {
        this(null, null, null);
    }

    public AttachmentUploadResponseUpload addAttachmentsItem(AttachmentObject attachmentsItem) {
        List<AttachmentObject> newAttachments = attachments == null ? new ArrayList<>() : new ArrayList<>(attachments);
        newAttachments.add(attachmentsItem);
        return withAttachments(newAttachments);
    }
}
