package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.serde.annotation.Serdeable;
import lol.pbu.z4j.client.CategoryClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Provides the 'sort by' options when querying Articles. <br>
 * See {@link  CategoryClient}
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@RequiredArgsConstructor
@Getter(onMethod_ = {@JsonValue})
@Serdeable
public enum SortArticleBy {
    /**
     * Order set manually using the Arrange Content page.
     * <p>Default order if no other order is selected</p>
     */
    POSITION("position"),
    /**
     * Order alphabetically by title. Must specify a locale in associated query
     */
    TITLE("title"),
    /**
     * Order by creation time
     */
    CREATED_AT("created_at"),
    /**
     * Order by update time
     */
    UPDATED_AT("updated_at"),
    /**
     * Order by the last time the title or body was edited. Must specify a locale in associated query
     */
    EDITED_AT("edited_at"),
    ;

    public static final Map<String, SortArticleBy> VALUE_MAPPING = Map.copyOf(Arrays.stream(values())
            .collect(Collectors.toMap(v -> v.value, Function.identity())));

    private final String value;


    /**
     * Create this enum from a value.
     *
     * @param value The value
     * @return The enum
     */
    @JsonCreator
    public static SortArticleBy fromValue(String value) {
        if (!VALUE_MAPPING.containsKey(value)) {
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
        return VALUE_MAPPING.get(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
