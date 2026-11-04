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
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom deserializer for {@link AuditEventsInner} supporting polymorphic and arbitrary payloads.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.2
 */
@Singleton
public class AuditEventsInnerDeserializer implements Deserializer<AuditEventsInner> {

    private static final Logger log = LoggerFactory.getLogger(AuditEventsInnerDeserializer.class);

    private static final Set<String> KNOWN_FIELDS = Set.of(
            "id", "type", "field_name", "body", "value", "previous_value", "via", "subject", "recipients"
    );

    private final Provider<ObjectMapper> objectMapperProvider;

    @Inject
    public AuditEventsInnerDeserializer(Provider<ObjectMapper> objectMapperProvider) {
        this.objectMapperProvider = objectMapperProvider;
    }

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
        preserveUnmappedFieldsIfBodyEmpty(node, builder);
        populateValues(node, builder);
        populateVia(node, builder);
        populateRecipients(node, builder);

        return builder.build();
    }

    private void populateId(JsonNode node, AuditEventsInnerBuilder builder) {
        JsonNode idNode = node.get("id");
        if (idNode != null) {
            if (idNode.isNumber()) {
                builder.id(idNode.getLongValue());
            } else if (idNode.isString()) {
                try {
                    builder.id(Long.parseLong(idNode.getStringValue().trim()));
                } catch (NumberFormatException _) {
                    // Ignore non-numeric id
                }
            }
        }
    }

    private void populateStringFields(JsonNode node, AuditEventsInnerBuilder builder) {
        JsonNode typeNode = node.get("type");
        if (typeNode != null && !typeNode.isNull()) {
            builder.type(typeNode.isString() ? typeNode.getStringValue() : typeNode.coerceStringValue());
        }

        JsonNode fieldNameNode = node.get("field_name");
        if (fieldNameNode != null && !fieldNameNode.isNull()) {
            builder.fieldName(fieldNameNode.isString() ? fieldNameNode.getStringValue() : fieldNameNode.coerceStringValue());
        }

        JsonNode subjectNode = node.get("subject");
        if (subjectNode != null && !subjectNode.isNull()) {
            builder.subject(subjectNode.isString() ? subjectNode.getStringValue() : subjectNode.coerceStringValue());
        }
    }

    private void populateBody(JsonNode node, AuditEventsInnerBuilder builder) throws IOException {
        JsonNode bodyNode = node.get("body");
        if (bodyNode != null && !bodyNode.isNull()) {
            if (bodyNode.isString()) {
                builder.body(bodyNode.getStringValue());
            } else {
                ObjectMapper mapper = objectMapperProvider.get();
                if (mapper != null) {
                    builder.body(mapper.writeValueAsString(bodyNode));
                } else {
                    builder.body(bodyNode.coerceStringValue());
                }
            }
        }
    }

    private void preserveUnmappedFieldsIfBodyEmpty(JsonNode node, AuditEventsInnerBuilder builder) throws IOException {
        if (builder.body() != null && !builder.body().isBlank()) {
            return;
        }
        Map<String, Object> unmapped = new LinkedHashMap<>();
        for (Entry<String, JsonNode> entry : node.entries()) {
            if (!KNOWN_FIELDS.contains(entry.getKey())) {
                unmapped.put(entry.getKey(), extractArbitraryValue(entry.getValue()));
            }
        }
        if (!unmapped.isEmpty()) {
            ObjectMapper mapper = objectMapperProvider.get();
            if (mapper != null) {
                builder.body(mapper.writeValueAsString(unmapped));
            }
        }
    }

    private void populateValues(JsonNode node, AuditEventsInnerBuilder builder) {
        JsonNode valueNode = node.get("value");
        if (valueNode != null && !valueNode.isNull()) {
            builder.value(extractArbitraryValue(valueNode));
        }

        JsonNode prevValueNode = node.get("previous_value");
        if (prevValueNode != null && !prevValueNode.isNull()) {
            builder.previousValue(extractArbitraryValue(prevValueNode));
        }
    }

    private void populateVia(JsonNode node, AuditEventsInnerBuilder builder) {
        JsonNode viaNode = node.get("via");
        if (viaNode != null && !viaNode.isNull()) {
            try {
                ObjectMapper mapper = objectMapperProvider.get();
                if (mapper != null) {
                    builder.via(mapper.readValueFromTree(viaNode, Via.class));
                }
            } catch (Exception e) {
                log.debug("Failed to deserialize audit event 'via' structure, ignoring to preserve audit stream: {}", e.getMessage());
            }
        }
    }

    private void populateRecipients(JsonNode node, AuditEventsInnerBuilder builder) {
        JsonNode recipientsNode = node.get("recipients");
        if (recipientsNode != null && recipientsNode.isArray()) {
            List<Long> recipients = new ArrayList<>();
            for (JsonNode r : recipientsNode.values()) {
                if (r.isNumber()) {
                    recipients.add(r.getLongValue());
                } else if (r.isString()) {
                    try {
                        recipients.add(Long.parseLong(r.getStringValue().trim()));
                    } catch (NumberFormatException e) {
                        log.debug("Skipping non-numeric recipient ID '{}': {}", r.getStringValue(), e.getMessage());
                    }
                }
            }
            builder.recipients(recipients);
        }
    }

    @Nullable
    private Object extractArbitraryValue(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        if (node.isString()) {
            return node.getStringValue();
        }
        if (node.isBoolean()) {
            return node.getBooleanValue();
        }
        if (node.isNumber()) {
            return node.getNumberValue();
        }
        if (node.isArray()) {
            return StreamSupport.stream(node.values().spliterator(), false)
                    .map(this::extractArbitraryValue)
                    .toList();
        }
        if (node.isObject()) {
            Map<String, Object> map = new LinkedHashMap<>();
            for (Entry<String, JsonNode> entry : node.entries()) {
                map.put(entry.getKey(), extractArbitraryValue(entry.getValue()));
            }
            return map;
        }
        return node.coerceStringValue();
    }
}
