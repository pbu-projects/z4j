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
 * ArticlesResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(ArticlesResponse.JSON_PROPERTY_ARTICLES)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
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