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

class TicketUpdateInputPrioritySpec extends Z4jSpec {

    @Unroll
    def "#priority toString() should return #expectedString"() {
        expect:
        priority.toString() == expectedString

        where:
        priority                         | expectedString
        TicketUpdateInputPriority.URGENT | "urgent"
        TicketUpdateInputPriority.HIGH   | "high"
        TicketUpdateInputPriority.NORMAL | "normal"
        TicketUpdateInputPriority.LOW    | "low"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedPriority"() {
        expect:
        TicketUpdateInputPriority.fromValue(stringValue) == expectedPriority

        where:
        stringValue | expectedPriority
        "urgent"    | TicketUpdateInputPriority.URGENT
        "high"      | TicketUpdateInputPriority.HIGH
        "normal"    | TicketUpdateInputPriority.NORMAL
        "low"       | TicketUpdateInputPriority.LOW
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TicketUpdateInputPriority.fromValue("invalid_priority")

        then:
        thrown(IllegalArgumentException)
    }
}

