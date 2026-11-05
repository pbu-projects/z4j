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

import java.time.ZonedDateTime

class TicketCountResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create TicketCountResponse with canonical constructor and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.parse("2026-11-03T10:15:30Z")
        TicketCountResponseCount expected = TicketCountResponseCountBuilder.builder().value(350L).refreshedAt(now).build()

        when:
        TicketCountResponse direct = new TicketCountResponse(expected)
        TicketCountResponse built = TicketCountResponseBuilder.builder().count(expected).build()

        then:
        verifyAll(direct) {
            count == expected
            count.value == 350L
            count.refreshedAt == now
        }
        verifyAll(built) {
            count == expected
            count.value == 350L
            count.refreshedAt == now
        }
    }

    def "should update count using record wither"() {
        given:
        TicketCountResponseCount original = TicketCountResponseCountBuilder.builder().value(10L).build()
        TicketCountResponseCount replacement = TicketCountResponseCountBuilder.builder().value(20L).build()
        TicketCountResponse response = new TicketCountResponse(original)

        when:
        TicketCountResponse updated = response.withCount(replacement)

        then:
        verifyAll(response) {
            count == original
            count.value == 10L
        }
        verifyAll(updated) {
            count == replacement
            count.value == 20L
        }
    }

    def "should round-trip serialize and deserialize TicketCountResponse"() {
        given:
        ZonedDateTime timestamp = ZonedDateTime.parse("2026-11-03T08:00:00Z")
        TicketCountResponseCount expected = TicketCountResponseCountBuilder.builder().value(777L).refreshedAt(timestamp).build()
        TicketCountResponse response = new TicketCountResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketCountResponse deserialized = objectMapper.readValue(json, TicketCountResponse.class)

        then:
        verifyAll(map) {
            containsKey("count")
            get("count") instanceof Map
            ((Map) get("count")).value == 777
        }
        verifyAll(deserialized) {
            it == response
            count != null
            count.value == 777L
            count.refreshedAt == timestamp
        }
    }

    def "should handle null count component in constructor and wither"() {
        when:
        TicketCountResponse response = new TicketCountResponse(null)
        TicketCountResponseCount replacement = TicketCountResponseCountBuilder.builder().value(99L).build()
        TicketCountResponse updated = response.withCount(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        TicketCountResponse deserialized = objectMapper.readValue(json, TicketCountResponse.class)

        then:
        verifyAll(response) {
            count() == null
        }
        verifyAll(updated) {
            count() == replacement
            count().value == 99L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("count")
        }
        verifyAll(deserialized) {
            it == response
            count() == null
        }
    }
}
