package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * CreateUserImageResponseUserImage
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        CreateUserImageResponseUserImage.JSON_PROPERTY_CONTENT_TYPE,
        CreateUserImageResponseUserImage.JSON_PROPERTY_PATH,
        CreateUserImageResponseUserImage.JSON_PROPERTY_SIZE,
})
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class CreateUserImageResponseUserImage {

    public static final String JSON_PROPERTY_CONTENT_TYPE = "content_type";
    public static final String JSON_PROPERTY_PATH = "path";
    public static final String JSON_PROPERTY_SIZE = "size";

    @Nullable
    @JsonProperty(JSON_PROPERTY_CONTENT_TYPE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String contentType;

    @Nullable
    @JsonProperty(JSON_PROPERTY_PATH)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String path;

    @Nullable
    @JsonProperty(JSON_PROPERTY_SIZE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private BigDecimal size;

}