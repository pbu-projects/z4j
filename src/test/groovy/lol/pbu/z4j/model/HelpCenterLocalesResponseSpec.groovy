package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class HelpCenterLocalesResponseSpec extends Z4jSpec {

    @Unroll
    def "should add locales item"() {
        given:
        def helpCenterLocalesResponse = new HelpCenterLocalesResponse()
        helpCenterLocalesResponse.locales == null
        def locale = faker.nation().language()

        when:
        helpCenterLocalesResponse.addLocalesItem(locale)

        then:
        helpCenterLocalesResponse.locales.size() == 1
        helpCenterLocalesResponse.locales.getAt(0) == locale
    }

    @Unroll
    def "add locales item to existing list"() {
        given:
        def existingLocale = faker.nation().language()
        def helpCenterLocalesResponse = new HelpCenterLocalesResponse(locales: [existingLocale])
        def newLocale = faker.nation().language()

        when:
        helpCenterLocalesResponse.addLocalesItem(newLocale)

        then:
        helpCenterLocalesResponse.locales.size() == 2
        helpCenterLocalesResponse.locales.containsAll([existingLocale, newLocale])
    }
}
