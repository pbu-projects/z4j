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
 * TopicsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(TopicsResponse.JSON_PROPERTY_TOPICS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class TopicsResponse {

    public static final String JSON_PROPERTY_TOPICS = "topics";

    @Nullable
    @JsonProperty(JSON_PROPERTY_TOPICS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Topic> topics;

    /**
     * Add an item to the topics property in a chainable fashion.
     *
     * @return The same instance of TopicsResponse for chaining.
     */
    public TopicsResponse addTopicsItem(Topic topicsItem) {
        if (topics == null) {
            topics = new ArrayList<>();
        }
        topics.add(topicsItem);
        return this;
    }

}