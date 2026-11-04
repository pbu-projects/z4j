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

class MetaSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Meta with canonical constructor and builder"() {
        when:
        Meta direct = new Meta(true, "after_123", "before_123")
        Meta built = MetaBuilder.builder()
                .hasMore(true)
                .afterCursor("after_123")
                .beforeCursor("before_123")
                .build()

        then:
        verifyAll(direct) {
            hasMore() == true
            afterCursor() == "after_123"
            beforeCursor() == "before_123"
        }
        verifyAll(built) {
            it == direct
            hasMore() == true
            afterCursor() == "after_123"
            beforeCursor() == "before_123"
        }
    }

    def "should update Meta using record withers"() {
        given:
        Meta original = new Meta(false, "cursor_a", "cursor_b")

        when:
        Meta updated = original
                .withHasMore(true)
                .withAfterCursor("cursor_c")
                .withBeforeCursor("cursor_d")

        then:
        verifyAll(original) {
            hasMore() == false
            afterCursor() == "cursor_a"
            beforeCursor() == "cursor_b"
        }
        verifyAll(updated) {
            hasMore() == true
            afterCursor() == "cursor_c"
            beforeCursor() == "cursor_d"
        }
    }

    def "should round-trip serialize and deserialize Meta"() {
        given:
        Meta meta = new Meta(true, "after_xyz", "before_abc")

        when:
        String json = objectMapper.writeValueAsString(meta)
        Map map = objectMapper.readValue(json, Map.class)
        Meta deserialized = objectMapper.readValue(json, Meta.class)

        then:
        verifyAll(map) {
            get("has_more") == true
            get("after_cursor") == "after_xyz"
            get("before_cursor") == "before_abc"
        }
        verifyAll(deserialized) {
            it == meta
            hasMore() == true
            afterCursor() == "after_xyz"
            beforeCursor() == "before_abc"
        }
    }

    def "should handle null components in Meta constructor, wither, and serde"() {
        when:
        Meta empty = new Meta(null, null, null)
        Meta built = MetaBuilder.builder().build()
        Meta cleared = new Meta(true, "a", "b")
                .withHasMore(null)
                .withAfterCursor(null)
                .withBeforeCursor(null)
        String json = objectMapper.writeValueAsString(empty)
        Map map = objectMapper.readValue(json, Map.class)
        Meta deserialized = objectMapper.readValue(json, Meta.class)

        then:
        verifyAll(empty) {
            hasMore() == null
            afterCursor() == null
            beforeCursor() == null
        }
        verifyAll(built) {
            it == empty
            hasMore() == null
            afterCursor() == null
            beforeCursor() == null
        }
        verifyAll(cleared) {
            it == empty
            hasMore() == null
            afterCursor() == null
            beforeCursor() == null
        }
        verifyAll(map) {
            isEmpty()
        }
        verifyAll(deserialized) {
            it == empty
            hasMore() == null
            afterCursor() == null
            beforeCursor() == null
        }
    }
}
