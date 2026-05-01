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

class TicketUpdateInputStatusSpec extends Z4jSpec {

    @Unroll
    def "#status toString() should return #expectedString"() {
        expect:
        status.toString() == expectedString

        where:
        status                          | expectedString
        TicketUpdateInputStatus.NEW     | "new"
        TicketUpdateInputStatus.OPEN    | "open"
        TicketUpdateInputStatus.PENDING | "pending"
        TicketUpdateInputStatus.SOLVED  | "solved"
        TicketUpdateInputStatus.CLOSED  | "closed"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedStatus"() {
        expect:
        TicketUpdateInputStatus.fromValue(stringValue) == expectedStatus

        where:
        stringValue | expectedStatus
        "new"       | TicketUpdateInputStatus.NEW
        "open"      | TicketUpdateInputStatus.OPEN
        "pending"   | TicketUpdateInputStatus.PENDING
        "solved"    | TicketUpdateInputStatus.SOLVED
        "closed"    | TicketUpdateInputStatus.CLOSED
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TicketUpdateInputStatus.fromValue("invalid_status")

        then:
        thrown(IllegalArgumentException)
    }
}

