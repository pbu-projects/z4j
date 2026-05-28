package lol.pbu.z4j.model;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serde;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TicketCustomFieldStringArraySerde implements Serde<TicketCustomFieldStringArray> {
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_VALUE = "value";

    @Override
    public TicketCustomFieldStringArray deserialize(Decoder decoder, DecoderContext context, Argument<? super TicketCustomFieldStringArray> type) throws IOException {
        Decoder objectDecoder = decoder.decodeObject();
        TicketCustomFieldStringArray result = new TicketCustomFieldStringArray();
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
                        Decoder arrayDecoder = objectDecoder.decodeArray();
                        List<String> values = new ArrayList<>();
                        while (arrayDecoder.hasNextArrayValue()) {
                            values.add(arrayDecoder.decodeString());
                        }
                        arrayDecoder.finishStructure();
                        result.setValue(values.toArray(new String[0]));
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
    public void serialize(Encoder encoder, EncoderContext context, Argument<? extends TicketCustomFieldStringArray> type, TicketCustomFieldStringArray value) throws IOException {
        Encoder objectEncoder = encoder.encodeObject(type);
        if (value.getId() != null) {
            objectEncoder.encodeKey(JSON_PROPERTY_ID);
            objectEncoder.encodeLong(value.getId());
        }
        if (value.getValue() != null) {
            objectEncoder.encodeKey(JSON_PROPERTY_VALUE);
            Encoder arrayEncoder = objectEncoder.encodeArray(Argument.of(String[].class));
            for (String s : value.getValue()) {
                arrayEncoder.encodeString(s);
            }
            arrayEncoder.finishStructure();
        }
        objectEncoder.finishStructure();
    }
}
