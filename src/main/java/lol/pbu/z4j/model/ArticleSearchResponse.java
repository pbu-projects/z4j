package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * ArticleSearchResponse
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
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
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