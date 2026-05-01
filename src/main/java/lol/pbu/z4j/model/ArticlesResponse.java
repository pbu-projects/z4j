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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * ArticlesResponse
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(ArticlesResponse.JSON_PROPERTY_ARTICLES)
@Serdeable
public class ArticlesResponse {

    public static final String JSON_PROPERTY_ARTICLES = "articles";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ARTICLES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Article> articles;

    /**
     * Add an item to the articles property in a chainable fashion.
     *
     * @return The same instance of ArticlesResponse for chaining.
     */
    public ArticlesResponse addArticlesItem(Article articlesItem) {
        if (articles == null) {
            articles = new ArrayList<>();
        }
        articles.add(articlesItem);
        return this;
    }

}
