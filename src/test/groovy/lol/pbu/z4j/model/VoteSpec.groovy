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

class VoteSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Vote with constructors and builder"() {
        when:
        Vote direct = new Vote(
                101L,
                "https://example.com/api/v2/help_center/articles/1/votes/101.json",
                202L,
                1,
                1L,
                "Article",
                "2026-01-01T00:00:00Z",
                "2026-01-02T00:00:00Z"
        )
        Vote oneArg = new Vote(-1)
        Vote noArg = new Vote()
        Vote built = VoteBuilder.builder()
                .id(101L)
                .url("https://example.com/api/v2/help_center/articles/1/votes/101.json")
                .userId(202L)
                .value(1)
                .itemId(1L)
                .itemType("Article")
                .createdAt("2026-01-01T00:00:00Z")
                .updatedAt("2026-01-02T00:00:00Z")
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
            url() == "https://example.com/api/v2/help_center/articles/1/votes/101.json"
            userId() == 202L
            value() == 1
            itemId() == 1L
            itemType() == "Article"
            createdAt() == "2026-01-01T00:00:00Z"
            updatedAt() == "2026-01-02T00:00:00Z"
        }
        verifyAll(oneArg) {
            id() == null
            url() == null
            userId() == null
            value() == -1
            itemId() == null
            itemType() == null
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(noArg) {
            id() == null
            url() == null
            userId() == null
            value() == null
            itemId() == null
            itemType() == null
            createdAt() == null
            updatedAt() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update Vote with record withers"() {
        given:
        Vote initial = new Vote(1)

        when:
        Vote updated = initial
                .withId(303L)
                .withUrl("https://example.com/api/votes/303.json")
                .withUserId(404L)
                .withValue(-1)
                .withItemId(2L)
                .withItemType("Post")
                .withCreatedAt("2026-02-01T00:00:00Z")
                .withUpdatedAt("2026-02-02T00:00:00Z")

        then:
        verifyAll(updated) {
            id() == 303L
            url() == "https://example.com/api/votes/303.json"
            userId() == 404L
            value() == -1
            itemId() == 2L
            itemType() == "Post"
            createdAt() == "2026-02-01T00:00:00Z"
            updatedAt() == "2026-02-02T00:00:00Z"
        }
    }

    def "should round-trip serialize and deserialize Vote"() {
        given:
        Vote vote = new Vote(
                555L,
                "https://example.com/api/votes/555.json",
                666L,
                1,
                777L,
                "Article",
                "2026-03-01T00:00:00Z",
                "2026-03-02T00:00:00Z"
        )

        when:
        String json = objectMapper.writeValueAsString(vote)
        Map map = objectMapper.readValue(json, Map.class)
        Vote deserialized = objectMapper.readValue(json, Vote.class)

        then:
        verifyAll(map) {
            get("id") == 555
            get("url") == "https://example.com/api/votes/555.json"
            get("user_id") == 666
            get("value") == 1
            get("item_id") == 777
            get("item_type") == "Article"
            get("created_at") == "2026-03-01T00:00:00Z"
            get("updated_at") == "2026-03-02T00:00:00Z"
        }
        verifyAll(deserialized) {
            it == vote
        }
    }
}
