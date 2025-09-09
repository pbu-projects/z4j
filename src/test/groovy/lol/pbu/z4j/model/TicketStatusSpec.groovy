package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class TicketStatusSpec extends Z4jSpec {

    @Unroll
    def "#status toString() should return #expectedString"() {
        expect:
        status.toString() == expectedString

        where:
        status               | expectedString
        TicketStatus.NEW     | "new"
        TicketStatus.OPEN    | "open"
        TicketStatus.PENDING | "pending"
        TicketStatus.SOLVED  | "solved"
        TicketStatus.CLOSED  | "closed"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedStatus"() {
        expect:
        TicketStatus.fromValue(stringValue) == expectedStatus

        where:
        stringValue | expectedStatus
        "new"       | TicketStatus.NEW
        "open"      | TicketStatus.OPEN
        "pending"   | TicketStatus.PENDING
        "solved"    | TicketStatus.SOLVED
        "closed"    | TicketStatus.CLOSED
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TicketStatus.fromValue("invalid_status")

        then:
        thrown(IllegalArgumentException)
    }
}
