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
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Post
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
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
        Post.JSON_PROPERTY_VOTE_SUM,
})
@Serdeable
public class Post {

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

    /**
     * The title of the post
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * The id of the author of the post. *Writable on create by Help Center managers -- see <a href=\"#create-post\">Create Post</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTHOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long authorId;

    /**
     * Whether further comments are allowed
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CLOSED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean closed;

    /**
     * The number of comments on the post
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COMMENT_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long commentCount;

    /**
     * The list of content tags attached to the post
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TAG_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> contentTagIds;

    /**
     * When the post was created. Writable on create by Help Center managers -- see <a href=\"#create-post\">Create Post</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime createdAt;

    /**
     * The details of the post made by the author. See <a href=\"#user-content\">User content</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DETAILS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String details;

    /**
     * Whether the post is featured
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FEATURED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean featured;

    /**
     * The number of followers of the post
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWER_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long followerCount;

    /**
     * The community url of the post
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HTML_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String htmlUrl;

    /**
     * Automatically assigned when the post is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * The user id of whoever performed the most recent (if any) non-author edit. A non-author edit consists of an edit make by a user other than the author that creates or updates the <code>title</code> or <code>details</code>. Note that only edits made after May 17, 2021 will be reflected in this field. If no non-author edits have occured since May 17, 2021, then this field will be <code>null</code>.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NON_AUTHOR_EDITOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long nonAuthorEditorId;

    /**
     * When the post was last edited by a non-author user
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NON_AUTHOR_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime nonAuthorUpdatedAt;

    /**
     * When true, pins the post to the top of its topic
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PINNED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean pinned;

    /**
     * The status of the post. Possible values: \"planned\", \"not_planned\" , \"answered\", or \"completed\"
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_STATUS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String status;

    /**
     * The id of the topic that the post belongs to
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TOPIC_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long topicId;

    /**
     * When the post was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime updatedAt;

    /**
     * The API url of the post
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * The total number of upvotes and downvotes
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VOTE_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long voteCount;

    /**
     * The sum of upvotes (+1) and downvotes (-1), which may be positive or negative
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VOTE_SUM)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long voteSum;

    public Post(String title) {
        this.title = title;
    }

    /**
     * Add an item to the contentTagIds property in a chainable fashion.
     *
     * @return The same instance of Post for chaining.
     */
    public Post addContentTagIdsItem(Long contentTagIdsItem) {
        if (contentTagIds == null) {
            contentTagIds = new ArrayList<>();
        }
        contentTagIds.add(contentTagIdsItem);
        return this;
    }

}
