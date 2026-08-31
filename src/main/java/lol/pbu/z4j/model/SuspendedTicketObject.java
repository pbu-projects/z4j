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
import java.util.List;
import lol.pbu.z4j.model.AttachmentObject;
import lol.pbu.z4j.model.SuspendedTicketObjectAuthor;
import lol.pbu.z4j.model.ViaObject;
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
 * SuspendedTicketObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    SuspendedTicketObject.JSON_PROPERTY_ATTACHMENTS,
    SuspendedTicketObject.JSON_PROPERTY_AUTHOR,
    SuspendedTicketObject.JSON_PROPERTY_BRAND_ID,
    SuspendedTicketObject.JSON_PROPERTY_CAUSE,
    SuspendedTicketObject.JSON_PROPERTY_CAUSE_ID,
    SuspendedTicketObject.JSON_PROPERTY_CONTENT,
    SuspendedTicketObject.JSON_PROPERTY_CREATED_AT,
    SuspendedTicketObject.JSON_PROPERTY_ERROR_MESSAGES,
    SuspendedTicketObject.JSON_PROPERTY_ID,
    SuspendedTicketObject.JSON_PROPERTY_MESSAGE_ID,
    SuspendedTicketObject.JSON_PROPERTY_RECIPIENT,
    SuspendedTicketObject.JSON_PROPERTY_SUBJECT,
    SuspendedTicketObject.JSON_PROPERTY_TICKET_ID,
    SuspendedTicketObject.JSON_PROPERTY_UPDATED_AT,
    SuspendedTicketObject.JSON_PROPERTY_URL,
    SuspendedTicketObject.JSON_PROPERTY_VIA,
})
@Serdeable
public class SuspendedTicketObject {

    public static final String JSON_PROPERTY_ATTACHMENTS = "attachments";
    public static final String JSON_PROPERTY_AUTHOR = "author";
    public static final String JSON_PROPERTY_BRAND_ID = "brand_id";
    public static final String JSON_PROPERTY_CAUSE = "cause";
    public static final String JSON_PROPERTY_CAUSE_ID = "cause_id";
    public static final String JSON_PROPERTY_CONTENT = "content";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ERROR_MESSAGES = "error_messages";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_MESSAGE_ID = "message_id";
    public static final String JSON_PROPERTY_RECIPIENT = "recipient";
    public static final String JSON_PROPERTY_SUBJECT = "subject";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VIA = "via";

    /**
     * <p>The attachments, if any associated to this suspended ticket. See <a href=\"/api-reference/ticketing/tickets/ticket-attachments/\">Attachments</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid AttachmentObject> attachments;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_AUTHOR)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private SuspendedTicketObjectAuthor author;

    /**
     * <p>The id of the brand this ticket is associated with. Only applicable for Enterprise accounts</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BRAND_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long brandId;

    /**
     * <p>Why the ticket was suspended</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CAUSE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String cause;

    /**
     * <p>The ID of the cause</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CAUSE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long causeId;

    /**
     * <p>The content that was flagged</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String content;

    /**
     * <p>The ticket ID this suspended email is associated with, if available</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The error messages if any associated to this suspended ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ERROR_MESSAGES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<Object> errorMessages;

    /**
     * <p>Automatically assigned</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>The ID of the email, if available</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MESSAGE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String messageId;

    /**
     * <p>The original recipient e-mail address of the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RECIPIENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String recipient;

    /**
     * <p>The value of the subject field for this ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SUBJECT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String subject;

    /**
     * <p>The ticket ID this suspended email is associated with, if available</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketId;

    /**
     * <p>When the ticket was assigned</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>The API url of this ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_VIA)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ViaObject via;

}