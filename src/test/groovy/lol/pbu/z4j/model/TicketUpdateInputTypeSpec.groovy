package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class TicketUpdateInputTypeSpec extends Z4jSpec {

    @Unroll
    def "#type toString() should return #expectedString"() {
        expect:
        type.toString() == expectedString

        where:
        type                           | expectedString
        TicketUpdateInputType.PROBLEM  | "problem"
        TicketUpdateInputType.INCIDENT | "incident"
        TicketUpdateInputType.QUESTION | "question"
        TicketUpdateInputType.TASK     | "task"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedType"() {
        expect:
        TicketUpdateInputType.fromValue(stringValue) == expectedType

        where:
        stringValue | expectedType
        "problem"   | TicketUpdateInputType.PROBLEM
        "incident"  | TicketUpdateInputType.INCIDENT
        "question"  | TicketUpdateInputType.QUESTION
        "task"      | TicketUpdateInputType.TASK
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        TicketUpdateInputType.fromValue("invalid_type")

        then:
        thrown(IllegalArgumentException)
    }
}
