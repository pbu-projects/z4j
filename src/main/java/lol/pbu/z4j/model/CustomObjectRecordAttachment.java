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
import java.time.ZonedDateTime;
import lol.pbu.z4j.model.CustomObjectRecordAttachmentMalwareScanStatus;
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
 * <p>A file attachment associated with a custom object record</p>
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
    CustomObjectRecordAttachment.JSON_PROPERTY_CONTENT_TYPE,
    CustomObjectRecordAttachment.JSON_PROPERTY_CONTENT_URL,
    CustomObjectRecordAttachment.JSON_PROPERTY_CREATED_AT,
    CustomObjectRecordAttachment.JSON_PROPERTY_CREATED_BY,
    CustomObjectRecordAttachment.JSON_PROPERTY_CUSTOM_OBJECT_RECORD_ID,
    CustomObjectRecordAttachment.JSON_PROPERTY_FILENAME,
    CustomObjectRecordAttachment.JSON_PROPERTY_ID,
    CustomObjectRecordAttachment.JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE,
    CustomObjectRecordAttachment.JSON_PROPERTY_MALWARE_SCAN_COMPLETED_AT,
    CustomObjectRecordAttachment.JSON_PROPERTY_MALWARE_SCAN_STATUS,
    CustomObjectRecordAttachment.JSON_PROPERTY_SIZE,
})
@Serdeable
public class CustomObjectRecordAttachment {

    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_CONTENT_URL = "content_url";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_CREATED_BY = "created_by";
    public static final String JSON_PROPERTY_CUSTOM_OBJECT_RECORD_ID = "custom_object_record_id";
    public static final String JSON_PROPERTY_FILENAME = "filename";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE = "malware_access_override";
    public static final String JSON_PROPERTY_MALWARE_SCAN_COMPLETED_AT = "malware_scan_completed_at";
    public static final String JSON_PROPERTY_MALWARE_SCAN_STATUS = "malware_scan_status";
    public static final String JSON_PROPERTY_SIZE = "size";

    /**
     * <p>The content type of the file</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentType;

    /**
     * <p>A full URL where the attachment file can be downloaded. The file may be hosted externally so take care not to inadvertently send Zendesk authentication credentials.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentUrl;

    /**
     * <p>The date and time when the attachment was created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * <p>The name of the user who created the attachment</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_BY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdBy;

    /**
     * <p>The ID of the custom object record this attachment belongs to</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CUSTOM_OBJECT_RECORD_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String customObjectRecordId;

    /**
     * <p>The name of the attachment file</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FILENAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String filename;

    /**
     * <p>Automatically assigned when created</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String id;

    /**
     * <p>If true, you can download an attachment flagged as malware. If false, you can't download such an attachment.</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean malwareAccessOverride;

    /**
     * <p>The date and time when the malware scan was completed</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_MALWARE_SCAN_COMPLETED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime malwareScanCompletedAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MALWARE_SCAN_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private CustomObjectRecordAttachmentMalwareScanStatus malwareScanStatus;

    /**
     * <p>The size of the file in bytes</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SIZE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Integer size;

}