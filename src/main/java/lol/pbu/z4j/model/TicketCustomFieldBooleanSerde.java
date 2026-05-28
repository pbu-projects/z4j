package lol.pbu.z4j.model;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
public class TicketCustomFieldBooleanSerde implements Serde<TicketCustomFieldBoolean> {
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_VALUE = "value";

    @Override
    public TicketCustomFieldBoolean deserialize(Decoder decoder, DecoderContext context, Argument<? super TicketCustomFieldBoolean> type) throws IOException {
        Decoder objectDecoder = decoder.decodeObject();
        TicketCustomFieldBoolean result = new TicketCustomFieldBoolean();
        String key;
        while ((key = objectDecoder.decodeKey()) != null) {
            switch (key) {
                case JSON_PROPERTY_ID:
                    if (!objectDecoder.decodeNull()) {
                        result.setId(objectDecoder.decodeLong());
                    }
                    break;
                case JSON_PROPERTY_VALUE:
                    if (!objectDecoder.decodeNull()) {
                        result.setValue(objectDecoder.decodeBoolean());
                    }
                    break;
                default:
                    objectDecoder.skipValue();
            }
        }
        objectDecoder.finishStructure();
        return result;
    }

    @Override
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends TicketCustomFieldBoolean> type, TicketCustomFieldBoolean value) throws IOException {
        Encoder objectEncoder = encoder.encodeObject(type);
        if (value.getId() != null) {
            objectEncoder.encodeKey(JSON_PROPERTY_ID);
            objectEncoder.encodeLong(value.getId());
        }
        if (value.getValue() != null) {
            objectEncoder.encodeKey(JSON_PROPERTY_VALUE);
            objectEncoder.encodeBoolean(value.getValue());
        }
        objectEncoder.finishStructure();
    }
}
