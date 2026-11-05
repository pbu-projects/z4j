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

class CommentSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Comment with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.parse("2026-02-01T10:00:00Z")

        when:
        Comment direct = new Comment(
                "Great article",
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                11L,
                "2026-02-01T10:00:00Z",
                "https://example.com/comments/1",
                101L,
                22L,
                now,
                301L,
                "Article",
                now,
                "https://example.com/api/comments/1.json",
                5L,
                4L
        )
        Comment twoArg = new Comment("Short note", LocaleAbbreviation.FRENCH)
        Comment oneArg = new Comment("One arg note")
        Comment noArg = new Comment()
        Comment built = CommentBuilder.builder()
                .body("Great article")
                .localeAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .authorId(11L)
                .createdAt("2026-02-01T10:00:00Z")
                .htmlUrl("https://example.com/comments/1")
                .id(101L)
                .nonAuthorEditorId(22L)
                .nonAuthorUpdatedAt(now)
                .sourceId(301L)
                .sourceType("Article")
                .updatedAt(now)
                .url("https://example.com/api/comments/1.json")
                .voteCount(5L)
                .voteSum(4L)
                .build()

        then:
        verifyAll(direct) {
            body() == "Great article"
            localeAbbreviation() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            authorId() == 11L
            createdAt() == "2026-02-01T10:00:00Z"
            htmlUrl() == "https://example.com/comments/1"
            id() == 101L
            nonAuthorEditorId() == 22L
            nonAuthorUpdatedAt() == now
            sourceId() == 301L
            sourceType() == "Article"
            updatedAt() == now
            url() == "https://example.com/api/comments/1.json"
            voteCount() == 5L
            voteSum() == 4L
        }
        verifyAll(twoArg) {
            body() == "Short note"
            localeAbbreviation() == LocaleAbbreviation.FRENCH
            authorId() == null
            id() == null
        }
        verifyAll(oneArg) {
            body() == "One arg note"
            localeAbbreviation() == null
            authorId() == null
            id() == null
        }
        verifyAll(noArg) {
            body() == null
            localeAbbreviation() == null
            authorId() == null
            id() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update Comment using record withers"() {
        given:
        Comment initial = new Comment("Original")
        ZonedDateTime now = ZonedDateTime.parse("2026-02-01T12:00:00Z")

        when:
        Comment updated = initial
                .withBody("Updated body")
                .withLocaleAbbreviation(LocaleAbbreviation.GERMAN)
                .withAuthorId(55L)
                .withCreatedAt("2026-02-01T12:00:00Z")
                .withHtmlUrl("https://example.com/comments/2")
                .withId(202L)
                .withNonAuthorEditorId(66L)
                .withNonAuthorUpdatedAt(now)
                .withSourceId(402L)
                .withSourceType("Article")
                .withUpdatedAt(now)
                .withUrl("https://example.com/api/comments/2.json")
                .withVoteCount(10L)
                .withVoteSum(8L)

        then:
        verifyAll(updated) {
            body() == "Updated body"
            localeAbbreviation() == LocaleAbbreviation.GERMAN
            authorId() == 55L
            createdAt() == "2026-02-01T12:00:00Z"
            htmlUrl() == "https://example.com/comments/2"
            id() == 202L
            nonAuthorEditorId() == 66L
            nonAuthorUpdatedAt() == now
            sourceId() == 402L
            sourceType() == "Article"
            updatedAt() == now
            url() == "https://example.com/api/comments/2.json"
            voteCount() == 10L
            voteSum() == 8L
        }
    }

    def "should implement CommentBase interface"() {
        given:
        ZonedDateTime now = ZonedDateTime.parse("2026-02-01T10:00:00Z")
        CommentBase base = new Comment("Base comment")
                .withId(999L)
                .withAuthorId(12L)
                .withLocaleAbbreviation(LocaleAbbreviation.SPANISH)
                .withCreatedAt("2026-02-01T10:00:00Z")
                .withHtmlUrl("https://example.com/hc/999")
                .withNonAuthorEditorId(13L)
                .withNonAuthorUpdatedAt(now)
                .withUpdatedAt(now)
                .withUrl("https://example.com/api/999.json")
                .withVoteCount(3L)
                .withVoteSum(2L)

        expect:
        verifyAll(base) {
            body() == "Base comment"
            id() == 999L
            authorId() == 12L
            localeAbbreviation() == LocaleAbbreviation.SPANISH
            createdAt() == "2026-02-01T10:00:00Z"
            htmlUrl() == "https://example.com/hc/999"
            nonAuthorEditorId() == 13L
            nonAuthorUpdatedAt() == now
            updatedAt() == now
            url() == "https://example.com/api/999.json"
            voteCount() == 3L
            voteSum() == 2L
        }
    }

    def "should round-trip serialize and deserialize Comment"() {
        given:
        Comment comment = new Comment("Helpful explanation")
                .withId(777L)
                .withLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .withAuthorId(88L)
                .withSourceId(99L)
                .withSourceType("Article")
                .withVoteCount(2L)
                .withVoteSum(2L)

        when:
        String json = objectMapper.writeValueAsString(comment)
        Map map = objectMapper.readValue(json, Map.class)
        Comment deserialized = objectMapper.readValue(json, Comment.class)

        then:
        verifyAll(map) {
            get("body") == "Helpful explanation"
            get("id") == 777
            get("locale") == "en-us"
            get("author_id") == 88
            get("source_id") == 99
            get("source_type") == "Article"
            get("vote_count") == 2
            get("vote_sum") == 2
        }
        verifyAll(deserialized) {
            it == comment
        }
    }
}
