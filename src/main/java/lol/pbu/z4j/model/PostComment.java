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

/**
 * PostComment
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
        PostComment.JSON_PROPERTY_BODY,
        PostComment.JSON_PROPERTY_AUTHOR_ID,
        PostComment.JSON_PROPERTY_CREATED_AT,
        PostComment.JSON_PROPERTY_HTML_URL,
        PostComment.JSON_PROPERTY_ID,
        PostComment.JSON_PROPERTY_NON_AUTHOR_EDITOR_ID,
        PostComment.JSON_PROPERTY_NON_AUTHOR_UPDATED_AT,
        PostComment.JSON_PROPERTY_OFFICIAL,
        PostComment.JSON_PROPERTY_POST_ID,
        PostComment.JSON_PROPERTY_UPDATED_AT,
        PostComment.JSON_PROPERTY_URL,
        PostComment.JSON_PROPERTY_VOTE_COUNT,
        PostComment.JSON_PROPERTY_VOTE_SUM,
})
@Serdeable
public class PostComment implements SearchResultsInner {

    public static final String JSON_PROPERTY_BODY = "body";
    public static final String JSON_PROPERTY_AUTHOR_ID = "author_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_HTML_URL = "html_url";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NON_AUTHOR_EDITOR_ID = "non_author_editor_id";
    public static final String JSON_PROPERTY_NON_AUTHOR_UPDATED_AT = "non_author_updated_at";
    public static final String JSON_PROPERTY_OFFICIAL = "official";
    public static final String JSON_PROPERTY_POST_ID = "post_id";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_VOTE_COUNT = "vote_count";
    public static final String JSON_PROPERTY_VOTE_SUM = "vote_sum";

    /**
     * The comment made by the author. See <a href=\"#user-content\">User content</a>
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_BODY)
    private String body;

    /**
     * The id of the author of the comment. Writable on create by Help Center managers. See <a href=\"#create-post-comment\">Create Post Comment</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTHOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long authorId;

    /**
     * When the comment was created. Writable on create by Help Center managers. See <a href=\"#create-post-comment\">Create Post Comment</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * The community url of the comment
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HTML_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String htmlUrl;

    /**
     * Automatically assigned when the comment is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * The user id of whoever performed the most recent (if any) non-author edit. A non-author edit consists of an edit make by a user other than the author that creates or updates the <code>body</code>. Note that only edits made after May 17, 2021 will be reflected in this field. If no non-author edits have occured since May 17, 2021, then this field will be <code>null</code>.
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NON_AUTHOR_EDITOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long nonAuthorEditorId;

    /**
     * When the comment was last edited by a non-author user
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_NON_AUTHOR_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ZonedDateTime nonAuthorUpdatedAt;

    /**
     * Whether the comment is marked as official
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OFFICIAL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean official;

    /**
     * The id of the post on which the comment was made
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POST_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long postId;

    /**
     * When the comment was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * The API url of the comment
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

    public PostComment(String body) {
        this.body = body;
    }

}
