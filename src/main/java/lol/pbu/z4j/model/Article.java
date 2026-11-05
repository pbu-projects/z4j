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
import java.util.ArrayList;
import java.util.List;

/**
 * Article in Zendesk Guide Help Center.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        Article.JSON_PROPERTY_LOCALE,
        Article.JSON_PROPERTY_PERMISSION_GROUP_ID,
        Article.JSON_PROPERTY_TITLE,
        Article.JSON_PROPERTY_AUTHOR_ID,
        Article.JSON_PROPERTY_BODY,
        Article.JSON_PROPERTY_COMMENTS_DISABLED,
        Article.JSON_PROPERTY_CONTENT_TAG_IDS,
        Article.JSON_PROPERTY_CREATED_AT,
        Article.JSON_PROPERTY_DRAFT,
        Article.JSON_PROPERTY_EDITED_AT,
        Article.JSON_PROPERTY_HTML_URL,
        Article.JSON_PROPERTY_ID,
        Article.JSON_PROPERTY_LABEL_NAMES,
        Article.JSON_PROPERTY_OUTDATED,
        Article.JSON_PROPERTY_OUTDATED_LOCALES,
        Article.JSON_PROPERTY_POSITION,
        Article.JSON_PROPERTY_PROMOTED,
        Article.JSON_PROPERTY_SECTION_ID,
        Article.JSON_PROPERTY_SOURCE_LOCALE,
        Article.JSON_PROPERTY_UPDATED_AT,
        Article.JSON_PROPERTY_URL,
        Article.JSON_PROPERTY_USER_SEGMENT_ID,
        Article.JSON_PROPERTY_USER_SEGMENT_IDS,
        Article.JSON_PROPERTY_VOTE_COUNT,
        Article.JSON_PROPERTY_VOTE_SUM
})
public record Article(
        @NotNull @JsonProperty(JSON_PROPERTY_LOCALE) LocaleAbbreviation localeAbbreviation,
        @NotNull @JsonProperty(JSON_PROPERTY_PERMISSION_GROUP_ID) Long permissionGroupId,
        @NotNull @JsonProperty(JSON_PROPERTY_TITLE) String title,
        @Nullable @JsonProperty(JSON_PROPERTY_AUTHOR_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long authorId,
        @Nullable @JsonProperty(JSON_PROPERTY_BODY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String body,
        @Nullable @JsonProperty(JSON_PROPERTY_COMMENTS_DISABLED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean commentsDisabled,
        @Nullable @JsonProperty(JSON_PROPERTY_CONTENT_TAG_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull String> contentTagIds,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_DRAFT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean draft,
        @Nullable @JsonProperty(JSON_PROPERTY_EDITED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String editedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_HTML_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String htmlUrl,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_LABEL_NAMES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull String> labelNames,
        @Nullable @JsonProperty(JSON_PROPERTY_OUTDATED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean outdated,
        @Nullable @JsonProperty(JSON_PROPERTY_OUTDATED_LOCALES) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull String> outdatedLocales,
        @Nullable @JsonProperty(JSON_PROPERTY_POSITION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long position,
        @Nullable @JsonProperty(JSON_PROPERTY_PROMOTED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean promoted,
        @Nullable @JsonProperty(JSON_PROPERTY_SECTION_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long sectionId,
        @Nullable @JsonProperty(JSON_PROPERTY_SOURCE_LOCALE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String sourceLocale,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_USER_SEGMENT_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long userSegmentId,
        @Nullable @JsonProperty(JSON_PROPERTY_USER_SEGMENT_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull String> userSegmentIds,
        @Nullable @JsonProperty(JSON_PROPERTY_VOTE_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long voteCount,
        @Nullable @JsonProperty(JSON_PROPERTY_VOTE_SUM) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long voteSum
) implements SearchResultsInner, ArticleBuilder.With {

    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_PERMISSION_GROUP_ID = "permission_group_id";
    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_AUTHOR_ID = "author_id";
    public static final String JSON_PROPERTY_BODY = "body";
    public static final String JSON_PROPERTY_COMMENTS_DISABLED = "comments_disabled";
    public static final String JSON_PROPERTY_CONTENT_TAG_IDS = "content_tag_ids";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DRAFT = "draft";
    public static final String JSON_PROPERTY_EDITED_AT = "edited_at";
    public static final String JSON_PROPERTY_HTML_URL = "html_url";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_LABEL_NAMES = "label_names";
    public static final String JSON_PROPERTY_OUTDATED = "outdated";
    public static final String JSON_PROPERTY_OUTDATED_LOCALES = "outdated_locales";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_PROMOTED = "promoted";
    public static final String JSON_PROPERTY_SECTION_ID = "section_id";
    public static final String JSON_PROPERTY_SOURCE_LOCALE = "source_locale";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_SEGMENT_ID = "user_segment_id";
    public static final String JSON_PROPERTY_USER_SEGMENT_IDS = "user_segment_ids";
    public static final String JSON_PROPERTY_VOTE_COUNT = "vote_count";
    public static final String JSON_PROPERTY_VOTE_SUM = "vote_sum";

    public Article() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Article addContentTagIdsItem(String contentTagIdsItem) {
        List<String> list = contentTagIds != null ? new ArrayList<>(contentTagIds) : new ArrayList<>();
        list.add(contentTagIdsItem);
        return withContentTagIds(list);
    }

    public Article addLabelNamesItem(String labelNamesItem) {
        List<String> list = labelNames != null ? new ArrayList<>(labelNames) : new ArrayList<>();
        list.add(labelNamesItem);
        return withLabelNames(list);
    }

    public Article addUserSegmentIdsItem(String userSegmentIdsItem) {
        List<String> list = userSegmentIds != null ? new ArrayList<>(userSegmentIds) : new ArrayList<>();
        list.add(userSegmentIdsItem);
        return withUserSegmentIds(list);
    }
}
