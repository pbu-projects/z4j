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
 * SectionsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(SectionsResponse.JSON_PROPERTY_SECTIONS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class SectionsResponse {

    public static final String JSON_PROPERTY_SECTIONS = "sections";

    @Nullable
    @JsonProperty(JSON_PROPERTY_SECTIONS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Section> sections;

    /**
     * Add an item to the sections property in a chainable fashion.
     *
     * @return The same instance of SectionsResponse for chaining.
     */
    public SectionsResponse addSectionsItem(Section sectionsItem) {
        if (sections == null) {
            sections = new ArrayList<>();
        }
        sections.add(sectionsItem);
        return this;
    }

}