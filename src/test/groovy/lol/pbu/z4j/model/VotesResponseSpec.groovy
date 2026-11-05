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

class VotesResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create VotesResponse with canonical constructor and builder"() {
        given:
        Vote item = new Vote().setId(101L).setValue(1).setUserId(202L)
        List<Vote> list = [item]

        when:
        VotesResponse direct = new VotesResponse(list)
        VotesResponse built = VotesResponseBuilder.builder()
                .votes(list)
                .build()

        then:
        verifyAll(direct) {
            votes() == list
            votes().size() == 1
            votes().first().id == 101L
            votes().first().value == 1
            votes().first().userId == 202L
        }
        verifyAll(built) {
            votes() == list
            votes().size() == 1
            votes().first().id == 101L
            votes().first().value == 1
            votes().first().userId == 202L
        }
    }

    def "should update votes using record wither and addVotesItem method"() {
        given:
        Vote first = new Vote().setId(1L).setValue(1)
        Vote second = new Vote().setId(2L).setValue(-1)
        VotesResponse response = new VotesResponse([first])

        when:
        VotesResponse withNewList = response.withVotes([second])
        VotesResponse withAdded = response.addVotesItem(second)
        VotesResponse fromNull = new VotesResponse(null).addVotesItem(first)

        then:
        verifyAll(response) {
            votes().size() == 1
            votes().first().id == 1L
        }
        verifyAll(withNewList) {
            votes().size() == 1
            votes().first().id == 2L
        }
        verifyAll(withAdded) {
            votes().size() == 2
            votes().first().id == 1L
            votes().last().id == 2L
        }
        verifyAll(fromNull) {
            votes().size() == 1
            votes().first().id == 1L
        }
    }

    def "should round-trip serialize and deserialize VotesResponse"() {
        given:
        Vote item = new Vote().setId(555L).setValue(1).setUserId(999L)
        VotesResponse response = new VotesResponse([item])

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        VotesResponse deserialized = objectMapper.readValue(json, VotesResponse.class)

        then:
        verifyAll(map) {
            containsKey("votes")
            get("votes") instanceof List
            ((List) get("votes")).size() == 1
        }
        verifyAll(deserialized) {
            it == response
            votes() != null
            votes().size() == 1
            votes().first().id == 555L
            votes().first().value == 1
            votes().first().userId == 999L
        }
    }

    def "should handle null votes component in constructor, wither, and serde"() {
        when:
        VotesResponse response = new VotesResponse(null)
        Vote item = new Vote().setId(99L).setValue(-1)
        VotesResponse updated = response.withVotes([item])
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        VotesResponse deserialized = objectMapper.readValue(json, VotesResponse.class)

        then:
        verifyAll(response) {
            votes() == null
        }
        verifyAll(updated) {
            votes() != null
            votes().size() == 1
            votes().first().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("votes")
        }
        verifyAll(deserialized) {
            it == response
            votes() == null
        }
    }
}
