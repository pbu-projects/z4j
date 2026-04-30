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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * LocalesWithDefaultResponse
 * @author Jonathan-Zollinger
 * @since 0.1.1
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        LocalesWithDefaultResponse.JSON_PROPERTY_DEFAULT_LOCALE,
        LocalesWithDefaultResponse.JSON_PROPERTY_LOCALES,
})
@Serdeable
public class LocalesWithDefaultResponse {

    public static final String JSON_PROPERTY_DEFAULT_LOCALE = "default_locale";
    public static final String JSON_PROPERTY_LOCALES = "locales";

    @Nullable
    @JsonProperty(JSON_PROPERTY_DEFAULT_LOCALE)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String defaultLocale;

    @Nullable
    @JsonProperty(JSON_PROPERTY_LOCALES)
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private List<@NotNull String> locales;

    /**
     * Add an item to the locales property in a chainable fashion.
     *
     * @return The same instance of LocalesWithDefaultResponse for chaining.
     */
    public LocalesWithDefaultResponse addLocalesItem(String localesItem) {
        if (locales == null) {
            locales = new ArrayList<>();
        }
        locales.add(localesItem);
        return this;
    }

}
