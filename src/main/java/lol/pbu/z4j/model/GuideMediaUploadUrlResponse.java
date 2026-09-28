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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lol.pbu.z4j.Generated;

/**
 * GuideMediaUploadUrlResponse
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        GuideMediaUploadUrlResponse.JSON_PROPERTY_UPLOAD_URL,
        GuideMediaUploadUrlResponse.JSON_PROPERTY_ASSET_UPLOAD_ID,
        GuideMediaUploadUrlResponse.JSON_PROPERTY_HEADERS
})
@Serdeable
@Generated
public class GuideMediaUploadUrlResponse {

    public static final String JSON_PROPERTY_UPLOAD_URL = "upload_url";
    public static final String JSON_PROPERTY_ASSET_UPLOAD_ID = "asset_upload_id";
    public static final String JSON_PROPERTY_HEADERS = "headers";

    @Nullable
    @JsonProperty(JSON_PROPERTY_UPLOAD_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String uploadUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ASSET_UPLOAD_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String assetUploadId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_HEADERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String headers;

    public GuideMediaUploadUrlResponse(String uploadUrl, String assetUploadId) {
        this.uploadUrl = uploadUrl;
        this.assetUploadId = assetUploadId;
    }
}
