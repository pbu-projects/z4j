package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class SectionPutRequestSpec extends Z4jSpec {

    @Unroll
    def "should create SectionPutRequest with correct properties"() {
        given:
        def categoryId = faker.number().randomNumber()
        def description = faker.lorem().sentence()
        def name = faker.lorem().word()
        def parentSectionId = faker.number().randomNumber()
        def position = faker.number().randomNumber()
        def sorting = SectionPutRequestSectionSorting.MANUAL
        def themeTemplate = faker.lorem().word()

        def sectionPutRequestSection = new SectionPutRequestSection(
                categoryId: categoryId,
                description: description,
                name: name,
                parentSectionId: parentSectionId,
                position: position,
                sorting: sorting,
                themeTemplate: themeTemplate
        )

        when:
        def sectionPutRequest = new SectionPutRequest(sectionPutRequestSection)

        then:
        sectionPutRequest.section == sectionPutRequestSection
        sectionPutRequest.section.dump() == sectionPutRequestSection.dump()
    }
}
