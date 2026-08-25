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
 * TicketContentPin
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    TicketContentPin.JSON_PROPERTY_ACCOUNT_ID,
    TicketContentPin.JSON_PROPERTY_CONTENT_ID,
    TicketContentPin.JSON_PROPERTY_CONTENT_TYPE,
    TicketContentPin.JSON_PROPERTY_CREATED_AT,
    TicketContentPin.JSON_PROPERTY_ID,
    TicketContentPin.JSON_PROPERTY_LOCALE,
    TicketContentPin.JSON_PROPERTY_TICKET_ID,
    TicketContentPin.JSON_PROPERTY_URL,
})
@Serdeable
public class TicketContentPin {

    public static final String JSON_PROPERTY_ACCOUNT_ID = "account_id";
    public static final String JSON_PROPERTY_CONTENT_ID = "content_id";
    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * <p>The ID of the account that owns the content pin.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ACCOUNT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String accountId;

    /**
     * <p>The ID of the content that is pinned.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentId;

    /**
     * <p>The type of content that is pinned. Example: external_content</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentType;

    /**
     * <p>The timestamp when the content pin was created.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The unique identifier for the content pin.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>The locale of the content pin.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String locale;

    /**
     * <p>The ID of the ticket associated with the content pin.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String ticketId;

    /**
     * <p>The URL to access the pinned content.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}