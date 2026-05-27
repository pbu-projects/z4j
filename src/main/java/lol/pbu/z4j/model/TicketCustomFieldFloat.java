package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.micronaut.serde.annotation.Serdeable;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * Custom field for floating-point numbers. See {@link TicketCustomFieldLong}
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@Serdeable
@JsonTypeName("float")
public class TicketCustomFieldFloat extends TicketCustomFieldsInner {
    private Float value;

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            this.value = null;
        } else if (value instanceof Number) {
            this.value = ((Number) value).floatValue();
        } else if (value instanceof String[] arr) {
            this.value = arr.length > 0 && arr[0] != null ? Float.parseFloat(arr[0]) : null;
        } else if (value instanceof Collection<?> col) {
            if (col.isEmpty()) {
                this.value = null;
            } else {
                Object element = col.iterator().next();
                this.value = element == null ? null : (element instanceof Number ? ((Number) element).floatValue() : Float.parseFloat(element.toString()));
            }
        } else if (value.getClass().isArray()) {
            int length = java.lang.reflect.Array.getLength(value);
            if (length > 0) {
                Object element = java.lang.reflect.Array.get(value, 0);
                this.value = element == null ? null : (element instanceof Number ? ((Number) element).floatValue() : Float.parseFloat(element.toString()));
            } else {
                this.value = null;
            }
        } else {
            this.value = Float.parseFloat(value.toString());
        }
    }
}
