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

class SearchResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create SearchResponse with canonical constructor and builder"() {
        given:
        SearchResult result = new SearchResult().withId(10L).withName("Result 1")
        User user = new User().withId(20L).withName("User 1")
        Organization org = new Organization().withId(30L)
        Group group = new Group()

        when:
        SearchResponse direct = new SearchResponse(
                1,
                "type:ticket",
                "https://example.zendesk.com/api/v2/search.json?page=2",
                "https://example.zendesk.com/api/v2/search.json?page=1",
                [result],
                [user],
                [org],
                [group]
        )
        SearchResponse built = SearchResponseBuilder.builder()
                .count(1)
                .facets("type:ticket")
                .nextPage("https://example.zendesk.com/api/v2/search.json?page=2")
                .previousPage("https://example.zendesk.com/api/v2/search.json?page=1")
                .results([result])
                .users([user])
                .organizations([org])
                .groups([group])
                .build()

        then:
        verifyAll(direct) {
            count() == 1
            facets() == "type:ticket"
            nextPage() == "https://example.zendesk.com/api/v2/search.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/search.json?page=1"
            results() == [result]
            users() == [user]
            organizations() == [org]
            groups() == [group]
        }
        verifyAll(built) {
            it == direct
            count() == 1
            facets() == "type:ticket"
            nextPage() == "https://example.zendesk.com/api/v2/search.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/search.json?page=1"
            results() == [result]
            users() == [user]
            organizations() == [org]
            groups() == [group]
        }
    }

    def "should update SearchResponse using record withers"() {
        given:
        SearchResult r1 = new SearchResult().withId(1L)
        SearchResult r2 = new SearchResult().withId(2L)
        User u1 = new User().withId(11L)
        User u2 = new User().withId(12L)
        Organization o1 = new Organization().withId(21L)
        Organization o2 = new Organization().withId(22L)
        Group g1 = new Group()
        Group g2 = new Group()
        SearchResponse response = new SearchResponse(1, "f1", "n1", "p1", [r1], [u1], [o1], [g1])

        when:
        SearchResponse updated = response
                .withCount(2)
                .withFacets("f2")
                .withNextPage("n2")
                .withPreviousPage("p2")
                .withResults([r2])
                .withUsers([u2])
                .withOrganizations([o2])
                .withGroups([g2])

        then:
        verifyAll(response) {
            count() == 1
            facets() == "f1"
            nextPage() == "n1"
            previousPage() == "p1"
            results() == [r1]
            users() == [u1]
            organizations() == [o1]
            groups() == [g1]
        }
        verifyAll(updated) {
            count() == 2
            facets() == "f2"
            nextPage() == "n2"
            previousPage() == "p2"
            results() == [r2]
            users() == [u2]
            organizations() == [o2]
            groups() == [g2]
        }
    }

    def "should round-trip serialize and deserialize SearchResponse"() {
        given:
        SearchResult result = new SearchResult().withId(100L).withName("Ticket Search Result")
        User user = new User().withId(200L).withName("Sideloaded User")
        Organization org = new Organization().withId(300L)
        Group group = new Group()
        SearchResponse response = new SearchResponse(
                1,
                "facets_str",
                "https://example.zendesk.com/api/v2/search.json?page=2",
                "https://example.zendesk.com/api/v2/search.json?page=1",
                [result],
                [user],
                [org],
                [group]
        )

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        SearchResponse deserialized = objectMapper.readValue(json, SearchResponse.class)

        then:
        verifyAll(map) {
            containsKey("count")
            containsKey("facets")
            containsKey("next_page")
            containsKey("previous_page")
            containsKey("results")
            containsKey("users")
            containsKey("organizations")
            containsKey("groups")
        }
        verifyAll(deserialized) {
            count() == 1
            facets() == "facets_str"
            nextPage() == "https://example.zendesk.com/api/v2/search.json?page=2"
            previousPage() == "https://example.zendesk.com/api/v2/search.json?page=1"
            results().size() == 1
            results().first().id == 100L
            users().size() == 1
            users().first().id == 200L
            organizations().size() == 1
            organizations().first().id == 300L
            groups().size() == 1
        }
    }

    def "should handle null components in SearchResponse"() {
        when:
        SearchResponse empty = new SearchResponse(null, null, null, null, null, null, null, null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        SearchResponse deserialized = objectMapper.readValue(json, SearchResponse.class)

        then:
        verifyAll(empty) {
            count() == null
            facets() == null
            nextPage() == null
            previousPage() == null
            results() == null
            users() == null
            organizations() == null
            groups() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            count() == null
            facets() == null
            nextPage() == null
            previousPage() == null
            results() == null
            users() == null
            organizations() == null
            groups() == null
        }
    }
}
