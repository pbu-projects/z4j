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

class CommunityPostSearchResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CommunityPostSearchResponse with canonical constructor and builder"() {
        given:
        Post item = new Post("Community Search Hit").setId(101L)
        List<Post> list = [item]

        when:
        CommunityPostSearchResponse direct = new CommunityPostSearchResponse(list)
        CommunityPostSearchResponse built = CommunityPostSearchResponseBuilder.builder()
                .results(list)
                .build()

        then:
        verifyAll(direct) {
            results() == list
            results().size() == 1
            results().first().id == 101L
            results().first().title == "Community Search Hit"
        }
        verifyAll(built) {
            it == direct
            results() == list
            results().size() == 1
            results().first().id == 101L
            results().first().title == "Community Search Hit"
        }
    }

    def "should update results using record wither and addResultsItem method"() {
        given:
        Post first = new Post("First Post").setId(1L)
        Post second = new Post("Second Post").setId(2L)
        CommunityPostSearchResponse response = new CommunityPostSearchResponse([first])

        when:
        CommunityPostSearchResponse withNewList = response.withResults([second])
        CommunityPostSearchResponse withAdded = response.addResultsItem(second)
        CommunityPostSearchResponse fromNull = new CommunityPostSearchResponse(null).addResultsItem(first)

        then:
        verifyAll(response) {
            results().size() == 1
            results().first().id == 1L
        }
        verifyAll(withNewList) {
            results().size() == 1
            results().first().id == 2L
        }
        verifyAll(withAdded) {
            results().size() == 2
            results().first().id == 1L
            results().last().id == 2L
        }
        verifyAll(fromNull) {
            results().size() == 1
            results().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize CommunityPostSearchResponse"() {
        given:
        Post item = new Post("Release Notes").setDetails("Version 2.0 is live").setId(555L)
        CommunityPostSearchResponse response = new CommunityPostSearchResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CommunityPostSearchResponse deserialized = objectMapper.readValue(json, CommunityPostSearchResponse.class)

        then:
        verifyAll(map) {
            containsKey("results")
            get("results") instanceof List
            ((List) get("results")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            results() != null
            results().size() == 1
            results().first().id == 555L
            results().first().title == "Release Notes"
        }
    }

    def "should handle null results component in constructor, wither, and serde"() {
        when:
        CommunityPostSearchResponse response = new CommunityPostSearchResponse(null)
        Post item = new Post("Maintenance Notice").setId(99L)
        CommunityPostSearchResponse updated = response.withResults([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CommunityPostSearchResponse deserialized = objectMapper.readValue(json, CommunityPostSearchResponse.class)

        then:
        verifyAll(response) {
            results() == null
        }
        verifyAll(updated) {
            results() != null
            results().size() == 1
            results().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("results")
        }
        verifyAll(deserialized) {
            it == response
            results() == null
        }
    }
}
