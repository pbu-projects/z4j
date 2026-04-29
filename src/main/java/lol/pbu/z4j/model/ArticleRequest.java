package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * ArticleRequest
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder({
        ArticleRequest.JSON_PROPERTY_ARTICLE,
        ArticleRequest.JSON_PROPERTY_NOTIFY_SUBSCRIBERS,
})
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class ArticleRequest {

    public static final String JSON_PROPERTY_ARTICLE = "article";
    public static final String JSON_PROPERTY_NOTIFY_SUBSCRIBERS = "notify_subscribers";

    @NotNull
    @Valid
    @JsonProperty(JSON_PROPERTY_ARTICLE)
    private ArticleRequestArticle article;

    @Nullable
    @JsonProperty(JSON_PROPERTY_NOTIFY_SUBSCRIBERS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Boolean notifySubscribers;

    public ArticleRequest(ArticleRequestArticle article) {
        this.article = article;
    }

}