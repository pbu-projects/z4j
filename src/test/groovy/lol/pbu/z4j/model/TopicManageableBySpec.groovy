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
