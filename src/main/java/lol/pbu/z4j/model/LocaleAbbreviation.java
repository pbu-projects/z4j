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

import static java.util.Locale.ROOT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The locale code for Zendesk content and translations.
 */
@Serdeable
public final class LocaleAbbreviation implements Serializable, Comparable<LocaleAbbreviation> {

    private static final long serialVersionUID = 1L;

    public static final LocaleAbbreviation ARABIC = new LocaleAbbreviation("ar");
    public static final LocaleAbbreviation PORTUGUESE_BRAZIL = new LocaleAbbreviation("pt-br");
    public static final LocaleAbbreviation BULGARIAN = new LocaleAbbreviation("bg");
    public static final LocaleAbbreviation CZECH = new LocaleAbbreviation("cs");
    public static final LocaleAbbreviation DANISH = new LocaleAbbreviation("da");
    public static final LocaleAbbreviation DUTCH = new LocaleAbbreviation("nl");
    public static final LocaleAbbreviation ENGLISH_UNITED_KINGDOM = new LocaleAbbreviation("en-gb");
    public static final LocaleAbbreviation ENGLISH_UNITED_STATES = new LocaleAbbreviation("en-us");
    public static final LocaleAbbreviation ENGLISH_VIETNAM = new LocaleAbbreviation("en-vn");
    public static final LocaleAbbreviation DARI_PERSIAN_AFGHANISTAN = new LocaleAbbreviation("fa-af");
    public static final LocaleAbbreviation FILIPINO = new LocaleAbbreviation("fil");
    public static final LocaleAbbreviation FINNISH = new LocaleAbbreviation("fi");
    public static final LocaleAbbreviation FRENCH = new LocaleAbbreviation("fr");
    public static final LocaleAbbreviation FRENCH_CANADA = new LocaleAbbreviation("fr-ca");
    public static final LocaleAbbreviation GERMAN = new LocaleAbbreviation("de");
    public static final LocaleAbbreviation GREEK = new LocaleAbbreviation("el");
    public static final LocaleAbbreviation HEBREW = new LocaleAbbreviation("he");
    public static final LocaleAbbreviation HINDI = new LocaleAbbreviation("hi");
    public static final LocaleAbbreviation HUNGARIAN = new LocaleAbbreviation("hu");
    public static final LocaleAbbreviation INDONESIAN = new LocaleAbbreviation("id");
    public static final LocaleAbbreviation ITALIAN = new LocaleAbbreviation("it");
    public static final LocaleAbbreviation JAPANESE = new LocaleAbbreviation("ja");
    public static final LocaleAbbreviation KOREAN = new LocaleAbbreviation("ko");
    public static final LocaleAbbreviation MALAY = new LocaleAbbreviation("ms");
    public static final LocaleAbbreviation NORWEGIAN = new LocaleAbbreviation("no");
    public static final LocaleAbbreviation POLISH = new LocaleAbbreviation("pl");
    public static final LocaleAbbreviation PORTUGUESE = new LocaleAbbreviation("pt");
    public static final LocaleAbbreviation ROMANIAN = new LocaleAbbreviation("ro");
    public static final LocaleAbbreviation RUSSIAN = new LocaleAbbreviation("ru");
    public static final LocaleAbbreviation SIMPLIFIED_CHINESE = new LocaleAbbreviation("zh-cn");
    public static final LocaleAbbreviation SPANISH = new LocaleAbbreviation("es");
    public static final LocaleAbbreviation SLOVAK = new LocaleAbbreviation("sk");
    public static final LocaleAbbreviation SWEDISH = new LocaleAbbreviation("sv");
    public static final LocaleAbbreviation THAI = new LocaleAbbreviation("th");
    public static final LocaleAbbreviation TRADITIONAL_CHINESE = new LocaleAbbreviation("zh-tw");
    public static final LocaleAbbreviation TURKISH = new LocaleAbbreviation("tr");
    public static final LocaleAbbreviation UKRAINIAN = new LocaleAbbreviation("uk");
    public static final LocaleAbbreviation VIETNAMESE_VIETNAM = new LocaleAbbreviation("vi-vn");
    public static final LocaleAbbreviation VIETNAMESE = new LocaleAbbreviation("vi");
    public static final LocaleAbbreviation ZULU_SOUTH_AFRICA = new LocaleAbbreviation("zu-za");

    private static final List<LocaleAbbreviation> KNOWN_VALUES = List.of(
            ARABIC,
            PORTUGUESE_BRAZIL,
            BULGARIAN,
            CZECH,
            DANISH,
            DUTCH,
            ENGLISH_UNITED_KINGDOM,
            ENGLISH_UNITED_STATES,
            ENGLISH_VIETNAM,
            DARI_PERSIAN_AFGHANISTAN,
            FILIPINO,
            FINNISH,
            FRENCH,
            FRENCH_CANADA,
            GERMAN,
            GREEK,
            HEBREW,
            HINDI,
            HUNGARIAN,
            INDONESIAN,
            ITALIAN,
            JAPANESE,
            KOREAN,
            MALAY,
            NORWEGIAN,
            POLISH,
            PORTUGUESE,
            ROMANIAN,
            RUSSIAN,
            SIMPLIFIED_CHINESE,
            SPANISH,
            SLOVAK,
            SWEDISH,
            THAI,
            TRADITIONAL_CHINESE,
            TURKISH,
            UKRAINIAN,
            VIETNAMESE_VIETNAM,
            VIETNAMESE,
            ZULU_SOUTH_AFRICA
    );

    public static final Map<String, LocaleAbbreviation> VALUE_MAPPING = KNOWN_VALUES.stream()
            .collect(Collectors.toUnmodifiableMap(LocaleAbbreviation::getValue, Function.identity()));

    private static final Pattern LOCALE_PATTERN = Pattern.compile("^[a-z]{2,3}(?:-[a-z0-9]{1,8})*$");

    private final String value;

    private LocaleAbbreviation(String value) {
        this.value = value;
    }

    public static LocaleAbbreviation[] values() {
        return KNOWN_VALUES.toArray(LocaleAbbreviation[]::new);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Parses a locale string into a {@link LocaleAbbreviation}.
     *
     * @param value The locale string
     * @return The matching locale, or null when blank
     */
    @JsonCreator
    @Nullable
    public static LocaleAbbreviation fromValue(@Nullable String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        String normalized = value.trim().toLowerCase(ROOT);
        LocaleAbbreviation known = VALUE_MAPPING.get(normalized);
        if (known != null) {
            return known;
        }
        if (!LOCALE_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
        return new LocaleAbbreviation(normalized);
    }

    public static LocaleAbbreviation of(String value) {
        LocaleAbbreviation parsed = fromValue(value);
        if (parsed == null) {
            throw new IllegalArgumentException("Locale value must not be null or blank");
        }
        return parsed;
    }

    @Override
    public int compareTo(LocaleAbbreviation other) {
        return value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LocaleAbbreviation that)) {
            return false;
        }
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
