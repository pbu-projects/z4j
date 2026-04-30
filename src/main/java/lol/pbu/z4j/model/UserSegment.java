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
 * <p>The <code>user_type</code> attribute takes one of the following values:</p> <table> <thead> <tr> <th>Value</th> <th>Users</th> </tr> </thead> <tbody> <tr> <td>signed_in_users</td> <td>only authenticated users</td> </tr> <tr> <td>staff</td> <td>only agents and Help Center managers</td> </tr> </tbody> </table> <p>For <code>group_ids</code>, <code>organization_ids</code>, <code>tags</code>, and <code>or_tags</code>, an empty array means that access is not restricted by the attribute. For example, if no group ids are specified, then users don't have to be in any specific group to have access.</p> <p>For <code>tags</code>, a user must have all the listed tags to have access. For <code>or_tags</code>, a user must have at least one of the listed tags to have access.</p>
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
        UserSegment.JSON_PROPERTY_NAME,
        UserSegment.JSON_PROPERTY_USER_TYPE,
        UserSegment.JSON_PROPERTY_ADDED_USER_IDS,
        UserSegment.JSON_PROPERTY_BUILT_IN,
        UserSegment.JSON_PROPERTY_CREATED_AT,
        UserSegment.JSON_PROPERTY_GROUP_IDS,
        UserSegment.JSON_PROPERTY_ID,
        UserSegment.JSON_PROPERTY_OR_TAGS,
        UserSegment.JSON_PROPERTY_ORGANIZATION_IDS,
        UserSegment.JSON_PROPERTY_TAGS,
        UserSegment.JSON_PROPERTY_UPDATED_AT,
})
@Serdeable
public class UserSegment {

    public static final String JSON_PROPERTY_NAME = "name";
    public static final String JSON_PROPERTY_USER_TYPE = "user_type";
    public static final String JSON_PROPERTY_ADDED_USER_IDS = "added_user_ids";
    public static final String JSON_PROPERTY_BUILT_IN = "built_in";
    public static final String JSON_PROPERTY_CREATED_AT = "created_at";
    public static final String JSON_PROPERTY_GROUP_IDS = "group_ids";
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_OR_TAGS = "or_tags";
    public static final String JSON_PROPERTY_ORGANIZATION_IDS = "organization_ids";
    public static final String JSON_PROPERTY_TAGS = "tags";
    public static final String JSON_PROPERTY_UPDATED_AT = "updated_at";

    /**
     * User segment name (localized to the locale of the current user for built-in user segments)
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_NAME)
    private String name;

    /**
     * The set of users who can view content
     */
    @NotNull
    @JsonProperty(JSON_PROPERTY_USER_TYPE)
    private String userType;

    /**
     * The ids of users added specifically to this user segment, regardless of matching tags or other criteria
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ADDED_USER_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> addedUserIds;

    /**
     * Whether the user segment is built-in. Built-in user segments cannot be modified
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_BUILT_IN)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean builtIn;

    /**
     * When the user segment was created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_CREATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String createdAt;

    /**
     * The ids of the groups that have access
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_GROUP_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> groupIds;

    /**
     * Automatically assigned when the user segment is created
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * A user must have at least one tag in the list to have access
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_OR_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> orTags;

    /**
     * The ids of the organizations that have access
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ORGANIZATION_IDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull Long> organizationIds;

    /**
     * All the tags a user must have to have access
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_TAGS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> tags;

    /**
     * When the user segment was last updated
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_UPDATED_AT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String updatedAt;

    public UserSegment(String name, String userType) {
        this.name = name;
        this.userType = userType;
    }

    /**
     * Add an item to the addedUserIds property in a chainable fashion.
     *
     * @return The same instance of UserSegment for chaining.
     */
    public UserSegment addAddedUserIdsItem(Long addedUserIdsItem) {
        if (addedUserIds == null) {
            addedUserIds = new ArrayList<>();
        }
        addedUserIds.add(addedUserIdsItem);
        return this;
    }

    /**
     * Add an item to the groupIds property in a chainable fashion.
     *
     * @return The same instance of UserSegment for chaining.
     */
    public UserSegment addGroupIdsItem(Long groupIdsItem) {
        if (groupIds == null) {
            groupIds = new ArrayList<>();
        }
        groupIds.add(groupIdsItem);
        return this;
    }

    /**
     * Add an item to the orTags property in a chainable fashion.
     *
     * @return The same instance of UserSegment for chaining.
     */
    public UserSegment addOrTagsItem(String orTagsItem) {
        if (orTags == null) {
            orTags = new ArrayList<>();
        }
        orTags.add(orTagsItem);
        return this;
    }

    /**
     * Add an item to the organizationIds property in a chainable fashion.
     *
     * @return The same instance of UserSegment for chaining.
     */
    public UserSegment addOrganizationIdsItem(Long organizationIdsItem) {
        if (organizationIds == null) {
            organizationIds = new ArrayList<>();
        }
        organizationIds.add(organizationIdsItem);
        return this;
    }

    /**
     * Add an item to the tags property in a chainable fashion.
     *
     * @return The same instance of UserSegment for chaining.
     */
    public UserSegment addTagsItem(String tagsItem) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tagsItem);
        return this;
    }

}
