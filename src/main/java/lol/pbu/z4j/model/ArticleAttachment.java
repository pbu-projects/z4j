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

/**
 * Article attachment uploaded to Zendesk Guide.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        ArticleAttachment.JSON_PROPERTY_ID,
        ArticleAttachment.JSON_PROPERTY_URL,
        ArticleAttachment.JSON_PROPERTY_ARTICLE_ID,
        ArticleAttachment.JSON_PROPERTY_FILE_NAME,
        ArticleAttachment.JSON_PROPERTY_DISPLAY_FILE_NAME,
        ArticleAttachment.JSON_PROPERTY_CONTENT_URL,
        ArticleAttachment.JSON_PROPERTY_CONTENT_TYPE,
        ArticleAttachment.JSON_PROPERTY_SIZE,
        ArticleAttachment.JSON_PROPERTY_INLINE,
        ArticleAttachment.JSON_PROPERTY_CREATED_AT,
        ArticleAttachment.JSON_PROPERTY_UPDATED_AT
})
public record ArticleAttachment(
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_ARTICLE_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long articleId,
        @Nullable @JsonProperty(JSON_PROPERTY_FILE_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String fileName,
        @Nullable @JsonProperty(JSON_PROPERTY_DISPLAY_FILE_NAME) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String displayFileName,
        @Nullable @JsonProperty(JSON_PROPERTY_CONTENT_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String contentUrl,
        @Nullable @JsonProperty(JSON_PROPERTY_CONTENT_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String contentType,
        @Nullable @JsonProperty(JSON_PROPERTY_SIZE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long size,
        @Nullable @JsonProperty(JSON_PROPERTY_INLINE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean inline,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedAt
) implements ArticleAttachmentBuilder.With {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_ARTICLE_ID = "article_id";
    public static final String JSON_PROPERTY_FILE_NAME = "file_name";
    public static final String JSON_PROPERTY_DISPLAY_FILE_NAME = "display_file_name";
    public static final String JSON_PROPERTY_CONTENT_URL = "content_url";
    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_SIZE = "size";
    public static final String JSON_PROPERTY_INLINE = "inline";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    public ArticleAttachment() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }
}
