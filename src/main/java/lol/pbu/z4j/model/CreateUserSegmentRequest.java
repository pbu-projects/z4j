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

/**
 * CreateUserSegmentRequest
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(CreateUserSegmentRequest.JSON_PROPERTY_USER_SEGMENT)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class CreateUserSegmentRequest {

    public static final String JSON_PROPERTY_USER_SEGMENT = "user_segment";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_USER_SEGMENT)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private UserSegment userSegment;

}