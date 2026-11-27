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
import io.micronaut.serde.Keys;
import io.micronaut.serde.KeysAwareDecoder;
import io.micronaut.serde.exceptions.InvalidFormatException;
import io.micronaut.serde.exceptions.NullValueSerdeException;
import io.micronaut.serde.exceptions.SerdeException;
import io.micronaut.serde.util.BinaryCodecUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Replays a buffered {@link JsonNode} tree through the public {@link Decoder} contract.
 */
abstract class JsonNodeTreeDecoder implements Decoder {

    private static final String TYPE_NUMBER = "NUMBER";
    private static final String ERR_NOT_A_NUMBER = "Not a number";

    @FunctionalInterface
    private interface ScalarReader<T> {
        T read(Decoder decoder) throws IOException;
    }

    static Decoder of(@Nullable JsonNode node) {
        return new RootDecoder(node == null ? JsonNode.nullNode() : node);
    }

    protected abstract JsonNode peekValue();

    @Override
    public Decoder decodeArray(Argument<?> type) throws IOException {
        JsonNode peeked = peekValue();
        if (!peeked.isArray()) {
            throw createDeserializationException("Not an array", toArbitrary(peeked));
        }
        skipValue();
        return new ArrayDecoder(peeked);
    }

    @Override
    public Decoder decodeObject(Argument<?> type) throws IOException {
        JsonNode peeked = peekValue();
        if (!peeked.isObject()) {
            throw createDeserializationException("Not an object", toArbitrary(peeked));
        }
        skipValue();
        return new ObjectDecoder(peeked);
    }

    @Override
    public String decodeString() throws IOException {
        return decodeScalar(JsonNode::isString, "STRING", "Not a string", Argument.STRING, JsonNode::getStringValue, Decoder::decodeString);
    }

    @Override
    public boolean decodeBoolean() throws IOException {
        return decodeScalar(JsonNode::isBoolean, "BOOLEAN", "Not a boolean", Argument.BOOLEAN, JsonNode::getBooleanValue, Decoder::decodeBoolean);
    }

    @Override
    public byte decodeByte() throws IOException {
        return decodeScalar(JsonNode::isNumber, TYPE_NUMBER, ERR_NOT_A_NUMBER, Argument.BYTE, node -> (byte) node.getIntValue(), Decoder::decodeByte);
    }

    @Override
    public short decodeShort() throws IOException {
        return decodeScalar(JsonNode::isNumber, TYPE_NUMBER, ERR_NOT_A_NUMBER, Argument.SHORT, node -> (short) node.getIntValue(), Decoder::decodeShort);
    }

    @Override
    public char decodeChar() throws IOException {
        return decodeScalar(JsonNode::isNumber, TYPE_NUMBER, ERR_NOT_A_NUMBER, Argument.CHAR, node -> (char) node.getIntValue(), Decoder::decodeChar);
    }

    @Override
    public int decodeInt() throws IOException {
        return decodeScalar(JsonNode::isNumber, TYPE_NUMBER, ERR_NOT_A_NUMBER, Argument.INT, JsonNode::getIntValue, Decoder::decodeInt);
    }

    @Override
    public long decodeLong() throws IOException {
        return decodeScalar(JsonNode::isNumber, TYPE_NUMBER, ERR_NOT_A_NUMBER, Argument.LONG, JsonNode::getLongValue, Decoder::decodeLong);
    }

    @Override
    public float decodeFloat() throws IOException {
        return decodeScalar(JsonNode::isNumber, TYPE_NUMBER, ERR_NOT_A_NUMBER, Argument.FLOAT, JsonNode::getFloatValue, Decoder::decodeFloat);
    }

    @Override
    public double decodeDouble() throws IOException {
        return decodeScalar(JsonNode::isNumber, TYPE_NUMBER, ERR_NOT_A_NUMBER, Argument.DOUBLE, JsonNode::getDoubleValue, Decoder::decodeDouble);
    }

    @Override
    public BigInteger decodeBigInteger() throws IOException {
        return decodeScalar(JsonNode::isNumber, TYPE_NUMBER, ERR_NOT_A_NUMBER, Argument.of(BigInteger.class), JsonNode::getBigIntegerValue, Decoder::decodeBigInteger);
    }

    @Override
    public BigDecimal decodeBigDecimal() throws IOException {
        return decodeScalar(JsonNode::isNumber, TYPE_NUMBER, ERR_NOT_A_NUMBER, Argument.of(BigDecimal.class), JsonNode::getBigDecimalValue, Decoder::decodeBigDecimal);
    }

    @Override
    public Number decodeNumber() throws IOException {
        return decodeScalar(JsonNode::isNumber, TYPE_NUMBER, ERR_NOT_A_NUMBER, Argument.of(Number.class), JsonNode::getNumberValue, Decoder::decodeNumber);
    }

    @Override
    public byte[] decodeBinary() throws IOException {
        if (peekValue().isString()) {
            return BinaryCodecUtil.decodeFromBase64String(this);
        }
        return BinaryCodecUtil.decodeFromArray(this);
    }

    @Override
    public boolean decodeNull() throws IOException {
        if (peekValue().isNull()) {
            skipValue();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Object decodeArbitrary() throws IOException {
        return toArbitrary(decodeNode());
    }

    @Override
    public JsonNode decodeNode() throws IOException {
        JsonNode node = peekValue();
        skipValue();
        return node;
    }

    @Override
    public Decoder decodeBuffer() throws IOException {
        return of(decodeNode());
    }

    @Override
    public IOException createDeserializationException(String message, @Nullable Object invalidValue) {
        if (invalidValue != null) {
            return new InvalidFormatException(message, null, invalidValue);
        }
        return new SerdeException(message);
    }

    private <T> T decodeScalar(
            Predicate<JsonNode> typeCheck,
            String expectedToken,
            String errorMessage,
            Argument<?> arrayType,
            Function<JsonNode, T> nodeExtractor,
            ScalarReader<T> arrayReader
    ) throws IOException {
        JsonNode peeked = peekValue();
        if (typeCheck.test(peeked)) {
            skipValue();
            return nodeExtractor.apply(peeked);
        }
        if (peeked.isNull()) {
            throw NullValueSerdeException.unexpectedToken(expectedToken, "NULL");
        }
        if (peeked.isArray()) {
            try (Decoder arrayDecoder = decodeArray(arrayType)) {
                if (!arrayDecoder.hasNextArrayValue()) {
                    arrayDecoder.finishStructure(true);
                    throw createDeserializationException("Cannot deserialize scalar from empty array", null);
                }
                T unwrapped;
                try {
                    unwrapped = arrayReader.read(arrayDecoder);
                } catch (IOException | RuntimeException e) {
                    arrayDecoder.finishStructure(true);
                    throw e;
                }
                if (arrayDecoder.hasNextArrayValue()) {
                    arrayDecoder.finishStructure(true);
                    throw createDeserializationException("Expected one " + expectedToken.toLowerCase() + ", but got array of multiple values", null);
                }
                return unwrapped;
            }
        }
        throw createDeserializationException(errorMessage, toArbitrary(peeked));
    }

    @Nullable
    static Object toArbitrary(@Nullable JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        if (node.isNumber()) {
            return node.getNumberValue();
        }
        if (node.isBoolean()) {
            return node.getBooleanValue();
        }
        if (node.isString()) {
            return node.getStringValue();
        }
        if (node.isArray()) {
            return StreamSupport.stream(node.values().spliterator(), false)
                    .map(JsonNodeTreeDecoder::toArbitrary)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        if (node.isObject()) {
            return StreamSupport.stream(node.entries().spliterator(), false)
                    .collect(
                            LinkedHashMap<String, Object>::new,
                            (map, entry) -> map.put(entry.getKey(), toArbitrary(entry.getValue())),
                            Map::putAll
                    );
        }
        return node.coerceStringValue();
    }

    static String toJson(@Nullable JsonNode node) {
        StringBuilder sb = new StringBuilder();
        appendJson(sb, node);
        return sb.toString();
    }

    private static void appendJson(StringBuilder sb, @Nullable JsonNode node) {
        if (node == null || node.isNull()) {
            sb.append("null");
        } else if (node.isBoolean()) {
            sb.append(node.getBooleanValue());
        } else if (node.isNumber()) {
            sb.append(node.getNumberValue());
        } else if (node.isString()) {
            appendJsonString(sb, node.getStringValue());
        } else if (node.isArray()) {
            sb.append(StreamSupport.stream(node.values().spliterator(), false)
                    .map(JsonNodeTreeDecoder::toJson)
                    .collect(Collectors.joining(",", "[", "]")));
        } else if (node.isObject()) {
            sb.append(StreamSupport.stream(node.entries().spliterator(), false)
                    .map(entry -> toJsonString(entry.getKey()) + ":" + toJson(entry.getValue()))
                    .collect(Collectors.joining(",", "{", "}")));
        } else {
            appendJsonString(sb, node.coerceStringValue());
        }
    }

    private static String toJsonString(String value) {
        StringBuilder sb = new StringBuilder();
        appendJsonString(sb, value);
        return sb.toString();
    }

    private static void appendJsonString(StringBuilder sb, String value) {
        sb.append('"');
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        sb.append('"');
    }

    private static final class RootDecoder extends JsonNodeTreeDecoder {
        private final JsonNode node;
        private boolean consumed;

        private RootDecoder(JsonNode node) {
            this.node = node;
        }

        @Override
        protected JsonNode peekValue() {
            if (consumed) {
                throw new IllegalStateException("Already drained");
            }
            return node;
        }

        @Override
        public boolean hasNextArrayValue() {
            return false;
        }

        @Override
        public String decodeKey() {
            throw new IllegalStateException("Can't be called on buffered node");
        }

        @Override
        public void skipValue() {
            if (consumed) {
                throw new IllegalStateException("Already drained");
            }
            consumed = true;
        }

        @Override
        public void finishStructure(boolean consumeLeftElements) {
            throw new IllegalStateException("Can't be called on buffered node");
        }
    }

    private static final class ArrayDecoder extends JsonNodeTreeDecoder {
        private final Iterator<JsonNode> iterator;
        @Nullable
        private JsonNode peeked;

        private ArrayDecoder(JsonNode node) {
            this.iterator = node.values().iterator();
            skipValue();
        }

        @Override
        protected JsonNode peekValue() {
            if (peeked == null) {
                throw new IllegalStateException("No more elements");
            }
            return peeked;
        }

        @Override
        public boolean hasNextArrayValue() {
            return peeked != null;
        }

        @Override
        public String decodeKey() {
            throw new IllegalStateException("Arrays have no keys");
        }

        @Override
        public void skipValue() {
            peeked = iterator.hasNext() ? iterator.next() : null;
        }

        @Override
        public void finishStructure(boolean consumeLeftElements) {
            if (!consumeLeftElements && peeked != null) {
                throw new IllegalStateException("Not all elements have been consumed yet");
            }
            if (consumeLeftElements) {
                peeked = null;
            }
        }
    }

    private static final class ObjectDecoder extends JsonNodeTreeDecoder implements KeysAwareDecoder {
        private final Iterator<Map.Entry<String, JsonNode>> iterator;
        @Nullable
        private String nextKey;
        @Nullable
        private JsonNode nextValue;
        private boolean finished;

        private ObjectDecoder(JsonNode node) {
            this.iterator = node.entries().iterator();
        }

        @Override
        protected JsonNode peekValue() {
            if (nextValue == null) {
                throw new IllegalStateException("Field name not parsed yet");
            }
            return nextValue;
        }

        @Override
        public boolean hasNextArrayValue() {
            return false;
        }

        @Nullable
        @Override
        public String decodeKey() {
            if (finished) {
                return null;
            }
            if (nextKey != null) {
                String key = nextKey;
                nextKey = null;
                return key;
            }
            if (nextValue != null) {
                throw new IllegalStateException("Field value not parsed yet");
            }
            if (!iterator.hasNext()) {
                return null;
            }
            Map.Entry<String, JsonNode> next = iterator.next();
            nextValue = next.getValue();
            return next.getKey();
        }

        @Override
        public int decodeKey(Keys keys) {
            if (finished) {
                return KeysAwareDecoder.MATCH_END_OBJECT;
            }
            if (nextKey != null) {
                return KeysAwareDecoder.MATCH_UNKNOWN_NAME;
            }
            if (nextValue != null) {
                throw new IllegalStateException("Field value not parsed yet");
            }
            if (!iterator.hasNext()) {
                return KeysAwareDecoder.MATCH_END_OBJECT;
            }
            Map.Entry<String, JsonNode> next = iterator.next();
            String key = next.getKey();
            nextValue = next.getValue();
            int index = keys.indexOf(key);
            if (index == -1) {
                nextKey = key;
                return KeysAwareDecoder.MATCH_UNKNOWN_NAME;
            }
            return index;
        }

        @Override
        public void skipValue() {
            if (nextValue == null || nextKey != null) {
                throw new IllegalStateException("Field name not parsed yet");
            }
            nextValue = null;
        }

        @Override
        public void finishStructure(boolean consumeLeftElements) {
            if (!consumeLeftElements && !finished && (nextKey != null || nextValue != null || iterator.hasNext())) {
                throw new IllegalStateException("Not all elements have been consumed yet");
            }
            nextKey = null;
            nextValue = null;
            finished = true;
        }
    }
}
