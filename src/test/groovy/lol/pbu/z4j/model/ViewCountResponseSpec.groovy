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

class ViewCountResponseSpec extends Z4jSpec {

    @Shared
    ObjectMapper objectMapper

    void setupSpec() {
        objectMapper = adminCtx.getBean(ObjectMapper.class)
    }

    def "should create ViewCountResponse with canonical constructor and builder"() {
        given:
        ViewCount expected = new ViewCount().setViewId(101L).setValue(42L).setPretty("42").setFresh(true)

        when:
        ViewCountResponse direct = new ViewCountResponse(expected)
        ViewCountResponse built = ViewCountResponseBuilder.builder().viewCount(expected).build()

        then:
        verifyAll(direct) {
            viewCount == expected
            viewCount.viewId == 101L
            viewCount.value == 42L
            viewCount.pretty == "42"
            viewCount.fresh == true
        }
        verifyAll(built) {
            viewCount == expected
            viewCount.viewId == 101L
            viewCount.value == 42L
            viewCount.pretty == "42"
            viewCount.fresh == true
        }
    }

    def "should update viewCount using record wither"() {
        given:
        ViewCount original = new ViewCount().setViewId(1L).setValue(10L)
        ViewCount updatedCount = new ViewCount().setViewId(2L).setValue(20L)
        ViewCountResponse response = new ViewCountResponse(original)

        when:
        ViewCountResponse updated = response.withViewCount(updatedCount)

        then:
        verifyAll(response) {
            viewCount == original
            viewCount.value == 10L
        }
        verifyAll(updated) {
            viewCount == updatedCount
            viewCount.value == 20L
        }
    }

    def "should round-trip serialize and deserialize ViewCountResponse"() {
        given:
        ViewCount expected = new ViewCount().setViewId(555L).setValue(88L).setPretty("88").setFresh(false)
        ViewCountResponse response = new ViewCountResponse(expected)

        when:
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ViewCountResponse deserialized = objectMapper.readValue(json, ViewCountResponse.class)

        then:
        verifyAll(map) {
            containsKey("view_count")
            get("view_count") instanceof Map
            ((Map) get("view_count")).view_id == 555
            ((Map) get("view_count")).value == 88
        }
        verifyAll(deserialized) {
            it == response
            viewCount != null
            viewCount.viewId == 555L
            viewCount.value == 88L
            viewCount.pretty == "88"
            viewCount.fresh == false
        }
    }

    def "should handle null viewCount component in constructor and wither"() {
        when:
        ViewCountResponse response = new ViewCountResponse(null)
        ViewCount replacement = new ViewCount().setViewId(99L).setValue(0L)
        ViewCountResponse updated = response.withViewCount(replacement)
        String json = objectMapper.writeValueAsString(response)
        Map map = objectMapper.readValue(json, Map.class)
        ViewCountResponse deserialized = objectMapper.readValue(json, ViewCountResponse.class)

        then:
        verifyAll(response) {
            viewCount() == null
        }
        verifyAll(updated) {
            viewCount() == replacement
            viewCount().viewId == 99L
            viewCount().value == 0L
        }
        verifyAll(map) {
            isEmpty()
            !containsKey("view_count")
        }
        verifyAll(deserialized) {
            it == response
            viewCount() == null
        }
    }
}
