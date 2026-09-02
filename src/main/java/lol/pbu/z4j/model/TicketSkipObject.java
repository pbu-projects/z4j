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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * TicketSkipObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketSkipObject.JSON_PROPERTY_CREATED_AT,
    TicketSkipObject.JSON_PROPERTY_ID,
    TicketSkipObject.JSON_PROPERTY_REASON,
    TicketSkipObject.JSON_PROPERTY_TICKET,
    TicketSkipObject.JSON_PROPERTY_TICKET_ID,
    TicketSkipObject.JSON_PROPERTY_UPDATED_AT,
    TicketSkipObject.JSON_PROPERTY_USER_ID,
})
@Serdeable
public class TicketSkipObject {

    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_REASON = "reason";
    public static final String JSON_PROPERTY_TICKET = "ticket";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_USER_ID = "user_id";

    /**
     * <p>Time the skip was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>Automatically assigned upon creation</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>Reason for skipping the ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_REASON)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String reason;

    /**
     * <p>The skipped ticket. See the <a href=\"/api-reference/ticketing/tickets/tickets/#json-format\">Ticket object reference</a></p>
     */
    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_TICKET)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketObject ticket;

    /**
     * <p>ID of the skipped ticket</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long ticketId;

    /**
     * <p>Time the skip was last updated</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * <p>ID of the skipping agent</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long userId;

}