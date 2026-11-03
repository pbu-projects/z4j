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

class CategorySourceLocaleRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CategorySourceLocaleRequest with canonical constructor and builder"() {
        when:
        CategorySourceLocaleRequest direct = new CategorySourceLocaleRequest(LocaleAbbreviation.GERMAN)
        CategorySourceLocaleRequest built = CategorySourceLocaleRequestBuilder.builder()
                .categoryLocale(LocaleAbbreviation.GERMAN)
                .build()

        then:
        verifyAll(direct) {
            categoryLocale() == LocaleAbbreviation.GERMAN
        }
        verifyAll(built) {
            it == direct
            categoryLocale() == LocaleAbbreviation.GERMAN
        }
    }

    def "should update categoryLocale using record wither"() {
        given:
        CategorySourceLocaleRequest request = new CategorySourceLocaleRequest(LocaleAbbreviation.ENGLISH_UNITED_STATES)

        when:
        CategorySourceLocaleRequest updated = request.withCategoryLocale(LocaleAbbreviation.SPANISH)

        then:
        verifyAll(request) {
            categoryLocale() == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
        verifyAll(updated) {
            categoryLocale() == LocaleAbbreviation.SPANISH
        }
    }

    def "should round-trip serialize and deserialize CategorySourceLocaleRequest"() {
        given:
        CategorySourceLocaleRequest request = new CategorySourceLocaleRequest(LocaleAbbreviation.GERMAN)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        CategorySourceLocaleRequest deserialized = objectMapper.readValue(json, CategorySourceLocaleRequest.class)

        then:
        verifyAll(map) {
            containsKey("category_locale")
            get("category_locale") == "de"
            size() == 1
        }
        verifyAll(deserialized) {
            it == request
            categoryLocale() == LocaleAbbreviation.GERMAN
        }
    }
}
