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
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.util.List;

/**
 * SealedTicketCustomFieldDeserializer
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Singleton
public class SealedTicketCustomFieldDeserializer implements Deserializer<TicketCustomField> {

    @Override
    public TicketCustomField deserialize(@NonNull Decoder decoder, @NonNull DecoderContext context, @NonNull Argument<? super TicketCustomField> type) throws IOException {

        Long id = null;
        Object value = null;

        // 1. Gather raw data using streaming decoder
        try (Decoder objectDecoder = decoder.decodeObject()) {
            String key;
            while ((key = objectDecoder.decodeKey()) != null) {
                switch (key) {
                    case "id" -> id = objectDecoder.decodeLong();
                    case "value" -> value = objectDecoder.decodeArbitrary();
                    default -> objectDecoder.skipValue();
                }
            }
        }

        if (value == null) return null;

        if (value instanceof List<?> l) {
            @SuppressWarnings("unchecked") List<String> castedList = (List<String>) l;
            return new TicketCustomField.TagList(id, castedList);
        }

        return switch (value) {
            case String s -> new TicketCustomField.Text(id, s);
            case Boolean b -> new TicketCustomField.Checkbox(id, b);
            case Number n -> handleNumericType(id, n);
            default -> null;
        };
    }

    private TicketCustomField handleNumericType(Long id, Number n) {
        if (n instanceof Double || n instanceof Float) {
            return new TicketCustomField.Decimal(id, n.floatValue());
        }
        return new TicketCustomField.Numeric(id, n.longValue());
    }
}
