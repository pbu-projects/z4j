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

class SectionSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Section with constructors and builder"() {
        when:
        Section direct = new Section(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                "Billing Questions",
                101L,
                "2026-01-01T00:00:00Z",
                "Questions regarding billing",
                "https://example.com/hc/sections/1",
                202L,
                false,
                null,
                1L,
                "en-us",
                "custom_template",
                "2026-01-02T00:00:00Z",
                "https://example.com/api/v2/help_center/sections/1.json"
        )
        Section oneArg = new Section("FAQ")
        Section twoArg = new Section(LocaleAbbreviation.ENGLISH_UNITED_STATES, "FAQ")
        Section noArg = new Section()
        Section built = SectionBuilder.builder()
                .localeAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .name("Billing Questions")
                .categoryId(101L)
                .createdAt("2026-01-01T00:00:00Z")
                .description("Questions regarding billing")
                .htmlUrl("https://example.com/hc/sections/1")
                .id(202L)
                .outdated(false)
                .parentSectionId(null)
                .position(1L)
                .sourceLocale("en-us")
                .themeTemplate("custom_template")
                .updatedAt("2026-01-02T00:00:00Z")
                .url("https://example.com/api/v2/help_center/sections/1.json")
                .build()

        then:
        verifyAll(direct) {
            localeAbbreviation() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            name() == "Billing Questions"
            categoryId() == 101L
            createdAt() == "2026-01-01T00:00:00Z"
            description() == "Questions regarding billing"
            htmlUrl() == "https://example.com/hc/sections/1"
            id() == 202L
            outdated() == false
            parentSectionId() == null
            position() == 1L
            sourceLocale() == "en-us"
            themeTemplate() == "custom_template"
            updatedAt() == "2026-01-02T00:00:00Z"
            url() == "https://example.com/api/v2/help_center/sections/1.json"
        }
        verifyAll(oneArg) {
            localeAbbreviation() == null
            name() == "FAQ"
            categoryId() == null
            createdAt() == null
            description() == null
            htmlUrl() == null
            id() == null
            outdated() == null
            parentSectionId() == null
            position() == null
            sourceLocale() == null
            themeTemplate() == null
            updatedAt() == null
            url() == null
        }
        verifyAll(twoArg) {
            localeAbbreviation() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            name() == "FAQ"
            categoryId() == null
            createdAt() == null
            description() == null
            htmlUrl() == null
            id() == null
            outdated() == null
            parentSectionId() == null
            position() == null
            sourceLocale() == null
            themeTemplate() == null
            updatedAt() == null
            url() == null
        }
        verifyAll(noArg) {
            localeAbbreviation() == null
            name() == null
            categoryId() == null
            createdAt() == null
            description() == null
            htmlUrl() == null
            id() == null
            outdated() == null
            parentSectionId() == null
            position() == null
            sourceLocale() == null
            themeTemplate() == null
            updatedAt() == null
            url() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update Section with record withers"() {
        given:
        Section initial = new Section()

        when:
        Section updated = initial
                .withLocaleAbbreviation(LocaleAbbreviation.GERMAN)
                .withName("Updated Section")
                .withCategoryId(303L)
                .withCreatedAt("2026-02-01T00:00:00Z")
                .withDescription("Updated description")
                .withHtmlUrl("https://example.com/hc/sections/2")
                .withId(404L)
                .withOutdated(true)
                .withParentSectionId(505L)
                .withPosition(3L)
                .withSourceLocale("de")
                .withThemeTemplate("default_template")
                .withUpdatedAt("2026-02-02T00:00:00Z")
                .withUrl("https://example.com/api/sections/2.json")

        then:
        verifyAll(updated) {
            localeAbbreviation() == LocaleAbbreviation.GERMAN
            name() == "Updated Section"
            categoryId() == 303L
            createdAt() == "2026-02-01T00:00:00Z"
            description() == "Updated description"
            htmlUrl() == "https://example.com/hc/sections/2"
            id() == 404L
            outdated() == true
            parentSectionId() == 505L
            position() == 3L
            sourceLocale() == "de"
            themeTemplate() == "default_template"
            updatedAt() == "2026-02-02T00:00:00Z"
            url() == "https://example.com/api/sections/2.json"
        }
    }

    def "should round-trip serialize and deserialize Section"() {
        given:
        Section section = new Section("Support")
                .withLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .withCategoryId(111L)
                .withCreatedAt("2026-03-01T00:00:00Z")
                .withDescription("General Support")
                .withHtmlUrl("https://example.com/hc/sections/777")
                .withId(777L)
                .withOutdated(false)
                .withParentSectionId(222L)
                .withPosition(2L)
                .withSourceLocale("en-us")
                .withThemeTemplate("theme1")
                .withUpdatedAt("2026-03-02T00:00:00Z")
                .withUrl("https://example.com/api/sections/777.json")

        when:
        String json = objectMapper.writeValueAsString(section)
        Map map = objectMapper.readValue(json, Map.class)
        Section deserialized = objectMapper.readValue(json, Section.class)

        then:
        verifyAll(map) {
            get("name") == "Support"
            get("locale") == "en-us"
            get("category_id") == 111
            get("created_at") == "2026-03-01T00:00:00Z"
            get("description") == "General Support"
            get("html_url") == "https://example.com/hc/sections/777"
            get("id") == 777
            get("outdated") == false
            get("parent_section_id") == 222
            get("position") == 2
            get("source_locale") == "en-us"
            get("theme_template") == "theme1"
            get("updated_at") == "2026-03-02T00:00:00Z"
            get("url") == "https://example.com/api/sections/777.json"
        }
        verifyAll(deserialized) {
            it == section
        }
    }
}
