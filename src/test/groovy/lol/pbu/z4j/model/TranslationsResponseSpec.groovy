package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class TranslationsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add translations item"() {
        given:
        def translationsResponse = new TranslationsResponse()
        translationsResponse.translations == null
        def translation = new Translation(faker.lorem().word(), faker.lorem().sentence())

        when:
        translationsResponse.addTranslationsItem(translation)

        then:
        translationsResponse.translations.size() == 1
        translationsResponse.translations.getAt(0) == translation
    }

    @Unroll
    def "add translations item to existing list"() {
        given:
        def existingTranslation = new Translation(faker.lorem().word(), faker.lorem().sentence())
        def translationsResponse = new TranslationsResponse(translations: [existingTranslation])
        def newTranslation = new Translation(faker.lorem().word(), faker.lorem().sentence())

        when:
        translationsResponse.addTranslationsItem(newTranslation)

        then:
        translationsResponse.translations.size() == 2
        translationsResponse.translations.containsAll([existingTranslation, newTranslation])
    }
}
