package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Boolean type custom fields
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonTypeName("boolean")
@Serdeable
public class TicketCustomFieldBoolean extends TicketCustomFieldsInner {
    private Boolean value;
}
