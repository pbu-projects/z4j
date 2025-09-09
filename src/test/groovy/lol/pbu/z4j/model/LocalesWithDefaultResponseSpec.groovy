package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class LocalesWithDefaultResponseSpec extends Z4jSpec {

    @Unroll
    def "should add locales item"() {
        given:
        def localesWithDefaultResponse = new LocalesWithDefaultResponse()
        localesWithDefaultResponse.locales == null
        def locale = faker.nation().language()

        when:
        localesWithDefaultResponse.addLocalesItem(locale)

        then:
        localesWithDefaultResponse.locales.size() == 1
        localesWithDefaultResponse.locales[0] == locale
    }

    @Unroll
    def "add locales item to existing list"() {
        given:
        def existingLocale = faker.nation().language()
        def localesWithDefaultResponse = new LocalesWithDefaultResponse()
        localesWithDefaultResponse.locales = [existingLocale]
        def newLocale = faker.nation().language()

        when:
        localesWithDefaultResponse.addLocalesItem(newLocale)

        then:
        localesWithDefaultResponse.locales.size() == 2
        localesWithDefaultResponse.locales.containsAll([existingLocale, newLocale])
    }
}
