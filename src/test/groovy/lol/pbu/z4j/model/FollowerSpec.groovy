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

class FollowerSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Follower with canonical constructor and builder"() {
        when:
        Follower direct = new Follower(EmailCCAllOfAction.PUT, "follower@example.com", "12345")
        Follower built = FollowerBuilder.builder()
                .action(EmailCCAllOfAction.PUT)
                .userEmail("follower@example.com")
                .userId("12345")
                .build()

        then:
        verifyAll(direct) {
            action() == EmailCCAllOfAction.PUT
            userEmail() == "follower@example.com"
            userId() == "12345"
        }
        verifyAll(built) {
            it == direct
            action() == EmailCCAllOfAction.PUT
            userEmail() == "follower@example.com"
            userId() == "12345"
        }
    }

    def "should update Follower using record withers"() {
        given:
        Follower original = new Follower(EmailCCAllOfAction.PUT, "follower1@example.com", "100")

        when:
        Follower updated = original
                .withAction(EmailCCAllOfAction.DELETE)
                .withUserEmail("follower2@example.com")
                .withUserId("200")

        then:
        verifyAll(original) {
            action() == EmailCCAllOfAction.PUT
            userEmail() == "follower1@example.com"
            userId() == "100"
        }
        verifyAll(updated) {
            action() == EmailCCAllOfAction.DELETE
            userEmail() == "follower2@example.com"
            userId() == "200"
        }
    }

    def "should round-trip serialize and deserialize Follower"() {
        given:
        Follower follower = new Follower(EmailCCAllOfAction.PUT, "watcher@example.com", "999")

        when:
        String json = objectMapper.writeValueAsString(follower)
        Map map = objectMapper.readValue(json, Map.class)
        Follower deserialized = objectMapper.readValue(json, Follower.class)

        then:
        verifyAll(map) {
            get("action") == "put"
            get("user_email") == "watcher@example.com"
            get("user_id") == "999"
        }
        verifyAll(deserialized) {
            it == follower
            action() == EmailCCAllOfAction.PUT
            userEmail() == "watcher@example.com"
            userId() == "999"
        }
    }

    def "should handle null components in Follower constructor, wither, and serde"() {
        when:
        Follower empty = new Follower(null, null, null)
        Follower built = FollowerBuilder.builder().build()
        Follower cleared = new Follower(EmailCCAllOfAction.PUT, "a@example.com", "1")
                .withAction(null)
                .withUserEmail(null)
                .withUserId(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        Follower deserialized = objectMapper.readValue(json, Follower.class)

        then:
        verifyAll(empty) {
            action() == null
            userEmail() == null
            userId() == null
        }
        verifyAll(built) {
            it == empty
            action() == null
            userEmail() == null
            userId() == null
        }
        verifyAll(cleared) {
            it == empty
            action() == null
            userEmail() == null
            userId() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            action() == null
            userEmail() == null
            userId() == null
        }
    }
}
