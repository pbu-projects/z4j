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
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Comment made by an author on an article or item.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        CommentBase.JSON_PROPERTY_BODY,
        CommentBase.JSON_PROPERTY_LOCALE,
        CommentBase.JSON_PROPERTY_AUTHOR_ID,
        CommentBase.JSON_PROPERTY_CREATED_AT,
        CommentBase.JSON_PROPERTY_HTML_URL,
        CommentBase.JSON_PROPERTY_ID,
        CommentBase.JSON_PROPERTY_NON_AUTHOR_EDITOR_ID,
        CommentBase.JSON_PROPERTY_NON_AUTHOR_UPDATED_AT,
        Comment.JSON_PROPERTY_SOURCE_ID,
        Comment.JSON_PROPERTY_SOURCE_TYPE,
        CommentBase.JSON_PROPERTY_UPDATED_AT,
        CommentBase.JSON_PROPERTY_URL,
        CommentBase.JSON_PROPERTY_VOTE_COUNT,
        CommentBase.JSON_PROPERTY_VOTE_SUM
})
public record Comment(
        @Nullable @NotNull @JsonProperty(JSON_PROPERTY_BODY) String body,
        @Nullable @JsonProperty(JSON_PROPERTY_LOCALE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) LocaleAbbreviation localeAbbreviation,
        @Nullable @JsonProperty(JSON_PROPERTY_AUTHOR_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long authorId,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_HTML_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String htmlUrl,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_NON_AUTHOR_EDITOR_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long nonAuthorEditorId,
        @Nullable @JsonProperty(JSON_PROPERTY_NON_AUTHOR_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime nonAuthorUpdatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_SOURCE_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long sourceId,
        @Nullable @JsonProperty(JSON_PROPERTY_SOURCE_TYPE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String sourceType,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_VOTE_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long voteCount,
        @Nullable @JsonProperty(JSON_PROPERTY_VOTE_SUM) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long voteSum
) implements CommentBase, CommentBuilder.With {

    public static final String JSON_PROPERTY_SOURCE_ID = "source_id";
    public static final String JSON_PROPERTY_SOURCE_TYPE = "source_type";

    public Comment() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Comment(String body) {
        this(body, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Comment(String body, LocaleAbbreviation localeAbbreviation) {
        this(body, localeAbbreviation, null, null, null, null, null, null, null, null, null, null, null, null);
    }
}
