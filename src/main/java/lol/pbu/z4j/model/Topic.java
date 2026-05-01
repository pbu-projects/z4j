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

/**
 * <p>The <code>manageable_by</code> attribute takes one of the following values:</p> <table> <thead> <tr> <th>Value</th> <th>Users</th> </tr> </thead> <tbody> <tr> <td>staff</td> <td>agents and managers</td> </tr> <tr> <td>managers</td> <td>only Help Center managers</td> </tr> </tbody> </table> <p>Note that <code>manageable_by</code> is only displayed to users who can manage the topic.</p>
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
        Topic.JSON_PROPERTY_NAME,
        Topic.JSON_PROPERTY_CREATED_AT,
        Topic.JSON_PROPERTY_DESCRIPTION,
        Topic.JSON_PROPERTY_FOLLOWER_COUNT,
        Topic.JSON_PROPERTY_HTML_URL,
        Topic.JSON_PROPERTY_ID,
        Topic.JSON_PROPERTY_MANAGEABLE_BY,
        Topic.JSON_PROPERTY_POSITION,
        Topic.JSON_PROPERTY_UPDATED_AT,
        Topic.JSON_PROPERTY_URL,
        Topic.JSON_PROPERTY_USER_SEGMENT_ID,
})
@Serdeable
public class Topic {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_DESCRIPTION = "description";
    public static final String JSON_PROPERTY_FOLLOWER_COUNT = "follower_count";
    public static final String JSON_PROPERTY_HTML_URL = "html_url";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_MANAGEABLE_BY = "manageable_by";
    public static final String JSON_PROPERTY_POSITION = "position";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";
    public static final String JSON_PROPERTY_URL = "url";
    public static final String JSON_PROPERTY_USER_SEGMENT_ID = "user_segment_id";

    /**
     * The name of the topic
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * When the topic was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * The description of the topic. By default an empty string
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_DESCRIPTION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String description;

    /**
     * The number of users following the topic
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_FOLLOWER_COUNT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long followerCount;

    /**
     * The community url of the topic
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_HTML_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String htmlUrl;

    /**
     * Automatically assigned when the topic is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    @Nullable
    @JsonProperty(JSON_PROPERTY_MANAGEABLE_BY)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TopicManageableBy manageableBy;

    /**
     * The position of the topic relative to other topics in the community
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_POSITION)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long position;

    /**
     * When the topic was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    /**
     * The API url of the topic
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_URL)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String url;

    /**
     * The id of the user segment to which this topic belongs
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_SEGMENT_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long userSegmentId;

    public Topic(String name) {
        this.name = name;
    }

}
