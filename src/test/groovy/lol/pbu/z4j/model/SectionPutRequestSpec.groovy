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

