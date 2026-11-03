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
import lol.pbu.z4j.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Base request payload for updating Help Center resource source locales.
 *
 * @author Jonathan-Zollinger
 * @since 0.3.0
 */
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Serdeable
@Generated
public class SourceLocaleRequest {

    @Nullable
    @JsonProperty("article_locale")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocaleAbbreviation articleLocale;

    @Nullable
    @JsonProperty("category_locale")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocaleAbbreviation categoryLocale;

    @Nullable
    @JsonProperty("section_locale")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocaleAbbreviation sectionLocale;

    @Generated
    public SourceLocaleRequest(LocaleAbbreviation categoryLocale, LocaleAbbreviation sectionLocale) {
        this.categoryLocale = categoryLocale;
        this.sectionLocale = sectionLocale;
    }

    @Generated
    public SourceLocaleRequest(LocaleAbbreviation locale) {
        this.articleLocale = locale;
        this.categoryLocale = locale;
        this.sectionLocale = locale;
    }

    @JsonIgnore
    @Generated
    public LocaleAbbreviation getSourceLocale() {
        if (articleLocale != null) {
            return articleLocale;
        }
        return categoryLocale != null ? categoryLocale : sectionLocale;
    }

    @JsonIgnore
    @Generated
    public SourceLocaleRequest setSourceLocale(LocaleAbbreviation locale) {
        this.articleLocale = locale;
        this.categoryLocale = locale;
        this.sectionLocale = locale;
        return this;
    }
}
