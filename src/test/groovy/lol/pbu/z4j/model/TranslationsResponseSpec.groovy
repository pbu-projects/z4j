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

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class TranslationsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add translations item"(LocaleAbbreviation locale) {
        given:
        def translationsResponse = new TranslationsResponse()
        translationsResponse.translations == null
        def translation = new Translation(locale, faker.lorem().sentence())

        when:
        translationsResponse.addTranslationsItem(translation)

        then:
        translationsResponse.translations.size() == 1
        translationsResponse.translations[0] == translation

        where:
        locale << LocaleAbbreviation.values()
    }

    @Unroll
    def "add translations item to existing list"(LocaleAbbreviation locale) {
        given:
        def existingTranslation = new Translation(locale, faker.lorem().sentence())
        def translationsResponse = new TranslationsResponse(translations: [existingTranslation])
        LocaleAbbreviation update = LocaleAbbreviation.HEBREW
        if (locale == update) {
            update = LocaleAbbreviation.SIMPLIFIED_CHINESE
        }
        def newTranslation = new Translation(update, faker.lorem().sentence())

        when:
        translationsResponse.addTranslationsItem(newTranslation)

        then:
        translationsResponse.translations.size() == 2
        translationsResponse.translations.containsAll([existingTranslation, newTranslation])

        where:
        locale << LocaleAbbreviation.values()
    }
}

