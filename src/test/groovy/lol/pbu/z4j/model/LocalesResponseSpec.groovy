package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class LocalesResponseSpec extends Z4jSpec {

    @Unroll
    def "should add locales item"() {
        given:
        def localesResponse = new LocalesResponse()
        localesResponse.locales == null
        def locale = new Locale(name: faker.nation().language())

        when:
        localesResponse.addLocalesItem(locale)

        then:
        localesResponse.locales.size() == 1
        localesResponse.locales.getAt(0) == locale
    }

    @Unroll
    def "add locales item to existing list"() {
        given:
        def existingLocale = new Locale(name: faker.nation().language())
        def localesResponse = new LocalesResponse(locales: [existingLocale])
        def newLocale = new Locale(name: faker.nation().language())

        when:
        localesResponse.addLocalesItem(newLocale)

        then:
        localesResponse.locales.size() == 2
        localesResponse.locales.containsAll([existingLocale, newLocale])
    }
}
