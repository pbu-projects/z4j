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
 * VotesResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(VotesResponse.JSON_PROPERTY_VOTES)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class VotesResponse {

    public static final String JSON_PROPERTY_VOTES = "votes";

    @Nullable
    @JsonProperty(JSON_PROPERTY_VOTES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Vote> votes;

    /**
     * Add an item to the votes property in a chainable fashion.
     *
     * @return The same instance of VotesResponse for chaining.
     */
    public VotesResponse addVotesItem(Vote votesItem) {
        if (votes == null) {
            votes = new ArrayList<>();
        }
        votes.add(votesItem);
        return this;
    }

}