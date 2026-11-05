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
 * User segment defining viewing permissions in Help Center.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
@RecordBuilder
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
        UserSegment.JSON_PROPERTY_UPDATED_AT
})
public record UserSegment(
        @NotNull @JsonProperty(JSON_PROPERTY_NAME) String name,
        @NotNull @JsonProperty(JSON_PROPERTY_USER_TYPE) String userType,
        @Nullable @JsonProperty(JSON_PROPERTY_ADDED_USER_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull Long> addedUserIds,
        @Nullable @JsonProperty(JSON_PROPERTY_BUILT_IN) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean builtIn,
        @Nullable @JsonProperty(JSON_PROPERTY_CREATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String createdAt,
        @Nullable @JsonProperty(JSON_PROPERTY_GROUP_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull Long> groupIds,
        @Nullable @JsonProperty(JSON_PROPERTY_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long id,
        @Nullable @JsonProperty(JSON_PROPERTY_OR_TAGS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull String> orTags,
        @Nullable @JsonProperty(JSON_PROPERTY_ORGANIZATION_IDS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull Long> organizationIds,
        @Nullable @JsonProperty(JSON_PROPERTY_TAGS) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) List<@NotNull String> tags,
        @Nullable @JsonProperty(JSON_PROPERTY_UPDATED_AT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String updatedAt
) implements UserSegmentBuilder.With {

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

    public UserSegment(String name, String userType) {
        this(name, userType, null, null, null, null, null, null, null, null, null);
    }

    public UserSegment() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }

    public UserSegment addAddedUserIdsItem(Long addedUserIdsItem) {
        List<Long> list = addedUserIds != null ? new ArrayList<>(addedUserIds) : new ArrayList<>();
        list.add(addedUserIdsItem);
        return withAddedUserIds(list);
    }

    public UserSegment addGroupIdsItem(Long groupIdsItem) {
        List<Long> list = groupIds != null ? new ArrayList<>(groupIds) : new ArrayList<>();
        list.add(groupIdsItem);
        return withGroupIds(list);
    }

    public UserSegment addOrTagsItem(String orTagsItem) {
        List<String> list = orTags != null ? new ArrayList<>(orTags) : new ArrayList<>();
        list.add(orTagsItem);
        return withOrTags(list);
    }

    public UserSegment addOrganizationIdsItem(Long organizationIdsItem) {
        List<Long> list = organizationIds != null ? new ArrayList<>(organizationIds) : new ArrayList<>();
        list.add(organizationIdsItem);
        return withOrganizationIds(list);
    }

    public UserSegment addTagsItem(String tagsItem) {
        List<String> list = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
        list.add(tagsItem);
        return withTags(list);
    }
}
