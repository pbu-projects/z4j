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
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import java.util.ArrayList;
import java.util.List;

@Serdeable
@RecordBuilder
public record MissingTranslationsResponse(
        @Nullable @JsonProperty(JSON_PROPERTY_LOCALES) List<LocaleAbbreviation> locales
) implements MissingTranslationsResponseBuilder.With {

    public static final String JSON_PROPERTY_LOCALES = "locales";

    public MissingTranslationsResponse addLocalesItem(LocaleAbbreviation item) {
        List<LocaleAbbreviation> list = new ArrayList<>(locales != null ? locales : List.of());
        list.add(item);
        return new MissingTranslationsResponse(list);
    }
}
