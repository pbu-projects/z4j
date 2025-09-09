package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class TicketTypeSpec extends Z4jSpec {

    @Unroll
    def "#type toString() should return #expectedString"() {
        expect:
        type.toString() == expectedString

        where:
        type                | expectedString
        TicketType.PROBLEM  | "problem"
        TicketType.INCIDENT | "incident"
        TicketType.QUESTION | "question"
        TicketType.TASK     | "task"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedType"() {
        expect:
        TicketType.fromValue(stringValue) == expectedType

        where:
        stringValue | expectedType
        "problem"   | TicketType.PROBLEM
        "incident"  | TicketType.INCIDENT
        "question"  | TicketType.QUESTION
        "task"      | TicketType.TASK
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TicketType.fromValue("invalid_type")

        then:
        thrown(IllegalArgumentException)
    }
}
