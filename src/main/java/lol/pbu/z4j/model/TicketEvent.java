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

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Event entry from incremental ticket event exports.
 *
 * @author Jonathan-Zollinger
 * @since 0.2.7
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        TicketEvent.JSON_PROPERTY_ID,
        TicketEvent.JSON_PROPERTY_TICKET_ID,
        TicketEvent.JSON_PROPERTY_EVENT_TYPE,
        TicketEvent.JSON_PROPERTY_VIA,
        TicketEvent.JSON_PROPERTY_CREATED_AT,
        TicketEvent.JSON_PROPERTY_AUTHOR_ID,
        TicketEvent.JSON_PROPERTY_VALUE,
        TicketEvent.JSON_PROPERTY_METADATA,
        TicketEvent.JSON_PROPERTY_AUDIT_EVENTS
})
public record TicketEvent(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_TICKET_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long ticketId,
        @Nullable @JsonProperty(JSON_PROPERTY_EVENT_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String eventType,
        @Nullable @JsonProperty(JSON_PROPERTY_VIA) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Object via,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_AUTHOR_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long authorId,
        @Nullable @JsonProperty(JSON_PROPERTY_VALUE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Object value,
        @Nullable @JsonProperty(JSON_PROPERTY_METADATA) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Map<String, Object> metadata,
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_AUDIT_EVENTS) @JsonAlias({"child_events", "events"}) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid AuditEventsInner> auditEvents
) implements Exportable, TicketEventBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_TICKET_ID = "ticket_id";
    public static final String JSON_PROPERTY_EVENT_TYPE = "event_type";
    public static final String JSON_PROPERTY_VIA = "via";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_AUTHOR_ID = "author_id";
    public static final String JSON_PROPERTY_VALUE = "value";
    public static final String JSON_PROPERTY_METADATA = "metadata";
    public static final String JSON_PROPERTY_AUDIT_EVENTS = "audit_events";
    public static final String JSON_PROPERTY_CHILD_EVENTS = "child_events";

    public TicketEvent() {
        this(null, null, null, null, null, null, null, null, null);
    }

    public TicketEvent(Long id) {
        this(id, null, null, null, null, null, null, null, null);
    }

    public TicketEvent addAuditEventsItem(AuditEventsInner auditEventsItem) {
        List<AuditEventsInner> newAuditEvents = auditEvents == null ? new ArrayList<>() : new ArrayList<>(auditEvents);
        newAuditEvents.add(auditEventsItem);
        return withAuditEvents(newAuditEvents);
    }
}
