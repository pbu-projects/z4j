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

/**
 * AttachmentBase
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
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
})
@Serdeable
public class AttachmentBase {

    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_CONTENT_URL = "content_url";
    public static final String JSON_PROPERTY_DELETED = "deleted";
    public static final String JSON_PROPERTY_FILE_NAME = "file_name";
    public static final String JSON_PROPERTY_HEIGHT = "height";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_INLINE = "inline";
    public static final String JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE = "malware_access_override";
    public static final String JSON_PROPERTY_MALWARE_SCAN_RESULT = "malware_scan_result";
    public static final String JSON_PROPERTY_MAPPED_CONTENT_URL = "mapped_content_url";
    public static final String JSON_PROPERTY_SIZE = "size";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_WIDTH = "width";

    /**
     * The content type of the image. Example value: \"image/png\"
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentType;

    /**
     * A full URL where the attachment image file can be downloaded. The file may be hosted externally so take care not to inadvertently send Zendesk authentication credentials. See <a href=\"/documentation/ticketing/managing-tickets/working-with-url-properties\">Working with url properties</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentUrl;

    /**
     * If true, the attachment has been deleted
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DELETED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean deleted;

    /**
     * The name of the image file
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FILE_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String fileName;

    /**
     * The height of the image file in pixels. If height is unknown, returns null
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HEIGHT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String height;

    /**
     * Automatically assigned when created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer id;

    /**
     * If true, the attachment is excluded from the attachment list and the attachment's URL can be referenced within the comment of a ticket. Default is false
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_INLINE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean inline;

    /**
     * If true, you can download an attachment flagged as malware. If false, you can't download such an attachment.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean malwareAccessOverride;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MALWARE_SCAN_RESULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private AttachmentBaseMalwareScanResult malwareScanResult;

    /**
     * The URL the attachment image file has been mapped to
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MAPPED_CONTENT_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String mappedContentUrl;

    /**
     * The size of the image file in bytes
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SIZE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer size;

    /**
     * A URL to access the attachment details
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * The width of the image file in pixels. If width is unknown, returns null
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_WIDTH)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String width;

}
