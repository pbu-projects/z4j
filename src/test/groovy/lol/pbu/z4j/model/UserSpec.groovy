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

import io.micronaut.json.JsonMapper
import spock.lang.Shared
import spock.lang.Specification

import java.time.ZoneOffset
import java.time.ZonedDateTime

class UserSpec extends Specification {

    @Shared
    JsonMapper jsonMapper

    void setupSpec() {
        jsonMapper = JsonMapper.createDefault()
    }

    def "can create user with getters setters and fluent chaining"() {
        given:
        def now = ZonedDateTime.of(2026, 1, 15, 10, 30, 0, 0, ZoneOffset.UTC)
        def photoAttachment = new Attachment().setId(555L).setFileName("avatar.png")

        when:
        def user = new User()
                .setId(101L)
                .setAgentBrandIds([1L, 2L])
                .setEmail("test.user@example.com")
                .setName("Test User")
                .setRole("agent")
                .setActive(true)
                .setAlias("Agent T")
                .setChatOnly(false)
                .setCustomRoleId(42L)
                .setDefaultGroupId(77L)
                .setDetails("Support specialist")
                .setExternalId("ext-101")
                .setIanaTimeZone("America/New_York")
                .setLastLoginAt(now)
                .setLocale("en-US")
                .setLocaleId(1L)
                .setModerator(true)
                .setNotes("Top performer")
                .setOnlyPrivateComments(false)
                .setOrganizationId(888L)
                .setPhone("+15555551234")
                .setPhoto(photoAttachment)
                .setRemotePhotoUrl("https://example.com/photo.png")
                .setReportCsv(false)
                .setRestrictedAgent(false)
                .setRoleType(1)
                .setShared(false)
                .setSharedAgent(false)
                .setSharedPhoneNumber(false)
                .setSignature("Thanks, Agent T")
                .setSuspended(false)
                .setTags(["support", "tier1"])
                .setTicketRestriction("assigned")
                .setTimeZone("Eastern Time (US & Canada)")
                .setTwoFactorAuthEnabled(true)
                .setUrl("https://example.zendesk.com/api/v2/users/101.json")
                .setUserFields(["department": "Support"])
                .setVerified(true)
                .setAuthenticityToken("token123")
                .setCreatedAt(now)
                .setUpdatedAt(now)

        then:
        verifyAll(user) {
            id == 101L
            agentBrandIds == [1L, 2L]
            email == "test.user@example.com"
            name == "Test User"
            role == "agent"
            active == true
            alias == "Agent T"
            chatOnly == false
            customRoleId == 42L
            defaultGroupId == 77L
            details == "Support specialist"
            externalId == "ext-101"
            ianaTimeZone == "America/New_York"
            lastLoginAt == now
            locale == "en-US"
            localeId == 1L
            moderator == true
            notes == "Top performer"
            onlyPrivateComments == false
            organizationId == 888L
            phone == "+15555551234"
            photo == photoAttachment
            remotePhotoUrl == "https://example.com/photo.png"
            reportCsv == false
            restrictedAgent == false
            roleType == 1
            shared == false
            sharedAgent == false
            sharedPhoneNumber == false
            signature == "Thanks, Agent T"
            suspended == false
            tags == ["support", "tier1"]
            ticketRestriction == "assigned"
            timeZone == "Eastern Time (US & Canada)"
            twoFactorAuthEnabled == true
            url == "https://example.zendesk.com/api/v2/users/101.json"
            userFields == ["department": "Support"]
            verified == true
            authenticityToken == "token123"
            createdAt == now
            updatedAt == now
        }
    }

    def "can add tags to user with null tags"() {
        given:
        def user = new User()

        when:
        user.addTagsItem("first_tag")

        then:
        verifyAll(user) {
            tags != null
            tags.size() == 1
            tags[0] == "first_tag"
        }
    }

    def "can add tags to user with existing tags"() {
        given:
        def user = new User().setTags(["first_tag"])

        when:
        user.addTagsItem("second_tag")

        then:
        verifyAll(user) {
            tags != null
            tags.size() == 2
            tags.contains("first_tag")
            tags.contains("second_tag")
        }
    }

    def "can serialize and deserialize user with JsonMapper"() {
        given:
        def user = new User()
                .setId(101L)
                .setName("Jane Doe")
                .setEmail("jane@example.com")
                .setRole("admin")
                .setActive(true)
                .setTags(["vip", "beta"])
                .setUserFields(["department": "Support"])

        when:
        def json = jsonMapper.writeValueAsString(user)
        def deserialized = jsonMapper.readValue(json, User)

        then:
        verifyAll(deserialized) {
            id == 101L
            name == "Jane Doe"
            email == "jane@example.com"
            role == "admin"
            active == true
            tags == ["vip", "beta"]
            userFields == ["department": "Support"]
        }
    }

    def "can deserialize user from raw JSON with snake case fields"() {
        given:
        def json = '''
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
        def deserialized = jsonMapper.readValue(json, User)

        then:
        verifyAll(deserialized) {
            id == 202L
            name == "John Doe"
            email == "john@example.com"
            role == "agent"
            customRoleId == 999L
            agentBrandIds == [1L, 2L]
            ianaTimeZone == "America/New_York"
            twoFactorAuthEnabled == true
        }
    }

    def "can serialize and deserialize UserResponse"() {
        given:
        def user = new User().setId(301L).setName("Test User")
        def response = new UserResponse(user)

        when:
        def json = jsonMapper.writeValueAsString(response)
        def deserialized = jsonMapper.readValue(json, UserResponse)

        then:
        verifyAll(deserialized) {
            user != null
            user.id == 301L
            user.name == "Test User"
        }
    }

    def "can serialize and deserialize UserRequest"() {
        given:
        def user = new User().setName("New User").setEmail("new@example.com")
        def request = new UserRequest().setUser(user)

        when:
        def json = jsonMapper.writeValueAsString(request)
        def deserialized = jsonMapper.readValue(json, UserRequest)

        then:
        verifyAll(deserialized) {
            user != null
            user.name == "New User"
            user.email == "new@example.com"
        }
    }

    def "can create UsersRequest and add users"() {
        given:
        def user1 = new User().setId(1L).setName("User One")
        def user2 = new User().setId(2L).setName("User Two")
        def request = new UsersRequest()

        when:
        request.addUsersItem(user1)
        request.addUsersItem(user2)

        then:
        verifyAll(request) {
            users != null
            users.size() == 2
            users[0].name == "User One"
            users[1].name == "User Two"
        }

        when:
        def json = jsonMapper.writeValueAsString(request)
        def deserialized = jsonMapper.readValue(json, UsersRequest)

        then:
        verifyAll(deserialized) {
            users != null
            users.size() == 2
            users[0].id == 1L
            users[1].id == 2L
        }
    }

    def "can serialize and deserialize UserCountResponse and UserCountResponseCount"() {
        given:
        def count = new UserCountResponseCount().setValue(42L)
        def response = new UserCountResponse(count)

        when:
        def json = jsonMapper.writeValueAsString(response)
        def deserialized = jsonMapper.readValue(json, UserCountResponse)

        then:
        verifyAll(deserialized) {
            count != null
            count.value == 42L
        }
    }

    def "can serialize and deserialize DeletedUserResponse"() {
        given:
        def user = new User().setId(501L).setName("Deleted User")
        def response = new DeletedUserResponse(user)

        when:
        def json = jsonMapper.writeValueAsString(response)
        def deserialized = jsonMapper.readValue(json, DeletedUserResponse)

        then:
        verifyAll(deserialized) {
            deletedUser != null
            deletedUser.id == 501L
            deletedUser.name == "Deleted User"
        }
    }

    def "can create DeletedUsersResponse and add deleted users"() {
        given:
        def user1 = new User().setId(601L).setName("Deleted One")
        def response = new DeletedUsersResponse()
                .setCount(1L)
                .setNextPage("https://example.zendesk.com/api/v2/deleted_users?page=2")
                .setPreviousPage(null)

        when:
        response.addDeletedUsersItem(user1)

        then:
        verifyAll(response) {
            deletedUsers != null
            deletedUsers.size() == 1
            count == 1L
            nextPage == "https://example.zendesk.com/api/v2/deleted_users?page=2"
            previousPage == null
        }

        when:
        def json = jsonMapper.writeValueAsString(response)
        def deserialized = jsonMapper.readValue(json, DeletedUsersResponse)

        then:
        verifyAll(deserialized) {
            deletedUsers != null
            deletedUsers.size() == 1
            deletedUsers[0].id == 601L
            count == 1L
        }
    }

    def "can serialize and deserialize UserRelated and UserRelatedResponse"() {
        given:
        def related = new UserRelated()
                .setAssignedTickets(5)
                .setCcdTickets(2)
                .setOrganizationSubscriptions(1)
                .setRequestedTickets(10)
        def response = new UserRelatedResponse().setUserRelated(related)

        when:
        def json = jsonMapper.writeValueAsString(response)
        def deserialized = jsonMapper.readValue(json, UserRelatedResponse)

        then:
        verifyAll(deserialized) {
            userRelated != null
            userRelated.assignedTickets == 5
            userRelated.ccdTickets == 2
            userRelated.organizationSubscriptions == 1
            userRelated.requestedTickets == 10
        }
    }
}
