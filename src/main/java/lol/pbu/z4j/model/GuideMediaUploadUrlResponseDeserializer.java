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
import lol.pbu.z4j.Generated;

import java.io.IOException;
import java.util.Map;

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
    private static final String KEY_URL = "url";

    @Override
    public GuideMediaUploadUrlResponse deserialize(@NonNull Decoder decoder, @NonNull DecoderContext context, @NonNull Argument<? super GuideMediaUploadUrlResponse> type) throws IOException {
        String uploadUrl = null;
        String assetUploadId = null;
        String headers = null;

        try (Decoder objectDecoder = decoder.decodeObject()) {
            String key;
            while ((key = objectDecoder.decodeKey()) != null) {
                switch (key) {
                    case KEY_ASSET_UPLOAD_ID -> assetUploadId = objectDecoder.decodeString();
                    case KEY_HEADERS -> headers = objectDecoder.decodeString();
                    case KEY_URL -> uploadUrl = objectDecoder.decodeString();
                    case "upload_url" -> {
                        Object val = objectDecoder.decodeArbitrary();
                        if (val instanceof String s) {
                            uploadUrl = s;
                        } else if (val instanceof Map<?, ?> map) {
                            uploadUrl = getMapString(map, KEY_URL, uploadUrl);
                            assetUploadId = getMapString(map, KEY_ASSET_UPLOAD_ID, assetUploadId);
                            headers = getMapString(map, KEY_HEADERS, headers);
                        }
                    }
                    default -> objectDecoder.skipValue();
                }
            }
        }

        return new GuideMediaUploadUrlResponse(uploadUrl, assetUploadId, headers);
    }

    private String getMapString(Map<?, ?> map, String key, String defaultValue) {
        Object val = map.get(key);
        return val != null ? val.toString() : defaultValue;
    }

    @Nullable
    @Override
    public GuideMediaUploadUrlResponse getDefaultValue(@NonNull DecoderContext context, @NonNull Argument<? super GuideMediaUploadUrlResponse> type) {
        return null;
    }
}
