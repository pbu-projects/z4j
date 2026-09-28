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
 * ArticleAttachment
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
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
        ArticleAttachment.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
@Generated
public class ArticleAttachment {

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

    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    @Nullable
    @JsonProperty(JSON_PROPERTY_ARTICLE_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long articleId;

    @Nullable
    @JsonProperty(JSON_PROPERTY_FILE_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String fileName;

    @Nullable
    @JsonProperty(JSON_PROPERTY_DISPLAY_FILE_NAME)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String displayFileName;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentUrl;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentType;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SIZE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long size;

    @Nullable
    @JsonProperty(JSON_PROPERTY_INLINE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean inline;

    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

}
