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
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import io.micronaut.serde.exceptions.SerdeException;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom deserializer for {@link AuditEventsInner} supporting different event types and flexible payloads.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.2
 */
@Singleton
public class AuditEventsInnerDeserializer implements Deserializer<AuditEventsInner> {

    private static final Logger log = LoggerFactory.getLogger(AuditEventsInnerDeserializer.class);

    @Nullable
    @Override
    public AuditEventsInner deserialize(
            @NonNull Decoder decoder,
            @NonNull DecoderContext context,
            @NonNull Argument<? super AuditEventsInner> type
    ) throws IOException {
        JsonNode node = decoder.decodeNode();
        if (node.isNull() || !node.isObject()) {
            return null;
        }

        AuditEventsInnerBuilder builder = AuditEventsInnerBuilder.builder();

        populateId(node, builder);
        populateStringFields(node, builder);
        populateBody(node, builder);
        populateValues(node, builder);
        populateVia(node, context, builder);
        populateRecipients(node, builder);

        return builder.build();
    }

    private void populateId(JsonNode node, AuditEventsInnerBuilder builder) throws IOException {
        JsonNode idNode = node.get("id");
        if (idNode == null || idNode.isNull()) {
            return;
        }
        if (idNode.isNumber()) {
            builder.id(idNode.getLongValue());
        } else if (idNode.isString()) {
            try {
                builder.id(Long.parseLong(idNode.getStringValue().trim()));
            } catch (NumberFormatException e) {
                throw new SerdeException("Invalid numeric audit event id: '" + idNode.getStringValue() + "'", e);
            }
        } else {
            throw new SerdeException("Invalid audit event id type: expected number or string");
        }
    }

    private void populateStringFields(JsonNode node, AuditEventsInnerBuilder builder) throws SerdeException {
        String type = readStringField(node, "type");
        if (type != null) {
            builder.type(type);
        }

        String fieldName = readStringField(node, "field_name");
        if (fieldName != null) {
            builder.fieldName(fieldName);
        }

        String subject = readStringField(node, "subject");
        if (subject != null) {
            builder.subject(subject);
        }
    }

    @Nullable
    private static String readStringField(JsonNode node, String fieldName) throws SerdeException {
        JsonNode fieldNode = node.get(fieldName);
        if (fieldNode == null || fieldNode.isNull()) {
            return null;
        }
        if (fieldNode.isString()) {
            return fieldNode.getStringValue();
        }
        if (!fieldNode.isValueNode()) {
            throw new SerdeException("Expected scalar string value for audit event field '" + fieldName + "'");
        }
        return fieldNode.coerceStringValue();
    }

    private void populateBody(JsonNode node, AuditEventsInnerBuilder builder) {
        JsonNode bodyNode = node.get("body");
        if (bodyNode != null && !bodyNode.isNull()) {
            builder.body(bodyNode.isString() ? bodyNode.getStringValue() : JsonNodeTreeDecoder.toJson(bodyNode));
        }
    }

    private void populateValues(JsonNode node, AuditEventsInnerBuilder builder) {
        JsonNode valueNode = node.get("value");
        if (valueNode != null && !valueNode.isNull()) {
            builder.value(JsonNodeTreeDecoder.toArbitrary(valueNode));
        }

        JsonNode prevValueNode = node.get("previous_value");
        if (prevValueNode != null && !prevValueNode.isNull()) {
            builder.previousValue(JsonNodeTreeDecoder.toArbitrary(prevValueNode));
        }
    }

    private void populateVia(JsonNode node, DecoderContext context, AuditEventsInnerBuilder builder) throws IOException {
        JsonNode viaNode = node.get("via");
        if (viaNode != null && !viaNode.isNull()) {
            builder.via(PolymorphicSerdeSupport.deserializeTarget(viaNode, context, Via.class));
        }
    }

    private void populateRecipients(JsonNode node, AuditEventsInnerBuilder builder) {
        JsonNode recipientsNode = node.get("recipients");
        if (recipientsNode != null && recipientsNode.isArray()) {
            List<Long> recipients = StreamSupport.stream(recipientsNode.values().spliterator(), false)
                    .map(this::parseRecipientId)
                    .filter(Objects::nonNull)
                    .toList();
            builder.recipients(recipients);
        }
    }

    @Nullable
    private Long parseRecipientId(JsonNode recipientNode) {
        if (recipientNode.isNumber()) {
            return recipientNode.getLongValue();
        }
        if (recipientNode.isString()) {
            try {
                return Long.parseLong(recipientNode.getStringValue().trim());
            } catch (NumberFormatException e) {
                log.debug("Skipping non-numeric recipient ID '{}': {}", recipientNode.getStringValue(), e.getMessage());
            }
        }
        return null;
    }
}
