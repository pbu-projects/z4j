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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;

@Serdeable
@RecordBuilder
public record GuideMediaCreateRequest(
        @Nullable @Valid @JsonProperty(JSON_PROPERTY_GUIDE_MEDIA) @JsonInclude(JsonInclude.Include.NON_NULL) GuideMedia guideMedia,
        @Nullable @JsonProperty(JSON_PROPERTY_ASSET_UPLOAD_ID) @JsonInclude(JsonInclude.Include.NON_NULL) String assetUploadId,
        @Nullable @JsonProperty(JSON_PROPERTY_FILENAME) @JsonInclude(JsonInclude.Include.NON_NULL) String filename
) implements GuideMediaCreateRequestBuilder.With {

    public static final String JSON_PROPERTY_GUIDE_MEDIA = "guide_media";
    public static final String JSON_PROPERTY_ASSET_UPLOAD_ID = "asset_upload_id";
    public static final String JSON_PROPERTY_FILENAME = "filename";

    public GuideMediaCreateRequest(GuideMedia guideMedia) {
        this(
                guideMedia,
                null,
                guideMedia != null ? guideMedia.name() : null
        );
    }

    public GuideMediaCreateRequest(String filename) {
        this(new GuideMedia(filename), null, filename);
    }

    public GuideMediaCreateRequest(String assetUploadId, String filename) {
        this(new GuideMedia(filename), assetUploadId, filename);
    }
}
