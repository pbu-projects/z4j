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
 * PostsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(PostsResponse.JSON_PROPERTY_POSTS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class PostsResponse {

    public static final String JSON_PROPERTY_POSTS = "posts";

    @Nullable
    @JsonProperty(JSON_PROPERTY_POSTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Post> posts;

    /**
     * Add an item to the posts property in a chainable fashion.
     *
     * @return The same instance of PostsResponse for chaining.
     */
    public PostsResponse addPostsItem(Post postsItem) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(postsItem);
        return this;
    }

}