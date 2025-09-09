package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class SectionsResponseSpec extends Z4jSpec {

    @Unroll
    def "should add sections item"() {
        given:
        def sectionsResponse = new SectionsResponse()
        sectionsResponse.sections == null
        def section = new Section(faker.lorem().word(), faker.lorem().sentence())

        when:
        sectionsResponse.addSectionsItem(section)

        then:
        sectionsResponse.sections.size() == 1
        sectionsResponse.sections.getAt(0) == section
    }

    @Unroll
    def "add sections item to existing list"() {
        given:
        def existingSection = new Section(faker.lorem().word(), faker.lorem().sentence())
        def sectionsResponse = new SectionsResponse(sections: [existingSection])
        def newSection = new Section(faker.lorem().word(), faker.lorem().sentence())

        when:
        sectionsResponse.addSectionsItem(newSection)

        then:
        sectionsResponse.sections.size() == 2
        sectionsResponse.sections.containsAll([existingSection, newSection])
    }
}
