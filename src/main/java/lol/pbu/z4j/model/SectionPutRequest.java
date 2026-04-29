package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * SectionPutRequest
 */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Getter
@Setter
@JsonPropertyOrder(SectionPutRequest.JSON_PROPERTY_SECTION)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class SectionPutRequest {

    public static final String JSON_PROPERTY_SECTION = "section";

    @NotNull
    @Valid
    @JsonProperty(JSON_PROPERTY_SECTION)
    private SectionPutRequestSection section;

    public SectionPutRequest(SectionPutRequestSection section) {
        this.section = section;
    }

}