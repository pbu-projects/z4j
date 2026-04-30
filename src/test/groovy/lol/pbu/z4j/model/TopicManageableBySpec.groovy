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

class TopicManageableBySpec extends Z4jSpec {

    @Unroll
    def "#manageableBy toString() should return #expectedString"() {
        expect:
        manageableBy.toString() == expectedString

        where:
        manageableBy               | expectedString
        TopicManageableBy.STAFF    | "staff"
        TopicManageableBy.MANAGERS | "managers"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedManageableBy"() {
        expect:
        TopicManageableBy.fromValue(stringValue) == expectedManageableBy

        where:
        stringValue | expectedManageableBy
        "staff"     | TopicManageableBy.STAFF
        "managers"  | TopicManageableBy.MANAGERS
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TopicManageableBy.fromValue("invalid_manageable_by")

        then:
        thrown(IllegalArgumentException)
    }
}

