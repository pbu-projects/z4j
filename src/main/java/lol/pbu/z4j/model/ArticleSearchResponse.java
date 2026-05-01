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
 * ArticleSearchResponse
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        ArticleSearchResponse.JSON_PROPERTY_RESULT_TYPE,
        ArticleSearchResponse.JSON_PROPERTY_RESULTS,
        ArticleSearchResponse.JSON_PROPERTY_SNIPPET,
})
@Serdeable
public class ArticleSearchResponse {

    public static final String JSON_PROPERTY_RESULT_TYPE = "result_type";
    public static final String JSON_PROPERTY_RESULTS = "results";
    public static final String JSON_PROPERTY_SNIPPET = "snippet";

    /**
     * For articles, always the string <code>article</code>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_RESULT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String resultType = "article";

    @Nullable
    @JsonProperty(JSON_PROPERTY_RESULTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Article> results;

    /**
     * <p>The portion of an article that is relevant to the search query, with matching words or phrases delimited by &lt;em&gt;&lt;/em&gt; tags. Example: a query for <code>carrot potato</code> might return the snippet <code>...don&#39;t confuse &lt;em&gt;carrots&lt;/em&gt; with &lt;em&gt;potatoes&lt;/em&gt;...</code></p>
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_SNIPPET)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String snippet;

    /**
     * Add an item to the results property in a chainable fashion.
     *
     * @return The same instance of ArticleSearchResponse for chaining.
     */
    public ArticleSearchResponse addResultsItem(Article resultsItem) {
        if (results == null) {
            results = new ArrayList<>();
        }
        results.add(resultsItem);
        return this;
    }

}
