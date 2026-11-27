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
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import lol.pbu.z4j.Generated;

/**
 * SealedTicketCustomFieldDeserializer
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Singleton
@Generated
public class SealedTicketCustomFieldDeserializer implements Deserializer<TicketCustomField> {

    @Override
    public TicketCustomField deserialize(@NonNull Decoder decoder, @NonNull DecoderContext context, @NonNull Argument<? super TicketCustomField> type) throws IOException {
        if (decoder.decodeNull()) {
            return null;
        }

        Long id = null;
        Object value = null;

        try (Decoder objectDecoder = decoder.decodeObject()) {
            String key;
            while ((key = objectDecoder.decodeKey()) != null) {
                switch (key) {
                    case "id" -> id = objectDecoder.decodeNull() ? null : objectDecoder.decodeLong();
                    case "value" -> value = objectDecoder.decodeArbitrary();
                    default -> objectDecoder.skipValue();
                }
            }
        }

        if (value == null) {
            return new TicketCustomField.Raw(id, null);
        }

        if (value instanceof List<?> list) {
            return decodeList(id, list);
        }

        return switch (value) {
            case String s -> new TicketCustomField.Text(id, s);
            case Boolean b -> new TicketCustomField.Checkbox(id, b);
            case Number n -> handleNumericType(id, n);
            default -> new TicketCustomField.Raw(id, value);
        };
    }

    private TicketCustomField decodeList(@Nullable Long id, List<?> list) {
        boolean allStrings = list.stream().allMatch(item -> item == null || item instanceof String);
        if (!allStrings) {
            return new TicketCustomField.Raw(id, list);
        }
        List<String> tags = list.stream()
                .map(String.class::cast)
                .toList();
        return new TicketCustomField.TagList(id, tags);
    }

    private TicketCustomField handleNumericType(@Nullable Long id, Number n) {
        if (n instanceof Double || n instanceof Float || n instanceof BigDecimal) {
            return new TicketCustomField.Decimal(id, n.doubleValue());
        }
        return new TicketCustomField.Numeric(id, n.longValue());
    }
}
