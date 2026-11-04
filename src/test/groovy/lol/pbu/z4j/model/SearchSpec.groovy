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
import spock.lang.Shared
import spock.lang.Specification

class SearchSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Search with constructors and builder"() {
        given:
        Post post = new Post("Search post").withId(101L)
        PostComment comment = new PostComment("Search comment").withId(201L)
        List<SearchResultsInner> list = [post, comment]

        when:
        Search direct = new Search(list)
        Search noArg = new Search()
        Search built = SearchBuilder.builder()
                .results(list)
                .build()

        then:
        verifyAll(direct) {
            results() == list
            results().size() == 2
        }
        verifyAll(noArg) {
            results() != null
            results().isEmpty()
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update Search using record wither and addResultsItem"() {
        given:
        Post post1 = new Post("First hit").withId(1L)
        Post post2 = new Post("Second hit").withId(2L)
        Search search = new Search([post1])
        Search searchNull = new Search(null)

        when:
        Search updatedWither = search.withResults([post2])
        Search updatedAdded = search.addResultsItem(post2)
        Search updatedFromNull = searchNull.addResultsItem(post1)

        then:
        verifyAll(search) {
            results().size() == 1
            results().first() == post1
        }
        verifyAll(updatedWither) {
            results().size() == 1
            results().first() == post2
        }
        verifyAll(updatedAdded) {
            results().size() == 2
            results().containsAll([post1, post2])
        }
        verifyAll(updatedFromNull) {
            results().size() == 1
            results().first() == post1
        }
    }

    def "should serialize Search to JSON"() {
        given:
        Post post = new Post("Search Hit").withId(555L)
        Search search = new Search([post])

        when:
        String json = objectMapper.writeValueAsString(search)
        Map map = objectMapper.readValue(json, Map.class)

        then:
        verifyAll(map) {
            containsKey("results")
            get("results") instanceof List
            ((List) get("results")).size() == 1
        }
    }
}
