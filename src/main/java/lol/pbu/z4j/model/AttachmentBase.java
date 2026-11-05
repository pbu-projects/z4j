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
import io.micronaut.serde.annotation.Serdeable;

/**
 * Base interface for file attachment models across Zendesk resources.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable.Serializable
@Serdeable.Deserializable(as = Attachment.class)
public interface AttachmentBase {

    String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    String JSON_PROPERTY_CONTENT_URL = "content_url";
    String JSON_PROPERTY_DELETED = "deleted";
    String JSON_PROPERTY_FILE_NAME = "file_name";
    String JSON_PROPERTY_HEIGHT = "height";
    String JSON_PROPERTY_ID = "id";
    String JSON_PROPERTY_INLINE = "inline";
    String JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE = "malware_access_override";
    String JSON_PROPERTY_MALWARE_SCAN_RESULT = "malware_scan_result";
    String JSON_PROPERTY_MAPPED_CONTENT_URL = "mapped_content_url";
    String JSON_PROPERTY_SIZE = "size";
    String JSON_PROPERTY_URL = "url";
    String JSON_PROPERTY_WIDTH = "width";

    @Nullable
    String contentType();

    @Nullable
    String contentUrl();

    @Nullable
    Boolean deleted();

    @Nullable
    String fileName();

    @Nullable
    String height();

    @Nullable
    Long id();

    @Nullable
    Boolean inline();

    @Nullable
    Boolean malwareAccessOverride();

    @Nullable
    AttachmentBaseMalwareScanResult malwareScanResult();

    @Nullable
    String mappedContentUrl();

    @Nullable
    Integer size();

    @Nullable
    String url();

    @Nullable
    String width();
}
