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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The locale of the translation
 */
@RequiredArgsConstructor
@Getter(onMethod = @__(@JsonValue))
@Serdeable
public enum LocaleAbbreviation {

    @JsonProperty("ar")
    ARABIC("ar"),

    @JsonProperty("pt-br")
    PORTUGUESE_BRAZIL("pt-br"),

    @JsonProperty("bg")
    BULGARIAN("bg"),

    @JsonProperty("cs")
    CZECH("cs"),

    @JsonProperty("da")
    DANISH("da"),

    @JsonProperty("nl")
    DUTCH("nl"),

    @JsonProperty("en-gb")
    ENGLISH_UNITED_KINGDOM("en-gb"),

    @JsonProperty("en-us")
    ENGLISH_UNITED_STATES("en-us"),

    @JsonProperty("fa-af")
    DARI_PERSIAN_AFGHANISTAN("fa-af"),

    @JsonProperty("fil")
    FILIPINO("fil"),

    @JsonProperty("fi")
    FINNISH("fi"),

    @JsonProperty("fr")
    FRENCH("fr"),

    @JsonProperty("fr-ca")
    FRENCH_CANADA("fr-ca"),

    @JsonProperty("de")
    GERMAN("de"),

    @JsonProperty("el")
    GREEK("el"),

    @JsonProperty("he")
    HEBREW("he"),

    @JsonProperty("hi")
    HINDI("hi"),

    @JsonProperty("hu")
    HUNGARIAN("hu"),

    @JsonProperty("id")
    INDONESIAN("id"),

    @JsonProperty("it")
    ITALIAN("it"),

    @JsonProperty("ja")
    JAPANESE("ja"),

    @JsonProperty("ko")
    KOREAN("ko"),

    @JsonProperty("ms")
    MALAY("ms"),

    @JsonProperty("false")
    NORWEGIAN("false"),

    @JsonProperty("pl")
    POLISH("pl"),

    @JsonProperty("ro")
    ROMANIAN("ro"),

    @JsonProperty("ru")
    RUSSIAN("ru"),

    @JsonProperty("zh-cn")
    SIMPLIFIED_CHINESE("zh-cn"),

    @JsonProperty("es")
    SPANISH("es"),

    @JsonProperty("sk")
    SLOVAK("sk"),

    @JsonProperty("sv")
    SWEDISH("sv"),

    @JsonProperty("th")
    THAI("th"),

    @JsonProperty("zh-tw")
    TRADITIONAL_CHINESE("zh-tw"),

    @JsonProperty("tr")
    TURKISH("tr"),

    @JsonProperty("uk")
    UKRAINIAN("uk"),

    @JsonProperty("vi")
    VIETNAMESE("vi"),
    ;

    public static final Map<String, LocaleAbbreviation> VALUE_MAPPING = Map.copyOf(Arrays.stream(values())
            .collect(Collectors.toMap(v -> v.value, Function.identity())));

    private final String value;

    /**
     * Create this enum from a value.
     *
     * @param value The value
     * @return The enum
     */
    @JsonCreator
    public static LocaleAbbreviation fromValue(String value) {
        if (!VALUE_MAPPING.containsKey(value.toLowerCase())) {
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
        return VALUE_MAPPING.get(value.toLowerCase());
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}