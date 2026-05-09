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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * PostComment
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@JsonPropertyOrder({
        CommentBase.JSON_PROPERTY_BODY,
        CommentBase.JSON_PROPERTY_AUTHOR_ID,
        CommentBase.JSON_PROPERTY_CREATED_AT,
        CommentBase.JSON_PROPERTY_HTML_URL,
        CommentBase.JSON_PROPERTY_ID,
        CommentBase.JSON_PROPERTY_NON_AUTHOR_EDITOR_ID,
        CommentBase.JSON_PROPERTY_NON_AUTHOR_UPDATED_AT,
        PostComment.JSON_PROPERTY_OFFICIAL,
        PostComment.JSON_PROPERTY_POST_ID,
        CommentBase.JSON_PROPERTY_UPDATED_AT,
        CommentBase.JSON_PROPERTY_URL,
        CommentBase.JSON_PROPERTY_VOTE_COUNT,
        CommentBase.JSON_PROPERTY_VOTE_SUM,
})
@Serdeable
public class PostComment extends CommentBase implements SearchResultsInner {

    public static final String JSON_PROPERTY_OFFICIAL = "official";
    public static final String JSON_PROPERTY_POST_ID = "post_id";

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


    public PostComment(String body) {
        setBody(body);
    }

}
