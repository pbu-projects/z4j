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

class CategorySpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Category with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.now()

        when:
        Category direct = new Category(
                "Announcements",
                now,
                "Company announcements",
                "https://example.com/hc/categories/1",
                101L,
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                false,
                1L,
                "en-us",
                now,
                "https://example.com/api/v2/help_center/categories/1.json"
        )
        Category oneArg = new Category("OneArgCategory")
        Category noArg = new Category()
        Category built = CategoryBuilder.builder()
                .name("Announcements")
                .createdAt(now)
                .description("Company announcements")
                .htmlUrl("https://example.com/hc/categories/1")
                .id(101L)
                .localeAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .outdated(false)
                .position(1L)
                .sourceLocale("en-us")
                .updatedAt(now)
                .url("https://example.com/api/v2/help_center/categories/1.json")
                .build()

        then:
        verifyAll(direct) {
            name() == "Announcements"
            createdAt() == now
            description() == "Company announcements"
            htmlUrl() == "https://example.com/hc/categories/1"
            id() == 101L
            localeAbbreviation() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            outdated() == false
            position() == 1L
            sourceLocale() == "en-us"
            updatedAt() == now
            url() == "https://example.com/api/v2/help_center/categories/1.json"
        }
        verifyAll(oneArg) {
            name() == "OneArgCategory"
            createdAt() == null
            description() == null
            htmlUrl() == null
            id() == null
            localeAbbreviation() == null
            outdated() == null
            position() == null
            sourceLocale() == null
            updatedAt() == null
            url() == null
        }
        verifyAll(noArg) {
            name() == null
            createdAt() == null
            description() == null
            htmlUrl() == null
            id() == null
            localeAbbreviation() == null
            outdated() == null
            position() == null
            sourceLocale() == null
            updatedAt() == null
            url() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update Category with record withers"() {
        given:
        Category initial = new Category("Initial")
        ZonedDateTime time = ZonedDateTime.parse("2026-01-01T12:00:00Z")

        when:
        Category updated = initial
                .withName("Updated Name")
                .withCreatedAt(time)
                .withDescription("Updated description")
                .withHtmlUrl("https://example.com/hc/categories/2")
                .withId(202L)
                .withLocaleAbbreviation(LocaleAbbreviation.SPANISH)
                .withOutdated(true)
                .withPosition(5L)
                .withSourceLocale("es")
                .withUpdatedAt(time)
                .withUrl("https://example.com/api/categories/2.json")

        then:
        verifyAll(updated) {
            name() == "Updated Name"
            createdAt() == time
            description() == "Updated description"
            htmlUrl() == "https://example.com/hc/categories/2"
            id() == 202L
            localeAbbreviation() == LocaleAbbreviation.SPANISH
            outdated() == true
            position() == 5L
            sourceLocale() == "es"
            updatedAt() == time
            url() == "https://example.com/api/categories/2.json"
        }
    }

    def "should round-trip serialize and deserialize Category"() {
        given:
        Category category = new Category("General")
                .withId(303L)
                .withDescription("General topics")
                .withHtmlUrl("https://example.com/hc/categories/303")
                .withLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .withOutdated(false)
                .withPosition(3L)
                .withSourceLocale("en-us")
                .withUrl("https://example.com/api/categories/303.json")

        when:
        String json = objectMapper.writeValueAsString(category)
        Map map = objectMapper.readValue(json, Map.class)
        Category deserialized = objectMapper.readValue(json, Category.class)

        then:
        verifyAll(map) {
            get("name") == "General"
            get("id") == 303
            get("description") == "General topics"
            get("html_url") == "https://example.com/hc/categories/303"
            get("locale") == "en-us"
            get("outdated") == false
            get("position") == 3
            get("source_locale") == "en-us"
            get("url") == "https://example.com/api/categories/303.json"
        }
        verifyAll(deserialized) {
            it == category
        }
    }
}
