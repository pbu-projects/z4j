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
 * UserSegmentsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(UserSegmentsResponse.JSON_PROPERTY_USER_SEGMENTS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class UserSegmentsResponse {

    public static final String JSON_PROPERTY_USER_SEGMENTS = "user_segments";

    @Nullable
    @JsonProperty(JSON_PROPERTY_USER_SEGMENTS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid UserSegment> userSegments;

    /**
     * Add an item to the userSegments property in a chainable fashion.
     *
     * @return The same instance of UserSegmentsResponse for chaining.
     */
    public UserSegmentsResponse addUserSegmentsItem(UserSegment userSegmentsItem) {
        if (userSegments == null) {
            userSegments = new ArrayList<>();
        }
        userSegments.add(userSegmentsItem);
        return this;
    }

}