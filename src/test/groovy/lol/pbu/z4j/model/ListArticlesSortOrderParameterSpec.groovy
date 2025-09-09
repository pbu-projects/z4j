package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class ListArticlesSortOrderParameterSpec extends Z4jSpec {

    @Unroll
    def "#order toString() should return #expectedString"() {
        expect:
        order.toString() == expectedString

        where:
        order                               | expectedString
        ListArticlesSortOrderParameter.ASC  | "asc"
        ListArticlesSortOrderParameter.DESC | "desc"
    }

    @Unroll
    def "fromValue(#stringValue) should return #expectedOrder"() {
        expect:
        ListArticlesSortOrderParameter.fromValue(stringValue) == expectedOrder

        where:
        stringValue | expectedOrder
        "asc"       | ListArticlesSortOrderParameter.ASC
        "desc"      | ListArticlesSortOrderParameter.DESC
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        ListArticlesSortOrderParameter.fromValue("invalid_order")

        then:
        thrown(IllegalArgumentException)
    }
}
