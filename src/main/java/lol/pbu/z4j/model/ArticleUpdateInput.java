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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Payload data for updating an existing Help Center article.
 * Unset fields are omitted, empty lists serialize as {@code []}, and cleared fields serialize as {@code null}.
 */
@Serdeable
@JsonPropertyOrder({
        ArticleUpdateInput.JSON_PROPERTY_LOCALE,
        ArticleUpdateInput.JSON_PROPERTY_PERMISSION_GROUP_ID,
        ArticleUpdateInput.JSON_PROPERTY_TITLE,
        ArticleUpdateInput.JSON_PROPERTY_AUTHOR_ID,
        ArticleUpdateInput.JSON_PROPERTY_BODY,
        ArticleUpdateInput.JSON_PROPERTY_COMMENTS_DISABLED,
        ArticleUpdateInput.JSON_PROPERTY_CONTENT_TAG_IDS,
        ArticleUpdateInput.JSON_PROPERTY_DRAFT,
        ArticleUpdateInput.JSON_PROPERTY_LABEL_NAMES,
        ArticleUpdateInput.JSON_PROPERTY_POSITION,
        ArticleUpdateInput.JSON_PROPERTY_PROMOTED,
        ArticleUpdateInput.JSON_PROPERTY_SECTION_ID,
        ArticleUpdateInput.JSON_PROPERTY_SOURCE_LOCALE,
        ArticleUpdateInput.JSON_PROPERTY_USER_SEGMENT_ID,
        ArticleUpdateInput.JSON_PROPERTY_USER_SEGMENT_IDS
})
public record ArticleUpdateInput(
        @Nullable @JsonProperty(JSON_PROPERTY_LOCALE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) LocaleAbbreviation localeAbbreviation,
        @Nullable @JsonProperty(JSON_PROPERTY_PERMISSION_GROUP_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long permissionGroupId,
        @Nullable @JsonProperty(JSON_PROPERTY_TITLE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String title,
        @Nullable @JsonProperty(JSON_PROPERTY_AUTHOR_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long authorId,
        @Nullable @JsonProperty(JSON_PROPERTY_BODY) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String body,
        @Nullable @JsonProperty(JSON_PROPERTY_COMMENTS_DISABLED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean commentsDisabled,
        @Nullable @JsonProperty(JSON_PROPERTY_CONTENT_TAG_IDS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@NotNull String> contentTagIds,
        @Nullable @JsonProperty(JSON_PROPERTY_DRAFT) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean draft,
        @Nullable @JsonProperty(JSON_PROPERTY_LABEL_NAMES) @JsonInclude(JsonInclude.Include.NON_NULL) List<@NotNull String> labelNames,
        @Nullable @JsonProperty(JSON_PROPERTY_POSITION) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long position,
        @Nullable @JsonProperty(JSON_PROPERTY_PROMOTED) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Boolean promoted,
        @Nullable @JsonProperty(JSON_PROPERTY_SECTION_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long sectionId,
        @Nullable @JsonProperty(JSON_PROPERTY_SOURCE_LOCALE) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) String sourceLocale,
        @Nullable @JsonProperty(JSON_PROPERTY_USER_SEGMENT_ID) @JsonInclude(JsonInclude.Include.USE_DEFAULTS) Long userSegmentId,
        @Nullable @JsonProperty(JSON_PROPERTY_USER_SEGMENT_IDS) @JsonInclude(JsonInclude.Include.NON_NULL) List<@NotNull String> userSegmentIds,
        @Nullable @JsonIgnore Set<String> clearedFields
) {

    public static final String JSON_PROPERTY_LOCALE = Article.JSON_PROPERTY_LOCALE;
    public static final String JSON_PROPERTY_PERMISSION_GROUP_ID = Article.JSON_PROPERTY_PERMISSION_GROUP_ID;
    public static final String JSON_PROPERTY_TITLE = Article.JSON_PROPERTY_TITLE;
    public static final String JSON_PROPERTY_AUTHOR_ID = Article.JSON_PROPERTY_AUTHOR_ID;
    public static final String JSON_PROPERTY_BODY = Article.JSON_PROPERTY_BODY;
    public static final String JSON_PROPERTY_COMMENTS_DISABLED = Article.JSON_PROPERTY_COMMENTS_DISABLED;
    public static final String JSON_PROPERTY_CONTENT_TAG_IDS = Article.JSON_PROPERTY_CONTENT_TAG_IDS;
    public static final String JSON_PROPERTY_DRAFT = Article.JSON_PROPERTY_DRAFT;
    public static final String JSON_PROPERTY_LABEL_NAMES = Article.JSON_PROPERTY_LABEL_NAMES;
    public static final String JSON_PROPERTY_POSITION = Article.JSON_PROPERTY_POSITION;
    public static final String JSON_PROPERTY_PROMOTED = Article.JSON_PROPERTY_PROMOTED;
    public static final String JSON_PROPERTY_SECTION_ID = Article.JSON_PROPERTY_SECTION_ID;
    public static final String JSON_PROPERTY_SOURCE_LOCALE = Article.JSON_PROPERTY_SOURCE_LOCALE;
    public static final String JSON_PROPERTY_USER_SEGMENT_ID = Article.JSON_PROPERTY_USER_SEGMENT_ID;
    public static final String JSON_PROPERTY_USER_SEGMENT_IDS = Article.JSON_PROPERTY_USER_SEGMENT_IDS;

    public ArticleUpdateInput {
        clearedFields = clearedFields == null ? Set.of() : Set.copyOf(clearedFields);
    }

    public ArticleUpdateInput() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Set.of());
    }

    public ArticleUpdateInput(
            LocaleAbbreviation localeAbbreviation,
            Long permissionGroupId,
            String title,
            Long authorId,
            String body,
            Boolean commentsDisabled,
            List<String> contentTagIds,
            Boolean draft,
            List<String> labelNames,
            Long position,
            Boolean promoted,
            Long sectionId,
            String sourceLocale,
            Long userSegmentId,
            List<String> userSegmentIds
    ) {
        this(localeAbbreviation, permissionGroupId, title, authorId, body, commentsDisabled, contentTagIds, draft,
                labelNames, position, promoted, sectionId, sourceLocale, userSegmentId, userSegmentIds, Set.of());
    }

    public static Builder builder() {
        return new Builder();
    }

    @Nullable
    public static ArticleUpdateInput from(@Nullable Article article) {
        if (article == null) {
            return null;
        }
        return new ArticleUpdateInput(
                article.localeAbbreviation(),
                article.permissionGroupId(),
                article.title(),
                article.authorId(),
                article.body(),
                article.commentsDisabled(),
                article.contentTagIds(),
                article.draft(),
                article.labelNames(),
                article.position(),
                article.promoted(),
                article.sectionId(),
                article.sourceLocale(),
                article.userSegmentId(),
                article.userSegmentIds(),
                Set.of()
        );
    }

    public ArticleUpdateInput withLocaleAbbreviation(@Nullable LocaleAbbreviation value) {
        return new ArticleUpdateInput(value, permissionGroupId, title, authorId, body, commentsDisabled, contentTagIds,
                draft, labelNames, position, promoted, sectionId, sourceLocale, userSegmentId, userSegmentIds,
                updateCleared(JSON_PROPERTY_LOCALE, value == null));
    }

    public ArticleUpdateInput clearLocaleAbbreviation() {
        return withLocaleAbbreviation(null);
    }

    public ArticleUpdateInput withPermissionGroupId(@Nullable Long value) {
        return new ArticleUpdateInput(localeAbbreviation, value, title, authorId, body, commentsDisabled, contentTagIds,
                draft, labelNames, position, promoted, sectionId, sourceLocale, userSegmentId, userSegmentIds,
                updateCleared(JSON_PROPERTY_PERMISSION_GROUP_ID, value == null));
    }

    public ArticleUpdateInput clearPermissionGroupId() {
        return withPermissionGroupId(null);
    }

    public ArticleUpdateInput withTitle(@Nullable String value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, value, authorId, body, commentsDisabled,
                contentTagIds, draft, labelNames, position, promoted, sectionId, sourceLocale, userSegmentId,
                userSegmentIds, updateCleared(JSON_PROPERTY_TITLE, value == null));
    }

    public ArticleUpdateInput clearTitle() {
        return withTitle(null);
    }

    public ArticleUpdateInput withAuthorId(@Nullable Long value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, value, body, commentsDisabled,
                contentTagIds, draft, labelNames, position, promoted, sectionId, sourceLocale, userSegmentId,
                userSegmentIds, updateCleared(JSON_PROPERTY_AUTHOR_ID, value == null));
    }

    public ArticleUpdateInput clearAuthorId() {
        return withAuthorId(null);
    }

    public ArticleUpdateInput withBody(@Nullable String value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, value, commentsDisabled,
                contentTagIds, draft, labelNames, position, promoted, sectionId, sourceLocale, userSegmentId,
                userSegmentIds, updateCleared(JSON_PROPERTY_BODY, value == null));
    }

    public ArticleUpdateInput clearBody() {
        return withBody(null);
    }

    public ArticleUpdateInput withCommentsDisabled(@Nullable Boolean value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, body, value,
                contentTagIds, draft, labelNames, position, promoted, sectionId, sourceLocale, userSegmentId,
                userSegmentIds, updateCleared(JSON_PROPERTY_COMMENTS_DISABLED, value == null));
    }

    public ArticleUpdateInput clearCommentsDisabled() {
        return withCommentsDisabled(null);
    }

    public ArticleUpdateInput withContentTagIds(@Nullable List<String> value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, body, commentsDisabled,
                value, draft, labelNames, position, promoted, sectionId, sourceLocale, userSegmentId, userSegmentIds,
                updateCleared(JSON_PROPERTY_CONTENT_TAG_IDS, value == null));
    }

    public ArticleUpdateInput clearContentTagIds() {
        return withContentTagIds(null);
    }

    public ArticleUpdateInput addContentTagIdsItem(String item) {
        List<String> list = contentTagIds != null ? new ArrayList<>(contentTagIds) : new ArrayList<>();
        list.add(item);
        return withContentTagIds(list);
    }

    public ArticleUpdateInput withDraft(@Nullable Boolean value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, body, commentsDisabled,
                contentTagIds, value, labelNames, position, promoted, sectionId, sourceLocale, userSegmentId,
                userSegmentIds, updateCleared(JSON_PROPERTY_DRAFT, value == null));
    }

    public ArticleUpdateInput clearDraft() {
        return withDraft(null);
    }

    public ArticleUpdateInput withLabelNames(@Nullable List<String> value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, body, commentsDisabled,
                contentTagIds, draft, value, position, promoted, sectionId, sourceLocale, userSegmentId, userSegmentIds,
                updateCleared(JSON_PROPERTY_LABEL_NAMES, value == null));
    }

    public ArticleUpdateInput clearLabelNames() {
        return withLabelNames(null);
    }

    public ArticleUpdateInput addLabelNamesItem(String item) {
        List<String> list = labelNames != null ? new ArrayList<>(labelNames) : new ArrayList<>();
        list.add(item);
        return withLabelNames(list);
    }

    public ArticleUpdateInput withPosition(@Nullable Long value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, body, commentsDisabled,
                contentTagIds, draft, labelNames, value, promoted, sectionId, sourceLocale, userSegmentId,
                userSegmentIds, updateCleared(JSON_PROPERTY_POSITION, value == null));
    }

    public ArticleUpdateInput clearPosition() {
        return withPosition(null);
    }

    public ArticleUpdateInput withPromoted(@Nullable Boolean value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, body, commentsDisabled,
                contentTagIds, draft, labelNames, position, value, sectionId, sourceLocale, userSegmentId,
                userSegmentIds, updateCleared(JSON_PROPERTY_PROMOTED, value == null));
    }

    public ArticleUpdateInput clearPromoted() {
        return withPromoted(null);
    }

    public ArticleUpdateInput withSectionId(@Nullable Long value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, body, commentsDisabled,
                contentTagIds, draft, labelNames, position, promoted, value, sourceLocale, userSegmentId,
                userSegmentIds, updateCleared(JSON_PROPERTY_SECTION_ID, value == null));
    }

    public ArticleUpdateInput clearSectionId() {
        return withSectionId(null);
    }

    public ArticleUpdateInput withSourceLocale(@Nullable String value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, body, commentsDisabled,
                contentTagIds, draft, labelNames, position, promoted, sectionId, value, userSegmentId, userSegmentIds,
                updateCleared(JSON_PROPERTY_SOURCE_LOCALE, value == null));
    }

    public ArticleUpdateInput clearSourceLocale() {
        return withSourceLocale(null);
    }

    public ArticleUpdateInput withUserSegmentId(@Nullable Long value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, body, commentsDisabled,
                contentTagIds, draft, labelNames, position, promoted, sectionId, sourceLocale, value, userSegmentIds,
                updateCleared(JSON_PROPERTY_USER_SEGMENT_ID, value == null));
    }

    public ArticleUpdateInput clearUserSegmentId() {
        return withUserSegmentId(null);
    }

    public ArticleUpdateInput withUserSegmentIds(@Nullable List<String> value) {
        return new ArticleUpdateInput(localeAbbreviation, permissionGroupId, title, authorId, body, commentsDisabled,
                contentTagIds, draft, labelNames, position, promoted, sectionId, sourceLocale, userSegmentId, value,
                updateCleared(JSON_PROPERTY_USER_SEGMENT_IDS, value == null));
    }

    public ArticleUpdateInput clearUserSegmentIds() {
        return withUserSegmentIds(null);
    }

    public ArticleUpdateInput addUserSegmentIdsItem(String item) {
        List<String> list = userSegmentIds != null ? new ArrayList<>(userSegmentIds) : new ArrayList<>();
        list.add(item);
        return withUserSegmentIds(list);
    }

    private Set<String> updateCleared(String jsonProperty, boolean cleared) {
        return cleared
                ? PartialUpdateSerde.addClearedField(clearedFields, jsonProperty)
                : PartialUpdateSerde.removeClearedField(clearedFields, jsonProperty);
    }

    public static final class Builder {

        private LocaleAbbreviation localeAbbreviation;
        private Long permissionGroupId;
        private String title;
        private Long authorId;
        private String body;
        private Boolean commentsDisabled;
        private List<String> contentTagIds;
        private Boolean draft;
        private List<String> labelNames;
        private Long position;
        private Boolean promoted;
        private Long sectionId;
        private String sourceLocale;
        private Long userSegmentId;
        private List<String> userSegmentIds;
        private final Set<String> clearedFields = new HashSet<>();

        private Builder() {
        }

        public Builder localeAbbreviation(@Nullable LocaleAbbreviation localeAbbreviation) {
            this.localeAbbreviation = localeAbbreviation;
            trackClear(JSON_PROPERTY_LOCALE, localeAbbreviation == null);
            return this;
        }

        public Builder clearLocaleAbbreviation() {
            return localeAbbreviation(null);
        }

        public Builder permissionGroupId(@Nullable Long permissionGroupId) {
            this.permissionGroupId = permissionGroupId;
            trackClear(JSON_PROPERTY_PERMISSION_GROUP_ID, permissionGroupId == null);
            return this;
        }

        public Builder clearPermissionGroupId() {
            return permissionGroupId(null);
        }

        public Builder title(@Nullable String title) {
            this.title = title;
            trackClear(JSON_PROPERTY_TITLE, title == null);
            return this;
        }

        public Builder clearTitle() {
            return title(null);
        }

        public Builder authorId(@Nullable Long authorId) {
            this.authorId = authorId;
            trackClear(JSON_PROPERTY_AUTHOR_ID, authorId == null);
            return this;
        }

        public Builder clearAuthorId() {
            return authorId(null);
        }

        public Builder body(@Nullable String body) {
            this.body = body;
            trackClear(JSON_PROPERTY_BODY, body == null);
            return this;
        }

        public Builder clearBody() {
            return body(null);
        }

        public Builder commentsDisabled(@Nullable Boolean commentsDisabled) {
            this.commentsDisabled = commentsDisabled;
            trackClear(JSON_PROPERTY_COMMENTS_DISABLED, commentsDisabled == null);
            return this;
        }

        public Builder clearCommentsDisabled() {
            return commentsDisabled(null);
        }

        public Builder contentTagIds(@Nullable List<String> contentTagIds) {
            this.contentTagIds = contentTagIds;
            trackClear(JSON_PROPERTY_CONTENT_TAG_IDS, contentTagIds == null);
            return this;
        }

        public Builder clearContentTagIds() {
            return contentTagIds(null);
        }

        public Builder draft(@Nullable Boolean draft) {
            this.draft = draft;
            trackClear(JSON_PROPERTY_DRAFT, draft == null);
            return this;
        }

        public Builder clearDraft() {
            return draft(null);
        }

        public Builder labelNames(@Nullable List<String> labelNames) {
            this.labelNames = labelNames;
            trackClear(JSON_PROPERTY_LABEL_NAMES, labelNames == null);
            return this;
        }

        public Builder clearLabelNames() {
            return labelNames(null);
        }

        public Builder position(@Nullable Long position) {
            this.position = position;
            trackClear(JSON_PROPERTY_POSITION, position == null);
            return this;
        }

        public Builder clearPosition() {
            return position(null);
        }

        public Builder promoted(@Nullable Boolean promoted) {
            this.promoted = promoted;
            trackClear(JSON_PROPERTY_PROMOTED, promoted == null);
            return this;
        }

        public Builder clearPromoted() {
            return promoted(null);
        }

        public Builder sectionId(@Nullable Long sectionId) {
            this.sectionId = sectionId;
            trackClear(JSON_PROPERTY_SECTION_ID, sectionId == null);
            return this;
        }

        public Builder clearSectionId() {
            return sectionId(null);
        }

        public Builder sourceLocale(@Nullable String sourceLocale) {
            this.sourceLocale = sourceLocale;
            trackClear(JSON_PROPERTY_SOURCE_LOCALE, sourceLocale == null);
            return this;
        }

        public Builder clearSourceLocale() {
            return sourceLocale(null);
        }

        public Builder userSegmentId(@Nullable Long userSegmentId) {
            this.userSegmentId = userSegmentId;
            trackClear(JSON_PROPERTY_USER_SEGMENT_ID, userSegmentId == null);
            return this;
        }

        public Builder clearUserSegmentId() {
            return userSegmentId(null);
        }

        public Builder userSegmentIds(@Nullable List<String> userSegmentIds) {
            this.userSegmentIds = userSegmentIds;
            trackClear(JSON_PROPERTY_USER_SEGMENT_IDS, userSegmentIds == null);
            return this;
        }

        public Builder clearUserSegmentIds() {
            return userSegmentIds(null);
        }

        private void trackClear(String jsonProperty, boolean cleared) {
            if (cleared) {
                clearedFields.add(jsonProperty);
            } else {
                clearedFields.remove(jsonProperty);
            }
        }

        public ArticleUpdateInput build() {
            return new ArticleUpdateInput(
                    localeAbbreviation,
                    permissionGroupId,
                    title,
                    authorId,
                    body,
                    commentsDisabled,
                    contentTagIds,
                    draft,
                    labelNames,
                    position,
                    promoted,
                    sectionId,
                    sourceLocale,
                    userSegmentId,
                    userSegmentIds,
                    Set.copyOf(clearedFields)
            );
        }
    }
}
