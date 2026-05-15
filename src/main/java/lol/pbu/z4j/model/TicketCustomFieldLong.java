package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Custom field for any number fields with no decimals. See {@link TicketCustomFieldFloat}
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Serdeable
@JsonTypeName("long")
public class TicketCustomFieldLong extends TicketCustomFieldsInner {
    private Long value;
}
