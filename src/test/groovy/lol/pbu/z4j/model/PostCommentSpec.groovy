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

class PostCommentSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create PostComment with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.parse("2026-03-01T10:00:00Z")

        when:
        PostComment direct = new PostComment(
                "Feedback on post",
                LocaleAbbreviation.ENGLISH_UNITED_STATES,
                15L,
                "2026-03-01T10:00:00Z",
                "https://example.com/posts/1/comments/1",
                201L,
                33L,
                now,
                true,
                501L,
                now,
                "https://example.com/api/post_comments/1.json",
                9L,
                7L
        )
        PostComment oneArg = new PostComment("Simple feedback")
        PostComment noArg = new PostComment()
        PostComment built = PostCommentBuilder.builder()
                .body("Feedback on post")
                .localeAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .authorId(15L)
                .createdAt("2026-03-01T10:00:00Z")
                .htmlUrl("https://example.com/posts/1/comments/1")
                .id(201L)
                .nonAuthorEditorId(33L)
                .nonAuthorUpdatedAt(now)
                .official(true)
                .postId(501L)
                .updatedAt(now)
                .url("https://example.com/api/post_comments/1.json")
                .voteCount(9L)
                .voteSum(7L)
                .build()

        then:
        verifyAll(direct) {
            body() == "Feedback on post"
            localeAbbreviation() == LocaleAbbreviation.ENGLISH_UNITED_STATES
            authorId() == 15L
            createdAt() == "2026-03-01T10:00:00Z"
            htmlUrl() == "https://example.com/posts/1/comments/1"
            id() == 201L
            nonAuthorEditorId() == 33L
            nonAuthorUpdatedAt() == now
            official() == true
            postId() == 501L
            updatedAt() == now
            url() == "https://example.com/api/post_comments/1.json"
            voteCount() == 9L
            voteSum() == 7L
        }
        verifyAll(oneArg) {
            body() == "Simple feedback"
            localeAbbreviation() == null
            authorId() == null
            id() == null
            official() == null
            postId() == null
        }
        verifyAll(noArg) {
            body() == null
            localeAbbreviation() == null
            authorId() == null
            id() == null
            official() == null
            postId() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update PostComment using record withers"() {
        given:
        PostComment initial = new PostComment("Initial")
        ZonedDateTime now = ZonedDateTime.parse("2026-03-01T12:00:00Z")

        when:
        PostComment updated = initial
                .withBody("Updated feedback")
                .withLocaleAbbreviation(LocaleAbbreviation.JAPANESE)
                .withAuthorId(77L)
                .withCreatedAt("2026-03-01T12:00:00Z")
                .withHtmlUrl("https://example.com/posts/2/comments/2")
                .withId(302L)
                .withNonAuthorEditorId(88L)
                .withNonAuthorUpdatedAt(now)
                .withOfficial(false)
                .withPostId(602L)
                .withUpdatedAt(now)
                .withUrl("https://example.com/api/post_comments/2.json")
                .withVoteCount(12L)
                .withVoteSum(10L)

        then:
        verifyAll(updated) {
            body() == "Updated feedback"
            localeAbbreviation() == LocaleAbbreviation.JAPANESE
            authorId() == 77L
            createdAt() == "2026-03-01T12:00:00Z"
            htmlUrl() == "https://example.com/posts/2/comments/2"
            id() == 302L
            nonAuthorEditorId() == 88L
            nonAuthorUpdatedAt() == now
            official() == false
            postId() == 602L
            updatedAt() == now
            url() == "https://example.com/api/post_comments/2.json"
            voteCount() == 12L
            voteSum() == 10L
        }
    }

    def "should implement CommentBase and SearchResultsInner"() {
        given:
        ZonedDateTime now = ZonedDateTime.parse("2026-03-01T10:00:00Z")
        PostComment comment = new PostComment("Community comment")
                .withId(444L)
                .withAuthorId(10L)
                .withPostId(888L)
                .withOfficial(true)
                .withCreatedAt("2026-03-01T10:00:00Z")
                .withUpdatedAt(now)

        CommentBase base = comment
        SearchResultsInner searchHit = comment

        expect:
        verifyAll(base) {
            body() == "Community comment"
            id() == 444L
            authorId() == 10L
            createdAt() == "2026-03-01T10:00:00Z"
            updatedAt() == now
        }
        verifyAll(searchHit) {
            it instanceof PostComment
            ((PostComment) it).postId() == 888L
            ((PostComment) it).official() == true
        }
    }

    def "should round-trip serialize and deserialize PostComment"() {
        given:
        PostComment comment = new PostComment("Helpful answer")
                .withId(888L)
                .withLocaleAbbreviation(LocaleAbbreviation.ENGLISH_UNITED_STATES)
                .withAuthorId(44L)
                .withPostId(999L)
                .withOfficial(true)
                .withVoteCount(6L)
                .withVoteSum(5L)

        when:
        String json = objectMapper.writeValueAsString(comment)
        Map map = objectMapper.readValue(json, Map.class)
        PostComment deserialized = objectMapper.readValue(json, PostComment.class)

        then:
        verifyAll(map) {
            get("body") == "Helpful answer"
            get("id") == 888
            get("locale") == "en-us"
            get("author_id") == 44
            get("post_id") == 999
            get("official") == true
            get("vote_count") == 6
            get("vote_sum") == 5
        }
        verifyAll(deserialized) {
            it == comment
        }
    }
}
