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
 * TranslationsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(TranslationsResponse.JSON_PROPERTY_TRANSLATIONS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class TranslationsResponse {

    public static final String JSON_PROPERTY_TRANSLATIONS = "translations";

    @Nullable
    @JsonProperty(JSON_PROPERTY_TRANSLATIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Translation> translations;

    /**
     * Add an item to the translations property in a chainable fashion.
     *
     * @return The same instance of TranslationsResponse for chaining.
     */
    public TranslationsResponse addTranslationsItem(Translation translationsItem) {
        if (translations == null) {
            translations = new ArrayList<>();
        }
        translations.add(translationsItem);
        return this;
    }

}