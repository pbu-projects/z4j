package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.micronaut.serde.annotation.Serdeable;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * Boolean type custom fields
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@JsonTypeName("boolean")
@Serdeable
public class TicketCustomFieldBoolean extends TicketCustomFieldsInner {
    private Boolean value;

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            this.value = null;
        } else if (value instanceof Boolean) {
            this.value = (Boolean) value;
        } else if (value instanceof String[] arr) {
            this.value = arr.length > 0 && arr[0] != null ? Boolean.parseBoolean(arr[0]) : null;
        } else if (value instanceof Collection<?> col) {
            if (col.isEmpty()) {
                this.value = null;
            } else {
                Object element = col.iterator().next();
                this.value = element == null ? null : (element instanceof Boolean ? (Boolean) element : Boolean.parseBoolean(element.toString()));
            }
        } else if (value.getClass().isArray()) {
            int length = java.lang.reflect.Array.getLength(value);
            if (length > 0) {
                Object element = java.lang.reflect.Array.get(value, 0);
                this.value = element == null ? null : (element instanceof Boolean ? (Boolean) element : Boolean.parseBoolean(element.toString()));
            } else {
                this.value = null;
            }
        } else {
            this.value = Boolean.parseBoolean(value.toString());
        }
    }
}
