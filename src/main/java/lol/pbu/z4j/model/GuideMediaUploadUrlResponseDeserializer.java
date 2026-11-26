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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import lol.pbu.z4j.Generated;

/**
 * Custom deserializer for GuideMediaUploadUrlResponse supporting both nested upload_url object and flat representations.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Singleton
@Generated
public class GuideMediaUploadUrlResponseDeserializer implements Deserializer<GuideMediaUploadUrlResponse> {

    private static final String KEY_ASSET_UPLOAD_ID = "asset_upload_id";
    private static final String KEY_HEADERS = "headers";
    private static final String KEY_UPLOAD_URL = "upload_url";
    private static final String KEY_URL = "url";

    @Serdeable
    record UploadUrlPayload(
            @Nullable @JsonProperty(KEY_UPLOAD_URL) String uploadUrl,
            @Nullable @JsonProperty(KEY_ASSET_UPLOAD_ID) String assetUploadId,
            @Nullable @JsonProperty(KEY_HEADERS) String headers
    ) {
    }

    @Nullable
    @Override
    public GuideMediaUploadUrlResponse deserialize(
            @NonNull Decoder decoder,
            @NonNull DecoderContext context,
            @NonNull Argument<? super GuideMediaUploadUrlResponse> type
    ) throws IOException {
        JsonNode node = PolymorphicSerdeSupport.decodeObjectNode(decoder, "GuideMediaUploadUrlResponse");
        if (node == null) {
            return null;
        }

        Map<String, JsonNode> normalized = new LinkedHashMap<>();
        copyIfPresent(node, KEY_URL, normalized, KEY_UPLOAD_URL);
        copyIfPresent(node, KEY_ASSET_UPLOAD_ID, normalized, KEY_ASSET_UPLOAD_ID);
        copyHeaders(node.get(KEY_HEADERS), normalized);

        JsonNode uploadUrlNode = node.get(KEY_UPLOAD_URL);
        if (uploadUrlNode != null) {
            if (uploadUrlNode.isObject()) {
                copyIfNonNull(uploadUrlNode, KEY_URL, normalized, KEY_UPLOAD_URL);
                copyIfNonNull(uploadUrlNode, KEY_ASSET_UPLOAD_ID, normalized, KEY_ASSET_UPLOAD_ID);
                JsonNode nestedHeaders = uploadUrlNode.get(KEY_HEADERS);
                if (nestedHeaders != null && !nestedHeaders.isNull()) {
                    copyHeaders(nestedHeaders, normalized);
                }
            } else if (!uploadUrlNode.isNull() || !normalized.containsKey(KEY_UPLOAD_URL)) {
                normalized.put(KEY_UPLOAD_URL, uploadUrlNode);
            }
        }

        UploadUrlPayload payload = PolymorphicSerdeSupport.deserializeTarget(
                JsonNode.createObjectNode(normalized),
                context,
                UploadUrlPayload.class
        );
        if (payload == null) {
            return null;
        }
        return new GuideMediaUploadUrlResponse(payload.uploadUrl(), payload.assetUploadId(), payload.headers());
    }

    private static void copyIfPresent(JsonNode source, String sourceKey, Map<String, JsonNode> target, String targetKey) {
        JsonNode value = source.get(sourceKey);
        if (value != null) {
            target.put(targetKey, value);
        }
    }

    private static void copyIfNonNull(JsonNode source, String sourceKey, Map<String, JsonNode> target, String targetKey) {
        JsonNode value = source.get(sourceKey);
        if (value != null && !value.isNull()) {
            target.put(targetKey, value);
        }
    }

    private static void copyHeaders(@Nullable JsonNode headersNode, Map<String, JsonNode> target) {
        if (headersNode == null) {
            return;
        }
        if (headersNode.isNull() || headersNode.isString()) {
            target.put(KEY_HEADERS, headersNode);
            return;
        }
        target.put(KEY_HEADERS, JsonNode.createStringNode(JsonNodeTreeDecoder.toJson(headersNode)));
    }

    @Nullable
    @Override
    public GuideMediaUploadUrlResponse getDefaultValue(@NonNull DecoderContext context, @NonNull Argument<? super GuideMediaUploadUrlResponse> type) {
        return null;
    }
}
