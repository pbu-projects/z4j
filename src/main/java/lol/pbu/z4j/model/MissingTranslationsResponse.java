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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.micronaut.serde.annotation.Serdeable;
import java.util.ArrayList;
import java.util.List;
import lol.pbu.z4j.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * MissingTranslationsResponse
 *
 * @author Jonathan-Zollinger
 * @since 0.2.6
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder(MissingTranslationsResponse.JSON_PROPERTY_LOCALES)
@Serdeable
@Generated
public class MissingTranslationsResponse {

    public static final String JSON_PROPERTY_LOCALES = "locales";

    @JsonProperty(JSON_PROPERTY_LOCALES)
    private List<LocaleAbbreviation> locales;

    /**
     * Add an item to the locales property in a chainable fashion.
     *
     * @param localesItem the locale abbreviation to add
     * @return The same instance of MissingTranslationsResponse for chaining.
     */
    public MissingTranslationsResponse addLocalesItem(LocaleAbbreviation localesItem) {
        if (locales == null) {
            locales = new ArrayList<>();
        }
        locales.add(localesItem);
        return this;
    }
}
