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
 * ArticleAttachment
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        ArticleAttachment.JSON_PROPERTY_ARTICLE_ID,
        ArticleAttachment.JSON_PROPERTY_CONTENT_TYPE,
        ArticleAttachment.JSON_PROPERTY_CONTENT_URL,
        ArticleAttachment.JSON_PROPERTY_CREATED_AT,
        ArticleAttachment.JSON_PROPERTY_FILE,
        ArticleAttachment.JSON_PROPERTY_FILE_NAME,
        ArticleAttachment.JSON_PROPERTY_GUIDE_MEDIA_ID,
        ArticleAttachment.JSON_PROPERTY_ID,
        ArticleAttachment.JSON_PROPERTY_INLINE,
        ArticleAttachment.JSON_PROPERTY_LOCALE,
        ArticleAttachment.JSON_PROPERTY_SIZE,
        ArticleAttachment.JSON_PROPERTY_UPDATED_AT,
        ArticleAttachment.JSON_PROPERTY_URL,
})
@Serdeable
public class ArticleAttachment {

    public static final String JSON_PROPERTY_ARTICLE_ID = "article_id";
    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_CONTENT_URL = "content_url";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_FILE = "file";
    public static final String JSON_PROPERTY_FILE_NAME = "file_name";
    public static final String JSON_PROPERTY_GUIDE_MEDIA_ID = "guide_media_id";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_INLINE = "inline";
    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_SIZE = "size";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";

    /**
     * The associated article, if present
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ARTICLE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long articleId;

    /**
     * The file type. Example: image/png
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentType;

    /**
     * URL where the attachment file can be downloaded
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentUrl;

    /**
     * The time the article attachment was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * File to upload, applicable only during creation.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FILE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Object file;

    /**
     * The file name
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FILE_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String fileName;

    /**
     * Unique identifier for the guide-media to associate with this attachment, applicable only during creation.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GUIDE_MEDIA_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String guideMediaId;

    /**
     * Assigned ID when the article attachment is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * The attached file is shown in the admin interface for inline attachments. Its URL can be referenced in the article's HTML body. Inline attachments are image files directly embedded in the article body. If false, the attachment is listed in the list of attachments. The default value is false
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_INLINE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean inline;

    /**
     * The locale of translation that the attachment will be attached to and can only be set on inline attachments
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String locale;

    /**
     * The attachment file size in bytes
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SIZE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long size;

    /**
     * The time the article attachment was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * The URL of the article attachment
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

}
