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
 * TicketFieldsResponse
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(TicketFieldsResponse.JSON_PROPERTY_TICKET_FIELDS)
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public class TicketFieldsResponse {

    public static final String JSON_PROPERTY_TICKET_FIELDS = "ticket_fields";

    @Nullable
    @JsonProperty(JSON_PROPERTY_TICKET_FIELDS)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@Valid TicketFieldObject> ticketFields;

    /**
     * Add an item to the ticketFields property in a chainable fashion.
     *
     * @return The same instance of TicketFieldsResponse for chaining.
     */
    public TicketFieldsResponse addTicketFieldsItem(TicketFieldObject ticketFieldsItem) {
        if (ticketFields == null) {
            ticketFields = new ArrayList<>();
        }
        ticketFields.add(ticketFieldsItem);
        return this;
    }

}