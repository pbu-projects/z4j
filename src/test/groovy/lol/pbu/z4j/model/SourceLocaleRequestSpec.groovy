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

import io.micronaut.context.ApplicationContext
import io.micronaut.serde.ObjectMapper
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class SourceLocaleRequestSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext context

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        context = ApplicationContext.run()
        objectMapper = context.getBean(ObjectMapper)
    }

    def "can instantiate and use SourceLocaleRequest getters and setters"() {
        given:
        SourceLocaleRequest req = new SourceLocaleRequest(LocaleAbbreviation.ENGLISH_UNITED_STATES)

        expect:
        verifyAll(req) {
            articleLocale == LocaleAbbreviation.ENGLISH_UNITED_STATES
            categoryLocale == LocaleAbbreviation.ENGLISH_UNITED_STATES
            sectionLocale == LocaleAbbreviation.ENGLISH_UNITED_STATES
            sourceLocale == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }

        when:
        req.setSourceLocale(LocaleAbbreviation.FRENCH)

        then:
        verifyAll(req) {
            articleLocale == LocaleAbbreviation.FRENCH
            categoryLocale == LocaleAbbreviation.FRENCH
            sectionLocale == LocaleAbbreviation.FRENCH
            sourceLocale == LocaleAbbreviation.FRENCH
        }

        when:
        req.setArticleLocale(null)

        then:
        verifyAll(req) {
            articleLocale == null
            sourceLocale == LocaleAbbreviation.FRENCH
        }

        when:
        req.setCategoryLocale(null)

        then:
        verifyAll(req) {
            categoryLocale == null
            sourceLocale == LocaleAbbreviation.FRENCH
        }

        when:
        req.setSectionLocale(null)

        then:
        verifyAll(req) {
            sectionLocale == null
            sourceLocale == null
        }
    }

    def "subclasses inherit from SourceLocaleRequest"() {
        given:
        ArticleSourceLocaleRequest artReq = new ArticleSourceLocaleRequest(LocaleAbbreviation.FRENCH)
        CategorySourceLocaleRequest catReq = new CategorySourceLocaleRequest(LocaleAbbreviation.GERMAN)
        SectionSourceLocaleRequest secReq = new SectionSourceLocaleRequest(LocaleAbbreviation.SPANISH)

        expect:
        verifyAll {
            artReq instanceof SourceLocaleRequest
            catReq instanceof SourceLocaleRequest
            secReq instanceof SourceLocaleRequest
            artReq.articleLocale == LocaleAbbreviation.FRENCH
            artReq.sourceLocale == LocaleAbbreviation.FRENCH
            catReq.categoryLocale == LocaleAbbreviation.GERMAN
            catReq.sourceLocale == LocaleAbbreviation.GERMAN
            secReq.sectionLocale == LocaleAbbreviation.SPANISH
            secReq.sourceLocale == LocaleAbbreviation.SPANISH
        }
    }

    def "can serialize and deserialize ArticleSourceLocaleRequest"() {
        given:
        ArticleSourceLocaleRequest request = new ArticleSourceLocaleRequest(LocaleAbbreviation.FRENCH)

        when: "serializing to JSON"
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)

        then: "only article_locale is present in the output"
        verifyAll(map) {
            containsKey("article_locale")
            get("article_locale") == "fr"
            !containsKey("category_locale")
            !containsKey("section_locale")
            !containsKey("source_locale")
        }

        when: "deserializing back to ArticleSourceLocaleRequest"
        ArticleSourceLocaleRequest deserialized = objectMapper.readValue(json, ArticleSourceLocaleRequest.class)

        then:
        verifyAll(deserialized) {
            articleLocale == LocaleAbbreviation.FRENCH
            sourceLocale == LocaleAbbreviation.FRENCH
            categoryLocale == null
            sectionLocale == null
        }
    }

    def "can serialize and deserialize CategorySourceLocaleRequest and SectionSourceLocaleRequest"() {
        given:
        CategorySourceLocaleRequest catReq = new CategorySourceLocaleRequest(LocaleAbbreviation.GERMAN)
        SectionSourceLocaleRequest secReq = new SectionSourceLocaleRequest(LocaleAbbreviation.SPANISH)

        when: "serializing CategorySourceLocaleRequest"
        String catJson = objectMapper.writeValueAsString(catReq)
        Map catMap = objectMapper.readValue(catJson, Map.class)

        then:
        verifyAll(catMap) {
            containsKey("category_locale")
            get("category_locale") == "de"
            !containsKey("article_locale")
            !containsKey("section_locale")
            !containsKey("source_locale")
        }

        when: "deserializing CategorySourceLocaleRequest"
        CategorySourceLocaleRequest catDeserialized = objectMapper.readValue(catJson, CategorySourceLocaleRequest.class)

        then:
        verifyAll(catDeserialized) {
            categoryLocale == LocaleAbbreviation.GERMAN
            sourceLocale == LocaleAbbreviation.GERMAN
            articleLocale == null
            sectionLocale == null
        }

        when: "serializing SectionSourceLocaleRequest"
        String secJson = objectMapper.writeValueAsString(secReq)
        Map secMap = objectMapper.readValue(secJson, Map.class)

        then:
        verifyAll(secMap) {
            containsKey("section_locale")
            get("section_locale") == "es"
            !containsKey("article_locale")
            !containsKey("category_locale")
            !containsKey("source_locale")
        }

        when: "deserializing SectionSourceLocaleRequest"
        SectionSourceLocaleRequest secDeserialized = objectMapper.readValue(secJson, SectionSourceLocaleRequest.class)

        then:
        verifyAll(secDeserialized) {
            sectionLocale == LocaleAbbreviation.SPANISH
            sourceLocale == LocaleAbbreviation.SPANISH
            articleLocale == null
            categoryLocale == null
        }
    }
}
