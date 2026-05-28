package lol.pbu.z4j.model;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
public class TicketCustomFieldFloatSerde implements Serde<TicketCustomFieldFloat> {
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_VALUE = "value";

    @Override
    public TicketCustomFieldFloat deserialize(Decoder decoder, DecoderContext context, Argument<? super TicketCustomFieldFloat> type) throws IOException {
        Decoder objectDecoder = decoder.decodeObject();
        TicketCustomFieldFloat result = new TicketCustomFieldFloat();
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
                        result.setValue(objectDecoder.decodeFloat());
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
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends TicketCustomFieldFloat> type, TicketCustomFieldFloat value) throws IOException {
        Encoder objectEncoder = encoder.encodeObject(type);
        if (value.getId() != null) {
            objectEncoder.encodeKey(JSON_PROPERTY_ID);
            objectEncoder.encodeLong(value.getId());
        }
        if (value.getValue() != null) {
            objectEncoder.encodeKey(JSON_PROPERTY_VALUE);
            objectEncoder.encodeFloat(value.getValue());
        }
        objectEncoder.finishStructure();
    }
}
