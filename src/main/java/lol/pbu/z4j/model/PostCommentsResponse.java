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
 * PostCommentsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(PostCommentsResponse.JSON_PROPERTY_COMMENTS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class PostCommentsResponse {

    public static final String JSON_PROPERTY_COMMENTS = "comments";

    @Nullable
    @JsonProperty(JSON_PROPERTY_COMMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid PostComment> comments;

    /**
     * Add an item to the comments property in a chainable fashion.
     *
     * @return The same instance of PostCommentsResponse for chaining.
     */
    public PostCommentsResponse addCommentsItem(PostComment commentsItem) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(commentsItem);
        return this;
    }

}