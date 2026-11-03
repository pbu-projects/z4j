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

class ArticleSourceLocaleRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ArticleSourceLocaleRequest with canonical constructor and builder"() {
        when:
        ArticleSourceLocaleRequest direct = new ArticleSourceLocaleRequest(LocaleAbbreviation.FRENCH)
        ArticleSourceLocaleRequest built = ArticleSourceLocaleRequestBuilder.builder()
                .articleLocale(LocaleAbbreviation.FRENCH)
                .build()

        then:
        verifyAll(direct) {
            articleLocale() == LocaleAbbreviation.FRENCH
        }
        verifyAll(built) {
            it == direct
            articleLocale() == LocaleAbbreviation.FRENCH
        }
    }

    def "should update articleLocale using record wither"() {
        given:
        ArticleSourceLocaleRequest request = new ArticleSourceLocaleRequest(LocaleAbbreviation.ENGLISH_UNITED_STATES)

        when:
        ArticleSourceLocaleRequest updated = request.withArticleLocale(LocaleAbbreviation.GERMAN)

        then:
        verifyAll(request) {
            articleLocale() == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
        verifyAll(updated) {
            articleLocale() == LocaleAbbreviation.GERMAN
        }
    }

    def "should round-trip serialize and deserialize ArticleSourceLocaleRequest"() {
        given:
        ArticleSourceLocaleRequest request = new ArticleSourceLocaleRequest(LocaleAbbreviation.FRENCH)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        ArticleSourceLocaleRequest deserialized = objectMapper.readValue(json, ArticleSourceLocaleRequest.class)

        then:
        verifyAll(map) {
            containsKey("article_locale")
            get("article_locale") == "fr"
            size() == 1
        }
        verifyAll(deserialized) {
            it == request
            articleLocale() == LocaleAbbreviation.FRENCH
        }
    }
}
