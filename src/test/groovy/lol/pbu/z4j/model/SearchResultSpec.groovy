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

class SearchResultSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create SearchResult with constructors and builder"() {
        when:
        SearchResult direct = new SearchResult(
                "2026-03-25T10:00:00Z",
                true,
                false,
                "Ticket result description",
                101L,
                "Ticket Result 1",
                "ticket",
                "2026-03-25T11:00:00Z",
                "https://example.com/api/v2/tickets/101.json"
        )
        SearchResult noArg = new SearchResult()
        SearchResult built = SearchResultBuilder.builder()
                .createdAt("2026-03-25T10:00:00Z")
                .isDefault(true)
                .deleted(false)
                .description("Ticket result description")
                .id(101L)
                .name("Ticket Result 1")
                .resultType("ticket")
                .updatedAt("2026-03-25T11:00:00Z")
                .url("https://example.com/api/v2/tickets/101.json")
                .build()

        then:
        verifyAll(direct) {
            createdAt() == "2026-03-25T10:00:00Z"
            isDefault() == true
            deleted() == false
            description() == "Ticket result description"
            id() == 101L
            name() == "Ticket Result 1"
            resultType() == "ticket"
            updatedAt() == "2026-03-25T11:00:00Z"
            url() == "https://example.com/api/v2/tickets/101.json"
        }
        verifyAll(noArg) {
            createdAt() == null
            isDefault() == null
            deleted() == null
            description() == null
            id() == null
            name() == null
            resultType() == null
            updatedAt() == null
            url() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update SearchResult using record withers"() {
        given:
        SearchResult initial = new SearchResult().withId(1L)

        when:
        SearchResult updated = initial
                .withCreatedAt("2026-03-25T12:00:00Z")
                .withIsDefault(false)
                .withDeleted(true)
                .withDescription("Updated description")
                .withId(202L)
                .withName("Updated Result")
                .withResultType("article")
                .withUpdatedAt("2026-03-25T13:00:00Z")
                .withUrl("https://example.com/api/articles/202.json")

        then:
        verifyAll(updated) {
            createdAt() == "2026-03-25T12:00:00Z"
            isDefault() == false
            deleted() == true
            description() == "Updated description"
            id() == 202L
            name() == "Updated Result"
            resultType() == "article"
            updatedAt() == "2026-03-25T13:00:00Z"
            url() == "https://example.com/api/articles/202.json"
        }
    }

    def "should round-trip serialize and deserialize SearchResult"() {
        given:
        SearchResult result = new SearchResult()
                .withId(777L)
                .withName("Custom Object Result")
                .withResultType("custom_object")
                .withIsDefault(true)
                .withDeleted(false)

        when:
        String json = objectMapper.writeValueAsString(result)
        Map map = objectMapper.readValue(json, Map.class)
        SearchResult deserialized = objectMapper.readValue(json, SearchResult.class)

        then:
        verifyAll(map) {
            get("id") == 777
            get("name") == "Custom Object Result"
            get("result_type") == "custom_object"
            get("default") == true
            get("deleted") == false
        }
        verifyAll(deserialized) {
            it == result
        }
    }
}
