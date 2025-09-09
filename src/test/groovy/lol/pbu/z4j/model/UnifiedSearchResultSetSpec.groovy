package lol.pbu.z4j.model

import lol.pbu.z4j.Z4jSpec
import spock.lang.Unroll

class UnifiedSearchResultSetSpec extends Z4jSpec {

    @Unroll
    def "should add results item"() {
        given:
        def unifiedSearchResultSet = new UnifiedSearchResultSet()
        unifiedSearchResultSet.results == null
        def unifiedSearchResult = new UnifiedSearchResult()

        when:
        unifiedSearchResultSet.addResultsItem(unifiedSearchResult)

        then:
        unifiedSearchResultSet.results.size() == 1
        unifiedSearchResultSet.results.getAt(0) == unifiedSearchResult
    }

    @Unroll
    def "add results item to existing list"() {
        given:
        def existingUnifiedSearchResult = new UnifiedSearchResult()
        def unifiedSearchResultSet = new UnifiedSearchResultSet(results: [existingUnifiedSearchResult])
        def newUnifiedSearchResult = new UnifiedSearchResult()

        when:
        unifiedSearchResultSet.addResultsItem(newUnifiedSearchResult)

        then:
        unifiedSearchResultSet.results.size() == 2
        unifiedSearchResultSet.results.containsAll([existingUnifiedSearchResult, newUnifiedSearchResult])
    }
}
