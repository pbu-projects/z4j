/*
 * Copyright 2026 Peanut Butter Unicorn, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

