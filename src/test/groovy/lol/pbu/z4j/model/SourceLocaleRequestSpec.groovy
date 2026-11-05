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
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared

class SourceLocaleRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create SourceLocaleRequest with canonical constructor, convenience constructors, and builder"() {
        when:
        SourceLocaleRequest canonical = new SourceLocaleRequest(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                LocaleAbbreviation.FRENCH,
                LocaleAbbreviation.GERMAN
        )
        SourceLocaleRequest twoArg = new SourceLocaleRequest(
                LocaleAbbreviation.FRENCH,
                LocaleAbbreviation.GERMAN
        )
        SourceLocaleRequest singleArg = new SourceLocaleRequest(LocaleAbbreviation.SPANISH)
        SourceLocaleRequest built = SourceLocaleRequestBuilder.builder()
                .articleLocale(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .categoryLocale(LocaleAbbreviation.FRENCH)
                .sectionLocale(LocaleAbbreviation.GERMAN)
                .build()

        then:
        verifyAll(canonical) {
            articleLocale() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            categoryLocale() == LocaleAbbreviation.FRENCH
            sectionLocale() == LocaleAbbreviation.GERMAN
            sourceLocale() == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
        verifyAll(twoArg) {
            articleLocale() == null
            categoryLocale() == LocaleAbbreviation.FRENCH
            sectionLocale() == LocaleAbbreviation.GERMAN
            sourceLocale() == LocaleAbbreviation.FRENCH
        }
        verifyAll(singleArg) {
            articleLocale() == LocaleAbbreviation.SPANISH
            categoryLocale() == LocaleAbbreviation.SPANISH
            sectionLocale() == LocaleAbbreviation.SPANISH
            sourceLocale() == LocaleAbbreviation.SPANISH
        }
        verifyAll(built) {
            it == canonical
            articleLocale() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            categoryLocale() == LocaleAbbreviation.FRENCH
            sectionLocale() == LocaleAbbreviation.GERMAN
        }
    }

    def "should evaluate sourceLocale fallback order and update via withers and withSourceLocale"() {
        given:
        SourceLocaleRequest req = new SourceLocaleRequest(LocaleAbbreviation.ENGLISH_UNITED_STATES)

        when:
        SourceLocaleRequest updatedAll = req.withSourceLocale(LocaleAbbreviation.FRENCH)
        SourceLocaleRequest withoutArticle = updatedAll.withArticleLocale(null)
        SourceLocaleRequest withoutCategory = withoutArticle.withCategoryLocale(null)
        SourceLocaleRequest withoutSection = withoutCategory.withSectionLocale(null)

        then:
        verifyAll(updatedAll) {
            articleLocale() == LocaleAbbreviation.FRENCH
            categoryLocale() == LocaleAbbreviation.FRENCH
            sectionLocale() == LocaleAbbreviation.FRENCH
            sourceLocale() == LocaleAbbreviation.FRENCH
        }
        verifyAll(withoutArticle) {
            articleLocale() == null
            categoryLocale() == LocaleAbbreviation.FRENCH
            sectionLocale() == LocaleAbbreviation.FRENCH
            sourceLocale() == LocaleAbbreviation.FRENCH
        }
        verifyAll(withoutCategory) {
            articleLocale() == null
            categoryLocale() == null
            sectionLocale() == LocaleAbbreviation.FRENCH
            sourceLocale() == LocaleAbbreviation.FRENCH
        }
        verifyAll(withoutSection) {
            articleLocale() == null
            categoryLocale() == null
            sectionLocale() == null
            sourceLocale() == null
        }
    }

    def "should round-trip serialize and deserialize SourceLocaleRequest"() {
        given:
        SourceLocaleRequest request = new SourceLocaleRequest(
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                LocaleAbbreviation.FRENCH,
                LocaleAbbreviation.GERMAN
        )

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        SourceLocaleRequest deserialized = objectMapper.readValue(json, SourceLocaleRequest.class)

        then:
        verifyAll(map) {
            get("article_locale") == "en-us"
            get("category_locale") == "fr"
            get("section_locale") == "de"
            !containsKey("source_locale")
            !containsKey("sourceLocale")
        }
        verifyAll(deserialized) {
            it == request
            articleLocale() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            categoryLocale() == LocaleAbbreviation.FRENCH
            sectionLocale() == LocaleAbbreviation.GERMAN
            sourceLocale() == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
    }

    def "should omit null fields during SourceLocaleRequest serialization"() {
        given:
        SourceLocaleRequest request = new SourceLocaleRequest(null, LocaleAbbreviation.GERMAN, null)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        SourceLocaleRequest deserialized = objectMapper.readValue(json, SourceLocaleRequest.class)

        then:
        verifyAll(map) {
            !containsKey("article_locale")
            get("category_locale") == "de"
            !containsKey("section_locale")
        }
        verifyAll(deserialized) {
            it == request
            articleLocale() == null
            categoryLocale() == LocaleAbbreviation.GERMAN
            sectionLocale() == null
            sourceLocale() == LocaleAbbreviation.GERMAN
        }
    }
}
