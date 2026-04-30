package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketFieldCreateRequest {
    @JsonProperty("ticket_field")
    private TicketField ticketField;
}
