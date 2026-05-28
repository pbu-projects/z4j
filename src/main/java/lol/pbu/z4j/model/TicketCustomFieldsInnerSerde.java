package lol.pbu.z4j.model;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
public class TicketCustomFieldsInnerSerde implements Serde<TicketCustomFieldsInner> {

    private final Serde<TicketCustomFieldBoolean> booleanSerde;
    private final Serde<TicketCustomFieldFloat> floatSerde;
    private final Serde<TicketCustomFieldLong> longSerde;
    private final Serde<TicketCustomFieldString> stringSerde;
    private final Serde<TicketCustomFieldStringArray> stringArraySerde;

    public TicketCustomFieldsInnerSerde(
            Serde<TicketCustomFieldBoolean> booleanSerde,
            Serde<TicketCustomFieldFloat> floatSerde,
            Serde<TicketCustomFieldLong> longSerde,
            Serde<TicketCustomFieldString> stringSerde,
            Serde<TicketCustomFieldStringArray> stringArraySerde
    ) {
        this.booleanSerde = booleanSerde;
        this.floatSerde = floatSerde;
        this.longSerde = longSerde;
        this.stringSerde = stringSerde;
        this.stringArraySerde = stringArraySerde;
    }

    @Override
    public TicketCustomFieldsInner deserialize(Decoder decoder, DecoderContext context, Argument<? super TicketCustomFieldsInner> type) throws IOException {
        try (Decoder objectDecoder = decoder.decodeObject()) {
            String key;
            while ((key = objectDecoder.decodeKey()) != null) {
                if ("type".equals(key)) {
                    String typeValue = objectDecoder.decodeString();
                    switch (typeValue) {
                        case "boolean":
                            return booleanSerde.deserialize(decoder, context, Argument.of(TicketCustomFieldBoolean.class));
                        case "float":
                            return floatSerde.deserialize(decoder, context, Argument.of(TicketCustomFieldFloat.class));
                        case "long":
                            return longSerde.deserialize(decoder, context, Argument.of(TicketCustomFieldLong.class));
                        case "string":
                            return stringSerde.deserialize(decoder, context, Argument.of(TicketCustomFieldString.class));
                        case "string_array":
                            return stringArraySerde.deserialize(decoder, context, Argument.of(TicketCustomFieldStringArray.class));
                        default:
                            throw new IOException("Unknown type: " + typeValue);
                    }
                } else {
                    objectDecoder.skipValue();
                }
            }
        }
        return null;
    }

    @Override
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends TicketCustomFieldsInner> type, TicketCustomFieldsInner value) throws IOException {
        if (value instanceof TicketCustomFieldBoolean) {
            booleanSerde.serialize(encoder, context, Argument.of(TicketCustomFieldBoolean.class), (TicketCustomFieldBoolean) value);
        } else if (value instanceof TicketCustomFieldFloat) {
            floatSerde.serialize(encoder, context, Argument.of(TicketCustomFieldFloat.class), (TicketCustomFieldFloat) value);
        } else if (value instanceof TicketCustomFieldLong) {
            longSerde.serialize(encoder, context, Argument.of(TicketCustomFieldLong.class), (TicketCustomFieldLong) value);
        } else if (value instanceof TicketCustomFieldString) {
            stringSerde.serialize(encoder, context, Argument.of(TicketCustomFieldString.class), (TicketCustomFieldString) value);
        } else if (value instanceof TicketCustomFieldStringArray) {
            stringArraySerde.serialize(encoder, context, Argument.of(TicketCustomFieldStringArray.class), (TicketCustomFieldStringArray) value);
        }
    }
}
