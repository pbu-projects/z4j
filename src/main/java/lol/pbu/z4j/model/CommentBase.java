package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Data
@Serdeable
public class CommentBase {

    public static final String JSON_PROPERTY_BODY = "body";
    public static final String JSON_PROPERTY_LOCALE = "locale";
    public static final String JSON_PROPERTY_AUTHOR_ID = "author_id";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_HTML_URL = "html_url";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_NON_AUTHOR_EDITOR_ID = "non_author_editor_id";
    public static final String JSON_PROPERTY_NON_AUTHOR_UPDATED_AT = "non_author_updated_at";
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
     * The locale in which this comment was made
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_LOCALE)
    private LocaleAbbreviation localeAbbreviation;

    /**
     * The id of the author of this comment. Writable on create by Help Center managers. See <a href=\"#create-comment\">Create Comment</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_AUTHOR_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long authorId;

    /**
     * The time the comment was created. Writable on create by Help Center managers. See <a href=\"#create-comment\">Create Comment</a>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * The url at which the comment is presented in Help Center
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
     * <h4>The user id of whoever performed the most recent (if any) non-author edit.</h4> A non-author edit consists of
     * an edit make by a user other than the author that creates or updates the <code>body</code> or
     * <code>author_id</code>. Note that only edits made after May 17, 2021, will be reflected in this field.
     * If no non-author edits have occurred since May 17, 2021, then this field will be<code>null</code>.
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
     * The time at which the comment was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * The API url of this comment
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

}