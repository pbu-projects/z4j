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

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.Generated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Request payload for updating the article source locale.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.10
 */
@Accessors(chain = true)
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonPropertyOrder(ArticleSourceLocaleRequest.JSON_PROPERTY_ARTICLE_LOCALE)
@Serdeable
@Generated
public class ArticleSourceLocaleRequest extends SourceLocaleRequest {

    public static final String JSON_PROPERTY_ARTICLE_LOCALE = "article_locale";

    public ArticleSourceLocaleRequest(@NotNull LocaleAbbreviation articleLocale) {
        super(articleLocale, null, null);
    }

}
