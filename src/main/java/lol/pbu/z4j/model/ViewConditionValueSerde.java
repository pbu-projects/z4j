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
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import io.micronaut.serde.exceptions.SerdeException;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.List;

/**
 * Serializes and deserializes {@link ViewConditionValue} as flat JSON values
 * (string, string array, boolean, number, or null).
 *
 * @author Jonathan-Zollinger
 * @since 0.4.3
 */
@Singleton
public class ViewConditionValueSerde implements Serde<ViewConditionValue> {

    private static final Argument<List<String>> STRING_LIST_ARGUMENT = Argument.listOf(String.class);

    @Override
    public void serialize(
            @NonNull Encoder encoder,
            @NonNull EncoderContext context,
            @NonNull Argument<? extends ViewConditionValue> type,
            @Nullable ViewConditionValue value) throws IOException {
        if (value == null) {
            encoder.encodeNull();
            return;
        }
        switch (value) {
            case ViewConditionValue.Text(String text) -> encodeNullableString(encoder, text);
            case ViewConditionValue.TextList(List<String> values) -> encodeStringList(encoder, values);
            case ViewConditionValue.Flag(Boolean flag) -> encodeNullableBoolean(encoder, flag);
            case ViewConditionValue.Numeric(Long number) -> encodeNullableLong(encoder, number);
        }
    }

    @Override
    public ViewConditionValue deserialize(
            @NonNull Decoder decoder,
            @NonNull DecoderContext context,
            @NonNull Argument<? super ViewConditionValue> type) throws IOException {
        if (decoder.decodeNull()) {
            return null;
        }
        Object raw = decoder.decodeArbitrary();
        if (raw == null) {
            return null;
        }
        return switch (raw) {
            case String text -> new ViewConditionValue.Text(text);
            case Boolean flag -> new ViewConditionValue.Flag(flag);
            case Number number -> new ViewConditionValue.Numeric(number.longValue());
            case List<?> items -> decodeTextList(items);
            default -> throw new SerdeException(
                    "Unsupported view condition value type: " + raw.getClass().getSimpleName());
        };
    }

    private void encodeNullableString(Encoder encoder, @Nullable String text) throws IOException {
        if (text == null) {
            encoder.encodeNull();
        } else {
            encoder.encodeString(text);
        }
    }

    private void encodeNullableBoolean(Encoder encoder, @Nullable Boolean flag) throws IOException {
        if (flag == null) {
            encoder.encodeNull();
        } else {
            encoder.encodeBoolean(flag);
        }
    }

    private void encodeNullableLong(Encoder encoder, @Nullable Long number) throws IOException {
        if (number == null) {
            encoder.encodeNull();
        } else {
            encoder.encodeLong(number);
        }
    }

    private void encodeStringList(Encoder encoder, @Nullable List<String> values) throws IOException {
        if (values == null) {
            encoder.encodeNull();
            return;
        }
        try (Encoder arrayEncoder = encoder.encodeArray(STRING_LIST_ARGUMENT)) {
            for (String item : values) {
                encodeNullableString(arrayEncoder, item);
            }
        }
    }

    private ViewConditionValue.TextList decodeTextList(List<?> items) throws SerdeException {
        boolean hasNonString = items.stream().anyMatch(item -> item != null && !(item instanceof String));
        if (hasNonString) {
            throw new SerdeException("View condition list values must only contain strings");
        }
        List<String> strings = items.stream()
                .map(String.class::cast)
                .toList();
        return new ViewConditionValue.TextList(strings);
    }
}
