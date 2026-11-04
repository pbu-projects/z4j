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

class PostSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Post with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.parse("2026-03-10T12:00:00Z")
        List<String> tags = ["tag_1", "tag_2"]

        when:
        Post direct = new Post(
                "Feature Request",
                101L,
                false,
                5L,
                tags,
                now,
                "Please add dark mode",
                true,
                12L,
                "https://example.com/posts/1",
                501L,
                202L,
                now,
                true,
                "planned",
                301L,
                now,
                "https://example.com/api/posts/1.json",
                25L,
                20L
        )
        Post oneArg = new Post("Bug Report")
        Post noArg = new Post()
        Post built = PostBuilder.builder()
                .title("Feature Request")
                .authorId(101L)
                .closed(false)
                .commentCount(5L)
                .contentTagIds(tags)
                .createdAt(now)
                .details("Please add dark mode")
                .featured(true)
                .followerCount(12L)
                .htmlUrl("https://example.com/posts/1")
                .id(501L)
                .nonAuthorEditorId(202L)
                .nonAuthorUpdatedAt(now)
                .pinned(true)
                .status("planned")
                .topicId(301L)
                .updatedAt(now)
                .url("https://example.com/api/posts/1.json")
                .voteCount(25L)
                .voteSum(20L)
                .build()

        then:
        verifyAll(direct) {
            title() == "Feature Request"
            authorId() == 101L
            closed() == false
            commentCount() == 5L
            contentTagIds() == tags
            createdAt() == now
            details() == "Please add dark mode"
            featured() == true
            followerCount() == 12L
            htmlUrl() == "https://example.com/posts/1"
            id() == 501L
            nonAuthorEditorId() == 202L
            nonAuthorUpdatedAt() == now
            pinned() == true
            status() == "planned"
            topicId() == 301L
            updatedAt() == now
            url() == "https://example.com/api/posts/1.json"
            voteCount() == 25L
            voteSum() == 20L
        }
        verifyAll(oneArg) {
            title() == "Bug Report"
            authorId() == null
            id() == null
            details() == null
            contentTagIds() == null
        }
        verifyAll(noArg) {
            title() == null
            authorId() == null
            id() == null
            details() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update Post using record withers"() {
        given:
        Post initial = new Post("Initial Title")
        ZonedDateTime now = ZonedDateTime.parse("2026-03-10T14:00:00Z")

        when:
        Post updated = initial
                .withTitle("Updated Title")
                .withAuthorId(201L)
                .withClosed(true)
                .withCommentCount(8L)
                .withContentTagIds(["tag_a"])
                .withCreatedAt(now)
                .withDetails("Updated details")
                .withFeatured(false)
                .withFollowerCount(15L)
                .withHtmlUrl("https://example.com/posts/2")
                .withId(602L)
                .withNonAuthorEditorId(303L)
                .withNonAuthorUpdatedAt(now)
                .withPinned(false)
                .withStatus("completed")
                .withTopicId(402L)
                .withUpdatedAt(now)
                .withUrl("https://example.com/api/posts/2.json")
                .withVoteCount(30L)
                .withVoteSum(28L)

        then:
        verifyAll(updated) {
            title() == "Updated Title"
            authorId() == 201L
            closed() == true
            commentCount() == 8L
            contentTagIds() == ["tag_a"]
            createdAt() == now
            details() == "Updated details"
            featured() == false
            followerCount() == 15L
            htmlUrl() == "https://example.com/posts/2"
            id() == 602L
            nonAuthorEditorId() == 303L
            nonAuthorUpdatedAt() == now
            pinned() == false
            status() == "completed"
            topicId() == 402L
            updatedAt() == now
            url() == "https://example.com/api/posts/2.json"
            voteCount() == 30L
            voteSum() == 28L
        }
    }

    def "should add content tag ids item"() {
        given:
        Post fromNull = new Post("Post without tags")
        Post fromExisting = new Post("Post with tags").withContentTagIds(["existing_tag"])

        when:
        Post updatedNull = fromNull.addContentTagIdsItem("new_tag")
        Post updatedExisting = fromExisting.addContentTagIdsItem("second_tag")

        then:
        verifyAll(updatedNull) {
            contentTagIds().size() == 1
            contentTagIds()[0] == "new_tag"
        }
        verifyAll(updatedExisting) {
            contentTagIds().size() == 2
            contentTagIds().containsAll(["existing_tag", "second_tag"])
        }
    }

    def "should implement SearchResultsInner"() {
        given:
        Post post = new Post("Search hit post").withId(999L)
        SearchResultsInner inner = post

        expect:
        verifyAll(inner) {
            it instanceof Post
            ((Post) it).title() == "Search hit post"
            ((Post) it).id() == 999L
        }
    }

    def "should round-trip serialize and deserialize Post"() {
        given:
        ZonedDateTime now = ZonedDateTime.parse("2026-03-10T12:00:00Z")
        Post post = new Post("Roadmap Update")
                .withId(777L)
                .withDetails("Quarterly update details")
                .withStatus("answered")
                .withCreatedAt(now)
                .withUpdatedAt(now)
                .withVoteCount(10L)
                .withVoteSum(8L)

        when:
        String json = objectMapper.writeValueAsString(post)
        Map map = objectMapper.readValue(json, Map.class)
        Post deserialized = objectMapper.readValue(json, Post.class)

        then:
        verifyAll(map) {
            get("title") == "Roadmap Update"
            get("id") == 777
            get("details") == "Quarterly update details"
            get("status") == "answered"
            get("vote_count") == 10
            get("vote_sum") == 8
        }
        verifyAll(deserialized) {
            it == post
        }
    }
}
