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

class SectionPutRequestSectionSortingSpec extends Z4jSpec {

    @Unroll
    def "#sorting toString() should return #expectedString"() {
        expect:
        sorting.toString() == expectedString

        where:
        sorting                                       | expectedString
        SectionPutRequestSectionSorting.MANUAL        | "manual"
        SectionPutRequestSectionSorting.TITLE         | "title"
        SectionPutRequestSectionSorting.CREATION_ASC  | "creation_asc"
        SectionPutRequestSectionSorting.CREATION_DESC | "creation_desc"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedSorting"() {
        expect:
        SectionPutRequestSectionSorting.fromValue(stringValue) == expectedSorting

        where:
        stringValue     | expectedSorting
        "manual"        | SectionPutRequestSectionSorting.MANUAL
        "title"         | SectionPutRequestSectionSorting.TITLE
        "creation_asc"  | SectionPutRequestSectionSorting.CREATION_ASC
        "creation_desc" | SectionPutRequestSectionSorting.CREATION_DESC
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        SectionPutRequestSectionSorting.fromValue("invalid_sorting")

        then:
        thrown(IllegalArgumentException)
    }
}

