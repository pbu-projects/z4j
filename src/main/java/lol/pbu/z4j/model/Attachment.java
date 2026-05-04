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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * A file represented as an <a href=\"developer.zendesk.com/api-reference/ticketing/tickets/ticket-attachments/\">Attachment</a> object
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({
        Attachment.JSON_PROPERTY_CONTENT_TYPE,
        Attachment.JSON_PROPERTY_CONTENT_URL,
        Attachment.JSON_PROPERTY_DELETED,
        Attachment.JSON_PROPERTY_FILE_NAME,
        Attachment.JSON_PROPERTY_HEIGHT,
        Attachment.JSON_PROPERTY_ID,
        Attachment.JSON_PROPERTY_INLINE,
        Attachment.JSON_PROPERTY_MALWARE_ACCESS_OVERRIDE,
        Attachment.JSON_PROPERTY_MALWARE_SCAN_RESULT,
        Attachment.JSON_PROPERTY_MAPPED_CONTENT_URL,
        Attachment.JSON_PROPERTY_SIZE,
        Attachment.JSON_PROPERTY_URL,
        Attachment.JSON_PROPERTY_WIDTH,
        Attachment.JSON_PROPERTY_THUMBNAILS,
})
@Serdeable
public class Attachment extends AttachmentBase {

    public static final String JSON_PROPERTY_THUMBNAILS = "thumbnails";

    /**
     * An array of attachment objects. Note that photo thumbnails do not have thumbnails
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_THUMBNAILS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid AttachmentBase> thumbnails;

}
