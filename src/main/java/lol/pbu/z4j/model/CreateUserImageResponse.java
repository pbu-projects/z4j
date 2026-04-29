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
 * CreateUserImageResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(CreateUserImageResponse.JSON_PROPERTY_USER_IMAGE)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class CreateUserImageResponse {

    public static final String JSON_PROPERTY_USER_IMAGE = "user_image";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_USER_IMAGE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private CreateUserImageResponseUserImage userImage;

}