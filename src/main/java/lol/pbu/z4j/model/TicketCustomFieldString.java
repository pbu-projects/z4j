package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * String type custom field. See {@link TicketCustomFieldStringArray}
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Serdeable
@JsonTypeName("string")
public class TicketCustomFieldString extends TicketCustomFieldsInner {
    private String value;
}
