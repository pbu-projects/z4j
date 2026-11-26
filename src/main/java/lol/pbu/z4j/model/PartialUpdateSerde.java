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
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serializer;
import io.micronaut.serde.Serializer.EncoderContext;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Shared helpers for partial update models that need to tell unset fields apart from cleared ones.
 */
final class PartialUpdateSerde {

    private static final Argument<Object> OBJECT_ARGUMENT = Argument.OBJECT_ARGUMENT;

    private PartialUpdateSerde() {
    }

    static Set<String> addClearedField(@Nullable Set<String> existing, String fieldName) {
        Set<String> updated = existing == null ? new HashSet<>() : new HashSet<>(existing);
        updated.add(fieldName);
        return Set.copyOf(updated);
    }

    static Set<String> removeClearedField(@Nullable Set<String> existing, String fieldName) {
        if (existing == null || existing.isEmpty() || !existing.contains(fieldName)) {
            return existing == null ? Set.of() : existing;
        }
        Set<String> updated = new HashSet<>(existing);
        updated.remove(fieldName);
        return Set.copyOf(updated);
    }

    static <V> void writeField(
            Encoder encoder,
            EncoderContext context,
            String jsonName,
            Argument<V> argument,
            @Nullable V value,
            Set<String> clearedFields
    ) throws IOException {
        if (value != null) {
            encoder.encodeKey(jsonName);
            encodeValue(encoder, context, argument, value);
        } else if (clearedFields.contains(jsonName)) {
            encoder.encodeKey(jsonName);
            encoder.encodeNull();
        }
    }

    static void writeNullableMapField(
            Encoder encoder,
            EncoderContext context,
            String jsonName,
            Argument<Map<String, Object>> argument,
            @Nullable Map<String, Object> map,
            Set<String> clearedFields
    ) throws IOException {
        if (map != null) {
            encoder.encodeKey(jsonName);
            try (Encoder mapEncoder = encoder.encodeObject(argument)) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    mapEncoder.encodeKey(entry.getKey());
                    Object entryValue = entry.getValue();
                    if (entryValue == null) {
                        mapEncoder.encodeNull();
                    } else {
                        encodeValue(mapEncoder, context, OBJECT_ARGUMENT, entryValue);
                    }
                }
            }
        } else if (clearedFields.contains(jsonName)) {
            encoder.encodeKey(jsonName);
            encoder.encodeNull();
        }
    }

    @SuppressWarnings("unchecked")
    private static <V> void encodeValue(
            Encoder encoder,
            EncoderContext context,
            Argument<V> argument,
            V value
    ) throws IOException {
        switch (value) {
            case String s -> encoder.encodeString(s);
            case Long l -> encoder.encodeLong(l);
            case Integer i -> encoder.encodeInt(i);
            case Boolean b -> encoder.encodeBoolean(b);
            default -> {
                Argument<V> targetArg = argument == OBJECT_ARGUMENT
                        ? (Argument<V>) Argument.of(value.getClass())
                        : argument;
                Serializer<? super V> serializer = context.findSerializer(targetArg).createSpecific(context, targetArg);
                serializer.serialize(encoder, context, targetArg, value);
            }
        }
    }
}
