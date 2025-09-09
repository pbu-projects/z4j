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
