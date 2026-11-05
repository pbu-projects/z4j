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

class CustomObjectLimitsResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create CustomObjectLimitsResponse with canonical constructor and builder"() {
        given:
        Integer expectedCount = 5
        Integer expectedLimit = 100

        when:
        CustomObjectLimitsResponse direct = new CustomObjectLimitsResponse(expectedCount, expectedLimit)
        CustomObjectLimitsResponse built = CustomObjectLimitsResponseBuilder.builder()
                .count(expectedCount)
                .limit(expectedLimit)
                .build()

        then:
        verifyAll(direct) {
            count == expectedCount
            limit == expectedLimit
        }
        verifyAll(built) {
            count == expectedCount
            limit == expectedLimit
        }
    }

    def "should update count and limit using record withers"() {
        given:
        CustomObjectLimitsResponse response = new CustomObjectLimitsResponse(2, 50)

        when:
        CustomObjectLimitsResponse withNewCount = response.withCount(4)
        CustomObjectLimitsResponse withNewLimit = response.withLimit(75)

        then:
        verifyAll(response) {
            count == 2
            limit == 50
        }
        verifyAll(withNewCount) {
            count == 4
            limit == 50
        }
        verifyAll(withNewLimit) {
            count == 2
            limit == 75
        }
    }

    def "should round-trip serialize and deserialize CustomObjectLimitsResponse"() {
        given:
        CustomObjectLimitsResponse response = new CustomObjectLimitsResponse(8, 250)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectLimitsResponse deserialized = objectMapper.readValue(json, CustomObjectLimitsResponse.class)

        then:
        verifyAll(map) {
            containsKey("count")
            containsKey("limit")
            get("count") == 8
            get("limit") == 250
        }
        verifyAll(deserialized) {
            it == response
            count == 8
            limit == 250
        }
    }

    def "should handle null components in constructor and wither"() {
        when:
        CustomObjectLimitsResponse response = new CustomObjectLimitsResponse(null, null)
        CustomObjectLimitsResponse updated = response.withCount(10).withLimit(500)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        CustomObjectLimitsResponse deserialized = objectMapper.readValue(json, CustomObjectLimitsResponse.class)

        then:
        verifyAll(response) {
            count() == null
            limit() == null
        }
        verifyAll(updated) {
            count() == 10
            limit() == 500
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("count")
            !containsKey("limit")
        }
        verifyAll(deserialized) {
            it == response
            count() == null
            limit() == null
        }
    }
}
