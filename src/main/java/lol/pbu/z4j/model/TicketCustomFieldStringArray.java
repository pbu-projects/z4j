package lol.pbu.z4j.model;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Custom field which can be an array of strings
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = false)
@Serdeable.Serializable(using = TicketCustomFieldStringArraySerde.class)
@Serdeable.Deserializable(using = TicketCustomFieldStringArraySerde.class)
public class TicketCustomFieldStringArray extends TicketCustomFieldsInner {
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
    private String[] value;
}
