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

import java.util.HashMap;
import java.util.Map;

/**
 * BadRequestErrorResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(BadRequestErrorResponse.JSON_PROPERTY_ERRORS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class BadRequestErrorResponse {

    public static final String JSON_PROPERTY_ERRORS = "errors";

    @Nullable
    @JsonProperty(JSON_PROPERTY_ERRORS)
    @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
    private Map<String, Object> errors;

    /**
     * Set the value for the key for the errors map property in a chainable fashion.
     *
     * @return The same instance of BadRequestErrorResponse for chaining.
     */
    public BadRequestErrorResponse putErrorsItem(String key, Object errorsItem) {
        if (errors == null) {
            errors = new HashMap<>();
        }
        errors.put(key, errorsItem);
        return this;
    }

}