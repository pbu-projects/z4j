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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Serdeable
@RecordBuilder
public record SourceLocaleRequest(
        @Nullable @JsonProperty(JSON_PROPERTY_ARTICLE_LOCALE) @JsonInclude(JsonInclude.Include.NON_NULL) LocaleAbbreviation articleLocale,
        @Nullable @JsonProperty(JSON_PROPERTY_CATEGORY_LOCALE) @JsonInclude(JsonInclude.Include.NON_NULL) LocaleAbbreviation categoryLocale,
        @Nullable @JsonProperty(JSON_PROPERTY_SECTION_LOCALE) @JsonInclude(JsonInclude.Include.NON_NULL) LocaleAbbreviation sectionLocale
) implements SourceLocaleRequestBuilder.With {

    public static final String JSON_PROPERTY_ARTICLE_LOCALE = "article_locale";
    public static final String JSON_PROPERTY_CATEGORY_LOCALE = "category_locale";
    public static final String JSON_PROPERTY_SECTION_LOCALE = "section_locale";

    public SourceLocaleRequest(LocaleAbbreviation categoryLocale, LocaleAbbreviation sectionLocale) {
        this(null, categoryLocale, sectionLocale);
    }

    public SourceLocaleRequest(LocaleAbbreviation locale) {
        this(locale, locale, locale);
    }

    @JsonIgnore
    public LocaleAbbreviation sourceLocale() {
        return articleLocale != null ? articleLocale : (categoryLocale != null ? categoryLocale : sectionLocale);
    }

    public SourceLocaleRequest withSourceLocale(LocaleAbbreviation locale) {
        return new SourceLocaleRequest(locale, locale, locale);
    }
}
