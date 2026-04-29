package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Defines the type of sorting used in this section
 */
@RequiredArgsConstructor
@Getter(onMethod = @__(@JsonValue))
@Serdeable
@Generated("io.micronaut.openapi.generator.JavaMicronautClientCodegen")
public enum SectionPutRequestSectionSorting {

    @JsonProperty("manual")
    MANUAL("manual"),

    @JsonProperty("title")
    TITLE("title"),

    @JsonProperty("creation_desc")
    CREATION_DESC("creation_desc"),

    @JsonProperty("creation_asc")
    CREATION_ASC("creation_asc"),
    ;

    public static final Map<String, SectionPutRequestSectionSorting> VALUE_MAPPING = Map.copyOf(Arrays.stream(values())
            .collect(Collectors.toMap(v -> v.value, Function.identity())));

    private final String value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Create this enum from a value.
     *
     * @param value The value
     * @return The enum
     */
    @JsonCreator
    public static SectionPutRequestSectionSorting fromValue(String value) {
        if (!VALUE_MAPPING.containsKey(value)) {
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
        return VALUE_MAPPING.get(value);
    }
}