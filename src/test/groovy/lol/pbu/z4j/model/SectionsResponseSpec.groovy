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

