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
import lol.pbu.z4j.model.TicketObject;
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