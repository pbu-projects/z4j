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

import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import io.micronaut.serde.exceptions.SerdeException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Shared helper for model deserializers that choose a target type from JSON.
 */
final class PolymorphicSerdeSupport {

    @FunctionalInterface
    interface TypeResolver<T> {
        Class<? extends T> resolve(JsonNode node) throws SerdeException;
    }

    private PolymorphicSerdeSupport() {
    }

    @Nullable
    static <T> T deserialize(
            Decoder decoder,
            Deserializer.DecoderContext context,
            String typeName,
            TypeResolver<T> typeResolver
    ) throws IOException {
        JsonNode node = decodeObjectNode(decoder, typeName);
        if (node == null) {
            return null;
        }
        Class<? extends T> targetType = typeResolver.resolve(node);
        return deserializeTarget(node, context, targetType);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    static <T, S extends T> S deserializeTarget(
            @Nullable JsonNode node,
            Deserializer.DecoderContext context,
            Class<S> targetType
    ) throws IOException {
        if (node == null || node.isNull()) {
            return null;
        }
        Argument<S> targetArgument = Argument.of(targetType);
        Deserializer<S> deserializer = (Deserializer<S>) context.findDeserializer(targetArgument)
                .createSpecific(context, targetArgument);
        return deserializer.deserialize(JsonNodeTreeDecoder.of(node), context, targetArgument);
    }

    @Nullable
    static JsonNode decodeObjectNode(Decoder decoder, String typeName) throws IOException {
        JsonNode node = decoder.decodeNode();
        if (node.isNull()) {
            return null;
        }
        if (!node.isObject()) {
            throw new SerdeException("Expected a JSON object for " + typeName);
        }
        return node;
    }

    @Nullable
    static <T> Class<? extends T> resolveByResultType(
            JsonNode node,
            Map<String, Class<? extends T>> resultTypes,
            String typeName
    ) throws SerdeException {
        JsonNode resultTypeNode = node.get("result_type");
        if (resultTypeNode == null || resultTypeNode.isNull()) {
            return null;
        }
        if (!resultTypeNode.isString()) {
            throw new SerdeException("Expected a string result_type for " + typeName);
        }
        String resultType = resultTypeNode.getStringValue();
        Class<? extends T> mapped = resultTypes.get(resultType);
        if (mapped == null) {
            throw new SerdeException("Unknown " + typeName + " result_type: " + resultType);
        }
        return mapped;
    }

    static boolean hasAnyField(JsonNode node, String... names) {
        return Arrays.stream(names).anyMatch(name -> {
            JsonNode field = node.get(name);
            return field != null && !field.isNull();
        });
    }
}
