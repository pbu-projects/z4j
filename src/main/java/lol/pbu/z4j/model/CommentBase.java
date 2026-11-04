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

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * Base interface for comment models across Help Center and Community.
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Serdeable
public interface CommentBase {

    String JSON_PROPERTY_BODY = "body";
    String JSON_PROPERTY_LOCALE = "locale";
    String JSON_PROPERTY_AUTHOR_ID = "author_id";
    String JSON_PROPERTY_CREATED_AT = "created_at";
    String JSON_PROPERTY_HTML_URL = "html_url";
    String JSON_PROPERTY_ID = "id";
    String JSON_PROPERTY_NON_AUTHOR_EDITOR_ID = "non_author_editor_id";
    String JSON_PROPERTY_NON_AUTHOR_UPDATED_AT = "non_author_updated_at";
    String JSON_PROPERTY_UPDATED_AT = "updated_at";
    String JSON_PROPERTY_URL = "url";
    String JSON_PROPERTY_VOTE_COUNT = "vote_count";
    String JSON_PROPERTY_VOTE_SUM = "vote_sum";

    @Nullable
    @NotNull
    String body();

    @Nullable
    LocaleAbbreviation localeAbbreviation();

    @Nullable
    Long authorId();

    @Nullable
    String createdAt();

    @Nullable
    String htmlUrl();

    @Nullable
    Long id();

    @Nullable
    Long nonAuthorEditorId();

    @Nullable
    ZonedDateTime nonAuthorUpdatedAt();

    @Nullable
    ZonedDateTime updatedAt();

    @Nullable
    String url();

    @Nullable
    Long voteCount();

    @Nullable
    Long voteSum();
}
