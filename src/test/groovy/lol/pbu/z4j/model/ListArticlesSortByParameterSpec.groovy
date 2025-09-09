package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class ListArticlesSortByParameterSpec extends Z4jSpec {

    @Unroll
    def "#parameter toString() should return #expectedString"() {
        expect:
        parameter.toString() == expectedString

        where:
        parameter                              | expectedString
        ListArticlesSortByParameter.POSITION   | "position"
        ListArticlesSortByParameter.TITLE      | "title"
        ListArticlesSortByParameter.CREATED_AT | "created_at"
        ListArticlesSortByParameter.UPDATED_AT | "updated_at"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedParameter"() {
        expect:
        ListArticlesSortByParameter.fromValue(stringValue) == expectedParameter

        where:
        stringValue  | expectedParameter
        "position"   | ListArticlesSortByParameter.POSITION
        "title"      | ListArticlesSortByParameter.TITLE
        "created_at" | ListArticlesSortByParameter.CREATED_AT
        "updated_at" | ListArticlesSortByParameter.UPDATED_AT
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        ListArticlesSortByParameter.fromValue("invalid_parameter")

        then:
        thrown(IllegalArgumentException)
    }
}
