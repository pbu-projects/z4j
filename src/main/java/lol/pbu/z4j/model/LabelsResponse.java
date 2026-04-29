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
 * LabelsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(LabelsResponse.JSON_PROPERTY_LABELS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class LabelsResponse {

    public static final String JSON_PROPERTY_LABELS = "labels";

    @Nullable
    @JsonProperty(JSON_PROPERTY_LABELS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid Label> labels;

    /**
     * Add an item to the labels property in a chainable fashion.
     *
     * @return The same instance of LabelsResponse for chaining.
     */
    public LabelsResponse addLabelsItem(Label labelsItem) {
        if (labels == null) {
            labels = new ArrayList<>();
        }
        labels.add(labelsItem);
        return this;
    }

}