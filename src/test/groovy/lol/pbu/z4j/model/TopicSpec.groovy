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

class TopicSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create Topic with constructors and builder"() {
        when:
        Topic direct = new Topic(
                "Feedback",
                "2026-01-01T00:00:00Z",
                "Product feedback topic",
                42L,
                "https://example.com/hc/topics/1",
                101L,
                TopicManageableBy.MANAGERS,
                2L,
                "2026-01-02T00:00:00Z",
                "https://example.com/api/v2/community/topics/1.json",
                202L
        )
        Topic oneArg = new Topic("OneArgTopic")
        Topic noArg = new Topic()
        Topic built = TopicBuilder.builder()
                .name("Feedback")
                .createdAt("2026-01-01T00:00:00Z")
                .description("Product feedback topic")
                .followerCount(42L)
                .htmlUrl("https://example.com/hc/topics/1")
                .id(101L)
                .manageableBy(TopicManageableBy.MANAGERS)
                .position(2L)
                .updatedAt("2026-01-02T00:00:00Z")
                .url("https://example.com/api/v2/community/topics/1.json")
                .userSegmentId(202L)
                .build()

        then:
        verifyAll(direct) {
            name() == "Feedback"
            createdAt() == "2026-01-01T00:00:00Z"
            description() == "Product feedback topic"
            followerCount() == 42L
            htmlUrl() == "https://example.com/hc/topics/1"
            id() == 101L
            manageableBy() == TopicManageableBy.MANAGERS
            position() == 2L
            updatedAt() == "2026-01-02T00:00:00Z"
            url() == "https://example.com/api/v2/community/topics/1.json"
            userSegmentId() == 202L
        }
        verifyAll(oneArg) {
            name() == "OneArgTopic"
            createdAt() == null
            description() == null
            followerCount() == null
            htmlUrl() == null
            id() == null
            manageableBy() == null
            position() == null
            updatedAt() == null
            url() == null
            userSegmentId() == null
        }
        verifyAll(noArg) {
            name() == null
            createdAt() == null
            description() == null
            followerCount() == null
            htmlUrl() == null
            id() == null
            manageableBy() == null
            position() == null
            updatedAt() == null
            url() == null
            userSegmentId() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update Topic with record withers"() {
        given:
        Topic initial = new Topic("Initial")

        when:
        Topic updated = initial
                .withName("Updated Topic")
                .withCreatedAt("2026-02-01T00:00:00Z")
                .withDescription("Updated description")
                .withFollowerCount(100L)
                .withHtmlUrl("https://example.com/hc/topics/2")
                .withId(303L)
                .withManageableBy(TopicManageableBy.STAFF)
                .withPosition(5L)
                .withUpdatedAt("2026-02-02T00:00:00Z")
                .withUrl("https://example.com/api/topics/2.json")
                .withUserSegmentId(404L)

        then:
        verifyAll(updated) {
            name() == "Updated Topic"
            createdAt() == "2026-02-01T00:00:00Z"
            description() == "Updated description"
            followerCount() == 100L
            htmlUrl() == "https://example.com/hc/topics/2"
            id() == 303L
            manageableBy() == TopicManageableBy.STAFF
            position() == 5L
            updatedAt() == "2026-02-02T00:00:00Z"
            url() == "https://example.com/api/topics/2.json"
            userSegmentId() == 404L
        }
    }

    def "should round-trip serialize and deserialize Topic"() {
        given:
        Topic topic = new Topic("Feature Requests")
                .withCreatedAt("2026-03-01T00:00:00Z")
                .withDescription("Ideas and feature requests")
                .withFollowerCount(15L)
                .withHtmlUrl("https://example.com/hc/topics/999")
                .withId(999L)
                .withManageableBy(TopicManageableBy.MANAGERS)
                .withPosition(1L)
                .withUpdatedAt("2026-03-02T00:00:00Z")
                .withUrl("https://example.com/api/topics/999.json")
                .withUserSegmentId(888L)

        when:
        String json = objectMapper.writeValueAsString(topic)
        Map map = objectMapper.readValue(json, Map.class)
        Topic deserialized = objectMapper.readValue(json, Topic.class)

        then:
        verifyAll(map) {
            get("name") == "Feature Requests"
            get("created_at") == "2026-03-01T00:00:00Z"
            get("description") == "Ideas and feature requests"
            get("follower_count") == 15
            get("html_url") == "https://example.com/hc/topics/999"
            get("id") == 999
            get("manageable_by") == "managers"
            get("position") == 1
            get("updated_at") == "2026-03-02T00:00:00Z"
            get("url") == "https://example.com/api/topics/999.json"
            get("user_segment_id") == 888
        }
        verifyAll(deserialized) {
            it == topic
        }
    }
}
