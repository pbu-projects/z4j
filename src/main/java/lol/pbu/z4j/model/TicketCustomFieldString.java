package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.micronaut.serde.annotation.Serdeable;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * String type custom field. See {@link TicketCustomFieldStringArray}
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@Serdeable
@JsonTypeName("string")
public class TicketCustomFieldString extends TicketCustomFieldsInner {
    private String value;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            this.value = null;
        } else if (value instanceof String[] arr) {
            this.value = arr.length > 0 ? arr[0] : null;
        } else if (value instanceof Collection<?> col) {
            this.value = col.isEmpty() ? null : col.iterator().next().toString();
        } else if (value.getClass().isArray()) {
            int length = java.lang.reflect.Array.getLength(value);
            if (length > 0) {
                Object element = java.lang.reflect.Array.get(value, 0);
                this.value = element == null ? null : element.toString();
            } else {
                this.value = null;
            }
        } else {
            this.value = value.toString();
        }
    }
}
