package lol.pbu.z4j.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Boolean type custom fields
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Data
@Serdeable.Serializable(using = TicketCustomFieldBooleanSerde.class)
@Serdeable.Deserializable(using = TicketCustomFieldBooleanSerde.class)
public class TicketCustomFieldBoolean extends TicketCustomFieldsInner {
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
    private Boolean value;
}
