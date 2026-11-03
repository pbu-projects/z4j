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

class ContentTagCountResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ContentTagCountResponse with canonical constructor and builder"() {
        given:
        Long expectedValue = 123L
        String expectedRefreshedAt = "2026-11-03T00:00:00Z"

        when:
        ContentTagCountResponse direct = new ContentTagCountResponse(expectedValue, expectedRefreshedAt)
        ContentTagCountResponse built = ContentTagCountResponseBuilder.builder()
                .value(expectedValue)
                .refreshedAt(expectedRefreshedAt)
                .build()

        then:
        verifyAll(direct) {
            value == expectedValue
            refreshedAt == expectedRefreshedAt
        }
        verifyAll(built) {
            value == expectedValue
            refreshedAt == expectedRefreshedAt
        }
    }

    def "should update value and refreshedAt using record withers"() {
        given:
        ContentTagCountResponse response = new ContentTagCountResponse(10L, "2026-11-01T00:00:00Z")

        when:
        ContentTagCountResponse withNewValue = response.withValue(20L)
        ContentTagCountResponse withNewRefreshedAt = response.withRefreshedAt("2026-11-02T00:00:00Z")

        then:
        verifyAll(response) {
            value == 10L
            refreshedAt == "2026-11-01T00:00:00Z"
        }
        verifyAll(withNewValue) {
            value == 20L
            refreshedAt == "2026-11-01T00:00:00Z"
        }
        verifyAll(withNewRefreshedAt) {
            value == 10L
            refreshedAt == "2026-11-02T00:00:00Z"
        }
    }

    def "should round-trip serialize and deserialize ContentTagCountResponse"() {
        given:
        ContentTagCountResponse response = new ContentTagCountResponse(789L, "2026-11-03T12:00:00Z")

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagCountResponse deserialized = objectMapper.readValue(json, ContentTagCountResponse.class)

        then:
        verifyAll(map) {
            containsKey("value")
            containsKey("refreshed_at")
            get("value") == 789
            get("refreshed_at") == "2026-11-03T12:00:00Z"
        }
        verifyAll(deserialized) {
            it == response
            value == 789L
            refreshedAt == "2026-11-03T12:00:00Z"
        }
    }

    def "should handle null components in constructor and wither"() {
        when:
        ContentTagCountResponse response = new ContentTagCountResponse(null, null)
        ContentTagCountResponse updated = response.withValue(50L).withRefreshedAt("2026-11-05T00:00:00Z")
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ContentTagCountResponse deserialized = objectMapper.readValue(json, ContentTagCountResponse.class)

        then:
        verifyAll(response) {
            value() == null
            refreshedAt() == null
        }
        verifyAll(updated) {
            value() == 50L
            refreshedAt() == "2026-11-05T00:00:00Z"
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("value")
            !containsKey("refreshed_at")
        }
        verifyAll(deserialized) {
            it == response
            value() == null
            refreshedAt() == null
        }
    }
}
