/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lol.pbu.z4j.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.serde.annotation.Serdeable;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lol.pbu.z4j.client.CategoryClient;

/**
 * Provides the 'sort by' options when querying Articles. <br>
 * See {@link  CategoryClient}
 *
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
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

    SortArticleBy(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

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
