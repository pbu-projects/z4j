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

import java.util.ArrayList;
import java.util.List;

/**
 * Article
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
        Article.JSON_PROPERTY_VOTE_SUM,
})
@Serdeable
public class Article implements SearchResultsInner {

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

    /**
     * The locale that the article is being displayed in
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_LOCALE)
    private String locale;

    /**
     * The id of the permission group which defines who can edit and publish this article
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_PERMISSION_GROUP_ID)
    private Long permissionGroupId;

    /**
     * The title of the article
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    /**
     * The id of the user who wrote the article (set to the user who made the request on create by default)
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTHOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long authorId;

    /**
     * <p>HTML body of the article. Unsafe tags and attributes may be removed before display. For a list of safe tags and attributes,  see <a href=\"https://support.zendesk.com/hc/en-us/articles/115015895948\">Allowing unsafe HTML in Help Center articles</a> in Zendesk help</p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BODY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String body;

    /**
     * True if comments are disabled; false otherwise
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_COMMENTS_DISABLED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean commentsDisabled;

    /**
     * The list of content tags attached to the article
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TAG_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> contentTagIds;

    /**
     * The time the article was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * True if the translation for the current locale is a draft; false otherwise. false by default. Can be set when creating but not when updating. For updating, see Translations
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DRAFT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean draft;

    /**
     * The time the article was last edited in its displayed locale
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_EDITED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String editedAt;

    /**
     * The url of the article in Help Center
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HTML_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String htmlUrl;

    /**
     * Automatically assigned when the article is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * An array of label names associated with this article. By default no label names are used. Only available on certain plans
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_LABEL_NAMES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> labelNames;

    /**
     * Deprecated. Always false because the source translation is always the most up-to-date translation
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OUTDATED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean outdated;

    /**
     * Locales in which the article was marked as outdated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OUTDATED_LOCALES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> outdatedLocales;

    /**
     * The position of this article in the article list. 0 by default
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    /**
     * True if this article is promoted; false otherwise. false by default
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_PROMOTED)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean promoted;

    /**
     * The id of the section to which this article belongs
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SECTION_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long sectionId;

    /**
     * The source (default) locale of the article
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SOURCE_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String sourceLocale;

    /**
     * The time the article was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * The API url of the article
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * The id of the user segment which defines who can see this article. Set to null to make it accessible to everyone. Either user_segment_id or user_segment_ids must be specified
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_SEGMENT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long userSegmentId;

    /**
     * List of user segment ids which define who can view this article. Set to an empty list to make it accessible to everyone. For Enterprise plans only this may contain more than one user_segment_id. Either user_segment_id or user_segment_ids must be specified
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_SEGMENT_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> userSegmentIds;

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

    public Article(String locale, Long permissionGroupId, String title) {
        this.locale = locale;
        this.permissionGroupId = permissionGroupId;
        this.title = title;
    }

    /**
     * Add an item to the contentTagIds property in a chainable fashion.
     *
     * @return The same instance of Article for chaining.
     */
    public Article addContentTagIdsItem(String contentTagIdsItem) {
        if (contentTagIds == null) {
            contentTagIds = new ArrayList<>();
        }
        contentTagIds.add(contentTagIdsItem);
        return this;
    }

    /**
     * Add an item to the labelNames property in a chainable fashion.
     *
     * @return The same instance of Article for chaining.
     */
    public Article addLabelNamesItem(String labelNamesItem) {
        if (labelNames == null) {
            labelNames = new ArrayList<>();
        }
        labelNames.add(labelNamesItem);
        return this;
    }

    /**
     * Add an item to the userSegmentIds property in a chainable fashion.
     *
     * @return The same instance of Article for chaining.
     */
    public Article addUserSegmentIdsItem(String userSegmentIdsItem) {
        if (userSegmentIds == null) {
            userSegmentIds = new ArrayList<>();
        }
        userSegmentIds.add(userSegmentIdsItem);
        return this;
    }

}
