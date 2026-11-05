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

import java.time.ZoneOffset
import java.time.ZonedDateTime

class UserSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = ObjectMapper.getDefault()

    def "should create User with constructors and builder"() {
        given:
        ZonedDateTime now = ZonedDateTime.of(2026, 1, 15, 10, 30, 0, 0, ZoneOffset.UTC)
        Attachment photoAttachment = new Attachment().withId(555L).withFileName("avatar.png")

        when:
        User direct = new User(
                101L,
                [1L, 2L],
                "test.user@example.com",
                "Test User",
                "agent",
                true,
                "Agent T",
                false,
                42L,
                77L,
                "Support specialist",
                "ext-101",
                "America/New_York",
                now,
                "en-US",
                1L,
                true,
                "Top performer",
                false,
                888L,
                "+15555551234",
                photoAttachment,
                "https://example.com/photo.png",
                false,
                false,
                1,
                false,
                false,
                false,
                "Thanks, Agent T",
                false,
                ["support", "tier1"],
                "assigned",
                "Eastern Time (US & Canada)",
                true,
                "https://example.zendesk.com/api/v2/users/101.json",
                ["department": "Support"],
                true,
                "token123",
                now,
                now
        )
        User noArg = new User()
        User built = UserBuilder.builder()
                .id(101L)
                .agentBrandIds([1L, 2L])
                .email("test.user@example.com")
                .name("Test User")
                .role("agent")
                .active(true)
                .alias("Agent T")
                .chatOnly(false)
                .customRoleId(42L)
                .defaultGroupId(77L)
                .details("Support specialist")
                .externalId("ext-101")
                .ianaTimeZone("America/New_York")
                .lastLoginAt(now)
                .locale("en-US")
                .localeId(1L)
                .moderator(true)
                .notes("Top performer")
                .onlyPrivateComments(false)
                .organizationId(888L)
                .phone("+15555551234")
                .photo(photoAttachment)
                .remotePhotoUrl("https://example.com/photo.png")
                .reportCsv(false)
                .restrictedAgent(false)
                .roleType(1)
                .shared(false)
                .sharedAgent(false)
                .sharedPhoneNumber(false)
                .signature("Thanks, Agent T")
                .suspended(false)
                .tags(["support", "tier1"])
                .ticketRestriction("assigned")
                .timeZone("Eastern Time (US & Canada)")
                .twoFactorAuthEnabled(true)
                .url("https://example.zendesk.com/api/v2/users/101.json")
                .userFields(["department": "Support"])
                .verified(true)
                .authenticityToken("token123")
                .createdAt(now)
                .updatedAt(now)
                .build()

        then:
        verifyAll(direct) {
            id() == 101L
            agentBrandIds() == [1L, 2L]
            email() == "test.user@example.com"
            name() == "Test User"
            role() == "agent"
            active() == true
            alias() == "Agent T"
            chatOnly() == false
            customRoleId() == 42L
            defaultGroupId() == 77L
            details() == "Support specialist"
            externalId() == "ext-101"
            ianaTimeZone() == "America/New_York"
            lastLoginAt() == now
            locale() == "en-US"
            localeId() == 1L
            moderator() == true
            notes() == "Top performer"
            onlyPrivateComments() == false
            organizationId() == 888L
            phone() == "+15555551234"
            photo() == photoAttachment
            remotePhotoUrl() == "https://example.com/photo.png"
            reportCsv() == false
            restrictedAgent() == false
            roleType() == 1
            shared() == false
            sharedAgent() == false
            sharedPhoneNumber() == false
            signature() == "Thanks, Agent T"
            suspended() == false
            tags() == ["support", "tier1"]
            ticketRestriction() == "assigned"
            timeZone() == "Eastern Time (US & Canada)"
            twoFactorAuthEnabled() == true
            url() == "https://example.zendesk.com/api/v2/users/101.json"
            userFields() == ["department": "Support"]
            verified() == true
            authenticityToken() == "token123"
            createdAt() == now
            updatedAt() == now
        }
        verifyAll(noArg) {
            id() == null
            name() == null
            email() == null
            role() == null
        }
        verifyAll(built) {
            it == direct
            id() == 101L
            name() == "Test User"
        }
    }

    def "should update User using withers"() {
        given:
        ZonedDateTime now = ZonedDateTime.of(2026, 2, 20, 8, 0, 0, 0, ZoneOffset.UTC)
        User initial = new User()

        when:
        User updated = initial
                .withId(202L)
                .withAgentBrandIds([5L])
                .withEmail("jane@example.com")
                .withName("Jane Smith")
                .withRole("admin")
                .withActive(true)
                .withAlias("JaneAdmin")
                .withChatOnly(true)
                .withCustomRoleId(10L)
                .withDefaultGroupId(20L)
                .withDetails("Lead Admin")
                .withExternalId("ext-202")
                .withIanaTimeZone("UTC")
                .withLastLoginAt(now)
                .withLocale("en-GB")
                .withLocaleId(2L)
                .withModerator(false)
                .withNotes("Key contact")
                .withOnlyPrivateComments(true)
                .withOrganizationId(999L)
                .withPhone("+15559876543")
                .withRemotePhotoUrl("https://example.com/jane.png")
                .withReportCsv(true)
                .withRestrictedAgent(true)
                .withRoleType(2)
                .withShared(true)
                .withSharedAgent(true)
                .withSharedPhoneNumber(true)
                .withSignature("Best, Jane")
                .withSuspended(true)
                .withTags(["admin"])
                .withTicketRestriction("groups")
                .withTimeZone("UTC")
                .withTwoFactorAuthEnabled(false)
                .withUrl("https://example.zendesk.com/users/202")
                .withUserFields([tier: "gold"])
                .withVerified(false)
                .withAuthenticityToken("auth456")
                .withCreatedAt(now)
                .withUpdatedAt(now)

        then:
        verifyAll(updated) {
            id() == 202L
            email() == "jane@example.com"
            name() == "Jane Smith"
            role() == "admin"
            active() == true
            tags() == ["admin"]
            suspended() == true
            verified() == false
        }
    }

    def "should add items to null and populated lists"() {
        given:
        User user = new User()

        when: "adding to null lists"
        User withTag = user.addTagsItem("first_tag")
        User withBrand = user.addAgentBrandIdsItem(10L)

        then:
        verifyAll(withTag) {
            tags() != null
            tags().size() == 1
            tags().first() == "first_tag"
        }
        verifyAll(withBrand) {
            agentBrandIds() != null
            agentBrandIds().size() == 1
            agentBrandIds().first() == 10L
        }

        when: "adding to populated lists"
        User withSecondTag = withTag.addTagsItem("second_tag")
        User withSecondBrand = withBrand.addAgentBrandIdsItem(20L)

        then:
        verifyAll(withSecondTag) {
            tags().size() == 2
            tags() == ["first_tag", "second_tag"]
        }
        verifyAll(withSecondBrand) {
            agentBrandIds().size() == 2
            agentBrandIds() == [10L, 20L]
        }
    }

    def "should round-trip serialize and deserialize User"() {
        given:
        User user = new User()
                .withId(101L)
                .withName("Jane Doe")
                .withEmail("jane@example.com")
                .withRole("admin")
                .withActive(true)
                .withTags(["vip", "beta"])
                .withUserFields(["department": "Support"])

        when:
        String json = objectMapper.writeValueAsString(user)
        Map map = objectMapper.readValue(json, Map.class)
        User deserialized = objectMapper.readValue(json, User.class)

        then:
        verifyAll(map) {
            get("id") == 101
            get("name") == "Jane Doe"
            get("email") == "jane@example.com"
            get("role") == "admin"
            get("active") == true
            get("tags") == ["vip", "beta"]
        }
        verifyAll(deserialized) {
            it == user
            id() == 101L
            name() == "Jane Doe"
            email() == "jane@example.com"
            role() == "admin"
            active() == true
            tags() == ["vip", "beta"]
            userFields() == ["department": "Support"]
        }
    }

    def "can deserialize user from raw JSON with snake case fields"() {
        given:
        String json = '''
        {
            "id": 202,
            "name": "John Doe",
            "email": "john@example.com",
            "role": "agent",
            "custom_role_id": 999,
            "agent_brand_ids": [1, 2],
            "iana_time_zone": "America/New_York",
            "two_factor_auth_enabled": true
        }
        '''

        when:
        User deserialized = objectMapper.readValue(json, User.class)

        then:
        verifyAll(deserialized) {
            id() == 202L
            name() == "John Doe"
            email() == "john@example.com"
            role() == "agent"
            customRoleId() == 999L
            agentBrandIds() == [1L, 2L]
            ianaTimeZone() == "America/New_York"
            twoFactorAuthEnabled() == true
        }
    }

    def "can serialize and deserialize UserResponse"() {
        given:
        User expectedUser = new User().withId(301L).withName("Test User")
        UserResponse response = new UserResponse(expectedUser)

        when:
        String json = objectMapper.writeValueAsString(response)
        UserResponse deserialized = objectMapper.readValue(json, UserResponse.class)

        then:
        verifyAll(deserialized) {
            user() != null
            user().id() == 301L
            user().name() == "Test User"
        }
    }

    def "can serialize and deserialize UserRequest"() {
        given:
        User expectedUser = new User().withName("New User").withEmail("new@example.com")
        UserRequest request = new UserRequest(expectedUser)

        when:
        String json = objectMapper.writeValueAsString(request)
        UserRequest deserialized = objectMapper.readValue(json, UserRequest.class)

        then:
        verifyAll(deserialized) {
            user() != null
            user().name() == "New User"
            user().email() == "new@example.com"
        }
    }

    def "can create UsersRequest and add users"() {
        given:
        User user1 = new User().withId(1L).withName("User One")
        User user2 = new User().withId(2L).withName("User Two")
        UsersRequest request = new UsersRequest(null)

        when:
        request = request.addUsersItem(user1).addUsersItem(user2)

        then:
        verifyAll(request) {
            users() != null
            users().size() == 2
            users()[0].name() == "User One"
            users()[1].name() == "User Two"
        }

        when:
        String json = objectMapper.writeValueAsString(request)
        UsersRequest deserialized = objectMapper.readValue(json, UsersRequest.class)

        then:
        verifyAll(deserialized) {
            users() != null
            users().size() == 2
            users()[0].id() == 1L
            users()[1].id() == 2L
        }
    }

    def "can serialize and deserialize UserCountResponse and UserCountResponseCount"() {
        given:
        UserCountResponseCount expectedCount = UserCountResponseCountBuilder.builder().value(42L).build()
        UserCountResponse response = new UserCountResponse(expectedCount)

        when:
        String json = objectMapper.writeValueAsString(response)
        UserCountResponse deserialized = objectMapper.readValue(json, UserCountResponse.class)

        then:
        verifyAll(deserialized) {
            count() != null
            count().value() == 42L
        }
    }

    def "can serialize and deserialize DeletedUserResponse"() {
        given:
        User user = new User().withId(501L).withName("Deleted User")
        DeletedUserResponse response = new DeletedUserResponse(user)

        when:
        String json = objectMapper.writeValueAsString(response)
        DeletedUserResponse deserialized = objectMapper.readValue(json, DeletedUserResponse.class)

        then:
        verifyAll(deserialized) {
            deletedUser() != null
            deletedUser().id() == 501L
            deletedUser().name() == "Deleted User"
        }
    }

    def "can create DeletedUsersResponse and add deleted users"() {
        given:
        User user1 = new User().withId(601L).withName("Deleted One")
        DeletedUsersResponse response = new DeletedUsersResponse(
                null,
                1L,
                "https://example.zendesk.com/api/v2/deleted_users?page=2",
                null
        )

        when:
        response = response.addDeletedUsersItem(user1)

        then:
        verifyAll(response) {
            deletedUsers() != null
            deletedUsers().size() == 1
            count() == 1L
            nextPage() == "https://example.zendesk.com/api/v2/deleted_users?page=2"
            previousPage() == null
        }

        when:
        String json = objectMapper.writeValueAsString(response)
        DeletedUsersResponse deserialized = objectMapper.readValue(json, DeletedUsersResponse.class)

        then:
        verifyAll(deserialized) {
            deletedUsers() != null
            deletedUsers().size() == 1
            deletedUsers()[0].id() == 601L
            count() == 1L
        }
    }

    def "can serialize and deserialize UserRelated and UserRelatedResponse"() {
        given:
        UserRelated related = new UserRelated()
                .withAssignedTickets(5)
                .withCcdTickets(2)
                .withOrganizationSubscriptions(1)
                .withRequestedTickets(10)
        UserRelatedResponse response = new UserRelatedResponse(related)

        when:
        String json = objectMapper.writeValueAsString(response)
        UserRelatedResponse deserialized = objectMapper.readValue(json, UserRelatedResponse.class)

        then:
        verifyAll(deserialized) {
            userRelated() != null
            userRelated().assignedTickets() == 5
            userRelated().ccdTickets() == 2
            userRelated().organizationSubscriptions() == 1
            userRelated().requestedTickets() == 10
        }
    }

    def "should verify User constants"() {
        expect:
        verifyAll {
            User.JSON_PROPERTY_ID == "id"
            User.JSON_PROPERTY_AGENT_BRAND_IDS == "agent_brand_ids"
            User.JSON_PROPERTY_EMAIL == "email"
            User.JSON_PROPERTY_NAME == "name"
            User.JSON_PROPERTY_ROLE == "role"
            User.JSON_PROPERTY_ACTIVE == "active"
            User.JSON_PROPERTY_ALIAS == "alias"
            User.JSON_PROPERTY_CHAT_ONLY == "chat_only"
            User.JSON_PROPERTY_CUSTOM_ROLE_ID == "custom_role_id"
            User.JSON_PROPERTY_DEFAULT_GROUP_ID == "default_group_id"
            User.JSON_PROPERTY_DETAILS == "details"
            User.JSON_PROPERTY_EXTERNAL_ID == "external_id"
            User.JSON_PROPERTY_IANA_TIME_ZONE == "iana_time_zone"
            User.JSON_PROPERTY_LAST_LOGIN_AT == "last_login_at"
            User.JSON_PROPERTY_LOCALE == "locale"
            User.JSON_PROPERTY_LOCALE_ID == "locale_id"
            User.JSON_PROPERTY_MODERATOR == "moderator"
            User.JSON_PROPERTY_NOTES == "notes"
            User.JSON_PROPERTY_ONLY_PRIVATE_COMMENTS == "only_private_comments"
            User.JSON_PROPERTY_ORGANIZATION_ID == "organization_id"
            User.JSON_PROPERTY_PHONE == "phone"
            User.JSON_PROPERTY_PHOTO == "photo"
            User.JSON_PROPERTY_REMOTE_PHOTO_URL == "remote_photo_url"
            User.JSON_PROPERTY_REPORT_CSV == "report_csv"
            User.JSON_PROPERTY_RESTRICTED_AGENT == "restricted_agent"
            User.JSON_PROPERTY_ROLE_TYPE == "role_type"
            User.JSON_PROPERTY_SHARED == "shared"
            User.JSON_PROPERTY_SHARED_AGENT == "shared_agent"
            User.JSON_PROPERTY_SHARED_PHONE_NUMBER == "shared_phone_number"
            User.JSON_PROPERTY_SIGNATURE == "signature"
            User.JSON_PROPERTY_SUSPENDED == "suspended"
            User.JSON_PROPERTY_TAGS == "tags"
            User.JSON_PROPERTY_TICKET_RESTRICTION == "ticket_restriction"
            User.JSON_PROPERTY_TIME_ZONE == "time_zone"
            User.JSON_PROPERTY_TWO_FACTOR_AUTH_ENABLED == "two_factor_auth_enabled"
            User.JSON_PROPERTY_URL == "url"
            User.JSON_PROPERTY_USER_FIELDS == "user_fields"
            User.JSON_PROPERTY_VERIFIED == "verified"
            User.JSON_PROPERTY_AUTHENTICITY_TOKEN == "authenticity_token"
            User.JSON_PROPERTY_CREATED_AT == "created_at"
            User.JSON_PROPERTY_UPDATED_AT == "updated_at"
        }
    }
}
