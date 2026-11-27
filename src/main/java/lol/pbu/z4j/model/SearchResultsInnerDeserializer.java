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
import io.micronaut.core.type.Argument;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.serde.Decoder;
import io.micronaut.serde.Deserializer;
import io.micronaut.serde.exceptions.SerdeException;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.Map;

/**
 * Deserializes {@link SearchResultsInner} items using {@code result_type} or object fields.
 */
@Singleton
public class SearchResultsInnerDeserializer implements Deserializer<SearchResultsInner> {

    private static final Map<String, Class<? extends SearchResultsInner>> RESULT_TYPES = Map.of(
            "article", Article.class,
            "post", Post.class,
            "comment", PostComment.class,
            "post_comment", PostComment.class
    );

    @Override
    public SearchResultsInner deserialize(
            @NonNull Decoder decoder,
            @NonNull DecoderContext context,
            @NonNull Argument<? super SearchResultsInner> type
    ) throws IOException {
        return PolymorphicSerdeSupport.deserialize(decoder, context, "SearchResultsInner", this::resolveTargetType);
    }

    private Class<? extends SearchResultsInner> resolveTargetType(JsonNode node) throws SerdeException {
        Class<? extends SearchResultsInner> byResultType = PolymorphicSerdeSupport.resolveByResultType(node, RESULT_TYPES, "SearchResultsInner");
        if (byResultType != null) {
            return byResultType;
        }

        if (PolymorphicSerdeSupport.hasAnyField(node, "section_id", "permission_group_id", "source_locale", "label_names",
                "draft", "promoted", "comments_disabled", "outdated_locales", "user_segment_id", "user_segment_ids", "edited_at")) {
            return Article.class;
        }
        if (PolymorphicSerdeSupport.hasAnyField(node, "topic_id", "details", "featured", "pinned", "closed",
                "comment_count", "follower_count")) {
            return Post.class;
        }
        if (PolymorphicSerdeSupport.hasAnyField(node, "post_id", "official")) {
            return PostComment.class;
        }
        if (PolymorphicSerdeSupport.hasAnyField(node, "title") && PolymorphicSerdeSupport.hasAnyField(node, "body")) {
            return Article.class;
        }
        if (PolymorphicSerdeSupport.hasAnyField(node, "body")) {
            return PostComment.class;
        }
        if (PolymorphicSerdeSupport.hasAnyField(node, "title")) {
            return Post.class;
        }
        throw new SerdeException("Cannot determine SearchResultsInner type from JSON object");
    }
}
