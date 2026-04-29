package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * HelpCenterLocalesResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(HelpCenterLocalesResponse.JSON_PROPERTY_LOCALES)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class HelpCenterLocalesResponse {

    public static final String JSON_PROPERTY_LOCALES = "locales";

    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> locales;

    /**
     * Add an item to the locales property in a chainable fashion.
     *
     * @return The same instance of HelpCenterLocalesResponse for chaining.
     */
    public HelpCenterLocalesResponse addLocalesItem(String localesItem) {
        if (locales == null) {
            locales = new ArrayList<>();
        }
        locales.add(localesItem);
        return this;
    }

}