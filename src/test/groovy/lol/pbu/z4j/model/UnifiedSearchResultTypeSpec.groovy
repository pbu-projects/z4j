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

class UnifiedSearchResultTypeSpec extends Z4jSpec {

    @Unroll
    def "#type toString() should return #expectedString"() {
        expect:
        type.toString() == expectedString

        where:
        type                                    | expectedString
        UnifiedSearchResultType.ARTICLE         | "ARTICLE"
        UnifiedSearchResultType.POST            | "POST"
        UnifiedSearchResultType.EXTERNAL_RECORD | "EXTERNAL_RECORD"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedType"() {
        expect:
        UnifiedSearchResultType.fromValue(stringValue) == expectedType

        where:
        stringValue       | expectedType
        "ARTICLE"         | UnifiedSearchResultType.ARTICLE
        "POST"            | UnifiedSearchResultType.POST
        "EXTERNAL_RECORD" | UnifiedSearchResultType.EXTERNAL_RECORD
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        UnifiedSearchResultType.fromValue("invalid_type")

        then:
        thrown(IllegalArgumentException)
    }
}

