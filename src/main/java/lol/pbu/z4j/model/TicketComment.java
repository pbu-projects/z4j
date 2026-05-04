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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TicketComment
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
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
        TicketComment.JSON_PROPERTY_VIA,
})
@Serdeable
public class TicketComment {

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

    /**
     * Attachments, if any. See <a href=\"developer.zendesk.com/api-reference/ticketing/tickets/ticket-attachments/\">Attachment</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Attachment> attachments;

    /**
     * The id of the ticket audit record. See <a href=\"developer.zendesk.com/api-reference/ticketing/tickets/ticket_audits/#show-audit\">Show Audit</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUDIT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer auditId;

    /**
     * The id of the comment author. See <a href=\"#author-id\">Author id</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTHOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer authorId;

    /**
     * The comment string. See <a href=\"#bodies\">Bodies</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String body;

    /**
     * The time the comment was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * The comment formatted as HTML. See <a href=\"#bodies\">Bodies</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HTML_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String htmlBody;

    /**
     * Automatically assigned when the comment is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * System information (web client, IP address, etc.) and comment flags, if any. See <a href=\"#comment-flags\">Comment flags</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_METADATA)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> metadata;

    /**
     * The comment presented as plain text. See <a href=\"#bodies\">Bodies</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PLAIN_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String plainBody;

    /**
     * true if a public comment; false if an internal note. The initial value set on ticket creation persists for any additional comment unless you change it
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PUBLIC)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean isPublic;

    /**
     * `Comment` or `VoiceComment`. The JSON object for adding voice comments to tickets is different. See <a href=\"/documentation/ticketing/managing-tickets/adding-voice-comments-to-tickets\">Adding voice comments to tickets</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String type;

    /**
     * List of tokens received from <a href=\"developer.zendesk.com/api-reference/ticketing/tickets/ticket-attachments/#upload-files\">uploading files</a> for comment attachments. The files are attached by creating or updating tickets with the tokens. See <a href=\"developer.zendesk.com/api-reference/ticketing/tickets/tickets/#attaching-files\">Attaching files</a> in Tickets
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPLOADS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> uploads;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketVia via;

    /**
     * Add an item to the uploads property in a chainable fashion.
     *
     * @return The same instance of TicketComment for chaining.
     */
    public TicketComment addUploadsItem(String uploadsItem) {
        if (uploads == null) {
            uploads = new ArrayList<>();
        }
        uploads.add(uploadsItem);
        return this;
    }

}
