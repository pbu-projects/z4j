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

class UserCountResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create UserCountResponse with canonical constructor and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.parse("2026-11-03T10:15:30Z")
        UserCountResponseCount expected = new UserCountResponseCount().setValue(120L).setRefreshedAt(now)

        when:
        UserCountResponse direct = new UserCountResponse(expected)
        UserCountResponse built = UserCountResponseBuilder.builder().count(expected).build()

        then:
        verifyAll(direct) {
            count == expected
            count.value == 120L
            count.refreshedAt == now
        }
        verifyAll(built) {
            count == expected
            count.value == 120L
            count.refreshedAt == now
        }
    }

    def "should update count using record wither"() {
        given:
        UserCountResponseCount original = new UserCountResponseCount().setValue(5L)
        UserCountResponseCount replacement = new UserCountResponseCount().setValue(15L)
        UserCountResponse response = new UserCountResponse(original)

        when:
        UserCountResponse updated = response.withCount(replacement)

        then:
        verifyAll(response) {
            count == original
            count.value == 5L
        }
        verifyAll(updated) {
            count == replacement
            count.value == 15L
        }
    }

    def "should round-trip serialize and deserialize UserCountResponse"() {
        given:
        ZonedDateTime timestamp = ZonedDateTime.parse("2026-11-03T09:30:00Z")
        UserCountResponseCount expected = new UserCountResponseCount().setValue(888L).setRefreshedAt(timestamp)
        UserCountResponse response = new UserCountResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UserCountResponse deserialized = objectMapper.readValue(json, UserCountResponse.class)

        then:
        verifyAll(map) {
            containsKey("count")
            get("count") instanceof Map
            ((Map) get("count")).value == 888
        }
        verifyAll(deserialized) {
            it == response
            count != null
            count.value == 888L
            count.refreshedAt == timestamp
        }
    }

    def "should handle null count component in constructor and wither"() {
        when:
        UserCountResponse response = new UserCountResponse(null)
        UserCountResponseCount replacement = new UserCountResponseCount().setValue(42L)
        UserCountResponse updated = response.withCount(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        UserCountResponse deserialized = objectMapper.readValue(json, UserCountResponse.class)

        then:
        verifyAll(response) {
            count() == null
        }
        verifyAll(updated) {
            count() == replacement
            count().value == 42L
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
