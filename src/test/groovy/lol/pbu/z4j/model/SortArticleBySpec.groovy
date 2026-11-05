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

import io.micronaut.serde.ObjectMapper
import lol.pbu.z4j.Z4jSpec
import spock.lang.Shared
import spock.lang.Unroll

class SortArticleBySpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    @Unroll
    def "#sortBy toString() and getValue() should return #expectedString"() {
        expect:
        verifyAll {
            sortBy.toString() == expectedString
            sortBy.getValue() == expectedString
        }

        where:
        sortBy                   | expectedString
        SortArticleBy.POSITION   | "position"
        SortArticleBy.TITLE      | "title"
        SortArticleBy.CREATED_AT | "created_at"
        SortArticleBy.UPDATED_AT | "updated_at"
        SortArticleBy.EDITED_AT  | "edited_at"
    }

    @Unroll
    def "fromValue(#stringValue) and JSON round-trip should return #expectedSortBy"() {
        when:
        String json = objectMapper.writeValueAsString(expectedSortBy)
        SortArticleBy deserialized = objectMapper.readValue(json, SortArticleBy.class)

        then:
        verifyAll {
            SortArticleBy.fromValue(stringValue) == expectedSortBy
            json == "\"${stringValue}\""
            deserialized == expectedSortBy
        }

        where:
        stringValue  | expectedSortBy
        "position"   | SortArticleBy.POSITION
        "title"      | SortArticleBy.TITLE
        "created_at" | SortArticleBy.CREATED_AT
        "updated_at" | SortArticleBy.UPDATED_AT
        "edited_at"  | SortArticleBy.EDITED_AT
    }

    def "fromValue() should throw IllegalArgumentException for invalid string"() {
        when:
        SortArticleBy.fromValue("invalid_sort")

        then:
        thrown(IllegalArgumentException)
    }
}
