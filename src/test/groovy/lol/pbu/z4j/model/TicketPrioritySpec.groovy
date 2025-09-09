package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class TicketPrioritySpec extends Z4jSpec {

    @Unroll
    def "#priority toString() should return #expectedString"() {
        expect:
        priority.toString() == expectedString

        where:
        priority              | expectedString
        TicketPriority.URGENT | "urgent"
        TicketPriority.HIGH   | "high"
        TicketPriority.NORMAL | "normal"
        TicketPriority.LOW    | "low"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedPriority"() {
        expect:
        TicketPriority.fromValue(stringValue) == expectedPriority

        where:
        stringValue | expectedPriority
        "urgent"    | TicketPriority.URGENT
        "high"      | TicketPriority.HIGH
        "normal"    | TicketPriority.NORMAL
        "low"       | TicketPriority.LOW
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TicketPriority.fromValue("invalid_priority")

        then:
        thrown(IllegalArgumentException)
    }
}
