package lol.pbu.z4j.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * String type custom field. See {@link TicketCustomFieldStringArray}
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Data
@Serdeable.Serializable(using = TicketCustomFieldStringSerde.class)
@Serdeable.Deserializable(using = TicketCustomFieldStringSerde.class)
public class TicketCustomFieldString extends TicketCustomFieldsInner {

    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_VALUE = "value";

    /**
     * The id of the custom field
     */
    @Nullable
    private Long id;

    /**
     * The value of the custom field
     */
    @Nullable
    private String value;
}
