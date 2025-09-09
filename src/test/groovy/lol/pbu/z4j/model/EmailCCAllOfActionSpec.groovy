package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class EmailCCAllOfActionSpec extends Z4jSpec {

    @Unroll
    def "#action toString() should return #expectedString"() {
        expect:
        action.toString() == expectedString

        where:
        action                    | expectedString
        EmailCCAllOfAction.PUT    | "put"
        EmailCCAllOfAction.DELETE | "delete"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedAction"() {
        expect:
        EmailCCAllOfAction.fromValue(stringValue) == expectedAction

        where:
        stringValue | expectedAction
        "put"       | EmailCCAllOfAction.PUT
        "delete"    | EmailCCAllOfAction.DELETE
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        EmailCCAllOfAction.fromValue("invalid_action")

        then:
        thrown(IllegalArgumentException)
    }
}
