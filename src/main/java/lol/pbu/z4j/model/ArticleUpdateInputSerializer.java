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

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.Encoder;
import io.micronaut.serde.Serializer;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Serializes {@link ArticleUpdateInput}, keeping empty lists and explicitly cleared nulls while omitting unset fields.
 */
@Singleton
public class ArticleUpdateInputSerializer implements Serializer<ArticleUpdateInput> {

    private static final Argument<ArticleUpdateInput> INPUT_ARG = Argument.of(ArticleUpdateInput.class);
    private static final Argument<LocaleAbbreviation> LOCALE_ARG = Argument.of(LocaleAbbreviation.class);
    private static final Argument<List<String>> STRING_LIST_ARG = Argument.listOf(String.class);

    @Override
    public void serialize(
            @NonNull Encoder encoder,
            @NonNull EncoderContext context,
            @NonNull Argument<? extends ArticleUpdateInput> type,
            @Nullable ArticleUpdateInput value
    ) throws IOException {
        if (value == null) {
            encoder.encodeNull();
            return;
        }
        Set<String> cleared = value.clearedFields();
        try (Encoder obj = encoder.encodeObject(INPUT_ARG)) {
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_LOCALE, LOCALE_ARG, value.localeAbbreviation(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_PERMISSION_GROUP_ID, Argument.LONG, value.permissionGroupId(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_TITLE, Argument.STRING, value.title(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_AUTHOR_ID, Argument.LONG, value.authorId(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_BODY, Argument.STRING, value.body(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_COMMENTS_DISABLED, Argument.BOOLEAN, value.commentsDisabled(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_CONTENT_TAG_IDS, STRING_LIST_ARG, value.contentTagIds(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_DRAFT, Argument.BOOLEAN, value.draft(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_LABEL_NAMES, STRING_LIST_ARG, value.labelNames(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_POSITION, Argument.LONG, value.position(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_PROMOTED, Argument.BOOLEAN, value.promoted(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_SECTION_ID, Argument.LONG, value.sectionId(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_SOURCE_LOCALE, Argument.STRING, value.sourceLocale(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_USER_SEGMENT_ID, Argument.LONG, value.userSegmentId(), cleared);
            PartialUpdateSerde.writeField(obj, context, ArticleUpdateInput.JSON_PROPERTY_USER_SEGMENT_IDS, STRING_LIST_ARG, value.userSegmentIds(), cleared);
        }
    }
}
