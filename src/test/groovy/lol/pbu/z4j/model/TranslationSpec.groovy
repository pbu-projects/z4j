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

class TranslationSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Translation with constructors and builder"() {
        when:
        Translation direct = new Translation(
                101L,
                "https://example.com/api/v2/help_center/articles/1/translations/fr.json",
                "https://example.com/hc/fr/articles/1",
                1L,
                "Article",
                LocaleAbbreviation.FRENCH,
                "Titre en francais",
                "<p>Corps du texte</p>",
                false,
                false,
                "2026-01-01T00:00:00Z",
                "2026-01-02T00:00:00Z",
                202L,
                303L
        )
        Translation noArg = new Translation()
        Translation built = TranslationBuilder.builder()
                .id(101L)
                .url("https://example.com/api/v2/help_center/articles/1/translations/fr.json")
                .htmlUrl("https://example.com/hc/fr/articles/1")
                .sourceId(1L)
                .sourceType("Article")
                .locale(LocaleAbbreviation.FRENCH)
                .title("Titre en francais")
                .body("<p>Corps du texte</p>")
                .outdated(false)
                .draft(false)
                .createdAt("2026-01-01T00:00:00Z")
                .updatedAt("2026-01-02T00:00:00Z")
                .updatedById(202L)
                .createdById(303L)
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
            url() == "https://example.com/api/v2/help_center/articles/1/translations/fr.json"
            htmlUrl() == "https://example.com/hc/fr/articles/1"
            sourceId() == 1L
            sourceType() == "Article"
            locale() == LocaleAbbreviation.FRENCH
            title() == "Titre en francais"
            body() == "<p>Corps du texte</p>"
            outdated() == false
            draft() == false
            createdAt() == "2026-01-01T00:00:00Z"
            updatedAt() == "2026-01-02T00:00:00Z"
            updatedById() == 202L
            createdById() == 303L
        }
        verifyAll(noArg) {
            id() == null
            url() == null
            htmlUrl() == null
            sourceId() == null
            sourceType() == null
            locale() == null
            title() == null
            body() == null
            outdated() == null
            draft() == null
            createdAt() == null
            updatedAt() == null
            updatedById() == null
            createdById() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update Translation with record withers"() {
        given:
        Translation initial = new Translation()

        when:
        Translation updated = initial
                .withId(404L)
                .withUrl("https://example.com/api/translations/404.json")
                .withHtmlUrl("https://example.com/hc/es/articles/2")
                .withSourceId(2L)
                .withSourceType("Section")
                .withLocale(LocaleAbbreviation.SPANISH)
                .withTitle("Titulo en espanol")
                .withBody("<p>Cuerpo</p>")
                .withOutdated(true)
                .withDraft(true)
                .withCreatedAt("2026-02-01T00:00:00Z")
                .withUpdatedAt("2026-02-02T00:00:00Z")
                .withUpdatedById(505L)
                .withCreatedById(606L)

        then:
        verifyAll(updated) {
            id() == 404L
            url() == "https://example.com/api/translations/404.json"
            htmlUrl() == "https://example.com/hc/es/articles/2"
            sourceId() == 2L
            sourceType() == "Section"
            locale() == LocaleAbbreviation.SPANISH
            title() == "Titulo en espanol"
            body() == "<p>Cuerpo</p>"
            outdated() == true
            draft() == true
            createdAt() == "2026-02-01T00:00:00Z"
            updatedAt() == "2026-02-02T00:00:00Z"
            updatedById() == 505L
            createdById() == 606L
        }
    }

    def "should round-trip serialize and deserialize Translation"() {
        given:
        Translation translation = new Translation(
                777L,
                "https://example.com/api/translations/777.json",
                "https://example.com/hc/de/articles/7",
                7L,
                "Category",
                LocaleAbbreviation.GERMAN,
                "Deutscher Titel",
                "<p>Deutscher Inhalt</p>",
                false,
                false,
                "2026-03-01T00:00:00Z",
                "2026-03-02T00:00:00Z",
                888L,
                999L
        )

        when:
        String json = objectMapper.writeValueAsString(translation)
        Map map = objectMapper.readValue(json, Map.class)
        Translation deserialized = objectMapper.readValue(json, Translation.class)

        then:
        verifyAll(map) {
            get("id") == 777
            get("url") == "https://example.com/api/translations/777.json"
            get("html_url") == "https://example.com/hc/de/articles/7"
            get("source_id") == 7
            get("source_type") == "Category"
            get("locale") == "de"
            get("title") == "Deutscher Titel"
            get("body") == "<p>Deutscher Inhalt</p>"
            get("outdated") == false
            get("draft") == false
            get("created_at") == "2026-03-01T00:00:00Z"
            get("updated_at") == "2026-03-02T00:00:00Z"
            get("updated_by_id") == 888
            get("created_by_id") == 999
        }
        verifyAll(deserialized) {
            it == translation
        }
    }
}
