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

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lol.pbu.z4j.model.AttachmentObject;
import lol.pbu.z4j.model.TicketAuditViaObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import io.micronaut.core.annotation.Nullable;

/**
 * TicketImportInputCommentsInner
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketImportInputCommentsInner.JSON_PROPERTY_ATTACHMENTS,
    TicketImportInputCommentsInner.JSON_PROPERTY_AUDIT_ID,
    TicketImportInputCommentsInner.JSON_PROPERTY_AUTHOR_ID,
    TicketImportInputCommentsInner.JSON_PROPERTY_BODY,
    TicketImportInputCommentsInner.JSON_PROPERTY_CREATED_AT,
    TicketImportInputCommentsInner.JSON_PROPERTY_HTML_BODY,
    TicketImportInputCommentsInner.JSON_PROPERTY_ID,
    TicketImportInputCommentsInner.JSON_PROPERTY_METADATA,
    TicketImportInputCommentsInner.JSON_PROPERTY_PLAIN_BODY,
    TicketImportInputCommentsInner.JSON_PROPERTY_PUBLIC,
    TicketImportInputCommentsInner.JSON_PROPERTY_TYPE,
    TicketImportInputCommentsInner.JSON_PROPERTY_UPLOADS,
    TicketImportInputCommentsInner.JSON_PROPERTY_VIA,
    TicketImportInputCommentsInner.JSON_PROPERTY_VALUE,
})
@Serdeable
public class TicketImportInputCommentsInner {

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
    public static final String JSON_PROPERTY_VALUE = "value";

    /**
     * <p>Attachments, if any. See <a href=\"/api-reference/ticketing/tickets/ticket-attachments/\">Attachment</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid AttachmentObject> attachments;

    /**
     * <p>The id of the ticket audit record. See <a href=\"/api-reference/ticketing/tickets/ticket_audits/#show-audit\">Show Audit</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUDIT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer auditId;

    /**
     * <p>The id of the comment author. See <a href=\"#author-id\">Author id</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTHOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer authorId;

    /**
     * <p>The comment string. See <a href=\"#bodies\">Bodies</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String body;

    /**
     * <p>The time the comment was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The comment formatted as HTML. See <a href=\"#bodies\">Bodies</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HTML_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String htmlBody;

    /**
     * <p>Automatically assigned when the comment is created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * <p>System information (web client, IP address, etc.) and comment flags, if any. See <a href=\"#comment-flags\">Comment flags</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_METADATA)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> metadata;

    /**
     * <p>The comment presented as plain text. See <a href=\"#bodies\">Bodies</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PLAIN_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String plainBody;

    /**
     * <p>true if a public comment; false if an internal note. The initial value set on ticket creation persists for any additional comment unless you change it</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PUBLIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean _public;

    /**
     * <p><code>Comment</code> or <code>VoiceComment</code>. The JSON object for adding voice comments to tickets is different. See <a href=\"/documentation/ticketing/managing-tickets/adding-voice-comments-to-tickets\">Adding voice comments to tickets</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

    /**
     * <p>List of tokens received from <a href=\"/api-reference/ticketing/tickets/ticket-attachments/#upload-files\">uploading files</a> for comment attachments. The files are attached by creating or updating tickets with the tokens. See <a href=\"/api-reference/ticketing/tickets/tickets/#attaching-files\">Attaching files</a> in Tickets</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPLOADS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> uploads;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketAuditViaObject via;

    /**
     * <p>The comment string value</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String value;

    /**
     * Add an item to the uploads property in a chainable fashion.
     *
     * @return The same instance of TicketImportInputCommentsInner for chaining.
     */
    public TicketImportInputCommentsInner addUploadsItem(String uploadsItem) {
        if (uploads == null) {
            uploads = new ArrayList<>();
        }
        uploads.add(uploadsItem);
        return this;
    }

}