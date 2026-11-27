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

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.type.Argument;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import io.micronaut.serde.exceptions.SerdeException;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.Map;

/**
 * Deserializes {@link Exportable} items using {@code result_type} or object fields.
 */
@Singleton
public class ExportableDeserializer implements Deserializer<Exportable> {

    private static final Map<String, Class<? extends Exportable>> RESULT_TYPES = Map.of(
            "ticket", Ticket.class,
            "user", User.class,
            "organization", Organization.class,
            "group", Group.class,
            "ticket_event", TicketEvent.class
    );

    @Override
    public Exportable deserialize(
            @NonNull Decoder decoder,
            @NonNull DecoderContext context,
            @NonNull Argument<? super Exportable> type
    ) throws IOException {
        return PolymorphicSerdeSupport.deserialize(decoder, context, "Exportable", this::resolveTargetType);
    }

    private Class<? extends Exportable> resolveTargetType(JsonNode node) throws SerdeException {
        Class<? extends Exportable> byResultType = PolymorphicSerdeSupport.resolveByResultType(node, RESULT_TYPES, "Exportable");
        if (byResultType != null) {
            return byResultType;
        }

        if (PolymorphicSerdeSupport.hasAnyField(node, "event_type", "ticket_id", "audit_events", "child_events")) {
            return TicketEvent.class;
        }
        if (PolymorphicSerdeSupport.hasAnyField(node, "requester_id", "subject", "status", "priority", "custom_fields")) {
            return Ticket.class;
        }
        if (PolymorphicSerdeSupport.hasAnyField(node, "email", "role", "iana_time_zone", "user_fields")) {
            return User.class;
        }
        if (PolymorphicSerdeSupport.hasAnyField(node, "default", "deleted", "is_public")) {
            return Group.class;
        }
        if (PolymorphicSerdeSupport.hasAnyField(node, "domain_names", "organization_fields", "shared_tickets", "shared_comments", "group_id")) {
            return Organization.class;
        }
        if (PolymorphicSerdeSupport.hasAnyField(node, "id")) {
            return Organization.class;
        }
        throw new SerdeException("Cannot determine Exportable type from JSON object");
    }
}
