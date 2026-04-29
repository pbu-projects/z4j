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
 * ArticleAttachmentsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(ArticleAttachmentsResponse.JSON_PROPERTY_ARTICLE_ATTACHMENTS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class ArticleAttachmentsResponse {

    public static final String JSON_PROPERTY_ARTICLE_ATTACHMENTS = "article_attachments";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ARTICLE_ATTACHMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid ArticleAttachment> articleAttachments;

    /**
     * Add an item to the articleAttachments property in a chainable fashion.
     *
     * @return The same instance of ArticleAttachmentsResponse for chaining.
     */
    public ArticleAttachmentsResponse addArticleAttachmentsItem(ArticleAttachment articleAttachmentsItem) {
        if (articleAttachments == null) {
            articleAttachments = new ArrayList<>();
        }
        articleAttachments.add(articleAttachmentsItem);
        return this;
    }

}