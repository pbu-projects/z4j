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
 * CreateTicketContentPinRequestTicketContentPin
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
    CreateTicketContentPinRequestTicketContentPin.JSON_PROPERTY_CONTENT_ID,
    CreateTicketContentPinRequestTicketContentPin.JSON_PROPERTY_CONTENT_TYPE,
    CreateTicketContentPinRequestTicketContentPin.JSON_PROPERTY_TICKET_ID,
    CreateTicketContentPinRequestTicketContentPin.JSON_PROPERTY_LOCALE,
})
@Serdeable
public class CreateTicketContentPinRequestTicketContentPin {

    public static final String JSON_PROPERTY_CONTENT_ID = "content_id";
    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_LOCALE = "locale";

    /**
     * <p>The id of the content to pin</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_CONTENT_ID)
    private String contentId;

    /**
     * <p>The type of content being pinned.</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    private String contentType;

    /**
     * <p>The id of the ticket to which the content pin will be added</p>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TICKET_ID)
    private String ticketId;

    /**
     * <p>The locale for the content pin. This is required only for articles.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String locale;

    public CreateTicketContentPinRequestTicketContentPin(String contentId, String contentType, String ticketId) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.ticketId = ticketId;
    }

}