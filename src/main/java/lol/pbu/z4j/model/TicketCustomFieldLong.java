package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
@JsonPropertyOrder({
        TicketCustomFieldLong.JSON_PROPERTY_ID,
        TicketCustomFieldLong.JSON_PROPERTY_VALUE,
})
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Data
@Serdeable
@JsonTypeName("long")
public class TicketCustomFieldLong extends TicketCustomFieldsInner {
    public static final String JSON_PROPERTY_ID = "id";
    public static final String JSON_PROPERTY_VALUE = "value";

    /**
     * The id of the custom field
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_ID)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long id;

    /**
     * The value of the custom field
     */
    @Nullable
    @JsonProperty(JSON_PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Long value;
}
