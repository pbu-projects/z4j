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

class VoteResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create VoteResponse with canonical constructor and builder"() {
        given:
        Vote expected = new Vote().withId(101L).withValue(1).withUserId(202L)

        when:
        VoteResponse direct = new VoteResponse(expected)
        VoteResponse built = VoteResponseBuilder.builder().vote(expected).build()

        then:
        verifyAll(direct) {
            vote == expected
            vote.id == 101L
            vote.value == 1
            vote.userId == 202L
        }
        verifyAll(built) {
            vote == expected
            vote.id == 101L
            vote.value == 1
            vote.userId == 202L
        }
    }

    def "should update vote using record wither"() {
        given:
        Vote original = new Vote().withId(1L).withValue(1)
        Vote updatedVote = new Vote().withId(2L).withValue(-1)
        VoteResponse response = new VoteResponse(original)

        when:
        VoteResponse updated = response.withVote(updatedVote)

        then:
        verifyAll(response) {
            vote == original
            vote.value == 1
        }
        verifyAll(updated) {
            vote == updatedVote
            vote.value == -1
        }
    }

    def "should round-trip serialize and deserialize VoteResponse"() {
        given:
        Vote expected = new Vote().withId(555L).withValue(1).withUserId(999L)
        VoteResponse response = new VoteResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        VoteResponse deserialized = objectMapper.readValue(json, VoteResponse.class)

        then:
        verifyAll(map) {
            containsKey("vote")
            get("vote") instanceof Map
            ((Map) get("vote")).id == 555
            ((Map) get("vote")).value == 1
            ((Map) get("vote")).user_id == 999
        }
        verifyAll(deserialized) {
            it == response
            vote != null
            vote.id == 555L
            vote.value == 1
            vote.userId == 999L
        }
    }

    def "should handle null vote component in constructor and wither"() {
        when:
        VoteResponse response = new VoteResponse(null)
        Vote replacement = new Vote().withId(99L).withValue(-1)
        VoteResponse updated = response.withVote(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        VoteResponse deserialized = objectMapper.readValue(json, VoteResponse.class)

        then:
        verifyAll(response) {
            vote() == null
        }
        verifyAll(updated) {
            vote() == replacement
            vote().id == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("vote")
        }
        verifyAll(deserialized) {
            it == response
            vote() == null
        }
    }
}
