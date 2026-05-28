package lol.pbu.z4j.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Custom field for any number fields with no decimals. See {@link TicketCustomFieldFloat}
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Data
@Serdeable.Serializable(using = TicketCustomFieldLongSerde.class)
@Serdeable.Deserializable(using = TicketCustomFieldLongSerde.class)
public class TicketCustomFieldLong extends TicketCustomFieldsInner {
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
    private Long value;
}
