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
 * CommunityPostSearchResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(CommunityPostSearchResponse.JSON_PROPERTY_RESULTS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class CommunityPostSearchResponse {

    public static final String JSON_PROPERTY_RESULTS = "results";

    @Nullable
    @JsonProperty(JSON_PROPERTY_RESULTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Post> results;

    /**
     * Add an item to the results property in a chainable fashion.
     *
     * @return The same instance of CommunityPostSearchResponse for chaining.
     */
    public CommunityPostSearchResponse addResultsItem(Post resultsItem) {
        if (results == null) {
            results = new ArrayList<>();
        }
        results.add(resultsItem);
        return this;
    }

}