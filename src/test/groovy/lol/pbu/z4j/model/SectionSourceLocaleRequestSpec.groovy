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

class SectionSourceLocaleRequestSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create SectionSourceLocaleRequest with canonical constructor and builder"() {
        when:
        SectionSourceLocaleRequest direct = new SectionSourceLocaleRequest(LocaleAbbreviation.SPANISH)
        SectionSourceLocaleRequest built = SectionSourceLocaleRequestBuilder.builder()
                .sectionLocale(LocaleAbbreviation.SPANISH)
                .build()

        then:
        verifyAll(direct) {
            sectionLocale() == LocaleAbbreviation.SPANISH
        }
        verifyAll(built) {
            it == direct
            sectionLocale() == LocaleAbbreviation.SPANISH
        }
    }

    def "should update sectionLocale using record wither"() {
        given:
        SectionSourceLocaleRequest request = new SectionSourceLocaleRequest(LocaleAbbreviation.ENGLISH_UNITED_STATES)

        when:
        SectionSourceLocaleRequest updated = request.withSectionLocale(LocaleAbbreviation.FRENCH)

        then:
        verifyAll(request) {
            sectionLocale() == LocaleAbbreviation.ENGLISH_UNITED_STATES
        }
        verifyAll(updated) {
            sectionLocale() == LocaleAbbreviation.FRENCH
        }
    }

    def "should round-trip serialize and deserialize SectionSourceLocaleRequest"() {
        given:
        SectionSourceLocaleRequest request = new SectionSourceLocaleRequest(LocaleAbbreviation.SPANISH)

        when:
        String json = objectMapper.writeValueAsString(request)
        Map map = objectMapper.readValue(json, Map.class)
        SectionSourceLocaleRequest deserialized = objectMapper.readValue(json, SectionSourceLocaleRequest.class)

        then:
        verifyAll(map) {
            containsKey("section_locale")
            get("section_locale") == "es"
            size() == 1
        }
        verifyAll(deserialized) {
            it == request
            sectionLocale() == LocaleAbbreviation.SPANISH
        }
    }
}
