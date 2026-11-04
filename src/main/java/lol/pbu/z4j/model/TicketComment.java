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
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Ticket comment added by an agent or end user.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketComment.JSON_PROPERTY_ATTACHMENTS,
        TicketComment.JSON_PROPERTY_AUDIT_ID,
        TicketComment.JSON_PROPERTY_AUTHOR_ID,
        TicketComment.JSON_PROPERTY_BODY,
        TicketComment.JSON_PROPERTY_CREATED_AT,
        TicketComment.JSON_PROPERTY_HTML_BODY,
        TicketComment.JSON_PROPERTY_ID,
        TicketComment.JSON_PROPERTY_METADATA,
        TicketComment.JSON_PROPERTY_PLAIN_BODY,
        TicketComment.JSON_PROPERTY_PUBLIC,
        TicketComment.JSON_PROPERTY_TYPE,
        TicketComment.JSON_PROPERTY_UPLOADS,
        TicketComment.JSON_PROPERTY_VIA
})
public record TicketComment(
        @Nullable @JsonProperty(JSON_PROPERTY_ATTACHMENTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid Attachment> attachments,
        @Nullable @JsonProperty(JSON_PROPERTY_AUDIT_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long auditId,
        @Nullable @JsonProperty(JSON_PROPERTY_AUTHOR_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long authorId,
        @Nullable @JsonProperty(JSON_PROPERTY_BODY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String body,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_HTML_BODY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String htmlBody,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_METADATA) @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS) Map<String, Object> metadata,
        @Nullable @JsonProperty(JSON_PROPERTY_PLAIN_BODY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String plainBody,
        @Nullable @JsonProperty(JSON_PROPERTY_PUBLIC) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean isPublic,
        @Nullable @JsonProperty(JSON_PROPERTY_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String type,
        @Nullable @JsonProperty(JSON_PROPERTY_UPLOADS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull String> uploads,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_VIA) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) TicketAuditVia via
) implements TicketCommentBuilder.With {

    public static final String JSON_PROPERTY_ATTACHMENTS = "attachments";
    public static final String JSON_PROPERTY_AUDIT_ID = "audit_id";
    public static final String JSON_PROPERTY_AUTHOR_ID = "author_id";
    public static final String JSON_PROPERTY_BODY = "body";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_HTML_BODY = "html_body";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_METADATA = "metadata";
    public static final String JSON_PROPERTY_PLAIN_BODY = "plain_body";
    public static final String JSON_PROPERTY_PUBLIC = "public";
    public static final String JSON_PROPERTY_TYPE = "type";
    public static final String JSON_PROPERTY_UPLOADS = "uploads";
    public static final String JSON_PROPERTY_VIA = "via";

    public TicketComment() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public TicketComment(String body) {
        this(null, null, null, body, null, null, null, null, null, null, null, null, null);
    }

    public TicketComment(String body, Boolean isPublic) {
        this(null, null, null, body, null, null, null, null, null, isPublic, null, null, null);
    }

    public TicketComment addUploadsItem(String uploadsItem) {
        List<String> newUploads = uploads == null ? new ArrayList<>() : new ArrayList<>(uploads);
        newUploads.add(uploadsItem);
        return withUploads(newUploads);
    }

    public TicketComment addAttachmentsItem(Attachment attachmentsItem) {
        List<Attachment> newAttachments = attachments == null ? new ArrayList<>() : new ArrayList<>(attachments);
        newAttachments.add(attachmentsItem);
        return withAttachments(newAttachments);
    }
}
