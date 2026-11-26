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

/**
 * Serializes {@link ArticleUpdateRequest} using {@link ArticleUpdateInput} when present, or {@link Article} otherwise.
 */
@Singleton
public class ArticleUpdateRequestSerializer implements Serializer<ArticleUpdateRequest> {

    private static final Argument<ArticleUpdateRequest> REQUEST_ARG = Argument.of(ArticleUpdateRequest.class);
    private static final Argument<ArticleUpdateInput> INPUT_ARG = Argument.of(ArticleUpdateInput.class);
    private static final Argument<Article> ARTICLE_ARG = Argument.of(Article.class);

    @Override
    public void serialize(
            @NonNull Encoder encoder,
            @NonNull EncoderContext context,
            @NonNull Argument<? extends ArticleUpdateRequest> type,
            @Nullable ArticleUpdateRequest value
    ) throws IOException {
        if (value == null) {
            encoder.encodeNull();
            return;
        }
        try (Encoder obj = encoder.encodeObject(REQUEST_ARG)) {
            if (value.articleInput() != null) {
                obj.encodeKey(ArticleUpdateRequest.JSON_PROPERTY_ARTICLE);
                context.findSerializer(INPUT_ARG)
                        .createSpecific(context, INPUT_ARG)
                        .serialize(obj, context, INPUT_ARG, value.articleInput());
            } else if (value.article() != null) {
                obj.encodeKey(ArticleUpdateRequest.JSON_PROPERTY_ARTICLE);
                context.findSerializer(ARTICLE_ARG)
                        .createSpecific(context, ARTICLE_ARG)
                        .serialize(obj, context, ARTICLE_ARG, value.article());
            }
        }
    }
}
