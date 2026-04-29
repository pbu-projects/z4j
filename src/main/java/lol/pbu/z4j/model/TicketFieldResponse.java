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
 * TicketFieldResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(TicketFieldResponse.JSON_PROPERTY_TICKET_FIELD)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class TicketFieldResponse {

    public static final String JSON_PROPERTY_TICKET_FIELD = "ticket_field";

    @Nullable
    @Valid
    @JsonProperty(JSON_PROPERTY_TICKET_FIELD)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private TicketFieldObject ticketField;

}