package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class UnifiedSearchResultTypeSpec extends Z4jSpec {

    @Unroll
    def "#type toString() should return #expectedString"() {
        expect:
        type.toString() == expectedString

        where:
        type                                    | expectedString
        UnifiedSearchResultType.ARTICLE         | "ARTICLE"
        UnifiedSearchResultType.POST            | "POST"
        UnifiedSearchResultType.EXTERNAL_RECORD | "EXTERNAL_RECORD"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedType"() {
        expect:
        UnifiedSearchResultType.fromValue(stringValue) == expectedType

        where:
        stringValue       | expectedType
        "ARTICLE"         | UnifiedSearchResultType.ARTICLE
        "POST"            | UnifiedSearchResultType.POST
        "EXTERNAL_RECORD" | UnifiedSearchResultType.EXTERNAL_RECORD
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        UnifiedSearchResultType.fromValue("invalid_type")

        then:
        thrown(IllegalArgumentException)
    }
}
