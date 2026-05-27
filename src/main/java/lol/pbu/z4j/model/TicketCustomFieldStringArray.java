package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.micronaut.serde.annotation.Serdeable;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * Custom field which can be an array of strings
 *
 * @author Jonathan-Zollinger
 * @since 0.1.5
 */
@Setter
@EqualsAndHashCode(callSuper = true)
@Serdeable
@JsonTypeName("string[]")
public class TicketCustomFieldStringArray extends TicketCustomFieldsInner {
    private String[] value;

    @Override
    public String[] getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if (value == null) {
            this.value = null;
        } else if (value instanceof String[]) {
            this.value = (String[]) value;
        } else if (value instanceof java.util.Collection) {
            this.value = ((java.util.Collection<?>) value).stream()
                    .map(Object::toString)
                    .toArray(String[]::new);
        } else if (value.getClass().isArray()) {
            int length = java.lang.reflect.Array.getLength(value);
            this.value = new String[length];
            for (int i = 0; i < length; i++) {
                Object element = java.lang.reflect.Array.get(value, i);
                this.value[i] = element == null ? null : element.toString();
            }
        } else {
            this.value = new String[]{value.toString()};
        }
    }

}
