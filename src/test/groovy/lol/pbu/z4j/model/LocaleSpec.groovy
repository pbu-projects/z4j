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
package lol.pbu.z4j.model

import io.micronaut.serde.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification

import java.time.ZonedDateTime

class LocaleSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Locale with canonical constructor and builder"() {
        given:
        ZonedDateTime created = ZonedDateTime.parse("2026-11-03T10:00:00Z")
        ZonedDateTime updated = ZonedDateTime.parse("2026-11-03T11:00:00Z")

        when:
        Locale direct = new Locale(
                created,
                101L,
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                "English",
                updated,
                "https://example.zendesk.com/api/v2/locales/101.json"
        )
        Locale built = LocaleBuilder.builder()
                .createdAt(created)
                .id(101L)
                .localeAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .name("English")
                .updatedAt(updated)
                .url("https://example.zendesk.com/api/v2/locales/101.json")
                .build()

        then:
        verifyAll(direct) {
            createdAt() == created
            id() == 101L
            localeAbbreviation() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            name() == "English"
            updatedAt() == updated
            url() == "https://example.zendesk.com/api/v2/locales/101.json"
        }
        verifyAll(built) {
            it == direct
            createdAt() == created
            id() == 101L
            localeAbbreviation() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            name() == "English"
            updatedAt() == updated
            url() == "https://example.zendesk.com/api/v2/locales/101.json"
        }
    }

    def "should update Locale using record withers"() {
        given:
        ZonedDateTime t1 = ZonedDateTime.parse("2026-11-03T01:00:00Z")
        ZonedDateTime t2 = ZonedDateTime.parse("2026-11-03T02:00:00Z")
        Locale original = new Locale(t1, 1L, LocaleAbbreviation.ENGLISH_UNITED_STATES, "English", t1, "url1")

        when:
        Locale updated = original
                .withCreatedAt(t2)
                .withId(2L)
                .withLocaleAbbreviation(LocaleAbbreviation.FRENCH)
                .withName("French")
                .withUpdatedAt(t2)
                .withUrl("url2")

        then:
        verifyAll(original) {
            createdAt() == t1
            id() == 1L
            localeAbbreviation() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            name() == "English"
            updatedAt() == t1
            url() == "url1"
        }
        verifyAll(updated) {
            createdAt() == t2
            id() == 2L
            localeAbbreviation() == LocaleAbbreviation.FRENCH
            name() == "French"
            updatedAt() == t2
            url() == "url2"
        }
    }

    def "should round-trip serialize and deserialize Locale"() {
        given:
        ZonedDateTime created = ZonedDateTime.parse("2026-11-03T09:00:00Z")
        ZonedDateTime updated = ZonedDateTime.parse("2026-11-03T09:30:00Z")
        Locale locale = new Locale(
                created,
                555L,
                LocaleAbbreviation.SPANISH,
                "Spanish",
                updated,
                "https://example.zendesk.com/api/v2/locales/555.json"
        )

        when:
        String json = objectMapper.writeValueAsString(locale)
        Map map = objectMapper.readValue(json, Map.class)
        Locale deserialized = objectMapper.readValue(json, Locale.class)

        then:
        verifyAll(map) {
            get("id") == 555
            get("locale") == "es"
            get("name") == "Spanish"
            get("url") == "https://example.zendesk.com/api/v2/locales/555.json"
        }
        verifyAll(deserialized) {
            it == locale
            createdAt() == created
            id() == 555L
            localeAbbreviation() == LocaleAbbreviation.SPANISH
            name() == "Spanish"
            updatedAt() == updated
            url() == "https://example.zendesk.com/api/v2/locales/555.json"
        }
    }

    def "should handle null components in Locale constructor, wither, and serde"() {
        when:
        Locale empty = new Locale(null, null, null, null, null, null)
        Locale built = LocaleBuilder.builder().build()
        ZonedDateTime now = ZonedDateTime.now()
        Locale cleared = new Locale(now, 1L, LocaleAbbreviation.ENGLISH_UNITED_STATES, "en", now, "url")
                .withCreatedAt(null)
                .withId(null)
                .withLocaleAbbreviation(null)
                .withName(null)
                .withUpdatedAt(null)
                .withUrl(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        Locale deserialized = objectMapper.readValue(json, Locale.class)

        then:
        verifyAll(empty) {
            createdAt() == null
            id() == null
            localeAbbreviation() == null
            name() == null
            updatedAt() == null
            url() == null
        }
        verifyAll(built) {
            it == empty
            createdAt() == null
            id() == null
            localeAbbreviation() == null
            name() == null
            updatedAt() == null
            url() == null
        }
        verifyAll(cleared) {
            it == empty
            createdAt() == null
            id() == null
            localeAbbreviation() == null
            name() == null
            updatedAt() == null
            url() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            createdAt() == null
            id() == null
            localeAbbreviation() == null
            name() == null
            updatedAt() == null
            url() == null
        }
    }
}
