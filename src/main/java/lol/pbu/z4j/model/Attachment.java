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
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * File attachment record linked to a comment or ticket.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        AttachmentBase.JSON_PROPERTY_CONTENT_TYPE,
        AttachmentBase.JSON_PROPERTY_CONTENT_URL,
        AttachmentBase.JSON_PROPERTY_DELETED,
        AttachmentBase.JSON_PROPERTY_FILE_NAME,
        AttachmentBase.JSON_PROPERTY_HEIGHT,
        AttachmentBase.JSON_PROPERTY_ID,
        AttachmentBase.JSON_PROPERTY_INLINE,
        AttachmentBase.JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE,
        AttachmentBase.JSON_PROPERTY_MALWARE_SCAN_RESULT,
        AttachmentBase.JSON_PROPERTY_MAPPED_CONTENT_URL,
        AttachmentBase.JSON_PROPERTY_SIZE,
        AttachmentBase.JSON_PROPERTY_URL,
        AttachmentBase.JSON_PROPERTY_WIDTH,
        Attachment.JSON_PROPERTY_THUMBNAILS
})
public record Attachment(
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_CONTENT_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String contentType,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_CONTENT_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String contentUrl,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_DELETED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean deleted,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_FILE_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String fileName,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_HEIGHT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String height,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_INLINE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean inline,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean malwareAccessOverride,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_MALWARE_SCAN_RESULT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) AttachmentBaseMalwareScanResult malwareScanResult,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_MAPPED_CONTENT_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String mappedContentUrl,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_SIZE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Integer size,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(AttachmentBase.JSON_PROPERTY_WIDTH) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String width,
        @Nullable @Valid @JsonProperty(Attachment.JSON_PROPERTY_THUMBNAILS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@Valid AttachmentBase> thumbnails
) implements AttachmentBase, AttachmentBuilder.With {

    public static final String JSON_PROPERTY_THUMBNAILS = "thumbnails";

    public Attachment() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Attachment addThumbnailsItem(AttachmentBase thumbnailsItem) {
        List<AttachmentBase> newThumbnails = thumbnails == null ? new ArrayList<>() : new ArrayList<>(thumbnails);
        newThumbnails.add(thumbnailsItem);
        return withThumbnails(newThumbnails);
    }
}
