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

class UserSegmentSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create UserSegment with constructors and builder"() {
        when:
        UserSegment direct = new UserSegment(
                "Staff Only",
                "staff",
                [101L, 102L],
                false,
                "2026-01-01T00:00:00Z",
                [201L],
                1L,
                ["or_tag1"],
                [301L],
                ["tag1"],
                "2026-01-02T00:00:00Z"
        )
        UserSegment twoArg = new UserSegment("VIP", "signed_in_users")
        UserSegment noArg = new UserSegment()
        UserSegment built = UserSegmentBuilder.builder()
                .name("Staff Only")
                .userType("staff")
                .addedUserIds([101L, 102L])
                .builtIn(false)
                .createdAt("2026-01-01T00:00:00Z")
                .groupIds([201L])
                .id(1L)
                .orTags(["or_tag1"])
                .organizationIds([301L])
                .tags(["tag1"])
                .updatedAt("2026-01-02T00:00:00Z")
                .build()

        then:
        verifyAll(direct) {
            name() == "Staff Only"
            userType() == "staff"
            addedUserIds() == [101L, 102L]
            builtIn() == false
            createdAt() == "2026-01-01T00:00:00Z"
            groupIds() == [201L]
            id() == 1L
            orTags() == ["or_tag1"]
            organizationIds() == [301L]
            tags() == ["tag1"]
            updatedAt() == "2026-01-02T00:00:00Z"
        }
        verifyAll(twoArg) {
            name() == "VIP"
            userType() == "signed_in_users"
            addedUserIds() == null
            builtIn() == null
            createdAt() == null
            groupIds() == null
            id() == null
            orTags() == null
            organizationIds() == null
            tags() == null
            updatedAt() == null
        }
        verifyAll(noArg) {
            name() == null
            userType() == null
            addedUserIds() == null
            builtIn() == null
            createdAt() == null
            groupIds() == null
            id() == null
            orTags() == null
            organizationIds() == null
            tags() == null
            updatedAt() == null
        }
        verifyAll(built) {
            it == direct
        }
    }

    def "should update UserSegment with record withers"() {
        given:
        UserSegment initial = new UserSegment("Initial", "staff")

        when:
        UserSegment updated = initial
                .withName("Updated Segment")
                .withUserType("signed_in_users")
                .withAddedUserIds([999L])
                .withBuiltIn(true)
                .withCreatedAt("2026-02-01T00:00:00Z")
                .withGroupIds([888L])
                .withId(2L)
                .withOrTags(["or1"])
                .withOrganizationIds([777L])
                .withTags(["t1"])
                .withUpdatedAt("2026-02-02T00:00:00Z")

        then:
        verifyAll(updated) {
            name() == "Updated Segment"
            userType() == "signed_in_users"
            addedUserIds() == [999L]
            builtIn() == true
            createdAt() == "2026-02-01T00:00:00Z"
            groupIds() == [888L]
            id() == 2L
            orTags() == ["or1"]
            organizationIds() == [777L]
            tags() == ["t1"]
            updatedAt() == "2026-02-02T00:00:00Z"
        }
    }

    def "should add items with helper methods when list is null or non-null"() {
        given:
        UserSegment initial = new UserSegment("Test", "staff")

        when:
        UserSegment step1 = initial
                .addAddedUserIdsItem(11L)
                .addGroupIdsItem(22L)
                .addOrTagsItem("orA")
                .addOrganizationIdsItem(33L)
                .addTagsItem("tagA")

        UserSegment step2 = step1
                .addAddedUserIdsItem(12L)
                .addGroupIdsItem(23L)
                .addOrTagsItem("orB")
                .addOrganizationIdsItem(34L)
                .addTagsItem("tagB")

        then:
        verifyAll(step1) {
            addedUserIds() == [11L]
            groupIds() == [22L]
            orTags() == ["orA"]
            organizationIds() == [33L]
            tags() == ["tagA"]
        }
        verifyAll(step2) {
            addedUserIds() == [11L, 12L]
            groupIds() == [22L, 23L]
            orTags() == ["orA", "orB"]
            organizationIds() == [33L, 34L]
            tags() == ["tagA", "tagB"]
        }
    }

    def "should round-trip serialize and deserialize UserSegment"() {
        given:
        UserSegment segment = new UserSegment(
                "Agents",
                "staff",
                [10L],
                false,
                "2026-03-01T00:00:00Z",
                [20L],
                555L,
                ["or_tag"],
                [30L],
                ["urgent"],
                "2026-03-02T00:00:00Z"
        )

        when:
        String json = objectMapper.writeValueAsString(segment)
        Map map = objectMapper.readValue(json, Map.class)
        UserSegment deserialized = objectMapper.readValue(json, UserSegment.class)

        then:
        verifyAll(map) {
            get("name") == "Agents"
            get("user_type") == "staff"
            get("added_user_ids") == [10]
            get("built_in") == false
            get("created_at") == "2026-03-01T00:00:00Z"
            get("group_ids") == [20]
            get("id") == 555
            get("or_tags") == ["or_tag"]
            get("organization_ids") == [30]
            get("tags") == ["urgent"]
            get("updated_at") == "2026-03-02T00:00:00Z"
        }
        verifyAll(deserialized) {
            it == segment
        }
    }
}
