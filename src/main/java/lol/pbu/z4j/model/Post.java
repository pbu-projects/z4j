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
import java.util.ArrayList;
import java.util.List;

/**
 * Community post entity in Help Center.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
@JsonPropertyOrder({
        Post.JSON_PROPERTY_TITLE,
        Post.JSON_PROPERTY_AUTHOR_ID,
        Post.JSON_PROPERTY_CLOSED,
        Post.JSON_PROPERTY_COMMENT_COUNT,
        Post.JSON_PROPERTY_CONTENT_TAG_IDS,
        Post.JSON_PROPERTY_CREATED_AT,
        Post.JSON_PROPERTY_DETAILS,
        Post.JSON_PROPERTY_FEATURED,
        Post.JSON_PROPERTY_FOLLOWER_COUNT,
        Post.JSON_PROPERTY_HTML_URL,
        Post.JSON_PROPERTY_ID,
        Post.JSON_PROPERTY_NON_AUTHOR_EDITOR_ID,
        Post.JSON_PROPERTY_NON_AUTHOR_UPDATED_AT,
        Post.JSON_PROPERTY_PINNED,
        Post.JSON_PROPERTY_STATUS,
        Post.JSON_PROPERTY_TOPIC_ID,
        Post.JSON_PROPERTY_UPDATED_AT,
        Post.JSON_PROPERTY_URL,
        Post.JSON_PROPERTY_VOTE_COUNT,
        Post.JSON_PROPERTY_VOTE_SUM
})
public record Post(
        @Nullable @NotNull @JsonProperty(JSON_PROPERTY_TITLE) String title,
        @Nullable @JsonProperty(JSON_PROPERTY_AUTHOR_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long authorId,
        @Nullable @JsonProperty(JSON_PROPERTY_CLOSED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean closed,
        @Nullable @JsonProperty(JSON_PROPERTY_COMMENT_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long commentCount,
        @Nullable @JsonProperty(JSON_PROPERTY_CONTENT_TAG_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<String> contentTagIds,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_DETAILS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String details,
        @Nullable @JsonProperty(JSON_PROPERTY_FEATURED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean featured,
        @Nullable @JsonProperty(JSON_PROPERTY_FOLLOWER_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long followerCount,
        @Nullable @JsonProperty(JSON_PROPERTY_HTML_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String htmlUrl,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_NON_AUTHOR_EDITOR_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long nonAuthorEditorId,
        @Nullable @JsonProperty(JSON_PROPERTY_NON_AUTHOR_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime nonAuthorUpdatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_PINNED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean pinned,
        @Nullable @JsonProperty(JSON_PROPERTY_STATUS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String status,
        @Nullable @JsonProperty(JSON_PROPERTY_TOPIC_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long topicId,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) ZonedDateTime updatedAt,
        @Nullable @JsonProperty(JSON_PROPERTY_URL) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String url,
        @Nullable @JsonProperty(JSON_PROPERTY_VOTE_COUNT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long voteCount,
        @Nullable @JsonProperty(JSON_PROPERTY_VOTE_SUM) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long voteSum
) implements SearchResultsInner, PostBuilder.With {

    public static final String JSON_PROPERTY_TITLE = "title";
    public static final String JSON_PROPERTY_AUTHOR_ID = "author_id";
    public static final String JSON_PROPERTY_CLOSED = "closed";
    public static final String JSON_PROPERTY_COMMENT_COUNT = "comment_count";
    public static final String JSON_PROPERTY_CONTENT_TAG_IDS = "content_tag_ids";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DETAILS = "details";
    public static final String JSON_PROPERTY_FEATURED = "featured";
    public static final String JSON_PROPERTY_FOLLOWER_COUNT = "follower_count";
    public static final String JSON_PROPERTY_HTML_URL = "html_url";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NON_AUTHOR_EDITOR_ID = "non_author_editor_id";
    public static final String JSON_PROPERTY_NON_AUTHOR_UPDATED_AT = "non_author_updated_at";
    public static final String JSON_PROPERTY_PINNED = "pinned";
    public static final String JSON_PROPERTY_STATUS = "status";
    public static final String JSON_PROPERTY_TOPIC_ID = "topic_id";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VOTE_COUNT = "vote_count";
    public static final String JSON_PROPERTY_VOTE_SUM = "vote_sum";

    public Post() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Post(String title) {
        this(title, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Post addContentTagIdsItem(String contentTagIdsItem) {
        List<String> list = contentTagIds != null ? new ArrayList<>(contentTagIds) : new ArrayList<>();
        list.add(contentTagIdsItem);
        return withContentTagIds(list);
    }
}
