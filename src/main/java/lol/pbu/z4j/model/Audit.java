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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Ticket audit record showing changes made to a ticket.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        Audit.JSON_PROPERTY_AUTHOR_ID,
        Audit.JSON_PROPERTY_CREATED_AT,
        Audit.JSON_PROPERTY_EVENTS,
        Audit.JSON_PROPERTY_ID,
        Audit.JSON_PROPERTY_METADATA,
        Audit.JSON_PROPERTY_TICKET_ID,
        Audit.JSON_PROPERTY_VIA
})
public record Audit(
        @Nullable @JsonProperty(JSON_PROPERTY_AUTHOR_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long authorId,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_EVENTS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid AuditEventsInner> events,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_METADATA) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Object metadata,
        @Nullable @JsonProperty(JSON_PROPERTY_TICKET_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long ticketId,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_VIA) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Via via
) implements AuditBuilder.With {

    public static final String JSON_PROPERTY_AUTHOR_ID = "author_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_EVENTS = "events";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_METADATA = "metadata";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_VIA = "via";

    public Audit() {
        this(null, null, null, null, null, null, null);
    }

    public Audit addEventsItem(AuditEventsInner eventsItem) {
        List<AuditEventsInner> newEvents = events == null ? new ArrayList<>() : new ArrayList<>(events);
        newEvents.add(eventsItem);
        return withEvents(newEvents);
    }
}
