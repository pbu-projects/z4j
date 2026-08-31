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

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import io.micronaut.core.annotation.Nullable;

/**
 * AttachmentBaseObject
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    AttachmentBaseObject.JSON_PROPERTY_CONTENT_TYPE,
    AttachmentBaseObject.JSON_PROPERTY_CONTENT_URL,
    AttachmentBaseObject.JSON_PROPERTY_DELETED,
    AttachmentBaseObject.JSON_PROPERTY_FILE_NAME,
    AttachmentBaseObject.JSON_PROPERTY_HEIGHT,
    AttachmentBaseObject.JSON_PROPERTY_ID,
    AttachmentBaseObject.JSON_PROPERTY_INLINE,
    AttachmentBaseObject.JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE,
    AttachmentBaseObject.JSON_PROPERTY_MALWARE_SCAN_RESULT,
    AttachmentBaseObject.JSON_PROPERTY_MAPPED_CONTENT_URL,
    AttachmentBaseObject.JSON_PROPERTY_SIZE,
    AttachmentBaseObject.JSON_PROPERTY_URL,
    AttachmentBaseObject.JSON_PROPERTY_WIDTH,
})
@Serdeable
public class AttachmentBaseObject {

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
     * <p>The content type of the image. Example value: \"image/png\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentType;

    /**
     * <p>A full URL where the attachment image file can be downloaded. The file may be hosted externally so take care not to inadvertently send Zendesk authentication credentials. See <a href=\"/documentation/api-basics/best-practices/working-with-url-properties/\">Working with url properties</a></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentUrl;

    /**
     * <p>If true, the attachment has been deleted</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DELETED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean deleted;

    /**
     * <p>The name of the image file</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FILE_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String fileName;

    /**
     * <p>The height of the image file in pixels. If height is unknown, returns null</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HEIGHT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long height;

    /**
     * <p>Automatically assigned when created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * <p>If true, the attachment is excluded from the attachment list and the attachment's URL can be referenced within the comment of a ticket. Default is false</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_INLINE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean inline;

    /**
     * <p>If true, you can download an attachment flagged as malware. If false, you can't download such an attachment.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean malwareAccessOverride;

    /**
     * <p>The result of the malware scan. There is a delay between the time the attachment is uploaded and when the malware scan is completed. Usually the scan is done within a few seconds, but high load conditions can delay the scan results. Possible values: \"malware_found\", \"malware_not_found\", \"failed_to_scan\", \"not_scanned\"</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MALWARE_SCAN_RESULT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String malwareScanResult;

    /**
     * <p>The URL the attachment image file has been mapped to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MAPPED_CONTENT_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String mappedContentUrl;

    /**
     * <p>The size of the image file in bytes</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SIZE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long size;

    /**
     * <p>A URL to access the attachment details</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * <p>The width of the image file in pixels. If width is unknown, returns null</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_WIDTH)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long width;

}