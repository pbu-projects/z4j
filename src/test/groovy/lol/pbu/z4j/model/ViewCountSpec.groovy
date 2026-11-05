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

class ViewCountSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create ViewCount with canonical constructor and builder"() {
        when:
        ViewCount direct = new ViewCount("https://example.zendesk.com/api/v2/views/42/count.json", 42L, 100L, "100+", true)
        ViewCount built = ViewCountBuilder.builder()
                .url("https://example.zendesk.com/api/v2/views/42/count.json")
                .viewId(42L)
                .value(100L)
                .pretty("100+")
                .fresh(true)
                .build()

        then:
        verifyAll(direct) {
            url() == "https://example.zendesk.com/api/v2/views/42/count.json"
            viewId() == 42L
            value() == 100L
            pretty() == "100+"
            fresh() == true
        }
        verifyAll(built) {
            it == direct
            url() == "https://example.zendesk.com/api/v2/views/42/count.json"
            viewId() == 42L
            value() == 100L
            pretty() == "100+"
            fresh() == true
        }
    }

    def "should update ViewCount using record withers"() {
        given:
        ViewCount original = new ViewCount("url1", 1L, 10L, "10", true)

        when:
        ViewCount updated = original
                .withUrl("url2")
                .withViewId(2L)
                .withValue(20L)
                .withPretty("20")
                .withFresh(false)

        then:
        verifyAll(original) {
            url() == "url1"
            viewId() == 1L
            value() == 10L
            pretty() == "10"
            fresh() == true
        }
        verifyAll(updated) {
            url() == "url2"
            viewId() == 2L
            value() == 20L
            pretty() == "20"
            fresh() == false
        }
    }

    def "should round-trip serialize and deserialize ViewCount"() {
        given:
        ViewCount viewCount = new ViewCount("https://example.com/count", 99L, 50L, "50", true)

        when:
        String json = objectMapper.writeValueAsString(viewCount)
        Map map = objectMapper.readValue(json, Map.class)
        ViewCount deserialized = objectMapper.readValue(json, ViewCount.class)

        then:
        verifyAll(map) {
            get("url") == "https://example.com/count"
            get("view_id") == 99
            get("value") == 50
            get("pretty") == "50"
            get("fresh") == true
        }
        verifyAll(deserialized) {
            it == viewCount
            url() == "https://example.com/count"
            viewId() == 99L
            value() == 50L
            pretty() == "50"
            fresh() == true
        }
    }

    def "should handle null components in ViewCount constructor, wither, and serde"() {
        when:
        ViewCount empty = new ViewCount(null, null, null, null, null)
        ViewCount built = ViewCountBuilder.builder().build()
        ViewCount cleared = new ViewCount("url", 1L, 1L, "1", true)
                .withUrl(null)
                .withViewId(null)
                .withValue(null)
                .withPretty(null)
                .withFresh(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        ViewCount deserialized = objectMapper.readValue(json, ViewCount.class)

        then:
        verifyAll(empty) {
            url() == null
            viewId() == null
            value() == null
            pretty() == null
            fresh() == null
        }
        verifyAll(built) {
            it == empty
            url() == null
            viewId() == null
            value() == null
            pretty() == null
            fresh() == null
        }
        verifyAll(cleared) {
            it == empty
            url() == null
            viewId() == null
            value() == null
            pretty() == null
            fresh() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            url() == null
            viewId() == null
            value() == null
            pretty() == null
            fresh() == null
        }
    }
}
