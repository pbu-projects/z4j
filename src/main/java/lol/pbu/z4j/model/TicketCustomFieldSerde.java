package lol.pbu.z4j.model;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Custom Serde implementation for TicketCustomField
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@Singleton
public class TicketCustomFieldSerde implements Serde<TicketCustomField<?>> {
    private static final String VALIDATION_ERR = "Type passed as custom field cannot be something other than String, List<String>, Long, Float or Boolean";

    @Override
    public TicketCustomField<?> deserialize(Decoder decoder, DecoderContext context, Argument<? super TicketCustomField<?>> instance) throws IOException {
        Long id = null;
        Object value = null;
        Argument<?> valueTypeArgument = instance.getFirstTypeVariable().orElse(Argument.OBJECT_ARGUMENT);
        var objectDecoder = decoder.decodeObject();
        String currentKey;
        while ((currentKey = objectDecoder.decodeKey()) != null) {
            switch (currentKey) {
                case "id" -> id = decoder.decodeLong();
                case "value" -> {
                    switch (valueTypeArgument.getType()) {
                        case Class<?> c when c == String.class -> value = decoder.decodeString();
                        case Class<?> c when c == List.class -> value = decodeStringList(decoder);
                        case Class<?> c when c == Long.class -> value = decoder.decodeNumber().longValue();
                        case Class<?> c when c == Float.class -> value = decoder.decodeNumber().floatValue();
                        case Class<?> c when c == Boolean.class -> value = decoder.decodeBoolean();
                        case Class<?> c when c == Object.class || c == Void.class -> value = decoder.decodeNull();
                        default -> throw new IllegalArgumentException(VALIDATION_ERR);
                    }
                }
                default -> decoder.skipValue();
            }
        }

        return new TicketCustomField<>().setId(id).setValue(value);
    }

    /**
     * The sole purpose of this method is to make the {@link #deserialize} method easier to read and maintain
     */
    private List<String> decodeStringList(Decoder decoder) {
        List<String> result = new ArrayList<>();
        try (Decoder arrayDecoder = decoder.decodeArray()) {
            while (arrayDecoder.hasNextArrayValue()) {
                result.add(arrayDecoder.decodeStringNullable());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to decode expected String or null value from index of List<String> in TicketCustomField. {}", e);
        }
        return result;
    }

    @Override
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends TicketCustomField<?>> type, TicketCustomField<?> instance) throws IOException {
        Objects.requireNonNull(instance, "TicketCustomField cannot be null");
        encodeId(encoder, instance);
        encodeValue(encoder, type, instance);
        encoder.finishStructure();
    }

    /**
     * The sole purpose of this method is to make the {@link #serialize} method easier to read and maintain
     */
    private static void encodeValue(Encoder encoder, Argument<? extends TicketCustomField<?>> type, TicketCustomField<?> instance) throws IOException {
        encoder.encodeKey("value");
        // getFirstTypeVariable retrieves the generic type used
        Argument<?> valueTypeArgument = type.getFirstTypeVariable().orElse(Argument.OBJECT_ARGUMENT);
        switch (valueTypeArgument.getType()) {
            case Class<?> c when c == String.class -> encoder.encodeString((String) instance.value);
            case Class<?> c when c == List.class -> encodeStringArray(encoder, instance, valueTypeArgument);
            case Class<?> c when c == Long.class -> encoder.encodeLong((long) instance.value);
            case Class<?> c when c == Float.class -> encoder.encodeFloat((float) instance.value);
            case Class<?> c when c == Boolean.class -> encoder.encodeBoolean((boolean) instance.value);
            case Class<?> c when c == Object.class || c == Void.class -> encoder.encodeNull();
            default -> throw new IllegalArgumentException(VALIDATION_ERR);
        }
    }

    /**
     * The sole purpose of this method is to make the {@link #serialize} method easier to read and maintain
     */
    private static void encodeId(Encoder encoder, TicketCustomField<?> instance) throws IOException {
        encoder.encodeKey("id");
        if (instance.id == null) {
            encoder.encodeNull();
        } else {
            encoder.encodeLong(instance.id);
        }
    }

    /**
     * The sole purpose of this method is to make the {@link #serialize} method easier to read and maintain
     */
    private static void encodeStringArray(Encoder encoder, TicketCustomField<?> instance, Argument<?> valueTypeArgument) throws IOException {
        try (Encoder arrayEncoder = encoder.encodeArray(valueTypeArgument)) {
            ((List<?>) instance.value).forEach(index -> {
                if (null == index) {
                    try {
                        arrayEncoder.encodeNull();
                    } catch (IOException e) {
                        throw new IllegalArgumentException("Unable to encode expected null value from index of List<String> in TicketCustomField. {}", e);
                    }
                } else {
                    try {
                        arrayEncoder.encodeString((String) index);
                    } catch (IOException e) {
                        throw new IllegalArgumentException("Unable to encode expected String value from index of List<String> in TicketCustomField. {}", e);
                    }
                }
            });
            arrayEncoder.finishStructure();
        }
    }

}

