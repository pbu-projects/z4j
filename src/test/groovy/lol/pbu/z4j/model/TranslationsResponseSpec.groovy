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
