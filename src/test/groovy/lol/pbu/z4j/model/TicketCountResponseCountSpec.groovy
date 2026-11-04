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

class TicketCountResponseCountSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create TicketCountResponseCount with canonical constructor and builder"() {
        given:
        ZonedDateTime timestamp = ZonedDateTime.parse("2026-11-03T12:00:00Z")

        when:
        TicketCountResponseCount direct = new TicketCountResponseCount(timestamp, 42L)
        TicketCountResponseCount built = TicketCountResponseCountBuilder.builder()
                .refreshedAt(timestamp)
                .value(42L)
                .build()

        then:
        verifyAll(direct) {
            refreshedAt() == timestamp
            value() == 42L
        }
        verifyAll(built) {
            it == direct
            refreshedAt() == timestamp
            value() == 42L
        }
    }

    def "should update TicketCountResponseCount using record withers"() {
        given:
        ZonedDateTime t1 = ZonedDateTime.parse("2026-11-03T01:00:00Z")
        ZonedDateTime t2 = ZonedDateTime.parse("2026-11-03T02:00:00Z")
        TicketCountResponseCount original = new TicketCountResponseCount(t1, 10L)

        when:
        TicketCountResponseCount updated = original
                .withRefreshedAt(t2)
                .withValue(20L)

        then:
        verifyAll(original) {
            refreshedAt() == t1
            value() == 10L
        }
        verifyAll(updated) {
            refreshedAt() == t2
            value() == 20L
        }
    }

    def "should round-trip serialize and deserialize TicketCountResponseCount"() {
        given:
        ZonedDateTime timestamp = ZonedDateTime.parse("2026-11-03T15:30:00Z")
        TicketCountResponseCount count = new TicketCountResponseCount(timestamp, 999L)

        when:
        String json = objectMapper.writeValueAsString(count)
        Map map = objectMapper.readValue(json, Map.class)
        TicketCountResponseCount deserialized = objectMapper.readValue(json, TicketCountResponseCount.class)

        then:
        verifyAll(map) {
            get("value") == 999
        }
        verifyAll(deserialized) {
            it == count
            refreshedAt() == timestamp
            value() == 999L
        }
    }

    def "should handle null components in TicketCountResponseCount constructor, wither, and serde"() {
        when:
        TicketCountResponseCount empty = new TicketCountResponseCount(null, null)
        TicketCountResponseCount built = TicketCountResponseCountBuilder.builder().build()
        ZonedDateTime now = ZonedDateTime.now()
        TicketCountResponseCount cleared = new TicketCountResponseCount(now, 1L)
                .withRefreshedAt(null)
                .withValue(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        TicketCountResponseCount deserialized = objectMapper.readValue(json, TicketCountResponseCount.class)

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
