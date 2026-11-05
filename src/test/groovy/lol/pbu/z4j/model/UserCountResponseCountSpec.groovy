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

import java.time.ZonedDateTime

class UserCountResponseCountSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create UserCountResponseCount with canonical constructor and builder"() {
        given:
        ZonedDateTime timestamp = ZonedDateTime.parse("2026-11-03T14:00:00Z")

        when:
        UserCountResponseCount direct = new UserCountResponseCount(timestamp, 150L)
        UserCountResponseCount built = UserCountResponseCountBuilder.builder()
                .refreshedAt(timestamp)
                .value(150L)
                .build()

        then:
        verifyAll(direct) {
            refreshedAt() == timestamp
            value() == 150L
        }
        verifyAll(built) {
            it == direct
            refreshedAt() == timestamp
            value() == 150L
        }
    }

    def "should update UserCountResponseCount using record withers"() {
        given:
        ZonedDateTime t1 = ZonedDateTime.parse("2026-11-03T03:00:00Z")
        ZonedDateTime t2 = ZonedDateTime.parse("2026-11-03T04:00:00Z")
        UserCountResponseCount original = new UserCountResponseCount(t1, 50L)

        when:
        UserCountResponseCount updated = original
                .withRefreshedAt(t2)
                .withValue(75L)

        then:
        verifyAll(original) {
            refreshedAt() == t1
            value() == 50L
        }
        verifyAll(updated) {
            refreshedAt() == t2
            value() == 75L
        }
    }

    def "should round-trip serialize and deserialize UserCountResponseCount"() {
        given:
        ZonedDateTime timestamp = ZonedDateTime.parse("2026-11-03T16:45:00Z")
        UserCountResponseCount count = new UserCountResponseCount(timestamp, 300L)

        when:
        String json = objectMapper.writeValueAsString(count)
        Map map = objectMapper.readValue(json, Map.class)
        UserCountResponseCount deserialized = objectMapper.readValue(json, UserCountResponseCount.class)

        then:
        verifyAll(map) {
            get("value") == 300
        }
        verifyAll(deserialized) {
            it == count
            refreshedAt() == timestamp
            value() == 300L
        }
    }

    def "should handle null components in UserCountResponseCount constructor, wither, and serde"() {
        when:
        UserCountResponseCount empty = new UserCountResponseCount(null, null)
        UserCountResponseCount built = UserCountResponseCountBuilder.builder().build()
        ZonedDateTime now = ZonedDateTime.now()
        UserCountResponseCount cleared = new UserCountResponseCount(now, 1L)
                .withRefreshedAt(null)
                .withValue(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        UserCountResponseCount deserialized = objectMapper.readValue(json, UserCountResponseCount.class)

        then:
        verifyAll(empty) {
            refreshedAt() == null
            value() == null
        }
        verifyAll(built) {
            it == empty
            refreshedAt() == null
            value() == null
        }
        verifyAll(cleared) {
            it == empty
            refreshedAt() == null
            value() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            refreshedAt() == null
            value() == null
        }
    }
}
