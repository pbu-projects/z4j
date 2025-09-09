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
